<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Fund Transfer - SBI</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CssFolder/transfer.css">
    <script>
        // Simple front-end validation check
        function validateTransfer() {
            var currentBalance = parseFloat(document.getElementById("currentBalance").value);
            var transferAmount = parseFloat(document.getElementById("amount").value);
            var recipientAcc = document.getElementById("recipientAcc").value;
            var senderAcc = "${sessionScope.account_no}";
            var pin=document.getElementById("pin").value;
            var validPin="${sessionScope.pin}";
            if (transferAmount <= 0) {
                alert("Transfer amount must be greater than zero.");
                return false;
            }
            if (transferAmount > currentBalance) {
                alert("Insufficient funds! Your available balance is ₹" + currentBalance);
                return false;
            }
            if(pin!==validPin)
        	{
        	alert("Please Enter Correct Pin! Entered Pin is Wrong")
        	return false;
        	}
            if (recipientAcc === senderAcc) {
                alert("Operation blocked: You cannot transfer money to your own account number.");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>

    <div class="transfer-container">
        <div class="bank-logo">State Bank of India</div>
        <h2>Instant Fund Transfer</h2>

        <input type="hidden" id="currentBalance" value="${sessionScope.balance}">

        <div class="current-balance-box">
            From Account: <strong>${sessionScope.account_no}</strong><br>
            Available Balance: <strong>₹ ${String.format("%,.2f", sessionScope.balance)}</strong>
        </div>

        <form action="${pageContext.request.contextPath}/TransferServlet" method="POST" onsubmit="return validateTransfer()">
            
            <div class="form-group">
                <label for="recipientAcc">Recipient Account Number</label>
                <input type="text" id="recipientAcc" name="recipient_account_no" placeholder="Enter recipient's account number" required autocomplete="off">
            </div>

            <div class="form-group">
                <label for="amount">Amount (₹)</label>
                <input type="number" id="amount" name="amount" step="0.01" placeholder="0.00" required>
            </div>

            <div class="form-group">
                <label for="pin">Transaction PIN (6 Digits)</label>
                <input type="password" id="pin" name="transaction_pin" placeholder="******" inputmode="numeric" pattern="\d{6}" maxlength="6" required autocomplete="new-password">
            </div>

            <button type="submit" class="btn-transfer">Confirm & Transfer</button>
        </form>

        <a href="${pageContext.request.contextPath}/Dashboard.jsp" class="btn-back">Cancel & Go Back</a>
    </div>

</body>
</html>