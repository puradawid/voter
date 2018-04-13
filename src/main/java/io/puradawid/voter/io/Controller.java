package io.puradawid.voter.io;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import io.puradawid.voter.Listener;
import io.puradawid.voter.VotingFacade;

@RestController
class Controller {

    private final VotingFacade voting;

    @Autowired
    Controller(VotingFacade voting) {
        this.voting = voting;
    }

    private Listener listener = new SinglePerson();

    @RequestMapping(method = RequestMethod.POST, path = "/vote/like")
    void like() {
        voting.vote(listener, new Date(), true);
        System.out.println("Like it!");
    }

    @RequestMapping(method = RequestMethod.POST, path = "/vote/dislike")
    void dislike() {
        voting.vote(listener, new Date(), false);
        System.out.println("Dislike it!");
    }

}

class SinglePerson implements Listener {

    @Override
    public String id() {
        return "single person";
    }
}