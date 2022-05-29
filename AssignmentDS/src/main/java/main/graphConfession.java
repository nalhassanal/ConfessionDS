package main;
import dataStructure.*;
public class graphConfession {
    public static void main(String[] args) {
        Graph<confessionPair> test = new Graph<>();
        confessionPair test1 = new confessionPair("DS00001", "jflajskd");
        confessionPair test2 = new confessionPair("DS00002", "jflajskd");
        confessionPair test3 = new confessionPair("DS00003", "jflajskd");

        confessGraph<confessionPair> graph = new confessGraph<>();

        graph.addVertex(test1);
        graph.addVertex(test2);
        graph.addVertex(test3);
//        graph.addUndirectedEdge(test1, test2);
        graph.addEdge(test1, test2);
        graph.addEdge(test1, test3);
        graph.addUndirectedEdge(test2,test3);
        graph.printEdges();

    }
}
