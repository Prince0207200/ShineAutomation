package com.shine.automation.tests;

import com.shine.automation.BaseTest;

import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

public class JobSearchTest extends BaseTest {

    @BeforeTest
    public void setup() throws Exception {
        initialize();
    }

    @Test
    public void jobSearchAndApply() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // ---------- LOGIN ----------
        driver.findElement(By.xpath("//*[@id=\"__next\"]/header/div[3]/div/div/div[2]/div[1]/div/button")).click();
        driver.findElement(By.id("id_email_login")).sendKeys(props.getProperty("email"));
        driver.findElement(By.id("id_password")).sendKeys(props.getProperty("password"));
        driver.findElement(By.xpath("//*[@id=\"cndidate_login_widget\"]/div[1]/form[5]/ul[1]/li[4]/div/button")).click();

        System.out.println("Logged in. Title: " + driver.getTitle());
        System.out.println("URL: " + driver.getCurrentUrl());
        captureScreenshot("AfterLogin");

        // ---------- SEARCH ----------
        WebElement jobTitleSpan = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Job title, skills']")));
        jobTitleSpan.click();

        WebElement jobTitleInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id_q")));
        jobTitleInput.clear();
        jobTitleInput.sendKeys(props.getProperty("jobTitle"));

        WebElement locationInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("id_loc")));
        locationInput.clear();
        locationInput.sendKeys(props.getProperty("location"));

        WebElement expInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("searchBar_experience")));
        expInput.click();
        WebElement expOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@id='item-key-2']/label")));
        expOption.click();

        driver.findElement(By.id("id_new_search_submit_button")).click();
        Thread.sleep(4000);

        System.out.println("Searched job: " + props.getProperty("jobTitle"));
        captureScreenshot("AfterSearch");

        // ---------- SELECT SECOND JOB ----------
        WebElement secondJobApplyBtn = driver.findElement(
            By.xpath("//*[@id=\"__next\"]/div[3]/div[2]/div[2]/div/div/div[2]/div[5]/div[4]/div[2]/button")
        );

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(false);", secondJobApplyBtn);
        Thread.sleep(3000);
        js.executeScript("window.scrollBy(0, 200);");
        Thread.sleep(3000);

        String jobTitle = driver.findElement(
            By.xpath("//*[@id=\"__next\"]/div[3]/div[2]/div[2]/div/div/div[2]/div[5]/div[2]/div[1]/p")
        ).getAttribute("title");

        String companyName = driver.findElement(
            By.xpath("//*[@id=\"__next\"]/div[3]/div[2]/div[2]/div/div/div[2]/div[5]/div[2]/div[1]/span")
        ).getAttribute("title");

        System.out.println("Second job: " + jobTitle + " at " + companyName);
        System.out.println("Second job button text: " + secondJobApplyBtn.getText());
        captureScreenshot("SecondJob");

        // ---------- CLICK APPLY ----------
        secondJobApplyBtn.click();
        Thread.sleep(4000);
        

        // ---------- HANDLE POPUPS ----------
        if (handleRelocationPopup(wait)) {
            System.out.println("Handled Case 2: Relocation Popup");

        } else if (handleInterviewAssuredPopup(wait)) {
            System.out.println("Handled Case 3: Interview Assured Popup");

            // 🔹 Check for recruiter details after interview popup (Case 4)
            if (handleRecruiterDetailsPopup(wait)) {
                System.out.println("Handled Case 4: Recruiter Additional Details Popup");
            }

        } else {
            System.out.println("Case 1: Simple Apply, no popup appeared");
            wait.until(ExpectedConditions.textToBePresentInElement(secondJobApplyBtn, "Applied"));
        }

     // ---------- FINAL ASSERT WITH WAIT ----------
        wait.until(ExpectedConditions.textToBePresentInElement(secondJobApplyBtn, "Applied"));
        captureScreenshot("AfterApply");
        String secondJobApplyBtnTextNow = secondJobApplyBtn.getText().trim();
        Assert.assertEquals(secondJobApplyBtnTextNow, "Applied", "Job application failed!");
        System.out.println("Applied Successfully! Confirmation: " + secondJobApplyBtnTextNow);

    }

    // -------------------- PRIVATE METHODS --------------------

    // Case 2 → Relocation
    private boolean handleRelocationPopup(WebDriverWait wait) {
        try {
            WebElement relocationPopup = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class,'popup') or contains(@class,'modal')]//p[contains(text(),'relocate')]")
            ));
            System.out.println("Relocation popup detected");

            WebElement yesOption = relocationPopup.findElement(By.xpath(".//label[text()='Yes']"));
            yesOption.click();
            System.out.println("Selected 'Yes' in relocation popup");

            WebElement submitBtn = relocationPopup.findElement(By.xpath(".//button[text()='Submit']"));
            submitBtn.click();
            System.out.println("Submitted relocation popup");

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Case 3 → Interview Assured
    private boolean handleInterviewAssuredPopup(WebDriverWait wait) {
        try {
            WebElement interviewPopup = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class,'interviewAssuredModalNova')]")
            ));
            System.out.println("Interview Assured popup detected");

            // Close notification popup if appears
            try {
                WebElement notificationPopup = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(@class,'modal_modalWrapper')]")
                ));
                WebElement closeBtn = notificationPopup.findElement(By.xpath(".//button[contains(@class,'btn-close')]"));
                closeBtn.click();
                System.out.println("Closed Notification popup");
            } catch (Exception e) {
                System.out.println("No Notification popup appeared, continuing...");
            }

            WebElement scheduleForLaterBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[contains(@class,'interviewAssuredModalNova_schedule')]")
            ));
            scheduleForLaterBtn.click();
            System.out.println("Clicked 'Schedule For Later'");

            WebElement scheduleOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='__next']//label[4]/span[2]")
            ));
            scheduleOption.click();
            System.out.println("Selected schedule option (radio 4)");

            WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='__next']/div[3]/div[2]/div[2]/div/div/div[2]/div[5]/div[5]/div/div/div/div[3]/button")
            ));
            saveBtn.click();
            System.out.println("Clicked Save button");

            WebElement doneBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[normalize-space()='Done']")
            ));
            doneBtn.click();
            System.out.println("Clicked Done button");

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Case 4 → Recruiter Additional Details (CTC + Notice Period)
    private boolean handleRecruiterDetailsPopup(WebDriverWait wait) {
        try {
            WebElement ctcDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='q3_2_0']")));
            Select ctcSelect = new Select(ctcDropdown);
            ctcSelect.selectByVisibleText("Rs 5 - 6 Lakh / Yr");
            System.out.println("Selected CTC value");

            WebElement noticeDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='q4_2_0']")));
            Select noticeSelect = new Select(noticeDropdown);
            noticeSelect.selectByVisibleText("1 month");
            System.out.println("Selected Notice Period");

            WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='modalID']/div/div/div/div/form/div/button")
            ));
            submitBtn.click();
            System.out.println("Submitted Recruiter Details popup");

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @AfterTest
    public void cleanup() {
        tearDown();
    }
}
