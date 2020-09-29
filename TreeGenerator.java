package recommender.sol;

import java.util.LinkedList;
import java.util.Random;

/**
 * a class handling tree generation
 *
 * @param <T> the datum type
 */
public class TreeGenerator<T extends IAttributeDatum> implements IGenerator {
  INode decisionTree;
  IAttributeDataset<T> dataset;

  /**
   * constructor for TreeGenerator
   *
   * @param dataset
   */
  public TreeGenerator(IAttributeDataset<T> dataset) {
    this.dataset = dataset;
    this.decisionTree = null;
  }

  @Override
  // build a decision tree to predict the named attribute
  public INode buildClassifier(String targetAttr) {
    LinkedList<String> attributes = this.dataset.getAttributes();

    Random randNum = new Random();
    for (int i = 0; i < 100; i++) {//Super scramble!
      int rand_int = randNum.nextInt(attributes.size());
      String thingy = attributes.remove(rand_int);
      attributes.addLast(thingy);
    }

    attributes.remove(targetAttr);
    attributes.addLast(targetAttr);

    INode updatedDecisionTree = treeBuilder(targetAttr, attributes,
      this.dataset, null, 0);
    this.decisionTree = updatedDecisionTree;

    return this.decisionTree;
  }

  /**
   * Helper method for buildtree
   *
   * @param targetAttr a string representing the attribute we're looking for
   *                   (bottom of the decision tree)
   * @param attributes a Linkedlist of string representing the current list of
   *                   all available attributes to make the tree from.
   * @param dataset    an IAttributeDataset<T> containing the datums to build
   *                   the rest of the tree from (compared to current node)
   * @param prevDecVal an object representing the value of the attribute of
   *                   the last node. This is a requirement member for the
   *                   partitioned subset used to create the remainder of the
   *                   tree.
   * @param globalI    an integer representing the current depth of the tree
   * @return a decision tree with target attribute returned as the leaves
   */
  public INode treeBuilder(String targetAttr, LinkedList<String> attributes,
                           IAttributeDataset<T> dataset, Object prevDecVal,
                           int globalI) {

    String testAttr = attributes.get(globalI);
    Object mostCommon = dataset.mostCommonValue(testAttr);
    LinkedList<IAttributeDataset<T>> recurNodes = dataset.partition(testAttr);
    Node newNode = new Node(testAttr, recurNodes.size(), prevDecVal);

    int i = 0;
    for (int j = 0; j < newNode.branches.length; j++) {
      IAttributeDataset<T> nextDataset = recurNodes.get(i);
      Object toPassOn = nextDataset.getSharedValue(testAttr);

      if (!testAttr.equals(targetAttr)) {
        int localI = globalI;
        newNode.branches[j] = treeBuilder(targetAttr, attributes,
          nextDataset, toPassOn, ++localI);

        if (mostCommon.equals(nextDataset.getSharedValue(testAttr))) {
          newNode.branches[j].makeBest();
        }
      } else {
        newNode.branches[j] = new Leaf(toPassOn,
          toPassOn.equals(mostCommon));
      }

      i++;
    }
    //need to make isBest
    return newNode;
  }

  @Override
  // produce the decision predicted for the given datum
  public Object lookupRecommendation(IAttributeDatum forVals) {
    return this.decisionTree.lookupDecision(forVals);
  }

  @Override
  // print the decision tree
  public void printTree() {
    //do print things
  }
}
