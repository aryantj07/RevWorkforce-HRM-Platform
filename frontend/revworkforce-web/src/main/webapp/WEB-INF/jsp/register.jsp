<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register - RevWorkforce</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f4f6f8;
            margin: 0;
            padding: 0;
        }

        .container {
            width: 420px;
            margin: 50px auto;
            background: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        h1 {
            text-align: center;
            margin-bottom: 10px;
        }

        .subtitle {
            text-align: center;
            color: #666;
            margin-bottom: 25px;
        }

        label {
            display: block;
            margin-top: 15px;
            margin-bottom: 5px;
            font-weight: bold;
        }

        input {
            width: 100%;
            padding: 10px;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        button {
            width: 100%;
            margin-top: 25px;
            padding: 11px;
            border: none;
            border-radius: 4px;
            background: #1976d2;
            color: white;
            font-size: 16px;
            cursor: pointer;
        }

        button:hover {
            background: #1565c0;
        }

        .message {
            padding: 10px;
            margin-bottom: 15px;
            border-radius: 4px;
            background: #ffecec;
            color: #b00020;
        }

        .login-link {
            text-align: center;
            margin-top: 20px;
        }

        .login-link a {
            color: #1976d2;
            text-decoration: none;
        }
    </style>
</head>

<body>

<div class="container">

    <h1>Create Account</h1>

    <div class="subtitle">
        Join RevWorkforce
    </div>

    <% if (request.getAttribute("error") != null) { %>
        <div class="message">
            <%= request.getAttribute("error") %>
        </div>
    <% } %>

    <%
        String error = (String) request.getAttribute("error");
    %>

    <form method="post" action="${pageContext.request.contextPath}/register">

        <label>Username</label>
        <input
                type="text"
                name="username"
                minlength="4"
                maxlength="100"
                required>

        <label>Email</label>
        <input
                type="email"
                name="email"
                required>

        <label>Password</label>
        <input
                type="password"
                name="password"
                minlength="8"
                required>

        <label>First Name</label>
        <input
                type="text"
                name="firstName"
                required>

        <label>Last Name</label>
        <input
                type="text"
                name="lastName"
                required>

        <label>Phone <span style="font-weight: normal;">(Optional)</span></label>
        <input
                type="text"
                name="phone">

        <button type="submit">
            Register
        </button>

    </form>

    <div class="login-link">
        Already have an account?
        <a href="${pageContext.request.contextPath}/login">
            Login here
        </a>
    </div>

</div>

</body>
</html>