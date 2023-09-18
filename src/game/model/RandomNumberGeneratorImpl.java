package game.model;

import java.util.Random;

/**
 * This class is used to generate random numbers. It is used for mocking the random functionality
 * as well as it supports true randomness.
 */
public class RandomNumberGeneratorImpl implements RandomNumberGenerator {

  private final Random javaRandom;
  private final int[] predefinedValues;
  private int countRandomValues;
  private final int seedValue;

  /**
   * This function is a constructor for creation of random number generator.(True random)
   */
  public RandomNumberGeneratorImpl() {
    javaRandom = new Random();
    seedValue = javaRandom.nextInt();
    javaRandom.setSeed(seedValue);
    this.predefinedValues = null;
    countRandomValues = 0;
  }

  /**
   * This function is a constructor for creation of random number generator.(Predefined random)
   *
   * @param predefinedValues predefined values of randomness
   * @throws IllegalArgumentException if the predefined values are null
   */
  public RandomNumberGeneratorImpl(int... predefinedValues) throws IllegalArgumentException {
    if (predefinedValues == null) {
      throw new IllegalArgumentException("Arguments cannot be null");
    }
    this.predefinedValues = predefinedValues;
    countRandomValues = 0;
    javaRandom = null;
    seedValue = 10;
  }

  @Override
  public int generateRandomValueForRange(int minimumRangeValue, int maximumRangeValue)
          throws IllegalStateException {
    if (javaRandom == null) {
      assert predefinedValues != null;
      if (predefinedValues.length == countRandomValues) {
        throw new IllegalStateException("Pre defined values are over.");
      }
      int value = predefinedValues[countRandomValues];
      countRandomValues++;
      return value;
    } else {
      if (minimumRangeValue > maximumRangeValue) {
        throw new IllegalArgumentException("Minimum Value cannot be more than Maximum Value");
      }
      return javaRandom.nextInt(maximumRangeValue - minimumRangeValue)
              + minimumRangeValue;
    }
  }

  @Override
  public int getRandomSeed() {
    return seedValue;
  }

  @Override
  public void setRandomSeed(int seed) {
    if (predefinedValues == null) {
      javaRandom.setSeed(seed);
    }
  }
}