package com.assignment.library.service;

import com.assignment.library.entity.Author;
import com.assignment.library.entity.Book;
import com.assignment.library.repository.AuthorRepository;
import com.assignment.library.repository.BookRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Autowired
    public LibraryService(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @PostConstruct
    @Transactional
    public void populateDatabase() {
        if (authorRepository.count() == 0 && bookRepository.count() == 0) {
            // Create 10 Authors
            Author a1 = authorRepository.save(new Author("George Orwell", "English novelist and essayist"));
            Author a2 = authorRepository.save(new Author("J.K. Rowling", "British author and philanthropist"));
            Author a3 = authorRepository.save(new Author("J.R.R. Tolkien", "English writer, poet, and academic"));
            Author a4 = authorRepository.save(new Author("Agatha Christie", "English writer known for her 66 detective novels"));
            Author a5 = authorRepository.save(new Author("Stephen King", "American author of horror, supernatural fiction"));
            Author a6 = authorRepository.save(new Author("Jane Austen", "English novelist known for her six major novels"));
            Author a7 = authorRepository.save(new Author("Charles Dickens", "English writer and social critic"));
            Author a8 = authorRepository.save(new Author("Mark Twain", "American writer, humorist, entrepreneur"));
            Author a9 = authorRepository.save(new Author("Arthur Conan Doyle", "British writer and medical doctor"));
            Author a10 = authorRepository.save(new Author("H.G. Wells", "English writer, prolific in many genres"));

            // Create 10 Books
            bookRepository.save(new Book("1984", "Dystopian", 1949, a1));
            bookRepository.save(new Book("Animal Farm", "Political Satire", 1945, a1));
            bookRepository.save(new Book("Harry Potter and the Sorcerer's Stone", "Fantasy", 1997, a2));
            bookRepository.save(new Book("The Hobbit", "Fantasy", 1937, a3));
            bookRepository.save(new Book("Murder on the Orient Express", "Mystery", 1934, a4));
            bookRepository.save(new Book("The Shining", "Horror", 1977, a5));
            bookRepository.save(new Book("Pride and Prejudice", "Romance", 1813, a6));
            bookRepository.save(new Book("A Tale of Two Cities", "Historical Fiction", 1859, a7));
            bookRepository.save(new Book("The Adventures of Tom Sawyer", "Fiction", 1876, a8));
            bookRepository.save(new Book("The Time Machine", "Science Fiction", 1895, a10));
        }
    }

    // Book Operations
    public List<Book> getAllBooksWithAuthors() {
        return bookRepository.findAllBooksWithAuthors();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    @Transactional
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    // Author Operations
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    @Transactional
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }
}
