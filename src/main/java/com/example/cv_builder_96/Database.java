package com.example.cv_builder_96;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Database {

    private static final String URL = "jdbc:sqlite:cv_builder.db";


    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }


    public static void createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    email TEXT UNIQUE NOT NULL,
                    phone TEXT,
                    address TEXT,
                    degree TEXT,
                    institution TEXT,
                    year TEXT,
                    jobTitle TEXT,
                    company TEXT,
                    duration TEXT,
                    projectTitle TEXT,
                    projectDescription TEXT
                );
                """;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Users table created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void insertUser(
            String name, String email, String phone, String address,
            String degree, String institution, String year,
            String jobTitle, String company, String duration,
            String projectTitle, String projectDescription
    ) {

        String sql = """
                INSERT INTO users(name, email, phone, address, degree, institution, year,
                                   jobTitle, company, duration, projectTitle, projectDescription)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
                """;

        try (Connection conn = connect();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, phone);
            pst.setString(4, address);
            pst.setString(5, degree);
            pst.setString(6, institution);
            pst.setString(7, year);
            pst.setString(8, jobTitle);
            pst.setString(9, company);
            pst.setString(10, duration);
            pst.setString(11, projectTitle);
            pst.setString(12, projectDescription);

            pst.executeUpdate();
            System.out.println("User inserted successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

