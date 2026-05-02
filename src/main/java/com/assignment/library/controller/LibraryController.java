package com.assignment.library.controller;

import com.assignment.library.entity.Author;
import com.assignment.library.entity.Book;
import com.assignment.library.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class LibraryController {

    private final LibraryService libraryService;

    @Autowired
    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    // --- Read Operation ---
    @GetMapping
    public String listBooks(Model model) {
        // Fetch data from service layer
        model.addAttribute("books", libraryService.getAllBooksWithAuthors());
        return "list"; // Binds to /WEB-INF/jsp/list.jsp
    }

    // --- Create Operation ---
    @GetMapping("/add-book")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", libraryService.getAllAuthors());
        return "form"; // Binds to /WEB-INF/jsp/form.jsp
    }

    @PostMapping("/add-book")
    public String saveNewBook(@ModelAttribute("book") Book book, RedirectAttributes redirectAttributes) {
        try {
            libraryService.saveBook(book);
            redirectAttributes.addFlashAttribute("successMessage", "Book saved successfully!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Integrity violation: Please ensure all required fields and relationships are valid.");
            return "redirect:/add-book";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while saving the book.");
            return "redirect:/add-book";
        }
        return "redirect:/";
    }

    @GetMapping("/add-author")
    public String showAddAuthorForm(Model model) {
        model.addAttribute("author", new Author());
        return "author-form";
    }

    @PostMapping("/add-author")
    public String saveNewAuthor(@ModelAttribute("author") Author author, RedirectAttributes redirectAttributes) {
        try {
            libraryService.saveAuthor(author);
            redirectAttributes.addFlashAttribute("successMessage", "Author saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while saving the author.");
            return "redirect:/add-author";
        }
        return "redirect:/";
    }

    // --- Update Operation ---
    @GetMapping("/edit-book/{id}")
    public String showEditBookForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Book book = libraryService.getBookById(id).orElse(null);
        if (book == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Book not found.");
            return "redirect:/";
        }
        model.addAttribute("book", book);
        model.addAttribute("authors", libraryService.getAllAuthors());
        return "form";
    }

    @PostMapping("/update-book/{id}")
    public String updateBook(@PathVariable("id") Long id, @ModelAttribute("book") Book book, RedirectAttributes redirectAttributes) {
        try {
            book.setId(id); // Ensure the correct ID is used
            libraryService.saveBook(book);
            redirectAttributes.addFlashAttribute("successMessage", "Book updated successfully!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Integrity violation: Could not update the book.");
            return "redirect:/edit-book/" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while updating the book.");
            return "redirect:/edit-book/" + id;
        }
        return "redirect:/";
    }

    // Exception Handler for any unhandled integrity violations
    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDataIntegrityViolationException(DataIntegrityViolationException ex, Model model) {
        model.addAttribute("errorMessage", "A database constraint was violated.");
        model.addAttribute("books", libraryService.getAllBooksWithAuthors());
        return "list";
    }
}
