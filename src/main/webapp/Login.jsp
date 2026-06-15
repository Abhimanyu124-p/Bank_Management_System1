<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/CssFolder/Login.css">
</head>
<body>
  <div class="login-container">
    <h1>Bank Management Login</h1>
    <form action="/LoginServlet" method="POST">
      <div class="form-group">
        <label for="username">Email</label>
        <input type="email" id="useremail" name="useremail" placeholder="xyz@gmail.com" required>
      </div>
      <div class="form-group">
        <label for="password">Password</label>
        <input type="password" id="password" name="password" placeholder="*******" required>
      </div>
      <button type="submit" class="btn">Login</button>
    </form>
    <div class="extra-links">
      <a href="register.html">Register</a> | 
      <a href="#">Forgot Password?</a>
    </div>
  </div>
</body>
</html>
