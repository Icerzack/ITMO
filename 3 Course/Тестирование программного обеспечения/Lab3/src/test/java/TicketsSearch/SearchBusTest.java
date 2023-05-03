package TicketsSearch;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.List;

public class SearchBusTest {
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
        driver.findElement(By.xpath("//div[@id='react-hat-container']/div/div/div/div[3]/div[2]/div/nav/li[3]/a/span[2]")).click();
        driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/section/div/div/div/div/div[2]/div[1]/div[2]/span[1]")).click();
        driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/section/div/div/div/div/div[2]/div[3]/div[2]/span[1]")).click();
        driver.findElement(By.xpath("//div[@id='content']/div/div/section/div/div/div/div/div[2]/div[4]/div/span[2]")).click();
        driver.findElement(By.xpath("//div[@id='content']/div/div/section/div/div/div/div/div[2]/div[6]/div/button/div/div")).click();
        {
            List<WebElement> elements = driver.findElements(By.xpath("//*[@id=\"content\"]/div/div[1]/header/div/div/div/div[3]/div[1]/a[2]/span[2]"));
            assert (elements.size() > 0);
        }
    }
}
