
package main;

import static spam.spamFilter.predictSpam;
import java.util.Timer;
import java.util.TimerTask;

public class QueueForSpamCheck{
    MyQueue<confessionPair> queue = new MyQueue<>();
    MyQueue<String> queueID = new MyQueue<>();
    MyQueue<String> queueContent = new MyQueue<>();
    Timer timer = new Timer();
    int secondsPassed = 0;
    
    public void addToQueue(confessionPair content){
        queue.enqueue(content);
    }
    
    public void removeFromQueue(){
        queue.dequeue();
    }
    
    public void display(){
        for(int i = 0; i < queue.getSize(); i++){
            System.out.println(queue.getElement(i));
        }
    }
    
    public void popUp(){
        System.out.println("Your confession has been approved !");
    }
    
    TimerTask task = new TimerTask(){
        public void run(){
            secondsPassed++;
            if(secondsPassed > 10){
               stop();
               
        }
    };
    
    public void start(){
        timer.scheduleAtFixedRate(task,1000,1000);
    }
    
    public void stop(){
        timer.cancel();
    }
    
    public boolean spamCheck(confessionPair content){
        boolean spam = predictSpam(content.getContent().split(" ") , true);
        if(spam){
            return true;
        }else
            return false;
    }
    
    public void checkingQueue(confessionPair content){
        // remember to add TIMER next time !
        addToQueue(content); 
        System.out.println("Checking The Content For Any Spam : ");
        System.out.print("Please wait a moment");
        start();
        // timer (condition if index sekian2...timer dia sekian2)
        if(spamCheck(content)==false)
            popUp();
        else{
            System.out.println("Spam detected ! Confession rejected !");
            removeFromQueue();
        }
    }
    
    public static void main(String[] args) {
        confessionPair pair = new confessionPair("ds00001","hayyo my name is shit");
        QueueForSpamCheck test = new QueueForSpamCheck();
        test.addToQueue(pair);
    }
}
