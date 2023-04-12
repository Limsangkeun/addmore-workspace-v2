package com.addmore.workspace.config;

import java.io.IOException;
import java.util.Optional;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class WebDriverConfig {

	@Value("classpath:msedgedriver.exe")
	Resource driverResource;

	private static final Logger logger = LoggerFactory.getLogger(WebDriverConfig.class);

	public WebDriverConfig() {
	}
	
	@Bean
	public WebDriver getWebDriver() throws IOException {

		EdgeOptions edgeOptions = new EdgeOptions();

		String property = System.getProperty("webdriver.edge.driver");

		if(Optional.ofNullable(property).isEmpty()) {
			logger.info("set System property for chrome driver");
			System.setProperty("webdriver.edge.driver", driverResource.getFile().getAbsolutePath());
		}
		edgeOptions.addArguments("--remote-allow-origins=*");
		edgeOptions.addArguments("user-agent=Mozilla/5.0 (Windows Phone 10.0; Android 4.2.1; Microsoft; Lumia 640 XL LTE) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Mobile Safari/537.36 Edge/12.10166");
		edgeOptions.addArguments("headless");
		edgeOptions.addArguments("Referer=https://map.kakao.com");
		return new EdgeDriver(edgeOptions);
	}
}
