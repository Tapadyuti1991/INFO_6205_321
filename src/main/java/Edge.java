public class Edge {
    private final int v; // Source Vertex
    private final int w; // Target Vertex
    private final double weight; // edge weight
    private final double timeTaken; // Edge Time Taken for Activity


    public Edge(int v, int w, double weight,double time) {
        this.v = w;
        this.w = v;
        this.weight = weight;
        this.timeTaken = time;
    }

    public double weight(){return weight;}

    public int from(){return v;}

    public int to(){return w;}

    public int compareTo(Edge that){
        if (this.weight() < that.weight()) return  -1;
        else if (this.weight() > that.weight()) return +1;
        else return 0;
    }

    public String toString(){
        return String.format("%d-%d %.2f",v,w,weight);
    }

}
