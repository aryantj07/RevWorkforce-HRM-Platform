<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>

<head>

    <title>Team Employees - RevWorkforce</title>

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

            <a href="${pageContext.request.contextPath}/manager/team"
               class="active">
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

            <h1>Team Employees</h1>

            <p>View employee information.</p>

        </header>


        <c:if test="${error != null}">
            <div class="error-message">
                ${error}
            </div>
        </c:if>


        <section class="card">

            <h2>Employees</h2>

            <c:choose>

                <c:when test="${empty employees}">

                    <p>No employees found.</p>

                </c:when>

                <c:otherwise>

                    <div class="table-container">

                        <table>

                            <thead>

                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Phone</th>
                                <th>Status</th>
                                <th>Department</th>
                                <th>Designation</th>
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

                                    <td>${departmentNames[employee.departmentId]}</td>
                                    <td>${designationNames[employee.designationId]}</td>

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