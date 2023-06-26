public class Edge {
    private Vertice initialVertice;
    private Vertice finalVertice;
    private int weight;

    public Edge(Vertice initialVertice, Vertice finalVertice, int weight) {
        this.initialVertice = initialVertice;
        this.finalVertice = finalVertice;
        this.weight = weight;
    }

    public Vertice getInitialVertice() {
        return initialVertice;
    }

    public Vertice getFinalVertice() {
        return finalVertice;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Edge { " +
                "initialVertice = " + initialVertice +
                ", finalVertice = " + finalVertice +
                ", weight = " + weight +
                " }";
    }
}
