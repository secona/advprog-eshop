package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @InjectMocks
    OrderServiceImpl orderService;

    @Mock
    OrderRepository orderRepository;

    List<Order> orders;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();

        Product product1 = new Product();
        product1.setProductId("3e8c353b-ff26-4dae-b925-fb7bd7c807f6");
        product1.setProductName("Kaoru Hana wa Rin to Saku Vol. 1");
        product1.setProductQuantity(1);

        products.add(product1);

        orders = new ArrayList<>();

        Order order1 = new Order("d4bfe9c1-c0da-4b94-bda4-7921c451ac14", products, 1708560000L, "Guts");
        Order order2 = new Order("f55e5029-9f30-43e7-87b0-7e979f392c24", products, 1708560000L, "Guts");

        orders.add(order1);
        orders.add(order2);
    }

    @Test
    void testCreateOrder() {
        Order order = orders.get(1);
        doReturn(order).when(orderRepository).save(order);

        Order result = orderService.createOrder(order);
        verify(orderRepository, times(1)).save(order);
        assertEquals(order.getId(), result.getId());
    }

    @Test
    void testCreateOrderIfAlreadyExists() {
        Order order = orders.get(1);
        doReturn(order).when(orderRepository).findById(order.getId());

        assertNull(orderService.createOrder(order));
        verify(orderRepository, times(0)).save(order);
    }

    @Test
    void testUpdateStatus() {
        Order order = orders.get(1);
        Order newOrder = new Order(order.getId(), order.getProducts(), order.getOrderTime(), order.getAuthor(), OrderStatus.SUCCESS.getValue());

        doReturn(order).when(orderRepository).findById(order.getId());
        doReturn(newOrder).when(orderRepository).save(any(Order.class));

        Order result = orderService.updateStatus(order.getId(), OrderStatus.SUCCESS.getValue());

        assertEquals(order.getId(), result.getId());
        assertEquals(OrderStatus.SUCCESS.getValue(), result.getStatus());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testUpdateStatusInvalidStatus() {
        Order order = orders.get(1);
        doReturn(order).when(orderRepository).findById(order.getId());

        assertThrows(IllegalArgumentException.class, () -> orderService.updateStatus(order.getId(), "asdf"));

        verify(orderRepository, times(0)).save(any(Order.class));
    }

    @Test
    void testUpdateStatusInvalidOrderId() {
        doReturn(null).when(orderRepository).findById("asdf");

        assertThrows(NoSuchElementException.class, () -> orderService.updateStatus("asdf", OrderStatus.SUCCESS.getValue()));
        verify(orderRepository, times(0)).save(any(Order.class));
    }

    @Test
    void testFindByIdIfIdFound() {
        Order order = orders.get(1);
        doReturn(order).when(orderRepository).findById(order.getId());

        Order result = orderService.findById(order.getId());
        assertEquals(order.getId(), result.getId());
    }

    @Test
    void testFindByIdIfIdNotFound() {
        doReturn(null).when(orderRepository).findById("asdf");

        Order result = orderService.findById("asdf");
        assertNull(result);
    }

    @Test
    void testFindAllByAuthorIfAuthorCorrect() {
        Order order = orders.get(1);
        doReturn(orders).when(orderRepository).findAllByAuthor(order.getAuthor());

        List<Order> results = orderService.findAllByAuthor(order.getAuthor());
        for (Order result : results) {
            assertEquals(order.getAuthor(), result.getAuthor());
        }
        assertEquals(2, results.size());
    }

    @Test
    void testFindAllByAuthorIfAllLowercase() {
        Order order = orders.get(1);
        doReturn(new ArrayList<>()).when(orderRepository).findAllByAuthor(order.getAuthor().toLowerCase());

        List<Order> results = orderService.findAllByAuthor(order.getAuthor().toLowerCase());
        assertTrue(results.isEmpty());
    }
}