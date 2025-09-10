//package com.shine.automation;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.time.Duration;
//import java.util.Properties;
//
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import io.github.bonigarcia.wdm.WebDriverManager;
//
//public class BaseTest {
//    public static WebDriver driver;
//    public static Properties props;
//
//    public void initialize() throws IOException {
//        props = new Properties();
//        FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
//        props.load(fis);
//
//        WebDriverManager.chromedriver().setup();
//        driver = new ChromeDriver();
//
//        driver.manage().window().maximize();
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//
//        driver.get(props.getProperty("baseUrl"));
//    }
//
//    public void tearDown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }
//}
