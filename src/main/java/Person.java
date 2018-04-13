public class Person implements Comparable<Person> {
    Chromosome ch;
    Person nextPerson;
    Person previousPerson;
    private Double fitnessScore;

    Person(Chromosome ch){
        this.ch = ch;
        this.fitnessScore = 0.0;
    }

    public void setFitnessScore (Double fitnessScore){
        this.fitnessScore = fitnessScore;
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


    public int compareTo(Person o) {
        if (this.getTotalRewardFetched() > o.getTotalRewardFetched()){
            return 1;
        }
        else if (this.getTotalRewardFetched() < o.getTotalRewardFetched()){
            return -1;
        }
        return 0;
    }
}
