package ru.gb.gbshopmart.web.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.gb.gbshopmart.entity.Category;
import ru.gb.gbshopmart.web.dto.CategoryDto;

@Mapper
public interface CategoryMapper {

    @Mapping(source = "categoryId", target = "id")
    Category toCategory(CategoryDto categoryDto);

    @Mapping(source = "id", target = "categoryId")
    CategoryDto toCategoryDto(Category category);
}
