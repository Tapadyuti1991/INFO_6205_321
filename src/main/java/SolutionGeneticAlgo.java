import java.util.Random;

public class SolutionGeneticAlgo {

    ActivityNodeGraph graph;
    GeneSymbolTable geneST;
    Population population;
    Person [] p;


//Constructor initialising all the Constraints and Generation -  0
SolutionGeneticAlgo(ActivityNodeGraph graph,GeneSymbolTable geneST){
    this.graph = graph;
    this.geneST = geneST;

    int initPopulation = Configuration.initialPopulation;

    while(initPopulation > 0) {
        Person p = makeChromosomeofEachIndivdual();
        addPersonToPopulation(p);
        initPopulation--;
    }

}

    private void addPersonToPopulation(Person p) {

    }

//    public int hash(int key)
//    { return (key.hashCode() & 0x7fffffff) % M; }



    private Person makeChromosomeofEachIndivdual() {
        Random r = new Random();

        int activity = r.nextInt(graph.getTotalNode()+1);


    }


}
