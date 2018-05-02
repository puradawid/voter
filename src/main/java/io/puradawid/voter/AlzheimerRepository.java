package io.puradawid.voter;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

class AlzheimerRepository implements VoteRepository {

    private final List<Vote> votes;

    public AlzheimerRepository() {
        this.votes = new LinkedList<>();
    }

    @Override
    public SavingResult save(Vote vote) {
        votes.add(vote);
        return new Result();
    }

    @Override
    public List<Vote> all() {
        return new ArrayList<>(votes);
    }

    static class Result implements SavingResult {

        @Override
        public boolean isCommitted() {
            return true;
        }
    }

}