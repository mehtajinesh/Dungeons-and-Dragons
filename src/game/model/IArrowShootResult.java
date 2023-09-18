package game.model;

/**
 * This class is used to represent an interface that helps
 * convey shoot results outside the model.
 */
public interface IArrowShootResult {

  /**
   * gets the shoot state.
   *
   * @return state of the shooting
   */
  DungeonsGame.ShootResultStates getShootState();

  /**
   * gets the cell which got affected.
   *
   * @return cell name
   */
  String getCellName();
}
