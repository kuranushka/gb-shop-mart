package ru.gb.gbshopmart.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.gb.gbshopmart.service.CategoryService;
import ru.gb.gbshopmart.web.dto.CategoryDto;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CategoryControllerModuleMockitoTest {

    private static final Long CATEGORY_ID = 10L;
    @Mock
    CategoryService categoryService;

    @InjectMocks
    CategoryController categoryController;

    ObjectMapper objectMapper = new ObjectMapper();
    List<CategoryDto> categoryDtoList = new ArrayList<>();
    CategoryDto categoryDto;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        categoryDtoList.add(CategoryDto.builder().categoryId(1L).title("Grocery").build());
        categoryDtoList.add(CategoryDto.builder().categoryId(2L).title("Fruits").build());
        categoryDtoList.add(CategoryDto.builder().categoryId(3L).title("Household Goods").build());

        final long CATEGORY_ID = 10L;
        categoryDto = CategoryDto.builder().categoryId(CATEGORY_ID).title("Equipment").build();

        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    void mockMvcGetCategoryListTest() throws Exception {
        given(categoryService.findAll()).willReturn(categoryDtoList);

        mockMvc.perform(get("/api/v1/category").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("title")))
                .andExpect(content().string(containsString("id")))
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].title").value("Grocery"))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void mockMvcGetSuccessCategoryTest() throws Exception {
        given(categoryService.findById(any())).willReturn(categoryDto);

        mockMvc.perform(get("/api/v1/category/10").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("title")))
                .andExpect(content().string(containsString("id")))
                .andExpect(jsonPath("$.id").value("10"))
                .andExpect(jsonPath("$.title").value("Equipment"));
    }

    @Test
    void mockMvcGetNoSuccessCategoryTest() throws Exception {
        given(categoryService.findById(CATEGORY_ID)).willReturn(null);

        mockMvc.perform(get("/api/v1/category/10"))
                .andExpect(status().isNotFound());
    }

    @Test
    void mockMvcSaveCategoryTest() throws Exception {
        given(categoryService.save(any())).willReturn(new CategoryDto(CATEGORY_ID, "Equipment"));

        mockMvc.perform(post("/api/v1/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Equipment\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void mockMvcSaveAlreadyExistedCategoryTest() throws Exception {
        given(categoryService.isTitleExist(any())).willReturn(true);

        mockMvc.perform(post("/api/v1/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Equipment\"}"))
                .andExpect(status().isBadRequest());
        verify(categoryService, times(1)).isTitleExist(any());
        verify(categoryService, times(0)).save(any());

    }

    @Test
    void mockMvcSuccessUpdateCategoryTest() throws Exception {
        given(categoryService.isIdExist(any())).willReturn(true);

        mockMvc.perform(put("/api/v1/category/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .writeValueAsString(CategoryDto.builder()
                                        .title("Grocery New Name")
                                        .build())))
                .andExpect(status().isNoContent());

        verify(categoryService, times(1)).isIdExist(any());
        verify(categoryService, times(1)).save(any());
    }

    @Test
    void mockMvcUpdateNotExistedCategoryTest() throws Exception {
        given(categoryService.isIdExist(any())).willReturn(false);

        mockMvc.perform(put("/api/v1/category/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"tile\": \"Equipment\"}"))
                .andExpect(status().isNotFound());
        verify(categoryService, times(1)).isIdExist(any());
        verify(categoryService, times(0)).save(any());

    }

    @Test
    void mockMvcDeleteTest() throws Exception {
        mockMvc.perform(delete("/api/v1/category/10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(categoryService, times(1)).deleteById(any());

    }

}