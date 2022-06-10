# Help

### Development

Cleaning and installing the project will run the liquibase migration included and create the tables. 
`` application.yml `` has the h2 db connection configurations. Db configs can be modified to use any other RDBMS

### Testing on development env
The application has the following endpoints
``/authenticate `` call this endpoint with request body: 
```json
{
   "username" : "username",
   "password": "password"
   } 
   ```
Response will looke like:
```json
{
   "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYnJhemVuZWIiLCJleHAiOjE2NTQ4MzM4OTgsImlhdCI6MTY1NDgzMDI5OH0.Qj_eknD7esZFshW8AOU6KERpdQQsJLqTCbGop33QrVU"``
  } 
```

Users need to first be registered on 
``/users`` before they can be used to login with. 
Request should look like: 
```json
{
  "username" : "username",
  "password": "password"
  } 
```

The ``accessToken`` above should be sent in the header as a bearer token in order to access the secured api's like:
* ``/me``
* POST ``/employees``, PUT ``/employees/id``, GET ``/employees`` and DELETE ``/employees/id`` 

## Swagger docs
Swagger documentation can be accessed on 
`` http://[host]: [port]/swagger-ui/index.html ``
Default link on development would be:
http://localhost:8080/swagger-ui/index.html

 