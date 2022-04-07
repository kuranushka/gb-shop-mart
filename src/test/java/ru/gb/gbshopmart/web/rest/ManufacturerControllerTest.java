package ru.gb.gbshopmart.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import ru.gb.gbapi.category.dto.CategoryDto;
import ru.gb.gbapi.manufacturer.dto.ManufacturerDto;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql("/test.sql")
class ManufacturerControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getAllManufacturersSuccessTest() throws Exception {
        mockMvc.perform(get("/api/v1/manufacturer")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(content().string(containsString("name")))
                .andExpect(content().string(containsString("Unilever")))
                .andExpect(content().string(containsString("Heinz")))
                .andExpect(content().string(containsString("Nestle")))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void getManufacturerSuccessTest() throws Exception {
        mockMvc.perform(get("/api/v1/manufacturer/1"))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Unilever"));
    }

    @Test
    void getManufacturerNoSuccessTest() throws Exception {
        mockMvc.perform(get("/api/v1/manufacturer/10"))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isNotFound());
    }

    @Test
    void createManufacturerSuccessTest() throws Exception {
        mockMvc.perform(post("/api/v1/manufacturer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ManufacturerDto.builder()
                                .name("IBM")
                                .build())))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl("/api/v1/manufacturer/4"))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/v1/manufacturer/4"))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("4"))
                .andExpect(jsonPath("$.name").value("IBM"));

        mockMvc.perform(get("/api/v1/manufacturer"))
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    void updateManufacturerSuccessTest() throws Exception {
        mockMvc.perform(put("/api/v1/manufacturer/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                ManufacturerDto.builder()
                                        .name("New Unilever")
                                        .build())
                        ))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/manufacturer/1"))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("New Unilever"));

        mockMvc.perform(get("/api/v1/manufacturer"))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void updateManufacturerNotSuccessTest() throws Exception {
        mockMvc.perform(put("/api/v1/manufacturer/4"))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().is4xxClientError());

        mockMvc.perform(get("/api/v1/manufacturer"))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void deleteManufacturerSuccessTest() throws Exception {
        mockMvc.perform(delete("/api/v1/manufacturer/1"))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/manufacturer/1"))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isNotFound());

        mockMvc.perform(get("/api/v1/manufacturer"))
                .andExpect(jsonPath("$", hasSize(2)));
    }
}