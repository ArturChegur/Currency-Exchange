# Currency Exchange Project

This project aims to provide a REST API for managing currencies and exchange rates. It allows users to view and edit lists of currencies and exchange rates, as well as perform currency conversion calculations.

Introduction

The project is built using Java and follows the MVC (Model-View-Controller) pattern. It uses Maven as the build tool and relies on Java servlets for the backend functionality. HTTP GET and POST requests are used for communication, with responses provided in JSON format. The project utilizes an SQLite database for data storage.

Features

Backend
Java Servlets: Backend functionality is implemented using Java servlets to handle HTTP requests and responses.
SQLite Database: Data is stored in an SQLite database, with separate tables for currencies and exchange rates.
REST API
The REST API provides the following endpoints for managing currencies and exchange rates:

Currencies

GET /currencies: Retrieves a list of all currencies.

GET /currency/{code}: Retrieves information about a specific currency.

POST /currencies: Adds a new currency to the database.

Exchange Rates

GET /exchangeRates: Retrieves a list of all exchange rates.

GET /exchangeRate/{baseCode}{targetCode}: Retrieves information about a specific exchange rate.

POST /exchangeRates: Adds a new exchange rate to the database.

PATCH /exchangeRate/{baseCode}{targetCode}: Updates an existing exchange rate in the database.
Currency Exchange

GET /exchange?from={baseCode}&to={targetCode}&amount={amount}: Performs a currency conversion calculation.

Setup and Deployment

To deploy the project, follow these steps:

Clone the repository to your local machine.
Make sure you have Java and Apache Tomcat installed.
Build the project using Maven: mvn clean install.
Deploy the generated WAR file to your Tomcat server.
Access the API endpoints using appropriate HTTP requests.

Project`s technical specifications - https://zhukovsd.github.io/java-backend-learning-course/Projects/CurrencyExchange/

Author - [ArturChegur]
