<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Library Management</title>
    <link rel="stylesheet" href="<c:url value='/css/style.css' />">
</head>
<body>
    <header>
        <div class="container">
            <h1>Library Management System</h1>
        </div>
    </header>

    <div class="container">
        <div class="actions">
            <a href="<c:url value='/add-book' />" class="btn">Add New Book</a>
            <a href="<c:url value='/add-author' />" class="btn btn-secondary">Add New Author</a>
        </div>

        <c:if test="${not empty successMessage}">
            <div class="message success">${successMessage}</div>
        </c:if>
        
        <c:if test="${not empty errorMessage}">
            <div class="message error">${errorMessage}</div>
        </c:if>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Book Title</th>
                    <th>Genre</th>
                    <th>Publication Year</th>
                    <th>Author Name</th>
                    <th>Author Bio</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="book" items="${books}">
                    <tr>
                        <td>${book.id}</td>
                        <td>${book.title}</td>
                        <td>${book.genre}</td>
                        <td>${book.publicationYear}</td>
                        <td>${book.author.name}</td>
                        <td>${book.author.bio}</td>
                        <td>
                            <a href="<c:url value='/edit-book/${book.id}' />" class="btn" style="padding: 0.5rem 1rem; font-size: 0.8rem;">Edit</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty books}">
                    <tr>
                        <td colspan="7" style="text-align: center;">No books found in the library.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</body>
</html>
