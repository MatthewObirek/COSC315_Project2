#pragma once
#include <queue>
struct task
{
    int id;
    int length;

};

class Semaphore
{
private:
    int value;
    std::queue<task> inTasks;
public:
    Semaphore(int n);
    void wait(task t);
    void Signal();
};