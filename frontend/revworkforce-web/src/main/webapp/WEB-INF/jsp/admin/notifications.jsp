<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>

<head>

    <title>Notifications - RevWorkforce</title>

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

            <a href="${pageContext.request.contextPath}/admin/reports">
                HR Reports
            </a>

            <a href="${pageContext.request.contextPath}/admin/notifications"
               class="active">
                Notifications
            </a>

            <a href="${pageContext.request.contextPath}/logout">
                Logout
            </a>

        </nav>

    </aside>

    <main class="main-content">

        <header class="dashboard-header">

            <div>

                <h1>Notifications</h1>

                <p>
                    View notifications received by the administrator.
                </p>

            </div>

            <form method="post"
                  action="${pageContext.request.contextPath}/admin/notifications/read-all">

                <button type="submit"
                        class="primary-button">

                    Mark All as Read

                </button>

            </form>

        </header>

        <c:if test="${error != null}">
            <div class="error-message">${error}</div>
        </c:if>

        <section class="card">

            <table class="data-table">

                <thead>

                <tr>

                    <th>Type</th>
                    <th>Title</th>
                    <th>Message</th>
                    <th>Date</th>
                    <th>Status</th>
                    <th>Actions</th>

                </tr>

                </thead>

                <tbody>

                <c:forEach var="notification"
                           items="${notifications}">

                    <tr>

                        <td>${notification.type}</td>

                        <td>${notification.title}</td>

                        <td>${notification.message}</td>

                        <td>${notification.createdAt}</td>

                        <td>

                            <c:choose>

                                <c:when test="${notification.read}">
                                    READ
                                </c:when>

                                <c:otherwise>
                                    UNREAD
                                </c:otherwise>

                            </c:choose>

                        </td>

                        <td>

                            <c:if test="${!notification.read}">

                                <form method="post"
                                      action="${pageContext.request.contextPath}/admin/notifications/${notification.id}/read">

                                    <button type="submit"
                                            class="primary-button">
                                        Mark Read
                                    </button>

                                </form>

                            </c:if>

                            <form method="post"
                                  action="${pageContext.request.contextPath}/admin/notifications/${notification.id}/delete">

                                <button type="submit"
                                        class="danger-button"
                                        onclick="return confirm('Delete this notification?');">
                                    Delete
                                </button>

                            </form>

                        </td>

                    </tr>

                </c:forEach>

                </tbody>

            </table>

        </section>

    </main>

</div>

</body>
</html>