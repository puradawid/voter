package io.puradawid.voter;

class CommitedVote implements VoteResult {

    @Override
    public String message() {
        return "OK";
    }

    @Override
    public boolean acknowledged() {
        return true;
    }
}