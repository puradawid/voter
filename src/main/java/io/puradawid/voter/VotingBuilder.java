package io.puradawid.voter;

public class VotingBuilder {

    private VoteRepository repository;
    private Voting build;

    public VotingBuilder withAlzheimerRepository() {
        repository = new AlzheimerRepository();
        return this;
    }

    public VotingBuilder withCustomRepository(VoteRepository repository) {
        this.repository = repository;
        return this;
    }

    public VotingReportFacade votingStatistics() {
        return build();
    }

    public VotingFacade votingFacade() {
        return build();
    }

    private Voting build() {
        if (repository == null) {
            throw new IllegalStateException("Provide repository");
        }

        if (build == null) {
            build = new Voting(repository);
        }

        return build;
    }

}