package game.model;

/**
 * This class implements the arrow shoot results interface. This includes
 * storing shooting state and the cell affected.
 */
public class ArrowShootResult implements IArrowShootResult {
  private final DungeonsGame.ShootResultStates shootState;
  private final String cellName;

  /**
   * Constructor for arrow shoot result.
   *
   * @param shootState shoot state
   * @param cellName   cell name
   */
  public ArrowShootResult(DungeonsGame.ShootResultStates shootState, String cellName) {
    if (shootState == null || cellName == null) {
      throw new IllegalArgumentException("Invalid Parameters");
    }
    this.shootState = shootState;
    this.cellName = cellName;
  }

  @Override
  public DungeonsGame.ShootResultStates getShootState() {
    return shootState;
  }

  @Override
  public String getCellName() {
    return cellName;
  }
}
