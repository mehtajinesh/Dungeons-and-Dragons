package game.controller;

import static java.lang.System.exit;

import game.model.DungeonsGame;
import game.model.PlayerData;
import game.view.GameView;

import java.util.List;
import java.util.Map;

/**
 * This class is used to implement the dungeon controller for GUI mode.
 * It includes implementation of all the features provided to the view.
 * (playing game, shooting arrow, picking up treasure, etc.)
 */
public class DungeonControllerGui implements IDungeonControllerGui {

  private final DungeonsGame model;
  private final GameView view;

  /**
   * Constructor for DungeonControllerGui.
   *
   * @param model model of the game
   * @param view  view of the game.
   * @throws IllegalArgumentException if the model or the view is null.
   */
  public DungeonControllerGui(DungeonsGame model, GameView view)
          throws IllegalArgumentException {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Invalid parameters provided");
    }
    this.model = model;
    this.view = view;
  }

  @Override
  public void playGame() {
    // set listener in view
    view.addListeners(this);
    // show the panel
    view.makeVisible();
    view.resetFocus();
    String playerName = "Player-1";
    // start the game for player
    model.startGameForPlayer(playerName);
    view.updatePlayerName(playerName);
    view.actionResult(String.format("Hey %s. Let's start the game. "
            + "Play a move or check out help to understand the game.", playerName));
  }

  @Override
  public void handleTreasurePickUpCurrentLocation() {
    try {
      // perform the treasure pick up operation
      model.pickUpTreasureCurrentLocation();
      // refresh current location
      view.actionResult("Player successfully picked up all the treasures in the location.");
      view.refresh();
      view.resetFocus();
    } catch (IllegalStateException | IllegalArgumentException ex) {
      // Show the exception to the user
      view.actionResult(ex.getMessage());
    }
  }

  @Override
  public void handleArrowPickUpCurrentLocation() {
    try {
      // perform the arrow pick up operation
      model.pickUpArrowCurrentLocation();
      // refresh current location
      view.actionResult("Player successfully picked up all the arrows in the location.");
      view.refresh();
      view.resetFocus();
    } catch (IllegalStateException | IllegalArgumentException ex) {
      //Show the exception to the user
      view.actionResult(ex.getMessage());
    }
  }

  @Override
  public void handleShootArrow(DungeonsGame.Direction direction, int distance) {
    try {
      // perform the arrow shooting operation
      DungeonsGame.ShootResultStates result =
              model.shootArrowForPlayer(direction, distance);
      // update user on result
      if (result == DungeonsGame.ShootResultStates.MONSTER_HIT_PARTIAL_DAMAGE) {
        view.actionResult("Nice shot. That's the sounds of success.");
      } else if (result == DungeonsGame.ShootResultStates.MONSTER_HIT_KILLED) {
        view.actionResult("Awesome shot. One monster less to deal with.");
      } else {
        view.actionResult("Bad shot. Better luck next time.");
      }
      view.refresh();
      view.resetFocus();
    } catch (IllegalStateException | IllegalArgumentException ex) {
      // Show the exception to the user
      view.actionResult(ex.getMessage());
    }
  }

  @Override
  public void handleMovePlayer(DungeonsGame.Direction direction) {
    try {
      // perform the treasure pick up operation
      DungeonsGame.MovementStates state = model.movePlayerToLocation(direction);
      if (state == DungeonsGame.MovementStates.MOVE_SUCCESS) {
        view.actionResult(String.format("Player moved successfully in %s direction.",
                direction.toString()));
      } else if (state == DungeonsGame.MovementStates.PLAYER_EATEN) {
        view.actionResult("Monster attacked and ate the player.");
      } else if (state == DungeonsGame.MovementStates.ESCAPE_MONSTER) {
        view.actionResult("You barely survived the attack from the monster. Be careful.");
      }
      // refresh current location
      DungeonsGame.GameState gameState = model.getGameStatus();
      if (gameState != DungeonsGame.GameState.PROGRESS) {
        view.displayGameResult();
      }
      view.refresh();
      view.resetFocus();
    } catch (IllegalStateException | IllegalArgumentException ex) {
      // Show the exception to the user
      view.actionResult(ex.getMessage());
    }
  }

  @Override
  public void handlePlayerDescriptionGeneration() {
    try {
      // perform the treasure pick up operation
      PlayerData playerData = model.getPlayerInformation();
      // refresh current location
      view.showPlayerDescription(generatePlayerInformationFromData(playerData));
      view.resetFocus();
    } catch (IllegalStateException | IllegalArgumentException ex) {
      // Show the exception to the user
      view.actionResult(ex.getMessage());
    }
  }

  @Override
  public void startNewGame(DungeonsGame.WrapType wrapType, int rowCount, int columnCount,
                           int interConnectivity, int distributionPercent,
                           int monsterCount, String playerName)
          throws IllegalArgumentException {
    try {
      model.setupNewGame(wrapType, rowCount, columnCount, interConnectivity, distributionPercent,
              monsterCount);
      model.startGameForPlayer(playerName);
      view.updatePlayerName(playerName);
      view.newGameSetup();
      view.actionResult("New game started. Please make an action to start the game.");
      view.resetFocus();
    } catch (IllegalArgumentException exception) {
      view.actionResult(exception.getMessage());
    }
  }

  @Override
  public void restartCurrentGame() {
    String existingPlayerName = model.getPlayerInformation().getPlayerName();
    model.reset();
    model.startGameForPlayer(existingPlayerName);
    view.resetGame();
    view.actionResult("Game restarted. Please make an action to start the game.");
    view.resetFocus();
  }

  @Override
  public void quitGame() {
    exit(0);
  }

  private String generatePlayerInformationFromData(PlayerData playerData) {
    StringBuilder playerInformation = new StringBuilder();
    playerInformation.append(String.format("Player Name: %s\n", playerData.getPlayerName()));
    Map<DungeonsGame.TreasureType, Integer> treasureMap = playerData.getAcquiredTreasures();
    playerInformation.append("Treasure Information: \n");
    if (treasureMap.isEmpty()) {
      playerInformation.append("None\n");
    } else {
      for (DungeonsGame.TreasureType treasure : treasureMap.keySet()) {
        playerInformation.append(String.format("%s:%s\n", treasure,
                treasureMap.get(treasure)));
      }
    }
    playerInformation.append("Available Arrows: ");
    List<DungeonsGame.ArrowType> arrows = playerData.getAcquiredArrows();
    if (arrows.isEmpty()) {
      playerInformation.append("None\n");
    } else {
      playerInformation.append(arrows.size()).append("\n");
    }
    return playerInformation.toString();
  }
}
