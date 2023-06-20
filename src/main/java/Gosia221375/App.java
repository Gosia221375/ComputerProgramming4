package Gosia221375;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import Gosia221375.payu.PayU;
import Gosia221375.payu.PayUApiCredentials;
import Gosia221375.productcatalog.HashMapProductStorage;
import Gosia221375.productcatalog.ProductCatalog;
import Gosia221375.sales.Sales;
import Gosia221375.sales.cart.CartStorage;
import Gosia221375.sales.offering.OfferCalculator;
import Gosia221375.sales.payment.PaymentGateway;
import Gosia221375.sales.payment.PayuPaymentGateway;
import Gosia221375.sales.productdetails.InMemoryProductDetailsProvider;
import Gosia221375.sales.productdetails.ProductCatalogProductDetailsProvider;
import Gosia221375.sales.productdetails.ProductDetailsProvider;
import Gosia221375.sales.reservation.InMemoryReservationStorage;
import Gosia221375.web.SessionCurrentCustomerContext;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    ProductCatalog createNewProductCatalog() {
        ProductCatalog productCatalog = new ProductCatalog(new HashMapProductStorage());

        String productId1 = productCatalog.addProduct("Applying UML and Patterns", "Craig Larman");
        productCatalog.assignImage(productId1, "/image/book_1.jpg");
        productCatalog.changePrice(productId1, BigDecimal.TEN);
        productCatalog.publishProduct(productId1);

        String productId2 = productCatalog.addProduct("Clean Code", "Robert Martin");
        productCatalog.assignImage(productId2, "/image/book_2.jpg");
        productCatalog.changePrice(productId2, BigDecimal.valueOf(20.20));
        productCatalog.publishProduct(productId2);

        String productId3 = productCatalog.addProduct("Domain-Driven Design", "Eric Evans");
        productCatalog.assignImage(productId3, "/image/book_3.jpg");
        productCatalog.changePrice(productId3, BigDecimal.valueOf(30.30));
        productCatalog.publishProduct(productId3);

        return productCatalog;
    }

    @Bean
    PaymentGateway createPaymentGateway() {
        return new PayuPaymentGateway(new PayU(PayUApiCredentials.sandbox(), new RestTemplate()));
    }

    @Bean
    Sales createSales(ProductDetailsProvider productDetailsProvider, PaymentGateway paymentGateway) {
        return new Sales(
                new CartStorage(),
                productDetailsProvider,
                new OfferCalculator(productDetailsProvider),
                paymentGateway,
                new InMemoryReservationStorage()
        );
    }

    @Bean
    SessionCurrentCustomerContext currentCustomerContext(HttpSession httpSession) {
        return new SessionCurrentCustomerContext(httpSession);
    }

    @Bean
    ProductDetailsProvider createProductDetailsProvider(ProductCatalog catalog) {
        return new ProductCatalogProductDetailsProvider(catalog);
    }
}
