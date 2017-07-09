package com.scarabcoder.login;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * MySQL manager for making connections to the database and used for both Spigot and Proxy plugins.
 * Can also create tables, should be use for every plugin connecting to the database.
 */
public class MySQLManager {

    private static Connection connection;

    private static String database;

    public static void startConnection(String host, String port, String user, String password, String database) throws SQLException {

        connection = DriverManager.getConnection("jdbc:mysql://" + host + "/" + database + "?user=" + user + "&password=" + password);
        database = database;
    }

    public static void initializeDatabases() throws SQLException {

        connection.prepareStatement("CREATE TABLE IF NOT EXISTS users (uuid VARCHAR(36) PRIMARY KEY NOT NULL, username VARCHAR(16) NULL, hashedPass VARCHAR(60) NULL, premium TINYINT(1) DEFAULT 0)").executeUpdate();


    }

    public static Connection getConnection(){
        return connection;
    }

    public static String getDatabase(){
        return database;
    }

}
