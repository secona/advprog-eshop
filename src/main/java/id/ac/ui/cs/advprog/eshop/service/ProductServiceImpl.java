package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        productRepository.create(product);
        return product;
    }

    @Override
    public List<Product> findAll() {
        Iterator<Product> productIterator = productRepository.findAll();
        List<Product> productList = new ArrayList<>();
        productIterator.forEachRemaining(productList::add);
        return productList;
    }

    @Override
    public Optional<Product> findOneById(String productId) {
        return productRepository.findOneById(productId);
    }

    @Override
    public Optional<Product> updateById(String productId, Product productData) {
        return productRepository.updateById(productId, productData);
    }

    @Override
    public void deleteById(String productId) {
        productRepository.deleteById(productId);
    }
}
