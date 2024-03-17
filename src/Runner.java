import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Runner {

    private String name;
    private String personalId;
    private Integer startingNumber;
    private Integer finalPosition;
    private Integer advantagePoints;
    private LocalDate birthDate;

    public Runner(String name) {
        this.name = name;
    }

    public Runner() {
    }

    public static class Builder {

        private Runner runner;

        public static Builder builder() {
            Builder builder = new Builder();
            builder.runner = new Runner();
            return builder;
        }

        public Builder withName(String name) {
            runner.name = name;
            return this;
        }

        public Builder withPersonalId(String personalId) {
            runner.personalId = personalId;
            return this;
        }

        public Builder withStartingNumber(Integer number) {
            runner.startingNumber = number;
            return this;
        }

        public Builder withFinalPosition(Integer number) {
            runner.finalPosition = number;
            return this;
        }
        public Builder withAdvantagePoints(Integer number) {
            runner.advantagePoints = number;
            return this;
        }
        public Builder withBirthDate(LocalDate birthDate) {
            runner.birthDate = birthDate;
            return this;
        }

        public Runner build() {
            return runner;
        }
    }

    private static final Map<String, Runner> RUNNERS;

    static {
        RUNNERS = new HashMap<>();
        RUNNERS.put("50210092072", new Runner("Joris "));
        RUNNERS.put("60305044446", new Runner(" Migle"));
        RUNNERS.put("60110263771", new Runner("Karolina"));
        RUNNERS.put("50309167405", new Runner("Donatas  "));
        RUNNERS.put("60309192584", new Runner("UGNE"));
        RUNNERS.put("60509224334", new Runner("kotryna"));
        RUNNERS.put("60007282869", new Runner("Laura"));
        RUNNERS.put("60304166052", new Runner("Monika"));
        RUNNERS.put("50505181437", new Runner("andrius"));
        RUNNERS.put("50103245201", new Runner("L i u d a s"));
        RUNNERS.put("60203065979", new Runner("Sofija"));
        RUNNERS.put("60410086671", new Runner("Amelija"));
        RUNNERS.put("50010230777", new Runner("Markas"));
        RUNNERS.put("50108261963", new Runner("Benas"));
        RUNNERS.put("60009058056", new Runner("Liepa"));
        RUNNERS.put("60511138391", new Runner("Lukne"));
        RUNNERS.put("50005185203", new Runner("Jonas"));
        RUNNERS.put("60110151044", new Runner("Emilija"));
        RUNNERS.put("60111208331", new Runner("Izabele"));
        RUNNERS.put("60303108022", new Runner("Gabija"));
        RUNNERS.put("50110066995", new Runner("Lukas"));
        RUNNERS.put("60307260489", new Runner("Leja"));
        RUNNERS.put("50506063503", new Runner("Herkus"));
        RUNNERS.put("60510159729", new Runner("Elija"));
        RUNNERS.put("50310149670", new Runner("Matas"));
        RUNNERS.put("50406161222", new Runner("Nojus"));
        RUNNERS.put("60106205373", new Runner("Ema"));
        RUNNERS.put("50305088266", new Runner("Leonas"));
        RUNNERS.put("50411308235", new Runner("Motiejus"));
        RUNNERS.put("60507254756", new Runner("Kamile"));
        RUNNERS.put("60207096951", new Runner("Atene"));
        RUNNERS.put("60412022738", new Runner("Adele"));
        RUNNERS.put("50509194538", new Runner("Domas"));
        RUNNERS.put("50506112102", new Runner("Emilis"));
        RUNNERS.put("60205199376", new Runner("Olivija"));
        RUNNERS.put("60004019453", new Runner("Patricija"));
        RUNNERS.put("50512106494", new Runner("Aronas"));
        RUNNERS.put("50312133248", new Runner("Azuolas"));
        RUNNERS.put("60104247785", new Runner("Smilte"));
        RUNNERS.put("60307173506", new Runner("Vilte"));
        RUNNERS.put("50010231517", new Runner("Tauras"));
        RUNNERS.put("60408142677", new Runner("Saule"));
        RUNNERS.put("60201136680", new Runner("Austeja"));
        RUNNERS.put("60311188301", new Runner("  Ugne  "));
        RUNNERS.put("60206272983", new Runner("Eleonora"));
        RUNNERS.put("50206053690", new Runner("Adomas"));
        RUNNERS.put("50501213778", new Runner("Rokas"));
        RUNNERS.put("60308254099", new Runner("Aiste"));
        RUNNERS.put("60110036523", new Runner("KAROlina "));
        RUNNERS.put("50307269292", new Runner("Tauras"));
    }

    public static Set<String> getAllRunnersIDs() throws InterruptedException {
        Thread.sleep(10);
        return RUNNERS.keySet();
    }

    public static Runner getRunnerById(String id) throws InterruptedException {
        Thread.sleep(10);
        return RUNNERS.get(id);
    }

    public void setStartingNumber(Integer startingNumber) {
        this.startingNumber = startingNumber;
    }

    @Override
    public String toString() {
        return "Runner{" +
                "name='" + name + '\'' +
                ", personalId='" + personalId + '\'' +
                ", startingNumber=" + startingNumber +
                ", finalPosition=" + finalPosition +
                ", advantagePoints=" + advantagePoints +
                ", birthDate=" + birthDate +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getPersonalId() {
        return personalId;
    }

    public Integer getStartingNumber() {
        return startingNumber;
    }

    public Integer getFinalPosition() {
        return finalPosition;
    }

    public Integer getAdvantagePoints() {
        return advantagePoints;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public static void printNames(){
        for (var entry : RUNNERS.entrySet()) {
            System.out.println(entry.getKey() + "/" + entry.getValue());
        }
    }
}
