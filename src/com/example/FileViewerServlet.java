package com.example;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class FileViewerServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileName = request.getParameter("file");
        File file = new File("/var/data/" + fileName); // base dir not enforced

        response.setContentType("text/plain");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                response.getWriter().println(line);
            }
        } catch (FileNotFoundException e) {
            response.getWriter().println("File not found.");
        }
    }
}
