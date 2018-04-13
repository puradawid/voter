package io.puradawid.voter;

import java.util.List;

public interface VoteRepository {

    SavingResult save(Vote vote);

    List<Vote> all();

    interface SavingResult {
        boolean isCommitted();
    }
}