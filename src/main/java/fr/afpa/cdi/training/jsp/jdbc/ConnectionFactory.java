/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.afpa.cdi.training.jsp.jdbc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cdi420
 */
public class ConnectionFactory {

    private static final ConnectionFactory instance = new ConnectionFactory();

    private String DRIVER_NAME;
    private String DATABASE_URL;
    private String DATABASE_USER;
    private String DATABASE_PASSWORD;

    public ConnectionFactory() {
        try {
            Properties properties = new Properties();
            properties.load(ConnectionFactory.class.getResourceAsStream("/properties/config.properties"));
            DRIVER_NAME = properties.getProperty(properties.getProperty("driver_name"));
            DATABASE_URL = properties.getProperty(properties.getProperty("database_url"));
            DATABASE_USER = properties.getProperty(properties.getProperty("database_user"));
            DATABASE_PASSWORD = properties.getProperty(properties.getProperty("database_password"));
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException | FileNotFoundException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Connection createConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        return connection;
    }

    public static Connection getConnection() throws SQLException {
        return instance.createConnection();
    }

}
