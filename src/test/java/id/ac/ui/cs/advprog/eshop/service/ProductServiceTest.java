package id.ac.ui.cs.advprog.eshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductServiceImpl productService;

    @Test
    void testCreateProduct() {
        Product product = new Product();
        product.setProductName("Kaoru Hana wa Rin to Saku Vol. 1");
        product.setProductQuantity(1);

        when(productRepository.create(product)).thenReturn(product);

        Product savedProduct = productService.create(product);
        assertEquals(savedProduct, product);
        verify(productRepository, times(1)).create(product);
    }

    @Test
    void testUpdateById() {
        Product oldProduct = new Product();
        oldProduct.setProductId(UUID.randomUUID().toString());
        oldProduct.setProductName("Kaoru Hana wa Rin to Saku Vol. 1");
        oldProduct.setProductQuantity(1);

        Product newProduct = new Product();
        newProduct.setProductId(oldProduct.getProductId());
        newProduct.setProductName("Kaoru Hana wa Rin to Saku Vol. 2");
        newProduct.setProductQuantity(1);

        when(productRepository.update(oldProduct.getProductId(), newProduct)).thenReturn(Optional.of(newProduct));

        Optional<Product> updatedProduct = productService.updateById(oldProduct.getProductId(), newProduct);
        assertTrue(updatedProduct.isPresent());

        Product product = updatedProduct.get();
        assertEquals(product, newProduct);
        verify(productRepository, times(1)).update(oldProduct.getProductId(), newProduct);
    }

    @Test
    void testFindAll() {
        Product product1 = new Product();
        product1.setProductId(UUID.randomUUID().toString());
        product1.setProductName("Kaoru Hana wa Rin to Saku Vol. 1");
        product1.setProductQuantity(1);

        Product product2 = new Product();
        product2.setProductId(UUID.randomUUID().toString());
        product2.setProductName("Kaoru Hana wa Rin to Saku Vol. 2");
        product2.setProductQuantity(1);

        Iterator<Product> productIterator = Arrays.asList(product1, product2).iterator();

        when(productRepository.findAll()).thenReturn(productIterator);

        List<Product> productList = productService.findAll();

        assertEquals(productList.get(0), product1);
        assertEquals(productList.get(1), product2);
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindOneById() {
        Product product = new Product();
        product.setProductId(UUID.randomUUID().toString());
        product.setProductName("Kaoru Hana wa Rin to Saku Vol. 1");
        product.setProductQuantity(1);

        when(productRepository.findById(product.getProductId())).thenReturn(Optional.of(product));

        Optional<Product> savedProduct = productService.findOneById(product.getProductId());

        assertTrue(savedProduct.isPresent());
        assertEquals(savedProduct.get(), product);
        verify(productRepository, times(1)).findById(product.getProductId());
    }

    @Test
    void testDeleteById() {
        Product product = new Product();
        product.setProductId(UUID.randomUUID().toString());
        product.setProductName("Kaoru Hana wa Rin to Saku Vol. 1");
        product.setProductQuantity(1);

        productService.deleteById(product.getProductId());

        verify(productRepository, times(1)).delete(product.getProductId());
    }
}