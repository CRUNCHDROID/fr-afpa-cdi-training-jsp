/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.afpa.cdi.training.jsp.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cdi420
 */
public class ConnectionFactory {

    private static final ConnectionFactory instance = new ConnectionFactory();

    private final String DRIVER_NAME = "com.mysql.jdbc.Driver";
    private final String DATABASE_URL = "jdbc:mysql://localhost/jdbc_db";
    private final String DATABASE_USER = "root";
    private final String DATABASE_PASSWORD = "";

    public ConnectionFactory() {
        try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException ex) {
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
