package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    public Product create(Product product);
    public List<Product> findAll();
    public Optional<Product> findOneById(String productId);
    public Optional<Product> updateById(String productId, Product productData);
    public void deleteById(String productId);
}
