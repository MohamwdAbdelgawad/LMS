package com.librarymanagementsystem.repository;

import com.librarymanagementsystem.entities.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatronRepository extends JpaRepository<Patron, Long> {
}