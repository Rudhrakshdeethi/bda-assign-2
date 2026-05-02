# Library Management System Assignment Report

**Github URL:** `[Placeholder for your Github URL]`

## 1. Entity Relationship Design

The application manages two entities: `Author` and `Book`.

**Author Entity:**
- `id` (Primary Key, Auto-increment)
- `name` (String, Not Null)
- `bio` (String)

**Book Entity:**
- `id` (Primary Key, Auto-increment)
- `title` (String, Not Null)
- `genre` (String, Not Null)
- `publication_year` (Integer)
- `author_id` (Foreign Key, Not Null)

**Relationship:**
- A **One-to-Many** relationship exists between `Author` and `Book`. One author can write multiple books, but each book is associated with exactly one author.
- This is mapped using JPA annotations `@OneToMany(mappedBy = "author")` on the `Author` side and `@ManyToOne` / `@JoinColumn(name = "author_id")` on the `Book` side.

---

## 2. Implementation Details

### Database Population
On startup, a `@PostConstruct` method within the `LibraryService` checks if the database is empty. If so, it creates and saves 10 `Author` records and 10 corresponding `Book` records using the Spring Data JPA repositories. We used an H2 in-memory database to allow immediate running without local database setup.

### Create Operation
- **Add Author:** Handled by `/add-author` (GET and POST). A JSP form (`author-form.jsp`) accepts the author's details.
- **Add Book:** Handled by `/add-book` (GET and POST). The JSP form (`form.jsp`) provides text inputs for book details and a dropdown to select an existing author. 
- **Exception Handling:** In the POST mapping, `DataIntegrityViolationException` is caught to gracefully handle any constraint violations (e.g., submitting a book without an author) and returns a user-friendly error message via `RedirectAttributes`.

### Read Operation
- The root URL `/` maps to the `listBooks` method in the `LibraryController`.
- **Custom Query:** To prevent N+1 select problems and fulfill the assignment requirement, a custom query is used in `BookRepository`:
  ```java
  @Query("SELECT b FROM Book b JOIN FETCH b.author")
  List<Book> findAllBooksWithAuthors();
  ```
- This inner join fetches all books along with their associated authors in a single query. The data is bound to `list.jsp` and displayed in an HTML table using JSTL (`<c:forEach>`).

### Update Operation
- Handled by `/edit-book/{id}` (GET) and `/update-book/{id}` (POST).
- The same `form.jsp` used for creation is reused for editing, pre-filled with the existing book's details. Upon submission, the book's ID is retained, and `JpaRepository.save()` updates the existing record instead of creating a new one.

---

## 3. Challenges Faced

1. **JSP Integration in Spring Boot:** Modern Spring Boot prefers Thymeleaf over JSP, and running JSP views within an executable JAR can be problematic. 
   - *Solution:* Included `tomcat-embed-jasper` and JSTL dependencies in `pom.xml`, and configured the `spring.mvc.view.prefix` and `suffix` properties. The application was designed to be run from an IDE or packaged as a WAR if needed.

2. **N+1 Problem in Read Operation:** When fetching a list of books and displaying their author names, Hibernate might execute one query for the books and an additional query for *each* author.
   - *Solution:* Used the `JOIN FETCH` clause in a custom JPQL query. This satisfies the requirement for an "inner join" and optimizes database access by loading everything in a single SQL statement.

3. **Form Handling with Object Relationships:** Binding an author selected from a dropdown in the HTML form directly to the `Book` entity's `Author` object can be tricky.
   - *Solution:* Named the select input `author.id`. Spring's web data binder automatically instantiates an `Author` object with the provided ID, which JPA correctly translates into the `author_id` foreign key when saving.

---
*Note: To fulfill the submission requirement, export this Markdown document to a PDF and add screenshots of your running application under the Implementation Details sections.*
