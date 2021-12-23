/**Sasha Ilinskaya
 * 12/01/2021
 * MyHeapTree is a class that works with the Node class to construct a heap tree and keep its heap properties
 * through addition and deletion.*/


import java.util.Arrays;

public class MyHeapTree {

    int capacity = 2;
    int size = 0;
    MyNode[] frequencies = new MyNode[capacity];

    public MyHeapTree(){

    }

    public void printHeap(){
        System.out.print("HEAP TREE: ");
        for(MyNode node : frequencies){
            System.out.print(node.frequency + "-" + node.symbol + ", ");
        }
        System.out.println();
    }
//PARENT
/**the following three functions give information on the parent node, these are helpful for the
 * heapUp function so that code can be concise. hasParent checks if the current node has a parent,
 * getParentIndex returns parent's index, and parentVal returns the frequency associated with the parent.*/
    public boolean hasParent(int index){
        return getParentIndex(index) >= 0;
    }
    public int getParentIndex(int index){
        return (index - 1) / 2;

    }
    public int parentVal(int index){
        return frequencies[getParentIndex(index)].frequency;
    }

//LEFT CHILD
/**the next 3 functions give information on the left child, if the current node has one, the index
 * of the child, and the frequency value that's associated with it.*/
    public boolean hasLeftChild(int index){
        return getLeftIndex(index) < size;

    }
    public int getLeftIndex(int index){
        return (index * 2) + 1;
    }
    public int getLeftVal(int index){
        return frequencies[getLeftIndex(index)].frequency;
    }

//RIGHT CHILD
/**the next 3 function do the same things as the ones for the left child, but this is for the right child*/
    public boolean hasRightChild(int index){
        return getRightIndex(index) < size;
    }
    public int getRightIndex(int index){
        return (index * 2) + 2;
    }
    public int getRightVal(int index){
        return frequencies[getRightIndex(index)].frequency;
    }

//HEAP FUNCTIONS
/**add function adds a new node passed as input at the very end of the heap array, then it's
 * bubbled up to its right spot*/
    public void add(MyNode newMyNode){
        extraCapacity();
        frequencies[size] = newMyNode;
        size++;

        heapUp();
    }

/**poll is the delete function, it takes the smallest value off of the heap tree (which is at the root)
 * and takes the last value in the tree and sets it as the new root. This value is then bubbled down to
 * its right spot.*/
    public MyNode poll(){
        if(size == 0) throw new IllegalStateException();
        MyNode root = frequencies[0];
        frequencies[0] = frequencies[size - 1];
        size--;
        heapDown();

        return root;
    }

/**peek returns the value of the smallest node in the heap tree (which is the first node)*/
    public MyNode peek(){
        return frequencies[0];
    }

/**swap is a helper function for my heapUp and heapDown, it swaps two values (nodes in my case) to be in
 * each other's old indices.*/
    public void swap(int firstInd, int secondInd){
        MyNode temp = frequencies[firstInd];
        frequencies[firstInd] = frequencies[secondInd];
        frequencies[secondInd] = temp;
    }

/**heapUp is used when a new value is added, it checks if the new value is smaller than its parent,
 * if it is then it's bubbled up or swapped with its parent until the parent is smaller*/
    public void heapUp(){
        int index = size - 1;
        while(hasParent(index) && parentVal(index) > frequencies[index].frequency){
            swap(getParentIndex(index), index);
            index = getParentIndex(index);
        }
    }
/**heapDown is the opposite of heapUp, heapDown is used when the smallest value is polled off the heap tree.
 * The last value is set as the new root, we check this value against the smaller of its two children,
 * if it's bigger then it's swapped with the smaller child until the smaller child is bigger than the value.*/
    public void heapDown(){
        int index = 0;
        while(hasLeftChild(index)){
            int smallerChildInd = getLeftIndex(index);
            if(hasRightChild(index) && getLeftVal(index) > getRightVal(index)){
                smallerChildInd = getRightIndex(index);
            }

            if(frequencies[index].frequency > frequencies[smallerChildInd].frequency){
                swap(index, smallerChildInd);
                index = smallerChildInd;
            }
            else break;
        }


    }

/**extraCapacity is needed when we're adding a new value and we don't have enough space in the array.
 * Arrays are difficult to work with because of their need for specific sizing, so this is my way of combating that.*/
    public void extraCapacity(){
        if(size == capacity){
            capacity *= 2;
            frequencies = Arrays.copyOf(frequencies, capacity);
        }
    }


}
