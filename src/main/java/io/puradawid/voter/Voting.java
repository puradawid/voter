package io.puradawid.voter;

import java.util.Date;
import java.util.List;

class Voting implements VotingFacade, VotingReportFacade {

    private VoteRepository repository;

    public Voting(VoteRepository repository) {
        this.repository = repository;
    }

    @Override
    public VoteResult vote(Listener listener, Date when, boolean positive) {
        VoteRepository.SavingResult result = repository.save(new VoteData(new AddVote(when, positive), listener));
        if (result.isCommitted()) {
            return new CommitedVote();
        } else {
            return new RejectedVote(result.toString());
        }
    }

    @Override
    public VotingStatistics statistics() {
        List<Vote> events = repository.all();
        return new Statistics(events.size(), (int) events.stream().map(x -> x.voter()).distinct().count());
    }


    static class VoteData implements Vote {

        private final Date arrived;
        private final Date declared;
        private final Listener voter;
        private final boolean positive;

        VoteData(AddVote event, Listener voter) {
            this.arrived = new Date();
            this.declared = event.getClientSideRegisteredTime();
            this.voter = voter;
            this.positive = event.isPositive();
        }

        @Override
        public Date arrived() {
            return arrived;
        }

        @Override
        public Date declared() {
            return declared;
        }

        @Override
        public Listener voter() {
            return voter;
        }

        @Override
        public boolean positive() {
            return positive;

        }
    }

    private class Statistics implements VotingStatistics {

        private final int totalVotes;
        private final int numberOfPeople;

        private Statistics(int totalVotes, int numberOfPeople) {
            this.totalVotes = totalVotes;
            this.numberOfPeople = numberOfPeople;
        }

        @Override
        public int totalVotes() {
            return totalVotes;
        }

        @Override
        public int numberOfPeople() {
            return numberOfPeople;
        }
    }
}