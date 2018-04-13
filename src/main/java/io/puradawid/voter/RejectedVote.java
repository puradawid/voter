package io.puradawid.voter;

class RejectedVote implements VoteResult {

    private final String message;

    RejectedVote(String message) {
        this.message = message;
    }

    @Override
    public String message() {
        return message;
    }

    @Override
    public boolean acknowledged() {
        return false;
    }
}