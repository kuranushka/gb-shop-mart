package ru.kuranov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kuranov.model.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
