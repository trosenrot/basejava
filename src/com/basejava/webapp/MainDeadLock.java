package com.basejava.webapp;

public class MainDeadLock {
    final static Object object1 = new Object();
    final static Object object2 = new Object();

    public static void main(String[] args) {
        deadLock(object1, object2);
        deadLock(object2, object1);
    }

    private static void deadLock(Object object1, Object object2) {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " пытается захватить " + getNameObject(object1));
            synchronized (object1) {
                System.out.println(Thread.currentThread().getName() + " захватил " + getNameObject(object1));
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + " прерван");
                }
                System.out.println(Thread.currentThread().getName() + " пытается захватить " + getNameObject(object2));
                synchronized (object2) {
                    System.out.println(Thread.currentThread().getName() + " захватил " + getNameObject(object2));
                }
            }
        }).start();
    }

    private static String getNameObject (Object object) {
        if (object.equals(object1)){
            return "object1";
        }
        return "object2";
    }
}
