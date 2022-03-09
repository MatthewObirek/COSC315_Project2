package Java;

import java.time.LocalTime;
import java.util.ArrayList;
//import java.util.concurrent.Semaphore;
//import java.util.concurrent.locks.Lock;

public class ProducerConsumer {
    // circular buffer = linked list
    private volatile ArrayList<Integer> buffer;
    private volatile int requestID;
    private static final Object mutex = new Object();
    // private var N = size of queue (buffer) and the num of consumers
    private volatile int N = 0;
    // private var M = how long a producer should sleep before producing next
    private volatile int M = 0;

    // constructors 
    public ProducerConsumer(){}

    public ProducerConsumer(int num, int sleepLength){
        this.N = num;
        this.M = sleepLength;
        this.buffer = new ArrayList<Integer>(num);
        this.requestID = 0;
    }

    // methods
    // producer method to be called by the parent thread
    public Runnable Producer (int M) throws InterruptedException {

        Runnable temp = new Runnable() {
            @Override
            public void run(){
                //int requestID = 0;
                while (true) {
                    LocalTime CURRENT_TIME = LocalTime.now();
                    int randomLength = (int) (Math.random() * M) + 1;
                    synchronized(mutex){
                        // add to buffer (index = requestID and item stored in buffer = randomLength)
                        buffer.add(randomLength);

                        // print statements
                        System.out.println("Producer: produced request ID " + requestID + ", length " + randomLength/1000 + " seconds at time " + CURRENT_TIME);
                        System.out.println("Producer: sleeping for " + M + " miliseconds\n");

                        requestID++;
                    }
                    // signal that the condition the consumer thread is waiting on is satisfied
                                                
                    
                    // Tells the parent thread to wait if the buffer is full
    
                    // puts thread to sleep for specified M length of time
                    try {
                        Thread.sleep(M);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        return temp;
    }

    public Runnable Consumer (int id) throws InterruptedException {
        Runnable temp = new Runnable() {
            @Override
            public void run(){
                while (true){

                    //while the buffer is empty the threat should wait
                    int length = 0;
                    boolean skipped = false;
                    LocalTime CURRENT_TIME;
                    synchronized (mutex){
                            // trys to ensure that buffer is only used if it has items in it.
                            if(!(buffer.size() == 0)) {
                            // gets Current time for the print statement
                            CURRENT_TIME = LocalTime.now();

                            
                            // get ID and length, then remove request from buffer
                            length = buffer.get(--requestID); //@todo this is where the outOfBoundsError arrises. 
                            // print statement
                            System.out.println("Consumer: assigned request ID " + requestID + " , processing request for the next " + length/1000 + " seconds, current time is " + CURRENT_TIME);
                        }
                        else{
                            skipped = true;
                        }
                    }    
                        // thread is busy for the amount of time specified from the buffer
                        try {
                            Thread.sleep(length);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        // print statement
                        if (!skipped) {
                            CURRENT_TIME = LocalTime.now();
                            System.out.println("Consumer: completed request ID " + id + " at time " + CURRENT_TIME + "\n");
                        }
  
                }
            }
        };
        return temp;
    }
}