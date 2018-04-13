package io.puradawid.voter;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

class AlzheimerRepository implements VoteRepository {

    private List<Vote> votes = new LinkedList<>();

    @Override
    public SavingResult save(Vote vote) {
        votes.add(vote);
        return new Result();
    }

    @Override
    public List<Vote> all() {
        return new ArrayList<>(votes);
    }

    static class Result implements VoteRepository.SavingResult {

        @Override
        public boolean isCommitted() {
            return true;
        }
    }

    static class AlzhaimerVote implements Vote {

        private final Date arrived;
        private final Date declared;
        private final Listener voter;
        private final boolean positive;

        AlzhaimerVote(Date arrived, Date declared, Listener voter, boolean positive) {
            this.arrived = arrived;
            this.declared = declared;
            this.voter = voter;
            this.positive = positive;
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
}