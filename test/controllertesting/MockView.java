package controllertesting;

import game.controller.IDungeonControllerGui;
import game.view.GameView;

/**
 * This class is used to implement the mock view. This is used to make sure that
 * the controller works correctly in isolation.
 */
public class MockView implements GameView {

  private final StringBuilder log;

  /**
   * Constructor for mock view.
   *
   * @param log logger where the logs are appended
   */
  public MockView(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void addListeners(IDungeonControllerGui listener) {
    log.append("Adding listener").append("\n");
  }

  @Override
  public void refresh() {
    log.append("Calling refresh").append("\n");
  }

  @Override
  public void makeVisible() {
    log.append("Making view visible").append("\n");
  }

  @Override
  public void updatePlayerName(String playerName) {
    log.append("Updating player name to ").append(playerName).append("\n");
  }

  @Override
  public void actionResult(String message) {
    log.append("Updating action result: ").append(message).append("\n");
  }

  @Override
  public void resetFocus() {
    log.append("Resetting focus").append("\n");
  }

  @Override
  public void displayGameResult() {
    log.append("Displaying game result").append("\n");
  }

  @Override
  public void showPlayerDescription(String playerDescription) {
    log.append("Showing player description: ").append(playerDescription).append("\n");
  }

  @Override
  public void resetGame() {
    log.append("Resetting the game").append("\n");
  }

  @Override
  public void newGameSetup() {
    log.append("New game setup").append("\n");
  }
}
