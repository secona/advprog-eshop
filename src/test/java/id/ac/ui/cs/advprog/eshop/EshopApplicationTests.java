package id.ac.ui.cs.advprog.eshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EshopApplicationTests {
	@Test
	void contextLoads() {
	}

	@Test
	void testMainMethodWorks() {
		// This is to test whether the Application errors or not.
		EshopApplication.main(new String[]{});
	}
}
