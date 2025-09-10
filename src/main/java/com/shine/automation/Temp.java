//package com.shine.automation.tests;
//
//import com.shine.automation.BaseTest;
//
//import java.time.Duration;
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.testng.Assert;
//import org.testng.annotations.*;
//
//public class JobSearchTest extends BaseTest {
//
//	@BeforeTest
//	public void setup() throws Exception {
//		initialize();
//	}
//
//	@Test
//	public void jobSearchAndApply() throws InterruptedException {
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//		
//		// Login
//		driver.findElement(By.xpath("//*[@id=\"__next\"]/header/div[3]/div/div/div[2]/div[1]/div/button")).click();
//		driver.findElement(By.id("id_email_login")).sendKeys(props.getProperty("email"));
//		driver.findElement(By.id("id_password")).sendKeys(props.getProperty("password"));
//		driver.findElement(By.xpath("//*[@id=\"cndidate_login_widget\"]/div[1]/form[5]/ul[1]/li[4]/div/button")).click();
//
//		System.out.println("Logged in. Title: " + driver.getTitle());
//		System.out.println("URL: " + driver.getCurrentUrl());
//
//		// Screenshot 1
//		// use TakesScreenshot and save here (optional, I can show later)
//
//		// Search Job
//
//	
//		
//
//	
//		WebElement jobTitleSpan = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Job title, skills']")));
//		jobTitleSpan.click();
//
//
//		WebElement jobTitleInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id_q")));
//		jobTitleInput.clear();
//		jobTitleInput.sendKeys(props.getProperty("jobTitle"));
//
//		WebElement locationInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("id_loc")));
//		locationInput.clear();
//		locationInput.sendKeys(props.getProperty("location"));
//	
//		WebElement expInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("searchBar_experience")));
//		expInput.click();
//		WebElement expOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@id='item-key-2']/label")));
//		expOption.click();
//		
//        driver.findElement(By.id("id_new_search_submit_button")).click();
//        Thread.sleep(4000);
////
////        // Screenshot 2
//        System.out.println("Searched job: " + props.getProperty("jobTitle"));
//        Thread.sleep(3000);
////
////        // Select second job
//        WebElement secondJobApplyBtn = driver.findElement(By.xpath("//*[@id=\"__next\"]/div[3]/div[2]/div[2]/div/div/div[2]/div[5]/div[4]/div[2]/button"));
//       
//      
//       
//        Thread.sleep(3000);
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//     
//        js.executeScript("arguments[0].scrollIntoView(false);", secondJobApplyBtn);
//        Thread.sleep(3000);
//
//        js.executeScript("window.scrollBy(0, 200);");
//        Thread.sleep(3000);
//
//
//        // Extract Job Title
//        String jobTitle = driver.findElement(By.xpath("//*[@id=\"__next\"]/div[3]/div[2]/div[2]/div/div/div[2]/div[5]/div[2]/div[1]/p")).getAttribute("title");
//      
//        // Extract Company Name
//        String companyName = driver.findElement(By.xpath("//*[@id=\"__next\"]/div[3]/div[2]/div[2]/div/div/div[2]/div[5]/div[2]/div[1]/span")).getAttribute("title");
//     
//       
//        System.out.println("Second job: " + jobTitle + " at " + companyName);
//        System.out.println("Second job button text: " + secondJobApplyBtn.getText());
//        
//        //click apply btn
//        secondJobApplyBtn.click();
//
//        Thread.sleep(4000);
////       String secondJobApplyBtnTextNow= driver.findElement(By.xpath("//*[@id=\"__next\"]/div[3]/div[2]/div[2]/div/div/div[2]/div[5]/div[4]/div[2]/button")).getText();
////       Assert.assertEquals(secondJobApplyBtnTextNow, "Applied", "Job application failed!");
////       System.out.println("Applied Successfully! Confirmation: " + secondJobApplyBtnTextNow);
//        
//        
//        // Handle popup if it appears
//     // After clicking Apply button
//        
//     // After clicking Apply button
//        try {
//            // --- CASE 2: Relocation Popup ---
//            WebElement relocationPopup = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("//div[contains(@class,'popup') or contains(@class,'modal')]//p[contains(text(),'relocate')]")
//            ));
//            System.out.println("Relocation popup detected");
//
//            WebElement yesOption = relocationPopup.findElement(By.xpath(".//label[text()='Yes']"));
//            yesOption.click();
//            System.out.println("Selected 'Yes' in relocation popup");
//
//            WebElement submitBtn = relocationPopup.findElement(By.xpath(".//button[text()='Submit']"));
//            submitBtn.click();
//            System.out.println("Submitted relocation popup");
//
//            wait.until(ExpectedConditions.textToBePresentInElement(secondJobApplyBtn, "Applied"));
//
//        } catch (Exception e1) {
//            try {
//                // --- CASE 3: Interview Assured Popup ---
//                WebElement interviewPopup = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                    By.xpath("//div[contains(@class,'interviewAssuredModalNova')]")
//                ));
//                System.out.println("Interview Assured popup detected");
//
//                // STEP 1: Close Notification popup if it appears
//                try {
//                    WebElement notificationPopup = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                        By.xpath("//div[contains(@class,'modal_modalWrapper')]")
//                    ));
//                    WebElement closeBtn = notificationPopup.findElement(By.xpath(".//button[contains(@class,'btn-close')]"));
//                    closeBtn.click();
//                    System.out.println("Closed Notification popup");
//                } catch (Exception e2) {
//                    System.out.println("No Notification popup appeared, continuing...");
//                }
//
//                // STEP 2: Click Schedule For Later
//                WebElement scheduleForLaterBtn = wait.until(ExpectedConditions.elementToBeClickable(
//                    By.xpath("//span[contains(@class,'interviewAssuredModalNova_schedule')]")
//                ));
//                scheduleForLaterBtn.click();
//                System.out.println("Clicked 'Schedule For Later'");
//
//                // STEP 3: Select radio option (4th one)
//                WebElement scheduleOption = wait.until(ExpectedConditions.elementToBeClickable(
//                    By.xpath("//*[@id='__next']//label[4]/span[2]")
//                ));
//                scheduleOption.click();
//                System.out.println("Selected schedule option (radio 4)");
//
//                // STEP 4: Submit/Confirm
//             // STEP 4: Click Save (instead of Submit/Confirm)
//                WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(
//                    By.xpath("//*[@id='__next']/div[3]/div[2]/div[2]/div/div/div[2]/div[5]/div[5]/div/div/div/div[3]/button")
//                ));
//                saveBtn.click();
//                System.out.println("Clicked Save button in Interview Assured popup");
//
//
//             // STEP 5: Click Done
//                WebElement doneBtn = wait.until(ExpectedConditions.elementToBeClickable(
//                    By.xpath("//button[normalize-space()='Done']")
//                ));
//                doneBtn.click();
//                System.out.println("Clicked Done button in Interview Assured popup");
//
//                // STEP 6: Verify Applied status
//                wait.until(ExpectedConditions.textToBePresentInElement(secondJobApplyBtn, "Applied"));
//
//            } catch (Exception e3) {
//                // --- CASE 1: Simple Apply ---
//                System.out.println("No popup appeared. Simple Apply.");
//                wait.until(ExpectedConditions.textToBePresentInElement(secondJobApplyBtn, "Applied"));
//            }
//        }
//
//        // Final assert for all 3 cases
//        String secondJobApplyBtnTextNow = secondJobApplyBtn.getText();
//        Assert.assertEquals(secondJobApplyBtnTextNow, "Applied", "Job application failed!");
//        System.out.println("Applied Successfully! Confirmation: " + secondJobApplyBtnTextNow);
//
//
//	}
//
//	@AfterTest
//	public void cleanup() {
//		tearDown();
//	}
//}
