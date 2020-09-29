package recommender.sol;

import tester.Tester;

import java.util.LinkedList;

public class RecommenderTests {
  public IAttributeDatum setupSpinachDatum() {
    return new Vegetable("green", true, true, false);
  }

  public IAttributeDatum newVeg() {
    return new Vegetable("red", false, false, false);
  }

  public IAttributeDatum setupKaleDatum() {
    return new Vegetable("green", true, true, true);
  }

  public IAttributeDatum setupPeasDatum() {
    return new Vegetable("green", false, true, true);
  }

  public IAttributeDatum setupCarrotDatum() {
    return new Vegetable("orange", false, false, false);
  }

  public IAttributeDatum setupLettuceDatum() {
    return new Vegetable("green", true, false, true);
  }

  public LinkedList<String> setupVegetableAttributesLL() {
    LinkedList<String> list = new LinkedList<String>();//is name an attribute?
    list.addLast("color");
    list.addLast("lowCarb");
    list.addLast("highFiber");
    list.addLast("likeToEat");
    return list;
  }

  public IAttributeDataset<IAttributeDatum> setup1ElemDataset() {
    LinkedList<IAttributeDatum> datumArrayList = new LinkedList<IAttributeDatum>();
    datumArrayList.add(setupLettuceDatum());
    return new Dataset<IAttributeDatum>(setupVegetableAttributesLL(),
      datumArrayList);
  }

  public IAttributeDataset<IAttributeDatum> setupSTDDataset() {
    LinkedList<IAttributeDatum> datumArrayList = new LinkedList<IAttributeDatum>();
    datumArrayList.add(setupLettuceDatum());
    datumArrayList.add(setupCarrotDatum());
    datumArrayList.add(setupPeasDatum());
    datumArrayList.add(setupKaleDatum());
    datumArrayList.add(setupSpinachDatum());
    return new Dataset<IAttributeDatum>(setupVegetableAttributesLL(),
      datumArrayList);
  }

  public IAttributeDataset<IAttributeDatum> setupEmptyDataset() {
    LinkedList<IAttributeDatum> datumArrayList = new LinkedList<IAttributeDatum>();
    return new Dataset<IAttributeDatum>(setupVegetableAttributesLL(),
      datumArrayList);
  }

  public IAttributeDataset<IAttributeDatum> setupAllGreenDataset() {
    LinkedList<IAttributeDatum> datumArrayList = new LinkedList<IAttributeDatum>();
    datumArrayList.add(setupLettuceDatum());
    datumArrayList.add(setupPeasDatum());
    datumArrayList.add(setupKaleDatum());
    datumArrayList.add(setupSpinachDatum());
    return new Dataset<IAttributeDatum>(setupVegetableAttributesLL(),
      datumArrayList);
  }

  public IAttributeDataset<IAttributeDatum> setupAllOrangeDataset() {
    LinkedList<IAttributeDatum> datumArrayList = new LinkedList<IAttributeDatum>();
    datumArrayList.add(setupCarrotDatum());
    return new Dataset<IAttributeDatum>(setupVegetableAttributesLL(),
      datumArrayList);
  }

  public LinkedList<IAttributeDataset<IAttributeDatum>> setupColorPartition() {
    LinkedList<IAttributeDataset<IAttributeDatum>> outputList =
      new LinkedList<IAttributeDataset<IAttributeDatum>>();
    outputList.add(setupAllOrangeDataset());
    outputList.add(setupAllGreenDataset());
    return outputList;
  }

  public TreeGenerator setupSTDTreeGenerator() {
    IAttributeDataset<IAttributeDatum> dataset = setupSTDDataset();
    TreeGenerator tree = new TreeGenerator(dataset);
    return tree;
  }

  public INode setupGrnLeaf() {
    return new Leaf("green", true);
  }

  public INode setupOrangeLeaf() {
    return new Leaf("orange", false);
  }

  public INode setupSTDNode() {
    INode grnLeaf = setupGrnLeaf();
    INode orangeLeaf = setupOrangeLeaf();
    Node node = new Node("color", 2, null);
    node.branches[0] = grnLeaf;
    node.branches[1] = orangeLeaf;
    return node;
  }

  /**
   * test for getAttributes
   */
  public void testGetAttributes(Tester t) {
    IAttributeDataset<IAttributeDatum> dataset = setupSTDDataset();
    LinkedList<String> compareTo = setupVegetableAttributesLL();
    IAttributeDataset<IAttributeDatum> emptyDS = setupEmptyDataset();
    t.checkExpect(dataset.getAttributes(), compareTo);
    t.checkException(new RuntimeException("Tried to get attributes of empty " +
      "dataset"), emptyDS, "getAttributes", null);
  }

