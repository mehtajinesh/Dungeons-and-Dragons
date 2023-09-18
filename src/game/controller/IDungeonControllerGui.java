package game.controller;

import game.model.DungeonsGame;

/**
 * Represents a Controller GUI for Dungeon Game: handle user moves by executing them
 * using the model; convey move outcomes to the user using console output;
 * provides all the callback for the view.
 */
public interface IDungeonControllerGui {

  /**
   * Execute a single game of dungeon given a dungeon game model. When the game is over,
   * the playGame method ends.
   */
  void playGame();

  /**
   * handle the callback for picking up treasure from current location of the player.
   */
  void handleTreasurePickUpCurrentLocation();

  /**
   * handle the callback for picking up arrow from current location of the player.
   */
  void handleArrowPickUpCurrentLocation();

  /**
   * handle the callback for shooting the arrow from current location.
   *
   * @param direction direction for arrow to shoot in
   * @param distance  distance of the arrow to shoot
   */
  void handleShootArrow(DungeonsGame.Direction direction, int distance);

  /**
   * handle the callback for moving the player from current location.
   *
   * @param direction direction of the movement.
   */
  void handleMovePlayer(DungeonsGame.Direction direction);

  /**
   * handle the callback for generating the player description from the model.
   */
  void handlePlayerDescriptionGeneration();

  /**
   * starts a new game with given parameters.
   *
   * @param wrapType            wrap type
   * @param rowCount            row count
   * @param columnCount         column count
   * @param interConnectivity   inter-connect value
   * @param distributionPercent treasure percent
   * @param monsterCount        monster count
   * @param playerName          player name
   */
  void startNewGame(DungeonsGame.WrapType wrapType, int rowCount, int columnCount,
                    int interConnectivity, int distributionPercent, int monsterCount,
                    String playerName);

  /**
   * restart the current game.
   */
  void restartCurrentGame();

  /**
   * quits the current game.
   */
  void quitGame();

}
