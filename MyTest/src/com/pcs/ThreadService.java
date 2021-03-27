package com.pcs;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 两线程交替打印 核心是对共享资源加锁
 */
public class ThreadService {
    private final static Object objA = new Object();//线程A的锁
    private final static Object objB = new Object();//线程B的锁

    class ThreadA extends Thread{
        public void run(){
            for (int i = 0; i < 10; i++) {
                synchronized (objA) {//对objA加锁
                    System.out.print("1");
                    synchronized (objB) {
                        objB.notify();//唤醒线程B，得放在阻塞线程A之前，不然线程A阻塞后，不会执行后面逻辑
                    }
                    try {
                        objA.wait();//线程A阻塞
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

    class ThreadB extends Thread{
        public void run(){
            for (int i = 0; i < 10; i++) {
                synchronized (objB) {//对objB加锁
                    System.out.print("2");
                    synchronized (objA) {
                        objA.notify();//唤醒线程A，得放在阻塞线程B之前，不然线程B阻塞后，不会执行后面逻辑
                    }
                    try {
                        objB.wait();//线程B阻塞
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

    /**
     * 这种两线程分别获取不同对象的锁，并没有资源的共享竞争，是伪并发
     * 交替打印121212
     */
    public void ThreadTest(){
//        Thread threadA = new Thread(new ThreadA());
//        Thread threadB = new Thread(new ThreadB());
        ThreadA threadA = new ThreadA();
        ThreadB threadB = new ThreadB();
        threadA.start();
        try {
            threadB.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadB.start();
    }

    private volatile static Boolean flag = true;
    private final static Object lock = new Object();

    class ThreadC implements Runnable{
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                synchronized (lock){
                    if(flag){
                        System.out.print("a");
                        flag = false;
                        try {
                            lock.notify();//唤醒其他线程
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else{
                        try {
                            //wait阻塞是会释放锁的，为什么输出的数字总数会小于20个的情况呢？
                            //原因是：线程1释放锁后，又抢到cpu资源了，flag为false,又wait了，直到线程2将flag改为true后，线程1才会打印。
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    class ThreadD implements Runnable{
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                synchronized (lock){
                    if(!flag){
                        System.out.print("b");
                        flag = true;
                        try {
                            lock.notify();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else{
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
     * synchronized + volatile 实现两线程交替打印abab
     */
    public void ThreadTestCD(){
        Thread thread1 = new Thread(new ThreadC());
        Thread thread2 = new Thread(new ThreadD());
        thread1.start();
        thread2.start();
    }

    private final AtomicInteger count = new AtomicInteger(0);
    class Thread1 implements Runnable{
        @Override
        public void run() {
            synchronized (count){
                while (count.get() <= 100){
                    if(count.get() % 2 == 0){
                        //是偶数
                        System.out.println("偶数:"+ (count.getAndIncrement()));
                        count.notify();
                    }else{
                        try {
                            count.wait();//不是偶数的话阻塞，让奇数线程打印
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    class Thread2 implements Runnable{
        @Override
        public void run() {
            synchronized (count){
                while (count.get() <= 100){
                    if(count.get() % 2 == 0){
                        try {
                            count.wait();//不是偶数的话阻塞，让奇数线程打印
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else{
                        //是奇数
                        System.out.println("奇数:"+ (count.getAndIncrement()));
                        count.notify();
                    }
                }
            }
        }
    }

    /**
     *
     * synchronized + AtomicInteger 实现两线程交替打印 100以内 奇偶数交替打印
     */
    public void ThreadTest12(){
        Thread thread1 = new Thread(new Thread1());
        Thread thread2 = new Thread(new Thread2());
        thread1.start();
        thread2.start();
    }
}
