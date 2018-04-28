package io.puradawid.voter.integration.webgui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class VoterApplicationWebInterface implements AutoCloseable {

    private final int port;
    private final String host;

    private final WebDriver driver;

    public VoterApplicationWebInterface(int port, String host) {
        this.port = port;
        this.host = host;
        this.driver = constructDriver();
    }

    private ChromeDriver constructDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless", "disable-gpu", "window-size=1920x1080");
        return new ChromeDriver(options);
    }

    public MainPage openMainPage() {
        driver.manage().window().fullscreen();
        driver.navigate().to("http://" + host + ":" + port);
        return new MainPage(driver);
    }

    @Override
    public void close() {
        driver.close();
    }

}