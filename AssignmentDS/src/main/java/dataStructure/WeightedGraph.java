package dataStructure;
import java.util.ArrayList;
public class WeightedGraph <T extends Comparable<T>, N extends Comparable<N>>{
    private Vertex<T, N> head;
    private int size;

    public WeightedGraph(){
        head = null;
        size = 0;
    }

    protected Vertex<T, N> getHead(){
        return this.head;
    }

    public void clear(){
        head = null;
    }

    public int getSize(){
        return this.size;
    }

    public int getIndeg(T vertexNode){
        if (hasVertex(vertexNode)){
            Vertex<T, N> curr = head;
            while (curr != null){
                if (curr.vertexObject.compareTo(vertexNode) == 0)
                    return curr.indeg;
                curr = curr.nextVertex;
            }
        }
        return -1;
    }

    public int getOutdeg(T vertexNode){
        if (hasVertex(vertexNode)){
            Vertex<T, N> curr = head;
            while (curr != null){
                if (curr.vertexObject.compareTo(vertexNode) == 0)
                    return curr.outdeg;
                curr = curr.nextVertex;
            }
        }
        return -1;
    }

    public boolean hasVertex(T vertexNode){
        if (head == null)
            return false;
        Vertex<T, N> cur = head;
        while (cur != null){
            if (cur.vertexObject.compareTo(vertexNode) == 0)
                return true;
            cur = cur.nextVertex;
        }
        return false;
    }

