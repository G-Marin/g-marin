import edu.princeton.cs.algs4.StdOut;
 
import java.util.Arrays;
 
public class CircularSuffixArray {
 
    private String oSuffix;             // Original Suffix/String
    private Suffix[] suffixes;          // Array of Suffix Objects
 
 
    private static class Suffix implements Comparable<Suffix> {
        /* @citation Adapted from:https://algs4.cs.princeton.edu/code/edu/
        princeton/cs/algs4/SuffixArray.java.html * princeton/cs/algs4/63suffix/
         abra.txt/ Accessed 12/12/2022. */
 
        private final String text;      // String element of Suffix object
        private final int index;        // Reference to start of suffix
 
 
        // Constructs a Suffix Object
        private Suffix(String text, int index) {
            this.text = text;
            this.index = index;
        }
 
        // Returns length of Suffix
        private int length() {
            return text.length() - index;
        }
 
        // returns characters of each suffix for comparison
        private char index(Suffix s, int i) {
 
            int sum = i + s.index;
            if (i + s.index > text.length() - 1) { 
                return s.text.charAt(sum - text.length());
            }
            else {
                return s.text.charAt(sum);
            }
        }
 
        // Compares two suffixes based on lexicographical order
        public int compareTo(Suffix that) {
            if (this == that) {
                return 0;
            }
            for (int i = 0; i < that.text.length(); i++) {
                if (this.index(this, i) < that.index(that, i)) return -1; 
                if (this.index(this, i) > that.index(that, i)) return +1;
            }
            return this.length() - that.length();
        }
    }
 
 
    // circular suffix array of s
    public CircularSuffixArray(String s) {
 
        if (s == null) {
            throw new IllegalArgumentException("Null String");
        }
 
        oSuffix = s;
        int length = length();
        suffixes = new Suffix[length];
 
        for (int offset = 0; offset < length; offset++) {
            Suffix currentSuffix = new Suffix(s, offset); 
            suffixes[offset] = (currentSuffix);
        }
        Arrays.sort(suffixes);
    }
 
    // length of s
    public int length() {
        return oSuffix.length();
    }
 
    // returns index of ith sorted suffix
    public int index(int i) {
 
        if (length() - 1 < i || i < 0) {
            throw new IllegalArgumentException("Out of Bounds");
        }
 
        return suffixes[i].index;
    }
 
    // unit testing (required)
    public static void main(String[] args) {
 
        CircularSuffixArray array = new CircularSuffixArray("ABRACADABRA!");
        for (int i = 0; i < array.length(); i++) {
            StdOut.println(array.index(i)); 
        }
        StdOut.print(array.length());
 
    }
}
 
