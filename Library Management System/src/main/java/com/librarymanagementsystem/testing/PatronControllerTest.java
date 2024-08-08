package com.librarymanagementsystem.testing;
import com.librarymanagementsystem.controller.BookController;
import com.librarymanagementsystem.controller.PatronController;
import com.librarymanagementsystem.entities.Book;
import com.librarymanagementsystem.entities.Patron;
import com.librarymanagementsystem.service.BookService;
import com.librarymanagementsystem.service.PatronService;
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
public class PatronControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PatronService patronService;

    @InjectMocks
    private PatronController patronController;

    public PatronControllerTest() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(patronController).build();
    }

    @Test
    public void testGetAllPatrons() throws Exception {
        when(patronService.getAllPatrons()).thenReturn(Arrays.asList(
                new Patron(1L, "Patron 1", "patron1@example.com"),
                new Patron(2L, "Patron 2", "patron2@example.com")
        ));

        mockMvc.perform(get("/api/patrons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Patron 1"))
                .andExpect(jsonPath("$[1].name").value("Patron 2"));
    }

    @Test
    public void testGetPatronById() throws Exception {
        Patron patron = new Patron(1L, "Patron 1", "patron1@example.com");

        when(patronService.getPatronById(1L)).thenReturn(Optional.of(patron));

        mockMvc.perform(get("/api/patrons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Patron 1"));
    }

    @Test
    public void testAddPatron() throws Exception {
        Patron patron = new Patron(1L, "New Patron", "newpatron@example.com");

        when(patronService.addPatron(any(Patron.class))).thenReturn(patron);

        mockMvc.perform(post("/api/patrons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New Patron\",\"contactInformation\":\"newpatron@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Patron"));
    }

    @Test
    public void testUpdatePatron() throws Exception {
        Patron updatedPatron = new Patron(1L, "Updated Patron", "updatedpatron@example.com");

        when(patronService.updatePatron(eq(1L), any(Patron.class))).thenReturn(updatedPatron);

        mockMvc.perform(put("/api/patrons/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Patron\",\"contactInformation\":\"updatedpatron@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Patron"));
    }

    @Test
    public void testDeletePatron() throws Exception {
        doNothing().when(patronService).deletePatron(1L);

        mockMvc.perform(delete("/api/patrons/1"))
                .andExpect(status().isNoContent());
    }
}