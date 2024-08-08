package com.librarymanagementsystem.testing;
import com.librarymanagementsystem.controller.BookController;
import com.librarymanagementsystem.controller.BorrowingRecordController;
import com.librarymanagementsystem.entities.Book;
import com.librarymanagementsystem.entities.BorrowingRecord;
import com.librarymanagementsystem.service.BookService;
import com.librarymanagementsystem.service.BorrowingRecordService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
public class BorrowingRecordControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BorrowingRecordService borrowingRecordService;

    @InjectMocks
    private BorrowingRecordController borrowingRecordController;

    public BorrowingRecordControllerTest() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(borrowingRecordController).build();
    }

    @Test
    public void testBorrowBook() throws Exception {
        BorrowingRecord record = new BorrowingRecord();
        record.setId(1L);

        when(borrowingRecordService.borrowBook(1L, 1L)).thenReturn(record);

        mockMvc.perform(post("/api/borrow/1/patron/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testReturnBook() throws Exception {
        BorrowingRecord record = new BorrowingRecord();
        record.setId(1L);

        when(borrowingRecordService.returnBook(1L, 1L)).thenReturn(record);

        mockMvc.perform(put("/api/return/1/patron/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }
}