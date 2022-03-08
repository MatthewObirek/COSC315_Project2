package Java;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

public class ProducerConsumer {
    // circular buffer = linked list
    private volatile LinkedList<Integer> buffer = new LinkedList<>();
    private Semaphore full;
    private Semaphore empty;
    private Object mutex;
    // private var N = size of queue (buffer) and the num of consumers
    private volatile int N = 0;
    // private var M = how long a producer should sleep before producing next
    private volatile int M = 0;

    // constructors 
    public ProducerConsumer(){}

    public ProducerConsumer(int num, int sleepLength){
        this.N = num;
        this.M = sleepLength;
        empty = new Semaphore(0);
        full = new Semaphore(num);
        mutex = new Object();
    }

    // methods
    // producer method to be called by the parent thread
    public Runnable Producer (int M) throws InterruptedException {

        Runnable temp = new Runnable() {
            @Override
            public void run(){
                int requestID = 0;
                while (true) {
                    LocalTime CURRENT_TIME = LocalTime.now();
                    int randomLength = (int) (Math.random() * M) + 1;
                    System.out.println("helloPro1");
                    synchronized(mutex){
                        System.out.println("helloPro2");
                        try {
                            empty.acquire();
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                        
                        
                        // add to buffer (index = requestID and item stored in buffer = randomLength)
                        buffer.add(randomLength);

                        // print statements
                        System.out.println("Producer: produced request ID " + requestID + ", length " + randomLength/1000 + " seconds at time " + CURRENT_TIME);
                        System.out.println("Producer: sleeping for " + M + " miliseconds\n");

                        requestID ++;

                        // signal that the condition the consumer thread is waiting on is satisfied
                        full.release();                        
                    }
                    // Tells the parent thread to wait if the buffer is full
                    //while(buffer.size() == this.N){

    
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
                    int length;
                    LocalTime CURRENT_TIME;
                    System.out.println("helloCon1");
                    synchronized (mutex){
                        System.out.println("helloCon2");
                    //for(int i = 0; i < this.N; i++){
                    CURRENT_TIME = LocalTime.now();

                    // get ID and length, then remove request from buffer

                    length = buffer.get(id);
                    }// mutex.unlock();
                    empty.release();
                    // print statement
                    System.out.println("Consumer: assigned request ID " + id + " , processing request for the next " + length/1000 + " seconds, current time is " + CURRENT_TIME);

                    // thread is busy for the amount of time specified from the buffer
                    
                    try {
                        Thread.sleep(length);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // print statement
                    CURRENT_TIME = LocalTime.now();
                    System.out.println("Consumer: completed request ID " + id + " at time " + CURRENT_TIME + "\n");

                    // signal that the condition the producer thread is waiting on is satisfied
                    //notify();

                    //}
                    
                    buffer.clear();
                }
            }
        };
        return temp;
    }
}