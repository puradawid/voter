package io.puradawid.voter.io;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.puradawid.voter.VotingBuilder;
import io.puradawid.voter.VotingFacade;
import io.puradawid.voter.VotingReportFacade;

@SpringBootApplication
public class VoterApplication {

    public static void main(String[] args) {
        SpringApplication.run(VoterApplication.class, args);
    }
}
