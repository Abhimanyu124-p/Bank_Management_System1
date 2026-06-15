<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Customer Support-State Bank</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CssFolder/support.css">
</head>
<body>

<div class="container">
    
    <div class="emergency-banner">
        <div>
            <strong>🚨 Emergency: Lost Card or Suspected Fraud?</strong> Call us immediately at 1-800-111-9999 or freeze your account.
        </div>
        <form action="${pageContext.request.contextPath}/FreezeAccountServlet" method="POST">
            <button type="submit" class="emergency-btn">Freeze Account Now</button>
        </form>
    </div>

    <h2>How can we help you, ${sessionScope.username}?</h2>
    <p>Logged in Account: <strong>${sessionScope.account_number}</strong></p>

    <div class="grid">
        <div class="card">
            <h3>Contact Channels</h3>
            <p><strong>📞 Toll-Free:</strong> 1-800-425-3800</p>
            <p><strong>✉️ Email Support:</strong> care@sbi2026.com</p>
            <p><strong>🏢 Head Branch:</strong> 10:00 AM – 4:00 PM (Mon-Sat)</p>
            <p><strong>🌐 IFSC Code:</strong> ${sessionScope.ifsc != null ? sessionScope.ifsc : "SBI2026"}</p>
        </div>

        <div class="card">
            <h3>Raise a Support Ticket</h3>
            <form action="${pageContext.request.contextPath}/SubmitTicketServlet" method="post">
                <input type="hidden" name="accountNo" value="${sessionScope.account_number}">
                <input type="hidden" name="email" value="${sessionScope.email}">

                <div class="form-group">
                    <label>Issue Category</label>
                    <input type="text" name="category" placeholder="e.g., Transaction Failure, Statement Issue" required>
                </div>
                <div class="form-group">
                    <label>Describe your issue</label>
                    <textarea name="description" rows="4" placeholder="Provide details here..." required></textarea>
                </div>
                <button type="submit" class="btn-submit">Submit Ticket</button>
            </form>
        </div>
    </div>

    <a href="${pageContext.request.contextPath}/Dashboard.jsp" class="btn-back">← Back to Dashboard</a>

</div>

</body>
</html>
