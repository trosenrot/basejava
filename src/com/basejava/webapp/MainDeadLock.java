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
            String nameObject1 = getNameObject(object1);
            String nameObject2 = getNameObject(object2);
            System.out.println(Thread.currentThread().getName() + " пытается захватить " + nameObject1);
            synchronized (object1) {
                System.out.println(Thread.currentThread().getName() + " захватил " + nameObject1);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + " прерван");
                }
                System.out.println(Thread.currentThread().getName() + " пытается захватить " + nameObject2);
                synchronized (object2) {
                    System.out.println(Thread.currentThread().getName() + " захватил " + nameObject2);
                }
            }
        }).start();
    }

    private static String getNameObject(Object object) {
        return object.equals(object1) ? "object1" : "object2";
    }
}
