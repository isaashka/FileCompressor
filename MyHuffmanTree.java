/**Sasha Ilinskaya
 * 12/01/2021
 * MyHuffmanTree class is meant to use MyHeapTree to build a tree with created internal nodes where the leaf nodes
 * hold the char values and frequencies. */

public class MyHuffmanTree {

    MyHeapTree minHeap = new MyHeapTree();

    MyNode root;
    int[] frequencies;
    char[] symbols;

    int size;

    String[] codes;
    char[] newSymbols;
    int[] newFrequencyOrder;
    int iterate = 0;

    int treeSize = 0;

    public MyHuffmanTree(int[] frequencies, char[] symbols, int size){
        this.frequencies = frequencies;
        this.symbols = symbols;

        this.size = size;
        this.root = new MyNode(0);

        this.codes = new String[size];
        this.newSymbols = new char[size];
        this.newFrequencyOrder = new int[size];
    }

    public String charToCode(char c){
        String code = "";
        char goal = newSymbols[0];

        int i = 0;
        while(goal != c){
            goal = newSymbols[i+1];
            i++;
        }
        code = codes[i];

        return code;
    }
//HUFFMAN TABLE
/**createHuffmanTable initializes the codes and newSymbols arrays to be the size of the number of symbols we're
 * dealing with. Then we call the getAllLeafCodes function to fill both arrays with symbols and the binary codes
 * for them.*/
    public void createHuffmanTable(){

//        System.out.println("Root is: "+ root.frequency);
//        System.out.println("children of root: "+ root.left.frequency + ", " + root.right.frequency);

        getAllLeafCodes(root, "");
        System.out.println();

    }
/**getAllLeafCodes is a recursive function that traces the Huffman tree in order to find its leaves,
 * every left turn it makes a 0 is added to the code and every right a 1 is added, once it finds a leaf
 * it adds the code to an array an the iterate index. The code always searches the left subtree first.*/
    public void getAllLeafCodes(MyNode current, String code){
        //base case, when all the codes have been filled into the String array
        if(iterate == size){
            return;
        }
        //second base case, when a recursion has led us to a leaf node, we record that node's symbol and
        //the code that's been written for it
        if(current.isLeaf){
            this.newSymbols[iterate] = current.symbol;
            this.codes[iterate] = code;
            this.newFrequencyOrder[iterate] = current.frequency;
            //once we reach a leaf node we can move on to the next leaf node, so we update to a new location
            //for the next leaf's code to go into
            iterate++;

            return;
        }
        //main recursive portion
        else{
            //the left node is always checked first, if it's not null we call this function again with the
            //left node as current, the code is updated
            if(current.left != null){
                getAllLeafCodes(current.left, (code += "0"));

                //if both children are null, we go back to the parent node of current, we also update the code to
                //not include the last added 0 or 1 so that we keep the path correct for the next leaf node
                String updatedCode = code.substring(0, code.length()-1);
                code = updatedCode;
            }

            //if the left node is null, we try for the right node, if it's not null we call this function
            //with the right node and updated code
            if(current.right != null){
                getAllLeafCodes(current.right, (code += "1"));

                String updatedCode = code.substring(0, code.length()-1);
                code = updatedCode;
            }

        }
    }


//HUFFMAN TREE
/**createHuffmanTree goes through the min heap tree and creates internal nodes*/
    public void createHuffmanTree(){
        while(minHeap.size > 1){
            createInternalNode();
        }
    }
    /**createInternalNode pops off first two smallest values of the min heap, adds their values together,
     * and creates a new internal node with the added values as its own value. Then the left and right children
     * of the internal node are assigned to be first and second (accordingly) and the internal node is added
     * back into the heap.*/
    public void createInternalNode(){
        MyNode first = minHeap.poll();
        MyNode second = minHeap.poll();

        MyNode internalNode = new MyNode(first.frequency + second.frequency);
        internalNode.left = first;
        internalNode.right = second;

        root = internalNode;

        minHeap.add(internalNode);

        treeSize += 1;
    }


//MIN HEAP TREE
/**fillHeap takes the values of frequencies array makes them into nodes and adds them into the min heap tree one by one*/
    public void fillHeap(){
        int iterate = 0;
        while(iterate < size){
            MyNode leaf = createLeaf(frequencies[iterate], symbols[iterate]);
            minHeap.add(leaf);
            iterate++;
        }
        //test
//        minHeap.printHeap();
    }
/**createLeaf takes in an integer and a char and creates a new node with those values*/
    public MyNode createLeaf(int val, char symbol){
        MyNode newLeaf = new MyNode(val);
        newLeaf.symbol = symbol;
        newLeaf.isLeaf = true;

        //9 bits for every leaf
        treeSize += 9;
        return newLeaf;
    }

}
