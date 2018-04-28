package io.puradawid.voter.integration;

import io.puradawid.voter.integration.webgui.VoterApplicationWebInterface;

class AudiencePerson implements AutoCloseable {

    private VoterApplicationWebInterface webInterface;

    AudiencePerson(String host, int port) {
        webInterface = new VoterApplicationWebInterface(port, host);
    }

    void like() {
        webInterface.openMainPage().clickLike();
    }

    void dislike() {
        webInterface.openMainPage().clickDislike();
    }

    @Override
    public void close() {
        webInterface.close();
    }
}