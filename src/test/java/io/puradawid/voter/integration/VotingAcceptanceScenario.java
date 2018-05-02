package io.puradawid.voter.integration;

import com.dyngr.Poller;
import com.dyngr.PollerBuilder;
import com.dyngr.core.AttemptResults;
import com.dyngr.core.StopStrategies;
import com.dyngr.core.WaitStrategies;

import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

import io.puradawid.voter.VotingReportFacade;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class VotingAcceptanceScenario {

    Setup testing = new Setup();

    @Test
    public void testVoting() throws InterruptedException {
        testing.withRunningApplication((ctx, port) -> {
            withNewPerson(liker ->
                withNewPerson(hater -> {
                    liker.like();
                    hater.dislike();
                    liker.like();
                    hater.dislike();
                    assertThat(totalVotes(ctx, 4), is(4));
                    assertThat(numberOfPeople(ctx, 2), is(2));
                }, port), port);
        });
    }

    private int totalVotes(ConfigurableApplicationContext ctx, int expected) {
        VotingReportFacade report = ctx.getBean("ReportFacade", VotingReportFacade.class);
        return pollManyTimesFor(() -> report.statistics().totalVotes(), expected);
    }

    private int numberOfPeople(ConfigurableApplicationContext ctx, int expected) {
        VotingReportFacade report = ctx.getBean("ReportFacade", VotingReportFacade.class);
        return pollManyTimesFor(() -> report.statistics().numberOfPeople(), expected);
    }

    private <T> T pollManyTimesFor(Supplier<T> supplier, T expected) {
        Poller<T> poller = PollerBuilder.<T>newBuilder()
            .withWaitStrategy(WaitStrategies.fibonacciWait())
            .withStopStrategy(StopStrategies.stopAfterDelay(2, TimeUnit.SECONDS))
            .polling(
                () -> {
                    T value = supplier.get();
                    if (value.equals(expected)) {
                        return AttemptResults.finishWith(value);
                    } else {
                        return AttemptResults.justContinue();
                    }
                }
            ).build();
        try {
            return poller.start().get();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        } catch (ExecutionException ex) {
            return supplier.get();
        }
    }

    private void withNewPerson(Consumer<AudiencePerson> consumer, int port) {
        try (AudiencePerson person = new AudiencePerson("localhost", port)) {
            consumer.accept(person);
        }
    }
}