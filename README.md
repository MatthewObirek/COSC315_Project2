# COSC315 Mini Project 2
This is a project that implements a multi-threaded request scheduler that is similar to how the popular apache web server schedules requests. This will be done in both Java and C.
### Group Members.
Monica Rampaul
Matthew Obirek
Manson Yu

## Comparison Discussion
he Java implementation was much more difficult than the C implementation of the problem - at least for us. I am far more familear with threading on C than I am on Java, having taken 407. I thought this would have been a piece of cake, and parts of it were, but overall it was difficult. the C implementain was definetly easier for us though.


## Java Implementation - Part 1
The Java implementation Contains the ProducerConsumer class, which contains the two methods `producer()` `Consumer()`. The Java implementation suffers from a perticularly bad out of bounds issue that arrises from incorrect locking and unlocking threads. What happens is that the thread doesnt lock in time, allowing other threads to queue after going through a conditional statement with outdated data. 
### Build Instructions
I use Visual Studio Code to run my programs. There is a very useful extention that allows for easy running of Java code. I have the JMV and jdk installed - which you can learn how to do <a href="https://www.java.com/en/download/help/linux_install.html">here</a>. When I press the run button. This is what is put into my terminal.
    `[matthewobirek@MatthewPC COSC315_Project2]$  cd "/home/matthewobirek/Documents/UBCO/Year 3/COSC 315 COSC315_Project2" ; /usr/bin/env /usr/lib/jvm/java-17-openjdk/bin/java --enable-preview -XX:+ShowCodeDetailsInExceptionMessages -cp /home/matthewobirek/.config/Code/User/workspaceStorage/a4293d73e5016adb574c1b2e91173aa6/redhat.java/jdt_ws/ `

### Sample Output
    [matthewobirek@MatthewPC COSC315_Project2]$  cd "/home/matthewobirek/Documents/UBCO/Year 3/COSC 315/COSC315_Project2" ; /usr/bin/env /usr/lib/jvm/java-17-openjdk/bin/java --enable-preview -XX:+ShowCodeDetailsInExceptionMessages -cp /home/matthewobirek/.config/Code/User/workspaceStorage/a4293d73e5016adb574c1b2e91173aa6/redhat.java/jdt_ws/COSC315_Project2_726b271/bin Java.Part1Java 
    How many children? 
    5
    Sleep for how long? (milisecs) 
    2000
    Producer: produced request ID 0, length 0 seconds at time 22:13:19.282744599
    Producer: sleeping for 2000 miliseconds

    Producer: produced request ID 1, length 1 seconds at time 22:13:21.285886816
    Producer: sleeping for 2000 miliseconds

    Consumer: assigned request ID 1 , processing request for the next 1 seconds, current time is 22:13:23.254531464
    Consumer: assigned request ID 0 , processing request for the next 0 seconds, current time is 22:13:23.254841375
    Exception in thread "Thread-3" Exception in thread "Thread-5" Exception in thread "Thread-4" java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 2
            at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:64)
            at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:70)
            at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:266)
            at java.base/java.util.Objects.checkIndex(Objects.java:359)
            at java.base/java.util.ArrayList.get(ArrayList.java:427)
            at Java.ProducerConsumer$2.run(ProducerConsumer.java:84)
            at java.base/java.lang.Thread.run(Thread.java:833)
    java.lang.IndexOutOfBoundsException: Index -2 out of bounds for length 2
            at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:64)
            at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:70)
            at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:266)
            at java.base/java.util.Objects.checkIndex(Objects.java:359)
            at java.base/java.util.ArrayList.get(ArrayList.java:427)
            at Java.ProducerConsumer$2.run(ProducerConsumer.java:84)
            at java.base/java.lang.Thread.run(Thread.java:833)
    java.lang.IndexOutOfBoundsException: Index -3 out of bounds for length 2
            at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:64)
            at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:70)
            at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:266)
            at java.base/java.util.Objects.checkIndex(Objects.java:359)
            at java.base/java.util.ArrayList.get(ArrayList.java:427)
            at Java.ProducerConsumer$2.run(ProducerConsumer.java:84)
            at java.base/java.lang.Thread.run(Thread.java:833)
    Producer: produced request ID -3, length 0 seconds at time 22:13:23.286272487
    Producer: sleeping for 2000 miliseconds

    Consumer: completed request ID 1 at time 22:13:23.305125083

    Exception in thread "Thread-2" java.lang.IndexOutOfBoundsException: Index -3 out of bounds for length 3
            at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:64)
            at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:70)
            at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:266)
            at java.base/java.util.Objects.checkIndex(Objects.java:359)
            at java.base/java.util.ArrayList.get(ArrayList.java:427)
            at Java.ProducerConsumer$2.run(ProducerConsumer.java:84)
            at java.base/java.lang.Thread.run(Thread.java:833)
    Consumer: completed request ID 0 at time 22:13:25.219751773

    Exception in thread "Thread-1" java.lang.IndexOutOfBoundsException: Index -4 out of bounds for length 3
            at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:64)
            at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:70)
            at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:266)
            at java.base/java.util.Objects.checkIndex(Objects.java:359)
            at java.base/java.util.ArrayList.get(ArrayList.java:427)
            at Java.ProducerConsumer$2.run(ProducerConsumer.java:84)
            at java.base/java.lang.Thread.run(Thread.java:833)
    Producer: produced request ID -4, length 1 seconds at time 22:13:25.286518149
    Producer: sleeping for 2000 miliseconds

    Producer: produced request ID -3, length 1 seconds at time 22:13:27.286787863
    Producer: sleeping for 2000 miliseconds


