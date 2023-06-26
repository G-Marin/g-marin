import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
 
public class Outcast {
    private WordNet net;
 
    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        net = wordnet;
    }
 
    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
 
        int championDistance = 0;
        int champion = 0;
 
        for (int i = 0; i < nouns.length; i++) {
 
            int sumDistance = 0;
            
            for (int j = 0; j < nouns.length; j++) {
                sumDistance = sumDistance + net.distance(nouns[j], nouns[i]);
            }
 
            if (sumDistance > championDistance) {
                championDistance = sumDistance;
                champion = i;
            }
 
 
        }
 
        return nouns[champion];
    }
 
    // test client
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
 
}
 
