import java.util.LinkedList;

public class GeneSymbolTable {
    public int M; //  Hash Table  Size
    public String [] activity;
    public Double [] timeTaken;
    public Double [] reward;
    public Gene [] genePool;

    public GeneSymbolTable(int M,String [] activity, Double[] timeTaken, Double [] reward){
        this.M = M;
        this.activity = activity;
        this.timeTaken = timeTaken;
        this.reward = reward;

        for(int i = 0 ; i <M ; i++){
            String dna = hash(i);
            genePool[i] = new Gene(dna,activity[i],timeTaken[i],reward[i]);
        }
    }

    public String hash(Integer i){


    }

    public void put(Key key, Value val)
    {
//        System.out.println(hash(key)+ " "+ val);
        st[hash(key)].put(key, val);}

    public Iterable<Key> keys(){
        LinkedList<Key> queue = new LinkedList<>();
        for (int i = 0; i < M; i++) {
            for (Key key : st[i].keys())
                queue.add(key);
        }
        return queue;
    }



}
