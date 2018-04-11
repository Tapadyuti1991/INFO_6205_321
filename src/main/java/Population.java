public class Population {
    Colonies firstColony;
    Colonies lastColony;
    int size;
    ActivityNodeGraph graph;

    public Population(ActivityNodeGraph graph){
        this.graph = graph;
        this.firstColony = null;
        this.lastColony = null;
        this.size = 0;
    }



}
