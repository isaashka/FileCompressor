/**Sasha Ilinskaya
 * 12/03/2021
 * Compressor class uses MyHuffmanTree, MyHeapTree, and MyNode in order to create a Huffman table using a sample file.
 * The program then checks if a binary file can be created and prints a message if it can or not. The program also
 * prints the symbols and their huffman codes in the same order.*/

import java.util.Arrays;
import java.util.Scanner;

public class MyCompressor {

    static TextFile newFile;
    static int[] frequencies;
    static char[] symbols;


    public static void main(String args[]){
        char readOrWrite = initializeFile();

        if(readOrWrite == 'r' || readOrWrite == 'R'){
            readPath(newFile);
        }

        else{
            //writePath
            //creates a new file and allows user to input words into the file
        }
    }

/**initializeFile is a method that works with the user to determine wether they want to read or write a file
 * and the name of the file they wish to use/create.*/
    public static char initializeFile(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Type in the name of your file and if you want to read (R,r) or write (W,w) it: ");
        String fileName = scan.nextLine();
        System.out.println();
        char readOrWrite = scan.next().charAt(0);
        newFile = new TextFile(fileName, readOrWrite);

        return readOrWrite;
    }

/**readPath is called when the user wants to read an existing file. The file is read all the way through and each
 * symbol and its frequency is recorded. A Huffman tree is then created using this information. The file is then closed
 * and we check if a binary version of this file is worth creating by comparing sizes of the two.*/
    public static void readPath(TextFile newFile){
        frequencies = frequencyCount(newFile);
        symbols = symbolArray();
        getRidOfZeros();

        MyHuffmanTree ht = new MyHuffmanTree(frequencies, symbols, frequencies.length);
        createHuffmanTable(ht);

        newFile.close();

        checkFileSizes(ht);

    }
/**the commented code is the unfinished implementation of writing a Binary File from the word file. The next step
 * after checking the file sizes (checkFileSizes) would be to create the Binary file and fill it with the Huffman
 * tree, Magic Number, and the whole original file converted to binary using Huffman. */
//    public static void compressFile(MyHuffmanTree ht){
////        BinaryFile newBinary = new BinaryFile("TestFileCompressed", 'w');
//        MyNode node = ht.root;
//        System.out.print("Huffman: ");
//        printPreOrder(ht, node);
//
//        System.out.println();
//        newFile.rewind();
//        System.out.print("Compressed File: ");
//        writeBinary(ht);
//
//    }
//    //printPreOrder prints out all values starting from the root and then printing each following
//    //value as it traverses the tree from the left-most values to the right
//    public static void printPreOrder(MyHuffmanTree ht, MyNode node){
//        if(node == null){
//            return;
//        }
//        else{
//            if(!node.isLeaf) {
//                System.out.print("1 ");
//            }
//            else{
//                System.out.println("0 ");
//            }
//            System.out.print(node.frequency + "   ");
//            printPreOrder(ht, node.left);
//            printPreOrder(ht, node.right);
//        }
//    }
//
//    public static void writeBinary(MyHuffmanTree ht){
//        while(!newFile.EndOfFile()){
//            char c = newFile.readChar();
//            ht.charToCode(c);
//            System.out.print(" ");
//        }
//    }

/**checkFileSize checks if the compressed file will be larger or smaller than the original file*/
    public static void checkFileSizes(MyHuffmanTree ht){
        //uncompressed file size
        int sizeUncompressed = 0;
        for(int i : frequencies){
            sizeUncompressed += i;
        }
        sizeUncompressed *= 8;

        //compressed file size
        int sizeCompressed = 0;
        int j = 0;
        for(int i : ht.newFrequencyOrder){
            sizeCompressed += i * ht.codes[j].length();
            j++;
        }
        sizeCompressed += ht.treeSize;
        //extra 2 bytes for magic number
        sizeCompressed += 16;
        //header info
        sizeCompressed += 32;

        if(sizeCompressed < sizeUncompressed){
//            compressFile(ht);
            System.out.println("compressable!");
        }
        else{
            System.out.println("The file is not compressable due to its size being larger than/equal to original!");
        }
    }

/**createHuffmanTable uses MyHuffmanTable properties to build a huffman table*/
    public static void createHuffmanTable(MyHuffmanTree ht){
        ht.fillHeap();
        ht.createHuffmanTree();
        ht.createHuffmanTable();

        printSymbolsAndCodes(ht);
    }
/**testing function to see the results of huffman table*/
    public static void printSymbolsAndCodes(MyHuffmanTree ht){
//        System.out.print("Original char array: ");
//        for(char c : symbols){
//            System.out.print(c + ", ");
//        }
//        System.out.println();
//
//        System.out.print("Original frequency array: ");
//        for(int i : frequencies){
//            System.out.print(i + ", ");
//        }
//        System.out.println();

        //NEW
        //chars
        System.out.print("new Chars: ");
        for(char c : ht.newSymbols){
            System.out.print(c + ",    ");
        }
        System.out.println();
//        //freq
//        System.out.print("new Frequencies: ");
//        for(int i : ht.newFrequencyOrder){
//            System.out.print(i + ",    ");
//        }
//        System.out.println();

        //codes
        System.out.print("Codes: ");
        for(String s : ht.codes){
            System.out.print(s + ", ");
        }
        System.out.println();
    }



    /**frequencyCount counts how many times each symbols comes up and adds it to the int array, each index
     * corresponds to the ascii number of the symbol*/
    public static int[] frequencyCount(TextFile file){
        int[] frequencies = new int[256];


        while(!file.EndOfFile()){
            char symbol = file.readChar();
            int index = (int) symbol;
            frequencies[index] += 1;
        }

        return frequencies;
    }

    /**symbolArray creates a char array of all the possible symbols from the ASCII table*/
    public static char[] symbolArray(){
        char[] symbols = new char[256];

        for(int i = 0; i < symbols.length; i++){
            symbols[i] = (char)i;
//            System.out.println(symbols[i] + " ");
        }

        return symbols;
    }

    /**getRidOfZeros takes away all the 0 frequencies from the array and cuts down the symbols array
     * accordingly, this is so that we don't have to worry about unused symbols later on in the code*/
    public static void getRidOfZeros(){
        int index = 0;
        int newLen = 0;

        while(index < frequencies.length){
            if(frequencies[index] != 0){
                newLen++;
            }
            index++;
        }

        int[] newFrequencies = new int[newLen];
        char[] newSymbols = new char[newLen];

        int j = 0;
        for (int i = 0; i < frequencies.length; i++){
            if (frequencies[i] != 0) {
                newFrequencies[j] = frequencies[i];
                newSymbols[j] = symbols[i];
                j++;
            }
        }

        frequencies = newFrequencies;
        symbols = newSymbols;

//        for(int i: frequencies){
//            System.out.print(i + " | ");
//        }
//        System.out.println();
//        System.out.println(Arrays.toString(symbols));

    }



}
