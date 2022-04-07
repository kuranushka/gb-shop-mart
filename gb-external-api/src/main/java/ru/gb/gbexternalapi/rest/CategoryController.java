package ru.gb.gbexternalapi.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.gbapi.category.api.CategoryGateway;
import ru.gb.gbapi.category.dto.CategoryDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryGateway categoryGateway;

    @GetMapping
    List<CategoryDto> getCategoryList() {
        return categoryGateway.getCategoryList();
    }

    @GetMapping("/{id}")
    ResponseEntity<CategoryDto> getCategory(@PathVariable("id") Long id) {
        return categoryGateway.getCategory(id);
    }

    @PostMapping()
    ResponseEntity<CategoryDto> handlePost(@Validated @RequestBody CategoryDto categoryDto) {
        return categoryGateway.handlePost(categoryDto);
    }

    @PutMapping("/{id}")
    ResponseEntity<CategoryDto> handleUpdate(@PathVariable("id") Long id, @Validated @RequestBody CategoryDto categoryDto) {
        return categoryGateway.handleUpdate(id, categoryDto);
    }

    @DeleteMapping("/{id}")
    void deleteById(@PathVariable("id") Long id) {
        categoryGateway.deleteById(id);
    }
}
