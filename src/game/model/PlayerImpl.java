package game.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * This class implements the player interface and stores the player's data.
 * It includes functionalities like getting the player's name and getting
 * the amount of treasure acquired by the player.
 */
class PlayerImpl implements Player {
  private final String playerName;
  private final Map<DungeonsGame.TreasureType, Integer> acquiredTreasures;
  private final List<String> cellsTravelled;
  private final List<DungeonsGame.ArrowType> availableArrows;

  /**
   * This function is used to create a player with the given name.
   *
   * @param playerName name of the player
   * @throws IllegalArgumentException if the player name is null
   */
  public PlayerImpl(String playerName) throws IllegalArgumentException {
    if (playerName == null) {
      throw new IllegalArgumentException("Invalid player name provided");
    }
    this.playerName = playerName;
    this.acquiredTreasures = new TreeMap<>();
    this.cellsTravelled = new ArrayList<>();
    this.availableArrows = new ArrayList<>();
    addDefaultArrows();
  }

  private void addDefaultArrows() {
    // add three arrows by default
    for (int count = 0; count < 3; count++) {
      this.availableArrows.add(DungeonsGame.ArrowType.CROOKED_ARROW);
    }
  }

  @Override
  public String getName() {
    return playerName;
  }

  @Override
  public String getCurrentDungeonLocation() {
    return cellsTravelled.get(cellsTravelled.size() - 1);
  }

  @Override
  public boolean checkForAtLeastOneArrow() {
    return availableArrows.size() > 0;
  }

  @Override
  public List<DungeonsGame.ArrowType> getAvailableArrows() {
    return availableArrows;
  }

  @Override
  public void reduceAvailableArrowByOne() throws IllegalStateException {
    if (this.checkForAtLeastOneArrow()) {
      availableArrows.remove(0);
    } else {
      throw new IllegalStateException("No arrow available to remove");
    }

  }

  @Override
  public void addArrow() {
    availableArrows.add(DungeonsGame.ArrowType.CROOKED_ARROW);
  }

  @Override
  public Map<DungeonsGame.TreasureType, Integer> getTreasure() {
    return acquiredTreasures;
  }

  @Override
  public void captureTreasure(DungeonsGame.TreasureType treasure) throws IllegalArgumentException {
    if (treasure == null) {
      throw new IllegalArgumentException("Invalid treasure provided");
    }
    int existingCount = 0;
    if (acquiredTreasures.containsKey(treasure)) {
      existingCount = acquiredTreasures.get(treasure);
    }
    acquiredTreasures.put(treasure, ++existingCount);
  }

  @Override
  public List<String> getTravelledPath() {
    return cellsTravelled;
  }

  @Override
  public void addCellAsVisited(String cellName) throws IllegalArgumentException {
    if (cellName == null) {
      throw new IllegalArgumentException("Invalid cell name provided");
    }
    cellsTravelled.add(cellName);
  }

}
