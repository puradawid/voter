package io.puradawid.voter.io;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.puradawid.voter.VotingBuilder;
import io.puradawid.voter.VotingFacade;
import io.puradawid.voter.VotingReportFacade;

@Configuration
@Profile("production")
class ProductionConfig {

    VotingBuilder builder = new VotingBuilder()
        .withCustomRepository(new InMemoryDecoratorRepository(new SaveToDiskRepository("production")));

    @Bean(name = "VotingFacade")
    public VotingFacade loadVotingFacade() {
        return builder.votingFacade();
    }

    @Bean(name = "ReportFacade")
    public VotingReportFacade loadReportFacade() {
        return builder.votingStatistics();
    }

}