package ru.gb.gbshopmart.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.gb.gbapi.common.enums.Status;
import ru.gb.gbshopmart.entity.Product;

import java.util.List;

public interface ProductDao extends JpaRepository<Product, Long> {
    List<Product> findAllByStatus(Status status);
    List<Product> findAllByStatus(Status status, Pageable pageable);
    List<Product> findAllByStatus(Status status, Sort sort);

    @Query(value = "select min(cost) from product", nativeQuery = true)
    Long findMinCost();

    @Query(value = "select max(cost) from product", nativeQuery = true)
    Long findMaxCost();

    @Query(value = "select * from Product p where p.cost between :min and :max", nativeQuery = true)
    Page<Product> findAllPagingAndSortingAndFiltering(Pageable pageable, Long min, Long max);

}
