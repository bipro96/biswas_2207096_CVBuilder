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

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Users table ensured.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static long insertUser(
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
             PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

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

            int affected = pst.executeUpdate();
            if (affected == 0) return -1;

            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }


    public static ObservableList<String> fetchUserById(long id) {
        ObservableList<String> data = FXCollections.observableArrayList();
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = connect(); PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setLong(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    data.add(rs.getString("name"));
                    data.add(rs.getString("email"));
                    data.add(rs.getString("phone"));
                    data.add(rs.getString("address"));
                    data.add(rs.getString("degree"));
                    data.add(rs.getString("institution"));
                    data.add(rs.getString("year"));
                    data.add(rs.getString("jobTitle"));
                    data.add(rs.getString("company"));
                    data.add(rs.getString("duration"));
                    data.add(rs.getString("projectTitle"));
                    data.add(rs.getString("projectDescription"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }


    public static boolean updateUser(
            long id,
            String name, String email, String phone, String address,
            String degree, String institution, String year,
            String jobTitle, String company, String duration,
            String projectTitle, String projectDescription
    ) {

        String sql = """
            UPDATE users SET
                name=?, email=?, phone=?, address=?, degree=?, institution=?, year=?, 
                jobTitle=?, company=?, duration=?, projectTitle=?, projectDescription=?
            WHERE id=?;
        """;

        try (Connection conn = connect(); PreparedStatement pst = conn.prepareStatement(sql)) {

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
            pst.setLong(13, id);

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    public static boolean deleteUser(long id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = connect(); PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setLong(1, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}