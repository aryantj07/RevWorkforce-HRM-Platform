<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>

<head>

    <title>Leave Balance - RevWorkforce</title>

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

            <a href="${pageContext.request.contextPath}/employee/leave/history">
                Leave History
            </a>

            <a href="${pageContext.request.contextPath}/employee/leave/balance"
               class="active">
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

            <h1>Leave Balance</h1>

            <p>
                View your leave allocation and current balance.
            </p>

        </header>


        <c:if test="${error != null}">

            <div class="error-message">
                ${error}
            </div>

        </c:if>


        <section class="card">

            <h2>Current Year Leave Balance</h2>

            <c:choose>

                <c:when test="${empty balances}">

                    <p>
                        No leave balance information is available.
                    </p>

                </c:when>


                <c:otherwise>

                    <div class="table-container">

                        <table>

                            <thead>

                            <tr>

                                <th>Leave Type</th>
                                <th>Year</th>
                                <th>Total Days</th>
                                <th>Used Days</th>
                                <th>Pending Days</th>
                                <th>Remaining Days</th>

                            </tr>

                            </thead>


                            <tbody>

                            <c:forEach var="balance"
                                       items="${balances}">

                                <tr>

                                    <td>
                                        ${balance.leaveType.name}
                                    </td>

                                    <td>
                                        ${balance.year}
                                    </td>

                                    <td>
                                        ${balance.totalDays}
                                    </td>

                                    <td>
                                        ${balance.usedDays}
                                    </td>

                                    <td>
                                        ${balance.pendingDays}
                                    </td>

                                    <td>
                                        ${balance.remainingDays}
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