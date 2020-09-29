package recommender.sol;

/*
 * A node in the decision tree
 */
public interface INode {
  // traverse tree based on attribute values to retrieve decision
  public Object lookupDecision(IAttributeDatum attrVals);

  // print tree
  public void printNode(String leadspace);

  //gets the decision required to get to that node
  public Object getDecision();

  //a boolean that returns true if previousValue field in the node is the
  // most common value of the dataset, false otherwise
  public boolean getIsBest();

  //a method that sets the isBest field of the current nodde to true
  public void makeBest();
}
