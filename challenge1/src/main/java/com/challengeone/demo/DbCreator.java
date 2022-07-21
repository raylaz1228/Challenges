package com.challengeone.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class DbCreator implements CommandLineRunner {

    @Value("${spring.datasource.url}")
    private String DB_URL;

    @Value("${spring.datasource.username}")
    private String DB_USERNAME;

    @Value("${spring.datasource.password}")
    private String DB_PASSWORD;

    @Value("${spring.datasource.driver-class-name}")
    private String DB_DRIVER;


    @Override
    public void run(String... args) throws Exception {
        Connection connection = null;
        Statement statemtnt = null;

        try {
            Class.forName(DB_DRIVER);
            try {
                connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            } catch (SQLException e) {
                //database does not exist, need to create it. Connect to default postgres database
                String databaseName = DB_URL.substring(DB_URL.lastIndexOf("/") + 1);
                String defaultPostgresDatabase = DB_URL.replace(databaseName, "postgres");

                connection = DriverManager.getConnection(defaultPostgresDatabase, DB_USERNAME, DB_PASSWORD);
                statemtnt = connection.createStatement();
                statemtnt.executeUpdate("CREATE DATABASE " + databaseName);
                statemtnt.executeUpdate("GRANT ALL PRIVILEGES ON DATABASE " + databaseName + " TO " + DB_USERNAME);
            }
        } catch (Exception e) {
            System.err.println("Failed to create database:"+ e);
            throw new RuntimeException("Failed to create database!", e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (statemtnt != null) {
                    statemtnt.close();
                }
            } catch (SQLException e) {
                System.err.println("Unable to close resource:"+ e);
            }
        }
    }
}
