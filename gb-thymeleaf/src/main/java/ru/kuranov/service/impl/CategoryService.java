package ru.kuranov.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.gbapi.category.dto.CategoryDto;
import ru.kuranov.service.gateway.CategoryGateway;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryGateway categoryGateway;

    public List<String> findAll() {
        return categoryGateway.getCategories().stream()
                .map(CategoryDto::getTitle)
                .collect(Collectors.toList());
    }
}
