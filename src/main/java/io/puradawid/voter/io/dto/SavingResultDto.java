package io.puradawid.voter.io.dto;

import io.puradawid.voter.VoteRepository;

public class SavingResultDto implements VoteRepository.SavingResult {

    private final boolean committed;

    public SavingResultDto(boolean committed) {
        this.committed = committed;
    }

    @Override
    public boolean isCommitted() {
        return committed;
    }
}