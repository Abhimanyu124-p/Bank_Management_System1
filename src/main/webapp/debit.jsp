<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Deposit / Credit Funds - SBI</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CssFolder/debit.css">
    <script>
        function validateDeposit() {
            var withdrawAmount = parseFloat(document.getElementById("amount").value);
            var balance=parseFloat("${sessionScope.balance}");
            var pin=document.getElementById("pin").value;
            var validPin="${sessionScope.pin}";
            if (isNaN(withdrawAmount) || withdrawAmount <= 0) {
                alert("Please enter a valid deposit amount greater than zero.");
                return false;
            }
            if (withdrawAmount>balance) 
            {
                alert("Insufficient funds! Your available balance is ₹" +balance);
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
        <h2>Withdraw / Debit Funds</h2>

        <div class="account-summary">
            Target Account: <strong>${sessionScope.account_no}</strong><br>
            Current Balance: <strong>₹ ${String.format("%,.2f", sessionScope.balance)}</strong>
        </div>

        <form action="${pageContext.request.contextPath}/WithdrawServlet" method="POST" onsubmit="return validateDeposit()">
            
            <input type="hidden" name="account_no" value="${sessionScope.account_no}">

            <div class="form-group">
                <label for="amount">Amount to Withdraw (₹)</label>
                <input type="number" id="amount" name="amount" step="0.01" placeholder="0.00" required autofocus>
            </div>

            <div class="form-group">
                <label for="pin">Confirm Account PIN (6 Digits)</label>
                <input type="password" id="pin" name="transaction_pin" placeholder="******" inputmode="numeric" pattern="\d{6}" maxlength="6" required autocomplete="current-password">
            </div>

            <button type="submit" class="btn-deposit">Confirm & Withdraw Amount</button>
        </form>

        <a href="${pageContext.request.contextPath}/Dashboard.jsp" class="btn-back">Cancel & Go Back</a>
    </div>

</body>
</html>