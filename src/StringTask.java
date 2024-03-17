import java.awt.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class StringTask {

    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        CopyOnWriteArrayList<Runner> runners = new CopyOnWriteArrayList<>();
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {

            Set<String> runnersId = Runner.getAllRunnersIDs();
            executorService.submit(() -> {
                try {
                    AtomicInteger atInt = new AtomicInteger(0);
                    for (String runnerId : runnersId) {
                        String name = Runner.getRunnerById(runnerId).getName();
                        name = name.replace(" ", "");
                        name = name.toLowerCase();
                        name = name.substring(0, 1).toUpperCase() + name.substring(1);

                        Runner nwRunner = Runner.Builder.builder()
                                .withName(name)
                                .withPersonalId(runnerId)
                                .withStartingNumber(atInt.incrementAndGet())
                                .withBirthDate(randomDate())
                                .build();

                        runners.add(nwRunner);
                    }

                } catch (InterruptedException e) {
                    throw new RuntimeException();
                }
            });
        }

        printByRepeatedName(runners);
        printByMatchingMonth(runners);
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println(totalTime + " ms");

    }

    private static void printByRepeatedName(List<Runner> runners) {
        runners.sort(Comparator.comparing(Runner::getName));
        String nameToCheck = "";
        int count = 0;
        Runner previuosRunner = new Runner();
        for (var runner : runners) {
            if (nameToCheck.equals(runner.getName())) {
                if (count == 0) {
                    System.out.println("NAME: " + nameToCheck);
                    System.out.println(previuosRunner);
                }
                System.out.println(runner);
                count += 1;
            } else {
                count = 0;
                nameToCheck = runner.getName();
                previuosRunner = runner;
            }
        }
    }

    private static void printByMatchingMonth(List<Runner> runners) {
        runners.sort(Comparator.comparing(runner -> runner.getBirthDate().getMonth()));
        int monthToCheck = 0;
        int count = 0;
        Runner previuosRunner = new Runner();
        for (var runner : runners) {
            if (monthToCheck == runner.getBirthDate().getMonthValue()) {
                if (count == 0) {
                    System.out.println("Month of Birth: " + monthToCheck);
                    System.out.println(previuosRunner);
                }
                System.out.println(runner);
                count += 1;
            } else {
                count = 0;
                monthToCheck = runner.getBirthDate().getMonthValue();
                previuosRunner = runner;
            }
        }
    }

    public static LocalDate randomDate() {

        int year = getRandomNumberInRange(1900, 2023);

        int month = getRandomNumberInRange(1, 12);

        int day = getRandomNumberInRange(1, LocalDate.of(year, month, 1).lengthOfMonth());

        return LocalDate.of(year, month, day);


    }

    public static int getRandomNumberInRange(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }
}
