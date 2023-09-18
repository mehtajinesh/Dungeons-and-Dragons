package game.model;

import java.util.List;
import java.util.Map;

/**
 * This class is used to implement the player data interface. It stores the player
 * information including player name, acquired treasure and arrows and traversed
 * path.
 */
public class PlayerDataImpl implements PlayerData {

  private final String playerName;
  private final Map<DungeonsGame.TreasureType, Integer> treasures;
  private final List<String> traversedPath;
  private final List<DungeonsGame.ArrowType> arrows;

  /**
   * Constructor for player data implementation.
   *
   * @param playerName    name of the player
   * @param treasures     map of treasures acquired
   * @param traversedPath traversed path list
   * @param arrows        map of arrows acquired
   * @throws IllegalArgumentException if the arguments provided are invalid
   */
  public PlayerDataImpl(String playerName, Map<DungeonsGame.TreasureType, Integer> treasures,
                        List<String> traversedPath, List<DungeonsGame.ArrowType> arrows)
          throws IllegalArgumentException {
    if (playerName == null || treasures == null || traversedPath == null || arrows == null) {
      throw new IllegalArgumentException("Invalid arguments provided");
    }
    this.playerName = playerName;
    this.treasures = treasures;
    this.traversedPath = traversedPath;
    this.arrows = arrows;
  }

  @Override
  public String getPlayerName() {
    return playerName;
  }

  @Override
  public Map<DungeonsGame.TreasureType, Integer> getAcquiredTreasures() {
    return treasures;
  }

  @Override
  public List<String> getTraversedPath() {
    return traversedPath;
  }

  @Override
  public List<DungeonsGame.ArrowType> getAcquiredArrows() {
    return arrows;
  }
}
