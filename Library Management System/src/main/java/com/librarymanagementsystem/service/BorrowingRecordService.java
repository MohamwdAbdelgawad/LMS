package com.librarymanagementsystem.service;


import com.librarymanagementsystem.entities.Book;
import com.librarymanagementsystem.entities.BorrowingRecord;
import com.librarymanagementsystem.entities.Patron;
import com.librarymanagementsystem.repository.BookRepository;
import com.librarymanagementsystem.repository.BorrowingRecordRepository;
import com.librarymanagementsystem.repository.PatronRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BorrowingRecordService {
    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PatronRepository patronRepository;

    @Transactional
    public BorrowingRecord borrowBook(Long bookId, Long patronId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        Patron patron = patronRepository.findById(patronId).orElseThrow(() -> new RuntimeException("Patron not found"));

        BorrowingRecord record = new BorrowingRecord();
        record.setBook(book);
        record.setPatron(patron);
        record.setBorrowingDate(LocalDate.now());

        return borrowingRecordRepository.save(record);
    }

    @Transactional
    public BorrowingRecord returnBook(Long bookId, Long patronId) {
        BorrowingRecord record = borrowingRecordRepository.findByBookIdAndPatronId(bookId, patronId)
                .orElseThrow(() -> new RuntimeException("Borrowing record not found"));

        record.setReturnDate(LocalDate.now());
        return borrowingRecordRepository.save(record);
    }
}