package com.example.cv_builder_96;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private static final String URL = "jdbc:sqlite:cv_builder.db";


    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Database connected successfully!");
        } catch (SQLException e) {
            System.out.println("Database connection failed!");
            e.printStackTrace();
        }
        return conn;
    }


    public static void initialize() {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                email TEXT UNIQUE NOT NULL,
                phone TEXT
            )
        """;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute(createTableSQL);
            System.out.println("Database initialized successfully!");

        } catch (SQLException e) {
            System.out.println("Database initialization failed!");
            e.printStackTrace();
        }
    }
}
