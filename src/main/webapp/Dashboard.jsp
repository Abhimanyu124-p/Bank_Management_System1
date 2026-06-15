<%
    // Force browser to fetch fresh data from the server every time
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
    response.setDateHeader("Expires", 0); // Proxies
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat, java.util.Date" %>
<%
    Date now = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy ");
    String formattedDate = sdf.format(now);
%>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Bank Management Dashboard</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/CssFolder/Dashboard.css">
  <script src="https://unpkg.com/feather-icons"></script>
  </head>
<body>
  <!-- Sidebar -->
  <div class="sidebar" id="sidebar">
    <h2>Bank Panel</h2>
    <a href="Dashboard.jsp">Home</a>
    <a href="CreateAccount.html">Open Account</a>
    <a href="Support.jsp">Customer Support</a>
    <a href="debit.jsp">Withdraw</a>
    <a href="credit.jsp">Deposit</a>
    <a href="transfer.jsp">Transfer</a>
    <a href="${pageContext.request.contextPath}/ViewTransactions">View Transactions</a>
    <a href="employee.jsp">Employee</a>
    <c:if test="${not empty sessionScope.username}"><a href="logoutservlet">Logout</a></c:if>
    <c:if test="${empty sessionScope.username}"><a href="Login.jsp">Login</a></c:if>
  </div>
  <!-- Main content -->
  <div class="main" id="main">
    <div class="topbar">
      <h1>Welcome to State Bank</h1>
       <span>Branch:${sessionScope.branch} | Date: <%=formattedDate%></span>
      <button class="toggle-btn" onclick="toggleSidebar()">☰ Menu</button>
    </div>

    <div class="content">
      <div class="welcome">
        <h2>Welcome, <%= session.getAttribute("username") %>!</h2>
        <p>Your current balance: ₹ <%= session.getAttribute("balance") %></p>
      </div>
      <!-- Responsive banner image -->
      <div class="banner">
      <img src="Dashboard.png" alt="Welcome to State Bank" class="banner-img">
      </div>
      <div class="container">
      <div class="container_head">
      <h3>IFSC:<button onclick="" class="ifscbtn"><%=session.getAttribute("ifsc") %></button></h3>
      <h3>Branch Code:<button onclick="" class="ifscbtn"><%= session.getAttribute("branch") %></button></h3>
      </div>
      <!-- Action cards -->
      <div class="actions">
      <c:if test="${not empty sessionScope.username}">
  <c:if test="${not empty sessionScope.account_no }">
  <div class="card">
  <i data-feather="credit-card" class="card-icon"></i>
  <h2>Credit Amount</h2>
  <button onclick="window.location.href='credit.jsp'">Credit</button>
</div>

<div class="card">
  <i data-feather="shopping-bag" class="card-icon"></i>
  <h2>Debit Amount</h2>
  <button onclick="window.location.href='debit.jsp'">Debit</button>
</div>

<div class="card">
  <i data-feather="repeat" class="card-icon"></i>
  <h2>Transfer Amount</h2>
  <button onclick="window.location.href='transfer.jsp'">Transfer</button>
</div>

<div class="card">
  <i data-feather="dollar-sign" class="card-icon"></i>
  <h2>Check Balance</h2>
  <button onclick="window.location.href='checkBalance.jsp'">Check</button>
</div>
</c:if>
<c:if test="${empty sessionScope.account_no }">
<div class="card">
  <i data-feather="user-plus" class="card-icon"></i>
  <h2>Create New Account</h2>
  <button onclick="window.location.href='CreateAccount.html'">Create</button>
</div>
</c:if>

<div class="card">
  <i data-feather="log-out" class="card-icon"></i>
  <h2>Logout</h2>
  <button onclick="window.location.href='logoutservlet'">Logout</button>
</div>
</c:if>
<c:if test="${empty sessionScope.username }">
<div class="card">
  <i data-feather="log-in" class="card-icon"></i>
  <h2>Login</h2>
  <button onclick="window.location.href='Login.jsp';">Login</button>
</div>
        </c:if>
      </div>
    </div>
  </div>
  <footer class="footer">
  <p>&copy; 2026 State Bank. All Rights Reserved.</p>
  <p>Powered by Bank Management System Project</p>
  </footer>
  </div>

  <!-- JavaScript -->
  <script>
    feather.replace();
    function toggleSidebar() {
      const sidebar = document.getElementById("sidebar");
      const main = document.getElementById("main");
      sidebar.classList.toggle("hidden");
      main.classList.toggle("expanded");
    }
  </script>
</body>
</html>