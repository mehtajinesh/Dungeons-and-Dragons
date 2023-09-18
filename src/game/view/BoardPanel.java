package game.view;

import game.controller.IDungeonControllerGui;
import game.model.DungeonCell;
import game.model.DungeonsGame;
import game.model.DungeonsGameReadOnly;
import game.model.IGameHistory;
import game.model.Monster;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * This class is used to depict the game board panel which is shown to the user for
 * playing the game. All the refreshes in the view are redirected to the paint
 * view functionality here.
 */
class BoardPanel extends JPanel implements KeyListener {

  private static final Map<Set<DungeonsGame.Direction>, String> exitImage = new HashMap<>();
  private int previousKeyCode;

  static {
    exitImage.put(new HashSet<>(List.of(DungeonsGame.Direction.EAST)), "/images/E.png");
    exitImage.put(new HashSet<>(List.of(DungeonsGame.Direction.NORTH)), "/images/N.png");
    exitImage.put(new HashSet<>(List.of(DungeonsGame.Direction.SOUTH)), "/images/S.png");
    exitImage.put(new HashSet<>(List.of(DungeonsGame.Direction.WEST)), "/images/W.png");
    exitImage.put(new HashSet<>(Arrays.asList(DungeonsGame.Direction.EAST,
            DungeonsGame.Direction.WEST)), "/images/EW.png");
    exitImage.put(new HashSet<>(Arrays.asList(DungeonsGame.Direction.NORTH,
            DungeonsGame.Direction.EAST)), "/images/NE.png");
    exitImage.put(new HashSet<>(Arrays.asList(DungeonsGame.Direction.NORTH,
            DungeonsGame.Direction.SOUTH)), "/images/NS.png");
    exitImage.put(new HashSet<>(Arrays.asList(DungeonsGame.Direction.NORTH,
            DungeonsGame.Direction.WEST)), "/images/NW.png");
    exitImage.put(new HashSet<>(Arrays.asList(DungeonsGame.Direction.SOUTH,
            DungeonsGame.Direction.EAST)), "/images/SE.png");
    exitImage.put(new HashSet<>(Arrays.asList(DungeonsGame.Direction.SOUTH,
            DungeonsGame.Direction.WEST)), "/images/SW.png");
    exitImage.put(new HashSet<>(Arrays.asList(DungeonsGame.Direction.NORTH,
            DungeonsGame.Direction.EAST, DungeonsGame.Direction.WEST)), "/images/NEW.png");
    exitImage.put(new HashSet<>(Arrays.asList(DungeonsGame.Direction.NORTH,
            DungeonsGame.Direction.SOUTH, DungeonsGame.Direction.EAST)), "/images/NSE.png");
    exitImage.put(new HashSet<>(Arrays.asList(DungeonsGame.Direction.NORTH,
            DungeonsGame.Direction.SOUTH, DungeonsGame.Direction.WEST)), "/images/NSW.png");
    exitImage.put(new HashSet<>(Arrays.asList(DungeonsGame.Direction.SOUTH,
            DungeonsGame.Direction.EAST, DungeonsGame.Direction.WEST)), "/images/SEW.png");
    exitImage.put(new HashSet<>(Arrays.asList(DungeonsGame.Direction.NORTH,
            DungeonsGame.Direction.SOUTH, DungeonsGame.Direction.EAST,
            DungeonsGame.Direction.WEST)), "/images/NSEW.png");
  }

  private final DungeonsGameReadOnly model;
  private final Map<String, JLabel> dungeonNameLabel;
  private IDungeonControllerGui controller;

  /**
   * Constructor for board panel.
   *
   * @param model model to be used.
   */
  BoardPanel(DungeonsGameReadOnly model)
          throws IllegalArgumentException {
    super();
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model = model;
    this.controller = null;
    dungeonNameLabel = new HashMap<>();
    previousKeyCode = -1;
    setup();
  }

  void setup() {
    removeAll();
    dungeonNameLabel.clear();
    List<List<DungeonCell>> locations = model.getDungeonCells();
    int rows = locations.size();
    int cols = locations.get(0).size();
    setLayout(new GridLayout(rows, cols, 0, 0));
    for (List<DungeonCell> row : locations) {
      for (DungeonCell cell : row) {
        JLabel dungeonLabel = new JLabel();
        dungeonNameLabel.put(cell.getCellName(), dungeonLabel);
        add(dungeonLabel);
      }
    }
    setVisible(true);
    setSize(new Dimension(cols * 128, rows * 128));
    reset();
  }

