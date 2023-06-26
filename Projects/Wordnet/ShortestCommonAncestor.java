mport edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
 
public class ShortestCommonAncestor {
    // Defensive copy of Digraph
    private final Digraph graph;
 
    // constructor takes a rooted DAG as argument
    public ShortestCommonAncestor(Digraph G) {
 
        if (G == null || !checkDAG(G)) {
            throw new IllegalArgumentException();
        }
 
        graph = new Digraph(G);
    }
 
    // Checks if Digraph is rooted/acyclic
    private boolean checkDAG(Digraph G) {
        int root = 0;
        for (int i = 0; i < G.V(); i++) {
            if (G.outdegree(i) == 0) {
                root += 1;
            }
        }
 
        DirectedCycle cycleFinder = new DirectedCycle(G);
        if (cycleFinder.hasCycle() || root > 1) {
            return false;
        }
        return true;
    }
 
 
    // Helper method returns SCA and shortest ancestral path
    private int[] scaLengthFinder(BreadthFirstDirectedPaths vSearch,
                                  BreadthFirstDirectedPaths wSearch) {
 
        int vertices = graph.V() - 1;
        int championLength = Integer.MAX_VALUE;
        int champion = -1;
 
 
        for (int vertex = 0; vertex <= vertices; vertex++) {
 
            if (vSearch.hasPathTo(vertex) && wSearch.hasPathTo(vertex)) {
 
                if (vSearch.distTo(vertex) + wSearch.distTo(vertex) < championLength) {
                    championLength = vSearch.distTo(vertex) + wSearch.distTo(vertex);
                    champion = vertex;
                }
            }
 
        }
 
        int[] championReturn = { champion, championLength };
        return championReturn;
 
    }
 
    // length of shortest ancestral path between v and w
    public int length(int v, int w) {
        if (v > graph.V() || v < 0 || w > graph.V() || w < 0) {
            throw new IllegalArgumentException();
        }
 
 
        BreadthFirstDirectedPaths vSearch = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths wSearch = new BreadthFirstDirectedPaths(graph, w);
 
        int[] championReturn = scaLengthFinder(vSearch, wSearch);
 
        return championReturn[1];
    }
 
    // a shortest common ancestor of vertices v and w
    public int ancestor(int v, int w) {
        if (v > graph.V() || v < 0 || w > graph.V() || w < 0) {
            throw new IllegalArgumentException();
        }
 
        BreadthFirstDirectedPaths vSearch = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths wSearch = new BreadthFirstDirectedPaths(graph, w);
 
        int[] championReturn = scaLengthFinder(vSearch, wSearch);
 
        return championReturn[0];
    }
 
    // length of shortest ancestral path of vertex subsets A and B
    public int lengthSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
 
        if (subsetA == null || subsetB == null) {
            throw new IllegalArgumentException();
        }
 
        for (Integer i : subsetA) {
            if (i == null || i > graph.V() || i < 0) {
                throw new IllegalArgumentException();
            }
        }
 
        for (Integer i : subsetB) {
            if (i == null || i > graph.V() || i < 0) {
                throw new IllegalArgumentException();
            }
        }
 
 
        BreadthFirstDirectedPaths vSearch =
                new BreadthFirstDirectedPaths(graph, subsetA);
        BreadthFirstDirectedPaths wSearch
                = new BreadthFirstDirectedPaths(graph, subsetB);
 
        int[] championReturn = scaLengthFinder(vSearch, wSearch);
 
        return championReturn[1];
    }
 
    // a shortest common ancestor of vertex subsets A and B
    public int ancestorSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
 
        if (subsetA == null || subsetB == null) {
            throw new IllegalArgumentException();
        }
 
 
        for (Integer i : subsetA) {
            if (i == null || i > graph.V() || i < 0) {
                throw new IllegalArgumentException();
            }
        }
 
        for (Integer i : subsetB) {
            if (i == null || i > graph.V() || i < 0) {
                throw new IllegalArgumentException();
            }
        }
 
        BreadthFirstDirectedPaths vSearch
                = new BreadthFirstDirectedPaths(graph, subsetA);
        BreadthFirstDirectedPaths wSearch
                = new BreadthFirstDirectedPaths(graph, subsetB);
 
        int[] championReturn = scaLengthFinder(vSearch, wSearch);
 
        return championReturn[0];
    }
 
    // unit testing (required)
    public static void main(String[] args) {
 
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        ShortestCommonAncestor sca = new ShortestCommonAncestor(G);
 
 
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sca.length(v, w);
            int ancestor = sca.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
 
        Stack<Integer> v = new Stack<Integer>();
        Stack<Integer> w = new Stack<Integer>();
 
        v.push(8);
        w.push(10);
 
        int ancestor = sca.ancestorSubset(v, w);
        int length = sca.lengthSubset(v, w);
 
        StdOut.println("Shortest Common Ancestor");
        StdOut.printf("length = %d, ancestor = %d\n", ancestor, length);
    }
}
 
 
