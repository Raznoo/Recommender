package recommender.sol;

/**
 * a class that handles vegetable class
 */
public class Vegetable implements IAttributeDatum {
  String color;
  Boolean lowCarb;
  Boolean highFiber;
  Boolean likeToEat;

  /**
   * constructor for vegetable class
   *
   * @param col         string representing the color of the vegetable
   * @param isLowCarb   a boolean on whether or not the vegetable is low in
   *                    carbohydrates
   * @param isHighFiber a boolean on whether or not the vegetable is high in
   *                    fiber
   * @param doLikeToEat a boolean on whether or not the vegetable is liked to
   *                    be eaten
   */
  public Vegetable(String col, Boolean isLowCarb, Boolean isHighFiber,
                   Boolean doLikeToEat) {
    this.color = col;
    this.highFiber = isHighFiber;
    this.lowCarb = isLowCarb;
    this.likeToEat = doLikeToEat;
  }

  @Override
  public Object getValueOf(String attributeName) {
    if (attributeName.equals("color")) {
      return this.color;
    } else if (attributeName.equals("lowCarb")) {
      return this.lowCarb;
    } else if (attributeName.equals("highFiber")) {
      return this.highFiber;
    } else if (attributeName.equals("likeToEat")) {
      return this.likeToEat;
    } else {
      throw new RuntimeException("Attribute is not part of datum");
    }
  }

}
