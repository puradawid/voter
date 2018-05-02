package io.puradawid.voter.integration.webgui;

import org.openqa.selenium.WebDriver;

public class MainPage {

    private final DislikeAction dislike;
    private final LikeAction like;

    MainPage(WebDriver driver) {
        dislike = new DislikeAction(driver);
        like = new LikeAction(driver);
    }

    public void clickLike() {
        like.perform();
    }

    public void clickDislike() {
        dislike.perform();
    }
}