package game.controller;

import game.model.DungeonsGame;
import game.model.PlayerData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * This class is used to represent a dungeon controller. It communicates to both the
 * dungeon game model and the output which is a console in this case.
 */
public class DungeonControllerConsole implements IConsoleDungeonController {

  private final Appendable output;
  private final Readable input;
  private final DungeonsGame model;

  /**
   * Constructor for the controller.
   *
   * @param in  the source to read from
   * @param out the target to print to
   * @param model model of the game
   * @throws IllegalArgumentException if invalid parameters provided
   */
  public DungeonControllerConsole(Readable in, Appendable out, DungeonsGame model)
          throws IllegalArgumentException {
    if (in == null || out == null || model == null) {
      throw new IllegalArgumentException("Readable or Appendable can't be null");
    }
    this.output = out;
    this.input = in;
    this.model = model;
  }

  @Override
  public void playGame() throws IllegalStateException {
    try {
      Scanner scan = new Scanner(this.input);
      String playerName = "Player-1";
      output.append(String.format("Hey %s. Let's start the game.", playerName)).append("\n");
      model.startGameForPlayer(playerName);
      DungeonsGame.GameState gameState = model.getGameStatus();
      while (gameState == DungeonsGame.GameState.PROGRESS) {
        DungeonsGame.SmellType smellType = model.getCurrentLocationSmell();
        //check for smell
        if (smellType == DungeonsGame.SmellType.STRONG) {
          output.append("Red Alert!!! Monsters incoming\n");
        } else if (smellType == DungeonsGame.SmellType.WEAK) {
          output.append("Warning! Something looks fishy. Potential monsters nearby\n");
        }
        //Show current location information
        String currentLocationInformation = model.getPlayerCurrentLocationInformation();
        output.append(currentLocationInformation);
        List<String> informationSegregated = new ArrayList<>();
        Collections.addAll(informationSegregated, currentLocationInformation.split("\n"));
        //Get user input
        while (true) {
          boolean validCommand = true;
          //Show available actions
          output.append("What would you like to do?\nMove, Pickup, or Shoot or Quit(M-P-S-Q)?\n");
          String inputCommand = scan.next();
          switch (inputCommand) {
            case "Q":
              //if quit, stop scan and game
              scan.close();
              gameState = DungeonsGame.GameState.STOPPED;
              break;
            case "M":
              //if player chose move, ask direction
              output.append("Which direction do you want to explore?\n");
              //show input options based on neighbours
              output.append(String.format("Options - [%s]?", String.join(",",
                      model.getAvailableMovements())));
              //get user input direction
              String movementDirectionInput = scan.next();
              List<String> validDirections = new ArrayList<>();
              for (DungeonsGame.Direction d : DungeonsGame.Direction.values()) {
                validDirections.add(d.toString());
              }
              if (!validDirections.contains(movementDirectionInput)) {
                validCommand = false;
                output.append(String.format("Invalid Direction Provided:%s. Let's try again.\n",
                        movementDirectionInput));
                break;
              }
              try {
                //perform movement and check for monster
                model.movePlayerToLocation(DungeonsGame.Direction.valueOf(movementDirectionInput));
              } catch (IllegalArgumentException ex) {
                validCommand = false;
                output.append(String.format("%s. Input: %s. Let's try again.\n",
                        ex.getMessage(), movementDirectionInput));
              } catch (IllegalStateException ex) {
                validCommand = false;
                output.append(ex.getMessage());
              }
              break;
            case "P":
              //if pickup, ask what
              output.append("Which do you want to pick up?\n");
              List<String> options = new ArrayList<>();
              int treasureDataStartIndex = informationSegregated.indexOf(
                      "Available Treasure:");
              if (!informationSegregated.get(treasureDataStartIndex + 1).equals("None")) {
                options.add("Treasure");
              }
              int arrowDataIndex = informationSegregated.indexOf("Available Arrows:0");
              if (arrowDataIndex == -1) {
                options.add("Arrow");
              }
              if (options.size() == 0) {
                output.append("Nothing to pick-up. Moving to next turn.\n");
                break;
              }
              output.append(String.format("Options - [%s]?", String.join(",", options)));
              //get user input action
              String pickupActionInput = scan.next();
              if (!options.contains(pickupActionInput)) {
                validCommand = false;
                output.append(String.format("Invalid Input Provided:%s. Let's try again.\n",
                        inputCommand));
                break;
              }
              try {
                //perform pickup
                if (pickupActionInput.equals("Treasure")) {
                  model.pickUpTreasureCurrentLocation();
                  output.append("Player successfully picked up all the "
                          + "treasures in the location\n");
                } else {
                  model.pickUpArrowCurrentLocation();
                  output.append("Player successfully picked up all the arrows in the location\n");
                }
              } catch (IllegalStateException ex) {
                validCommand = false;
                output.append(ex.getMessage());
              }
              break;
            case "S":
              // if shoot, ask direction and distance
              // check if empty for arrows
              PlayerData playerInformation = model.getPlayerInformation();
              if (playerInformation.getAcquiredArrows().isEmpty()) {
                output.append("Looks like you are out of arrows. "
                        + "Start searching for more arrows and then shoot.\n");
                break;
              }
              output.append("Which direction to shoot arrow?\n");
              List<String> directions = new ArrayList<>();
              for (DungeonsGame.Direction d : DungeonsGame.Direction.values()) {
                directions.add(d.toString());
              }
              output.append(String.format("Options - [%s]?", String.join(",", directions)));
              //get user input direction
              String directionInput = scan.next();
              if (!directions.contains(directionInput)) {
                validCommand = false;
                output.append(String.format("Invalid Input Provided:%s. Let's try again.\n",
                        inputCommand));
                break;
              }
              output.append("What distance in range 1 to 10 inclusive?\n");
              //get user input distance
              String distanceInput = scan.next();
              int distance;
              try {
                distance = Integer.parseInt(distanceInput);
              } catch (NumberFormatException exception) {
                validCommand = false;
                output.append(String.format("Invalid Input Provided:%s. Let's try again.\n",
                        inputCommand));
                break;
              }
              try {
                // perform shooting
                DungeonsGame.ShootResultStates result =
                        model.shootArrowForPlayer(DungeonsGame.Direction.valueOf(
                                directionInput), distance);
                // update user on result
                if (result == DungeonsGame.ShootResultStates.MONSTER_HIT_PARTIAL_DAMAGE) {
                  output.append("Nice shot. That's the sounds of success.\n");
                } else if (result == DungeonsGame.ShootResultStates.MONSTER_HIT_KILLED) {
                  output.append("Awesome shot. One monster less to deal with.\n");
                } else {
                  output.append("Bad shot. Better luck next time.\n");
                }
                // check if empty for arrows
                playerInformation = model.getPlayerInformation();
                if (playerInformation.getAcquiredArrows().isEmpty()) {
                  output.append("Looks like you are out of arrows. "
                          + "Start searching for more arrows.\n");
                }
              } catch (IllegalArgumentException ex) {
                validCommand = false;
                output.append(String.format("%s. Input Distance: %d. Let's try again.\n",
                        ex.getMessage(), distance));
              } catch (IllegalStateException ex) {
                validCommand = false;
                output.append(ex.getMessage());
              }
              break;
            default:
              validCommand = false;
              output.append(String.format("Invalid Input Provided:%s. Let's try again.\n",
                      inputCommand));
              break;
          }
          if (validCommand) {
            break;
          }
        }
        if (gameState != DungeonsGame.GameState.STOPPED) {
          gameState = model.getGameStatus();
        }
        output.append("\n-----------------------------------------------\n");
      }
      if (gameState == DungeonsGame.GameState.WON) {
        output.append("You are a winner. Here is a summary of the player:\n");
        output.append(generatePlayerInformationFromData(model.getPlayerInformation()));
      } else if (gameState == DungeonsGame.GameState.LOST) {
        output.append("Sorry. You are dead. Better luck next time. "
                + "Here is a summary of the player:\n");
        output.append(generatePlayerInformationFromData(model.getPlayerInformation()));
      } else {
        output.append("Looks like you are quitting the game. Hope to see you soon.\n");
      }
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
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
    playerInformation.append("Traversed Path: \n");
    List<String> traversedPath = playerData.getTraversedPath();
    if (traversedPath.isEmpty()) {
      playerInformation.append("None\n");
    } else {
      for (String cellName : traversedPath) {
        playerInformation.append(cellName).append("\n");
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
