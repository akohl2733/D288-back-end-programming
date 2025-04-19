package ak288.com.example.demo.services;

import ak288.com.example.demo.dao.CartRepository;
import ak288.com.example.demo.dao.CustomerRepository;
import ak288.com.example.demo.entities.Cart;
import ak288.com.example.demo.entities.CartItem;
import ak288.com.example.demo.services.PurchaseResponse;
import ak288.com.example.demo.services.Purchase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.Set;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private CartRepository cartRepository;

    public CheckoutServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        Cart cart = purchase.getCart();

        String orderTrackingNumber = generateOrderTrackingNumber();
        cart.setOrderTrackingNumber(orderTrackingNumber);

        Set<CartItem> cartItems = purchase.getCartItem();
        cartItems.forEach(item -> cart.add(item));

        cartRepository.save(cart);

        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }

}
