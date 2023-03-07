package com.example.produktapi;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeleniumTests {

    @Test
    public void checkTitleOfWebbutik() {
        // Hämta in den webdriver som ska användas
        WebDriver driver = new ChromeDriver();

        // Navigera till den webbsida som ska testas
        driver.get("https://java22.netlify.app/");

        // Testa om förväntad titel matchar webbplatsens titel
        assertEquals("Webbutik", driver.getTitle(), "Titeln stämmer inte");

        driver.quit();
    }

    @Test
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

    @Test
    public void checkPriceForFjallravenFoldsackNo1BackpackFits15Laptops() {
        // Hämta in den webdriver som ska användas
        WebDriver driver = new ChromeDriver();

        // Navigera till den webbsida som ska testas
        driver.get("https://java22.netlify.app/");

        // Skaffa fram elementet som priser ligger i genom xpath
        WebElement priceElement = new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.visibilityOfElementLocated
                        (By.xpath("//p[contains(text(), 'Fin väska me plats för dator')]")
                        ));

        // Ta fram priset från texten
        String descriptionAndPriceText = priceElement.getText();
        String price = descriptionAndPriceText.replaceAll("[^\\d.]","");

        // Kontrollera att priset är rätt på produkten
        assertEquals("109.95", price, "Priset är inte densamma");

        driver.quit();
    }

    @Test
    public void checkPriceForSamsung49InchCHG90144HzCurvedGamingMonitor() {
        // Hämta in den webdriver som ska användas
        WebDriver driver = new ChromeDriver();

        // Navigera till den webbsida som ska testas
        driver.get("https://java22.netlify.app/");

        // Skaffa fram elementet som priser ligger i genom xpath
        WebElement priceElement = new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.visibilityOfElementLocated
                        (By.xpath("//p[contains(text(), 'En lite böjd skär Men den funkar ändå!')]")
                        ));

        // Ta fram priset från texten
        String descriptionAndPriceText = priceElement.getText();
        String price = descriptionAndPriceText.replaceAll("[^\\d.]","");

        // Kontrollera att priset är rätt på produkten
        assertEquals("999.99", price, "Priset är inte densamma");

        driver.quit();
    }

    @Test
    public void checkPriceForMensCottonJacket() {
        // Hämta in den webdriver som ska användas
        WebDriver driver = new ChromeDriver();

        // Navigera till den webbsida som ska testas
        driver.get("https://java22.netlify.app/");

        // Skaffa fram elementet som priser ligger i genom xpath
        WebElement priceElement = new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.visibilityOfElementLocated
                        (By.xpath("//p[contains(text(), 'Mer casual än såhär blir det inte!')]")
                        ));

        // Ta fram priset från texten
        String descriptionAndPriceText = priceElement.getText();
        String price = descriptionAndPriceText.replaceAll("[^\\d.]","");

        // Kontrollera att priset är rätt på produkten
        assertEquals("15.99", price, "Priset är inte densamma");

        driver.quit();
    }

}
