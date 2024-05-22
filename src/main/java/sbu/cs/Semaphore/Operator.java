package sbu.cs.Semaphore;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Semaphore;

public class Operator extends Thread {
    Semaphore sem = new Semaphore(2);

    public Operator(String name) {
        super(name);
    }

    @Override
    public void run() {
        try {
            sem.acquire();

            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedCurrentTime = currentTime.format(formatter);
            System.out.println(Thread.currentThread().getName()+"  "+formattedCurrentTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < 10; i++)
        {
            Resource.accessResource();         // critical section - a Maximum of 2 operators can access the resource concurrently
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        sem.release();
    }
}
