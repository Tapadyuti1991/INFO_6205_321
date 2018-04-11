import java.util.ArrayList;
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
        population.add(p);
    }

//    public int hash(int key)
//    { return (key.hashCode() & 0x7fffffff) % M; }



    private Person makeChromosomeofEachIndivdual() {
        Random r = new Random();
        Double totalTime = 0.0;
        ArrayList<Gene> personChromosome = new ArrayList<Gene>();
        while(totalTime < 960) {
            int activityNo = r.nextInt(graph.getTotalNode() + 1);
            Gene g = geneST.genePool[activityNo];
            totalTime += g.timeTaken;
            if(totalTime < 960){
                personChromosome.add(g);
            }
            else{

                ArrayList<Gene> fillRemainingGene = new ArrayList<Gene>() ;
                for(Gene tempG: geneST.genePool){
                    if(tempG.timeTaken < )
                }

            }
        }
        Person p = new Person(new Chromosome(personChromosome));

        return p;

    }




}
