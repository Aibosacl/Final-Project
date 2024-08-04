package com.ironhack.onlinebookstore.service;

import com.ironhack.onlinebookstore.DTOs.BookDTO;
import com.ironhack.onlinebookstore.model.Book;
import com.ironhack.onlinebookstore.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
class BookServiceImpl extends BookService {

    @Autowired
    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        super(bookRepository);
    }

    public List<BookDTO> findAll() {
        return bookRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<BookDTO> findById(Long id) {
        return Optional.ofNullable(bookRepository.findById(id).map(this::convertToDTO).orElse(null));
    }

    @Override
    public List<BookDTO> findByContainingTitle(String title) {
        return List.of();
    }

    public BookDTO save(BookDTO bookDTO) {
        Book book = convertToEntity(bookDTO);
        return convertToDTO(bookRepository.save(book));
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDTO> findByAuthor(String author) {
        return List.of();
    }

    @Override
    public List<BookDTO> findByGenre(String genre) {
        return List.of();
    }

    private BookDTO convertToDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setGenre(book.getGenre());
        bookDTO.setPrice(book.getPrice());
        return bookDTO;
    }

    private Book convertToEntity(BookDTO bookDTO) {
        Book book = new Book();
        book.setId(bookDTO.getId());
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setGenre(bookDTO.getGenre());
        book.setPrice(bookDTO.getPrice());
        return book;
    }
}
