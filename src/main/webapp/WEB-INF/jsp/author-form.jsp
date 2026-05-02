<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add New Author</title>
    <link rel="stylesheet" href="<c:url value='/css/style.css' />">
</head>
<body>
    <header>
        <div class="container">
            <h1>Add New Author</h1>
        </div>
    </header>

    <div class="container">
        <c:if test="${not empty errorMessage}">
            <div class="message error">${errorMessage}</div>
        </c:if>

        <div class="form-container">
            <form action="<c:url value='/add-author' />" method="post">
                <div class="form-group">
                    <label for="name">Author Name</label>
                    <input type="text" id="name" name="name" value="${author.name}" required>
                </div>
                
                <div class="form-group">
                    <label for="bio">Biography</label>
                    <textarea id="bio" name="bio" rows="4">${author.bio}</textarea>
                </div>
                
                <div class="actions" style="margin-top: 2rem;">
                    <button type="submit" class="btn">Save</button>
                    <a href="<c:url value='/' />" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
