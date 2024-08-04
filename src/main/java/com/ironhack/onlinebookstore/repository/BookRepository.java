package com.ironhack.onlinebookstore.repository;

import com.ironhack.onlinebookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookRepository extends JpaRepository<Book, Long> {

}

