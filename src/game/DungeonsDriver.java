package game;

import game.controller.DungeonControllerConsole;
import game.controller.DungeonControllerGui;
import game.controller.IConsoleDungeonController;
import game.controller.IDungeonControllerGui;
import game.model.DungeonsGame;
import game.model.DungeonsGameImpl;
import game.model.RandomNumberGenerator;
import game.model.RandomNumberGeneratorImpl;
import game.view.GameView;
import game.view.GameViewImpl;

import java.io.InputStreamReader;
import java.util.Objects;

/**
 * This class is used to test the dungeons game which is played by one player.
 * This class also uses all the functionalities that are part of the dungeons game interface.
 * This is the driver class for this application.
 */
public class DungeonsDriver {

  /**
   * This function is called the main function as it is executed as soon as the user
   * opens the application.
   *
   * @param args command line arguments provided
   */
  public static void main(String[] args) {
    try {
      // Check if command line args are given
      if (args.length == 0) {
        // no command line arguments given, so start the GUI game
        RandomNumberGenerator randomizer = new RandomNumberGeneratorImpl();
        //create game model
        DungeonsGame dungeonsGameReadOnly = new DungeonsGameImpl(DungeonsGame.WrapType.NON_WRAPPING,
                4, 4, 1, 30, 3, randomizer);
        //create view
        GameView gameView = new GameViewImpl(dungeonsGameReadOnly);
        //create controller
        IDungeonControllerGui controller = new DungeonControllerGui(dungeonsGameReadOnly,
                gameView);
        controller.playGame();
      } else if (args.length < 6) {
        throw new IllegalArgumentException("Minimum Six Arguments needed. "
                + "Please check ReadMe file.");
      } else {
        // command line arguments given, so start a console based game
        String welcomeMessage = "Welcome to the Dungeons game!!! This game is played by only one "
                + "player.\n";
        System.out.println(welcomeMessage);
        RandomNumberGenerator randomizer = new RandomNumberGeneratorImpl();
        DungeonsGame.WrapType wrapType;
        if (Objects.equals(args[0], "0")) {
          wrapType = DungeonsGame.WrapType.NON_WRAPPING;
        } else {
          wrapType = DungeonsGame.WrapType.WRAPPING;
        }
        int rowCount = Integer.parseInt(args[1]);
        int colCount = Integer.parseInt(args[2]);
        int interConnectivity = Integer.parseInt(args[3]);
        int treasurePercent = Integer.parseInt(args[4]);
        int monsterCount = Integer.parseInt(args[5]);
        //create game model
        DungeonsGame dungeonsGame = new DungeonsGameImpl(wrapType,
                rowCount, colCount, interConnectivity, treasurePercent, monsterCount, randomizer);
        //create controller
        Readable reader = new InputStreamReader(System.in);
        IConsoleDungeonController controller = new DungeonControllerConsole(reader,
                System.out, dungeonsGame);
        controller.playGame();
      }
    } catch (IllegalStateException | IllegalArgumentException | UnsupportedOperationException e) {
      //show the error to the user
      System.out.println("Error Occurred: " + e.getMessage());
    }
  }
}
