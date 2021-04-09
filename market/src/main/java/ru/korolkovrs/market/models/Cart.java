package ru.korolkovrs.market.models;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "carts")
@Data
public class Cart {
    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id")
    private UUID id;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> cartItems;

    @Column(name = "price")
    private int price;

    @OneToOne
    @JoinColumn(name = "owner_id")
    private User user;

    public void add(CartItem cartItem) {
        this.cartItems.add(cartItem);
        cartItem.setCart(this);
        recalculate();
    }

    public void recalculate() {
        price = 0;
        for (CartItem ci : cartItems) {
            price += ci.getPrice();
        }
    }

    public void clear() {
        for (CartItem ci : cartItems) {
            ci.setCart(null);
        }
        cartItems.clear();
        recalculate();
    }

    public CartItem getItemByProductId(Long productId) {
        for (CartItem ci : cartItems) {
            if (ci.getProduct().getId().equals(productId)) {
                return ci;
            }
        }
        return null;
    }

    public void merge(Cart another) {
        for (CartItem ci : another.cartItems) {
            add(ci);
        }
    }
}
