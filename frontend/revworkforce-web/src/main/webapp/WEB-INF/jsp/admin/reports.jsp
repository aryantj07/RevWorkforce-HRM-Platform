<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>

<head>

    <title>HR Reports - RevWorkforce</title>

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

            <a href="${pageContext.request.contextPath}/admin/dashboard">
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

            <a href="${pageContext.request.contextPath}/admin/reports"
               class="active">
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

            <h1>HR Reports</h1>

            <p>
                Workforce, leave and performance metrics.
            </p>

        </header>

        <c:if test="${error != null}">
            <div class="error-message">${error}</div>
        </c:if>

        <c:if test="${dashboard != null}">

            <section class="dashboard-cards">

                <div class="dashboard-card">
                    <h3>Total Employees</h3>
                    <p>${dashboard.totalEmployees}</p>
                </div>

                <div class="dashboard-card">
                    <h3>Active Employees</h3>
                    <p>${dashboard.activeEmployees}</p>
                </div>

                <div class="dashboard-card">
                    <h3>Inactive Employees</h3>
                    <p>${dashboard.inactiveEmployees}</p>
                </div>

            </section>

            <section class="card">

                <h2>Leave Summary</h2>

                <div class="dashboard-cards">

                    <div class="dashboard-card">
                        <h3>Total Applications</h3>
                        <p>
                            ${dashboard.leaveSummary.totalApplications}
                        </p>
                    </div>

                    <div class="dashboard-card">
                        <h3>Approved</h3>
                        <p>
                            ${dashboard.leaveSummary.approvedApplications}
                        </p>
                    </div>

                    <div class="dashboard-card">
                        <h3>Rejected</h3>
                        <p>
                            ${dashboard.leaveSummary.rejectedApplications}
                        </p>
                    </div>

                    <div class="dashboard-card">
                        <h3>Pending</h3>
                        <p>
                            ${dashboard.leaveSummary.pendingApplications}
                        </p>
                    </div>

                    <div class="dashboard-card">
                        <h3>Utilization</h3>
                        <p>
                            ${dashboard.leaveSummary.utilizationPercentage}%
                        </p>
                    </div>

                </div>

            </section>

            <section class="card">

                <h2>Performance Summary</h2>

                <div class="dashboard-cards">

                    <div class="dashboard-card">
                        <h3>Total Reviews</h3>
                        <p>
                            ${dashboard.performanceSummary.totalReviews}
                        </p>
                    </div>

                    <div class="dashboard-card">
                        <h3>Completed</h3>
                        <p>
                            ${dashboard.performanceSummary.completedReviews}
                        </p>
                    </div>

                    <div class="dashboard-card">
                        <h3>Pending</h3>
                        <p>
                            ${dashboard.performanceSummary.pendingReviews}
                        </p>
                    </div>

                    <div class="dashboard-card">
                        <h3>Average Rating</h3>
                        <p>
                            ${dashboard.performanceSummary.averageRating}
                            / 5
                        </p>
                    </div>

                </div>

            </section>

        </c:if>

        <section class="card">

            <h2>Employee Report</h2>

            <table class="data-table">

                <thead>

                <tr>

                    <th>ID</th>
                    <th>Employee</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Status</th>
                    <th>Date Joined</th>
                    <th>Department ID</th>
                    <th>Designation ID</th>

                </tr>

                </thead>

                <tbody>

                <c:forEach var="employee"
                           items="${employees}">

                    <tr>

                        <td>${employee.id}</td>

                        <td>
                            ${employee.firstName}
                            ${employee.lastName}
                        </td>

                        <td>${employee.email}</td>

                        <td>${employee.phoneNumber}</td>

                        <td>${employee.status}</td>

                        <td>${employee.dateOfJoining}</td>

                        <td>${employee.departmentId}</td>

                        <td>${employee.designationId}</td>

                    </tr>

                </c:forEach>

                </tbody>

            </table>

        </section>

    </main>

</div>

</body>
</html>