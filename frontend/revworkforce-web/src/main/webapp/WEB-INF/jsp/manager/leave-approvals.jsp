<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>

<head>

    <title>Leave Approvals - RevWorkforce</title>

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

            <a href="${pageContext.request.contextPath}/manager/leave-approvals"
               class="active">
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

            <h1>Leave Approvals</h1>

            <p>
                Review pending employee leave requests.
            </p>

        </header>


        <c:if test="${param.success != null}">
            <div class="success-message">
                ${param.success}
            </div>
        </c:if>


        <c:if test="${param.error != null}">
            <div class="error-message">
                ${param.error}
            </div>
        </c:if>


        <c:if test="${error != null}">
            <div class="error-message">
                ${error}
            </div>
        </c:if>


        <section class="card">

            <h2>Pending Leave Requests</h2>

            <c:choose>

                <c:when test="${empty leaves}">

                    <p>
                        There are no pending leave requests.
                    </p>

                </c:when>


                <c:otherwise>

                    <div class="table-container">

                        <table>

                            <thead>

                            <tr>

                                <th>Employee ID</th>
                                <th>Leave Type</th>
                                <th>Start Date</th>
                                <th>End Date</th>
                                <th>Days</th>
                                <th>Reason</th>
                                <th>Status</th>
                                <th>Actions</th>

                            </tr>

                            </thead>


                            <tbody>

                            <c:forEach var="leave"
                                       items="${leaves}">

                                <tr>

                                    <td>
                                        ${leave.employeeId}
                                    </td>

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

                                        <div style="display: flex;
                                                    flex-direction: column;
                                                    gap: 8px;
                                                    min-width: 180px;">

                                            <!-- Approve Leave -->

                                            <form method="post"
                                                  action="${pageContext.request.contextPath}/manager/leave-approvals/${leave.id}/approve">

                                                <input type="text"
                                                       name="comments"
                                                       placeholder="Optional comment"
                                                       style="width: 100%;
                                                              box-sizing: border-box;
                                                              padding: 6px;
                                                              margin-bottom: 5px;">

                                                <button type="submit"
                                                        style="background: #198754;
                                                               color: white;
                                                               border: none;
                                                               padding: 7px 12px;
                                                               border-radius: 4px;
                                                               cursor: pointer;
                                                               width: 100%;">
                                                    Approve
                                                </button>

                                            </form>


                                            <!-- Reject Leave -->

                                            <form method="post"
                                                  action="${pageContext.request.contextPath}/manager/leave-approvals/${leave.id}/reject">

                                                <input type="text"
                                                       name="comments"
                                                       placeholder="Reason for rejection"
                                                       style="width: 100%;
                                                              box-sizing: border-box;
                                                              padding: 6px;
                                                              margin-bottom: 5px;">

                                                <button type="submit"
                                                        style="background: #dc3545;
                                                               color: white;
                                                               border: none;
                                                               padding: 7px 12px;
                                                               border-radius: 4px;
                                                               cursor: pointer;
                                                               width: 100%;">
                                                    Reject
                                                </button>

                                            </form>

                                        </div>

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