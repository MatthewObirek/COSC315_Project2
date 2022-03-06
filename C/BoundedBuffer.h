#pragma once
#include "Semaphore.h"

class BoundedBuffer
{
private:
    Semaphore mutex;
    Semaphore empty;
    Semaphore full;
public:
    BoundedBuffer(int N);
    void Producer();
    void Consumer();
};