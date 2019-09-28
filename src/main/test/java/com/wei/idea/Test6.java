package com.wei.idea;

import org.junit.jupiter.api.Test;

/**
 * @author wei
 * @date 2019/6/23-21:28
 */
public class Test6 {
    public static void main(String[] args) {
        Count count=new Count();
        Thread thread=new Thread(new run1(count));
        thread.start();
    }
    /**
     * 重入锁
     */


}
class  run1 implements Runnable{
    private Count count=null;
    public  run1(Count count){
        this.count=count;
    }
    @Override
    public void run() {
        count.print();
    }
}


/**
 * 不可重入锁
 */
class Lock {
    private boolean isLocked = false;

    public synchronized void lock() throws InterruptedException {
        System.out.println("lock");
        while (isLocked) {
            System.out.println("阻塞");
            wait();
        }
        isLocked = true;
    }

    public synchronized void unlock() {
        System.out.println("unlock");
        isLocked = false;
        System.out.println("释放");
        notify();
    }
}

/**
 * 可重入锁 当前线程可重入
 */
 class Lock1{
    boolean isLocked = false;
    Thread  lockedBy = null;
    int lockedCount = 0;
    public synchronized void lock()
            throws InterruptedException{
        Thread thread = Thread.currentThread();
        while(isLocked && lockedBy != thread){
            wait();
        }
        isLocked = true;
        lockedCount++;
        lockedBy = thread;
    }
    public synchronized void unlock(){
        if(Thread.currentThread() == this.lockedBy){
            lockedCount--;
            if(lockedCount == 0){
                isLocked = false;
                notify();
            }
        }
    }
}

 class Count{
        Lock lock = new Lock();
        public void print(){
            System.out.println("print");
            try {
                lock.lock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            doAdd();
            lock.unlock();
        }
        public void doAdd(){
            System.out.println("doAdd");
            try {
                lock.lock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //do something
            lock.unlock();
        }
}
