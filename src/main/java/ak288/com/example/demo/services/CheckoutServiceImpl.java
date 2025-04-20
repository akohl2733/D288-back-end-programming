package ak288.com.example.demo.services;

import ak288.com.example.demo.dao.CartRepository;
import ak288.com.example.demo.dao.CartItemRepository;
import ak288.com.example.demo.dao.CustomerRepository;
import ak288.com.example.demo.entities.Cart;
import ak288.com.example.demo.entities.Excursion;
import ak288.com.example.demo.entities.CartItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ak288.com.example.demo.entities.StatusType;


import java.util.UUID;
import java.util.Objects;
import java.math.BigDecimal;

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
        try {
            if (purchase.getCustomer() == null) {
                return new PurchaseResponse("ERROR: Customer cannot be null");
            }
            if (purchase.getCartItems() == null || purchase.getCartItems().isEmpty()) {
                return new PurchaseResponse("ERROR: Cart cannot be empty");
            }

            Cart cart = purchase.getCart();
            if (cart == null || cart.getId() == null || cart.getId() == 0) {
                cart = new Cart();
                cart.setCustomer(purchase.getCustomer());
                cart.setStatus(StatusType.pending);
                cart = cartRepository.save(cart);
            }

            BigDecimal totalPackagePrice = BigDecimal.ZERO;
            int totalPartySize = purchase.getCart().getParty_size();

            for (CartItem item : purchase.getCartItems()) {
                if (item.getVacation() == null || item.getVacation().getTravel_price() == null) {
                    return new PurchaseResponse("ERROR: Invalid vacation in cart");
                }

                BigDecimal itemTotal = item.getVacation().getTravel_price();

                if (item.getExcursions() != null && !item.getExcursions().isEmpty()) {
                    BigDecimal excursionsTotal = item.getExcursions().stream()
                            .map(Excursion::getExcursion_price)
                            .filter(Objects::nonNull)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    itemTotal = itemTotal.add(excursionsTotal);
                }

                totalPackagePrice = totalPackagePrice.add(itemTotal);

                item.setCart(cart);
                cartItemRepository.save(item);
            }

            totalPackagePrice = totalPackagePrice.multiply(BigDecimal.valueOf(totalPartySize));
            cart.setPackage_price(totalPackagePrice);
            cart.setParty_size(totalPartySize);
            cart.setOrderTrackingNumber(generateOrderTrackingNumber());
            cart.setStatus(StatusType.ordered);
            cartRepository.save(cart);

            return new PurchaseResponse(cart.getOrderTrackingNumber());
        } catch (Exception e) {
            System.err.println("Checkout failed: " + e.getMessage());
            e.printStackTrace();
            return new PurchaseResponse("ERROR: Checkout processing failed");
        }
    }

    private String generateOrderTrackingNumber() {

        return UUID.randomUUID().toString();

    }
}