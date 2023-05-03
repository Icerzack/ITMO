package Pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrainPageTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        js = (JavascriptExecutor) driver;
        vars = new HashMap<>();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void trainPage() {
        driver.get("https://www.tutu.ru/");
        driver.manage().window().setSize(new Dimension(1000, 1000));
        driver.findElement(By.xpath("//div[@id='react-hat-container']/div/div/div/div[3]/div[2]/div/nav/li[2]/a/span[2]")).click();
        vars.put("url", js.executeScript("return window.location.href"));
        assertEquals(vars.get("url").toString(), "https://www.tutu.ru/poezda/");
    }
}
