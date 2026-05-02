package com.assignment.library.service;

import com.assignment.library.entity.Author;
import com.assignment.library.entity.Book;
import com.assignment.library.repository.AuthorRepository;
import com.assignment.library.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LibraryServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private LibraryService libraryService;

    private Author author;
    private Book book;

    @BeforeEach
    void setUp() {
        author = new Author("Test Author", "Bio");
        author.setId(1L);

        book = new Book("Test Title", "Fiction", 2023, author);
        book.setId(1L);
    }

    @Test
    void testGetAllBooksWithAuthors() {
        when(bookRepository.findAllBooksWithAuthors()).thenReturn(Arrays.asList(book));
        List<Book> books = libraryService.getAllBooksWithAuthors();
        assertEquals(1, books.size());
        assertEquals("Test Title", books.get(0).getTitle());
        verify(bookRepository, times(1)).findAllBooksWithAuthors();
    }

    @Test
    void testSaveBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        Book savedBook = libraryService.saveBook(book);
        assertNotNull(savedBook);
        assertEquals("Test Title", savedBook.getTitle());
    }

    @Test
    void testGetBookById() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Optional<Book> foundBook = libraryService.getBookById(1L);
        assertTrue(foundBook.isPresent());
        assertEquals(1L, foundBook.get().getId());
    }

    @Test
    void testSaveAuthor() {
        when(authorRepository.save(any(Author.class))).thenReturn(author);
        Author savedAuthor = libraryService.saveAuthor(author);
        assertNotNull(savedAuthor);
        assertEquals("Test Author", savedAuthor.getName());
    }

    @Test
    void testGetAllAuthors() {
        when(authorRepository.findAll()).thenReturn(Arrays.asList(author));
        List<Author> authors = libraryService.getAllAuthors();
        assertEquals(1, authors.size());
        assertEquals("Test Author", authors.get(0).getName());
    }
}
