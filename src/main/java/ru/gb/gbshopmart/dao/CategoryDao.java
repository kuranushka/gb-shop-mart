package ru.gb.gbshopmart.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.gbshopmart.entity.Category;

import java.util.List;
import java.util.Set;

public interface CategoryDao extends JpaRepository<Category, Long> {

    List<Category> findCategoriesByTitleIn(Set<String> titles);

}
