package io.puradawid.voter.io.dto;

import io.puradawid.voter.Listener;

public class ListenerDto implements Listener {

    private String id;

    ListenerDto(String id) {
        this.id = id;
    }

    @Override
    public String id() {
        return id;
    }
}