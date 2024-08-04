package com.ironhack.onlinebookstore.service;

import com.ironhack.onlinebookstore.DTOs.BookDTO;
import com.ironhack.onlinebookstore.model.Book;
import com.ironhack.onlinebookstore.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public abstract class BookService {
    private final BookRepository bookRepository;

    public abstract List<BookDTO> findByContainingTitle(String title);

    public BookDTO save(BookDTO bookDTO) {
        Book book = convertToEntity(bookDTO);
        Book savedBook = bookRepository.save(book);
        return convertToDTO(savedBook);
    }

    public List<BookDTO> findAll() {
        return bookRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<BookDTO> findById(Long id) {
        return bookRepository.findById(id).map(this::convertToDTO);
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    public abstract List<BookDTO> findByAuthor(String author);

    public abstract List<BookDTO> findByGenre(String genre);

    private BookDTO convertToDTO(Book book) {
        return new BookDTO(book.getId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.getPrice());
    }

    private Book convertToEntity(BookDTO bookDTO) {
        return new Book(bookDTO.getId(), bookDTO.getTitle(), bookDTO.getAuthor(), bookDTO.getGenre(), bookDTO.getPrice());
    }
}