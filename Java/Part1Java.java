package Java;

import java.util.Scanner;

public class Part1Java {
    public static void main(String arg[]) throws InterruptedException{
        // create new instance of the producer consumer object
        Scanner sc = new Scanner(System.in);
        System.out.println("How many children? ");
        int N = sc.nextInt();
        System.out.println("Sleep for how long? (milisecs) ");
        int M = sc.nextInt();

        final ProducerConsumer producerConsumer = new ProducerConsumer(N, M);

        // Create threads:
       // Thread 
        Thread master = new Thread(producerConsumer.Producer(M));
        Thread slave[] = new Thread[N];


        master.start();
        Thread.sleep(2*M);
        System.out.println("hello1");
        for (int i = 0; i < N; i++) {
            slave[i] = new Thread(producerConsumer.Consumer(i));
            //System.out.println("hello2");
            slave[i].start();
            //System.out.println("hello3");
        }
        master.join();
        for (int i = 0; i < N; i++) {
            slave[i].join();
        }
        sc.close();
    }
    
};