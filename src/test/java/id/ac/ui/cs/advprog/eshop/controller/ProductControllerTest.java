package id.ac.ui.cs.advprog.eshop.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Test
    void testProductListPage() throws Exception {
        Product product1 = new Product();
        product1.setProductId(UUID.randomUUID().toString());
        product1.setProductName("Kaoru Hana wa Rin to Saku Vol. 1");
        product1.setProductQuantity(1);

        Product product2 = new Product();
        product2.setProductId(UUID.randomUUID().toString());
        product2.setProductName("Kaoru Hana wa Rin to Saku Vol. 2");
        product2.setProductQuantity(1);

        List<Product> productList = Arrays.asList(product1, product2);

        when(productService.findAll()).thenReturn(productList);

        this.mockMvc
            .perform(get("/product/list"))
            .andExpect(status().isOk())
            .andExpect(model().attribute("products", productList))
            .andExpect(view().name("ListProduct"));
    }

    @Test
    void testCreateProductPage() throws Exception {
        MvcResult result = this.mockMvc
            .perform(get("/product/create"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("product"))
            .andExpect(view().name("CreateProduct"))
            .andReturn();

        ModelAndView modelAndView = result.getModelAndView();
        assertTrue(modelAndView.getModel().get("product") instanceof Product);
    }

    @Test
    void testCreateProductPost() throws Exception {
        this.mockMvc
            .perform(
                post("/product/create")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("productName", "Kaoru Hana wa Rin to Saku Vol. 1")
                    .param("productQuantity", "1")
            )
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("list"));

        // Get the product being created
        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productService, times(1)).create(productCaptor.capture());

        // Test the created product
        Product capturedProduct = productCaptor.getValue();
        assertEquals("Kaoru Hana wa Rin to Saku Vol. 1", capturedProduct.getProductName());
        assertEquals(1, capturedProduct.getProductQuantity());
    }

    @Test
    void testUpdateProductPost() throws Exception {
        Product product = new Product();
        product.setProductId(UUID.randomUUID().toString());
        product.setProductName("Kaoru Hana wa Rin to Saku Vol. 1");
        product.setProductQuantity(1);

        this.mockMvc
            .perform(
                post("/product/edit/" + product.getProductId())
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("productName", "Kaoru Hana wa Rin to Saku Vol. 2")
                    .param("productQuantity", "2")
            )
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("../list"));

        // Get the product being created
        ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productService, times(1)).updateById(idCaptor.capture(), productCaptor.capture());

        // Test the updated product
        Product capturedProduct = productCaptor.getValue();
        String capturedId = idCaptor.getValue();
        assertEquals(product.getProductId(), capturedId);
        assertEquals("Kaoru Hana wa Rin to Saku Vol. 2", capturedProduct.getProductName());
        assertEquals(2, capturedProduct.getProductQuantity());
    }

    @Test
    void testDeleteProductPost() throws Exception {
        String productId = UUID.randomUUID().toString();

        this.mockMvc
            .perform(post("/product/delete/" + productId))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("../list"));

        verify(productService, times(1)).deleteById(productId);
    }
}
