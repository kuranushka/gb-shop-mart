package ru.kuranov.model.entity;

import lombok.*;
import ru.gb.gbapi.common.enums.Status;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "totalcost")
    private BigDecimal totalCost;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "cart_products")
    private Map<Long, Long> products;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return id.equals(cart.id) && totalCost.equals(cart.totalCost) && status == cart.status && products.equals(cart.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, totalCost, status, products);
    }
}
