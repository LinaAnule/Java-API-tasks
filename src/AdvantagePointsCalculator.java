import java.util.List;
import java.util.function.Function;

@FunctionalInterface
public interface AdvantagePointsCalculator {

    List<Runner> calculatePoints(List<Runner> runners, List<Function> functions);

}
