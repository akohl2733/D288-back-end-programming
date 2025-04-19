package ak288.com.example.demo.services;

import ak288.com.example.demo.dao.CartRepository;
import ak288.com.example.demo.dao.CustomerRepository;
import ak288.com.example.demo.entities.Cart;
import ak288.com.example.demo.entities.CartItem;
import ak288.com.example.demo.services.PurchaseResponse;
import ak288.com.example.demo.services.Purchase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ak288.com.example.demo.entities.StatusType;


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

        if (purchase.getCart() == null) {
            return new PurchaseResponse("ERROR: Cart cannot be null");
        }

        Cart cart = purchase.getCart();

        if (cart.getCartItem() == null || cart.getCartItem().isEmpty()) {
            return new PurchaseResponse("ERROR: Cart can't be empty");
        }

        String orderTrackingNumber = generateOrderTrackingNumber();
        cart.setOrderTrackingNumber(orderTrackingNumber);
        cart.setStatus(StatusType.ORDERED);

        Set<CartItem> cartItems = purchase.getCartItem();
        cartItems.forEach(item -> cart.add(item));

        cartRepository.save(cart);

        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }

}
