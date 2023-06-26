import java.util.ArrayList;

public class Vertice {
    private String data;
    private ArrayList<Edge> edges;

    public Vertice(String inputData){
        this.data = inputData;
        this.edges = new ArrayList<Edge>();
    }

    public void addEdge(Vertice verticeFinal, int weight){
        this.edges.add(new Edge(this, verticeFinal, weight));
    }

    public void removeEdge(Vertice verticeFinal){
        this.edges.removeIf(edge -> edge.getFinalVertice().equals(verticeFinal));
    }

    public String getData(){
        return this.data;
    }

    public Vertice getVertex(String data){
        if(this.data.equals(data)){
            return this;
        }
        return null;
    }

    public ArrayList<Edge> getEdges(){
        return this.edges;
    }

    public String print(boolean showWeight){
        String message = "";

        if(this.edges.size() == 0){
            message += this.data + " -->";
            return message;
        }

        for(int i = 0; i < this.edges.size(); i++){
            if(i == 0){
                message += this.edges.get(i).getInitialVertice().data + " --> ";
            }

            message += this.edges.get(i).getFinalVertice().data;

            if(showWeight){
                message += "' (" + this.edges.get(i).getWeight() + ")";
            }

            if(i != this.edges.size() -1){
                message += ", ";
            }
        }
        return message;
    }
}
