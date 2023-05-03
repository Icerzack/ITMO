package TicketsSearch;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

public class SearchTrainsTest {
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
    public void searchTrains() {
        driver.get("https://www.tutu.ru/");
        driver.manage().window().setSize(new Dimension(1000, 1000));
        driver.findElement(By.xpath("//div[@id='react-hat-container']/div/div/div/div[3]/div[2]/div/nav/li[2]/a/span[2]")).click();
        driver.findElement(By.xpath("//div[@id='wrapper']/div[3]/div/form/div/div/div/div/div/div/input")).click();
        driver.findElement(By.xpath("//div[@id='wrapper']/div[3]/div/form/div/div/div/div/div/div/input")).sendKeys("Пукса");
        driver.findElement(By.xpath("//div[@id='wrapper']/div[3]/div/form/div/div/div[3]/div/div/div/input")).click();
        driver.findElement(By.xpath("//div[@id='wrapper']/div[3]/div/form/div/div/div[3]/div/div/div/input")).sendKeys("Гонжа");
        driver.findElement(By.xpath("//div[@id='wrapper']/div[3]/div/form/div/div/div[4]/div/div/div/input")).click();
        driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/table/tbody/tr/td[4]/a")).click();
        driver.findElement(By.xpath("//div[@id='wrapper']/div[3]/div/form/div/div/div[6]/button/span/span[3]")).click();
        {
            List<WebElement> elements = driver.findElements(By.xpath("//div[@id='root']/div/div[2]/div/div/div"));
            assert (elements.size() > 0);
        }
    }
}
