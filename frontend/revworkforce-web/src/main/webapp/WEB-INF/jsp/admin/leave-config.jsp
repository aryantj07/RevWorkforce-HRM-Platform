<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>

<head>

    <title>Leave Configuration - RevWorkforce</title>

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

            <a href="${pageContext.request.contextPath}/admin/leave-config"
               class="active">
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
            <h1>Leave Configuration</h1>
            <p>Configure leave types, yearly quotas and company holidays.</p>
        </header>

        <c:if test="${param.success != null}">
            <div class="success-message">${param.success}</div>
        </c:if>

        <c:if test="${error != null}">
            <div class="error-message">${error}</div>
        </c:if>

        <!-- LEAVE TYPES -->

        <section class="card">

            <h2>Create Leave Type</h2>

            <form method="post"
                  action="${pageContext.request.contextPath}/admin/leave-config/types">

                <div class="form-grid">

                    <div class="form-group">
                        <label>Name</label>
                        <input type="text"
                               name="name"
                               required>
                    </div>

                    <div class="form-group">
                        <label>Code</label>
                        <input type="text"
                               name="code"
                               required>
                    </div>

                    <div class="form-group">
                        <label>Description</label>
                        <input type="text"
                               name="description">
                    </div>

                    <div class="form-group">

                        <label>
                            <input type="checkbox"
                                   name="paid"
                                   value="true"
                                   checked>
                            Paid Leave
                        </label>

                    </div>

                    <div class="form-group">

                        <label>
                            <input type="checkbox"
                                   name="active"
                                   value="true"
                                   checked>
                            Active
                        </label>

                    </div>

                </div>

                <div class="form-actions">

                    <button class="primary-button"
                            type="submit">
                        Create Leave Type
                    </button>

                </div>

            </form>

        </section>

        <section class="card">

            <h2>Leave Types</h2>

            <table class="data-table">

                <thead>

                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Code</th>
                    <th>Paid</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>

                </thead>

                <tbody>

                <c:forEach var="leaveType"
                           items="${leaveTypes}">

                    <tr>

                        <td>${leaveType.id}</td>
                        <td>${leaveType.name}</td>
                        <td>${leaveType.code}</td>
                        <td>${leaveType.paid ? 'Yes' : 'No'}</td>

                        <td>
                            ${leaveType.active ? 'ACTIVE' : 'INACTIVE'}
                        </td>

                        <td>

                            <form method="post"
                                  action="${pageContext.request.contextPath}/admin/leave-config/types/${leaveType.id}/status">

                                <input type="hidden"
                                       name="active"
                                       value="${!leaveType.active}">

                                <button type="submit"
                                        class="primary-button">

                                    ${leaveType.active ? 'Deactivate' : 'Activate'}

                                </button>

                            </form>

                        </td>

                    </tr>

                </c:forEach>

                </tbody>

            </table>

        </section>

        <!-- QUOTA -->

        <section class="card">

            <h2>Allocate Yearly Leave Quota</h2>

            <form method="post"
                  action="${pageContext.request.contextPath}/admin/leave-config/quotas">

                <div class="form-grid">

                    <div class="form-group">

                        <label>Leave Type</label>

                        <select name="leaveTypeId"
                                required>

                            <option value="">
                                Select Leave Type
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

                        <label>Year</label>

                        <input type="number"
                               name="year"
                               min="2000"
                               value="2026"
                               required>

                    </div>

                    <div class="form-group">

                        <label>Total Days</label>

                        <input type="number"
                               name="totalDays"
                               min="1"
                               required>

                    </div>

                </div>

                <div class="form-actions">

                    <button type="submit"
                            class="primary-button">
                        Allocate Quota
                    </button>

                </div>

            </form>

        </section>

        <!-- HOLIDAYS -->

        <section class="card">

            <h2>Add Company Holiday</h2>

            <form method="post"
                  action="${pageContext.request.contextPath}/admin/leave-config/holidays">

                <div class="form-grid">

                    <div class="form-group">

                        <label>Name</label>

                        <input type="text"
                               name="name"
                               required>

                    </div>

                    <div class="form-group">

                        <label>Date</label>

                        <input type="date"
                               name="holidayDate"
                               required>

                    </div>

                    <div class="form-group">

                        <label>Description</label>

                        <input type="text"
                               name="description">

                    </div>

                    <div class="form-group">

                        <label>

                            <input type="checkbox"
                                   name="recurring"
                                   value="true">

                            Recurring Holiday

                        </label>

                    </div>

                </div>

                <div class="form-actions">

                    <button type="submit"
                            class="primary-button">
                        Add Holiday
                    </button>

                </div>

            </form>

        </section>

        <section class="card">

            <h2>Company Holidays</h2>

            <table class="data-table">

                <thead>

                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Date</th>
                    <th>Description</th>
                    <th>Recurring</th>
                    <th>Action</th>
                </tr>

                </thead>

                <tbody>

                <c:forEach var="holiday"
                           items="${holidays}">

                    <tr>

                        <td>${holiday.id}</td>
                        <td>${holiday.name}</td>
                        <td>${holiday.holidayDate}</td>
                        <td>${holiday.description}</td>
                        <td>${holiday.recurring ? 'Yes' : 'No'}</td>

                        <td>

                            <form method="post"
                                  action="${pageContext.request.contextPath}/admin/leave-config/holidays/${holiday.id}/delete">

                                <button type="submit"
                                        class="danger-button"
                                        onclick="return confirm('Delete this holiday?');">
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