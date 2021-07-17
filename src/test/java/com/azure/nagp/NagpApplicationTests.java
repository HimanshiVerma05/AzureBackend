package com.azure.nagp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NagpApplicationTests {
	public WebDriver driver;

	@BeforeMethod
	public void setup() {
		String hostname = System.getProperty("hostname");
		String port = System.getProperty("port");
		String context = System.getProperty("context");
		driver = new HtmlUnitDriver();
		driver.get("http://" + hostname + ":" + port + "/" + context);
	}
	// Test to pass as to verify listeners .
		@Test
		public void VerifyTitle() {
			String hostname = System.getProperty("hostname");
			String port = System.getProperty("port");
			String context = System.getProperty("context");
			String completeAddress = "http://" + hostname + ":" + port + "/" + context;
			String welcometext = driver.getTitle();
			Assert.assertEquals(welcometext, "DevOps Tool", "Text not found");
		}

}
