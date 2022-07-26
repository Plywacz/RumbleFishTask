package com.example.rumblefishsimpletask.controller;

import com.example.rumblefishsimpletask.application.NameDto;
import com.example.rumblefishsimpletask.domain.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class NameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Set<Name> testData;

    @BeforeEach
    void setUp() {
        testData.clear();
    }

    @Test
    void save() throws Exception {
        mockMvc.perform(
                        post("/name")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(newNameJson())
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Jan"));
    }

    @Test
    void save_nameAlreadyExists() throws Exception {
        testData.add(new Name(new NameDto("DuplicatedName")));

        mockMvc.perform(
                        post("/name")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(duplicatedNameJson())
                )
                .andExpect(status().isConflict());
    }

    @Test
    void save_givenNameFormatIsIncorrect() throws Exception {
        mockMvc.perform(
                        post("/name")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(incorrectFormattedName())
                )
                .andExpect(status().isBadRequest());
    }

    private String newNameJson() {
        return """
                  {
                  "name": "Jan"
                  }
                """;
    }

    private String duplicatedNameJson() {
        return """
                  {
                  "name": "DuplicatedName"
                  }
                """;
    }

    private String incorrectFormattedName() {
        return """
                  {
                  "name": "123NamQRe4"
                  }
                """;
    }

    @Test
    void greet() throws Exception {
        testData.add(new Name(new NameDto("Jan")));

        mockMvc.perform(
                        get("/name/Jan")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Greeting Jan"));
    }

    @Test
    void greet_nameNotExists() throws Exception {
        mockMvc.perform(
                        get("/name/Jan")
                )
                .andExpect(status().isNotFound());
    }
}