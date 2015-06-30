**Database Action Monitor**
-

Database Action Monitor Spring Boot application using ActiveMq and Spring WebSocket



Database Action Monitor
-----------------------
This application is designed to monitor every actions (INSERT,UPDATE,DELETE) on a table called "messages".
The database is H2 Database, a single file based db.
This table simply contains text messages just for testing purposes.
There are some REST endpoints for manual testing.
For manual testing, there you can find Database_Action_Monitoring.json.postman_collection that you can import to Postman.
Using Postman collection you can add/delete/update messages, check status of the application and check the actual version of the application.
This is a standalone Spring Boot based application.

Build & Run
-----------
First of all go to the project's directory and run the following command from the command line:
mvn clean package
Then type: java -jar target/action-monitor-1.0.jar

How it works
------------
First of all when application starts, the database will created and will be filled with some test data.
Then you have to open http://localhost:8080 and click on "Connect" button.
Then you will notified when a row inserted, updated or deleted.
You can open that URL on many tabs, and you can see that if something happen on the table, you will see the event message on every tabs.

Logs & DB
---------
Every log files are located in the directory where you started from the application/tomcat/logs.
In the same directory (where you started from the app) there you can find the database file also.

Testing
-------
To run test simply go into the project directory and type: mvn test
For manual testing there is a Postman collection called Database_Action_Monitoring.json.postman_collection.
For testing main functionality open http://localhost:8080 in the browser and click on "Connect" button.
Then import Postman collection and try out some of them, and you will see every actions in browser.