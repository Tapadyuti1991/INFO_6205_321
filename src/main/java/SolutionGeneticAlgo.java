import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

//DO Breeding in Parallel
//8he constraint
public class SolutionGeneticAlgo {

    ActivityNodeGraph graph;
    GeneSymbolTable geneST;
    Population population;

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

    getThePhenotype(graph);

}

    private void getThePhenotype(ActivityNodeGraph graph) {


    }

    public void doEvolution(){
    System.out.println("Evolution Process Started ... ");
    int noOfEvolution = 1;
    while(noOfEvolution < Configuration.numberOfEvolution){
        System.out.println("************************************************");
        System.out.println("Generation"+ noOfEvolution);
        breedTheCurrentPopulation(population);
//        testtheCurrentPopulation(noOfEvolution);
//        applySurvivalOfFittest(population,noOfEvolution);
        getThemostFittestPerson(population);
        testtheCurrentPopulation(noOfEvolution);
        noOfEvolution++;
    }
}

    private void getThemostFittestPerson(Population population) {
            System.out.println("Getting Most Fittest Person check...");

            if(population.size > Configuration.MaximumNumberOfPopulation)
            {
                System.out.println("Getting Most Fittest Person out of ..." + population.size);
                int minpqSize = (int)(population.size * Configuration.survivalRate);
                MinPQ<Person> pq = new MinPQ<Person>(minpqSize+1);
                for (Colony c : population.colonies()) {
                        for(Person p: c.getAllPerson()){
//                            System.out.print(" TT :"+ p.getTotalTimeSpent());
//                            System.out.print(" TRP:  "+ p.getTotalRewardFetched());
//                            System.out.print(p.getAllGeneSequence());
                            pq.insert(p);
                            if(pq.size() > minpqSize){
                                Person delp = pq.delMin();
//                                System.out.println("Person with "+ delp.getTotalRewardFetched()+"Points deleted ");
                            }
                        }
                }
                ArrayList<Person> p = new ArrayList<Person>();
//                System.out.println("Most Fittest Person on the Current Generation");
                while (!pq.isEmpty()) p.add(pq.delMin());
                population.deleteAllColony();
                System.out.println("Population Colony Count <<<< "+ population.getColonyCount() + "Size " +population.size);
                Person [] randP = new Person[p.size()];
                int randCount = 0;
                for(Person randPerson:p){
                    randP[randCount] = randPerson;
                    randCount++;
                }

                //  Fisher-Yates method shuffling
                Random rnd = new Random();

                for (int j = randP.length-1; j >0; j--) {
                    int index = rnd.nextInt(j+1);
                    Person a = randP[index];
                    randP[index] = randP[j];
                    randP[j] = a;
                }

                for(int i=0;i<randP.length;i++){
                    population.addPerson(randP[i]);
                }
                System.out.println("Population Colony Count <<<< "+ population.getColonyCount() + "Size " +population.size);
            }
        System.out.println("Getting Most Fittest Person done...");
    }



    private void breedTheCurrentPopulation(Population population) {
//    Colony [] currentColonies = new Colony[population.getColonyCount()];
        System.out.println("Breeding the Current Population ....");
    int countColony = 0;
    ArrayList<Person> newGenAllPerson = new ArrayList<Person>();
    System.out.println("Current Colony Count before Breeding : "+ population.getColonyCount());

    for(Colony c : population.colonies()){
        breedIntraColony(c,newGenAllPerson);
    }

    System.out.println("Current Colony Count After Breeding before Deleting old Pop. : "+ population.getColonyCount() + "Size"+ population.size);


    population.deleteAllColony();
    System.out.println("Current Pop Size After Breeding After Deleting old Pop. : "+  population.getColonyCount() + "Size"+ population.size);

    int popCount = 0;
    System.out.println("Total Number of New Persons to be added "+ newGenAllPerson.size());
    for(Person p : newGenAllPerson) {
        population.addPerson(p);
//        System.out.println("Total Colony :" + population.getColonyCount()+" ");
//
//        int Ccount = 0;
//        for(Colony c :population.colonies()) {
//            System.out.print("Colony :" + Ccount+" ");
//            Ccount++;
//            System.out.println("NO. of Person in CoLony :" + c.size());
//        }
//        popCount ++;
//        System.out.println("******");
//        System.out.println("Adding New Persons to Population"+ popCount);
    }

    System.out.println("Current Colony Count After Breeding after Deleting old Pop: "+ population.getColonyCount() + "Size " +population.size);
//     breedInterColony(currentColonies);
    }


    private void breedIntraColony(Colony c,ArrayList<Person>newGenAllPerson) {
//            System.out.println("Breeding - INTRA Colony");
            Person personArray [] = new Person[c.size()];
            int pCount = 0;

            for(Person p : c.getAllPerson()){
                personArray[pCount] = p;pCount++;
            }
//            System.out.println("All Persons of Current Colony Copied ..."+ personArray.length);

            Random r = new Random();
//            System.out.println("Toal Size of GENR ST Table : " + geneST.size());
//            System.out.println("Log of geneST Size "+ Math.log(geneST.size()));
//            double allBinValue = geneST.size() *  Math.log10(geneST.size());

            Map<Integer,ArrayList<Integer>> map = new HashMap();                       //n*(n-1)/2
            int countTemp = 0;
            ArrayList<Integer> personindex ;
            for(int i = 0 ; i <personArray.length; i++){
                for(int j = i+1 ; j <personArray.length; j++){
                    personindex = new ArrayList<Integer>();
                    personindex.add(i);
                    personindex.add(j);
//                    System.out.println("Adding to Map "+ map.size());
                    map.put(countTemp, personindex);
                    countTemp++;
//                    System.out.println("Adding to Map "+ map.size());
                }
            }
//            System.out.println("Map Built");

//            System.out.println("All Bin Value "+ (int)allBinValue);
        //NOt Required ****
//            int loopCount = 0;
//            if (map.size() < (int)allBinValue ){ loopCount = map.size(); }
//            else{ loopCount =  (int)allBinValue; }
        //******



            if(map.size() < Configuration.MaximumNumberOfPopulation){
//                System.out.println("Creating Mutation == "+ map.size());
                for(int i = 0 ; i <map.size();i++){
                    ArrayList<Integer> index = map.get(i);
//                    System.out.println("Mutation No. " + count + "Person Selected :" + index.get(0) + " " + index.get(1));
                    ArrayList<Gene> child1Gene = new ArrayList<Gene>();
                    ArrayList<Gene> child2Gene = new ArrayList<Gene>();
                    domutation(personArray[index.get(0)], personArray[index.get(1)], child1Gene, child2Gene, newGenAllPerson);
                }
            }
            else {
//                System.out.println("Creating Mutation == "+ Configuration.MaximumNumberOfPopulation/2*population.getColonyCount());
                int count = 0;

                while (count < 1000) {
                    int s = map.size();
//                    System.out.println( "Count "+ count);
                    int index1 = r.nextInt(s);
////                System.out.println("Random Number generated :"+ index1);
                    ArrayList<Integer> index = map.get(index1);
                    ArrayList<Gene> child1Gene = new ArrayList<Gene>();
                    ArrayList<Gene> child2Gene = new ArrayList<Gene>();
//
//                    System.out.println("Mutation No. " + count + "Person Selected :" + index.get(0) + " " + index.get(1));
                    domutation(personArray[index.get(0)], personArray[index.get(1)], child1Gene, child2Gene, newGenAllPerson);
                    count++;
                }
            }
}

    private void culltheUnfittest(ArrayList<Person> newGenAllPerson) {

            System.out.println("Getting Most Fittest Person check...");
            int minpqSize = (int)(newGenAllPerson.size()* Configuration.survivalRate);
            MinPQ<Person> pq = new MinPQ<Person>(minpqSize + 1);

            for(Person p: newGenAllPerson){
                pq.insert(p);
                if(pq.size() > minpqSize){
                    Person delp = pq.delMin();
//                    System.out.println("Person with "+ delp.getTotalRewardFetched()+"Points deleted ");
                }
            }

            ArrayList<Person> p = new ArrayList<Person>();
            System.out.println("Most Fittest Person on the Current Generation");
            while (!pq.isEmpty()) p.add(pq.delMin());
            for (Person t : p) {System.out.println(t.getTotalRewardFetched());}

        System.out.println("Getting Most Fittest Person check DONE...");
    }

    private void domutation(Person person1, Person person2,ArrayList<Gene> child1Gene,ArrayList<Gene> child2Gene,ArrayList<Person>newGenAllPerson ) {
//    System.out.println("Mutation Started ...");
    ArrayList<Gene> Gene1cut1 = new ArrayList<Gene>();
    ArrayList<Gene> Gene1cut2 = new ArrayList<Gene>();
    ArrayList<Gene> Gene2cut1 = new ArrayList<Gene>();
    ArrayList<Gene> Gene2cut2 = new ArrayList<Gene>();

    double timeFinished = 0;
    for(Gene g :person1.ch.genePool) {
        if (timeFinished >= 960 / 2) {
            Gene1cut2.add(g);
        }

        else if (timeFinished < 960/2){Gene1cut1.add(g);}
        timeFinished += g.timeTaken;
    }
    timeFinished = 0;
    for(Gene g :person2.ch.genePool){
        if (timeFinished > 960 / 2) {
            Gene2cut2.add(g);
        }
        else if (timeFinished < 960/2){ Gene2cut1.add(g);}
        timeFinished += g.timeTaken;
    }

        child1Gene = makeChildGene(child1Gene,Gene1cut1,Gene2cut2);
//        System.out.print(" Child1 Gene Done");
        child2Gene = makeChildGene(child2Gene,Gene2cut1,Gene1cut2);
//        System.out.print(" Child2 Gene Done|");
        Person p1 = new Person(new Chromosome(child1Gene));
        Person p2 = new Person(new Chromosome(child1Gene));
//        System.out.print(" |TTchrom1 "+p1.getTotalTimeSpent());System.out.print(" |TTchrom2 "+p2.getTotalTimeSpent());
        newGenAllPerson.add(p1);
//        System.out.println(" ");
        newGenAllPerson.add(p2);
//        System.out.print("| Mutation Finished, two new OffSpring Added ..");
//        System.out.println("|Current NewGenPerson added "+ newGenAllPerson.size());
    }

    private ArrayList<Gene> makeChildGene(ArrayList<Gene> child1Gene, ArrayList<Gene> gene1cut1, ArrayList<Gene> gene2cut2) {
        double totalTimeTaken1 = 0.0;
        for(Gene g :gene1cut1){
            child1Gene.add(g);
            totalTimeTaken1 += g.timeTaken;
        }
//        System.out.print("|TTN"+ totalTimeTaken1+" ");
        for(Gene g :gene2cut2){
            double currtotalTimeTaken1 = totalTimeTaken1;

            totalTimeTaken1 += g.timeTaken;
//            System.out.print("|TT "+totalTimeTaken1);
            if(totalTimeTaken1 < 960.0){
                child1Gene.add(g);
            }

            else if(totalTimeTaken1 > 960.0){
                double tempTimeRequired = Math.abs(960 - totalTimeTaken1) ;

                child1Gene = mutateGene(child1Gene,currtotalTimeTaken1);
                break;
            }
            else if(totalTimeTaken1 == 960.0){child1Gene.add(g);
//            System.out.print("|TTBB"+ totalTimeTaken1+" ");
            break;}
        }
        if(totalTimeTaken1 < 960){
            child1Gene = mutateGene(child1Gene,totalTimeTaken1);
        }
//        System.out.println(" ");
        return child1Gene;
    }

    private ArrayList<Gene> mutateGene(ArrayList<Gene> child1Gene, double totalTimeTaken1) {

//        double tempTimeRequired = Math.abs(totalTimeTaken1 - parentGene.timeTaken -960) ;
        double tempTimeRequired = Math.abs(960 - totalTimeTaken1) ;
//        System.out.print(" Current TS1 : "+ tempTimeRequired);
        for(Gene tempG: geneST.genePool){
            if(tempTimeRequired == 0.0) break;
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

//
    private Person makeChromosomeofEachIndivdual() {
//        Random r = new Random();
//        Double totalTime = 0.0;
        ArrayList<Gene> personChromosome = new ArrayList<Gene>();
        makefirstHalfGene(personChromosome);
        makeSecondHalfGene(personChromosome);
//        while(totalTime < 960) {
//            int activityNo = r.nextInt(graph.getTotalNode());
//            Gene g = geneST.genePool[activityNo];
//            double currTotaltime = totalTime;
//            totalTime += g.timeTaken;
////            System.out.print(totalTime + " ");
//            if(totalTime < 960){
//                personChromosome.add(g);
//            }
//            else if(totalTime == 960.0){
//                break;
//            }
//            else{
//
//                personChromosome = mutateGene(personChromosome,currTotaltime);
//                break;
//            }
//
//        }
//        System.out.println("END ");
        //TESTING PURPOSE ONLY
        Person p;
        if(checkTotalTimeofChromosome(personChromosome)) {
            return new Person(new Chromosome(personChromosome));
        }
        System.out.println("Next try ");

        return makeChromosomeofEachIndivdual();

    }

    private void makefirstHalfGene(ArrayList<Gene> personChromosome) {
        Double totalTime = 0.0;
        Random r = new Random();
        while(totalTime < 480) {
            int activityNo = r.nextInt(graph.getTotalNode());
            Gene g = geneST.genePool[activityNo];
            double currTotaltime = totalTime;
            totalTime += g.timeTaken;
//            System.out.print(totalTime + " ");
            if(totalTime < 480){
                personChromosome.add(g);
            }
            else if(totalTime == 480.0){
                break;
            }
            else{

                personChromosome = mutateGene(personChromosome,currTotaltime);
                break;
            }

        }

    }
    private void makeSecondHalfGene(ArrayList<Gene> personChromosome) {
    }

    private boolean checkTotalTimeofChromosome(ArrayList<Gene> personChromosome) {
    Double totalTime = 0.0;
    for(Gene g :personChromosome){
        totalTime += g.timeTaken;
    }
    if(totalTime == 960.0){
        return true;
    }
    return false;
//    System.out.println("TotalTime of each Person in Activity " + totalTime);
    }

    private void testtheCurrentPopulation(int evo) {
        System.out.println("Testing the result og Generation Evolved  Report");
        System.out.println("Gen : "+ evo);
        System.out.println("Total Colony :" + population.getColonyCount());

        int Ccount = 0;
        for(Colony c :population.colonies()) {
            System.out.println("Colony :"+ Ccount);Ccount++;
            int pCount = 0;
            System.out.println("NO. of Person in CoLony :"+ c.size());
            for(Person p:c.getAllPerson()){
                System.out.print("Person "+ pCount+ " ");pCount++;
                System.out.print(" Total Time :"+ p.getTotalTimeSpent());
                System.out.print("Total Reward Point "+ p.getTotalRewardFetched());
//                System.out.print(p.getAllGeneSequence());
                System.out.println(" ");
            }
            System.out.println(" ");

        }

    }

}
