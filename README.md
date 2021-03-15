# atm-machine
Demo project for atm-machine

----------------------
Build
----------------------
```
mvn clean install
```
During the build Spring Boot REST tests will run. A mysql container will spin up as specified in pom.xml and once tests have run it will be torn down.

----------------------
Run App
----------------------
```
docker-compose up 
```

(docker-compose.yml located in project root)

This will do the following:

 - create atm-machine image

 - run atm container
 
 - run mysql container

 - connect them on the same network (atm_network)
 
 Application is ready the you can see the following in the logs: **System is starting, initialising default accounts, & resetting ATM balance**
 
 You can also check if the system is up by using [localhost:8080/actuator/health](localhost:8080/actuator/health)

----------------------
REST Examples
----------------------
 ```
Note: Postman JSON file located in project root
```

  -  Get all accounts: 
  
[localhost:8080/account](localhost:8080/account)

  -  Get an account: 
  
[(localhost:8080/account/1](localhost:8080/account/1)
		
  -  Get account balance: 
  
[localhost:8080/balance?id=1&pin=1234](localhost:8080/balance?id=1&pin=1234)

  -  Get account balance: 
  
[localhost:8080/balance?id=1&pin=123](localhost:8080/balance?id=1&pin=123)

  -  Withdraw cash: 
  
[localhost:8080/cash?id=1&pin=1234&cash=190](localhost:8080/cash?id=1&pin=1234&cash=190)

  -  Check ATM balance: 
  
[localhost:8080/atmcash](localhost:8080/atmcash)
		
  -  Exceed max ATM amount: 
  
[localhost:8080/cash?id=1&pin=1234&cash=1550](localhost:8080/cash?id=1&pin=1234&cash=1550)
		
  -  Incorrect PIN: 
  
[localhost:8080/cash?id=1&pin=123456&cash=100](localhost:8080/cash?id=1&pin=123456&cash=100)

-------------------------
Design Decisions
-------------------------

Used [Spring Data JPA](https://spring.io/projects/spring-data-jpa) for database CRUD actions as this gives many benefits out-of-the-box such as less boilerplate code and less configuration. 

For **mvn clean install** I used jacoco code coverage plugin to ensure minimum % of code is covered and used docker-compose plugin to bring up mysql container for REST tests (uses docker-compose-mysql.yml).

Tested from the REST level to ensure outputs are as expected and consistent. Tests use **test.properties** to communicate with mysl container. Code coverage then ensure that lines and branches are reached by these tests.

Did not add Atm class to mysql database as there is only one of these and it can be held in memory.

I chose not to javadoc entities or Swagger classes but other classes have javadocs.

-------------------------
Known Issues
-------------------------

- Sometimes when running **docker-compose up** the database container has not fully come up and causes exceptions in the command line but once a retry has occurred it will have stabilized.

- There may be some "problems" highlighted in the pom for docker-compose-maven-plugin & maven-antrun-plugin but these can be safely ignored - they do not interfere with the application.

- mysql docker container created with **--initialize-insecure option**. This is not secure and not recommended for production but for a demo it is sufficient.


-------------------------
CI/CD
-------------------------

Once a commit has occurred a build is triggered in [travis-ci.com](travis-ci.com). A hook is added to github from travis-ci which triggers the build whenever code is committed. Information for the build is obtained from the simple **.travis.yml**.

-------------------------
Swagger UI
-------------------------

Once application is running Swagger UI can be located at [http://localhost:8080/swagger-ui.html#](http://localhost:8080/swagger-ui.html#)


-------------------------
To Query Database
-------------------------
winpty docker exec -it <image-id> mysql -uroot -p (password: root)

**Note**: winpty is only necessary if running command from git bash)

-------------------------
Statistics
-------------------------

- Number of RESTtests: 14
- Code Coverage: > 90%
- Sonarlint violations: 0
