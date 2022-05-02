package ru.kuranov.service.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import ru.gb.gbapi.common.enums.Status;
import ru.gb.gbapi.product.dto.ProductDto;
import ru.kuranov.model.entity.Cart;
import ru.kuranov.repository.CartRepository;
import ru.kuranov.service.CartService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductService productService;
    private Map<Long, Long> productsInCart = new HashMap<>();
    private BigDecimal totalCartCost;
    private Long cartId;

    @Override
    public void saveCart() {
        Cart cart = Cart.builder()
                .id(cartId)
                .totalCost(totalCartCost)
                .products(getCartProductsId(getCartProducts()))
                .status(Status.ACTIVE)
                .build();
        cartRepository.save(cart);
        productsInCart.clear();
        totalCartCost = BigDecimal.valueOf(0);
    }

    @Override
    public void addProductToCart(Long productId, Long quantity) {
        if (productId <= 0) {
            return;
        }
        if (productsInCart.containsKey(productId)) {
            productsInCart.replace(productId, productsInCart.get(productId) + quantity);
        } else {
            productsInCart.put(productId, quantity);
        }
        updateTotalCost();
        Map<ProductDto, Long> cartProducts = getCartProducts();
        if (cartId == null) {
            Cart cart = Cart.builder()
                    .products(getCartProductsId(cartProducts))
                    .status(Status.ACTIVE)
                    .totalCost(totalCartCost)
                    .build();
            cartRepository.save(cart);
        } else {
            Optional<Cart> cart = cartRepository.findById(cartId);
            if (cart.isPresent()) {
                cart.get().setProducts(getCartProductsId(cartProducts));
                cart.get().setTotalCost(totalCartCost);
                cartRepository.save(cart.get());
            }
        }
    }

    private Map<Long, Long> getCartProductsId(Map<ProductDto, Long> cartProducts) {
        return cartProducts
                .entrySet()
                .stream()
                .collect(Collectors.toMap(key -> key.getKey().getId(), Map.Entry::getValue));
    }

    @Override
    public Map<ProductDto, Long> updateCart(Long productId, Long quantity) {
        for (Map.Entry<Long, Long> entry : productsInCart.entrySet()) {
            if (entry.getKey().equals(productId)) {
                entry.setValue(quantity);
            }
        }
        updateTotalCost();
        return getCartProducts();
    }

    @Override
    public void deleteProductFromCart(Long productToDelete) {
        Map<Long, Long> filteredProduct = productsInCart.entrySet().stream()
                .filter(entry -> !entry.getKey().equals(productToDelete))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        productsInCart = filteredProduct;
        updateTotalCost();
    }

    @Override
    public Map<ProductDto, Long> getCartProducts() {
        Map<ProductDto, Long> cartProducts = new HashMap<>();
        List<ProductDto> allProducts = productService.findAll();
        for (ProductDto product : allProducts) {
            if (productsInCart.containsKey(product.getId())) {
                Long quantity = productsInCart.get(product.getId());
                cartProducts.put(product, quantity);
            }
        }
        return cartProducts;
    }

    @Override
    public void updateTotalCost() {
        Map<ProductDto, Long> products = getCartProducts();
        totalCartCost = BigDecimal.valueOf(0);
        for (Map.Entry<ProductDto, Long> entry : products.entrySet()) {
            BigDecimal cost = entry.getKey().getCost();
            Long productQuantity = entry.getValue();
            BigDecimal fullProductCost = cost.multiply(BigDecimal.valueOf(productQuantity));
            totalCartCost = totalCartCost.add(fullProductCost);
        }
    }

    @Override
    public BigDecimal getTotalCartCost() {
        return totalCartCost;
    }

    @Override
    public boolean validQuantity(String quantity) {
        return quantity.matches("\\d*");
    }
}
