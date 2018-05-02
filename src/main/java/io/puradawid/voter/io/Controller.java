package io.puradawid.voter.io;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

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

    @RequestMapping(method = RequestMethod.POST, path = "/vote/like")
    void like() {
        voting.vote(loadListener(), new Date(), true);
        System.out.println("Like it!");
    }

    @RequestMapping(method = RequestMethod.POST, path = "/vote/dislike")
    void dislike() {
        voting.vote(loadListener(), new Date(), false);
        System.out.println("Dislike it!");
    }

    private Listener loadListener() {
        return new SinglePerson(RequestContextHolder.currentRequestAttributes().getSessionId());
    }

}

class SinglePerson implements Listener {

    private final String id;

    public SinglePerson(String id) {
        this.id = id;
    }

    @Override
    public String id() {
        return id;
    }
}