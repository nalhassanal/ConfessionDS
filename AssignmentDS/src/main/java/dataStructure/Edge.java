package dataStructure;

public class Edge <T extends Comparable<T>, N extends Comparable<N>>{
    Vertex<T, N> dest;
    N weight;
    Edge<T, N> nextEdge;

    public Edge(){
        dest = null;
        weight = null;
        nextEdge = null;
    }

    public Edge(Vertex<T, N> dest, N weight, Edge<T, N> nextEdge){
        this.dest = dest;
        this.weight = weight;
        this.nextEdge = nextEdge;
    }

    @Override
    public String toString(){
        return "Destination: " + dest +", Weight: " + weight;
    }
}
