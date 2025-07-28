package com.example;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class LoginServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:sqlite:users.db"; // Hardcoded DB path
    private static final String SESSION_KEY = "hardcoded-session-key"; // Insecure

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            Statement stmt = conn.createStatement();

            // ❌ SQL Injection Vulnerability
            String query = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                // ✅ Login success
                HttpSession session = request.getSession();
                session.setAttribute("auth", SESSION_KEY);
                response.getWriter().println("Logged in successfully!");
            } else {
                response.getWriter().println("Login failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Database error.");
        }

        // ❌ Insecure Deserialization
        String data = request.getParameter("payload");
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data.getBytes()))) {
            Object obj = ois.readObject(); // Unsafe
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
