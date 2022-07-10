package com.mulithreading.java.thread;

import static com.mulithreading.java.util.CommonUtil.delay;

public class HelloWorldThreadExample {
    private static String result="";

    private static void hello(){
        delay(700);
        System.out.println("inside hello");
        result = result.concat("Hello");
    }
    private static void world(){
        delay(600);
        System.out.println("inside world");
        result = result.concat("World");
    }

    public static void main(String[] args) throws InterruptedException {

//    	Runnable helloRunnable = new Runnable() {
//			
//			@Override
//			public void run() {
//				hello();
//				
//			}
//		};
//		
//		Runnable worldRunnable = new Runnable() {
//			
//			@Override
//			public void run() {
//				world();
//			}
//		};
//    	
    	Thread helloThread = new Thread(()-> hello());
    	Thread worldThread = new Thread(()-> world());
    	
    	//Starting the thread
    	helloThread.start();
    	worldThread.start();
    	
    	//Joining the thread
    	helloThread.join();// (Waiting for the threads to finish)
    	worldThread.join();
    	
    	System.out.println("Result is : " + result);
    	
    	    
    	
    	
    }
}
