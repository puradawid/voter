package io.puradawid.voter;

class PlainPerson implements Listener {

    private static int nextId = 0;

    static PlainPerson next() {
        return new PlainPerson(Integer.toString(nextId++));
    }

    static PlainPerson of(int id) {
        return new PlainPerson(Integer.toString(id));
    }

    private final String id;

    public PlainPerson(String id) {
        this.id = id;
    }

    @Override
    public String id() {
        return id;
    }
}