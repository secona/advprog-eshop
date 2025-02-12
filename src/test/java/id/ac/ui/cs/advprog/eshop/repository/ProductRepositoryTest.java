package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductRepositoryTest {
    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setup() {}

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("c245ed51-ad89-4e6f-b50a-35293ab8d7d1");
        product.setProductName("Berserk Vol. 1");
        product.setProductQuantity(1);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());

        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("c245ed51-ad89-4e6f-b50a-35293ab8d7d1");
        product1.setProductName("Berserk Vol. 1");
        product1.setProductQuantity(1);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("8f1de06e-58ee-4b5b-85f5-6a822710f07e");
        product2.setProductName("Berserk Vol. 2");
        product2.setProductQuantity(1);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());

        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        assertEquals(product1.getProductName(), savedProduct.getProductName());
        assertEquals(product1.getProductQuantity(), savedProduct.getProductQuantity());

        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertEquals(product2.getProductName(), savedProduct.getProductName());
        assertEquals(product2.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindOneByIdFound() {
        Product product1 = new Product();
        product1.setProductId("c245ed51-ad89-4e6f-b50a-35293ab8d7d1");
        product1.setProductName("Berserk Vol. 1");
        product1.setProductQuantity(1);
        productRepository.create(product1);

        Optional<Product> found = productRepository.findOneById(product1.getProductId());

        assertTrue(found.isPresent());

        Product foundProduct = found.get();
        assertEquals(product1.getProductId(), foundProduct.getProductId());
        assertEquals(product1.getProductName(), foundProduct.getProductName());
        assertEquals(product1.getProductQuantity(), foundProduct.getProductQuantity());
    }

    @Test
    void testFindOneByIdNotFound() {
        Product product1 = new Product();
        product1.setProductId("c245ed51-ad89-4e6f-b50a-35293ab8d7d1");
        product1.setProductName("Berserk Vol. 1");
        product1.setProductQuantity(1);
        productRepository.create(product1);

        Optional<Product> found = productRepository.findOneById("c245ed51-ad89-4e6f-b50a-35293ab8d7d1");

        assertFalse(found.isPresent());
    }
}
