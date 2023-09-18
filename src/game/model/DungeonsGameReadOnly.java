package game.model;

import java.util.List;

/**
 * This interface exposes all the read only functionalities of the game model.
 * During this, we enforce no modification happening to the model from outside
 * when accessing the model.
 */
public interface DungeonsGameReadOnly {

  /**
   * This function is used to get the smell for current location.
   *
   * @return smell of current location
   */
  DungeonsGame.SmellType getCurrentLocationSmell();

  /**
   * This function is used to get the current location of the player.
   *
   * @return current location of the user
   * @throws IllegalStateException if the game has not started
   */
  DungeonCell getPlayerCurrentLocation() throws IllegalStateException;

  /**
   * This function is used to get the available direction for movement.
   *
   * @return list of available direction for movement.
   */
  List<String> getAvailableMovements();

  /**
   * This function is used to get a complete description of the location where the
   * player is currently resided.
   *
   * @return description of the location (treasure in the location and the possible moves)
   * @throws IllegalStateException if the player has not entered the game
   */
  String getPlayerCurrentLocationInformation() throws IllegalStateException;

  /**
   * This function is used to get the player's information
   * (including total treasure list acquired).
   *
   * @return player information
   * @throws IllegalStateException if the player has not entered the game
   */
  PlayerData getPlayerInformation() throws IllegalStateException;

  /**
   * This function is used to get the status of the game.
   *
   * @return game status
   */
  DungeonsGame.GameState getGameStatus();

  /**
   * This function is used to get the complete game grid cells.
   *
   * @return list of all dungeon cells
   */
  List<List<DungeonCell>> getDungeonCells();

  /**
   * gets the game history (includes all actions performed by the user).
   *
   * @return map of game history
   */
  IGameHistory getGameHistory();

  /**
   * gets the whole grid of the game.
   *
   * @return string representation of the whole grid
   */
  String getGameGrid();
}
