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
            <p>Manager Portal</p>
        </div>

        <nav>

            <a href="${pageContext.request.contextPath}/manager/dashboard">
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

            <a href="${pageContext.request.contextPath}/manager/notifications"
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

            <h1>Notifications</h1>

            <p>
                View your latest notifications and updates.
            </p>

        </header>


        <c:if test="${error != null}">
            <div class="error-message">
                ${error}
            </div>
        </c:if>


        <section class="card">

            <div style="display:flex; justify-content:space-between; align-items:center;">

                <h2>My Notifications</h2>

                <form method="post"
                      action="${pageContext.request.contextPath}/manager/notifications/read-all">

                    <button type="submit" class="button">
                        Mark All as Read
                    </button>

                </form>

            </div>


            <c:choose>

                <c:when test="${empty notifications}">

                    <p>No notifications available.</p>

                </c:when>


                <c:otherwise>

                    <div class="table-container">

                        <table>

                            <thead>

                            <tr>
                                <th>Type</th>
                                <th>Title</th>
                                <th>Message</th>
                                <th>Status</th>
                                <th>Created At</th>
                                <th>Actions</th>
                            </tr>

                            </thead>


                            <tbody>

                            <c:forEach var="notification"
                                       items="${notifications}">

                                <tr>

                                    <td>${notification.type}</td>

                                    <td>
                                        <strong>${notification.title}</strong>
                                    </td>

                                    <td>${notification.message}</td>

                                    <td>

                                        <c:choose>

                                            <c:when test="${notification.read}">
                                                Read
                                            </c:when>

                                            <c:otherwise>
                                                Unread
                                            </c:otherwise>

                                        </c:choose>

                                    </td>

                                    <td>${notification.createdAt}</td>

                                    <td>

                                        <c:if test="${not notification.read}">

                                            <form method="post"
                                                  action="${pageContext.request.contextPath}/manager/notifications/${notification.id}/read"
                                                  style="display:inline;">

                                                <button type="submit"
                                                        class="button">
                                                    Mark Read
                                                </button>

                                            </form>

                                        </c:if>


                                        <form method="post"
                                              action="${pageContext.request.contextPath}/manager/notifications/${notification.id}/delete"
                                              style="display:inline;">

                                            <button type="submit"
                                                    class="button">
                                                Delete
                                            </button>

                                        </form>

                                    </td>

                                </tr>

                            </c:forEach>

                            </tbody>

                        </table>

                    </div>

                </c:otherwise>

            </c:choose>

        </section>

    </main>

</div>

</body>

</html>