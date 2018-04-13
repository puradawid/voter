package io.puradawid.voter.integration.webgui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

class LikeAction {

    private final Button button;

    LikeAction(WebDriver driver) {
        this.button = new Button(driver, By.cssSelector("button.like-it"));
    }

    void perform() {
        button.click();
    }

}