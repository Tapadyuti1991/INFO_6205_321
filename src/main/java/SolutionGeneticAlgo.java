import org.apache.xmlbeans.impl.xb.xsdschema.All;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
    this.population = new Population(Configuration.eachColonyMaximumSize);
    int initPopulation = Configuration.initialPopulationSize;

    while(initPopulation > 0) {
        Person p = makeChromosomeofEachIndivdual();
        addPersonToPopulation(p);
        initPopulation--;
    }

}
public void doEvolution(){
    System.out.println("Evolution Process Started ... ");
    int noOfEvolution = 1;
    while(noOfEvolution < Configuration.numberOfEvolution){
        System.out.println("Generation"+ noOfEvolution);
        breedTheCurrentPopulation(population);
        cullTheMostFitness(noOfEvolution);
        noOfEvolution++;
    }
}

    private void cullTheMostFitness(int evo) {
    System.out.println("Gen : "+ evo);
    System.out.println("Total Colony :" + population.getColonyCount());

        int Ccount = 0;
        for(Colony c :population.colonies()) {
            System.out.println("Colony :"+ Ccount);Ccount++;
            int pCount = 0;
            for(Person p:c.getAllPerson()){
                System.out.print("Person "+ pCount+ " ");pCount++;
                System.out.print(" Total Time :"+ p.getTotalTimeSpent());
                System.out.print("Total Reward Point "+ p.getTotalRewardFetched());
                System.out.print(p.getAllGeneSequence());
                System.out.println(" ");
            }
            System.out.println(" ");

        }

    }

    private void breedTheCurrentPopulation(Population population) {
//    Colony [] currentColonies = new Colony[population.getColonyCount()];
        System.out.println("Breeding the Current Population ....");
    int countColony = 0;
    ArrayList<Person> newGenAllPerson = new ArrayList<Person>();
    for(Colony c : population.colonies()){
        breedIntraColony(c,newGenAllPerson);
    }
    population.deleteAllColony();

    for(Person p : newGenAllPerson) {
        population.addPerson(p);
    }

//     breedInterColony(currentColonies);
    }


    private void breedIntraColony(Colony c,ArrayList<Person>newGenAllPerson) {
            System.out.println("Breeding - INTRA Colony");
            Person personArray [] = new Person[c.size()];
            int pCount = 0;

            for(Person p : c.getAllPerson()){
                personArray[pCount] = p;pCount++;
            }
            System.out.println("All Persons of Current Colony Copied ...");

            Random r = new Random();
            System.out.println("Toal Size of GENR ST Table : " + geneST.size());
            System.out.println("Log of geneST Size "+ Math.log(geneST.size()));
            double allBinValue = geneST.size() *  Math.log(geneST.size());

            Map<Integer,ArrayList<Integer>> map = new HashMap();
            //n*(n-1)/2
            int countTemp = 0;
            ArrayList<Integer> personindex ;
            for(int i = 0 ; i <personArray.length; i++){
                for(int j = i+1 ; j <personArray.length; j++){
                    personindex = new ArrayList<Integer>();
                    personindex.add(i);
                    personindex.add(j);
                    map.put(countTemp,personindex);
                }
            }

            int count = 0;
            System.out.println("All Bin Value "+ (int)allBinValue);
            while(count < (int)allBinValue){
                int index1 = r.nextInt(map.size());
                ArrayList<Integer> index = map.get(index1);
                ArrayList<Gene> child1Gene = new ArrayList<Gene>();
                ArrayList<Gene> child2Gene = new ArrayList<Gene>();

                System.out.println("Mutation No. "+ count+ "Person Selected :" + index.get(0)+ index.get(1));
                domutation(personArray[index.get(0)], personArray[index.get(1)],child1Gene,child2Gene,newGenAllPerson);
                count++;
            }
}

    private void domutation(Person person1, Person person2,ArrayList<Gene> child1Gene,ArrayList<Gene> child2Gene,ArrayList<Person>newGenAllPerson ) {
    System.out.println("Mutation Started ...");
    ArrayList<Gene> Gene1cut1 = new ArrayList<Gene>();
    ArrayList<Gene> Gene1cut2 = new ArrayList<Gene>();
    ArrayList<Gene> Gene2cut1 = new ArrayList<Gene>();
    ArrayList<Gene> Gene2cut2 = new ArrayList<Gene>();

    double timeFinished = 0;
    for(Gene g :person1.ch.genePool) {
        if (timeFinished > 960 / 2) {
            Gene1cut2.add(g);
        }

        Gene1cut1.add(g);
        timeFinished += g.timeTaken;
    }
    timeFinished = 0;
    for(Gene g :person2.ch.genePool){
        if (timeFinished > 960 / 2) {
            Gene2cut2.add(g);
        }
        Gene2cut1.add(g);
        timeFinished += g.timeTaken;
    }

        child1Gene = makeChildGene(child1Gene,Gene1cut1,Gene2cut2);
        child2Gene = makeChildGene(child2Gene,Gene2cut1,Gene1cut1);

        newGenAllPerson.add(new Person(new Chromosome(child1Gene)));
        newGenAllPerson.add(new Person(new Chromosome(child2Gene)));
        System.out.println("Mutation Finished, two new OffSpring Added ..");
        System.out.println("Current NewGenPerson added "+ newGenAllPerson.size());
    }

    private ArrayList<Gene> makeChildGene(ArrayList<Gene> child1Gene, ArrayList<Gene> gene1cut1, ArrayList<Gene> gene2cut2) {
        double totalTimeTaken1 = 0.0;
        for(Gene g :gene1cut1){
            child1Gene.add(g);
            totalTimeTaken1 += g.timeTaken;
        }

        for(Gene g :gene2cut2){

            totalTimeTaken1 += g.timeTaken;
            if(totalTimeTaken1 <= 960){
                child1Gene.add(g);
            }
            else{
                child1Gene = getTheRemainingGene(g,child1Gene,totalTimeTaken1);
            }
        }
        return child1Gene;
    }

    private ArrayList<Gene> getTheRemainingGene(Gene parentGene, ArrayList<Gene> child1Gene, double totalTimeTaken1) {

        double tempTimeRequired = Math.abs(totalTimeTaken1 - parentGene.timeTaken -960) ;
//                System.out.println("Current TS1 : "+ tempTimeRequired);
        for(Gene tempG: geneST.genePool){
            if(tempG.timeTaken <= tempTimeRequired && tempTimeRequired > 0){
//                        System.out.println("Inside 2nd Time "+ tempG.timeTaken);
                child1Gene.add(tempG);
                tempTimeRequired -= tempG.timeTaken;
//                        System.out.println("Current TS : "+ tempTimeRequired);
            }
        }
        return child1Gene;
    }


    private void breedInterColony(Colony c) {


    }


    private void addPersonToPopulation(Person p) {
        population.addPerson(p);
    }

