package io.puradawid.voter;

import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class VotingTest {

    @Test
    public void testLikeScenario() {
        // Given a listener is identifiable
        Listener identifiableListener = PlainPerson.next();
        RecordingRepository rr = new RecordingRepository();
        VotingFacade facade = new VotingBuilder().withCustomRepository(rr).votingFacade();
        // When listener likes the presentation at the moment
        facade.vote(identifiableListener, new Date(), true);
        // Then this information is getting stored through repository
        assertThat(rr.vote, is(notNullValue()));
        assertThat(rr.vote.positive(), is(true));
    }

    @Test
    public void testDislikeScenario() {
        // Given a listener is identifiable
        Listener identifiableListener = PlainPerson.next();
        RecordingRepository rr = new RecordingRepository();
        VotingFacade facade = new VotingBuilder().withCustomRepository(rr).votingFacade();
        // When listener doesn't like the presentation at the moment
        facade.vote(identifiableListener, new Date(), false);
        // Then this information is getting stored through repository
        assertThat(rr.vote, is(notNullValue()));
        assertThat(rr.vote.positive(), is(false));
    }

    @Test
    public void testReadingStatsWithoutVoting() {
        // Given there is none listeners on presentation
        VotingBuilder builder = new VotingBuilder().withAlzheimerRepository();
        VotingReportFacade reportFacade = builder.votingStatistics();
        VotingFacade voting = builder.votingFacade();

        // When noone vote anything

        // Then statistics should be 0 vote and 0 people total
        assertThat(reportFacade.statistics().numberOfPeople(), is(0));
        assertThat(reportFacade.statistics().totalVotes(), is(0));
    }

    @Test
    public void testReadingStatsWithOnePositiveVote() {
        // Given there is just one listener on presentation
        Listener listener = PlainPerson.next();
        VotingBuilder builder = new VotingBuilder().withAlzheimerRepository();
        VotingReportFacade reportFacade = builder.votingStatistics();
        VotingFacade voting = builder.votingFacade();

        // And he/she just posted one like
        voting.vote(listener, new Date(), true);

        // Then statistics should be 1 vote total
        assertThat(reportFacade.statistics().numberOfPeople(), is(1));
        assertThat(reportFacade.statistics().totalVotes(), is(1));
    }

    @Test
    public void testReadingStatsWithTwoPositiveVotesFromOnePerson() {
        // Given there is just one listener on presentation
        Listener listener = PlainPerson.next();
        VotingBuilder builder = new VotingBuilder().withAlzheimerRepository();
        VotingReportFacade reportFacade = builder.votingStatistics();
        VotingFacade voting = builder.votingFacade();

        // And he/she just posted two likes
        voting.vote(listener, new Date(), true);
        voting.vote(listener, new Date(), true);

        // Then statistics should be 2 vote total and one person attending
        assertThat(reportFacade.statistics().numberOfPeople(), is(1));
        assertThat(reportFacade.statistics().totalVotes(), is(2));
    }

    static class RecordingRepository implements VoteRepository {

        private Vote vote;

        @Override
        public SavingResult save(Vote vote) {
            this.vote = vote;
            return () -> true;
        }

        @Override
        public List<Vote> all() {
            return Lists.newArrayList(vote);
        }
    }
}