package game.model;

/**
 * This interface represents the monster entity in the game.
 * Note: A Location can have only one monster at a time.
 */
public interface Monster {

  /**
   * This enum represents the monster health.
   */
  enum Health {
    FULL,
    HALF,
    ZERO
  }

  /**
   * This function is used to get the name of the monster.
   *
   * @return name of the monster
   */
  String getName();

  /**
   * This function is used to get the health of the monster.
   *
   * @return health of the monster
   */
  Health getHealth();

  /**
   * This function is used to update the health of the monster.
   *
   * @param updatedHealth updated health value
   * @throws IllegalArgumentException if the health provided is invalid
   */
  void updateHealth(Health updatedHealth) throws IllegalArgumentException;
}
