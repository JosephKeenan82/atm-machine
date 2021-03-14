# Neueda atm-machine
Demo project for atm-machine

----------------------
Build
----------------------
```
mvn clean install
```

----------------------
Run App
----------------------
```
docker-compose up 
```

(docker-compose.yml located in project root)

This will do the following:

 - create mysql database

 - create atm-machine image

 - run atm container
 
 - run mysql container


----------------------
REST Examples
----------------------
 
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
Statistics
-------------------------

- Number of tests: 12
- Code Coverage: > 90%

-------------------------
Swagger UI
-------------------------

Once application is running Swagger UI can be located at [http://localhost:8080/swagger-ui.html#](http://localhost:8080/swagger-ui.html#)


-------------------------
To Query Database
-------------------------
winpty docker exec -it f30b630383fd mysql -uroot -p (password: root)

**Note**: winpty is only necessary if running command from git bash)


-------------------------
Design Decisions
-------------------------

Used [Spring Data JPA](https://spring.io/projects/spring-data-jpa) for database CRUD actions as this gives many benefits out-of-the-box such as less boilerplate code and less configuration. 

For mvn clean install used jacoco code coverage plugin to ensure minimum % of code is covered and used docker-compose plugin to bring up mysql container for REST tests (uses docker-compose-mysql.yml).

Test from the REST level to ensure outputs are as expected and consistent. Tests use test.properties to communicate with mysl container.

Once a commit has occurred a build is triggered in [travis-ci.com](travis-ci.com). See .travis.yml for more information.

-------------------------
Known Issues
-------------------------

- Sometimes when running docker-compose up the database container has not fully come up and causes exceptions in the command line but once a retry has occurred it should have stabilised.