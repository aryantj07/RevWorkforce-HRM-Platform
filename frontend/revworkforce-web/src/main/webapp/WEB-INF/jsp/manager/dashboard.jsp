<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manager Dashboard - RevWorkforce</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

<div class="dashboard-layout">

    <aside class="sidebar">

        <div class="sidebar-header">
            <h2>RevWorkforce</h2>
            <p>Manager Portal</p>
        </div>

        <nav>

            <a href="${pageContext.request.contextPath}/manager/dashboard"
               class="active">
                Dashboard
            </a>

            <a href="${pageContext.request.contextPath}/manager/team">
                Team Employees
            </a>

            <a href="${pageContext.request.contextPath}/manager/leave-approvals">
                Leave Approvals
            </a>

            <a href="${pageContext.request.contextPath}/manager/performance">
                Performance Feedback
            </a>

            <a href="${pageContext.request.contextPath}/manager/notifications">
                Notifications
            </a>

            <a href="${pageContext.request.contextPath}/logout">
                Logout
            </a>

        </nav>

    </aside>


    <main class="main-content">

        <header class="dashboard-header">

            <h1>Manager Dashboard</h1>

            <p>
                Manage your team, leave requests and performance.
            </p>

        </header>


        <section class="cards">

            <div class="card">
                <h3>Team Employees</h3>
                <p>View employee information.</p>

                <a href="${pageContext.request.contextPath}/manager/team"
                   class="button">
                    View Team
                </a>
            </div>


            <div class="card">
                <h3>Leave Approvals</h3>
                <p>Review pending leave requests.</p>

                <a href="${pageContext.request.contextPath}/manager/leave-approvals"
                   class="button">
                    Manage Leaves
                </a>
            </div>


            <div class="card">
                <h3>Performance</h3>
                <p>Review employee performance.</p>

                <a href="${pageContext.request.contextPath}/manager/performance"
                   class="button">
                    Manage Performance
                </a>
            </div>


            <div class="card">
                <h3>Notifications</h3>
                <p>View team-related notifications.</p>

                <a href="${pageContext.request.contextPath}/manager/notifications"
                   class="button">
                    View Notifications
                </a>
            </div>

        </section>

    </main>

</div>

</body>
</html>