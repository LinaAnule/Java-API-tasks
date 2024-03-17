import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ImmutabilityTask {

    public static void main(String[] args) throws InterruptedException {

        long startTime = System.currentTimeMillis();
        ConcurrentMap<Integer, Runner> runnersMap = new ConcurrentHashMap<>();
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {

            Set<String> runnersId = Runner.getAllRunnersIDs();
            executorService.submit(() -> {
                try {
                    AtomicInteger atInt = new AtomicInteger(0);
                    for (String runnerId : runnersId) {

                        Runner nwRunner = Runner.Builder.builder()
                                .withName(Runner.getRunnerById(runnerId).getName())
                                .withPersonalId(runnerId)
                                .withStartingNumber(atInt.incrementAndGet()).build();

                        runnersMap.put(nwRunner.getStartingNumber(), nwRunner);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException();
                }
            });
        }

        for (var entry : runnersMap.entrySet()) {
            System.out.println(entry.getKey() + "- " + entry.getValue());
        }
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println(totalTime + " ms");

    }
}
