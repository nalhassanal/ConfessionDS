package dataStructure;

public class testGraph {
    public static void main(String[] args) {
        Graph <String> graph = new Graph<>();
        String [] test = {"a" , "b", "c", "d", "e"};
        for (String element : test)
            graph.addVertex(element);

        for (int i = 0; i < graph.getSize(); i++) {
            System.out.print(i + ": " + graph.getVertex(i) + "\t");
        }
        System.out.println();

        System.out.println("Add edge from a to b : " + graph.addEdge("a", "b"));

//        System.out.println(graph.getEdgeWeight("a" , "b"));

        graph.printEdges();
    }
}
