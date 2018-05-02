package io.puradawid.voter;

import io.puradawid.voter.dto.ListenerDto;

class PlainPerson extends ListenerDto {

    private static int nextId = 0;

    static PlainPerson next() {
        return new PlainPerson(Integer.toString(nextId++));
    }

    static PlainPerson of(int id) {
        return new PlainPerson(Integer.toString(id));
    }

    public PlainPerson(String id) {
        super(id);
    }
}