
#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>
#include <time.h>
#include <semaphore.h>




sem_t s_full;         
sem_t s_empty; 
pthread_mutex_t mutex;
int requestBuffer[10];
int requestID = 0;
char* showTime(){
	time_t t;
    struct tm *currentTime;
    t = time(0);
    // current computer time
    currentTime = localtime(&t);
	// allocate memory for the threads to read current time from memory
    char* time = malloc(sizeof(char) * 1024);
    // store it in allocated memory, pointed by pointer
    sprintf(time,"%d:%d:%d", currentTime->tm_hour, currentTime->tm_min, currentTime->tm_sec);
	return time;
}



// producerThread function
void *producerThread(void *args){
	
     while (1) {
        int requestLen = rand() % 10;
        sem_wait(&s_empty);
        // locking the access 
        pthread_mutex_lock(&mutex);
		if(requestID > 9){
		  requestBuffer[requestID%10] = requestLen;
          requestID++;
		}else{
          requestBuffer[requestID] = requestLen;
          requestID++;
		}   
        // grant other threads access to buffer
        pthread_mutex_unlock(&mutex);
        printf("--------------------------------------------\n");
		printf("Request: request ID %d, length %d seconds at time %s\n",requestID,requestLen,showTime());
        // increment a value in the full semaphore indicating one more space is taken in the buffer
        sem_post(&s_full);
		int sleepTime = (rand()%10)+1;
		printf("Request: sleeping for %d seconds\n",sleepTime);
		sleep(sleepTime);
        printf("--------------------------------------------\n");
    }

}

// consumer thread
void *consumerThread(void *args){
	int tid = *((int *) args);
    int requestLength;
	int id;

	while (1) {
        // decrement it if there is a value indicating full sempahore
        sem_wait(&s_full);
        pthread_mutex_lock(&mutex);
        requestLength = requestBuffer[(requestID%10)- 1];
		id = requestID;
        pthread_mutex_unlock(&mutex);
         // put and increment in the empty semaphore indicating there is a free slot in the buffer
        sem_post(&s_empty);

        printf("--------------------------------------------\n");
        // print latest request id, length and current time this thread consumed from the buffer
		printf("Consumer %d: assigned request ID %d, processing request for the next %d seconds, current time %s\n",tid,requestID,requestLength,showTime());
        printf("--------------------------------------------\n");
        
        // putting the comsumer thread to sleep to process the request for the request length time
        //  we got from the buffer
        sleep(requestLength);
		printf("Consumer %d: completed request ID %d at time %s\n",tid,id,showTime());


    }
    
}


int main(){

    // creating semaphores

    // empty buffer
    sem_init(&s_full, 0, 0);
    // buffer with 10 spaces in it 
    sem_init(&s_empty, 0, 10);
    pthread_t pthread, cthread[3];
    // producer thread
	pthread_create(&pthread, NULL, producerThread, NULL);
    // consumer thread
	for(int i = 0;i <3;i++){
      pthread_create(&cthread[i], NULL, consumerThread, (void *) &i);
	}

	pthread_join(pthread, NULL);
	for(int i = 0;i<3;i++){
		pthread_join(cthread[i], NULL);

	}


    // destroy sempty semaphore 
	sem_destroy(&s_empty);
    // destroy sfull semaphore 
    sem_destroy(&s_full);

    return 0;
}