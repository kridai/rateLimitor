# rateLimiter
A spring boot application for Implementing Rate limiter in an API

Step to run the Application
-Clone https://github.com/kridai/rateLimitor.git
- run ./mvnw spring-boot:run
- hit API localhost:8080/api/greeting  the only api currently exposed
- we need to pass X-Client-ID. this is required to indentify client uniquely, Idealy there should be authorization for this, but that would have increased the scope


The Rate Limt attribute is defined in https://github.com/kridai/rateLimitor/blob/main/src/main/resources/application.properties 
api.hourly.rate.limit = 5 . Meaning only 5 calls to an api is allowed per hour via a specific client.

We are using Spring ConcurrentMapCacheManager to cache the data for each client.  

