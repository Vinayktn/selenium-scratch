package com.vinaykumar.hrmauto.pages;

import com.vinaykumar.hrmauto.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ContactDetailsPage extends BasePage {

    private By mobilePhoneInput = By.xpath("//label[normalize-space()='Mobile']/parent::div/following-sibling::div/input");
    private By workEmailInput = By.xpath("//label[normalize-space()='Work Email']/parent::div/following-sibling::div/input");
    private By contactDetailsSaveButton = By.xpath("//button[normalize-space()='Save']");

    public ContactDetailsPage(WebDriver driver) {
        super(driver);
    }

    public void enterMobilePhone(String phone){
        sendText(mobilePhoneInput, phone);
    }
    public void enterWorkEmail(String email) {
        sendText(workEmailInput, email);
    }
    public void clickSave() {
        click(contactDetailsSaveButton);
    }
}