    public boolean hasEdge(T src, T dst){
        if (head == null)
            return false;
        if (!hasVertex(src) || !hasVertex(dst))
            return false;
        Vertex<T, N> cur = head;
        while (cur != null){
            if (cur.vertexObject.compareTo(src) == 0){
                Edge<T, N> curEdge = cur.firstEdge;
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

    public boolean addVertex(T vertexNode){
        if (!hasVertex(vertexNode)){
            Vertex <T, N> temp = head;
            Vertex <T, N> newVertex = new Vertex<>(vertexNode, null);

            if (head == null)
                head = newVertex;
            
            else {
                Vertex<T, N> curr = head;
                while (temp != null){
                    curr = temp;
                    temp = temp.nextVertex;
                }
                curr.nextVertex = newVertex;
            }
            size++;
            return true;
        }
        return false;
    }
    

    public int getIndex(T vertexNode){
        Vertex<T, N> curr = head;
        int pos = 0;
        while (curr != null){
            if (curr.vertexObject.compareTo(vertexNode) == 0)
                return pos;
            curr = curr.nextVertex;
            pos++;
        }
        return -1;
    }

    public T getVertex(int pos){
        if (pos > size - 1 || pos < 0)
            return null;
        Vertex<T, N> curr = head;
        for (int i = 0; i < pos; i++)
            curr = curr.nextVertex;
        return curr.vertexObject;
    }

    public ArrayList<T> getAllVertexObjects(){
        ArrayList<T> ls = new ArrayList<>();
        Vertex<T, N> curr = head;
        while (curr != null){
            ls.add(curr.vertexObject);
            curr = curr.nextVertex;
        }
        return ls;
    }

    public ArrayList<Vertex<T, N>> getAllVertices(){
        ArrayList<Vertex<T, N>> ls = new ArrayList<>();
        Vertex<T, N> curr = head;
        while (curr != null){
            ls.add(curr);
            curr = curr.nextVertex;
        }
        return ls;
    }

    public boolean addEdge(T src, T dst, N weight){
        if (head == null)
            return false;
        if (!hasVertex(src) || !hasVertex(dst))
            return false;

        Vertex<T, N> curr = head;
        while (curr != null){
            if (curr.vertexObject.compareTo(src) == 0){
                Vertex<T, N> temp = head;
                while (temp != null){
                    if (temp.vertexObject.compareTo(dst) == 0){
                        Edge<T, N> currEdge = curr.firstEdge;
                        Edge<T, N> newEdge = new Edge<>(temp, weight, currEdge);
                        curr.firstEdge = newEdge;
                        curr.outdeg++;
                        temp.indeg++;
                        return true;
                    }
                    temp = temp.nextVertex;
                }
            }
            curr = curr.nextVertex;
        }
        return false;
    }

    public boolean addUndirectedEdge(T src, T dst, N weight){
        return addEdge(src, dst, weight) && addEdge(dst, src, weight);
    }

    public boolean removeEdge(T src, T dst){
        if (!hasEdge(src, dst))
            return false;
        Vertex<T, N> source = head;
        while (source != null){
            if (source.vertexObject.compareTo(src) == 0){
                Edge <T, N> currentEdge = source.firstEdge;
                if (currentEdge.dest.vertexObject.compareTo(dst) == 0){
                    source.firstEdge = currentEdge.nextEdge;
                    currentEdge.nextEdge = null;
                }
                else {
                    Edge<T, N> prevEdge = currentEdge;
                    while (currentEdge != null){
                        if (currentEdge.dest.vertexObject.compareTo(dst) == 0){
                            prevEdge.nextEdge = currentEdge.nextEdge;
                            currentEdge.nextEdge = null;
                            break;
                        }
                    }
                }
                source.outdeg--;
                currentEdge.dest.indeg--;
                System.out.println("Edge from " + src + " to " + dst + " has been removed.");
                return true;
            }
            source = source.nextVertex;
        }
        return false;
    }

    public N getEdgeWeight(T src, T dst){
        if (head == null)
            return null;
        if (!hasVertex(src) || !hasVertex(dst))
            return null;
        Vertex<T, N> curr = head;
        while (curr != null){
            if (curr.vertexObject.compareTo(src) == 0){
                Edge<T, N> currEdge = curr.firstEdge;
                while (currEdge != null){
                    if (currEdge.dest.vertexObject.compareTo(dst) == 0)
                        return currEdge.weight;
                    currEdge = currEdge.nextEdge;
                }
            }
            curr = curr.nextVertex;
        }
        return null;
    }

    public ArrayList<T> getNeighbours(T src){
        if (!hasVertex(src))
            return null;
        ArrayList<T> ls = new ArrayList<>();
        Vertex<T, N> curr = head;
        while (curr != null){
            if (curr.vertexObject.compareTo(src) == 0){
                Edge<T, N> currEdge = curr.firstEdge;
                while (currEdge != null){
                    ls.add(currEdge.dest.vertexObject);
                    currEdge = currEdge.nextEdge;
                }
                break;
            }
            curr = curr.nextVertex;
        }
        return ls;
    }

    public void printEdges(){
        Vertex<T, N> curr = head;
        while (curr != null){
            System.out.print("# " + curr.vertexObject + " : ");
            Edge<T, N> currEdge = curr.firstEdge;
            while (currEdge != null){
                System.out.print("[" + curr.vertexObject + "," + currEdge.dest.vertexObject + "]");
                currEdge = currEdge.nextEdge;
            }
            System.out.println();
            curr = curr.nextVertex;
        }
        System.out.println();
    }
    
    public boolean removeEdgeasdas(T src, T dst){
        if (!hasEdge(src, dst))
            return false;
        Vertex<T, N> source = head;
        while (source != null){
            if (source.vertexObject.compareTo(src) == 0){
                Edge <T, N> currentEdge = source.firstEdge;
                if (currentEdge.dest.vertexObject.compareTo(dst) == 0){
                    source.firstEdge = currentEdge.nextEdge;
                    currentEdge.nextEdge = null;
                }
                else {
                    Edge<T, N> prevEdge = currentEdge;
                    while (currentEdge != null){
                        if (currentEdge.dest.vertexObject.compareTo(dst) == 0){
                            prevEdge.nextEdge = currentEdge.nextEdge;
                            currentEdge.nextEdge = null;
                            break;
                        }
                    }
                }
                source.outdeg--;
                currentEdge.dest.indeg--;
                System.out.println("Edge from " + src + " to " + dst + " has been removed.");
                return true;
            }
            source = source.nextVertex;
        }
        return false;
    }

    
    
    public T removeVertex(T vertex){
        
         Vertex<T,N> removing = head;
         
         while(removing!=null){
             if(removing.vertexObject.equals(vertex)){
                 Vertex<T,N> temp = removing.nextVertex;
                 removing = temp; //to copy vertex temp into removing
                 removing.nextVertex = temp.nextVertex;
                 temp = null;
                 size--;
                 
             }
         }
         return removing.vertexObject;
    }
    







}
