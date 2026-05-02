<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${book.id == null ? 'Add New Book' : 'Edit Book'}</title>
    <link rel="stylesheet" href="<c:url value='/css/style.css' />">
</head>
<body>
    <header>
        <div class="container">
            <h1>${book.id == null ? 'Add New Book' : 'Edit Book'}</h1>
        </div>
    </header>

    <div class="container">
        <c:if test="${not empty errorMessage}">
            <div class="message error">${errorMessage}</div>
        </c:if>

        <div class="form-container">
            <form action="<c:url value='${book.id == null ? \"/add-book\" : \"/update-book/\".concat(book.id)}' />" method="post">
                <div class="form-group">
                    <label for="title">Book Title</label>
                    <input type="text" id="title" name="title" value="${book.title}" required>
                </div>
                
                <div class="form-group">
                    <label for="genre">Genre</label>
                    <input type="text" id="genre" name="genre" value="${book.genre}" required>
                </div>
                
                <div class="form-group">
                    <label for="publicationYear">Publication Year</label>
                    <input type="number" id="publicationYear" name="publicationYear" value="${book.publicationYear}" required>
                </div>
                
                <div class="form-group">
                    <label for="author">Author</label>
                    <select id="author" name="author.id" required>
                        <option value="">-- Select Author --</option>
                        <c:forEach var="author" items="${authors}">
                            <option value="${author.id}" ${book.author != null && book.author.id == author.id ? 'selected' : ''}>${author.name}</option>
                        </c:forEach>
                    </select>
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
