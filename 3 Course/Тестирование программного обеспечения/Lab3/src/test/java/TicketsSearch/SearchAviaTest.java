package TicketsSearch;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

public class SearchAviaTest {
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
    public void searchAvia() {
        driver.get("https://www.tutu.ru/");
        driver.manage().window().setSize(new Dimension(1000, 1000));
        driver.findElement(By.xpath("//div[@id='react-hat-container']/div/div/div/div[3]/div[2]/div/nav/li/a/span[2]")).click();
        driver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div[1]/div[2]/div/div/div/div/div/div[1]/div/div[1]/div[1]/span/span[1]")).click();
//        driver.findElement(By.xpath("//div[@id='root']/div[2]/div/div[2]/div/div/div/div/div/div/div/div/div/div/div/span/div/div/input")).sendKeys("Москва");
        driver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div[1]/div[2]/div/div/div/div/div/div[1]/div/div[1]/div[2]/span/span[1]")).click();
//        driver.findElement(By.xpath("//div[@id='root']/div[2]/div/div[2]/div/div/div/div/div/div/div/div/div[2]/div/div/span/div/div/input")).sendKeys("Санкт-Петербург");
        driver.findElement(By.xpath("//div[@id='root']/div[2]/div/div[2]/div/div/div/div/div/div/div/div[2]/div/div/div/div/input")).click();
        driver.findElement(By.xpath("//div[3]/div/div[4]")).click();
        driver.findElement(By.xpath("//div[@id='root']/div[2]/div/div[2]/div/div/div/div/div/div/div/div[2]/div[2]/div/div/div/input")).click();
        driver.findElement(By.xpath("//div[3]/div/div[7]")).click();
        driver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div[1]/div[2]/div/div/div/div/div/div[2]/div[2]/div/button")).click();
        {
            List<WebElement> elements = driver.findElements(By.xpath("//*[@id=\"root\"]/div/div[1]/div/div/div[2]/div[1]/div[1]/a[2]/span/span"));
            assert (elements.size() > 0);
        }
    }
}
