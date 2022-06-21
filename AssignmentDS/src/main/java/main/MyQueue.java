
package main;

import java.util.LinkedList;

public class MyQueue<E> {
    LinkedList<E> list = new LinkedList<>();
    
    
    public void enqueue(E o){
        list.add(o);
    }
    
    public E dequeue(){
        return list.removeFirst();
    }
    
    public int getSize(){
        return list.size();
    }
    
    public E peek(){
        return list.getFirst();
    }
    
    public boolean contain(E o){
        return list.contains(o);
    }
    
    @Override
    public String toString(){
        return list.toString();
    }
    
    public E getQueue(int i){
        return list.get(i);
    }
    
    public boolean isEmpty(){
        return list.isEmpty();
    }
}
