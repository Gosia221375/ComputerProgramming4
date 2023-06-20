package Gosia221375.sales.productdetails;

import pl.jGosia221375.sales.productdetails.ProductDetails;

import java.util.Optional;

public interface ProductDetailsProvider {
    public Optional<ProductDetails> load(String productId);
}
