#!/usr/bin/env bash

PROJECT_NAME='location-service'
PROJECT_VERSION='1.0'
APPLICATION_MAIN_CLASS='com.example.locationservice.LocationServiceApplication'

JAVAX_NET_DEBUG=""
[ -n "$JAVAX_NET_DEBUG_ITEMS" ] && JAVAX_NET_DEBUG="-Djavax.net.debug=${JAVAX_NET_DEBUG_ITEMS}"

IFS='' read -d '' -r OOM_ACTIONS <<'HEREDOC'
-XX:+ExitOnOutOfMemoryError
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=/var/lib/app
HEREDOC

IFS='' read -d '' -r RAM_OPTS <<'HEREDOC'
-XX:InitialRAMPercentage=50.0
HEREDOC

JUL_OPTS='-Djava.util.logging.manager=org.apache.logging.log4j.jul.LogManager'

IFS='' read -d '' -r MISC_OPTS <<HEREDOC
-Dfile.encoding=UTF-8
-Djava.net.preferIPv4Stack=true
-XX:-UsePerfData
HEREDOC

CP='-cp /etc/app/:/usr/share/app/self/*'

# JMS settings
IFS='' read -d '' -r JMX_LINE <<HEREDOC
-Dcom.sun.management.jmxremote.port=8686
-Dcom.sun.management.jmxremote.rmi.port=8686
-Djava.rmi.server.hostname=0.0.0.0
-Dcom.sun.management.jmxremote.authenticate=false
-Dcom.sun.management.jmxremote.ssl=false
-Dcom.sun.management.jmxremote.local.only=false
HEREDOC

JDK_JAVA_OPTIONS="$CP $OOM_ACTIONS $RAM_OPTS $JUL_OPTS $MISC_OPTS $JAVAX_NET_DEBUG $JMX_LINE"
JDK_JAVA_OPTIONS=${JDK_JAVA_OPTIONS//$'\n'/ }
export JDK_JAVA_OPTIONS

###############################################
# Define variables
###############################################

# default values
: "${DEBUG_SUSPEND:=n}"
: "${MAX_RAM_PERCENTAGE:=75.0}"

# Set JVM max memory
MAX_RAM_USAGE="-XX:MaxRAMPercentage=${MAX_RAM_PERCENTAGE}"

echo "--- --- --- starting location-service version 1.0 --- --- ---"

exec java -Dv=$PROJECT_VERSION $MAX_RAM_USAGE $JDK_JAVA_OPTIONS $APPLICATION_MAIN_CLASS
