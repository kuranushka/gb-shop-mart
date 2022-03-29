package ru.gb.gbshopmart.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import ru.gb.gbshopmart.web.dto.CategoryDto;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql("/fill-categories.sql")
class CategoryControllerEnvironmentMockitoTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void mockMvcGetCategoryListEnvironmentTest() throws Exception {
        mockMvc.perform(get("/api/v1/category"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("title")))
                .andExpect(content().string(containsString("id")))
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].title").value("Grocery"))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void mockMvcGetSuccessCategoryEnvironmentTest() throws Exception {
        mockMvc.perform(get("/api/v1/category/2"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("title")))
                .andExpect(content().string(containsString("id")))
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.title").value("Fruits"));
    }

    @Test
    void mockMvcGetNoSuccessCategoryEnvironmentTest() throws Exception {
        mockMvc.perform(get("/api/v1/category/10"))
                .andExpect(status().isNotFound());
    }

    @Test
    void mockMvcSaveCategoryEnvironmentTest() throws Exception {
        mockMvc.perform(post("/api/v1/category/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .writeValueAsString(CategoryDto.builder()
                                        .title("Equipment")
                                        .build())))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/v1/category/4"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("title")))
                .andExpect(content().string(containsString("id")))
                .andExpect(jsonPath("$.id").value("4"))
                .andExpect(jsonPath("$.title").value("Equipment"));

        mockMvc.perform(get("/api/v1/category"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    void mockMvcSaveAlreadyExistedCategoryEnvironmentTest() throws Exception {
        mockMvc.perform(post("/api/v1/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .writeValueAsString(CategoryDto.builder()
                                        .title("Grocery")
                                        .build())))
                .andExpect(status().isBadRequest());
    }

    @Test
    void mockMvcSuccessUpdateCategoryEnvironmentTest() throws Exception {
        mockMvc.perform(put("/api/v1/category/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CategoryDto.builder()
                                .title("Grocery New Name")
                                .build())))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/category/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("title")))
                .andExpect(content().string(containsString("id")))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Grocery New Name"));
    }

    @Test
    void mockMvcUpdateNotExistedCategoryEnvironmentTest() throws Exception {
        mockMvc.perform(put("/api/v1/category/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CategoryDto.builder()
                                .title("Grocery New Name")
                                .build())))
                .andExpect(status().isNotFound());
    }

    @Test
    void mockMvcDeleteCategoryEnvironmentTest() throws Exception {
        mockMvc.perform(delete("/api/v1/category/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/category/1"))
                .andExpect(status().isNotFound());

        mockMvc.perform(get("/api/v1/category"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}
