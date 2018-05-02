package io.puradawid.voter.io;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Date;

import io.puradawid.voter.VoteRepository;
import io.puradawid.voter.io.dto.VoteDto;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SaveToDiskRepositoryTest {

    @Before
    @After
    public void init() {
        File database = new File("testing.db");
        if (database.exists()) {
            database.delete();
        }
    }

    @Test
    public void testSavingMethod() {
        VoteRepository repo = new SaveToDiskRepository("testing");
        repo.save(new VoteDto(new Date(), new Date(), "1", true));
    }

    @Test
    public void testSaveAndLoadMethod() {
        int votesAmount = 2;
        VoteRepository repo = new SaveToDiskRepository("testing");
        for (int i = 0; i < votesAmount; i++) {
            repo.save(new VoteDto(new Date(), new Date(), "1", true));
        }
        assertThat(repo.all().size(), is(votesAmount));
    }

    @Test
    public void testSaveAndLoadMethodWithDecorator() {
        int votesAmount = 2;
        VoteRepository repo = new InMemoryDecoratorRepository(new SaveToDiskRepository("testing"));
        for (int i = 0; i < votesAmount; i++) {
            repo.save(new VoteDto(new Date(), new Date(), "1", true));
        }
        assertThat(repo.all().size(), is(votesAmount));
    }
}

