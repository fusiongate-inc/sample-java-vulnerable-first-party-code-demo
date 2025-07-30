package com.example;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.Properties;

public class LoginServlet extends HttpServlet {

    private static final String DB_URL_PROP_KEY = "db.url";
    private static final String SESSION_KEY_PROP_KEY = "session.key";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || password == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing username or password");
            return;
        }

        try (Connection conn = getConnection()) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                HttpSession session = request.getSession();
                String sessionKey = getSessionKey();
                session.setAttribute("auth", sessionKey);
                response.getWriter().println("Logged in successfully!");
            } else {
                response.getWriter().println("Login failed.");
            }
        } catch (SQLException e) {
            logError(e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        }
    }

    private Connection getConnection() throws SQLException {
        String dbUrl = getConfigProperty(DB_URL_PROP_KEY);
        return DriverManager.getConnection(dbUrl);
    }

    private String getSessionKey() {
        return getConfigProperty(SESSION_KEY_PROP_KEY);
    }

    private String getConfigProperty(String key) {
        Properties props = new Properties();
        try (InputStream is = getServletContext().getResourceAsStream("/WEB-INF/config.properties")) {
            props.load(is);
            return props.getProperty(key);
        } catch (IOException e) {
            logError(e);
            return null;
        }
    }

    private void logError(Exception e) {
        ServletContext ctx = getServletContext();
        ctx.log("Error in " + getClass().getName(), e);
    }
}