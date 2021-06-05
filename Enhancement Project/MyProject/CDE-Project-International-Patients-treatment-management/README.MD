# CDE Project International Patients Treatment Management System

International Patients Treatment Management System is a web application to list and choose the treatment packages for International patients, register them, track their treatment to closure through insurance claim.


* The application can be broken down into four microservices and one web client
* The Microservices were independently tested and deployed on cloud
* The microservices used H2 In-memory database and performed CRUD operations
* These services were consumed from an MVC app running in a local environment
* The microservices were 
    * International patient treatment offerings module
    * International patient treatment module
    * Insurance claim module
    * Authorization service

* Enhancements points

    * Enabled the admin to update treatment packages available for international patients.
    * Enabled the admin to add new specialist details for International patients.
    * Enabled the admin to delete a specific specialist.
    * Provided with the list of specialists by their area of expertise.
    * Provided an interface to add a new specialist.
    * Enhanced "our specialists" page to support delete functionality of a specific specialist.
    * Enhanced "our specialists" page to support filtering the list of specialists based on their area of expertise.
    * Enhanced "our specialists" page to support reset functionality to clear the applied filter.
    * Enhanced "our service packages" page to support update functionality of treatment package.
	* Provided an interface to update treatment package details.
	* Added exception handling for both add specialist detail and update treatment package functionalities.
    * Functions within respective controllers and services, models, and JSP code for UI, were implemented according to the above requirements.
    


	

## Technologies used

    Java 11 
    Spring Data Access JPA 
    Spring Boot 
    Spring MVC 
    Spring REST 
    Microservices 
    AWS 
    Lombok Logging 
    Spring security 
    Junit, Mockito, MockMVC 
    H2 in-memory Database