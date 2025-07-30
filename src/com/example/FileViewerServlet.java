package com.example;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileViewerServlet extends HttpServlet {
    private static final String BASE_DIR = "/var/data/"; // Base directory for files

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileName = request.getParameter("file");
        if (fileName == null || fileName.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "File name parameter is missing.");
            return;
        }

        Path filePath = Paths.get(BASE_DIR, fileName);
        if (!filePath.normalize().startsWith(BASE_DIR)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Path traversal attempt detected.");
            return;
        }

        File file = filePath.toFile();
        if (!file.exists() || !file.isFile()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found.");
            return;
        }

        response.setContentType("text/plain");
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = bis.read(buffer)) != -1) {
                response.getOutputStream().write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error reading file.");
        }
    }
}