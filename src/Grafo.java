import java.util.*;

public class Grafo {
    private ArrayList<Vertice> vertices;
    private boolean isDirected;
    private boolean isWeighted;

    public Grafo(boolean isDirected, boolean isWeighted){
        this.vertices = new ArrayList<Vertice>();
        this.isDirected = isDirected;
        this.isWeighted = isWeighted;
    }

    public void addVertice(String data){
        Vertice nuevoVertice = new Vertice(data);
        this.vertices.add(nuevoVertice);
    }

    public void addEdge(String initialVertice, String finalVertice, int weight){
        if(!this.isWeighted){
            weight = 1;
        }

        getVertexByValue(initialVertice).addEdge(getVertexByValue(finalVertice), weight);

        if(!this.isDirected){
            getVertexByValue(finalVertice).addEdge(getVertexByValue(initialVertice), weight);
        }
    }

    public void removeEdge(Vertice initialVertice, Vertice finalVertice){
        initialVertice.removeEdge(finalVertice);
        if(!this.isDirected){
            finalVertice.removeEdge(initialVertice);
        }
    }

    public void removeVertice(Vertice vertice){
        this.vertices.remove(vertice);
    }

    public ArrayList<Vertice> getVertices() {
        return vertices;
    }

    public boolean isDirected() {
        return isDirected;
    }

    public boolean isWeighted() {
        return isWeighted;
    }

    public Vertice getVertexByValue(String value){
        for(Vertice v: this.vertices){
            if(v.getData().equals(value)){
                return v;
            }
        }
        return null;
    }

    public String print(){
        String grafoString = "";
        for(Vertice v: this.vertices){
            grafoString += v.print(isWeighted);
            grafoString += "\n";
        }
        return grafoString;
    }

    /*public String depthFirstTraversal(Vertice startVertex, ArrayList<Vertice> visitedVertices){
        String result = "";
        result += startVertex.getData() + "\n";
        for(Edge e: startVertex.getEdges()){
            Vertice vecino = e.getFinalVertice();
            if(!visitedVertices.contains(vecino)){
                visitedVertices.add(vecino);
                result += depthFirstTraversal(vecino, visitedVertices);
            }
        }
        return result;
    }*/

    public String depthFirstTraversal(Vertice startVertex, ArrayList<Vertice> visitedVertices){
        visitedVertices.add(startVertex);
        String result = startVertex.getData() + "\n";
        for(Edge e: startVertex.getEdges()){
            Vertice vecino = e.getFinalVertice();
            if(!visitedVertices.contains(vecino)){
                result += depthFirstTraversal(vecino, visitedVertices);
            }
        }
        return result;
    }

    public String breadthFirstTraversal(Vertice startVertex) {
        ArrayList<Vertice> visitedVertices = new ArrayList<>();
        Queue<Vertice> queue = new LinkedList<>();
        queue.add(startVertex);
        visitedVertices.add(startVertex);

        String result = "";

        while (!queue.isEmpty()) {
            Vertice currentVertex = queue.poll();
            result += currentVertex.getData() + "\n";

            for (Edge edge : currentVertex.getEdges()) {
                Vertice neighbor = edge.getFinalVertice();
                if (!visitedVertices.contains(neighbor)) {
                    visitedVertices.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        return result;
    }

    public String dijkstra(Vertice startVertex) {
        HashMap<Vertice, Integer> distances = new HashMap<>();
        HashMap<Vertice, Vertice> previousVertices = new HashMap<>();
        PriorityQueue<Vertice> queue = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        for (Vertice vertex : vertices) {
            if (vertex.equals(startVertex)) {
                distances.put(vertex, 0);
            } else {
                distances.put(vertex, Integer.MAX_VALUE);
            }
            queue.add(vertex);
        }

        while (!queue.isEmpty()) {
            Vertice currentVertex = queue.poll();
            for (Edge edge : currentVertex.getEdges()) {
                Vertice neighbor = edge.getFinalVertice();
                int alternatePathDistance = distances.get(currentVertex) + edge.getWeight();
                if (alternatePathDistance < distances.get(neighbor)) {
                    distances.put(neighbor, alternatePathDistance);
                    previousVertices.put(neighbor, currentVertex);
                    queue.remove(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        String result = "";
        for (Vertice vertex : vertices) {
            if (distances.get(vertex) == Integer.MAX_VALUE) {
                result += "No hay camino de " + startVertex.getData() + " a " + vertex.getData() + "\n";
            } else {
                result += "El camino más corto de " + startVertex.getData() + " a " + vertex.getData() + " es ";
                ArrayList<Vertice> path = new ArrayList<>();
                for (Vertice currentVertex = vertex; currentVertex != null; currentVertex = previousVertices.get(currentVertex)) {
                    path.add(currentVertex);
                }
                Collections.reverse(path);
                for (Vertice pathVertex : path) {
                    result += pathVertex.getData() + " ";
                }
                result += "con un peso total de " + distances.get(vertex) + "\n";
            }
        }

        return result;
    }


}
