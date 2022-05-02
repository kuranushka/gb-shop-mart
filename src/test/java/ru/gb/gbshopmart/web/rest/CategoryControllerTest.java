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

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql("/test.sql")
class CategoryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getAllCategoriesSuccessTest() throws Exception {
        mockMvc.perform(get("/api/v1/category")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(content().string(containsString("title")))
                .andExpect(content().string(containsString("Fruits")))
                .andExpect(content().string(containsString("Grocery")))
                .andExpect(content().string(containsString("Bread")))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void getCategorySuccessTest() throws Exception {
        mockMvc.perform(get("/api/v1/category/1"))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Fruits"));
    }

    @Test
    void getCategoryNoSuccessTest() throws Exception {
        mockMvc.perform(get("/api/v1/category/10"))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void createCategorySuccessTest() throws Exception {
        mockMvc.perform(post("/api/v1/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CategoryDto.builder()
                                .title("Sweets")
                                .build())))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl("/api/v1/category/4"))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/v1/category/4"))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("4"))
                .andExpect(jsonPath("$.title").value("Sweets"));

        mockMvc.perform(get("/api/v1/category"))
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    void updateCategorySuccessTest() throws Exception {
        mockMvc.perform(put("/api/v1/category/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                CategoryDto.builder()
                                        .title("New Fruits")
                                        .build())
                        ))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/category/1"))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("New Fruits"));

        mockMvc.perform(get("/api/v1/category"))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void updateCategoryNotSuccessTest() throws Exception {
        mockMvc.perform(put("/api/v1/category/4"))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().is4xxClientError());

        mockMvc.perform(get("/api/v1/category"))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void deleteCategorySuccessTest() throws Exception {
        mockMvc.perform(delete("/api/v1/category/1"))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/category/1"))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isInternalServerError());

        mockMvc.perform(get("/api/v1/category"))
                .andExpect(jsonPath("$", hasSize(2)));
    }
}