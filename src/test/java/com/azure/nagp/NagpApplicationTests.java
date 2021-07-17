package com.azure.nagp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class NagpApplicationTests {
	
	@Autowired
	UserService userService;
	// Test to pass as to verify listeners .
		@Test
		public void verifyUserDetails() {
			
			String name = userService.getName();
			assertEquals("Himanshi",name);
			assertNotNull(name);
		}

}
