package tech.lmru.client;

import java.sql.*;

public class JdbcClient {
    static final String DATABASE_URL = "jdbc:postgresql://o-cmpl-pg-db-01.ru-central1.internal:5435/communication"; //preprod
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    //static final String DATABASE_URL = "jdbc:postgresql://localhost:55432/communication";//local
    //static final String DATABASE_URL = "jdbc:postgresql://t-cmpl-pg-db-01.ru-central1.internal:5435/communication"; //test

    static final String USER = "communication_rw_user"; //test and preprod
    static final String PASSWORD = "8wX5q6ewWy4HLc5g"; //preprod
    //static final String USER = "communication"; //local
    //static final String PASSWORD = "communication"; //local
    //static final String PASSWORD = "B8exV5rgdKBbWPSU"; //test

    public static Statement statementDB;
    private static Connection connectionDB;

    public static Statement getConnectionDB() throws ClassNotFoundException, SQLException {
        System.out.println("Registering JDBC driver...");
        Class.forName(JDBC_DRIVER);

        System.out.println("Creating connection to database...");
        connectionDB = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);

        System.out.println("Getting records...");
        statementDB = connectionDB.createStatement();
        return statementDB;
    }

    public static void closeConnectionDB() throws ClassNotFoundException, SQLException {
        if (statementDB != null) {
            statementDB.close();
        }
        if (connectionDB != null) {
            connectionDB.close();
        }
    }}
