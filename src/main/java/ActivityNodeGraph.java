public class ActivityNodeGraph {

    private int N;  //Number of Nodes in GRaph
    private int E;         // Number of Edges;
    private Bag<Edge>[] adj;  //adjacency lists

    public ActivityNodeGraph(int n) {
        this.N = n;
        this.E = 0;
        adj = (Bag<Edge>[]) new Bag[N]; // BAG of type Edge
        for (int i = 0; i < N; i++) {    // Initiating with Blank Bags in adj
            adj[i] = new Bag<Edge>();
        }
    }
    //Getter Setter for N , E
    public int getTotalNode() { return N; }
    public int getTotalEdge() { return E; }

    public void addEdge(Edge e)
    {
        adj[e.from()].add(e);
        E++;
    }

    public Iterable<Edge> adj(int v) { return adj[v]; }

    public Iterable<Edge> edges()
    {
        Bag<Edge> b = new Bag<Edge>();
        int count = 0 ;
        int count1 = 0;
        for (int v = 0; v < N; v++) {
            for (Edge e : adj[v]) {
//                System.out.println(e.toString());
                b.add(e);
            }
        }
        return b;
    }
}
