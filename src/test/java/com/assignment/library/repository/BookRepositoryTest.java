package com.assignment.library.repository;

import com.assignment.library.entity.Author;
import com.assignment.library.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void testFindAllBooksWithAuthors() {
        // Given
        Author author = new Author("John Doe", "A new author");
        authorRepository.save(author);

        Book book = new Book("John's Book", "Sci-Fi", 2024, author);
        bookRepository.save(book);

        // When
        List<Book> books = bookRepository.findAllBooksWithAuthors();

        // Then
        assertFalse(books.isEmpty());
        Book foundBook = books.get(0);
        assertEquals("John's Book", foundBook.getTitle());
        assertNotNull(foundBook.getAuthor());
        assertEquals("John Doe", foundBook.getAuthor().getName());
    }
}
