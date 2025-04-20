package ak288.com.example.demo.services;

import ak288.com.example.demo.entities.CartItem;
import ak288.com.example.demo.entities.Cart;
import ak288.com.example.demo.entities.Customer;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
public class Purchase {

    private Customer customer;
    private Cart cart;
    private Set<CartItem> cartItems;
}
