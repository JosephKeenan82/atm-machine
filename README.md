# atm-machine
Demo project for atm-machine

----------------------
Run App
----------------------
docker-compose up

This will 

 - create mysql database

 - create atm-machine image

 - run atm application


----------------------
REST Examples
----------------------
 
  -  Get all accounts: 
  
		localhost:8080/accounts

  -  Get an account: 
  
		localhost:8080/accounts
		
  -  Get account balance: 
  
		localhost:8080/balance?id=1&pin=1234

  -  Get account balance: 
  
		localhost:8080/balance?id=1&pin=123

  -  Withdraw cash: 
  
		localhost:8080/cash?id=1&pin=1234&cash=190

  -  Check ATM balance: 
  
		localhost:8080/atmcash
		
  -  Exceed max ATM amount: 
  
		localhost:8080/cash?id=1&pin=1234&cash=1550
		
  -  Incorrect PIN: 
  
		localhost:8080/cash?id=1&pin=123456&cash=100

-------------------------
Design Decisions & Issues
-------------------------

To build a new image, if required, for docker-compose running mvn clean install will need to have a running instance of mysql. I attempted to run a mysql image as part of the build but did not have time to finish this. 

Steps are:

	docker-compose -f docker-compose-mysql.yml up
	run mvn clean install
	docker image build -t atm-machine .
	docker-compose -f docker-compose-mysql.yml down
	docker-compose up
