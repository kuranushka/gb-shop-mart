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
import ru.gb.gbapi.common.enums.Status;
import ru.gb.gbapi.product.dto.ProductDto;
import ru.gb.gbshopmart.dao.CategoryDao;
import ru.gb.gbshopmart.dao.ManufacturerDao;
import ru.gb.gbshopmart.entity.Category;
import ru.gb.gbshopmart.entity.Manufacturer;
import ru.gb.gbshopmart.web.dto.mapper.CategoryMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql("/test.sql")
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ManufacturerDao manufacturerDao;

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    CategoryMapper categoryMapper;

    @Test
    void getAllProductsSuccessTest() throws Exception {
        mockMvc.perform(get("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(content().string(containsString("title")))
                .andExpect(content().string(containsString("cost")))
                .andExpect(content().string(containsString("manufactureDate")))
                .andExpect(content().string(containsString("status")))
                .andExpect(content().string(containsString("manufacturer")))
                .andExpect(content().string(containsString("categories")))
                .andExpect(content().string(containsString("Apple")))
                .andExpect(content().string(containsString("Grapes")))
                .andExpect(content().string(containsString("Cucumber")))
                .andExpect(jsonPath("$", hasSize(9)));
    }

    @Test
    void getProductSuccessTest() throws Exception {
        mockMvc.perform(get("/api/v1/product/1"))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Apple"))
                .andExpect(jsonPath("$.cost").value("10.99"))
                .andExpect(jsonPath("$.title").value("Apple"))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$.manufacturer").value("Unilever"))
                .andExpect(jsonPath("$.categories").value(hasSize(1)));
    }

    @Test
    void getProductNoSuccessTest() throws Exception {
        mockMvc.perform(get("/api/v1/product/10"))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isNotFound());
    }


    @Test
    void createProductSuccessTest() throws Exception {

        Optional<Manufacturer> manufacturer = manufacturerDao.findByName("Unilever");
        Set<Category> categories = Set.of(categoryDao.findById(1L).get(), categoryDao.findById(2L).get());

        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ProductDto.builder()
                                .title("New Product")
                                .cost(BigDecimal.valueOf(99.99))
                                .manufactureDate(LocalDate.of(2022, 4, 7))
                                .status(Status.ACTIVE)
                                .manufacturer(manufacturer.get().getName())
                                .categories(categories.stream()
                                        .map(cat -> categoryMapper.toCategoryDto(cat))
                                        .collect(Collectors.toSet()))
                                .build())))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl("/api/v1/product/10"))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/v1/product/10"))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("10"))
                .andExpect(jsonPath("$.title").value("New Product"))
                .andExpect(jsonPath("$.cost").value("99.99"))
                .andExpect(jsonPath("$.manufactureDate").value("07.04.2022"))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$.manufacturer").value("Unilever"))
                .andExpect(jsonPath("$.categories").value(hasSize(2)));

        mockMvc.perform(get("/api/v1/product"))
                .andExpect(jsonPath("$", hasSize(10)));
    }

    @Test
    void createProductNotSuccessTest() throws Exception {

        String notExistedManufacturer = Manufacturer.builder()
                .name("Not Existed Manufacturer")
                .build()
                .getName();

        String existedManufacturer = Manufacturer.builder()
                .name("Unilever")
                .build()
                .getName();

        Set<CategoryDto> notExistedCategories = Set.of(
                CategoryDto.builder()
                        .title("Not Existed Category")
                        .build(),
                CategoryDto.builder()
                        .title("Fruits")
                        .build());

        Set<CategoryDto> existedCategories = Set.of(
                CategoryDto.builder()
                        .title("Fruits")
                        .build());

        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ProductDto.builder()
                                .title("New Product")
                                .cost(BigDecimal.valueOf(99.99))
                                .manufactureDate(LocalDate.of(2022, 4, 7))
                                .status(Status.ACTIVE)
                                .manufacturer(notExistedManufacturer)
                                .categories(notExistedCategories)
                                .build())))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isConflict());

        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ProductDto.builder()
                                .title("New Product")
                                .cost(BigDecimal.valueOf(99.99))
                                .manufactureDate(LocalDate.of(2022, 4, 7))
                                .status(Status.ACTIVE)
                                .manufacturer(notExistedManufacturer)
                                .categories(existedCategories)
                                .build())))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isConflict());

        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ProductDto.builder()
                                .title("New Product")
                                .cost(BigDecimal.valueOf(99.99))
                                .manufactureDate(LocalDate.of(2022, 4, 7))
                                .status(Status.ACTIVE)
                                .manufacturer(existedManufacturer)
                                .categories(notExistedCategories)
                                .build())))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isConflict());
    }

    @Test
    void createProductNotValidationCostTest() throws Exception {
        Optional<Manufacturer> manufacturer = manufacturerDao.findByName("Unilever");
        Set<Category> categories = Set.of(categoryDao.findById(1L).get(), categoryDao.findById(2L).get());

        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ProductDto.builder()
                                .title("New Product")
                                .cost(BigDecimal.valueOf(9999))
                                .manufactureDate(LocalDate.of(2022, 4, 7))
                                .status(Status.ACTIVE)
                                .manufacturer(manufacturer.get().getName())
                                .categories(categories.stream()
                                        .map(cat -> categoryMapper.toCategoryDto(cat))
                                        .collect(Collectors.toSet()))
                                .build())))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ProductDto.builder()
                                .title("New Product")
                                .cost(BigDecimal.valueOf(-2))
                                .manufactureDate(LocalDate.of(2022, 4, 7))
                                .status(Status.ACTIVE)
                                .manufacturer(manufacturer.get().getName())
                                .categories(categories.stream()
                                        .map(cat -> categoryMapper.toCategoryDto(cat))
                                        .collect(Collectors.toSet()))
                                .build())))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ProductDto.builder()
                                .title("New Product")
                                .cost(BigDecimal.valueOf(90.999))
                                .manufactureDate(LocalDate.of(2022, 4, 7))
                                .status(Status.ACTIVE)
                                .manufacturer(manufacturer.get().getName())
                                .categories(categories.stream()
                                        .map(cat -> categoryMapper.toCategoryDto(cat))
                                        .collect(Collectors.toSet()))
                                .build())))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createProductNotValidationTimeTest() throws Exception {
        Optional<Manufacturer> manufacturer = manufacturerDao.findByName("Unilever");
        Set<Category> categories = Set.of(categoryDao.findById(1L).get(), categoryDao.findById(2L).get());

        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ProductDto.builder()
                                .title("New Product")
                                .cost(BigDecimal.valueOf(99.99))
                                .manufactureDate(LocalDate.of(2040, 4, 7))
                                .status(Status.ACTIVE)
                                .manufacturer(manufacturer.get().getName())
                                .categories(categories.stream()
                                        .map(cat -> categoryMapper.toCategoryDto(cat))
                                        .collect(Collectors.toSet()))
                                .build())))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createProductNotValidationTitleTest() throws Exception {
        Optional<Manufacturer> manufacturer = manufacturerDao.findByName("Unilever");
        Set<Category> categories = Set.of(categoryDao.findById(1L).get(), categoryDao.findById(2L).get());

        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ProductDto.builder()
                                .title("")
                                .cost(BigDecimal.valueOf(99.99))
                                .manufactureDate(LocalDate.of(2010, 4, 7))
                                .status(Status.ACTIVE)
                                .manufacturer(manufacturer.get().getName())
                                .categories(categories.stream()
                                        .map(cat -> categoryMapper.toCategoryDto(cat))
                                        .collect(Collectors.toSet()))
                                .build())))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateProductSuccessTest() throws Exception {
        Optional<Manufacturer> manufacturer = manufacturerDao.findByName("Heinz");
        Set<Category> categories = Set.of(categoryDao.findById(1L).get(), categoryDao.findById(2L).get());

        mockMvc.perform(put("/api/v1/product/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ProductDto.builder()
                                .title("New Product")
                                .cost(BigDecimal.valueOf(99.99))
                                .manufactureDate(LocalDate.of(2022, 4, 7))
                                .status(Status.ACTIVE)
                                .manufacturer(manufacturer.get().getName())
                                .categories(categories.stream()
                                        .map(cat -> categoryMapper.toCategoryDto(cat))
                                        .collect(Collectors.toSet()))
                                .build())))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/product/1"))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("New Product"))
                .andExpect(jsonPath("$.cost").value("99.99"))
                .andExpect(jsonPath("$.manufactureDate").value("07.04.2022"))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$.manufacturer").value("Heinz"))
                .andExpect(jsonPath("$.categories").value(hasSize(2)));

        mockMvc.perform(get("/api/v1/product"))
                .andExpect(jsonPath("$", hasSize(9)));
    }

    @Test
    void updateProductNotSuccessTest() throws Exception {

        String notExistedManufacturer = Manufacturer.builder()
                .name("Not Existed Manufacturer")
                .build()
                .getName();

        String existedManufacturer = Manufacturer.builder()
                .name("Unilever")
                .build()
                .getName();

        Set<CategoryDto> notExistedCategories = Set.of(
                CategoryDto.builder()
                        .title("Not Existed Category")
                        .build(),
                CategoryDto.builder()
                        .title("Fruits")
                        .build());

        Set<CategoryDto> existedCategories = Set.of(
                CategoryDto.builder()
                        .title("Fruits")
                        .build());

        mockMvc.perform(put("/api/v1/product/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ProductDto.builder()
                                .title("New Product")
                                .cost(BigDecimal.valueOf(99.99))
                                .manufactureDate(LocalDate.of(2022, 4, 7))
                                .status(Status.ACTIVE)
                                .manufacturer(notExistedManufacturer)
                                .categories(notExistedCategories)
                                .build())))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isConflict());

        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ProductDto.builder()
                                .title("New Product")
                                .cost(BigDecimal.valueOf(99.99))
                                .manufactureDate(LocalDate.of(2022, 4, 7))
                                .status(Status.ACTIVE)
                                .manufacturer(notExistedManufacturer)
                                .categories(existedCategories)
                                .build())))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isConflict());

        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ProductDto.builder()
                                .title("New Product")
                                .cost(BigDecimal.valueOf(99.99))
                                .manufactureDate(LocalDate.of(2022, 4, 7))
                                .status(Status.ACTIVE)
                                .manufacturer(existedManufacturer)
                                .categories(notExistedCategories)
                                .build())))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isConflict());
    }

    @Test
    void deleteProductSuccessTest() throws Exception {
        mockMvc.perform(delete("/api/v1/product/1"))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/product/1"))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andExpect(status().isNotFound());

        mockMvc.perform(get("/api/v1/product"))
                .andExpect(jsonPath("$", hasSize(8)));
    }
}