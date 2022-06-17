
package main;

import java.util.Arrays;
import java.util.LinkedList;
   
public class MyQueue<E> {

    private LinkedList<E> list;

    public MyQueue(E[] e) {
        list = new LinkedList<>(Arrays.asList(e));
    }
    
    public MyQueue(){
        
    }  

    public void enqueue(E e) {
        list.add(e);
    }

    public E dequeue() {
        return list.removeFirst();
    }

    public E getElement(int i) {
        if(i < 0 && i >= list.size()) return null;
        return list.get(i);
    }

    public E peek() {
        if (list.isEmpty()) return null;
        return list.getFirst();
    }
    
    public E front(){
        return list.getFirst();
    }

    public int getSize() {
        return list.size();
    }

    public boolean contains(E e) {
        return list.contains(e);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public String toString() {
        return list.toString();
    }
}
