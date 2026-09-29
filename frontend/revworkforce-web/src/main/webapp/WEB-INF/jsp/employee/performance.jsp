<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>

<head>

    <title>Performance - RevWorkforce</title>

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

            <a href="${pageContext.request.contextPath}/employee/leave/balance">
                Leave Balance
            </a>

            <a href="${pageContext.request.contextPath}/employee/performance"
               class="active">
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

            <h1>Performance</h1>

            <p>
                View your performance reviews and manage your goals.
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


        <!-- PERFORMANCE REVIEWS -->

        <section class="card">

            <h2>Performance Reviews</h2>

            <c:choose>

                <c:when test="${empty reviews}">

                    <p>
                        No performance reviews are available yet.
                    </p>

                </c:when>

                <c:otherwise>

                    <c:forEach var="review"
                               items="${reviews}">

                        <div class="card">

                            <h3>
                                ${review.reviewPeriod}
                            </h3>

                            <p>
                                <strong>Status:</strong>
                                ${review.status}
                            </p>

                            <p>
                                <strong>Rating:</strong>

                                <c:choose>

                                    <c:when test="${review.rating != null}">
                                        ${review.rating}
                                    </c:when>

                                    <c:otherwise>
                                        Not rated
                                    </c:otherwise>

                                </c:choose>

                            </p>

                            <p>
                                <strong>Manager Feedback:</strong>
                            </p>

                            <p>
                                <c:choose>

                                    <c:when test="${not empty review.managerFeedback}">
                                        ${review.managerFeedback}
                                    </c:when>

                                    <c:otherwise>
                                        No manager feedback yet.
                                    </c:otherwise>

                                </c:choose>
                            </p>


                            <form method="post"
                                  action="${pageContext.request.contextPath}/employee/performance/review/${review.id}">

                                <div class="form-group">

                                    <label for="selfReview-${review.id}">
                                        Your Self Review
                                    </label>

                                    <textarea
                                            id="selfReview-${review.id}"
                                            name="selfReview"
                                            rows="5"
                                            minlength="1"
                                            required>${review.selfReview}</textarea>

                                </div>

                                <button type="submit"
                                        class="button">

                                    Submit Self Review

                                </button>

                            </form>

                        </div>

                    </c:forEach>

                </c:otherwise>

            </c:choose>

        </section>


        <!-- GOALS -->

        <section class="card">

            <h2>My Goals</h2>

            <c:choose>

                <c:when test="${empty goals}">

                    <p>
                        No goals have been created yet.
                    </p>

                </c:when>

                <c:otherwise>

                    <c:forEach var="goal"
                               items="${goals}">

                        <div class="card">

                            <h3>
                                ${goal.title}
                            </h3>

                            <p>
                                ${goal.description}
                            </p>

                            <form method="post"
                                  action="${pageContext.request.contextPath}/employee/performance/goals/${goal.id}">

                                <div class="form-group">

                                    <label>
                                        Title
                                    </label>

                                    <input type="text"
                                           name="title"
                                           value="${goal.title}"
                                           required>

                                </div>

                                <div class="form-group">

                                    <label>
                                        Description
                                    </label>

                                    <textarea name="description"
                                              rows="3"
                                              required>${goal.description}</textarea>

                                </div>

                                <div class="form-group">

                                    <label>
                                        Status
                                    </label>

                                    <select name="status"
                                            required>

                                        <option value="NOT_STARTED"
                                                ${goal.status == 'NOT_STARTED' ? 'selected' : ''}>
                                            Not Started
                                        </option>

                                        <option value="IN_PROGRESS"
                                                ${goal.status == 'IN_PROGRESS' ? 'selected' : ''}>
                                            In Progress
                                        </option>

                                        <option value="COMPLETED"
                                                ${goal.status == 'COMPLETED' ? 'selected' : ''}>
                                            Completed
                                        </option>

                                    </select>

                                </div>

                                <button type="submit"
                                        class="button">

                                    Update Goal

                                </button>

                            </form>

                        </div>

                    </c:forEach>

                </c:otherwise>

            </c:choose>


            <h3>Create New Goal</h3>

            <form method="post"
                  action="${pageContext.request.contextPath}/employee/performance/goals">

                <div class="form-group">

                    <label for="title">
                        Goal Title
                    </label>

                    <input type="text"
                           id="title"
                           name="title"
                           required>

                </div>


                <div class="form-group">

                    <label for="description">
                        Description
                    </label>

                    <textarea id="description"
                              name="description"
                              rows="4"
                              required></textarea>

                </div>


                <button type="submit"
                        class="button">

                    Create Goal

                </button>

            </form>

        </section>

    </main>

</div>

</body>

</html>