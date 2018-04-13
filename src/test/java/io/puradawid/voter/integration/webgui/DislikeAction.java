package io.puradawid.voter.integration.webgui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

class DislikeAction {

    private final Button dislike;

    DislikeAction(WebDriver driver) {
        dislike = new Button(driver, By.cssSelector("button.dislike-it"));
    }

    void perform() {
        dislike.click();
    }

}