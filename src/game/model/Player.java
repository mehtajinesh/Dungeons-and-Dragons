package game.model;

import java.util.List;
import java.util.Map;

/**
 * This interface represents a player entity in the dungeons game. It holds all the different
 * information of the player like the current location on the player in the game and total treasure
 * collected by till now.
 */
interface Player {

  /**
   * This function is used to get the name of the player.
   *
   * @return player name
   */
  String getName();

  /**
   * This function is used to get the list of treasure acquired till now.
   *
   * @return map of acquired treasures.
   */
  Map<DungeonsGame.TreasureType, Integer> getTreasure();

  /**
   * This function is used to capture the provided treasure by the player.
   *
   * @param treasure treasure to be captured
   * @throws IllegalArgumentException if the treasure is invalid
   */
  void captureTreasure(DungeonsGame.TreasureType treasure) throws IllegalArgumentException;

  /**
   * This function is used to get the travelled path of the player.
   *
   * @return string format of the traversed path for the player
   */
  List<String> getTravelledPath();

  /**
   * This function is used to mark the provided cell name as visited.
   * This is helpful when we want to know what path has the player traversed
   * till now.
   *
   * @param cellName name of the cell(cave/tunnel)
   * @throws IllegalArgumentException if the cell name is invalid
   */
  void addCellAsVisited(String cellName) throws IllegalArgumentException;

  /**
   * This function is used to get current dungeon location.
   *
   * @return current dungeon cell location
   */
  String getCurrentDungeonLocation();

  /**
   * This function is used to check if there is at least one arrow available.
   *
   * @return true if at least one arrow is available else false
   */
  boolean checkForAtLeastOneArrow();

  /**
   * This function is used to get the available arrows to shoot.
   *
   * @return list of available arrows.
   */
  List<DungeonsGame.ArrowType> getAvailableArrows();

  /**
   * This function is used to reduce available arrow by one.
   * Make sure to check for 'checkForAtLeastOneArrow' before accessing this
   *
   * @throws IllegalStateException if there is no arrow to remove
   */
  void reduceAvailableArrowByOne() throws IllegalStateException;

  /**
   * This function is used to add an arrow to acquired list.
   */
  void addArrow();
}
