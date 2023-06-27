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

    public void removeVertice(Vertice vertice){
        // Eliminar los bordes que apuntan al vértice que se va a eliminar
        for (Vertice v : vertices) {
            if (v != vertice) {
                ArrayList<Edge> edgesToRemove = new ArrayList<>();
                for (Edge edge : v.getEdges()) {
                    if (edge.getFinalVertice() == vertice) {
                        edgesToRemove.add(edge);
                    }
                }
                v.getEdges().removeAll(edgesToRemove);
            }
        }

        // Eliminar el vértice de la lista de vértices
        vertices.remove(vertice);
    }


    public ArrayList<Vertice> getVertices() {
        return vertices;
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
        // Creamos un HashMap para almacenar las distancias más cortas desde el vértice de inicio a todos los demás vértices
        HashMap<Vertice, Integer> distances = new HashMap<>();

        // Creamos otro HashMap para almacenar el vértice anterior en el camino más corto
        HashMap<Vertice, Vertice> previousVertices = new HashMap<>();

        // Creamos una PriorityQueue para gestionar los vértices cuyas distancias más cortas aún no se han encontrado
        PriorityQueue<Vertice> queue = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        // Inicializamos las distancias
        for (Vertice vertex : vertices) {
            if (vertex.equals(startVertex)) { // La distancia del vértice de inicio a sí mismo es 0
                distances.put(vertex, 0);
            } else { // La distancia inicial a todos los demás vértices es infinita
                distances.put(vertex, Integer.MAX_VALUE);
            }
            queue.add(vertex); // Añadimos cada vértice a la cola
        }
        // Ejecutamos el algoritmo de Dijkstra
        while (!queue.isEmpty()) {
            // Sacamos el vértice con la distancia más corta desde el vértice de inicio
            Vertice currentVertex = queue.poll();
            // Recorremos todos los bordes del vértice actual
            for (Edge edge : currentVertex.getEdges()) {
                Vertice neighbor = edge.getFinalVertice();
                // Calculamos la distancia del camino alternativo
                int alternatePathDistance = distances.get(currentVertex) + edge.getWeight();
                // Comprobamos si el camino alternativo es más corto
                if (alternatePathDistance < distances.get(neighbor)) {
                    // Si es así, actualizamos la distancia y el vértice anterior
                    distances.put(neighbor, alternatePathDistance);
                    previousVertices.put(neighbor, currentVertex);
                    // Actualizamos la cola
                    queue.remove(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        // Recogemos los resultados
        String result = "";
        for (Vertice vertex : vertices) {
            // Si no hay camino a un vértice, lo indicamos en la cadena de resultado
            if (distances.get(vertex) == Integer.MAX_VALUE) {
                result += "No hay camino de " + startVertex.getData() + " a " + vertex.getData() + "\n";
            } else { // Si hay un camino, lo recogemos y lo añadimos a la cadena de resultado
                result += "El camino más corto de " + startVertex.getData() + " a " + vertex.getData() + " es [";
                ArrayList<String> path = new ArrayList<>();
                // Recuperamos la ruta más corta
                for (Vertice currentVertex = vertex; currentVertex != null; currentVertex = previousVertices.get(currentVertex)) {
                    path.add(String.valueOf(currentVertex.getData()));
                }
                Collections.reverse(path); // Invertimos la ruta
                // Añadimos la ruta a la cadena de resultado usando join() para conseguir el formato [1, 2]
                result += String.join(", ", path);
                result += "] con un peso total de " + distances.get(vertex) + "\n";
            }
        }

        return result; // Devolvemos la cadena de resultado
    }



}
