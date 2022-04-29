package ru.kuranov.service;

import org.springframework.stereotype.Service;
import ru.gb.gbapi.product.dto.ProductDto;

import java.math.BigDecimal;
import java.util.Map;

@Service
public interface CartService {

    void saveCart();

    void addProductToCart(Long productId, Long quantity);

    Map<ProductDto, Long> updateCart(Long productId, Long quantity);

    void deleteProductFromCart(Long productToDelete);

    Map<ProductDto, Long> getCartProducts();

    void updateTotalCost();

     BigDecimal getTotalCartCost();

    boolean validQuantity(String quantity);
}
