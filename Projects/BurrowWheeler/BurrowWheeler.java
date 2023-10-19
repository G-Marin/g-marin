import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
 
import java.util.Objects;
 
public class BurrowsWheeler {
 
 
    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {
        String s = BinaryStdIn.readString();
        BinaryStdIn.close();
 
        CircularSuffixArray array = new CircularSuffixArray(s); 
        int length = array.length();
        char[] encoded = new char[length];
 
 
        for (int i = 0; i < length; i++) {
 
            if (array.index(i) == 0) {
                BinaryStdOut.write(i); 
            }
            encoded[i] = s.charAt((array.index(i) + length - 1) % length); 
        }
 
        for (int i = 0; i < length; i++) {
            BinaryStdOut.write(encoded[i]);
        }
        BinaryStdOut.close();
    }
 
    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
 
        int R = 256; 
 
        int start = BinaryStdIn.readInt();
        String input = BinaryStdIn.readString();
        int length = input.length();
        BinaryStdIn.close();
 
 
        // Builds a int array from the input
        char[] lastChar = new char[length];
        int[] count = new int[R + 1];
        int[] next = new int[length];
 
 
        for (int i = 0; i < length; i++) { 
            lastChar[i] = input.charAt(i);
        }
 
        /* @citation Copied from: https://algs4.cs.princeton.edu/lectures/demo
        /51DemoKeyIndexedCounting.pdf Accessed 12/9/22
         */
 
        for (int i = 0; i < length; i++) {
            count[lastChar[i] + 1]++;
        }
 
 
        for (int r = 0; r < R; r++) {
            count[r + 1] += count[r];
        }
 
        /* end citation  */
 
        for (int i = 0; i < length; i++) { 
            next[count[lastChar[i]]] = i;
            count[lastChar[i]] += 1;
        }
 
 
        for (int i = 0; i < length; i++) {
            start = next[start];
            BinaryStdOut.write(lastChar[start]);
        }
        BinaryStdOut.close();
    }
 
    public static void main(String[] args) {
 
        if (Objects.equals(args[0], "-")) {
            transform();
        }
 
        if (Objects.equals(args[0], "+")) {
            inverseTransform();
        }
    }
 
}
