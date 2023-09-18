package game.controller;

/**
 * Represents a Controller for Dungeon Game: handle user moves by executing them using the model;
 * convey move outcomes to the user using console output.
 */
public interface IConsoleDungeonController {

  /**
   * Execute a single game of dungeon given a dungeon game model. When the game is over,
   * the playGame method ends.
   *
   * @throws IllegalStateException if IO Operation fails
   */
  void playGame() throws IllegalStateException;
}
