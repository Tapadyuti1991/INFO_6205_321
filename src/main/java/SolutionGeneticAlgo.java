import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

//DO Breeding in Parallel
public class SolutionGeneticAlgo {

    ActivityNodeGraph graph;
    GeneSymbolTable geneST;
    Population population;
    final static Logger logger = Logger.getLogger(SolutionGeneticAlgo.class);

    public  CompletableFuture<Colony> getCompetableFutureResult(Colony c,ArrayList<Person> newGenAllPerson) {
        return  parsort(c,newGenAllPerson);
    }

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

    public Double[] doEvolution(){

    logger.info("Evolution Process Started ... ");
    int noOfEvolution = 0;
    Double [] allBestPerson = new Double[Configuration.numberOfEvolution];
    while(noOfEvolution < Configuration.numberOfEvolution){
        logger.info("************************************************");
        logger.info("Generation"+ noOfEvolution);
        breedTheCurrentPopulation(population);
        Double bp = getThemostFittestPerson(population);
        allBestPerson[noOfEvolution] = bp;
//        testtheCurrentPopulation(noOfEvolution);
        noOfEvolution++;
    }

    return allBestPerson;
}

    private void breedTheCurrentPopulation(Population population) {
//        System.out.println("Breeding the Current Population ....");
        int countColony = 0;
        ArrayList<Person> newGenAllPerson = new ArrayList<Person>();

        List<CompletableFuture<Colony>> futureResultList = new ArrayList<>();

        for(Colony c : population.colonies()){

//            futureResultList.add(getCompetableFutureResult(c,newGenAllPerson));
            breedIntraColony(c,newGenAllPerson);


        }

        ///** PARALLEL Processing for Breeding Colony wise *****************
//        CompletableFuture[] futureResultArray = futureResultList.toArray(new CompletableFuture[futureResultList.size()]);
//
//        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(futureResultArray);
//
//        CompletableFuture<List<Colony>> finalResults = combinedFuture
//                .thenApply(voidd -> futureResultList.stream().map(future -> future.join()).collect(Collectors.toList()));
//
//
//        finalResults.whenComplete((result,throwable)-> {
//            Colony colony = population.firstColony;
//            for(Colony subarray : result){
//                  colony = subarray;
//                  colony = colony.nextColony;
//            }
//        });
//        finalResults.join();
        //**************************************************

        population.deleteAllColony();
        for(Person p : newGenAllPerson) {
            population.addPerson(p);
        }
    }

    private  CompletableFuture<Colony> parsort(Colony c,ArrayList<Person> newGenAllPerson) {

        return CompletableFuture.supplyAsync(
                () -> {
                    System.out.println("Parallel Computing ..");
                    breedIntraColony(c,newGenAllPerson);
                    return c;
                }
        );
    }

    public Double getThemostFittestPerson(Population population) {
            Double bestPerson = null;
            if(population.size > Configuration.MaximumNumberOfPopulation)
            {
//                System.out.println("Getting Most Fittest Person out of ..." + population.size);
                int minpqSize = (int)(population.size * Configuration.survivalRate);
                MinPQ<Person> pq = new MinPQ<Person>(minpqSize+1);

                for (Colony c : population.colonies()) {

                        for(Person p: c.getAllPerson()){
                            pq.insert(p);
                            if(pq.size() > minpqSize){
                                Person delp = pq.delMin();
                            }
                        }
                }
                ArrayList<Person> p = new ArrayList<Person>();
//                System.out.println("Most Fittest Person on the Current Generation");
                while (!pq.isEmpty()) p.add(pq.delMin());
                Person bPerson = p.get(p.size()-1);
                bestPerson = p.get(p.size()-1).getfitnessScore();
                logger.info("Best Fitness Score: "+ p.get(p.size()-1).getfitnessScore()/960.0);
                logger.info("Activity List Done in 16 Hours ");
                logger.info(bPerson.getAllGeneSequence());
                String act = "";
                for(Gene g : bPerson.ch.genePool){
                    act += g.activity+ "|";
                }
                logger.info(act);
                population.deleteAllColony();

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
            }
            else{
                System.out.println("Culling Skipped As Total Population < Maximum Expected ");
            }
//        System.out.println("Getting Most Fittest Person done...");
        return bestPerson;
    }



