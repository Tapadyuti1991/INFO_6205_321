import java.util.ArrayList;
import java.util.LinkedList;

public class GeneSymbolTable {
    public int M; //  Hash Table  Size
    public String [] activity;
    public Double [] timeTaken;
    public Double [] reward;
    public Gene [] genePool;
    public String [] dnaAll;
    public ArrayList<String> base = new ArrayList<String>();

    public GeneSymbolTable(int M,String [] activity, Double[] timeTaken, Double [] reward){
        this.M = M;
        this.activity = activity;
        this.timeTaken = timeTaken;
        this.reward = reward;
        makeSymbolTable();
        for(int i = 0 ; i <M ; i++){
            String dna = hash(i);
            genePool[i] = new Gene(dna,activity[i],timeTaken[i],reward[i]);
        }
    }

    private void makeSymbolTable() {
        String a = "A";
        String g = "G";
        String t = "T";
        String c = "C";
        String dna [] = new String [4];
        {
            char set1[] = {'A', 'C','G','T'};
            int k = 4;
            createAllLength(set1, k);
        }

        String [] dnaAll = (String[]) base.toArray();

    }

    public void createAllLength(char set[], int k) {
        int n = set.length;
        createAllLength(set, "", n, k);
    }

    public void createAllLength(char set[], String prefix, int n, int k) {
        if (k == 0) {
            base.add(prefix);
            return;
        }
        for (int i = 0; i < n; ++i) {
            String newPrefix = prefix + set[i];
            createAllLength(set, newPrefix, n, k - 1);
        }
    }

    public String hash(Integer i){

        return dnaAll[(i.hashCode()& 0x7fffffff)%M];

    }

}
