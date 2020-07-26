package ProducerConsumer.demo01;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
 
/**
 * https://blog.csdn.net/qq_33006397/article/details/82657596
 * 生产者消费者模式：使用Object.wait() / notify()方法实现
 */
public class ProducerConsumer {
    private static final int CAPACITY = 5;
//申请一个容量最大的仓库
    public static void main(String args[]){
        Queue<Integer> queue = new LinkedList<Integer>();
 
        Thread producer1 = new Producer("P1", queue, CAPACITY);
        Thread producer2 = new Producer("P2", queue, CAPACITY);
        Thread consumer1 = new Consumer("C1", queue, CAPACITY);
        Thread consumer2 = new Consumer("C2", queue, CAPACITY);
        Thread consumer3 = new Consumer("C3", queue, CAPACITY);
 
        producer1.start();
        producer2.start();
        consumer1.start();
        consumer2.start();
        consumer3.start();
    }
 
    /**
     * 生产者
     */
    public static class Producer extends Thread{
        private Queue<Integer> queue;
       //队列作为仓库
        String name;
        int maxSize;
        int i = 0;
 
        public Producer(String name, Queue<Integer> queue, int maxSize){
            super(name);
            this.name = name;
            this.queue = queue;
            this.maxSize = maxSize;
        }
 
        @Override
        public void run(){
            while(true){
//while(condition）为自旋锁，为防止该线程没有收到notify()调用也从wait()中返回
//（也称作虚假唤醒），这个线程会重新去检查condition条件以决定当前是否可以安全
//地继续执行还是需要重新保持等待，而不是认为线程被唤醒了就可以安全地继续执行
//了,自旋锁当终止条件满足时，才会停止自旋，这里设置了一直执行，直到程序手动停
//止。
                synchronized(queue){
                    //给队列加锁,保证线程安全
                    while(queue.size() == maxSize){
                        //当队列是满的时候，生产者线程等待，由消费者线程进行操作
                        try {
                            System.out .println("Queue is full, Producer[" + name + "] thread waiting for " + "consumer to take something from queue.");
                            queue.wait();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    //队列不为空的时候，生产者被唤醒进行操作
                    System.out.println("[" + name + "] Producing value : +" + i);
                    queue.offer(i++);
                    //因此如果想在一个满的队列中加入一个新项，调用 add() 方法就会抛出一
                   //个 unchecked 异常，而调用 offer() 方法会返回 false
                    queue.notifyAll();
 
                    try {
                        Thread.sleep(new Random().nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
 
        }
    }
 
    /**
     * 消费者
     */
    public static class Consumer extends Thread{
        private Queue<Integer> queue;
        String name;
        int maxSize;
 
        public Consumer(String name, Queue<Integer> queue, int maxSize){
            super(name);
            this.name = name;
            this.queue = queue;
            this.maxSize = maxSize;
        }
 
        @Override
        public void run(){
            while(true){
                synchronized(queue){
                    while(queue.isEmpty()){
                        try {
                            //队列为空，说明没有生产者生产的商品，消费者进行等待
                            System.out.println("Queue is empty, Consumer[" + name + "] thread is waiting for Producer");
                            queue.wait();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    int x = queue.poll();
                    //如果队列元素为空，调用remove() 的行为与 Collection 接口的版本相似会抛出异常，这里是模拟消费者取走商品的过程
                    // 但是新的 poll() 方法在用空集合调用时只是返回 null。因此新的方法更适合容易出现异常条件的情况。
                    System.out.println("[" + name + "] Consuming value : " + x);
                    queue.notifyAll();
                     //唤醒所有队列，消费者和生产者根据队列情况进行操作
 
                    try {
                        Thread.sleep(new Random().nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}