    public void breedIntraColony(Colony c,ArrayList<Person>newGenAllPerson) {
//        System.out.println("Breeding - INTRA Colony");
        Random r = new Random();
        //**********Creating Person Array from Current Colony ****************
        Person personArray [] = new Person[c.size()];
        int pCount = 0;
        for(Person p : c.getAllPerson()){ personArray[pCount] = p;pCount++; }
        //*********************************************************************
//            System.out.println("All Persons of Current Colony Copied ..."+ personArray.length);
//            System.out.println("Toal Size of GENR ST Table : " + geneST.size());
//            System.out.println("Log of geneST Size "+ Math.log(geneST.size()));
//            double allBinValue = geneST.size() *  Math.log10(geneST.size());
        //***************************************************************************************************
        //** Creating Map of consisting ALL Possible Pairs for Mating from Population ***********************
        //***************************************************************************************************
        Map<Integer,ArrayList<Integer>> map = new HashMap();                       //n*(n-1)/2 total Possible Pairs
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
        //*************************************************************************************
        //Size of Map Grows in Order of 4,6,15,105,5460 ..... in each generation
        //Untill it is Less than expected total Number of Population , it loops  from 0 to Map.Size()
        if(map.size() < 100){
                System.out.println("Creating Mutation == "+ map.size());
            for(int i = 0 ; i <map.size();i++){
                ArrayList<Integer> index = map.get(i);
//                    System.out.println("Mutation No. " + count + "Person Selected :" + index.get(0) + " " + index.get(1));
                ArrayList<Gene> child1Gene = new ArrayList<Gene>();
                ArrayList<Gene> child2Gene = new ArrayList<Gene>();
                domutation(personArray[index.get(0)], personArray[index.get(1)], child1Gene, child2Gene, newGenAllPerson);
            }
        }
        // When Map.size() i.e. No of All Possible Pairs >
        else {
//                System.out.println("Creating Mutation == "+ Configuration.MaximumNumberOfPopulation/2*population.getColonyCount());
//            System.out.println("Creating Mutation == 100");
            int count = 0;
            int [] random10num = new int [10];
            for(int rc = 0; rc<random10num.length; rc++) random10num[rc] = r.nextInt(100);
            boolean [] tomutate = new boolean[100];
            for(int rc : random10num) tomutate[rc] = true;
            /***************************************************/
            while (count < 100) {
                int s = map.size();
//                    System.out.println( "Count "+ count);
                int index1 = r.nextInt(s);
////                System.out.println("Random Number generated :"+ index1);
                ArrayList<Integer> index = map.get(index1);
                ArrayList<Gene> child1Gene = new ArrayList<Gene>();
                ArrayList<Gene> child2Gene = new ArrayList<Gene>();
//
//                    System.out.println("Mutation No. " + count + "Person Selected :" + index.get(0) + " " + index.get(1));
                if (tomutate[count]){
//                    System.out.print("mutate occurs");
                domutation(personArray[index.get(0)], personArray[index.get(1)], child1Gene, child2Gene, newGenAllPerson);
                }
                else {
                    if(count%2 ==0){
                    donormalFission(personArray[index.get(0)], personArray[index.get(1)],child1Gene, child2Gene,newGenAllPerson);}
                    else{
                        domutation2(personArray[index.get(0)], personArray[index.get(1)],child1Gene, child2Gene,newGenAllPerson);
                    }
                }


                count++;
            }
        }
    }

    public void domutation2(Person person1, Person person2, ArrayList<Gene> child1Gene, ArrayList<Gene> child2Gene, ArrayList<Person> newGenAllPerson) {
        Random r = new Random();
        int [] random10num = new int [10];
        for(int rc = 0; rc<random10num.length; rc++) random10num[rc] = r.nextInt(person1.ch.genePool.size());
        boolean [] tomutate = new boolean[person1.ch.genePool.size()];
        for(int rc : random10num) tomutate[rc] = true;

        int countg1 = 0;
//        System.out.print(" B4 MT value : "+ person1.getfitnessScore());
        for(Gene g :person1.ch.genePool)
            { if(tomutate[countg1]) {
                double searchThatActivity = g.timeTaken;
                ArrayList<Gene> tempGenePool = new ArrayList<Gene>();
                for(Gene gp: geneST.genePool ){
                    if(gp.timeTaken == searchThatActivity ){ tempGenePool.add(gp); }
                }
//                System.out.print("tempGP S: "+tempGenePool.size()+ "||");
                int pickThatGene = r.nextInt(tempGenePool.size());
                int tgeneCounter = 0;
                for(Gene tgene: tempGenePool){
                    if (pickThatGene == tgeneCounter ){ child1Gene.add(tgene);break; }
                    tgeneCounter++;
                }
                }
                else child1Gene.add(g);

                countg1++;
            }


        for(Gene g :person2.ch.genePool) { child2Gene.add(g); }

        Person p1 = new Person(new Chromosome(child1Gene));
//        System.out.print(" After MT value : "+ p1.getfitnessScore());

        Person p2 = new Person(new Chromosome(child2Gene));

        newGenAllPerson.add(p1);
        newGenAllPerson.add(p2);


    }

