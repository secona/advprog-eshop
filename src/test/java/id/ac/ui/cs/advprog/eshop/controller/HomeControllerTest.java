package id.ac.ui.cs.advprog.eshop.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testHomePageMessage() throws Exception {
        this.mockMvc
            .perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(view().name("Home"));
    }
}
