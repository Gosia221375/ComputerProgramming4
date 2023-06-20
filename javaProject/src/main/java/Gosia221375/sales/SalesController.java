package Gosia221375.sales;

import org.springframework.web.bind.annotation.*;

import pl.jGosia221375.sales.offering.Offer;
import pl.jGosia221375.sales.reservation.OfferAcceptanceRequest;
import pl.jGosia221375.sales.reservation.ReservationDetails;
import pl.jGosia221375.web.CurrentCustomerContext;
import pl.jGosia221375.web.SessionCurrentCustomerContext;

@RestController
public class SalesController {

    private Sales sales;
    private CurrentCustomerContext currentCustomerContext;

    public SalesController(Sales sales, CurrentCustomerContext currentCustomerContext) {
        this.sales = sales;
        this.currentCustomerContext = currentCustomerContext;
    }

    @GetMapping("/api/current-offer")
    public Offer currentOffer() {
        return sales.getCurrentOffer(getCurrentCustomer());
    }

    @PostMapping("/api/cart/{productId}")
    public void addToCart(@PathVariable String productId) {

        sales.addToCart(getCurrentCustomer(), productId);
    }


    @PostMapping("/api/accept-offer")
    public ReservationDetails acceptOffer(@RequestBody OfferAcceptanceRequest request) {
        return sales.acceptOffer(getCurrentCustomerId(), request);
    }

    @GetMapping("/api/current-customer")
    public String getCurrentCustomerId() {
        return currentCustomerContext.getCurrentCustomerId();
    }
    private String getCurrentCustomer() {
        return currentCustomerContext.getCurrentCustomerId();
    }
}
