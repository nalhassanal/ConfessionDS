package dataStructure;

public class Vertex <T extends Comparable<T>, N extends Comparable<N>>{
    T vertexObject;
    int indeg;
    int outdeg;
    Vertex<T, N> nextVertex;
    Edge<T, N> firstEdge;

    public Vertex(){
        vertexObject = null;
        indeg = outdeg = 0;
        nextVertex = null;
        firstEdge = null;
    }

    public Vertex(T vertexObject, Vertex<T, N> nextVertex){
        this.vertexObject = vertexObject;
        indeg = outdeg = 0;
        this.nextVertex = nextVertex;
        firstEdge = null;
    }

    @Override
    public String toString(){
        return "Vertex: " + vertexObject;
    }
}
