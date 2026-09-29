<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>

<head>

    <title>Announcements - RevWorkforce</title>

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

            <a href="${pageContext.request.contextPath}/admin/announcements"
               class="active">
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

            <h1>Announcements</h1>

            <p>
                Create and manage company-wide HR announcements.
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

            <h2>Create Announcement</h2>

            <form method="post"
                  action="${pageContext.request.contextPath}/admin/announcements">

                <div class="form-grid">

                    <div class="form-group">

                        <label>Title</label>

                        <input type="text"
                               name="title"
                               maxlength="150"
                               required>

                    </div>

                    <div class="form-group">

                        <label>Status</label>

                        <select name="active">

                            <option value="true">
                                Active
                            </option>

                            <option value="false">
                                Inactive
                            </option>

                        </select>

                    </div>

                    <div class="form-group"
                         style="grid-column: 1 / -1;">

                        <label>Message</label>

                        <textarea name="message"
                                  rows="5"
                                  required></textarea>

                    </div>

                </div>

                <div class="form-actions">

                    <button class="primary-button"
                            type="submit">

                        Publish Announcement

                    </button>

                </div>

            </form>

        </section>

        <section class="card">

            <h2>Existing Announcements</h2>

            <table class="data-table">

                <thead>

                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Message</th>
                    <th>Created</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>

                </thead>

                <tbody>

                <c:forEach var="announcement"
                           items="${announcements}">

                    <tr>

                        <td>${announcement.id}</td>

                        <td>

                            <form method="post"
                                  action="${pageContext.request.contextPath}/admin/announcements/${announcement.id}/update">

                                <input type="text"
                                       name="title"
                                       value="${announcement.title}"
                                       required>

                        </td>

                        <td>

                                <textarea name="message"
                                          rows="3"
                                          required>${announcement.message}</textarea>

                        </td>

                        <td>
                            ${announcement.createdAt}
                        </td>

                        <td>

                                <select name="active">

                                    <option value="true"
                                            ${announcement.active ? 'selected' : ''}>
                                        Active
                                    </option>

                                    <option value="false"
                                            ${!announcement.active ? 'selected' : ''}>
                                        Inactive
                                    </option>

                                </select>

                        </td>

                        <td>

                                <button type="submit"
                                        class="primary-button">
                                    Update
                                </button>

                            </form>

                            <form method="post"
                                  action="${pageContext.request.contextPath}/admin/announcements/${announcement.id}/delete">

                                <button type="submit"
                                        class="danger-button"
                                        onclick="return confirm('Delete this announcement?');">
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