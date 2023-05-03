package TicketsSearch;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

public class SearchPrigorodTest {
    private WebDriver driver;
    JavascriptExecutor js;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        js = (JavascriptExecutor) driver;
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void searchElectr() {
        driver.get("https://www.tutu.ru/");
        driver.manage().window().setSize(new Dimension(1000, 1000));
        driver.findElement(By.xpath("//div[@id='react-hat-container']/div/div/div/div[3]/div[2]/div/nav/li[5]/a/span[2]")).click();
        driver.findElement(By.xpath("//div[@id='wrapper']/div[2]/div/form/div/div/div/div/div[2]/span/span")).click();
        driver.findElement(By.xpath("//div[@id='wrapper']/div[2]/div/form/div/div/div[3]/div/div[2]/span/span")).click();
        driver.findElement(By.xpath("//div[@id='wrapper']/div[2]/div/form/div/div/div[5]/button/span/span[3]")).click();
        {
            List<WebElement> elements = driver.findElements(By.xpath("//div[@id='wrapper']/div[3]/div/div[2]/div/h1"));
            assert (elements.size() > 0);
        }
    }
}
