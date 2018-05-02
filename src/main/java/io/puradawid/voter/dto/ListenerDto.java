package io.puradawid.voter.dto;

import io.puradawid.voter.Listener;

public class ListenerDto implements Listener {

    private final String id;

    public ListenerDto(String id) {
        this.id = id;
    }

    @Override
    public String id() {
        return id;
    }
}