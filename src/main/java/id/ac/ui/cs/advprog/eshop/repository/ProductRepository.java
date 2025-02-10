package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        product.setProductId(UUID.randomUUID().toString());
        productData.add(product);
        return product;
    }

    public Optional<Product> findOneById(String productId) {
        Optional<Product> productResult = Optional.empty();

        for (Product product : productData) {
            if (product.getProductId().equals(productId)) {
                productResult = Optional.of(product);
                break;
            }
        }

        return productResult;
    }

    public Optional<Product> updateById(String productId, Product productData) {
        Optional<Product> productOptional = findOneById(productId);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setProductName(productData.getProductName());
            product.setProductQuantity(productData.getProductQuantity());

            return Optional.of(product);
        }

        return Optional.empty();
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }
}
