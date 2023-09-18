package game.view;

import game.controller.IDungeonControllerGui;
import game.model.DungeonsGame;
import game.model.DungeonsGameReadOnly;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * This class is used to implement the game view of the dungeon. It includes refreshing the board,
 * displaying the game results, showing the player description and many more.
 */
public class GameViewImpl extends JFrame implements GameView {

  private final JLabel playerNameValue;
  private final BoardPanel gameBoardPanel;
  private final JLabel statusValue;
  private final DungeonsGameReadOnly model;
  private final JButton playerDescription;
  private IDungeonControllerGui controller;
  private final JMenuItem newGame;
  private final JMenuItem restartGame;
  private final JMenuItem quitGame;
  private final JMenuItem examples;

  /**
   * Constructor for GameViewImpl class.
   *
   * @param dungeonsGameReadOnly read only model of the game
   * @throws IllegalArgumentException if the model is empty
   */
  public GameViewImpl(DungeonsGameReadOnly dungeonsGameReadOnly) throws IllegalArgumentException {
    super("Dungeons Game");
    if (dungeonsGameReadOnly == null) {
      throw new IllegalArgumentException("Invalid model provided");
    }
    this.model = dungeonsGameReadOnly;
    this.controller = null;
    setSize(800, 800);
    JPanel mainContainer = new JPanel();
    mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
    JScrollPane scrollPane = new JScrollPane(mainContainer);
    add(scrollPane);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //Creating the MenuBar and adding components
    JMenuBar menuBar = new JMenuBar();
    JMenu gameMenu = new JMenu("Game");
    JMenu helpMenu = new JMenu("Help");
    menuBar.add(gameMenu);
    menuBar.add(helpMenu);
    // Setup Game Menu
    newGame = new JMenuItem("New Game");
    restartGame = new JMenuItem("Restart Game");
    quitGame = new JMenuItem("Quit Game");
    gameMenu.add(newGame);
    gameMenu.add(restartGame);
    gameMenu.add(quitGame);
    //Setup Help Menu
    examples = new JMenuItem("Controls");
    helpMenu.add(examples);

    //Creating labels and buttons for player information
    JPanel playerInformationPanel = new JPanel();
    JLabel playerNameLabel = new JLabel("Player Name :");
    playerNameValue = new JLabel("Player Name Value");
    playerDescription = new JButton("Player Description");
    playerInformationPanel.add(playerNameLabel);
    playerInformationPanel.add(playerNameValue);
    playerInformationPanel.add(Box.createRigidArea(new Dimension(10, 0)));
    playerInformationPanel.add(playerDescription);
    mainContainer.add(playerInformationPanel);

    JPanel gameBoardContainer = new JPanel();
    gameBoardContainer.setLayout(new FlowLayout());

    //Create a panel for game and pass model to the panel
    gameBoardPanel = new BoardPanel(dungeonsGameReadOnly);
    gameBoardContainer.add(gameBoardPanel);
    mainContainer.add(gameBoardContainer);
    //Creating status panel
    JPanel statusPanel = new JPanel();
    JLabel statusLabel = new JLabel("Game Message:");
    statusValue = new JLabel("Game Message Value");
    statusPanel.add(statusLabel);
    statusPanel.add(statusValue);
    mainContainer.add(statusPanel);
    setJMenuBar(menuBar);
  }

  @Override
  public void addListeners(IDungeonControllerGui controller) throws IllegalArgumentException {
    if (controller == null) {
      throw new IllegalArgumentException();
    }
    this.controller = controller;
    ActionListener playerDescriptionListener = new PlayerDescriptionListener();
    playerDescription.addActionListener(playerDescriptionListener);
    ActionListener newGameListener = new NewGameListener();
    newGame.addActionListener(newGameListener);
    ActionListener restartListener = new RestartListener();
    restartGame.addActionListener(restartListener);
    ActionListener quitListener = new QuitListener();
    quitGame.addActionListener(quitListener);
    ActionListener helpListener = new HelpListener();
    examples.addActionListener(helpListener);
    addWindowListener(new WindowAdapter() {
      public void windowOpened(WindowEvent e) {
        gameBoardPanel.requestFocus();
      }
    });
    gameBoardPanel.addListeners(controller);
  }

  @Override
  public void refresh() {
    repaint();
  }

  @Override
  public void makeVisible() {
    setVisible(true);
  }

  @Override
  public void resetFocus() {
    gameBoardPanel.setFocusable(true);
    gameBoardPanel.requestFocus();
  }

  @Override
  public void displayGameResult() {
    DungeonsGame.GameState state = model.getGameStatus();
    String messageToUser;
    if (state == DungeonsGame.GameState.WON) {
      messageToUser = "You are a winner. Please start a new game from the menu bar.";
    } else if (state == DungeonsGame.GameState.LOST) {
      messageToUser = "Sorry. You are dead. Better luck next time. "
              + "Please start a new game from the menu bar.";
    } else {
      messageToUser = "Looks like you are quitting the game. Hope to see you soon";
    }
    actionResult(messageToUser);
    JOptionPane.showMessageDialog(this, messageToUser,
            "Information", JOptionPane.INFORMATION_MESSAGE);
    refresh();
  }

  @Override
  public void showPlayerDescription(String playerDescription) throws IllegalStateException {
    if (playerDescription == null) {
      throw new IllegalStateException("Invalid Player Description acquired");
    }
    JOptionPane.showMessageDialog(this, playerDescription,
            "Player Description", JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public void resetGame() {
    gameBoardPanel.reset();
  }

  @Override
  public void newGameSetup() {
    gameBoardPanel.setup();
  }

  @Override
  public void updatePlayerName(String playerName) throws IllegalStateException {
    if (playerName == null) {
      throw new IllegalStateException("Invalid player name found");
    }
    playerNameValue.setText(playerName);
  }

  @Override
  public void actionResult(String message) throws IllegalStateException {
    if (message == null) {
      throw new IllegalStateException("Invalid action result message acquired");
    }
    statusValue.setText(message);
  }

  private void showControlsToUser() {
    JOptionPane.showMessageDialog(getContentPane(), "To Shoot Arrow: Use the arrows on the "
                    + "keyboard with 's' pressed simultaneously\n"
                    + "To Move Player: Use the arrows on the keyboard or click on "
                    + "the nearby cells.\n"
                    + "To Pickup Treasure: Use the 't' key to pickup treasure from "
                    + "current location\n"
                    + "To Pickup Arrow: Use the 'a' key to pickup arrow from \n"
                    + "current location\n"
                    + "Goal: You have to reach to the end location in the dungeon and kill "
                    + "any monsters on the way if needed.",
            "Controls Information", JOptionPane.INFORMATION_MESSAGE);
  }

  private class RestartListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      controller.restartCurrentGame();
    }
  }

  private class QuitListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      controller.quitGame();
    }
  }

  private class PlayerDescriptionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      controller.handlePlayerDescriptionGeneration();
    }
  }

  private class NewGameListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      Settings dialog = new Settings(JFrame.getFrames()[0], controller);
      dialog.setVisible(true);
    }
  }

  private class HelpListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      showControlsToUser();
    }
  }
}
