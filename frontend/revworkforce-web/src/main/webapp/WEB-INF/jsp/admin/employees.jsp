<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>

<head>

    <title>Employee Management - RevWorkforce</title>

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

            <a href="${pageContext.request.contextPath}/admin/employees"
               class="active">
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

            <div>
                <h1>Employee Management</h1>
                <p>Create and manage employee records.</p>
            </div>

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

            <h2>Create Employee</h2>

            <form method="post"
                  action="${pageContext.request.contextPath}/admin/employees">

                <div class="form-grid">

                    <div class="form-group">

                        <label for="userId">
                            User
                        </label>

                        <select id="userId"
                                name="userId"
                                required
                                onchange="fillUserDetails(this)">

                            <option value="">Select User</option>

                            <c:forEach var="user" items="${users}">

                                <c:if test="${user.active && user.role == 'EMPLOYEE'}">

                                    <option value="${user.id}"
                                            data-first-name="${user.firstName}"
                                            data-last-name="${user.lastName}"
                                            data-email="${user.email}"
                                            data-phone="${user.phone}">
                                        ${user.username} - ${user.firstName} ${user.lastName}
                                    </option>

                                </c:if>

                            </c:forEach>

                        </select>

                    </div>


                    <div class="form-group">

                        <label for="firstName">
                            First Name
                        </label>

                        <input type="text"
                               id="firstName"
                               name="firstName"
                               readonly
                               required>

                    </div>


                    <div class="form-group">

                        <label for="lastName">
                            Last Name
                        </label>

                        <input type="text"
                               id="lastName"
                               name="lastName"
                               readonly
                               required>

                    </div>


                    <div class="form-group">

                        <label for="email">
                            Email
                        </label>

                        <input type="email"
                               id="email"
                               name="email"
                               readonly
                               required>

                    </div>


                    <div class="form-group">

                        <label for="phoneNumber">
                            Phone Number
                        </label>

                        <input type="text"
                               id="phoneNumber"
                               name="phoneNumber"
                               readonly>

                    </div>


                    <div class="form-group">

                        <label for="status">
                            Status
                        </label>

                        <select id="status"
                                name="status"
                                required>

                            <option value="ACTIVE">
                                ACTIVE
                            </option>

                            <option value="INACTIVE">
                                INACTIVE
                            </option>

                            <option value="OFFBOARDED">
                                OFFBOARDED
                            </option>

                        </select>

                    </div>


                    <div class="form-group">

                        <label for="dateOfJoining">
                            Date of Joining
                        </label>

                        <input type="date"
                               id="dateOfJoining"
                               name="dateOfJoining"
                               required>

                    </div>


                    <div class="form-group">

                        <label for="departmentId">
                            Department
                        </label>

                        <select id="departmentId"
                                name="departmentId"
                                required>

                            <option value="">
                                Select Department
                            </option>

                            <c:forEach var="department"
                                       items="${departments}">

                                <option value="${department.id}">
                                    ${department.name}
                                </option>

                            </c:forEach>

                        </select>

                    </div>


                    <div class="form-group">

                        <label for="designationId">
                            Designation
                        </label>

                        <select id="designationId"
                                name="designationId"
                                required>

                            <option value="">
                                Select Designation
                            </option>

                            <c:forEach var="designation"
                                       items="${designations}">

                                <option value="${designation.id}">
                                    ${designation.name}
                                </option>

                            </c:forEach>

                        </select>

                    </div>


                    <div class="form-group">

                        <label for="address">
                            Address
                        </label>

                        <input type="text"
                               id="address"
                               name="address">

                    </div>

                </div>


                <div class="form-actions">

                    <button type="submit"
                            class="primary-button">

                        Create Employee

                    </button>

                </div>

            </form>

        </section>

    </main>

</div>
<script>

function fillUserDetails(select) {

    const option = select.options[select.selectedIndex];

    console.log("Selected user:", option.text);
    console.log("First name:", option.getAttribute("data-first-name"));
    console.log("Last name:", option.getAttribute("data-last-name"));
    console.log("Email:", option.getAttribute("data-email"));
    console.log("Phone:", option.getAttribute("data-phone"));

    if (!option || !option.value) {
        clearUserDetails();
        return;
    }

    document.getElementById("firstName").value =
        option.getAttribute("data-first-name") || "";

    document.getElementById("lastName").value =
        option.getAttribute("data-last-name") || "";

    document.getElementById("email").value =
        option.getAttribute("data-email") || "";

    document.getElementById("phoneNumber").value =
        option.getAttribute("data-phone") || "";
}


function clearUserDetails() {

    document.getElementById("firstName").value = "";
    document.getElementById("lastName").value = "";
    document.getElementById("email").value = "";
    document.getElementById("phoneNumber").value = "";
}


window.addEventListener("load", function () {

    const userSelect = document.getElementById("userId");

    if (userSelect && userSelect.value) {
        fillUserDetails(userSelect);
    }

});

</script>

</body>

</html>