  void reset() {
    List<List<DungeonCell>> locations = model.getDungeonCells();
    for (List<DungeonCell> row : locations) {
      for (DungeonCell cell : row) {
        JLabel dungeonLabel = dungeonNameLabel.get(cell.getCellName());
        URL url = getClass().getResource("/images/Blank.png");
        if (url == null) {
          throw new IllegalStateException("Blank file missing");
        }
        try {
          BufferedImage baseImage = ImageIO.read(url);
          dungeonLabel.setIcon(new ImageIcon(baseImage));
        } catch (IOException exception) {
          throw new IllegalArgumentException("Error reading blank file resource");
        }
      }
    }
  }

  private BufferedImage getFinalBaseImage(DungeonCell cell) {
    Map<DungeonsGame.Direction, DungeonCell> neighbours = cell.getActualNeighbours();
    Set<DungeonsGame.Direction> exitDirections = neighbours.keySet();
    String dungeonImage = exitImage.get(exitDirections);
    URL url = getClass().getResource(dungeonImage);
    if (url == null) {
      throw new IllegalStateException("dungeon location image file missing");
    }
    BufferedImage baseImage;
    try {
      baseImage = ImageIO.read(url);
    } catch (IOException exception) {
      throw new IllegalStateException("Error reading location file resource");
    }
    List<DungeonsGame.TreasureType> treasures = cell.getAvailableTreasures();
    int diamondCount = Collections.frequency(treasures, DungeonsGame.TreasureType.DIAMONDS);
    if (diamondCount > 0) {
      url = getClass().getResource("/images/diamond.png");
      if (url == null) {
        throw new IllegalStateException("diamond image file missing");
      }
      try {
        BufferedImage diamondImage = ImageIO.read(url);
        BufferedImage countDiamondImage = superImposeText(diamondImage,
                String.valueOf(diamondCount), 13, 20, Color.black);
        baseImage = superImposeImage(baseImage, countDiamondImage, 15, 15);
      } catch (IOException exception) {
        throw new IllegalStateException("Error reading diamond file resource");
      }
    }
    int rubyCount = Collections.frequency(treasures, DungeonsGame.TreasureType.RUBIES);
    if (rubyCount > 0) {
      url = getClass().getResource("/images/ruby.png");
      if (url == null) {
        throw new IllegalStateException("ruby image file missing");
      }
      try {
        BufferedImage rubyImage = ImageIO.read(url);
        BufferedImage countRubyImage = superImposeText(rubyImage,
                String.valueOf(rubyCount), 12, 20, Color.black);
        baseImage = superImposeImage(baseImage, countRubyImage, 75, 15);
      } catch (IOException exception) {
        throw new IllegalStateException("Error reading ruby file resource");
      }
    }
    int sapphireCount = Collections.frequency(treasures, DungeonsGame.TreasureType.SAPPHIRES);
    if (sapphireCount > 0) {
      url = getClass().getResource("/images/sapphire.png");
      if (url == null) {
        throw new IllegalStateException("sapphire image file missing");
      }
      try {
        BufferedImage sapphireImage = ImageIO.read(url);
        BufferedImage countSapphireImage = superImposeText(sapphireImage,
                String.valueOf(sapphireCount), 12, 20, Color.black);
        baseImage = superImposeImage(baseImage, countSapphireImage, 15, 75);
      } catch (IOException exception) {
        throw new IllegalStateException("Error reading sapphire file resource");
      }
    }
    if (cell.getMonster() != null) {
      Monster monster = cell.getMonster();
      Monster.Health monsterHealth = monster.getHealth();
      BufferedImage monsterImage;
      try {
        if (monsterHealth == Monster.Health.FULL) {
          url = getClass().getResource("/images/full_monster.png");
          if (url == null) {
            throw new IllegalStateException("full health monster image file missing");
          }
          monsterImage = ImageIO.read(url);
        } else if (monsterHealth == Monster.Health.HALF) {
          url = getClass().getResource("/images/half_monster.png");
          if (url == null) {
            throw new IllegalStateException("half health monster image file missing");
          }
          monsterImage = ImageIO.read(url);
        } else {
          url = getClass().getResource("/images/dead_monster.png");
          if (url == null) {
            throw new IllegalStateException("dead monster image file missing");
          }
          monsterImage = ImageIO.read(url);
        }
      } catch (IOException exception) {
        throw new IllegalStateException("Error reading monster file resource");
      }

      baseImage = superImposeImage(baseImage, monsterImage, 32, 32);
    }
    List<DungeonsGame.ArrowType> arrows = cell.getAvailableArrows();
    if (arrows.size() > 0) {
      url = getClass().getResource("/images/arrow.png");
      if (url == null) {
        throw new IllegalStateException("arrow image file missing");
      }
      try {
        BufferedImage arrowsImage = ImageIO.read(url);
        BufferedImage countArrowsImage = superImposeText(arrowsImage,
                String.valueOf(arrows.size()), 12, 26, Color.white);
        if (cell.getCellType() == DungeonCell.CellType.CAVE) {
          baseImage = superImposeImage(baseImage, countArrowsImage, 75, 75);
        } else {
          baseImage = superImposeImage(baseImage, countArrowsImage, 32, 32);
        }
      } catch (IOException exception) {
        throw new IllegalStateException("Error reading arrow file resource");
      }
    }
    return baseImage;
  }

