package game.view;

import game.controller.IDungeonControllerGui;

/**
 * This interface is used to provide exposure to the view functionalities of the game.
 * This includes making the view visible, refreshing the view, showing the end results
 * and many more.
 */
public interface GameView {

  /**
   * Set up the controller to handle click events in this view.
   *
   * @param listener the controller
   * @throws IllegalArgumentException if listener is invalid.
   */
  void addListeners(IDungeonControllerGui listener) throws IllegalArgumentException;

  /**
   * refreshes the view of the game.
   */
  void refresh();

  /**
   * make the view visible to start the game session.
   */
  void makeVisible();

  /**
   * updates the player name on the view.
   *
   * @param playerName name of the player
   * @throws IllegalStateException if the player name is invalid
   */
  void updatePlayerName(String playerName) throws IllegalStateException;

  /**
   * updates the result of the action recently performed by the user.
   *
   * @param message result in the form of a string
   * @throws IllegalStateException if the message is invalid
   */
  void actionResult(String message) throws IllegalStateException;

  /**
   * resets the focus back to the game board.
   */
  void resetFocus();

  /**
   * displays the game results based on the game state.
   */
  void displayGameResult();

  /**
   * shows the player description.
   *
   * @param playerDescription information on player's activities
   * @throws IllegalStateException if the player description is invalid
   */
  void showPlayerDescription(String playerDescription) throws IllegalStateException;

  /**
   * resets the game.
   */
  void resetGame();

  /**
   * setups the view for the new game.
   */
  void newGameSetup();
}
