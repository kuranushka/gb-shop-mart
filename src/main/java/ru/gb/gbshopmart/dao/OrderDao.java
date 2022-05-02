package ru.gb.gbshopmart.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.gbshopmart.entity.Order;

@Repository
public interface OrderDao extends JpaRepository<Order, Long> {
}
