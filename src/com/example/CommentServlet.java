package com.example;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class CommentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String comment = request.getParameter("comment");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // ❌ Reflected XSS Vulnerability
        out.println("<html><body>");
        out.println("<h2>Your Comment:</h2>");
        out.println("<p>" + comment + "</p>");  // unescaped output
        out.println("</body></html>");
    }
}
