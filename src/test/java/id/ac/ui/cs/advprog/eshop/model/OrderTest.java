package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {
    private List<Product> products;

    @BeforeEach
    void setup() {
        this.products = new ArrayList<>();

        Product product1 = new Product();
        product1.setProductId("3e8c353b-ff26-4dae-b925-fb7bd7c807f6");
        product1.setProductName("Kaoru Hana wa Rin to Saku Vol. 1");
        product1.setProductQuantity(1);

        Product product2 = new Product();
        product2.setProductId("87bffef1-45cf-403f-a2b7-65b766fc5729");
        product2.setProductName("Berserk Deluxe Edition Vol. 1");
        product2.setProductQuantity(1);

        this.products.add(product1);
        this.products.add(product2);
    }

    @Test
    void testCreateOrderEmptyProduct() {
        this.products.clear();

        assertThrows(IllegalArgumentException.class, () -> {
            Order order = new Order("d4bfe9c1-c0da-4b94-bda4-7921c451ac14", this.products, 1708560000L, "Guts");
        });
    }

    @Test
    void testCreateOrderDefaultStatus() {
        Order order = new Order("d4bfe9c1-c0da-4b94-bda4-7921c451ac14", this.products, 1708560000L, "Guts");

        assertSame(this.products, order.getProducts());
        assertEquals(2, order.getProducts().size());
        assertEquals("Kaoru Hana wa Rin to Saku Vol. 1", order.getProducts().get(0).getProductName());
        assertEquals("Berserk Deluxe Edition Vol. 1", order.getProducts().get(1).getProductName());

        assertEquals("d4bfe9c1-c0da-4b94-bda4-7921c451ac14", order.getId());
        assertEquals(1708560000L, order.getOrderTime());
        assertEquals("Guts", order.getAuthor());
        assertEquals("WAITING_PAYMENT", order.getStatus());
    }

    @Test
    void testCreateOrderSuccessStatus() {
        Order order = new Order("d4bfe9c1-c0da-4b94-bda4-7921c451ac14", this.products, 1708560000L, "Guts", "SUCCESS");
        assertEquals("STATUS", order.getStatus());
    }

    @Test
    void testCreateOrderInvalidStatus() {
        assertThrows(IllegalArgumentException.class, () -> {
            Order order = new Order("d4bfe9c1-c0da-4b94-bda4-7921c451ac14", this.products, 1708560000L, "Guts", "A");
        });
    }

    @Test
    void testSetStatusToCancelled() {
        Order order = new Order("d4bfe9c1-c0da-4b94-bda4-7921c451ac14", this.products, 1708560000L, "Guts");

        order.setStatus("CANCELLED");
        assertEquals("CANCELLED", order.getStatus());
    }

    @Test
    void testSetStatusToInvalidStatus() {
        Order order = new Order("d4bfe9c1-c0da-4b94-bda4-7921c451ac14", this.products, 1708560000L, "Guts");

        assertThrows(IllegalArgumentException.class, () -> order.setStatus("A"));
    }
}
