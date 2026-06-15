<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Withdraw Successfull</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/CssFolder/transaction.css">
</head>
<body>
<div class="container">
<div class="bnk-logo">State Bank</div>
<div class="main-msg">Transaction Successfull</div>
 <div class="current-balance-box">
     From Account: <strong>${sessionScope.account_no}</strong><br>
     Available Balance: <strong>₹ ${String.format("%,.2f", sessionScope.balance)}</strong>
  </div>
 <div class="msg">
   <strong>₹ ${String.format("%,.2f", sessionScope.amt)} Withdrawn Successfully From Account Number: ${sessionScope.account_no}</strong>
 </div>
 <a href="${pageContext.request.contextPath}\Dashboard.jsp" class="goBack">Go Back To Dashboard</a>
</div>
</body>
</html>