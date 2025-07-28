# VulnerableJavaApp

This is an intentionally insecure Java web application built for educational purposes, including ethical hacking, penetration testing, and security training.

## 🔥 Vulnerabilities

1. **SQL Injection**  
   - File: `LoginServlet.java`
   - Vulnerable login query allows injection via username/password.

2. **Cross-Site Scripting (XSS)**  
   - File: `CommentServlet.java`
   - User input is printed directly into HTML without escaping.

3. **Hardcoded Credentials**  
   - File: `LoginServlet.java`
   - Database and session keys are hardcoded.

4. **Insecure Deserialization**  
   - File: `LoginServlet.java`
   - User-supplied serialized Java objects are deserialized blindly.

5. **Directory Traversal**  
   - File: `FileViewerServlet.java`
   - No checks on file path, allowing access to arbitrary files.

## ⚠️ Disclaimer

For ethical use only. Run in isolated environments. Do **not** deploy to production systems.
