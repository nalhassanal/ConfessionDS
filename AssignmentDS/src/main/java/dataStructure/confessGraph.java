package dataStructure;
import java.util.ArrayList;
import main.confessionPair;
public class confessGraph <T extends confessionPair>{

    private WeightedGraph <confessionPair, Integer> graph;
    private Vertex<confessionPair, Integer> head;
    private int size;

    public confessGraph(){
        graph = new WeightedGraph<>();
        head = graph.getHead();
        size = graph.getSize() ;
    }

    public void clear(){
        graph.clear();
    }

    public int getSize(){
        return graph.getSize();
    }

    public boolean hasVertex(T vertex){
        if (head == null)
            return false;
        Vertex<confessionPair, Integer> cur = head;
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
        Vertex<confessionPair, Integer> cur = head;
        while (cur != null){
            if (cur.vertexObject.compareTo(src) == 0){
                Edge<confessionPair, Integer> curEdge = cur.firstEdge;
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

    public confessionPair getVertex(int pos){
        return graph.getVertex(pos);
    }

    public ArrayList<confessionPair> getAllVertexObjects(){
        return graph.getAllVertexObjects();
    }

    public ArrayList<Vertex<confessionPair, Integer>> getAllVertices(){
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

    public ArrayList<confessionPair> getNeighbours(T src){
        return graph.getNeighbours(src);
    }

    public void printEdges(){
        Vertex<confessionPair, Integer> curr = graph.getHead();
        while (curr != null){
            System.out.print("# " + curr.vertexObject.getId() + " : ");
            Edge<confessionPair, Integer> currEdge = curr.firstEdge;
            while (currEdge != null){
                System.out.print("[" + curr.vertexObject.getId() + "," + currEdge.dest.vertexObject.getId() + "]");
                currEdge = currEdge.nextEdge;
            }
            System.out.println();
            curr = curr.nextVertex;
        }
        
        // test2
    }

}
