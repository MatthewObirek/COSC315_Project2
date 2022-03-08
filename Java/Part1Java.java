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
        Thread master = new Thread(new Runnable() {
            @Override
            public void run(){
                try {
                    producerConsumer.Producer();

                    for (int i = 0; i < N; i++){
                        producerConsumer.Consumer();
                    }
                    
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        master.start();

        master.join();
    }
};