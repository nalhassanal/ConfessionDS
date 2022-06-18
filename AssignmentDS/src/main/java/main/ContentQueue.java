
package main;

import java.util.Timer;
import java.util.TimerTask;

public class ContentQueue {
    MyQueue<confessionPair> queue = new MyQueue<>();
    Timer timer = new Timer();
    int seconds = 0;
    
    public void display(){
        for(int i = 0; i < queue.getSize(); i++){
            System.out.println(queue.getElement(i));
        }
    }
    
    TimerTask task = new TimerTask(){
        public void run(){
            seconds++;
            do{
                
            }while(seconds < 10)
        }
    };
    
    public void start(){
        timer.scheduleAtFixedRate(task,1000,1000);
    }
    
    public void stop(){
        timer.cancel();
    }
}
