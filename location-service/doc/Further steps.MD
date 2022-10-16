# Further steps

## Code
1. Remove unnecessary id from user_location table.
2. Catch unique constraint violation at UserLocation in a more targeted way.
All DTO and DOM objects can be generated.
3. Integrate project with swagger.

## Speed and scalability
1. Insert data in batches using producer-consumer model to improve insert speed
2. To achieve infinite horizontal scalability change DB to non-relational shardable DB

## Requirements
1. Requirements need to be clarified and expanded.

## Monitoring
1. All DB operations should send metrics
   1. Counter metrics
   2. Speed metrics

## Testing
1. Unit tests - test coverage should be increased.
2. Load tests - load tests should be created to prove compliance with the requirements and prove absence of concurrent date changes issues.
3. Integration tests - should verify external contract requirements.
