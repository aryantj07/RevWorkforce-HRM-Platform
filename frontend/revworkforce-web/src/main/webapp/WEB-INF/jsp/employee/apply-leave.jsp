<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>

<head>

    <title>Apply Leave - RevWorkforce</title>

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

            <a href="${pageContext.request.contextPath}/employee/leave/apply"
               class="active">
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

        </nav>

    </aside>


    <main class="main-content">

        <header class="dashboard-header">

            <h1>Apply Leave</h1>

            <p>
                Submit a new leave request.
            </p>

        </header>


        <c:if test="${param.success != null}">

            <div class="success-message">
                ${param.success}
            </div>

        </c:if>


        <c:if test="${error != null}">

            <div class="error-message">
                ${error}
            </div>

        </c:if>


        <section class="card">

            <h2>Leave Request</h2>


            <form method="post"
                  action="${pageContext.request.contextPath}/employee/leave/apply">


                <div class="form-group">

                    <label for="leaveTypeId">
                        Leave Type
                    </label>

                    <select id="leaveTypeId"
                            name="leaveTypeId"
                            required>

                        <option value="">
                            Select leave type
                        </option>

                        <c:forEach var="leaveType"
                                   items="${leaveTypes}">

                            <option value="${leaveType.id}">
                                ${leaveType.name}
                            </option>

                        </c:forEach>

                    </select>

                </div>


                <div class="form-group">

                    <label for="startDate">
                        Start Date
                    </label>

                    <input type="date"
                           id="startDate"
                           name="startDate"
                           required>

                </div>


                <div class="form-group">

                    <label for="endDate">
                        End Date
                    </label>

                    <input type="date"
                           id="endDate"
                           name="endDate"
                           required>

                </div>


                <div class="form-group">

                    <label for="reason">
                        Reason
                    </label>

                    <textarea id="reason"
                              name="reason"
                              rows="5"
                              minlength="3"
                              maxlength="500"
                              required></textarea>

                </div>


                <button type="submit"
                        class="button">

                    Submit Leave Request

                </button>

            </form>

        </section>

    </main>

</div>

</body>

</html>