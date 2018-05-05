package io.puradawid.voter.io;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.puradawid.voter.VotingBuilder;
import io.puradawid.voter.VotingFacade;
import io.puradawid.voter.VotingReportFacade;

@Configuration
@Profile({"production", "dev"})
class StoreInDiskConfiguration {

    VotingBuilder builder = new VotingBuilder()
        .withCustomRepository(new InMemoryDecoratorRepository(new SaveToDiskRepository("production")));

    @Bean(name = "VotingFacade")
    VotingFacade loadVotingFacade() {
        return builder.votingFacade();
    }

    @Bean(name = "ReportFacade")
    VotingReportFacade loadReportFacade() {
        return builder.votingStatistics();
    }

}