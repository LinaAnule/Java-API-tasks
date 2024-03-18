import java.awt.*;
import java.lang.reflect.Type;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
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

        Function<Runner, Integer> isBornOnWeekend = (runner) -> {
            DayOfWeek dayOfWeek = runner.getBirthDate().getDayOfWeek();
            if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
                return 3;
            }
            return 0;
        };

        Function<Runner, Integer> isBornNotWinter = (runner) -> {
            Month month = runner.getBirthDate().getMonth();
            if (month != Month.DECEMBER && month != Month.JANUARY && month != Month.FEBRUARY) {
                return 2;
            }
            return 0;
        };

        Function<Runner, Integer> isStartingNumber = (runner) -> {
            int startingNumber = runner.getStartingNumber();
            if (startingNumber <= 25 && startingNumber > 0) {
                return 1;
            }
            return 0;
        };

        AdvantagePointsCalculator calculateAdvantagePoints = (runners1, functions) -> {
            List<Runner> runnersWithAdvPoints = new CopyOnWriteArrayList<>();
            for (Runner runner : runners1) {
                Integer advPoints = 0;
                for (Function function : functions) {
                    advPoints = advPoints + (Integer) function.apply(runner);
                }
                Runner newRunner = Runner.Builder.builder()
                        .withName(runner.getName())
                        .withPersonalId(runner.getPersonalId())
                        .withStartingNumber(runner.getStartingNumber())
                        .withBirthDate(runner.getBirthDate())
                        .withAdvantagePoints(advPoints)
                        .build();
                runnersWithAdvPoints.add(newRunner);
            }
            return runnersWithAdvPoints;
        };

        List<Function> functionList = new ArrayList<>();
        functionList.add(isBornOnWeekend);
        functionList.add(isBornNotWinter);
        functionList.add(isStartingNumber);
        List<Runner> runnerList = calculateAdvantagePoints.calculatePoints(runners, functionList);
        sortWinners(runnerList);
//        printByRepeatedName(runners);
//        printByMatchingMonth(runners);

        long endTime = System.currentTimeMillis();

        long totalTime = endTime - startTime;
        System.out.println(totalTime + " ms");


    }

    private static void sortWinners(List<Runner> runners) {
        runners.sort(Comparator.comparing(Runner::getAdvantagePoints).reversed());
        int advPoints = 0;
        int positions = 0;
        for (Runner runner : runners) {
            if (positions == 4) {
                break;
            }
            System.out.println(runner);

            if (advPoints != runner.getAdvantagePoints()) {
                positions += 1;
                System.out.println(positions + " PLACE. HEy");
                advPoints = runner.getAdvantagePoints();

            }

        }


//        List<Integer> distinctAdvantagePoints = runners.stream()
//                .map(Runner::getAdvantagePoints)
//                .distinct()
//                .sorted(Comparator.reverseOrder())
//                .limit(3)
//                .toList();
//        System.out.println("WInners La La La");
//        runners.stream()
//                .filter(runner -> distinctAdvantagePoints.contains(runner.getAdvantagePoints()))
//                .sorted(Comparator.comparing(Runner::getAdvantagePoints).reversed())
//                .forEach( System.out::println);


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