  private BufferedImage superImposeImage(BufferedImage baseImage, BufferedImage topImage,
                                         int xOffset, int yOffset) {

    int widthOfFinalImage = Math.max(baseImage.getWidth(), topImage.getWidth());
    int heightOfFinalImage = Math.max(baseImage.getHeight(), topImage.getHeight());

    //Creating the final image of width and height that will match the final image.
    // The image will be RGB+Alpha
    BufferedImage finalImage = new BufferedImage(widthOfFinalImage,
            heightOfFinalImage, BufferedImage.TYPE_INT_ARGB);
    Graphics finalImageGraphics = finalImage.getGraphics();
    finalImageGraphics.drawImage(baseImage, 0, 0, null);
    finalImageGraphics.drawImage(topImage, xOffset, yOffset, null);
    return finalImage;
  }

  private BufferedImage superImposeText(BufferedImage baseImage, String topText,
                                        int xOffset, int yOffset, Color color) {

    int widthOfFinalImage = baseImage.getWidth();
    int heightOfFinalImage = baseImage.getHeight();

    //Creating the final image of width and height that will match the final image.
    // The image will be RGB+Alpha
    BufferedImage finalImage = new BufferedImage(widthOfFinalImage,
            heightOfFinalImage, BufferedImage.TYPE_INT_ARGB);
    Graphics finalImageGraphics = finalImage.getGraphics();
    finalImageGraphics.drawImage(baseImage, 0, 0, null);
    finalImageGraphics.setColor(color);
    finalImageGraphics.drawString(topText, xOffset, yOffset);
    return finalImage;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (model.getGameStatus() != DungeonsGame.GameState.NOT_STARTED) {
      IGameHistory gameHistory = model.getGameHistory();
      if (gameHistory.size() > 1) {
        DungeonsGame.GameEvents recentEvent = gameHistory
                .getEventFromHistory(gameHistory.size() - 1);
        if (recentEvent == DungeonsGame.GameEvents.MOVE
                || recentEvent == DungeonsGame.GameEvents.MONSTER_HIT) {
          String previousCellName;
          List<String> playerTraversedCells = model.getPlayerInformation().getTraversedPath();
          if (recentEvent == DungeonsGame.GameEvents.MONSTER_HIT) {
            previousCellName = gameHistory.getLocationFromHistory(gameHistory.size() - 1);
          } else {
            previousCellName = playerTraversedCells.get(playerTraversedCells.size() - 2);
          }
          if (playerTraversedCells.contains(previousCellName)) {
            DungeonCell previousCell = null;
            List<List<DungeonCell>> cells = model.getDungeonCells();
            for (List<DungeonCell> rows : cells) {
              for (DungeonCell cell : rows) {
                if (cell.getCellName().equals(previousCellName)) {
                  previousCell = cell;
                  break;
                }
              }
              if (previousCell != null) {
                break;
              }
            }
            if (previousCell != null) {
              refreshCell(previousCell);
            }
          }
        }
      }
      DungeonCell cell = model.getPlayerCurrentLocation();
      refreshCell(cell);
    }
  }

