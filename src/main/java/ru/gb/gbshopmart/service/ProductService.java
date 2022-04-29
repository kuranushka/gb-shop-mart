package ru.gb.gbshopmart.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.gbapi.category.dto.CategoryDto;
import ru.gb.gbapi.common.enums.Status;
import ru.gb.gbapi.product.dto.ProductDto;
import ru.gb.gbshopmart.dao.CategoryDao;
import ru.gb.gbshopmart.dao.ManufacturerDao;
import ru.gb.gbshopmart.dao.ProductDao;
import ru.gb.gbshopmart.entity.Manufacturer;
import ru.gb.gbshopmart.entity.Product;
import ru.gb.gbshopmart.web.dto.mapper.ProductMapper;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductDao productDao;
    private final ManufacturerDao manufacturerDao;
    private final ProductMapper productMapper;
    private final CategoryDao categoryDao;

    @Transactional(propagation = Propagation.NEVER, isolation = Isolation.DEFAULT)
    public long count() {
        System.out.println(productDao.count());
        return productDao.count();
    }

    @Transactional
    public ProductDto save(final ProductDto productDto) {
        Product product = productMapper.toProduct(productDto, manufacturerDao, categoryDao);
        if (product.getId() != null) {
            productDao.findById(productDto.getId()).ifPresent(
                    (p) -> product.setVersion(p.getVersion())
            );
        }
        return productMapper.toProductDto(productDao.save(product));
    }


    @Transactional(readOnly = true)
    public ProductDto findById(Long id) {
        return productMapper.toProductDto(productDao.findById(id).orElse(null));
    }


    public List<ProductDto> findAll() {
        return productDao.findAll().stream().map(productMapper::toProductDto).collect(Collectors.toList());
    }

    public List<Product> findAllActive() {
        return productDao.findAllByStatus(Status.ACTIVE);
    }

    public void deleteById(Long id) {
        try {
            productDao.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getMessage());
        }
    }

    public void disable(Long id) {
        Optional<Product> product = productDao.findById(id);
        product.ifPresent(p -> {
            p.setStatus(Status.DISABLED);
            productDao.save(p);
        });
    }

    public List<Product> findAll(int page, int size) {
        return productDao.findAllByStatus(Status.ACTIVE, PageRequest.of(page, size));
    }

    public List<Product> findAllSortedById() {
        return productDao.findAllByStatus(Status.ACTIVE, Sort.by("id"));
    }

    public List<Product> findAllSortedById(int page, int size) {
        return productDao.findAllByStatus(Status.ACTIVE, PageRequest.of(page, size, Sort.by("id")));
    }

    public Optional<ProductDto> isValidAttributes(ProductDto productDto) {
        Set<String> titles = productDto.getCategories().stream().map(CategoryDto::getTitle).collect(Collectors.toSet());
        Set<CategoryDto> categories = categoryDao.findCategoriesByTitleIn(titles)
                .stream()
                .map(category -> CategoryDto.builder()
                        .id(category.getId())
                        .title(category.getTitle())
                        .build())
                .collect(Collectors.toSet());
        Optional<Manufacturer> optionalManufacturer = manufacturerDao.findByName(productDto.getManufacturer());
        if (optionalManufacturer.isPresent()) {
            productDto.setCategories(categories);
            productDto.setManufacturer(optionalManufacturer.get().getName());
        }
        return Optional.of(productDto);
    }

    @Transactional(readOnly = true)
    public Long findMinCost() {
        return productDao.findMinCost();
    }


    @Transactional(readOnly = true)
    public Long findMaxCost() {
        return productDao.findMaxCost();
    }

    public Page<Product> findAllPagingAndSortingAndFiltering(
            Integer page, Integer productsOnPage,
            Long min, Long max,
            String sortDirection) {
        Pageable pageable;
        if (sortDirection.equals("asc")) {
            pageable = PageRequest.of(page, productsOnPage, Sort.by("cost").ascending());
        } else {
            pageable = PageRequest.of(page, productsOnPage, Sort.by("cost").descending());
        }
        return productDao.findAllPagingAndSortingAndFiltering(pageable, min, max);
    }
}
