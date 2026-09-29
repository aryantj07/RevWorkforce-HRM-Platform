<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Employee Dashboard - RevWorkforce</title>
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
        <a href="${pageContext.request.contextPath}/employee/profile">
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

            <h1>Employee Dashboard</h1>

            <div class="header-user">
                Welcome
            </div>

        </header>

        <section class="content">

            <h2>Overview</h2>

            <div class="cards">

                <div class="card">
                    <h3>Leave Balance</h3>
                    <div class="value">-</div>
                </div>

                <div class="card">
                    <h3>Pending Leaves</h3>
                    <div class="value">-</div>
                </div>

                <div class="card">
                    <h3>Performance Reviews</h3>
                    <div class="value">-</div>
                </div>

                <div class="card">
                    <h3>Unread Notifications</h3>
                    <div class="value">-</div>
                </div>

            </div>

        </section>

    </main>

</div>

</body>
</html>