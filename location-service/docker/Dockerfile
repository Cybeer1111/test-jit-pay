FROM bellsoft/liberica-openjdk-debian:11.0.15-10
USER root
# Start script
COPY --chown=root:2020 start.sh /usr/share/app/bin/
# libraries
COPY --chown=root:2020 pack /usr/share/app/self/
# settings
COPY --chown=root:2020 src/main/resources /etc/app/
# set rights to directories; create app directory, user group and user for app.
RUN chmod -R ug=rx,o-rwx /etc/app /usr/share/app &&\
    find /etc/app /usr/share/app -type f -exec chmod ug-x {} \; &&\
    chmod ug+x /usr/share/app/bin/start.sh &&\
    install -o 2020 -g 2020 -m ug=rwx,o-rwx -d /var/lib/app &&\
    groupadd -g 2020 Heimdallr && useradd --no-log-init -r -s /bin/false -u 2020 -g Heimdallr Heimdallr
USER Heimdallr
WORKDIR /var/lib/app
ENTRYPOINT ["bash", "/usr/share/app/bin/start.sh"]
# JMX port inside container
EXPOSE 8686
# working directory
VOLUME /var/lib/app