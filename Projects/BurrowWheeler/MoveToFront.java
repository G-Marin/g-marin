import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.Objects;

public class MoveToFront {

    private static final int R = 256;

    // apply move-to-front encoding, reading from stdin and writing to stdout
    public static void encode() {

        char[] ASCII = new char[R];

        for (int c = 0; c < R; c++) {
            ASCII[c] = (char) c;
        }


        while (!BinaryStdIn.isEmpty()) {

            char c = BinaryStdIn.readChar(8);
            int index = 0;

            for (int i = 0; i < R; i++) {
                if (ASCII[i] == c) {
                    index = i;
                    break;
                }
            }

            BinaryStdOut.write(index, 8);

            char temp = ASCII[0];
            for (int i = 0; i < index; i++) {
                char tempTwo = ASCII[i + 1];
                ASCII[i + 1] = temp;
                temp = tempTwo;
            }
            ASCII[0] = c;
        }

        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from stdin and writing to stdout
    public static void decode() {

        char[] ASCII = new char[R];

        for (char c = 0; c < R; c++) {
            ASCII[c] = c;
        }


        while (!BinaryStdIn.isEmpty()) {

            char c = BinaryStdIn.readChar(8);
            BinaryStdOut.write(ASCII[c]);
            char cIndex = ASCII[c];
            char temp = ASCII[0];
            
            for (int i = 0; i < c; i++) {
                char tempTwo = ASCII[i + 1];
                ASCII[i + 1] = temp;
                temp = tempTwo;
            }
            ASCII[0] = cIndex;
        }

        BinaryStdOut.close();
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {

        if (Objects.equals(args[0], "-")) {
            encode();
        }

        if (Objects.equals(args[0], "+")) {
            decode();
        }
    }
}
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
 
import java.util.Objects;
 
public class MoveToFront {
 
    private static final int R = 256; 
 
    // apply move-to-front encoding, reading from stdin and writing to stdout
    public static void encode() {
 
        char[] ASCII = new char[R];
 
        for (int c = 0; c < R; c++) {
            ASCII[c] = (char) c; 
        }
 
 
        while (!BinaryStdIn.isEmpty()) {
 
            char c = BinaryStdIn.readChar(8);
            int index = 0;
 
            for (int i = 0; i < R; i++) {
                if (ASCII[i] == c) {
                    index = i;
                    break;
                }
            }
 
            BinaryStdOut.write(index, 8);
 
            char temp = ASCII[0];
            for (int i = 0; i < index; i++) { 
                char tempTwo = ASCII[i + 1];
                ASCII[i + 1] = temp;
                temp = tempTwo;
            }
            ASCII[0] = c;
        }
 
        BinaryStdOut.close();
    }
 
    // apply move-to-front decoding, reading from stdin and writing to stdout
    public static void decode() {
 
        char[] ASCII = new char[R];
 
        for (char c = 0; c < R; c++) {
            ASCII[c] = c;
        }
 
 
        while (!BinaryStdIn.isEmpty()) {
 
            char c = BinaryStdIn.readChar(8);
            BinaryStdOut.write(ASCII[c]);
            char cIndex = ASCII[c];
            char temp = ASCII[0];
            
            for (int i = 0; i < c; i++) {
                char tempTwo = ASCII[i + 1];
                ASCII[i + 1] = temp;
                temp = tempTwo;
            }
            ASCII[0] = cIndex;
        }
 
        BinaryStdOut.close();
    }
 
    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
 
        if (Objects.equals(args[0], "-")) {
            encode();
        }
 
        if (Objects.equals(args[0], "+")) {
            decode();
        }
    }
}
 
