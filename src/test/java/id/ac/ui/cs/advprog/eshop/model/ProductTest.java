package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {
    Product product;

    @BeforeEach
    void setup() {
        this.product = new Product();
        this.product.setProductId("c245ed51-ad89-4e6f-b50a-35293ab8d7d1");
        this.product.setProductName("Berserk Vol. 1");
        this.product.setProductQuantity(1);
    }

    @Test
    void testGetProductId() {
        assertEquals("c245ed51-ad89-4e6f-b50a-35293ab8d7d1", this.product.getProductId());
    }

    @Test
    void testGetProductName() {
        assertEquals("Berserk Vol. 1", this.product.getProductName());
    }

    @Test
    void testGetProductQuantity() {
        assertEquals(1, this.product.getProductQuantity());
    }
}
