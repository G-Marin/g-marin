import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
 
import java.util.Arrays;
 
public class Autocomplete {
    private final Term[] termArray;   // Defensive copy of array
 
    // Initializes the data structure from the given array of terms.
    public Autocomplete(Term[] terms) {
        if (terms == null) {
            throw new IllegalArgumentException();
        }
 
        int termLength = terms.length;
        termArray = new Term[termLength];
        for (int i = 0; i < terms.length; i++) {
 
            if (terms[i] == null) {
                throw new IllegalArgumentException();
            }
            termArray[i] = terms[i];
        }
        Arrays.sort(termArray);
    }
 
    // Returns all terms that start with the given prefix,
    // in descending order of weight.
    public Term[] allMatches(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException();
        }
 
        Term prefixTerm = new Term(prefix, 0);
        int pLength = prefix.length();
        int first = BinarySearchDeluxe.firstIndexOf(
                termArray, prefixTerm, Term.byPrefixOrder(pLength));
        int last = BinarySearchDeluxe.lastIndexOf(
                termArray, prefixTerm, Term.byPrefixOrder(pLength));
 
 
        if (last == -1 || first == -1) {
            Term[] empty = new Term[0];
            return empty;
        }
 
        int matches = last - first + 1;
        Term[] matchedItems = new Term[matches];
        for (int i = first; i <= last; i++) {
            matchedItems[i - first] = termArray[i];
        }
        Arrays.sort(matchedItems, Term.byReverseWeightOrder());
        return matchedItems;
 
    }
 
    // Returns the number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {
 
        Term prefixTerm = new Term(prefix, 1);
        int pLength = prefix.length();
        int first = BinarySearchDeluxe.firstIndexOf(
                termArray, prefixTerm, Term.byPrefixOrder(pLength));
        int last = BinarySearchDeluxe.lastIndexOf(
                termArray, prefixTerm, Term.byPrefixOrder(pLength));
 
        if (first == -1 || last == -1) {
            return 0;
        }
        int matches = last - first + 1;
        return matches;
    }
 
 
    // unit testing (required)
    public static void main(String[] args) {
 
 
        // read in the terms from a file
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        Term[] terms = new Term[n];
        for (int i = 0; i < n; i++) {
            long weight = in.readLong();           // read the next weight
            in.readChar();                         // scan past the tab
            String query = in.readLine();          // read the next query
            terms[i] = new Term(query, weight);    // construct the term
        }
 
        // read in queries from standard input and print the top k matching terms
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(terms);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            StdOut.printf("%d matches\n", autocomplete.numberOfMatches(prefix));
            for (int i = 0; i < Math.min(k, results.length); i++)
                StdOut.println(results[i]);
        }
 
    }
}
