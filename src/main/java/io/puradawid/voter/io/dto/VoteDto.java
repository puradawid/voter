package io.puradawid.voter.io.dto;

import java.util.Date;

import io.puradawid.voter.Listener;
import io.puradawid.voter.Vote;

public class VoteDto implements Vote {

    private Date arrived;
    private Date declared;
    private ListenerDto listener;
    private boolean positive;

    public VoteDto(Date arrived, Date declared, String listenerId, boolean positive) {
        this.arrived = arrived;
        this.declared = declared;
        this.listener = new ListenerDto(listenerId);
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
        return listener;
    }

    @Override
    public boolean positive() {
        return positive;
    }
}