//    public int hash(int key)
//    { return (key.hashCode() & 0x7fffffff) % M; }



    private Person makeChromosomeofEachIndivdual() {
        Random r = new Random();
        Double totalTime = 0.0;
        ArrayList<Gene> personChromosome = new ArrayList<Gene>();
        while(totalTime < 960) {
            int activityNo = r.nextInt(graph.getTotalNode());
            Gene g = geneST.genePool[activityNo];
            totalTime += g.timeTaken;
//            System.out.print(totalTime + " ");
            if(totalTime <= 960){
                personChromosome.add(g);
            }
            else{
                personChromosome = getTheRemainingGene(g,personChromosome,totalTime);

//                double tempTimeRequired = Math.abs(totalTime - g.timeTaken -960) ;
////                System.out.println("Current TS1 : "+ tempTimeRequired);
//                for(Gene tempG: geneST.genePool){
//                    if(tempG.timeTaken <= tempTimeRequired && tempTimeRequired > 0){
////                        System.out.println("Inside 2nd Time "+ tempG.timeTaken);
//                        personChromosome.add(tempG);
//                        tempTimeRequired -= tempG.timeTaken;
////                        System.out.println("Current TS : "+ tempTimeRequired);
//                    }
//                }

            }

        }
//        System.out.println("END ");
        //TESTING PURPOSE ONLY
//        checkTotalTimeofChromosome(personChromosome);
        Person p = new Person(new Chromosome(personChromosome));

        return p;

    }

    private void checkTotalTimeofChromosome(ArrayList<Gene> personChromosome) {
    Double totalTime = 0.0;
    for(Gene g :personChromosome){
        totalTime += g.timeTaken;
    }
//    System.out.println("TotalTime of each Person in Activity " + totalTime);
    }


}
