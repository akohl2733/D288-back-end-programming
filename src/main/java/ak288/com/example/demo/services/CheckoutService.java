package ak288.com.example.demo.services;

import ak288.com.example.demo.services.PurchaseResponse;
import ak288.com.example.demo.services.Purchase;


public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);
}
