<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>

<head>

    <title>Leave History - RevWorkforce</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">

</head>

<body>

<div class="dashboard-layout">

    <aside class="sidebar">

        <div class="sidebar-header">
            <h2>RevWorkforce</h2>
            <p>Employee Panel</p>
        </div>

        <nav>

            <a href="${pageContext.request.contextPath}/employee/dashboard">
                Dashboard
            </a>

            <a href="${pageContext.request.contextPath}/employee/profile">
                My Profile
            </a>

            <a href="${pageContext.request.contextPath}/employee/leave/apply">
                Apply Leave
            </a>

            <a href="${pageContext.request.contextPath}/employee/leave/history"
               class="active">
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

        </nav>

    </aside>


    <main class="main-content">

        <header class="dashboard-header">

            <h1>Leave History</h1>

            <p>
                View your submitted leave requests.
            </p>

        </header>


        <c:if test="${error != null}">

            <div class="error-message">
                ${error}
            </div>

        </c:if>


        <section class="card">

            <h2>My Leave Requests</h2>

            <c:choose>

                <c:when test="${empty leaves}">

                    <p>
                        You have no leave requests yet.
                    </p>

                </c:when>


                <c:otherwise>

                    <div class="table-container">

                        <table>

                            <thead>

                            <tr>

                                <th>Leave Type</th>
                                <th>Start Date</th>
                                <th>End Date</th>
                                <th>Days</th>
                                <th>Reason</th>
                                <th>Status</th>
                                <th>Applied At</th>
                                <th>Comments</th>

                            </tr>

                            </thead>


                            <tbody>

                            <c:forEach var="leave"
                                       items="${leaves}">

                                <tr>

                                    <td>
                                        ${leave.leaveType.name}
                                    </td>

                                    <td>
                                        ${leave.startDate}
                                    </td>

                                    <td>
                                        ${leave.endDate}
                                    </td>

                                    <td>
                                        ${leave.totalDays}
                                    </td>

                                    <td>
                                        ${leave.reason}
                                    </td>

                                    <td>
                                        ${leave.status}
                                    </td>

                                    <td>
                                        ${leave.appliedAt}
                                    </td>

                                    <td>
                                        <c:choose>

                                            <c:when test="${not empty leave.approverComments}">
                                                ${leave.approverComments}
                                            </c:when>

                                            <c:otherwise>
                                                -
                                            </c:otherwise>

                                        </c:choose>
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