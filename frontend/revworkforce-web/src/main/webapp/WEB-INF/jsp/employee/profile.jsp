<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Profile - RevWorkforce</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

<div class="app">

    <aside class="sidebar">

        <h2>RevWorkforce</h2>

        <div class="role">Employee Portal</div>

        <a href="${pageContext.request.contextPath}/employee/dashboard">
            Dashboard
        </a>

        <a href="${pageContext.request.contextPath}/employee/profile"
           class="active">
            My Profile
        </a>

        <a href="${pageContext.request.contextPath}/employee/leave/apply">
            Apply Leave
        </a>

        <a href="${pageContext.request.contextPath}/employee/leave/history">
            Leave History
        </a>

        <a href="${pageContext.request.contextPath}/employee/leave/balance">
            Leave Balance
        </a>

        <a href="${pageContext.request.contextPath}/employee/performance">
            Performance
        </a>

        <a href="${pageContext.request.contextPath}/employee/notifications">
            Notifications
        </a>

        <a href="${pageContext.request.contextPath}/logout">
            Logout
        </a>

    </aside>

    <main class="main">

        <header class="header">

            <h1>My Profile</h1>

            <div class="header-user">
                ${profile.username}
            </div>

        </header>

        <section class="content">

            <h2>Personal Information</h2>

            <% if (request.getAttribute("error") != null) { %>
                <div class="error">
                    <%= request.getAttribute("error") %>
                </div>
            <% } %>

            <% if (request.getAttribute("profile") != null) { %>

            <div class="form-card">

                <div class="form-group">
                    <label>Username</label>
                    <input type="text"
                           value="${profile.username}"
                           readonly>
                </div>

                <div class="form-group">
                    <label>Email</label>
                    <input type="text"
                           value="${profile.email}"
                           readonly>
                </div>

                <div class="form-group">
                    <label>First Name</label>
                    <input type="text"
                           value="${profile.firstName}"
                           readonly>
                </div>

                <div class="form-group">
                    <label>Last Name</label>
                    <input type="text"
                           value="${profile.lastName}"
                           readonly>
                </div>

                <div class="form-group">
                    <label>Phone</label>
                    <input type="text"
                           value="${profile.phone}"
                           readonly>
                </div>

                <div class="form-group">
                    <label>Role</label>
                    <input type="text"
                           value="${profile.role}"
                           readonly>
                </div>

            </div>

            <% } %>

        </section>

    </main>

</div>

</body>
</html>