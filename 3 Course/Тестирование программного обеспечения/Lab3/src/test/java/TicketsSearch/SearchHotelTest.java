package TicketsSearch;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

public class SearchHotelTest {
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
    public void searchBus() {
        driver.get("https://www.tutu.ru/");
        driver.manage().window().setSize(new Dimension(1000, 1000));
        driver.findElement(By.xpath("//div[@id='react-hat-container']/div/div/div/div[3]/div[2]/div/nav/li[4]/a/span[2]")).click();
        driver.findElement(By.xpath("//div[@id='root']/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div/input")).click();
        driver.findElement(By.xpath("//div[@id='root']/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/span/div/div/div/div/div/div")).click();
        driver.findElement(By.xpath("//div[@id='root']/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/div/div[2]/div/div[2]/div/div/div/input")).click();
        driver.findElement(By.xpath("//div[3]/div/div[4]")).click();
        driver.findElement(By.xpath("//div[2]/div[7]")).click();
        driver.findElement(By.xpath("//div[@id='root']/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/div/div[2]/div/div[5]/div/button/div/span")).click();
        {
            List<WebElement> elements = driver.findElements(By.xpath("//div[@id='searchForm']/div/div"));
            assert (elements.size() > 0);
        }
    }
}
