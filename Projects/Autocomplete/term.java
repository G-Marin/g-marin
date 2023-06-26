mport edu.princeton.cs.algs4.StdOut;
 
import java.util.Comparator;
 
public class Term implements Comparable<Term> {
    private String query;   // String of Term
    private long weight;    // Weight of Term
 
 
    // Initializes a term with the given query string and weight.
    public Term(String query, long weight) {
 
        if (query == null) {
            throw new IllegalArgumentException("Empty String");
        }
        this.query = query;
        if (weight < 0) {
            throw new IllegalArgumentException("Negative Weight");
        }
        this.weight = weight;
    }
 
    // Compares the two terms in descending order by weight.
    public static Comparator<Term> byReverseWeightOrder() {
        return new WeightComparison();
    }
 
    // Compares Term's weight and returns result
    private static class WeightComparison implements Comparator<Term> {
        public int compare(Term a, Term b) {
            return (int) (b.weight - a.weight);
        }
    }
 
 
    // Compares the two terms in lexicographic order,
    // but using only the first r characters of each query.
    public static Comparator<Term> byPrefixOrder(int r) {
        if (r < 0) {
            throw new IllegalArgumentException("Negative r");
        }
        return new PrefixComparison(r);
    }
 
    // Compares terms up to r characters and returns result
    private static class PrefixComparison implements Comparator<Term> {
        private int rChar;   // Number of characters to check up to
 
 
        // Keeps track of number of characters to check
        public PrefixComparison(int r) {
            rChar = r;
        }
 
        public int compare(Term a, Term b) {
            int j = 0;
            int aLength = a.query.length();
            int bLength = b.query.length();
 
            int minLength = Math.min(aLength, bLength);
            int loopVal = Math.min(minLength, rChar);
            for (int i = 0; i < loopVal; i++) {
                char aCharacter = a.query.charAt(i);
                char bCharacter = b.query.charAt(i);
 
                if (aCharacter == bCharacter) {
                    j = 0;
                }
                else if (aCharacter > bCharacter) {
                    j = 1;
                    break;
                }
                else {
                    j = -1;
                    break;
                }
            }
            if (j == 0 && rChar > minLength) {
                return Integer.compare(aLength, bLength);
 
            }
            return j;
        }
    }
 
 
    // Compares the two terms in lexicographic order by query.
    public int compareTo(Term that) {
        int j = this.query.compareTo(that.query);
        return j;
    }
 
    // Returns a string representation of this term in the following format:
    // the weight, followed by a tab, followed by the query.
    public String toString() {
        String j = weight + "\t" + query;
        return j;
    }
 
    // unit testing (required)
    public static void main(String[] args) {
 
        Term p = new Term("CCAGTAAA", 6);
        Term q = new Term("CAAAGCG", 2);
        StdOut.println("Compares by Prefix Order: "
                               + byPrefixOrder(3).compare(p, q));
        StdOut.println(" Compare by Reverse Weight: "
                               + byReverseWeightOrder().compare(p, q));
        StdOut.println("Compares two terms in lexixographic order: " +
                               p.compareTo(q));
 
 
    }
}
 
