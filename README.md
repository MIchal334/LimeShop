# LimeShop
The main goal of this project was to make backend for web app Limeshop. This app is made for lime trade. The system has three actors:

Client - has connections with lime dealers, checking informations about it's purcheses and making new ones.

Dealer - has list of clients and resources of lime, can accept a transaction and check other current transactions.

Admin - is able of checking informations about all transactions in the system. Has power to remove users. This kind of account can not be created. It is created automatically. 

Technologies:
Java, Spring Boot, Flayway, Keycloak, Docker, GIT, MySQL.


Start of application: 
In order to run the application, file 'docker-compose.ymal' is necessary. It is located in the repository. Command "docker-compose up" should be run from console in folder, where 'docker-compose.ymal' is located. After a few minutes application should be ready to use. Keycloak console is available at adress "localhost:8180", where useres in the system can be managed (login: "admin" password: "admin"). Useing MySQL Workbench it is possible to connest and have look at data base (login: "limeU", password: "limeP"). Endpoints structure is available at "localhost:8090/swagger-ui.html". The repository contains also file with endpoints exported from PostMan. One of the most imporant endpoints, whitch is not visible in swagger is endpoint at adress "http://127.0.0.1:8081/auth". This endpoint returns authorization token. In order to get the token this endpoint should be execute with type "x-www-form-urlencoded" and with body "username" and "password". This endpoint is temporary, just for beckend testing.  
