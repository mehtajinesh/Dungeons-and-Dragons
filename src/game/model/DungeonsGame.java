package game.model;

/**
 * This interface is used to provide access to the dungeons game.
 * The client can get the grid information, start the game, move
 * the player around the grid, get the player's total treasure count
 * and do much more.
 */
public interface DungeonsGame extends DungeonsGameReadOnly {

  /**
   * This enum represents the treasure type - can be either diamonds, rubies or sapphires.
   */
  enum TreasureType {
    DIAMONDS,
    RUBIES,
    SAPPHIRES
  }

  /**
   * This enum represent the wrap type for a dungeon game.
   */
  enum WrapType {
    WRAPPING,
    NON_WRAPPING
  }

  /**
   * This enum represents the type of the smell in a cave.
   */
  enum SmellType {
    STRONG,
    WEAK,
    NO_SMELL
  }

  /**
   * This enum represents the type of direction available inside the cell
   * for the movement of the player.
   */
  enum Direction {
    NORTH,
    SOUTH,
    EAST,
    WEST
  }

  /**
   * This enum represents the type of the arrow to be used in the game by the player.
   */
  enum ArrowType {
    CROOKED_ARROW
  }

  /**
   * different results from the movement activity.
   */
  enum MovementStates {
    MOVE_SUCCESS,
    ESCAPE_MONSTER,
    PLAYER_EATEN
  }

  /**
   * This enum represents the states of result which can come up when the player shoots
   * an arrow from the current location.
   */
  enum ShootResultStates {
    MONSTER_MISSED,
    MONSTER_HIT_PARTIAL_DAMAGE,
    MONSTER_HIT_KILLED
  }

  /**
   * This enum represents the states of the game.
   */
  enum GameState {
    WON,
    LOST,
    PROGRESS,
    NOT_STARTED,
    STOPPED
  }

  /**
   * This enum represents different events which can happen as part of the game.
   */
  enum GameEvents {
    ARROW_SHOOT,
    TREASURE_PICKUP,
    ARROW_PICKUP,
    MONSTER_HIT,
    MOVE
  }

  /**
   * This function is used start the game for the given player name.
   *
   * @param playerName name of the player
   * @throws IllegalArgumentException if the player name is invalid
   */
  void startGameForPlayer(String playerName) throws IllegalArgumentException;

  /**
   * This function is used to move a player to provided location.
   *
   * @param direction direction of the movement
   * @return movement state
   * @throws IllegalArgumentException if the cell(tunnel/cave) is already occupied or
   *                                  inputs are invalid.
   */
  MovementStates movePlayerToLocation(Direction direction) throws IllegalArgumentException;

  /**
   * This function is used to shoot arrow for the player in the given direction
   * and provided distance.
   *
   * @param direction direction of the arrow
   * @param distance  distance of the arrow
   * @return resulting shooting state for the arrow fired
   * @throws IllegalArgumentException if the direction or distance provided is invalid
   * @throws IllegalStateException    if the player doesn't have any arrows left to shoot
   */
  ShootResultStates shootArrowForPlayer(Direction direction, int distance)
          throws IllegalArgumentException, IllegalStateException;

  /**
   * This function is used to pick up treasure for the current location.
   *
   * @throws IllegalStateException if the player is not initialized
   */
  void pickUpTreasureCurrentLocation() throws IllegalStateException;

  /**
   * This function is used to pick up arrow for the current location.
   *
   * @throws IllegalStateException if the player is not initialized
   */
  void pickUpArrowCurrentLocation() throws IllegalStateException;

  /**
   * resets the state of the model to original state (like the fresh game).
   */
  void reset();

  /**
   * sets up a new game based on provided inputs.
   *
   * @param wrapType            wrapping type
   * @param rowCount            row count
   * @param columnCount         column count
   * @param interConnectivity   interconnectivity value
   * @param distributionPercent treasure and arrow percentage
   * @param monsterCount        monster count
   * @throws IllegalArgumentException if the parameters are invalid
   */
  void setupNewGame(DungeonsGame.WrapType wrapType, int rowCount, int columnCount,
                    int interConnectivity, int distributionPercent,
                    int monsterCount) throws IllegalArgumentException;

}