  private void refreshCell(DungeonCell cell) {
    JLabel currentCellLabel = dungeonNameLabel.get(cell.getCellName());
    BufferedImage baseImage;
    try {
      baseImage = getFinalBaseImage(cell);
      if (cell.getCellName().equals(model.getPlayerCurrentLocation().getCellName())) {
        URL url = getClass().getResource("/images/player.png");
        if (url == null) {
          JOptionPane.showMessageDialog(getParent(),
                  "Searching the player resource file failed", "Error", JOptionPane.ERROR_MESSAGE);
          return;
        }
        BufferedImage playerImage = ImageIO.read(url);
        baseImage = superImposeImage(baseImage, playerImage, 32, 32);
        DungeonsGame.SmellType currentSmell = model.getCurrentLocationSmell();
        if (currentSmell == DungeonsGame.SmellType.STRONG) {
          url = getClass().getResource("/images/HighSmell.png");
          if (url == null) {
            JOptionPane.showMessageDialog(getParent(),
                    "Searching the high smell resource file failed", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
          }
          BufferedImage highSmellImage = ImageIO.read(url);
          baseImage = superImposeImage(baseImage, highSmellImage, 0, 0);
        } else if (currentSmell == DungeonsGame.SmellType.WEAK) {
          url = getClass().getResource("/images/LowSmell.png");
          if (url == null) {
            JOptionPane.showMessageDialog(getParent(),
                    "Searching the low smell resource file failed", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
          }
          BufferedImage lowSmellImage = ImageIO.read(url);
          baseImage = superImposeImage(baseImage, lowSmellImage, 0, 0);
        }
      }
      currentCellLabel.setIcon(new ImageIcon(baseImage));
    } catch (IOException e) {
      JOptionPane.showMessageDialog(getParent(),
              "Error when reading image", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  void addListeners(IDungeonControllerGui listener) {
    controller = listener;
    addKeyListener(this);
    MouseAdapter clickAdapter = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        if (model.getGameStatus() == DungeonsGame.GameState.PROGRESS) {
          int mouseX = e.getPoint().x;
          int mouseY = e.getPoint().y;
          DungeonCell currentCellLocation = model.getPlayerCurrentLocation();
          JLabel currentLabel = dungeonNameLabel.get(currentCellLocation.getCellName());
          int labelX = currentLabel.getX();
          int labelY = currentLabel.getY();
          if ((mouseX > (labelX + 128) && (mouseX < (labelX + 256)))
                  && (mouseY > labelY) && (mouseY < (labelY + 128))) {
            listener.handleMovePlayer(DungeonsGame.Direction.EAST);
          } else if ((mouseX > (labelX - 128) && (mouseX < labelX))
                  && (mouseY > labelY) && (mouseY < (labelY + 128))) {
            listener.handleMovePlayer(DungeonsGame.Direction.WEST);
          } else if ((mouseX > labelX && (mouseX < (labelX + 128)))
                  && (mouseY > (labelY - 128)) && (mouseY < labelY)) {
            listener.handleMovePlayer(DungeonsGame.Direction.NORTH);
          } else if ((mouseX > labelX && (mouseX < (labelX + 128)))
                  && (mouseY > labelY + 128) && (mouseY < labelY + 256)) {
            listener.handleMovePlayer(DungeonsGame.Direction.SOUTH);
          }
        }

      }
    };
    addMouseListener(clickAdapter);
  }

  @Override
  public void keyTyped(KeyEvent e) {
    //do nothing
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (model.getGameStatus() == DungeonsGame.GameState.PROGRESS) {
      int currentKeyCode = e.getKeyCode();
      if (previousKeyCode == 's' || previousKeyCode == 'S') {
        if (currentKeyCode == KeyEvent.VK_UP) {
          int distance = getDistanceInput();
          if (distance < 0) {
            return;
          }
          controller.handleShootArrow(DungeonsGame.Direction.NORTH, distance);
        } else if (currentKeyCode == KeyEvent.VK_DOWN) {
          int distance = getDistanceInput();
          if (distance < 0) {
            return;
          }
          controller.handleShootArrow(DungeonsGame.Direction.SOUTH, distance);
        } else if (currentKeyCode == KeyEvent.VK_RIGHT) {
          int distance = getDistanceInput();
          if (distance < 0) {
            return;
          }
          controller.handleShootArrow(DungeonsGame.Direction.EAST, distance);
        } else if (currentKeyCode == KeyEvent.VK_LEFT) {
          int distance = getDistanceInput();
          if (distance < 0) {
            return;
          }
          controller.handleShootArrow(DungeonsGame.Direction.WEST, distance);
        }
      }
      previousKeyCode = currentKeyCode;
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (model.getGameStatus() == DungeonsGame.GameState.PROGRESS) {
      previousKeyCode = -1;
      int currentKeyCode = e.getKeyCode();
      if (currentKeyCode == KeyEvent.VK_RIGHT) {
        controller.handleMovePlayer(DungeonsGame.Direction.EAST);
      } else if (currentKeyCode == KeyEvent.VK_LEFT) {
        controller.handleMovePlayer(DungeonsGame.Direction.WEST);
      } else if (currentKeyCode == KeyEvent.VK_UP) {
        controller.handleMovePlayer(DungeonsGame.Direction.NORTH);
      } else if (currentKeyCode == KeyEvent.VK_DOWN) {
        controller.handleMovePlayer(DungeonsGame.Direction.SOUTH);
      } else if (currentKeyCode == 't' || currentKeyCode == 'T') {
        controller.handleTreasurePickUpCurrentLocation();
      } else if (currentKeyCode == 'a' || currentKeyCode == 'A') {
        controller.handleArrowPickUpCurrentLocation();
      }
    }
  }

  private int getDistanceInput() {
    int distance = -1;
    try {
      distance = Integer.parseInt(JOptionPane.showInputDialog(this,
              "Please provide distance in the range 0-10", "Distance Input Dialog",
              JOptionPane.INFORMATION_MESSAGE));
    } catch (NumberFormatException exception) {
      JOptionPane.showMessageDialog(this, "Invalid distance provided.",
              "Error", JOptionPane.ERROR_MESSAGE);
    }
    return distance;
  }
}
