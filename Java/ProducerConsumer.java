package Java;

import java.time.LocalTime;
import java.util.LinkedList;

public class ProducerConsumer {
    // circular buffer = linked list
    private LinkedList<Integer> buffer = new LinkedList<>();
    // private var N = size of queue (buffer) and the num of consumers
    private int N = 0;
    // private var M = how long a producer should sleep before producing next
    private int M = 0;

    // constructors 
    public ProducerConsumer(){}

    public ProducerConsumer(int num, int sleepLength){
        this.N = num;
        this.M = sleepLength;
    }

    // methods
    // producer method to be called by the parent thread
    public synchronized void Producer () throws InterruptedException{

        int requestID = 0;
        while (requestID < this.N){
            LocalTime CURRENT_TIME = LocalTime.now();
            int randomLength = (int) (Math.random() * this.M) + 1;
            // Tells the parent thread to wait if the buffer is full
            while(buffer.size() == this.N){
                wait();
            }

            // add to buffer (index = requestID and item stored in buffer = randomLength)
            buffer.add(randomLength);

            // print statements
            System.out.println("Producer: produced request ID " + requestID + ", length " + randomLength/1000 + " seconds at time " + CURRENT_TIME);
            System.out.println("Producer: sleeping for " + this.M + " miliseconds\n");

            requestID ++;

            // signal that the condition the consumer thread is waiting on is satisfied
            notify();

            // puts thread to sleep for specified M length of time
            Thread.sleep(this.M);
        }

    }

    public synchronized void Consumer () throws InterruptedException{

        System.out.println("\n\n");

        while (true){

            //while the buffer is empty the threat should wait
            while(buffer.size() == 0){
                wait();
            }

            for(int i = 0; i < this.N; i++){
                LocalTime CURRENT_TIME = LocalTime.now();

                // get ID and length, then remove request from buffer
                int id = i;
                int length = buffer.get(id);

                // print statement
                System.out.println("Consumer: assigned request ID " + id + " , processing request for the next " + length/1000 + " seconds, current time is " + CURRENT_TIME);

                // thread is busy for the amount of time specified from the buffer
                Thread.sleep(length);

                // print statement
                CURRENT_TIME = LocalTime.now();
                System.out.println("Consumer: completed request ID " + id + " at time " + CURRENT_TIME + "\n");

                // signal that the condition the producer thread is waiting on is satisfied
                notify();

            }
            
            buffer.clear();

        }
    }
}
