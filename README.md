
# Project Title

Coding assignment for Optus/Greenlight.
I've intentionally omitted the instructions PDF - I didn't think you'd want that published.

I've assumed:

- For word counts, case is insignificant (this seems to be confirmed by examples in spec).
- The server is given the text to be analyzed before the /search and /top requests are made so that different texts can be analyzed.

One of the questions requires JSON response, while the other requires a text response.
I've implemented both to support either format.
I considered accepting the request data in either format, too, but decided against it.
Generally unwise to expose more necessary in an API, especially without consultation.


## Getting Started

Service is currently configured to run on port 8080 and can be exercised by starting service with:

```
mvn spring-boot:run
```

and then using another shell to run scripts:

```
bash scripts/analyze.sh  # to load test data
bash scripts/search.sh   # to exercise search operation
bash scripts/top5.sh     # to exercise top operation
bash scripts/topN.sh 30  # to exercise top operation
```

### Prerequisities

* linux (or similar) platform to support shell scripts and instructions.
* maven
* java 1.7 or later

## Running the tests

Tests can be run with:

```
mvn -P integration-test
```

I have not separated unit and integration tests.


## Acknowledgments

I sought assistance from a number of sites while doing this, including:

http://docs.spring.io/spring-boot/docs/current/reference/html/getting-started-first-application.html

http://stackoverflow.com/questions/7161638/how-do-i-use-a-custom-serializer-with-jackson

http://www.sothawo.com/2015/07/build-a-spring-boot-rest-service-with-basic-authentication-for-several-users/

