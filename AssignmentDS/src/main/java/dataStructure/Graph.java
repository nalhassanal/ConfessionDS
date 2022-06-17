package dataStructure;
import java.util.ArrayList;
import main.confessionPair;
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
    
     public T Search(int ConfessId){
        if (ConfessId > size - 1 || ConfessId < 0)
            return null;
        Vertex<T, Integer> curr = head;
        for (int i = 0; i < ConfessId; i++)
            curr = curr.nextVertex;
        return curr.vertexObject;
    }
     
      public T removeFirstVertex(){
        if (size == 0) return null;
     else {
         Vertex<T,Integer> temp = graph.getHead();
         graph.setHead(graph.getHead().nextVertex);
         size--;
        
         return temp.vertexObject;
     }
        
    }
    
public boolean removeVertex(T vertex){
            System.out.println("vertex index: " + getIndex(vertex));
          
            if(getIndex(vertex)==0) removeFirstVertex();
         
            else{
            Vertex<T,Integer> current = graph.getHead();
         for(int i=0; i < getIndex(vertex)-1; i++){
             current = current.nextVertex;}
             
            Vertex<T,Integer> temp = current.nextVertex;
            current.nextVertex = temp.nextVertex;
            size--;
            }
         return true;
    }
        
       public void BFS(T vertex){
            
            for(int i=0; i < size; i++){
                
                if(hasEdge(vertex,getVertex(i))){
                    System.out.println(getVertex(i));
                    removeEdge(vertex,getVertex(i));
                    removeVertex(getVertex(i));
                    
                }
                
                else System.out.println(vertex + " dont have any edges with " + getVertex(i) );
            }
        
        }


}
