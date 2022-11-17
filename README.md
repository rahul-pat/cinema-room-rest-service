# Cinema Room REST Service Project

This project is part of the Spring Security for Java Backend Developers course 
track at [JetBrains Academy](https://www.jetbrains.com/academy/).

## Table of contents
* [Technologies](#technologies)
* [Getting started](#getting-started)
* [Project details](#project-details)

## Technologies

This project was built using the following:
* Spring Boot framework version 2.7.5
* Spring Web MVC module
* Jackson library version 2.13.4
* Gradle
* Java 17


## Getting started

To use this Spring Boot application, simply clone this repository and 
run CinemaRoomRestService.java.

The application will start running on localhost port 28852.

## Project details

This Spring Boot application is a back-end service written in Java that allows a client to 
purchase tickets, refund tickets, view available seats and view the performance stats
of a given movie cinema.

This application contains the following entities (classes):
* Seat.java
* Purchases.java
* Token.java
* Cinema.java

And the following REST Controller:
* CinemaController.java

Clients can make perform the actions by calling the REST endpoints defined in 
the CinemaController.java class.
* View all available seats for purchase from the "/seats" endpoint 
* Purchase a ticket from the "/purchase" endpoint
* Refund a ticket from the "/return" endpoint
* View cinema stats from the "/stats" endpoint

### View all available seats

Using an application such as Postman, perform an HTTP ```GET``` request 
to ```localhost:28852/seats```.

Expected response:

```
{
    "total_rows": 9,
    "total_columns": 9,
    "available_seats": [
        {
            "row": 1,
            "column": 1,
            "price": 10
        },
        {
            "row": 1,
            "column": 2,
            "price": 10
        },
        {
            "row": 1,
            "column": 3,
            "price": 10
        },
        {
            "row": 1,
            "column": 4,
            "price": 10
        },
        
        [.....]
        
        {
            "row": 9,
            "column": 5,
            "price": 8
        },
        {
            "row": 9,
            "column": 6,
            "price": 8
        },
        {
            "row": 9,
            "column": 7,
            "price": 8
        },
        {
            "row": 9,
            "column": 8,
            "price": 8
        },
        {
            "row": 9,
            "column": 9,
            "price": 8
        }
    ]
}

```

Calling this endpoint after purchasing tickets will no longer show the seats
already purchased.

### Purchase a seat

Using an application such as Postman, perform an HTTP ```POST``` request
to ```localhost:28852/purchase``` and include the seat you would like to purchase in
the request body:

```
{
    "row": 1,
    "column": 1
}
```

Expected response:

```
{
    "ticket": {
        "row": 1,
        "column": 1,
        "price": 10
    },
    "token": "439d93aa-b189-4180-8b81-4c7c47c533d8"
}
```

The token can be used to refund your ticket

If you attempt to purchase a ticket for a seat that is already sold, 
the expected response will be:

```
{
    "error": "The ticket has been already purchased!"
}
```

If you attempt to purchase a seat that is out of bounds (for example a seat in Row 10), 
the expected response will be:

```
{
    "error": "The number of a row or a column is out of bounds!"
}
```

### Refund a ticket

Using an application such as Postman, perform an HTTP ```POST``` request
to ```localhost:28852/return``` and include your token in the request body:


```
{
    "token": "439d93aa-b189-4180-8b81-4c7c47c533d8"
}
```

Expected response:

```
{
    "returned_ticket": {
        "row": 1,
        "column": 1,
        "price": 10
    }
}
```
If an invalid token is provided, the expected response will be:

```
{
    "error": "Wrong token!"
}
```

### View cinema stats

Using an application such as Postman, perform an HTTP ```POST``` request
to ```localhost:28852/stats``` and include as query params ```password=super_secret```
or simply ```localhost:28852/stats?password=super_secret```

Expected response after purchasing one ticket:
```
{
    "current_income": 10,
    "number_of_purchased_tickets": 1,
    "number_of_available_seats": 80
}
```

If the wrong password or no password is provided, the expected response will be:

```
{
    "error": "The password is wrong!"
}
```