  /**
   * test for getAttributes
   *
   * @param t
   */
  public void testPartition(Tester t) {
    IAttributeDataset<IAttributeDatum> dataset = setupSTDDataset();
    LinkedList<IAttributeDataset<IAttributeDatum>>
      compareTo = setupColorPartition();
    t.checkExpect(dataset.partition("color"), compareTo);
  }

  /**
   * test for allSameValue
   */
  public void testAllSameValue(Tester t) {
    IAttributeDataset<IAttributeDatum> dataset = setupAllGreenDataset();
    IAttributeDataset<IAttributeDatum> dataset2 = setupSTDDataset();
    IAttributeDataset<IAttributeDatum> emptyDS = setupEmptyDataset();
    t.checkExpect(emptyDS.allSameValue("color"), true);
    t.checkExpect(dataset.allSameValue("color"), true);
    t.checkExpect(dataset2.allSameValue("color"), false);
  }

  /**
   * test for size
   *
   * @param t
   */
  public void testSize(Tester t) {
    IAttributeDataset<IAttributeDatum> dataset = setupSTDDataset();
    IAttributeDataset<IAttributeDatum> emptyDS = setupEmptyDataset();
    t.checkExpect(emptyDS.size(), 0);
    t.checkExpect(dataset.size(), 5);
  }

  /**
   * test for addLast
   */
  public void testAddLast(Tester t) {
    IAttributeDataset<IAttributeDatum> emptyDS = setupEmptyDataset();
    IAttributeDataset<IAttributeDatum> compareTo = setup1ElemDataset();
    emptyDS.addLast(setupLettuceDatum());
    t.checkExpect(emptyDS, compareTo);
  }

  /**
   * test for getSharedValue
   */
  public void testGetSharedValue(Tester t) {
    IAttributeDataset<IAttributeDatum> emptyDS = setupEmptyDataset();
    IAttributeDataset<IAttributeDatum> datasetG = setupAllGreenDataset();
    IAttributeDataset<IAttributeDatum> datasetO = setupAllOrangeDataset();
    t.checkExpect(datasetG.getSharedValue("color"), "green");
    t.checkExpect(datasetO.getSharedValue("color"), "orange");

    t.checkException(new RuntimeException("Tried to get shared value of" +
      " empty dataset"), emptyDS, "getSharedValue", "color");
  }

  /**
   * test for mostCommonValue
   *
   * @param t
   */
  public void testMostCommonValue(Tester t) {
    IAttributeDataset<IAttributeDatum> emptyDS = setupEmptyDataset();
    IAttributeDataset<IAttributeDatum> dataset = setupSTDDataset();
    t.checkExpect(dataset.mostCommonValue("color"), "green");
    t.checkException(new RuntimeException("Tried to get most common value " +
      "of empty dataset"), emptyDS, "mostCommonValue", "color");
  }

  /////////////////////////////////////////////////////////////////////////////
  //TESTS FOR IATTRIBUTEDATUM//////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  /**
   * test for getValueOf
   *
   * @param t
   */
  public void testGetValueOf(Tester t) {
    IAttributeDatum datum = setupLettuceDatum();
    t.checkExpect(datum.getValueOf("color"), "green");
    t.checkException(new RuntimeException("Attribute is not part of datum"),
      datum, "getValueOf", "isRipe");
  }

  /////////////////////////////////////////////////////////////////////////////
  //TESTS FOR INODE////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  /**
   * test for makeBest
   *
   * @param t
   */
  public void testMakeBest(Tester t) {
    INode node = setupSTDNode();
    INode orangeLeaf = setupOrangeLeaf();
    t.checkExpect(orangeLeaf.getIsBest(), false);
    orangeLeaf.makeBest();
    t.checkExpect(orangeLeaf.getIsBest(), true);
    t.checkExpect(node.getIsBest(), false);
    node.makeBest();
    t.checkExpect(node.getIsBest(), true);
  }

  /**
   * test for getIsBest
   *
   * @param t
   */
  public void testGetIsBest(Tester t) {
    INode orangeLeaf = setupOrangeLeaf();
    INode greenLeaf = setupGrnLeaf();
    INode node = setupSTDNode();
    t.checkExpect(orangeLeaf.getIsBest(), false);
    t.checkExpect(greenLeaf.getIsBest(), true);
    t.checkExpect(node.getIsBest(), false);
  }

  /**
   * test for getDecision
   *
   * @param t
   */
  public void testGetDecision(Tester t) {
    INode orangeLeaf = setupOrangeLeaf();
    INode greenLeaf = setupGrnLeaf();
    INode node = setupSTDNode();
    t.checkExpect(orangeLeaf.getDecision(), "orange");
    t.checkExpect(greenLeaf.getDecision(), "green");
    t.checkExpect(node.getDecision(), null);
  }

  public static void main(String[] args) {
    Tester.run(new RecommenderTests());
  }
}
