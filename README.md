# Springboot PostgreSQL Template

https://api.google.com
**Packaging** - com.google.api

Model - Define entities(tables)
Repositories - Model <-> JPA
Services - Logic implementation (tell the repository to run some stuffs).
Controllers - API Endpoints 

**Requests**
GET
POST
PUT - Used to update the whole model
DELETE
PATCH - It is used to update certain field of the model

**Enums, DTOs, Interfaces**
Enum - Datatypes use to define constant stuff e.g Sex(MALE, FEMALE), Nationality
DTOs - Data Transfer Object - We select only the data that we need
Interfaces - Unimplemented methods

**ORMs** - Object Relational Mapping - Hibernate
Hibernate -> (JPA) Java Persistence Annotation

**CORs** - Cross Origin Request Mapping

**Spring Security**
Springboot - Has its own security implementation

```
User: user
Password: rwerw-rwerw-wrer-werer
From UsernamePasswordAuthenticationToken
credentials to null

```

JWT Token, Find user by email, generating token

Spring Container(To inject sth it has to be a bean).
A bean is initiated at startup