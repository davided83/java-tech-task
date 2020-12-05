# Lunch Microservice

The service provides an endpoint that will determine, from a set of recipes, what I can have for lunch at a given date, based on my fridge ingredient's expiry date, so that I can quickly decide what I’ll be having to eat, and the ingredients required to prepare the meal.

## Prerequisites

* [Java 11 Runtime](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
* [Docker](https://docs.docker.com/get-docker/) & [Docker-Compose](https://docs.docker.com/compose/install/)

*Note: Docker is used for the local MySQL database instance, feel free to use your own instance or any other SQL database and insert data from lunch-data.sql script* 


### Run

1. Start database:

    ```
    docker-compose up -d
    ```
   
2. Add test data from  `sql/lunch-data.sql` to the database. Here's a helper script if you prefer:


    ```
    CONTAINER_ID=$(docker inspect --format="{{.Id}}" lunch-db)
    ```
    
    ```
    docker cp sql/lunch-data.sql $CONTAINER_ID:/lunch-data.sql
    ```
    
    ```
    docker exec $CONTAINER_ID /bin/sh -c 'mysql -u root -prezdytechtask lunch </lunch-data.sql'
    ```
    
3. Run Springboot LunchApplication
    
    mvn org.springframework.boot:spring-boot-maven-plugin:run 


## Personal considerations
Considering the 2-hour time, this is what I was able to achieve and what could have been improved further:

- Setup my local environment (install java 11, Docker, MySql, change local settings)
- Adapt configuration to my workspace (pom.xml, application.properties, ...)
- Fix some existing bugs in entities and service
- change lunch-data.sql to adapt to naming conventions
- refactor some code with more readable comments
- sort the resultSet by useBy and bestBefore dates
- create unit test LunchControllerTest as @SpringBootTest
- test the API with Postman
- LunchController.java can be improved in terms of @PostMapping @GetMapping
- LunchExceptionHandler.java can be changed to be more contextual, relevant and searchable
- LunchApplication.java can be enhanced with some logs
- There should be more extensive and detailed test units attached to the project
- UnitTest is still not runnable

