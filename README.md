# Challenges

This ReadMe file will explain the steps needed to try out the challenges. It would be best if you could download the project and open it on Intellij. 
The saved .iml files will set most of the project configuration and would require you to set the jdk information in Project Structure menu of Intellij.

## Challenge1

The only requirement is to install Postgresql on your local machine and use its port in application.properties.
The project will automatically create a database, the required tables and indexes along with test data using a utility called Liquibase.

## Backend API
The url to hit the backend is `http://localhost:8080/users`. This API takes 2 optional parameters.
Its sortBy and filter. Valid options for sortBy are name and age. If you want descending order, simply specify a `-` in front of the sort type as in `-name` or `-age`.
The filter param is a key value pair. Here are some example of API variation:

```
http://localhost:8080/users?sortBy=name
http://localhost:8080/users?filter=lastname:Kay
http://localhost:8080/users?sortBy=age&filter=age:30
http://localhost:8080/users?sortBy=age&filter=age:30,lastname:Kay
http://localhost:8080/users?sortBy=age&filter=age:30,lastname:Kay&start=0&limit=100
```

##Challenge 1 - React app
If opened on Intellij, you would see a run config called "challenge1-frontend" which basically does a `npm start`.
For the purpose of this challenge and in the interest of time, pagination is not implemented.
But the backend API is capable of doing pagination using start and limit params.

## Challenge 2
To test challenge2, run Challege2.java which is a standalone java class file.

