package game.model;

import java.util.List;
import java.util.Map;

/**
 * This interface is used to store the player information in a structured
 * format. This includes player name, acquired treasure and arrows and traversed
 * path.
 */
public interface PlayerData {

  /**
   * gets the player name.
   * @return name of the player
   */
  String getPlayerName();

  /**
   * gets the acquired treasure by the user.
   * @return acquired treasure
   */
  Map<DungeonsGame.TreasureType, Integer> getAcquiredTreasures();

  /**
   * gets the traversed path.
   * @return list of paths visited
   */
  List<String> getTraversedPath();

  /**
   * gets the acquired arrows.
   * @return list of arrows acquired
   */
  List<DungeonsGame.ArrowType> getAcquiredArrows();
}
