package game.view;

import game.controller.IDungeonControllerGui;
import game.model.DungeonsGame;

import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Spring;
import javax.swing.SpringLayout;


class Settings extends JDialog implements ActionListener {

  private final JTextField playerNameInput;
  private final JTextField rowInput;
  private final JTextField colInput;
  private final JTextField interconnectivityInput;
  private final JComboBox<String> wrapTypeValue;
  private final JTextField treasureInput;
  private final JTextField monsterCountInput;

  private final IDungeonControllerGui controller;

  /**
   * Constructor for settings. Note: This class is package private hence we
   * don't have to validate the parameters.
   * @param frame parent frame
   * @param controller controller to be used for callbacks
   */
  Settings(Frame frame, IDungeonControllerGui controller) {
    super(frame, "Settings", true);
    this.controller = controller;
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    JPanel formPanel = new JPanel(new SpringLayout());
    //player
    JLabel playerNameLabel = new JLabel("Player Name: ", JLabel.TRAILING);
    formPanel.add(playerNameLabel);
    playerNameInput = new JTextField();
    playerNameLabel.setLabelFor(playerNameInput);
    formPanel.add(playerNameInput);
    //wrap
    JLabel wrapType = new JLabel("Wrap Type: ", JLabel.TRAILING);
    formPanel.add(wrapType);
    wrapTypeValue = new JComboBox<>();
    wrapTypeValue.addItem("Wrapping");
    wrapTypeValue.addItem("Non-Wrapping");
    wrapType.setLabelFor(wrapTypeValue);
    formPanel.add(wrapTypeValue);
    //rows
    JLabel rowLabel = new JLabel("Rows: ", JLabel.TRAILING);
    formPanel.add(rowLabel);
    rowInput = new JTextField();
    rowLabel.setLabelFor(rowInput);
    formPanel.add(rowInput);
    //cols
    JLabel colLabel = new JLabel("Columns: ", JLabel.TRAILING);
    formPanel.add(colLabel);
    colInput = new JTextField();
    colLabel.setLabelFor(colInput);
    formPanel.add(colInput);
    //interconnectivity
    JLabel interconnectivityLabel = new JLabel("Interconnectivity: ", JLabel.TRAILING);
    formPanel.add(interconnectivityLabel);
    interconnectivityInput = new JTextField();
    interconnectivityLabel.setLabelFor(interconnectivityInput);
    formPanel.add(interconnectivityInput);
    //treasure distribution
    JLabel treasureDistribution = new JLabel("Treasure Distribution ", JLabel.TRAILING);
    formPanel.add(treasureDistribution);
    treasureInput = new JTextField();
    treasureDistribution.setLabelFor(treasureInput);
    formPanel.add(treasureInput);
    //monster count
    JLabel monsterCount = new JLabel("Monster Count: ", JLabel.TRAILING);
    formPanel.add(monsterCount);
    monsterCountInput = new JTextField();
    monsterCount.setLabelFor(monsterCountInput);
    formPanel.add(monsterCountInput);
    makeCompactGrid(formPanel,
            7, 2, //rows, cols
            6, 6,        //initX, initY
            6, 6);       //xPad, yPad
    mainPanel.add(formPanel);
    //button
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    JButton okButton = new JButton("Start");
    okButton.addActionListener(this); //
    buttonPanel.add(okButton);
    mainPanel.add(buttonPanel);
    add(mainPanel);
    pack();
    setLocationRelativeTo(frame);
  }

  /**
   * Aligns the first <code>rows</code> * <code>cols</code>
   * components of <code>parent</code> in
   * a grid. Each component in a column is as wide as the maximum
   * preferred width of the components in that column;
   * height is similarly determined for each row.
   * The parent is made just big enough to fit them all.
   *
   * @param rows     number of rows
   * @param cols     number of columns
   * @param initialX x location to start the grid at
   * @param initialY y location to start the grid at
   * @param xPad     x padding between cells
   * @param yPad     y padding between cells
   */
  public static void makeCompactGrid(Container parent,
                                     int rows, int cols,
                                     int initialX, int initialY,
                                     int xPad, int yPad) {
    SpringLayout layout;
    try {
      layout = (SpringLayout) parent.getLayout();
    } catch (ClassCastException exc) {
      System.err.println("The first argument to makeCompactGrid must use SpringLayout.");
      return;
    }

    //Align all cells in each column and make them the same width.
    Spring x = Spring.constant(initialX);
    for (int c = 0; c < cols; c++) {
      Spring width = Spring.constant(0);
      for (int r = 0; r < rows; r++) {
        width = Spring.max(width,
                getConstraintsForCell(r, c, parent, cols).getWidth());
      }
      for (int r = 0; r < rows; r++) {
        SpringLayout.Constraints constraints =
                getConstraintsForCell(r, c, parent, cols);
        constraints.setX(x);
        constraints.setWidth(width);
      }
      x = Spring.sum(x, Spring.sum(width, Spring.constant(xPad)));
    }

    //Align all cells in each row and make them the same height.
    Spring y = Spring.constant(initialY);
    for (int r = 0; r < rows; r++) {
      Spring height = Spring.constant(0);
      for (int c = 0; c < cols; c++) {
        height = Spring.max(height,
                getConstraintsForCell(r, c, parent, cols).getHeight());
      }
      for (int c = 0; c < cols; c++) {
        SpringLayout.Constraints constraints =
                getConstraintsForCell(r, c, parent, cols);
        constraints.setY(y);
        constraints.setHeight(height);
      }
      y = Spring.sum(y, Spring.sum(height, Spring.constant(yPad)));
    }

    //Set the parent's size.
    SpringLayout.Constraints pCons = layout.getConstraints(parent);
    pCons.setConstraint(SpringLayout.SOUTH, y);
    pCons.setConstraint(SpringLayout.EAST, x);
  }

  private static SpringLayout.Constraints getConstraintsForCell(
          int row, int col,
          Container parent,
          int cols) {
    SpringLayout layout = (SpringLayout) parent.getLayout();
    Component c = parent.getComponent(row * cols + col);
    return layout.getConstraints(c);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    DungeonsGame.WrapType wrapTypeData;
    String wrapType = String.valueOf(wrapTypeValue.getSelectedItem());
    if (wrapType.equals("Wrapping")) {
      wrapTypeData = DungeonsGame.WrapType.WRAPPING;
    } else {
      wrapTypeData = DungeonsGame.WrapType.NON_WRAPPING;
    }
    String playerName = playerNameInput.getText();
    int rows = getValidatedInput("Row", rowInput);
    int cols = getValidatedInput("Column", colInput);
    int interconnectivity = getValidatedInput("Interconnectivity", interconnectivityInput);
    int treasurePercent = getValidatedInput("Treasure Percent", treasureInput);
    int monsterCount = getValidatedInput("Monster Count", monsterCountInput);
    try {
      controller.startNewGame(wrapTypeData, rows, cols, interconnectivity,
              treasurePercent, monsterCount, playerName);
      dispose();
    } catch (IllegalArgumentException exception) {
      JOptionPane.showMessageDialog(getContentPane(),
              String.format("Issue encountered: %s", exception.getMessage()),
              "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  int getValidatedInput(String fieldName, JTextField field) {
    int processedInput = -1;
    try {
      processedInput = Integer.parseInt(field.getText());
    } catch (NumberFormatException exception) {
      JOptionPane.showMessageDialog(getContentPane(),
              String.format("%s input has to be a integer.", fieldName),
              "Error", JOptionPane.ERROR_MESSAGE);
    }
    return processedInput;
  }

}
