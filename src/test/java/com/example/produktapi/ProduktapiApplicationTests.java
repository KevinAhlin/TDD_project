package com.example.produktapi;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
class ProduktapiApplicationTests {

	// skapa seleniumtester här
	@Test
	@Disabled
	public void checkIfTitleMatchForChrome() {
		// Hämta in den webdriver som ska användas
		WebDriver chromeDriver = new ChromeDriver();

		// Navigera till den webbsida som ska testas (URL)
		chromeDriver.get("https://sti.se");

		// Testa om förväntad titel matchar webbplatsens titel
		assertEquals("STI - YH-program och vidareutbildningar inom teknik & IT",
						chromeDriver.getTitle(), "Titeln matchar inte");

		//driver.quit();
	}

	@Test
	@Disabled
	public void checkIfTitleMatchForMSEdge() {
		// Hämta in den webdriver som ska användas
		WebDriver edgeDriver = new EdgeDriver();

		// Navigera till den webbsida som ska testas
		edgeDriver.get("https://sti.se");

		// Testa om förväntad titel matchar webbplatsens titel
		assertEquals("STI - YH-program och vidareutbildningar inom teknik & IT",
						edgeDriver.getTitle(), "Titeln matchar inte");

		// edgeDriver.quit();
	}

	@Test
	@Disabled
	public void checkTitle() {
		// Hämta in den webdriver som ska användas
		WebDriver driver = new ChromeDriver();

		// Navigera till den webbsida som ska testas
		driver.get("https://java22.netlify.app/");

		// Testa om förväntad titel matchar webbplatsens titel
		assertEquals("Webbutik", driver.getTitle(), "Titeln stämmer inte med förväntat");

		driver.quit();		// När vi är klara med tester stänger drivern ned webbläsaren

	}

	@Test
	@Disabled
	public void checkH1Text() {
		// Hämta in den webdriver som ska användas
		WebDriver driver = new ChromeDriver();

		// Navigera till den webbsida som ska testas
		driver.get("https://java22.netlify.app/");

		String h1Text = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[1]/div/h1")).getText();

		assertEquals("Testdriven utveckling - projekt", h1Text, "Rubriken verkar inte stämma");

		driver.quit();

	}

	@Test
	@Disabled
	public void numberOfProductsShouldBeTwenty() {
		// Hämta in den webdriver som ska användas
		WebDriver driver = new ChromeDriver();

		// Navigera till den webbsida som ska testas
		driver.get("https://java22.netlify.app/");

		// Skapa en lista som innehåller alla produkter
		List<WebElement> products = driver.findElements(By.className("productItem"));

		// Kolla om listan har rätt antal produkter som på webbsidan
		assertEquals(20, products.size(), "Antalet produkter stämmer inte");

		driver.quit();
	}

}
