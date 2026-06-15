<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Account Created Successfully</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CssFolder/accountCreated.css">
</head>
<body>

    <div class="success-container">
        <div class="success-icon">&#10004;</div>
        <h2>Account Created Successfully!</h2>
        
        <table class="details-table">
            <tr>
                <th>Account Number:</th>
                <td>${sessionScope.account_no}</td>
            </tr>
            <tr>
                <th>Branch Name:</th>
                <td>${sessionScope.branch}</td>
            </tr>
            <tr>
                <th>IFSC Code:</th>
                <td>${sessionScope.ifsc}</td>
            </tr>
            <tr>
                <th>Account Type:</th>
                <td>${sessionScope.type}</td>
            </tr>
            <tr>
                <th>Registered Phone:</th>
                <td>${sessionScope.phone_no}</td>
            </tr>
        </table>

        <a href="${pageContext.request.contextPath}/Dashboard.jsp" class="btn-dashboard">
            Go to Dashboard
        </a>
    </div>

</body>
</html>