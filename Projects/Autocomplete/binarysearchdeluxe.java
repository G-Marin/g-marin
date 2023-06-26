import edu.princeton.cs.algs4.StdOut;
 
import java.util.Comparator;
 
 
public class BinarySearchDeluxe {
    /* @citation Adapted from:
    https://drive.google.com/file/d/1Ou7RRyLwAH2mxDuE0335oGkE_WkMTYwK/view
    *Accessed 10/3/2022. */
 
    // Returns the index of the first key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
 
        if (key == null || comparator == null || a == null) {
            throw new IllegalArgumentException("Invalid Inputs");
        }
 
        int n = a.length;
        int lo = 0;
        int hi = n - 1;
        int lastMatch = -1;
 
        if (n == 0) {
            return -1;
        }
        while (lo <= hi) {
            int mid = (lo + hi) >>> 1;
            int cmp = comparator.compare(key, a[mid]);
 
 
            if (cmp == 0) {
                hi = mid - 1;
                lastMatch = mid;
            }
            else if (cmp < 0) {
                hi = mid - 1;
            }
            else {
                lo = mid + 1;
            }
 
        }
        return lastMatch;
    }
 
    // Returns the index of the last key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        if (key == null || comparator == null || a == null) {
            throw new IllegalArgumentException("Invalid Inputs");
        }
        int n = a.length;
 
        if (n == 0) {
            return -1;
        }
 
 
        int lo = 0;
        int hi = n - 1;
        int lastMatch = -1;
 
        while (lo <= hi) {
            int mid = (lo + hi) >>> 1;
            int cmp = comparator.compare(key, a[mid]);
 
 
            if (cmp == 0) {
                lo = mid + 1;
                lastMatch = mid;
            }
            else if (cmp < 0) {
                hi = mid - 1;
            }
            else {
                lo = mid + 1;
            }
 
        }
        return lastMatch;
    }
 
    // unit testing (required)
    public static void main(String[] args) {
        // Test Case from TigerFile
 
        String[] a = {
                "ACG", "AGC", "AGC", "ATG", "ATT", "CCG", "CCG", "CGC", "CTT",
                "GAG", "GCA", "GCC", "GCG", "GTC", "TCA", "TCA", "TCG"
        };
 
 
        int d = BinarySearchDeluxe.firstIndexOf(a, "GCC",
                                                String.CASE_INSENSITIVE_ORDER);
        StdOut.println(d);
 
        int c = BinarySearchDeluxe.lastIndexOf(a, "TCG",
                                               String.CASE_INSENSITIVE_ORDER);
 
        StdOut.println(c);
 
    }
 
}
 
