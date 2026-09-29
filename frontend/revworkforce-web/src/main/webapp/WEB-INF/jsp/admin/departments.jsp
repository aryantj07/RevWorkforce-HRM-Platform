<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Departments & Designations - RevWorkforce</title>
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

            <a href="${pageContext.request.contextPath}/admin/departments"
               class="active">
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
            <h1>Departments & Designations</h1>
            <p>Manage organizational departments and employee roles.</p>
        </header>

        <c:if test="${param.success != null}">
            <div class="success-message">${param.success}</div>
        </c:if>

        <c:if test="${error != null}">
            <div class="error-message">${error}</div>
        </c:if>

        <!-- DEPARTMENTS -->

        <section class="card">

            <h2>Create Department</h2>

            <form method="post"
                  action="${pageContext.request.contextPath}/admin/departments">

                <div class="form-grid">

                    <div class="form-group">
                        <label>Name</label>
                        <input type="text"
                               name="name"
                               required>
                    </div>

                    <div class="form-group">
                        <label>Description</label>
                        <input type="text"
                               name="description">
                    </div>

                </div>

                <div class="form-actions">
                    <button class="primary-button" type="submit">
                        Create Department
                    </button>
                </div>

            </form>

        </section>

        <section class="card">

            <h2>Departments</h2>

            <table class="data-table">

                <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Actions</th>
                </tr>
                </thead>

                <tbody>

                <c:forEach var="department"
                           items="${departments}">

                    <tr>

                        <td>${department.id}</td>

                        <td>
                            <form method="post"
                                  action="${pageContext.request.contextPath}/admin/departments/${department.id}/update">

                                <input type="text"
                                       name="name"
                                       value="${department.name}"
                                       required>
                        </td>

                        <td>
                                <input type="text"
                                       name="description"
                                       value="${department.description}">
                        </td>

                        <td>

                                <button type="submit"
                                        class="primary-button">
                                    Update
                                </button>

                            </form>

                            <form method="post"
                                  action="${pageContext.request.contextPath}/admin/departments/${department.id}/delete"
                                  style="display:inline;">

                                <button type="submit"
                                        class="danger-button"
                                        onclick="return confirm('Delete this department?');">
                                    Delete
                                </button>

                            </form>

                        </td>

                    </tr>

                </c:forEach>

                </tbody>

            </table>

        </section>

        <!-- DESIGNATIONS -->

        <section class="card">

            <h2>Create Designation</h2>

            <form method="post"
                  action="${pageContext.request.contextPath}/admin/designations">

                <div class="form-grid">

                    <div class="form-group">
                        <label>Name</label>
                        <input type="text"
                               name="name"
                               required>
                    </div>

                    <div class="form-group">
                        <label>Description</label>
                        <input type="text"
                               name="description">
                    </div>

                </div>

                <div class="form-actions">

                    <button class="primary-button" type="submit">
                        Create Designation
                    </button>

                </div>

            </form>

        </section>

        <section class="card">

            <h2>Designations</h2>

            <table class="data-table">

                <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Actions</th>
                </tr>
                </thead>

                <tbody>

                <c:forEach var="designation"
                           items="${designations}">

                    <tr>

                        <td>${designation.id}</td>

                        <td>
                            <form method="post"
                                  action="${pageContext.request.contextPath}/admin/designations/${designation.id}/update">

                                <input type="text"
                                       name="name"
                                       value="${designation.name}"
                                       required>
                        </td>

                        <td>

                                <input type="text"
                                       name="description"
                                       value="${designation.description}">

                        </td>

                        <td>

                                <button type="submit"
                                        class="primary-button">
                                    Update
                                </button>

                            </form>

                            <form method="post"
                                  action="${pageContext.request.contextPath}/admin/designations/${designation.id}/delete"
                                  style="display:inline;">

                                <button type="submit"
                                        class="danger-button"
                                        onclick="return confirm('Delete this designation?');">
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