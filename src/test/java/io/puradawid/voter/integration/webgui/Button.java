package io.puradawid.voter.integration.webgui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

class Button {

    private WebElement underlying;
    private final WebDriver driver;
    private final By selector;

    Button(WebDriver driver, By selector) {
        this.driver = driver;
        this.selector = selector;
    }

    void click() {
        lazyLoad().click();
    }

    private WebElement lazyLoad() {
        if (underlying == null) {
            underlying = driver.findElement(selector);
        }
        return underlying;
    }
}