package recommender.sol;

/**
 * a class that handles INode
 */
public class Node implements INode {
  String currentAttributeToTest;
  INode[] branches;// paths to next node
  Boolean isBest;//uses mostCommonValue
  Object previousDecisionValue;//may be ok to remove

  /**
   * a constructor for the Node class
   *
   * @param test         a string representing the current attribute to test for
   * @param branchNum    an integer representing the number of possible values
   *                     of the test attribute (based off the input dataset)
   * @param prevdecision an object representing the value of the test
   *                     attribute of the previous node's test attribute.
   */
  public Node(String test, int branchNum, Object prevdecision) {
    this.currentAttributeToTest = test;
    this.branches = new INode[branchNum];
    this.previousDecisionValue = prevdecision;
    this.isBest = false;
  }

  @Override
  public Object lookupDecision(IAttributeDatum attrVals) {
    Object decision = null;
    boolean branchExist = false;

    for (INode branch : this.branches) {
      if (attrVals.getValueOf(this.currentAttributeToTest)
        .equals(branch.getDecision())) {
        branchExist = true;
        decision = branch.lookupDecision(attrVals);
      }
    }

    if (!branchExist) {
      for (INode branch : this.branches) {
        if (branch.getIsBest()) {
          decision = branch.lookupDecision(attrVals);
        }
      }
    }

    return decision;
  }

  @Override
  public void makeBest() {
    this.isBest = true;
  }

  @Override
  public Object getDecision() {
    return this.previousDecisionValue;
  }

  @Override
  public boolean getIsBest() {
    return this.isBest;
  }

  // print tree
  @Override
  public void printNode(String leadspace) {
    //do print things
  }
}
