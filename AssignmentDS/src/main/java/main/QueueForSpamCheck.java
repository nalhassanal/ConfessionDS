
package main;

import static spam.spamFilter.predictSpam;

public class QueueForSpamCheck{
    MyQueue<confessionPair> queue = new MyQueue<>();
    
    public void addToQueue(confessionPair content){
        queue.enqueue(content);
    }
    
    public void removeFromQueue(){
        queue.dequeue();
    }
    
    public void checkForSpam(confessionPair content){
        boolean spam = predictSpam(content.getContent().split(" "), true);
        System.out.println(spam);
    }
    
    public void checkingQueue(confessionPair content){
        int index = 0;
        // remember to add TIMER next time !
        addToQueue(content);
        index ++;
        // timer (condition if index sekian2...timer dia sekian2)
        for(checkForSpam(content).{
            
        }
    }
}
