import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.*;

public class AutomationTest {
    WebDriver driver;

    @Test
    public void LetsGetCheckedChallenge() {
        driver = new ChromeDriver();

        driver.navigate().to("https://www.google.com/maps");

        driver.findElement(By.xpath("//*[@id=\"yDmH0d\"]//form")).submit();

        String searchText = "Dublin";
        WebElement searchBox = driver.findElement(By.id("searchboxinput"));
        WebElement searchButton = driver.findElement(By.id("searchbox-searchbutton"));

        searchBox.sendKeys(searchText);
        searchButton.click();

        WebDriverWait wait = new WebDriverWait(driver, 60);

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"pane\"]//h1")));
        } catch(TimeoutException ex) {
            fail("Left panel not found");
        }

        WebElement headline = driver.findElement(By.xpath("//*[@id=\"pane\"]//h1"));
        assertEquals(searchText, headline.getText(), "Headline check failed");

        WebElement directionsButton = driver.findElement(By.className("S9kvJb"));
        directionsButton.click();

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("widget-directions")));
        } catch(TimeoutException ex) {
            fail("Directions panel not found");
        }

        WebElement destination = driver.findElement(By.xpath("//div[@id=\"sb_ifc52\"]/input"));
        String destinationText = destination.getAttribute("aria-label");
        assertTrue(destinationText.contains("Dublin"));
    }

    @AfterEach
    public void teardown() {
        driver.close();
        driver.quit();
    }
}