## C Implementation - Part 2
The C program contains no classes or structs. This is due to C not having classes, and the question not needing structs. This part of the project uses two Semaphores `s_empty` and `s_full` and a lock `mutex` That all allow for the boundedBuffer to be filled and emptied without any segfaults or out of bounds errors. The program loops forever, and can be stopped with `ctrl+c`. We chose C because two of our team members have experience with C pthreads.

### Build Instructions
All Linux Distros come with gcc, which will work exactly the same for this project. That being said, clang was what was used in this project. 
To run the C code either clone the repo, or download the `miniproject2.c` file, and run this in the directory it is located in.   
    clang miniproject2.c -o main.o
    ./main.o

### Sample Output
    [matthewobirek@MatthewPC C]$ clang miniproject2.c -o main.o
    [matthewobirek@MatthewPC C]$ ./main.o
    --------------------------------------------
    Request: request ID 1, length 3 seconds at time 22:17:25
    Request: sleeping for 7 seconds
    --------------------------------------------
    Consumer 1: assigned request ID 1, processing request for the next 3 seconds, current time 22:17:25
    --------------------------------------------
    Consumer 1: completed request ID 1 at time 22:17:28
    --------------------------------------------
    --------------------------------------------
    Request: request ID 2, length 7 seconds at time 22:17:32
    Request: sleeping for 6 seconds
    --------------------------------------------
    Consumer 2: assigned request ID 2, processing request for the next 7 seconds, current time 22:17:32
    --------------------------------------------
    --------------------------------------------
    --------------------------------------------
    Request: request ID 3, length 3 seconds at time 22:17:38
    Request: sleeping for 6 seconds
    --------------------------------------------
    Consumer 3: assigned request ID 3, processing request for the next 3 seconds, current time 22:17:38
    --------------------------------------------
    Consumer 2: completed request ID 2 at time 22:17:39
    Consumer 3: completed request ID 3 at time 22:17:41
    --------------------------------------------
    --------------------------------------------
    Request: request ID 4, length 6 seconds at time 22:17:44
    Request: sleeping for 3 seconds
    --------------------------------------------
    Consumer 1: assigned request ID 4, processing request for the next 6 seconds, current time 22:17:44
    --------------------------------------------
    --------------------------------------------
    --------------------------------------------
    Request: request ID 5, length 9 seconds at time 22:17:47
    Request: sleeping for 2 seconds
    --------------------------------------------
    Consumer 2: assigned request ID 5, processing request for the next 9 seconds, current time 22:17:47
    --------------------------------------------
    --------------------------------------------
    --------------------------------------------
    Request: request ID 6, length 2 seconds at time 22:17:49
    Request: sleeping for 8 seconds
    --------------------------------------------
    Consumer 3: assigned request ID 6, processing request for the next 2 seconds, current time 22:17:49
    --------------------------------------------