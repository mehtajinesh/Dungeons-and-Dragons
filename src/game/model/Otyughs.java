package game.model;

/**
 * This class is used to implement a monster of type Otyughs. It includes storing
 * information about name and health of the monster.
 */
public class Otyughs implements Monster {
  private final String name;
  private Health health;

  /**
   * Constructor for Otyughs instance.
   *
   * @param name name of the Otyughs.
   * @throws IllegalArgumentException if the name provided is invalid.
   */
  public Otyughs(String name) throws IllegalArgumentException {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Name cannot be null or empty");
    }
    this.name = name;
    this.health = Health.FULL;
  }

  /**
   * Copy constructor.
   *
   * @param monster monster to be copied
   * @throws IllegalArgumentException if the monster is invalid.
   */
  public Otyughs(Monster monster) throws IllegalArgumentException {
    if (monster == null) {
      throw new IllegalArgumentException("Monster cannot be null or empty");
    }
    this.name = monster.getName();
    this.health = monster.getHealth();
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Health getHealth() {
    return health;
  }

  @Override
  public void updateHealth(Health updatedHealth) throws IllegalArgumentException {
    if (updatedHealth == null) {
      throw new IllegalArgumentException("Invalid Health provided");
    }
    health = updatedHealth;
  }
}
