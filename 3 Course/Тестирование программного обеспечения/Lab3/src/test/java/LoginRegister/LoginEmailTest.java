package LoginRegister;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

public class LoginEmailTest {
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
    public void login() {
        driver.get("https://www.tutu.ru/");
        driver.manage().window().setSize(new Dimension(1000, 1000));
        driver.findElement(By.xpath("//div[@id='react-hat-container']/div/div/div/div[2]/div/div/noindex/div")).click();
        driver.findElement(By.xpath("//input[@name='email']")).click();
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("YOUR EMAIL");
        driver.findElement(By.xpath("//input[@name='password']")).click();
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("YOUR PASS");
        driver.findElement(By.xpath("//button/div/div")).click();
        {
            List<WebElement> elements = driver.findElements(By.xpath("//div[@id='react-hat-container']/div/div/div/div[2]/div/div/a[2]"));
            assert (elements.size() > 0);
        }
    }
}
