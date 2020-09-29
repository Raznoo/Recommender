package recommender.sol;

/**
 * a class handling leaf
 */
public class Leaf implements INode {
  Object decision;
  Boolean isBest;

  /**
   * @param decision an object representing the final decision in the tree
   * @param isBest   a boolean that will be true if this is the most common value
   *                 (leaf) in the dataset
   */
  public Leaf(Object decision, Boolean isBest) {
    this.decision = decision;
    this.isBest = isBest;
  }

  // traverse tree based on attribute values to retrieve decision
  @Override
  public Object lookupDecision(IAttributeDatum attrVals) {
    return this.decision;
  }

  // print tree
  @Override
  public void printNode(String leadspace) {
    //do print things
  }

  @Override
  public Object getDecision() {
    return this.decision;
  }

  @Override
  public boolean getIsBest() {
    return this.isBest;
  }

  @Override
  public void makeBest() {
    this.isBest = true;
  }
}
