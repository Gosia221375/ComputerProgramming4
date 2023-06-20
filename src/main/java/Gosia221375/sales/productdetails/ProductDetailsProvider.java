package Gosia221375.sales.productdetails;

import Gosia221375.sales.productdetails.ProductDetails;

import java.util.Optional;

public interface ProductDetailsProvider {
    public Optional<ProductDetails> load(String productId);
}
