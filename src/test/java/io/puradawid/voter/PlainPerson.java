package io.puradawid.voter;

class PlainPerson implements Listener {

    private static int nextId = 0;

    static PlainPerson next() {
        return new PlainPerson(Integer.toString(nextId++));
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