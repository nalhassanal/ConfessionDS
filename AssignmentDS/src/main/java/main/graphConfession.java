package main;
import dataStructure.*;
public class graphConfession {
    public static void main(String[] args) {
        Graph<confessionPair> test = new Graph<>();
        confessionPair test1 = new confessionPair("DS00003", "jflajskd");
        confessionPair test2 = new confessionPair("DS00002", "jflajskd");
        System.out.println(test1.compareTo(test2));
        test.addVertex(test1);
        test.addVertex(test2);
        test.addUndirectedEdge(test1,test2);
        test.printEdges();
    }
}
