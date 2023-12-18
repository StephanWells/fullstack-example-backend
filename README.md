# Full-Stack Example Backend

This is a repository demonstrating a backend that is part of a full-stack survey application.

The goal of the survey is to ask for a user's basic information and what kind of loan they are interested in, and then
saving that information into a database.

The database used here is a MySQL database. It has been hooked up with a Hibernate ORM, and all the database schemas,
definitions, and tables are created programmatically.

An API is exposed with endpoints to access and create the database entities.

**Features**:

- High extensibility and maintainability.
- Error handling for API requests.
- Supported loan types and currencies defined as enums that are automatically synchronised with the database.
- DAO (Data Access Object), DTO (Data Transfer Object), controllers, converters, and Database model entities for every
  resource.
- Validation for loan applicants, loans, recurring expenses, and income sources.

## Initial setup

1. Install [MySQL](https://dev.mysql.com/downloads/mysql/). Ensure that the MySQL service is running.
2. Install [Java 14](https://www.oracle.com/java/technologies/javase/jdk14-archive-downloads.html). Ensure it has been
   added to your PATH.
3. Install [Maven](https://maven.apache.org/download.cgi). Ensure it has been added to your PATH.
4. Create an `application.properties` file in `src/main/resources` with the following information:
   1. **db.host**: The host name of MySQL service that is running, e.g. `jdbc:mysql://localhost`.
   2. **db.port**: The port of the MySQL service that is running, e.g. `3306`.
   3. **db.user**: Your MySQL username, e.g. `root`.
   4. **db.password**: Your MySQL password, e.g. `1234`.
   5. **db.name**: The name of the database that will be created, e.g. `loans`.
   6. **api.host**: The host that the API will listen on, e.g. `localhost`.
   7. **api.port**: The port that the API will listen on, e.g. `8080`.

Example `application.properties` file:

```properties
# Database config
db.host=jdbc:mysql://localhost
db.port=3306
db.username=root
db.password=1234
db.name=loans
# API config
api.host=localhost
api.port=8080
```

## Running

To run the application, run the following command in the terminal:

```bash
mvn install exec:java
```

To ensure that it works, check the logs. An API log saying `>> Listening on localhost:8080` (which will change depending
on what values were set in `application.properties`) indicates that the backend is up and running.

## Testing

You can test the API using a tool such as [Postman](https://www.postman.com/downloads/). Currently, the following
endpoints are exposed:

- `GET /loanApplicants`
- `GET /loanApplicants/:id`
- `POST /loanApplicants`
- `GET /loans`
- `GET /loans/:id`
- `POST /loans`
- `GET /currencies`
- `GET /loanTypes`
