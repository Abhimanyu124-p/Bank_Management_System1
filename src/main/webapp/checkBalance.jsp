<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Check Account Balance</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CssFolder/checkBalance.css">
</head>
<body>

    <div class="balance-container">
        <div class="bank-brand">State Bank </div>
        <h2>Account Balance Statement</h2>
        
        <div class="account-card">
            <div class="acc-label">Account Holder</div>
            <div class="acc-number">${sessionScope.username}</div>
            
            <div class="acc-label">Available Balance</div>
            <div class="balance-display">
                ₹ ${String.format("%,.2f", sessionScope.balance)}
            </div>
        </div>

        <div class="info-row">
            <span class="info-label">Account Number:</span>
            <span class="info-value">${sessionScope.account_no}</span>
        </div>
        <div class="info-row">
            <span class="info-label">Branch Name:</span>
            <span class="info-value">${sessionScope.branch}</span>
        </div>
        <div class="info-row">
            <span class="info-label">IFSC Code:</span>
            <span class="info-value">${sessionScope.ifsc}</span>
        </div>

        <div class="btn-group">
            <a href="${pageContext.request.contextPath}/Dashboard.jsp" class="btn btn-secondary">
                Back to Dashboard
            </a>
            <button onclick="window.print()" class="btn btn-primary">
                Print Statement
            </button>
        </div>
    </div>

</body>
</html>