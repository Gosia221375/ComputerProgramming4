package Gosia221375.sales.payment;

public interface PaymentGateway {
    PaymentData register(RegisterPaymentRequest request);
}
