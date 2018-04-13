package io.puradawid.voter;

import java.util.Date;

class AddVote {

    private final Date clientSideRegisteredTime;
    private final boolean positive;

    AddVote(Date clientSideRegisteredTime, boolean positive) {
        this.clientSideRegisteredTime = clientSideRegisteredTime;
        this.positive = positive;
    }

    Date getClientSideRegisteredTime() {
        return clientSideRegisteredTime;
    }

    boolean isPositive() {
        return positive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AddVote addVote = (AddVote) o;

        if (positive != addVote.positive) {
            return false;
        }
        return clientSideRegisteredTime != null ? clientSideRegisteredTime
            .equals(addVote.clientSideRegisteredTime) : addVote.clientSideRegisteredTime == null;

    }

    @Override
    public int hashCode() {
        int result = clientSideRegisteredTime != null ? clientSideRegisteredTime.hashCode() : 0;
        result = 31 * result + (positive ? 1 : 0);
        return result;
    }
}