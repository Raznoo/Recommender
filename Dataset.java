package recommender.sol;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * a class that handles dataset
 *
 * @param <T>
 */
public class Dataset<T extends IAttributeDatum> implements IAttributeDataset<T> {
  ArrayList<T> datums;
  LinkedList<String> attr;

  /**
   * a constructor for the dataset class
   *
   * @param attributes a linked list of strings representing the attibutes of
   *                   a datum of type T
   * @param rows       a linked list of all of the datums in the dataset
   */
  public Dataset(LinkedList<String> attributes, LinkedList<T> rows) {
    this.datums = new ArrayList<T>();

    for (T row : rows) {
      this.datums.add(row);
    }

    this.attr = attributes;
  }

  @Override
  public LinkedList<String> getAttributes() {
    if (this.datums.size() == 0) {
      throw new RuntimeException("Tried to get attributes of empty dataset");
    } else {
      return this.attr;
    }
  }

  @Override
  public boolean allSameValue(String ofAttribute) {
    for (int i = 0; i < this.size() - 1; i++) {
      if (!this.datums.get(i).getValueOf(ofAttribute)
        .equals(this.datums.get(i + 1).getValueOf(ofAttribute))) {
        return false;
      }
    }

    return true;//vacuously true for empty dataset
  }

  @Override
  public int size() {
    return this.datums.size();
  }

  @Override
  public void addLast(T datum) {
    this.datums.add(datum);
  }

  @Override
  public LinkedList<IAttributeDataset<T>> partition(String onAttribute) {
    LinkedList<IAttributeDataset<T>> outputData = new LinkedList<IAttributeDataset<T>>();

    if (this.size() != 0) {
      for (T datum : this.datums) {
        boolean createBSwitch = false;

        for (IAttributeDataset<T> datasetNode : outputData) {
          if (datum.getValueOf(onAttribute)
            .equals(datasetNode.getSharedValue(onAttribute))) {
            createBSwitch = true;
            datasetNode.addLast(datum);
          }
        }

        if (!createBSwitch) {
          LinkedList<T> firstDatum = new LinkedList<T>();
          firstDatum.add(datum);
          IAttributeDataset<T> newNode = new Dataset<T>(this.attr, firstDatum);
          outputData.addFirst(newNode);
        }

      }
    }

    return outputData;
  }

  @Override
  public Object getSharedValue(String ofAttribute) {
    if (this.datums.size() == 0) {
      throw new RuntimeException("Tried to get shared value of empty dataset");
    } else {
      return this.datums.get(0).getValueOf(ofAttribute);
    }
  }

  @Override
  public Object mostCommonValue(String ofAttribute) {
    if (this.datums.size() == 0) {
      throw new RuntimeException("Tried to get most common value of empty " +
        "dataset");
    } else {
      LinkedList<IAttributeDataset<T>> newData = this.partition(ofAttribute);
      int greatestIndex = 0;

      for (int i = 0; i < newData.size(); i++) {
        if (newData.get(greatestIndex).size() < newData.get(i).size()) {
          greatestIndex = i;
        }
      }

      return newData.get(greatestIndex).getSharedValue(ofAttribute);
    }
  }
}