    public void donormalFission(Person person1, Person person2,ArrayList<Gene> child1Gene,ArrayList<Gene> child2Gene, ArrayList<Person> newGenAllPerson) {

        for(Gene g :person1.ch.genePool) { child1Gene.add(g); }
        for(Gene g :person2.ch.genePool) { child2Gene.add(g); }
        Person p1 = new Person(new Chromosome(child1Gene));
        Person p2 = new Person(new Chromosome(child2Gene));
        newGenAllPerson.add(p1);
        newGenAllPerson.add(p2);
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
        Person p2 = new Person(new Chromosome(child2Gene));

//        System.out.println("Parent Gene 1"+ person1.getAllGeneSequence());
//        System.out.println("Child Gene 1"+ p1.getAllGeneSequence());
//
//        System.out.println("Parent Gene2"+ person2.getAllGeneSequence());
//        System.out.println("Child Gene2"+ p2.getAllGeneSequence());
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

                child1Gene = mutateGene(child1Gene,currtotalTimeTaken1,960);
                break;
            }
            else if(totalTimeTaken1 == 960.0){child1Gene.add(g);
//            System.out.print("|TTBB"+ totalTimeTaken1+" ");
            break;}
        }
        if(totalTimeTaken1 < 960){
            child1Gene = mutateGene(child1Gene,totalTimeTaken1,960);
        }
//        System.out.println(" ");
        return child1Gene;
    }

    private ArrayList<Gene> mutateGene(ArrayList<Gene> child1Gene, double totalTimeTaken1,double totalTimeLimit) {
//            System.out.println("Mutate Gene Called ");
//        double tempTimeRequired = Math.abs(totalTimeTaken1 - parentGene.timeTaken -960) ;
        double tempTimeRequired = Math.abs(totalTimeLimit - totalTimeTaken1) ;
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
//        System.out.println("req: "+tempTimeRequired);
        double timeCount = 0.0;
        for(Gene g :child1Gene){timeCount += g.timeTaken;}
        if(timeCount < totalTimeLimit){
            double timeFill = totalTimeLimit - tempTimeRequired;
            System.out.print("VacGenAdded: of "+ timeFill);
            Gene vacantActivity = new Gene("OOOO","Vacant",timeFill,1.0);
            child1Gene.add(vacantActivity);
        }
        return child1Gene;
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
        logger.info("Half Gene Time ::"+new Person(new Chromosome(personChromosome)).getTotalTimeSpent());
        makeSecondHalfGene(personChromosome);
        logger.info("  Full Gene Time ::"+new Person(new Chromosome(personChromosome)).getTotalTimeSpent());
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

    public void makefirstHalfGene(ArrayList<Gene> personChromosome) {
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
                personChromosome = mutateGene(personChromosome,currTotaltime,480.0);
                break;
            }
        }
    }
    public void makeSecondHalfGene(ArrayList<Gene> personChromosome) {
        Double totalTime = 0.0;
    if(480 == new Person(new Chromosome(personChromosome)).getTotalTimeSpent()){
        totalTime = 480.0;
        Random r = new Random();
        while(totalTime < 960.0) {
            int activityNo = r.nextInt(graph.getTotalNode());
            Gene g = geneST.genePool[activityNo];
            double currTotaltime = totalTime;
            totalTime += g.timeTaken;
//            System.out.print(totalTime + "|");
            if(totalTime < 960.0){
                personChromosome.add(g);
            }
            else if(totalTime == 960.0){
                break;
            }
            else{
                personChromosome = mutateGene(personChromosome,currTotaltime,960.0);
                break;
            }
        }
    }
    }

    public boolean checkTotalTimeofChromosome(ArrayList<Gene> personChromosome) {
    Double totalTime = 0.0;
    for(Gene g :personChromosome){
        totalTime += g.timeTaken;
    }
        logger.info("Total Time of ch :"+ totalTime);
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
            System.out.print("NO. of Person in CoLony :"+ c.size());
            for(Person p:c.getAllPerson()){
                System.out.println("Person "+ pCount+ " ");pCount++;
                System.out.println(" Total Time :"+ p.getTotalTimeSpent());
                System.out.println("Total Reward Point "+ p.getfitnessScore());
                System.out.println(p.getAllGeneSequence());
//                System.out.println(" ");
            }
//            System.out.println(" ");

        }

    }

}
