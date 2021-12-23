/**Sasha Ilinskaya
 * 12/01/2021
 * MyNode class is meant to hold a frequency value, a char value, and pointers to its left and right child.*/

public class MyNode {

    int frequency;
    char symbol;
    MyNode left, right;

    /**isLeaf checks if the current node is a leaf which is changed to true in MyHuffmanTree when creating leaf nodes*/
    boolean isLeaf = false;

/**Constructor takes an int value and sets it to the class value "frequency" and it also sets left and right
 * children to be null*/
    public MyNode(int value){
        this.frequency = value;

        this.left = null;
        this.right = null;
    }



}
