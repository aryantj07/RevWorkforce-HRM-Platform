<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>

<head>

    <title>Performance Feedback - RevWorkforce</title>

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

            <a href="${pageContext.request.contextPath}/manager/performance"
               class="active">
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

            <h1>Performance Feedback</h1>

            <p>
                Create reviews and provide feedback on employee performance.
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

            <h2>Create Performance Review</h2>

            <form method="post"
                  action="${pageContext.request.contextPath}/manager/performance/create">

                <div class="form-group">

                    <label for="employeeId">
                        Employee
                    </label>

                    <select id="employeeId"
                            name="employeeId"
                            required>

                        <option value="">
                            Select Employee
                        </option>

                        <c:forEach var="employee"
                                   items="${employees}">

                            <option value="${employee.id}">
                                ${employee.id} -
                                ${employee.firstName}
                                ${employee.lastName}
                                (${employee.email})
                            </option>

                        </c:forEach>

                    </select>

                </div>


                <div class="form-group">

                    <label for="reviewPeriod">
                        Review Period
                    </label>

                    <input type="text"
                           id="reviewPeriod"
                           name="reviewPeriod"
                           placeholder="2026-Q4"
                           required>

                </div>


                <div class="form-group">

                    <label for="selfReview">
                        Initial Self Review
                    </label>

                    <textarea id="selfReview"
                              name="selfReview"
                              rows="4"
                              placeholder="Enter the initial self-review content"
                              required></textarea>

                </div>


                <button type="submit">
                    Create Review
                </button>

            </form>

        </section>


        <section class="card">

            <h2>Performance Reviews</h2>

            <c:choose>

                <c:when test="${empty reviews}">

                    <p>
                        No performance reviews are available yet.
                    </p>

                </c:when>

                <c:otherwise>

                    <div class="table-container">

                        <table>

                            <thead>

                            <tr>

                                <th>ID</th>
                                <th>Employee ID</th>
                                <th>Review Period</th>
                                <th>Self Review</th>
                                <th>Status</th>
                                <th>Rating</th>
                                <th>Manager Feedback</th>
                                <th>Action</th>

                            </tr>

                            </thead>


                            <tbody>

                            <c:forEach var="review"
                                       items="${reviews}">

                                <tr>

                                    <td>
                                        ${review.id}
                                    </td>

                                    <td>
                                        ${review.employeeId}
                                    </td>

                                    <td>
                                        ${review.reviewPeriod}
                                    </td>

                                    <td>
                                        ${review.selfReview}
                                    </td>

                                    <td>
                                        ${review.status}
                                    </td>

                                    <td>

                                        <c:choose>

                                            <c:when test="${review.rating != null}">
                                                ${review.rating}
                                            </c:when>

                                            <c:otherwise>
                                                -
                                            </c:otherwise>

                                        </c:choose>

                                    </td>

                                    <td>

                                        <c:choose>

                                            <c:when test="${review.managerFeedback != null}">
                                                ${review.managerFeedback}
                                            </c:when>

                                            <c:otherwise>
                                                -
                                            </c:otherwise>

                                        </c:choose>

                                    </td>

                                    <td>

                                        <c:choose>

                                            <c:when test="${review.status == 'SELF_REVIEW_SUBMITTED'}">

                                                <form method="post"
                                                      action="${pageContext.request.contextPath}/manager/performance/${review.id}/feedback">

                                                    <textarea name="feedback"
                                                              rows="3"
                                                              placeholder="Enter manager feedback"
                                                              required></textarea>

                                                    <br>

                                                    <button type="submit">
                                                        Submit Feedback
                                                    </button>

                                                </form>

                                            </c:when>

                                            <c:when test="${review.status == 'DRAFT'}">

                                                <span>
                                                    Waiting for employee self-review
                                                </span>

                                            </c:when>

                                            <c:when test="${review.status == 'FEEDBACK_SUBMITTED'}">

                                                <form method="post"
                                                      action="${pageContext.request.contextPath}/manager/performance/${review.id}/rating">

                                                    <label for="rating-${review.id}">
                                                        Rating
                                                    </label>

                                                    <select id="rating-${review.id}"
                                                            name="rating"
                                                            required
                                                            style="padding: 6px; margin: 5px 0;">

                                                        <option value="">
                                                            Select Rating
                                                        </option>

                                                        <option value="1">
                                                            1
                                                        </option>

                                                        <option value="2">
                                                            2
                                                        </option>

                                                        <option value="3">
                                                            3
                                                        </option>

                                                        <option value="4">
                                                            4
                                                        </option>

                                                        <option value="5">
                                                            5
                                                        </option>

                                                    </select>

                                                    <br>

                                                    <button type="submit">
                                                        Submit Rating
                                                    </button>

                                                </form>

                                            </c:when>

                                            <c:when test="${review.status == 'RATING_SUBMITTED'}">

                                                <form method="post"
                                                      action="${pageContext.request.contextPath}/manager/performance/${review.id}/complete">

                                                    <button type="submit">
                                                        Complete Review
                                                    </button>

                                                </form>

                                            </c:when>

                                            <c:when test="${review.status == 'COMPLETED'}">

                                                <span>
                                                    Review Completed
                                                </span>

                                            </c:when>

                                            <c:otherwise>

                                                <span>
                                                    No action available
                                                </span>

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