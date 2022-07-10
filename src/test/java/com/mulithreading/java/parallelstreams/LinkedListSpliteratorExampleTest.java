package com.mulithreading.java.parallelstreams;

import static org.junit.Assert.*;

import org.junit.jupiter.api.RepeatedTest;

import com.mulithreading.java.util.DataSet;

import java.util.LinkedList;
import java.util.List;

public class LinkedListSpliteratorExampleTest {

	LinkedListSpliteratorExample linkedListSpliteratorExample = 
			new LinkedListSpliteratorExample();
	@RepeatedTest(5)
	public void testMultiplyEachValue() {
		
		LinkedList<Integer> inputList = DataSet.generateIntegerLinkedList(1000000);
		List<Integer> muliplyEachValue = 
				linkedListSpliteratorExample.muliplyEachValue(inputList, 2, false);
		assertEquals(1000000, muliplyEachValue.size());
	
	}
	
	@RepeatedTest(5)
	public void testMultiplyEachValue_parallel() {
		LinkedList<Integer> inputList = DataSet.generateIntegerLinkedList(1000000);
		List<Integer> muliplyEachValue = 
				linkedListSpliteratorExample.muliplyEachValue(inputList, 2, true);
		assertEquals(1000000, muliplyEachValue.size());
	
	}
}
