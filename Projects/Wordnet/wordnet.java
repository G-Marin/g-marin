import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.StdOut;
 
public class WordNet {
 
    // ST containing nouns per id 
    private RedBlackBST<Integer, Queue<String>> idsSt;
    // ST containing ids each noun appears in 
    private RedBlackBST<String, Queue<Integer>> nounsSt;
    // SCA data type
    private ShortestCommonAncestor cAncestor;
    // Digraph
    private Digraph graph;
 
 
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException();
        }
 
        int vertices;
        idsSt = new RedBlackBST<Integer, Queue<String>>();
        nounsSt = new RedBlackBST<String, Queue<Integer>>();
 
        readSynsets(synsets);
        vertices = idsSt.size();
        graph = new Digraph(vertices);
        readHypernyms(hypernyms);
        cAncestor = new ShortestCommonAncestor(graph);
 
    }
 
    // Reads files and puts data in Red-Black BST
    private void readSynsets(String synsets) {
 
        In syn = new In(synsets);
 
        while (!syn.isEmpty()) { 
            String line = syn.readLine(); 
            String[] lineArray = line.split(","); 
            int id = Integer.parseInt(lineArray[0]); 
            String[] nounArray = lineArray[1].split(" "); 
            Queue<String> nounQueue = new Queue<String>(); 
  
  
            for (int i = 0; i < nounArray.length; i++) { 
  
                if (nounsSt.contains(nounArray[i])) { 
                    nounsSt.get(nounArray[i]).enqueue(id); 
                } 
  
                else { 
                    Queue<Integer> idVal = new Queue<>(); 
                    idVal.enqueue(id); 
                    nounsSt.put(nounArray[i], idVal); 
                } 
                nounQueue.enqueue(nounArray[i]); 
            } 
            idsSt.put(id, nounQueue); 
  
        } 
    }
 
    // Reads edges and builds digraph
    private void readHypernyms(String hypernyms) {
        In nyms = new In(hypernyms);
 
        while (!nyms.isEmpty()) {
            String line = nyms.readLine();
            String[] edges = line.split(",");
            int v = Integer.parseInt(edges[0]);
 
            for (int i = 1; i < edges.length; i++) {
                int w = Integer.parseInt(edges[i]);
                graph.addEdge(v, w);
            }
        }
    }
 
 
    // the set of all WordNet nouns
    public Iterable<String> nouns() {
        return nounsSt.keys();
    }
 
    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }
 
        return nounsSt.contains(word);
    }
 
 
    // a synset (second field of synsets.txt) that is a shortest common ancestor
    // of noun1 and noun2 (defined below)
    public String sca(String noun1, String noun2) {
        if (noun1 == null || noun2 == null || !nounsSt.contains(noun1) ||
                !nounsSt.contains(noun2)) {
            throw new IllegalArgumentException();
        }
 
        int synset = cAncestor.ancestorSubset(nounsSt.get(noun1), nounsSt.get(noun2));
 
        return idsSt.get(synset).toString();
    }
 
    // distance between noun1 and noun2 (defined below)
    public int distance(String noun1, String noun2) {
        if (noun1 == null || noun2 == null || !nounsSt.contains(noun1) ||
                !nounsSt.contains(noun2)) {
            throw new IllegalArgumentException();
        }
 
        int length = cAncestor.lengthSubset(nounsSt.get(noun1), nounsSt.get(noun2));
        return length;
    }
 
    // unit testing (required)
    public static void main(String[] args) {
 
        String synsets = (args[0]);
        String hypernyms = (args[1]);
        String nounone = (args[2]);
        String nountwo = (args[3]);
 
        WordNet net = new WordNet(synsets, hypernyms);
 
        StdOut.println(net.distance(nounone, nountwo));
        StdOut.println(net.isNoun(nounone));
        StdOut.println(net.nouns());
        StdOut.println(net.sca(nounone, nountwo));
        
    }
 
}
 
