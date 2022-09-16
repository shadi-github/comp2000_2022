import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import static java.util.Comparator.comparingDouble;

public class ChaseMove implements MoveStrategy {
  @Override
  public Cell chooseNextLoc(List<Cell> possibleLocs, Actor currActor, List<Actor> otherActors) {
    Optional<Actor> player = Optional.empty();
    for(Actor a: otherActors) {
      if(a.isHuman()) {
        player = Optional.of(a);
      }
    }
    if(player.isPresent()) {
      Cell loc = player.get().getLocation();
      List<Double> distance = new ArrayList<Double>();
      for(Cell c: possibleLocs) {
        double d = Math.sqrt(Math.pow(loc.col-c.col,2) + Math.pow(loc.row-c.row,2));
        distance.add(d);
      }
      int index = IntStream.range(0,distance.size()).boxed()
                            .min(comparingDouble(distance::get)).orElse(-1);
      if(index >= 0) {
        return possibleLocs.get(index);
      }
    }
    return null;
  }

  public String toString() {
    return "chase movement";
  }
}
