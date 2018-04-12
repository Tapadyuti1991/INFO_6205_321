public class Person {
    Chromosome ch;
    Person nextPerson;
    Person previousPerson;

    Person(Chromosome ch){
        this.ch = ch;
    }

    public Double getTotalTimeSpent(){
        Double timeTaken = 0.0;
        for(Gene eachGene: ch.genePool){
            timeTaken += eachGene.timeTaken;
        }
        return timeTaken;
    }

    public Double getTotalRewardFetched(){
        Double totalPoints = 0.0;
        for(Gene eachGene: ch.genePool){
            totalPoints += eachGene.reward;
        }
        return totalPoints;
    }

    public String getAllGeneSequence(){
        StringBuilder sb = new StringBuilder();
        for(Gene eachGene : ch.genePool){
            sb.append(eachGene.DNA+ "-");
        }
        return sb+"";
    }



}
