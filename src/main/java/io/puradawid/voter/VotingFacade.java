package io.puradawid.voter;

import java.util.Date;

public interface VotingFacade {
    VoteResult vote(Listener listener, Date when, boolean positive);
}