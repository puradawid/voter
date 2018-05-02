package io.puradawid.voter.io;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import io.puradawid.voter.Vote;
import io.puradawid.voter.VoteRepository;
import io.puradawid.voter.dto.SavingResultDto;

class InMemoryDecoratorRepository implements VoteRepository {

    private final List<Vote> inMemory = new CopyOnWriteArrayList<>();
    private final VoteRepository permanentRepo;
    private final BlockingQueue<Vote> queue = new LinkedBlockingQueue<>();

    public InMemoryDecoratorRepository(VoteRepository permanentRepo) {
        this.permanentRepo = permanentRepo;
        this.inMemory.addAll(permanentRepo.all());
        startExecution();
    }

    @Override
    public SavingResult save(Vote vote) {
        inMemory.add(vote);
        queue.add(vote);
        return new SavingResultDto(true);
    }

    @Override
    public List<Vote> all() {
        return new LinkedList<>(inMemory);
    }

    private void startExecution() {
        Executor ex = Executors.newSingleThreadExecutor();
        ex.execute(() -> {
            try {
                while (true) {
                    permanentRepo.save(queue.take());
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}