package io.puradawid.voter.integration;

import com.dyngr.Poller;
import com.dyngr.PollerBuilder;
import com.dyngr.core.AttemptResults;
import com.dyngr.core.WaitStrategies;

import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Random;
import java.util.concurrent.ExecutionException;

import io.puradawid.voter.VotingReportFacade;
import io.puradawid.voter.integration.webgui.MainPage;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class VotingTests {

    Setup testing = new Setup();

    ChromeDriver driver = constructDriver();

    @Test
    public void testVoting() throws InterruptedException {
        int port = new Random().nextInt(8999) + 1000;
        testing.withRunningApplication(ctx -> {
            try {
                driver.manage().window().fullscreen();
                driver.navigate().to("http://localhost:" + port);
                MainPage page = new MainPage(driver);
                page.clickLike();
                VotingReportFacade report = ctx.getBean("ReportFacade", VotingReportFacade.class);
                assertThat(getTotalVotes(report), is(1));
            } finally {
                driver.close();
            }
        }, port);
    }

    @Test
    public void testDislikeVoting() throws InterruptedException, ExecutionException {
        int port = new Random().nextInt(8999) + 1000;
        testing.withRunningApplication(ctx -> {
            try {
                driver.manage().window().fullscreen();
                driver.navigate().to("http://localhost:" + port);
                MainPage page = new MainPage(driver);
                page.clickDislike();
                VotingReportFacade report = ctx.getBean("ReportFacade", VotingReportFacade.class);
                assertThat(getTotalVotes(report), is(1));
            } finally {
                driver.close();
            }
        }, port);
    }

    private ChromeDriver constructDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless", "disable-gpu", "window-size=1920x1080");
        return new ChromeDriver(options);
    }

    private int getTotalVotes(VotingReportFacade report) {
        Poller<Integer> poller = PollerBuilder.<Integer>newBuilder()
            .withWaitStrategy(WaitStrategies.fibonacciWait())
            .polling(
                () -> {
                    int totalVotes = report.statistics().totalVotes();
                    if (totalVotes > 0) {
                        return AttemptResults.finishWith(totalVotes);
                    } else {
                        return AttemptResults.justContinue();
                    }
                }
            ).build();
        try {
            return poller.start().get();
        } catch (InterruptedException | ExecutionException ex) {
            return 0;
        }
    }
}