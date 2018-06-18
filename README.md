Prerequisites:

1. Java 8
2. Apache Maven 3.3.9

About the project:

Two http endpoints are provided which accept JSON base64 encoded binary data 

    <host>/api/v1/diff/<ID>/left
    <host>/api/v1/diff/<ID>/right

Provided data can be diff-ed and the results will be available on a third end point

    <host>/api/v1/diff/<ID>

The results will provide the following info in JSON format

    If equal return that
    If not of equal size just return that
    If of same size provide insight in where the diffs are

Input JSON format taken:

    {
      "field1": "c3RyaW5nMQ==",
      "field2": "c3RyaW5nMg==",
      "field3": "c3RyaW5nMw==",
      "field4": "c3RyaW5nNA=="
    }

The field names can be anything. The values should be some valid Base64 encoded strings.

Output/Response is also in JSON format

Steps to run the project using command line:
    
    Navigate to project directory structure and then in to data-differentiator folder in it.
    Run the command "mvn clean install"
    
    Run using an IDE(Intellij): 
    Import the project data-differentiator as Maven project
    Run the file DataDifferentiatorApplication.java
    
    Run using terminal:
    After doing "mvn clean install", navigate to <your-working-directory>/data-differentiator/application/target
    Run the command: java -jar data-differentiator-application-1.0-SNAPSHOT.jar
    
    Access the application at http://localhost:8080/
    This will redirect to swagger-ui page. If a blank page loads, kindly refresh the page.

Test cases details:

    Unit test cases:
        DataDiffServiceImplTest.java
        DataDiffValidatorTest.java

    Integration test cases:
        DataDiffApiImplTest.java


Database details:

    In-memory H2 database is used.
    It can be accessed at http://localhost:8080/h2-console after running the application
    Make sure the db details are as below:
    Driver Class: org.h2.Driver
    JDBC URL: jdbc:h2:mem:testdb
    User Name: sa
    Password: <Blank>

    Click the Connect button.
    Databse table: DIFF_DATA

Project structure:
    
    Spring boot based application.
    Parent module: data-differentiator
    Children: application and common-lib
    common-lib is included as a dependency in application module
    
    
Application flow:
    
    Incoming request accepted by DataDiffApiImpl.java 
    This calls the service DataDiffServiceImpl.java
    The service calls 
            DataDiffRepository.java to fetch data from database
            DataDiffValidator.java to perform validation on the data
                Exceptions thrown by validator are handled by DiffDataExceptionHandler.java
            Returns the response.

Suggestions for improvement:
    
    1. API naming convention
        It is good to have API naming convention as <host>/api/v1/ rather that <host>/v1
        It helps us to know that all the API resources are under /api/
        Also, if the API evolves, then the new api can be added in <host>/api/v2/
        (This suggestion has been included in the code)
        
    2. Send appropriate HTTP status codes to the client depending upon the result obtained.
        This helps the client to determine the nature of the result.
        (This suggestion has been included in the code)
        
    3. Swagger-UI
        Swagger UI can be used which gives the end user an interface with all the API details.
        (This suggestion has been included in the code)
    
    4. Functionality
        As per the problem statement, it seems that storing the left and right data is necessary to get the difference on a separate API.
        If persisting the data is not mandatory, then a single API can be implemented which can accept both left and right data 
        as JSON data and the difference can be returned as the response of the same API. 
        This will avoid interaction with database and will also not require the end user to pass the ID in the API.
        (This suggestion is not included in the code)
        
    5. Security
        Role based security can be applied to the APIs.
        Of the 3 APIs, 2 APIs to save left/right data can be accessed by one ROLE and the diff API can be accessed by another ROLE.
        (This suggestion is not included in the code)
        
        
        
