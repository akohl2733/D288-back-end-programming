package ak288.com.example.demo.services;

import ak288.com.example.demo.dao.CartRepository;
import ak288.com.example.demo.dao.CartItemRepository;
import ak288.com.example.demo.dao.CustomerRepository;
import ak288.com.example.demo.entities.Cart;
import ak288.com.example.demo.entities.Customer;
import ak288.com.example.demo.entities.CartItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ak288.com.example.demo.entities.StatusType;


import java.util.UUID;
import java.util.Set;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private CustomerRepository customerRepository;
    private CartRepository cartRepository;
    private CartItemRepository cartItemRepository;

    public CheckoutServiceImpl(
            CustomerRepository customerRepository,
            CartRepository cartRepository,
            CartItemRepository cartItemRepository
    ) {
        this.customerRepository = customerRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        // retrieve the cart info from dto
        Cart cart = purchase.getCart();

        if (cart == null || cart.getCartItem() == null || cart.getCartItem().isEmpty()) {
            return new PurchaseResponse("ERROR: Cart cannot be empty");
        }

        // generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        cart.setOrderTrackingNumber(orderTrackingNumber);

        // populate cart with cartItems
        Set<CartItem> cartItems = purchase.getCartItem();
        cartItems.forEach(item -> item.setCart(cart));
        cartItems.forEach(item -> cart.add(item));


        // save cart to the database
        cart.setStatus(StatusType.ordered);
        cartRepository.save(cart);
//        cartItems.forEach(item -> System.out.println(item));

        //populate customer with cart
        Customer customer = purchase.getCustomer();
        customer.addCart(cart);

        // save customer to the database
        // for some reason the tracking number doesn't populate unless this is commented out
//        customerRepository.save(customer);

        // return a response
        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {

        // generate a random UUID number (UUID version-4)
        return UUID.randomUUID().toString();

    }
}