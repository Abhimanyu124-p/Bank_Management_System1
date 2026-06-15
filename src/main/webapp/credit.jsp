<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Credit Funds-State Bank</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CssFolder/credit.css">
    <script>
        function validateDeposit() {
            var depositAmount = parseFloat(document.getElementById("amount").value);
            var pin=document.getElementById("pin").value;
            var validPin="${sessionScope.pin}";
            if (isNaN(depositAmount) || depositAmount <= 0) {
                alert("Please enter a valid deposit amount greater than zero.");
                return false;
            }
            if (depositAmount > 1000000) { // Setting a mock safety limit of 10 Lakhs per transit
                alert("For security reasons, single credit deposits cannot exceed ₹10,000,000.");
                return false;
            }
            if(pin!==validPin)
            {
            	alert("Please Enter Correct Pin! Entered Pin is Wrong")
            	return false;
            }
            return true;
        }
    </script>
</head>
<body>

    <div class="deposit-container">
        <div class="bank-logo">State Bank</div>
        <h2>Deposit / Credit Funds</h2>

        <div class="account-summary">
            Target Account: <strong>${sessionScope.account_no}</strong><br>
            Current Balance: <strong>₹ ${String.format("%,.2f", sessionScope.balance)}</strong>
        </div>

        <form action="${pageContext.request.contextPath}/DepositServlet" method="POST" onsubmit="return validateDeposit()">
            
            <input type="hidden" name="account_no" value="${sessionScope.account_no}">

            <div class="form-group">
                <label for="amount">Amount to Credit (₹)</label>
                <input type="number" id="amount" name="amount" step="0.01" placeholder="0.00" required autofocus>
            </div>

            <div class="form-group">
                <label for="pin">Confirm Account PIN (6 Digits)</label>
                <input type="password" id="pin" name="transaction_pin" placeholder="******" inputmode="numeric" pattern="\d{6}" maxlength="6" required autocomplete="current-password">
            </div>

            <button type="submit" class="btn-deposit">Confirm & Credit Amount</button>
        </form>

        <a href="${pageContext.request.contextPath}/Dashboard.jsp" class="btn-back">Cancel & Go Back</a>
    </div>

</body>
</html>
