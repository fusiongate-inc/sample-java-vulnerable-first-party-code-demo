package com.example;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

/**
 * Servlet for handling user comments.
 */
public class CommentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String comment = request.getParameter("comment");

        // Validate and sanitize the comment parameter
        if (comment == null || comment.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid comment parameter");
            return;
        }
        comment = sanitizeComment(comment);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Output the comment in a safe manner
        out.println("<html><body>");
        out.println("<h2>Your Comment:</h2>");
        out.println("<p>" + comment + "</p>");
        out.println("</body></html>");
    }

    /**
     * Sanitizes the comment string to prevent XSS attacks.
     *
     * @param comment The comment string to be sanitized.
     * @return The sanitized comment string.
     */
    private String sanitizeComment(String comment) {
        // Replace HTML special characters with their corresponding entities
        return comment.replaceAll("&", "&amp;")
                .replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;")
                .replaceAll("\"", "&quot;")
                .replaceAll("'", "&#039;");
    }
}