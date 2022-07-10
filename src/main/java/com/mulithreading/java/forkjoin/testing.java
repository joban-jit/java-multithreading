package com.mulithreading.java.forkjoin;

public class testing {

	public static void main(String[] args) {
		System.out.println(factorial(4));

	}
	
	public static int factorial(int number) {
		if(number==1) {
			return 1;
		}else {
			return number*factorial(number-1);
		}
		
		
	}

}
