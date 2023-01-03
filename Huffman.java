import java.io.*;
import java.util.*;

//This class contains method to generate Huffman code tree, encode and decode the text input by the user.
public class Huffman {
    public static void main(String[] args) throws FileNotFoundException {

        Scanner in = new Scanner(System.in);

        //User interaction.
        System.out.println("Huffman Coding");
        System.out.print("Enter the name of the file with letters and probability: ");
        Scanner file = new Scanner(new File(in.nextLine()));
        System.out.println("Building the Huffman tree ....");
        System.out.println("Huffman coding completed.");
        System.out.print("Enter a line (uppercase letters only): ");
        String text = in.nextLine();

        //to check if the user has input all uppercase letters.
        boolean check = false;
        for (int i = 0; i<text.length(); i++) {
            if(Character.isUpperCase(text.charAt(i))) {
                check = true;
            }
        }

        if(check) {
            HashMap<Character, String> code = new HashMap<>();

            char ch = 'A';
            for (String s : findEncoding(HuffmanTree(file))) {
                code.put(ch, s);
                ch++;
            }

            //printing the encoded and decoded text.
            System.out.println("The encoded line is: ");
            System.out.println(Arrays.toString(encodedText(text, code)).replace("]", "").replace("[", "").replace(", ", ""));
            System.out.println();
            System.out.println("The decoded line is: ");
            System.out.println(Arrays.toString(decodedText(encodedText(text, code), code)).replace("]", "").replace("[", "").replace(", ", ""));

        }

        else {
            System.out.println("Uppercase letters only.");
        }

    }

    /**
     * This method reads in a file containing alphabets arranged in ascending order of
     * their probability and returns a Huffman binary tree.
     * Huffman tree is a tree made with an alphabets' probability as nodes and arranged in ascending order of
     * their probability with the sum of their probability as root node i.e. 1.
     * @param file inserting the file storing the alphabets and their probability.
     * @return binary tree with 1 as root node followed by nodes having decreasing probability.
     */
    public static BinaryTree<Pair> HuffmanTree(Scanner file) {

        Queue<BinaryTree<Pair>> S = new LinkedList<>();
        Queue<BinaryTree<Pair>> T = new LinkedList<>();

        //while loop to read data from the file.
        while (file.hasNext()) {
            BinaryTree<Pair> A = new BinaryTree<>();
            Pair alpha = new Pair(file.next().charAt(0), file.nextDouble());
            A.makeRoot(alpha);
            S.add(A);
        }

        BinaryTree<Pair> A;
        BinaryTree<Pair> B;

        //while loop to create the Huffman tree.
        while (!S.isEmpty()) {
            if (T.isEmpty()) {
                A = S.remove();
                B = S.remove();
            } else {
                if (S.peek().getData().getProb() < T.peek().getData().getProb()) {
                    A = S.remove();
                } else {
                    A = T.remove();
                }
                if (!T.isEmpty()) {
                    if (S.peek().getData().getProb() < T.peek().getData().getProb()) {
                        B = S.remove();
                    } else {
                        B = T.remove();
                    }
                } else {
                    B = S.remove();
                }
            }
            BinaryTree<Pair> P = new BinaryTree<>();
            Pair root = new Pair('#', (A.getData().getProb()) + B.getData().getProb());
            P.makeRoot(root);
            P.attachLeft(A);
            P.attachRight(B);
            T.add(P);
        }

        while (T.size() > 1) {
            A = T.remove();
            B = T.remove();
            BinaryTree<Pair> P = new BinaryTree<>();
            Pair root = new Pair('#', (A.getData().getProb()) + B.getData().getProb());
            P.makeRoot(root);
            P.attachLeft(A);
            P.attachRight(B);
            T.add(P);
        }

        return T.poll();
    }

    /**
     * this method is helper method for recursive calls to convert string into binary code.
     * @param bt inputs Huffman tree created from the HuffmanTree method.
     * @return an array of binary digits.
     */
    private static String[] findEncoding(BinaryTree<Pair> bt) {
        String[] result = new String[26];
        findEncoding(bt, result, "");
        return result;
    }

    /**
     * this method converts alphabets into binary digits according to the Huffman tree.
     * @param bt inputs Huffman tree created from the HuffmanTree method.
     * @param a array of string containing alphabets
     * @param prefix string containing the previously converted code.
     */
    private static void findEncoding(BinaryTree<Pair> bt, String[] a, String prefix) {
        if (bt.getLeft() == null && bt.getRight() == null) {
            a[bt.getData().getValue() - 65] = prefix;
        } else {
            findEncoding(bt.getLeft(), a, prefix + "0");
            findEncoding(bt.getRight(), a, prefix + "1");
        }
    }

    /**
     * this method converts string into binary digits according to the Huffman tree.
     * @param text inputs the string entered by the user.
     * @param code inputs the hashmap containing alphabet characters and their binary codes.
     * @return an array of binary digits.
     */
    private static String[] encodedText(String text, HashMap<Character,String> code) {

        //breaking the string into an array of characters.
        char[] chr = new char[text.length()];

        for (int i = 0; i < text.length(); i++) {
            chr[i] = text.charAt(i);
        }

        String[] output = new String[text.length()];

        //comparing the string characters with the keys of hashmap and storing the binary code assigned with the
        //matching key in an array.
        for (int i=0;i< chr.length;i++) {
            for (char j = 'A'; j <= 'Z'; j++) {
                if (chr[i] == j) {
                    output[i]=(code.get(j));
                }
                if (chr[i] == ' ') {
                    output[i]= (" ");
                    break;
                }
            }
        }
        return output;
    }

    /**
     * this method converts string of binary digits into text according to the Huffman tree.
     * @param output inputs an array of binary digits.
     * @param code inputs the hashmap containing alphabet characters and their binary codes.
     * @return an array of string.
     */
    private static String[] decodedText(String[] output, HashMap<Character,String> code) {
        String[] outDec = new String[output.length];

        //comparing the binary code in the array with the values in the hashmap and
        // storing the assigned key in an array.
        for(int i=0;i< output.length;i++) {
            for (char j = 'A'; j <= 'Z'; j++) {
                if (output[i].equals(code.get(j))) {
                    outDec[i] = j + "";

                }
                if (output[i].equals(" ")) {
                    outDec[i] = " ";

                }
            }
        }
        return outDec;
    }

}

