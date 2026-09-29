<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>RevWorkforce HRM</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f4f6f8;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .login-box {
            background: white;
            width: 350px;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.12);
        }

        h1 {
            text-align: center;
            margin-bottom: 5px;
        }

        h3 {
            text-align: center;
            font-weight: normal;
            color: #666;
        }

        input {
            width: 100%;
            padding: 10px;
            margin: 8px 0;
            box-sizing: border-box;
        }

        button {
            width: 100%;
            padding: 10px;
            margin-top: 10px;
            cursor: pointer;
        }

        .register-link {
            text-align: center;
            margin-top: 20px;
            color: #666;
        }

        .register-link a {
            color: #1976d2;
            text-decoration: none;
        }

        .register-link a:hover {
            text-decoration: underline;
        }

        .success-message {
            color: #2e7d32;
            background: #e8f5e9;
            padding: 10px;
            border-radius: 4px;
            margin-bottom: 15px;
        }

        .error-message {
            color: #b00020;
            background: #ffebee;
            padding: 10px;
            border-radius: 4px;
            margin-bottom: 15px;
        }
    </style>
</head>

<body>

<div class="login-box">

    <h1>RevWorkforce</h1>
    <h3>HR Management Platform</h3>

    <% if (request.getAttribute("success") != null) { %>
        <div class="success-message">
            <%= request.getAttribute("success") %>
        </div>
    <% } %>

    <% if (request.getAttribute("error") != null) { %>
        <div class="error-message">
            <%= request.getAttribute("error") %>
        </div>
    <% } %>

    <form method="post" action="${pageContext.request.contextPath}/login">

        <input type="text"
               name="username"
               placeholder="Username"
               required>

        <input type="password"
               name="password"
               placeholder="Password"
               required>

        <button type="submit">Login</button>

    </form>

    <div class="register-link">
        Don't have an account?
        <a href="${pageContext.request.contextPath}/register">
            Create an account
        </a>
    </div>

</div>

</body>
</html>