import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class GeneSymbolTable {
    private int M; //  Hash Table  Size
    public String [] activity;
    public Double [] timeTaken;
    public Double [] reward;
    public Gene [] genePool;
    public String [] dnaAll;
    public ArrayList<String> base = new ArrayList<String>();
    final static Logger logger = Logger.getLogger(Driver.class);
    public GeneSymbolTable(int M,String [] activity, Double[] timeTaken, Double [] reward){
        this.M = M;
        this.activity = activity;
        this.timeTaken = timeTaken;
        this.reward = reward;
        genePool = new Gene [M];
        makeSymbolTable();
        for(int i = 0 ; i < M ; i++){
            String dna = hash(i);
//            System.out.println("Hash Created .. ");
            genePool[i] = new Gene(dna,activity[i],timeTaken[i],reward[i]);
        }
        logger.info("Gene Symbol Table creation Done ..");
    }

    public int size(){return M;}
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
        System.out.println(base);
        dnaAll = new String[base.size()];
        int count = 0;
        for(String geneName : base){
            dnaAll[count] = geneName;
            count++;
        }
        logger.info(" Make Symbol Table Done .. ");
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
         Random r = new Random ();
         int j = r.nextInt(M);
//         System.out.println(j);
        return dnaAll[j];

    }

}
