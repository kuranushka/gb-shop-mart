package ru.gb.gbshopmart.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import ru.gb.gbshopmart.config.ShopConfig;
import ru.gb.gbshopmart.entity.Category;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(ShopConfig.class)
@Sql("/fill-categories.sql")
public class CategoryDaoTest {

    private final long CATEGORY_ID = 1L;
    private final String CATEGORY_TITLE = "Grocery";
    private final String NEW_CATEGORY_TITLE = "Equipmrnt";

    @Autowired
    CategoryDao categoryDao;

    @Test
    void getCategoryListJpaTest() {
        List<Category> categories = categoryDao.findAll();
        assertAll(
                () -> assertNotNull(categories),
                () -> assertEquals(3, categories.size())
        );
    }

    @Test
    void getCategoryByIdJpaTest() {
        Optional<Category> category = categoryDao.findById(CATEGORY_ID);

        assertAll(
                () -> assertNotNull(category),
                () -> assertTrue(category.isPresent()),
                () -> assertEquals(category.get().getTitle(), "Grocery")
        );
    }

    @Test
    void getCategoryByTitleJpaTest() {
        Optional<Category> category = categoryDao.findByTitle(CATEGORY_TITLE);

        assertAll(
                () -> assertNotNull(category),
                () -> assertTrue(category.isPresent()),
                () -> assertEquals(category.get().getId(), CATEGORY_ID)
        );
    }

    @Test
    void saveCategoryJpaTest() {
        Category category = Category.builder()
                .title(NEW_CATEGORY_TITLE)
                .build();
        categoryDao.save(category);

        Optional<Category> optionalCategory = categoryDao.findByTitle(NEW_CATEGORY_TITLE);

        assertAll(
                () -> assertNotNull(optionalCategory),
                () -> assertTrue(optionalCategory.isPresent()),
                () -> assertEquals(optionalCategory.get().getTitle(), NEW_CATEGORY_TITLE)
        );

        List<Category> categories = categoryDao.findAll();

        assertAll(
                () -> assertNotNull(categories),
                () -> assertEquals(categories.size(), 4)
        );
    }

    @Test
    void updateCategoryJpaTest() {
        Category extractedCategory = categoryDao.findById(CATEGORY_ID).orElseThrow();
        int versionExtractedCategory = extractedCategory.getVersion();

        extractedCategory.setTitle(NEW_CATEGORY_TITLE);
        categoryDao.save(extractedCategory);

        Category savedCategory = categoryDao.findByTitle(NEW_CATEGORY_TITLE).orElseThrow();
        int versionSavedCategory = savedCategory.getVersion();

        assertAll(
                () -> assertEquals(savedCategory.getTitle(), NEW_CATEGORY_TITLE),
                () -> assertEquals(extractedCategory.getId(), savedCategory.getId()),
                () -> assertNotEquals(versionExtractedCategory, versionSavedCategory),
                () -> assertNotNull(savedCategory.getLastModifiedBy()),
                () -> assertNotNull(savedCategory.getLastModifiedDate()),
                () -> assertEquals(savedCategory.getLastModifiedBy(), "User")
        );
    }

    @Test
    void deleteCategoryJpaTest() {
        Category category = categoryDao.findById(CATEGORY_ID).orElseThrow();

        categoryDao.deleteById(CATEGORY_ID);

        assertAll(
                () -> assertEquals(categoryDao.findAll().size(), 2),
                () -> assertEquals(categoryDao.findById(CATEGORY_ID), Optional.empty()),
                () -> assertEquals(categoryDao.findByTitle(category.getTitle()), Optional.empty())
        );
    }
}
