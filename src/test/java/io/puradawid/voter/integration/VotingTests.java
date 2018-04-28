package io.puradawid.voter.integration;

import com.dyngr.Poller;
import com.dyngr.PollerBuilder;
import com.dyngr.core.AttemptResults;
import com.dyngr.core.WaitStrategies;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.ExecutionException;

import io.puradawid.voter.VotingReportFacade;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class VotingTests {

    Setup testing = new Setup();

    @Test
    public void testVoting() throws InterruptedException {
        int port = new Random().nextInt(8999) + 1000;
        testing.withRunningApplication(ctx -> {
            try (AudiencePerson person = new AudiencePerson("localhost", port)) {
                person.like();
                VotingReportFacade report = ctx.getBean("ReportFacade", VotingReportFacade.class);
                assertThat(getTotalVotes(report), is(1));
            }
        }, port);
    }

    @Test
    public void testDislikeVoting() throws InterruptedException, ExecutionException {
        int port = new Random().nextInt(8999) + 1000;
        testing.withRunningApplication(ctx -> {
            try (AudiencePerson person = new AudiencePerson("localhost", port)) {
                person.dislike();
                VotingReportFacade report = ctx.getBean("ReportFacade", VotingReportFacade.class);
                assertThat(getTotalVotes(report), is(1));
            }
        }, port);
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