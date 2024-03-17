import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {


    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {

            Set<String> runnersId = Runner.getAllRunnersIDs();
            executorService.submit(() -> {
                try {
                    for (String runnerId : runnersId) {
                        System.out.println(Runner.getRunnerById(runnerId).getName());
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException();
                }
            });
        }
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println(totalTime + "ms");

        long startTime1 = System.currentTimeMillis();
        ConcurrentMap<Integer, Runner> runnersMap = new ConcurrentHashMap<>();
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {

            Set<String> runnersId = Runner.getAllRunnersIDs();
            executorService.submit(() -> {
                try {
                    AtomicInteger atInt = new AtomicInteger(0);
                    for (String runnerId : runnersId) {
                        Runner.getRunnerById(runnerId).setStartingNumber(atInt.incrementAndGet());
                        runnersMap.put(Runner.getRunnerById(runnerId).getStartingNumber(), Runner.getRunnerById(runnerId));
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException();
                }
            });
        }

        for (var entry : runnersMap.entrySet()) {
            System.out.println(entry.getKey() + "- " + entry.getValue());
        }
        long endTime1 = System.currentTimeMillis();
        long totalTime1 = endTime1 - startTime1;
        System.out.println(totalTime1 + " ms");
    }


}

