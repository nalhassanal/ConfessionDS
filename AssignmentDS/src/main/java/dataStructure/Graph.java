package dataStructure;
import java.util.ArrayList;
public class Graph <T extends Comparable<T>>{

    private WeightedGraph <T, Integer> graph;
    private Vertex<T, Integer> head;
    private int size;

    public Graph(){
        graph = new WeightedGraph<>();
        head = null;
        size = 0;
    }

    public void clear(){
        head = null;
    }

    public int getSize(){
        return this.size;
    }

    public boolean hasVertex(T vertex){
        if (head == null)
            return false;
        Vertex<T, Integer> cur = head;
        while (cur != null){
            if (cur.vertexObject.compareTo(vertex) == 0)
                return true;
            cur = cur.nextVertex;
        }
        return false;
    }

    public boolean hasEdge (T src, T dst){
        if (head == null)
            return false;
        if (!hasVertex(src) || !hasVertex(dst))
            return false;
        Vertex<T, Integer> cur = head;
        while (cur != null){
            if (cur.vertexObject.compareTo(src) == 0){
                Edge<T, Integer> curEdge = cur.firstEdge;
                while (curEdge != null){
                    if (curEdge.dest.vertexObject.compareTo(dst) == 0)
                        return true;
                    curEdge = curEdge.nextEdge;
                }
            }
            cur = cur.nextVertex;
        }
        return false;
    }

    public int getIndeg (T vertex){
        return graph.getIndeg(vertex);
    }

    public int getOutdeg (T vertex){
        return graph.getOutdeg(vertex);
    }

    public boolean addVertex(T vertex){
        return graph.addVertex(vertex);
    }

    public int getIndex(T vertex){
        return graph.getIndex(vertex);
    }

    public T getVertex(int pos){
        return graph.getVertex(pos);
    }

    public ArrayList<T> getAllVertexObjects(){
        return graph.getAllVertexObjects();
    }

    public ArrayList<Vertex<T, Integer>> getAllVertices(){
        return graph.getAllVertices();
    }

    public boolean addEdge(T src, T dst) {
        return graph.addEdge(src, dst, null);
    }

    public boolean addUndirectedEdge(T src, T dst) {
        return graph.addUndirectedEdge(src, dst, null);
    }

    public boolean removeEdge(T src, T dst){
        return graph.removeEdge(src, dst);
    }

    public ArrayList<T> getNeighbours(T src){
        return graph.getNeighbours(src);
    }

    public void printEdges(){
        graph.printEdges();
    }

}
