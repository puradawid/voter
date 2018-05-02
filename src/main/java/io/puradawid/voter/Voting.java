package io.puradawid.voter;

import java.util.Date;
import java.util.List;

import io.puradawid.voter.dto.VoteDto;

class Voting implements VotingFacade, VotingReportFacade {

    private VoteRepository repository;

    public Voting(VoteRepository repository) {
        this.repository = repository;
    }

    @Override
    public VoteResult vote(Listener listener, Date when, boolean positive) {
        VoteRepository.SavingResult
            result =
            repository.save(new VoteData(new AddVote(when, positive), listener));
        if (result.isCommitted()) {
            return new CommitedVote();
        } else {
            return new RejectedVote(result.toString());
        }
    }

    @Override
    public VotingStatistics statistics() {
        List<Vote> events = repository.all();
        return new Statistics(events.size(),
            (int) events.stream().map(x -> x.voter().id()).distinct().count());
    }


    private static class VoteData extends VoteDto {
        VoteData(AddVote event, Listener voter) {
            super(new Date(), event.getClientSideRegisteredTime(), voter.id(), event.isPositive());
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