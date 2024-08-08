package com.librarymanagementsystem.repository;

import com.librarymanagementsystem.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
