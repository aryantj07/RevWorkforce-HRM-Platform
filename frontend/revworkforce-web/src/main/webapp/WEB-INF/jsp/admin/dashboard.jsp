<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>

<head>

    <title>Admin Dashboard - RevWorkforce</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">

</head>

<body>

<div class="dashboard-layout">

    <aside class="sidebar">

        <div class="sidebar-header">
            <h2>RevWorkforce</h2>
            <p>Admin Panel</p>
        </div>

        <nav>

            <a href="${pageContext.request.contextPath}/admin/dashboard"
               class="active">
                Dashboard
            </a>

            <a href="${pageContext.request.contextPath}/admin/employees">
                Employee Management
            </a>

            <a href="${pageContext.request.contextPath}/admin/departments">
                Departments & Designations
            </a>

            <a href="${pageContext.request.contextPath}/admin/leave-config">
                Leave Configuration
            </a>

            <a href="${pageContext.request.contextPath}/admin/announcements">
                Announcements
            </a>

            <a href="${pageContext.request.contextPath}/admin/reports">
                HR Reports
            </a>

            <a href="${pageContext.request.contextPath}/admin/notifications">
                Notifications
            </a>

            <a href="${pageContext.request.contextPath}/logout">
                Logout
            </a>

        </nav>

    </aside>


    <main class="main-content">

        <header class="dashboard-header">

            <h1>Admin Dashboard</h1>

            <p>
                Manage employees, HR configuration and reports.
            </p>

        </header>


        <section class="dashboard-cards">

            <div class="dashboard-card">

                <h3>Employee Management</h3>

                <p>
                    Create and manage employee records,
                    onboarding and employee information.
                </p>

                <a href="${pageContext.request.contextPath}/admin/employees">
                    Manage Employees →
                </a>

            </div>


            <div class="dashboard-card">

                <h3>Departments & Designations</h3>

                <p>
                    Manage company departments and employee designations.
                </p>

                <a href="${pageContext.request.contextPath}/admin/departments">
                    Manage →
                </a>

            </div>


            <div class="dashboard-card">

                <h3>Leave Configuration</h3>

                <p>
                    Configure leave types, quotas and company leave settings.
                </p>

                <a href="${pageContext.request.contextPath}/admin/leave-config">
                    Manage →
                </a>

            </div>


            <div class="dashboard-card">

                <h3>Announcements</h3>

                <p>
                    Create and publish announcements for employees.
                </p>

                <a href="${pageContext.request.contextPath}/admin/announcements">
                    Manage →
                </a>

            </div>


            <div class="dashboard-card">

                <h3>HR Reports</h3>

                <p>
                    View employee, leave and performance reports
                    and key HR metrics.
                </p>

                <a href="${pageContext.request.contextPath}/admin/reports">
                    View Reports →
                </a>

            </div>


            <div class="dashboard-card">

                <h3>Notifications</h3>

                <p>
                    View system notifications and employee-related updates.
                </p>

                <a href="${pageContext.request.contextPath}/admin/notifications">
                    View Notifications →
                </a>

            </div>

        </section>

    </main>

</div>

</body>

</html>