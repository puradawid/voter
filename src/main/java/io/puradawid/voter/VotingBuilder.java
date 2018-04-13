package io.puradawid.voter;

public class VotingBuilder {

    private VoteRepository repository;

    public VotingBuilder withAlzheimerRepository()  {
        repository = new AlzheimerRepository();
        return this;
    }

    public VotingBuilder withCustomRepository(VoteRepository repository) {
        this.repository = repository;
        return this;
    }


    public VotingReportFacade votingStatistics() {
        if (repository == null) {
            throw new IllegalStateException("Provide repository");
        }

        return new Voting(repository);
    }

    public VotingFacade votingFacade() {
        if (repository == null) {
            throw new IllegalStateException("Provide repository");
        }

        return new Voting(repository);
    }

}