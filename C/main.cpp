#include <stdio.h>
#include <stdlib.h>
#include <iostream>

#include "BoundedBuffer.h"

#define N 10


int main(int arg, char** args)
{

    BoundedBuffer bBuffer(N);

    bBuffer.Producer();
    //"Listen" for requests, and send them to the request Queue
    
    //Any Slave thread grabs request out of the queue and runs it.
    bBuffer.Consumer();


    return EXIT_SUCCESS;
}
