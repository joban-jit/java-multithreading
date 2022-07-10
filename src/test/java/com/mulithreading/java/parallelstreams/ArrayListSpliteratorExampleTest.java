package com.mulithreading.java.parallelstreams;

import static org.junit.Assert.*;

import org.junit.jupiter.api.RepeatedTest;

import com.mulithreading.java.util.DataSet;

import java.util.ArrayList;
import java.util.List;

public class ArrayListSpliteratorExampleTest {

	ArrayListSpliteratorExample arrayListSpliteratorExample = 
			new ArrayListSpliteratorExample();
	
	@RepeatedTest(5)
	public void testMultiplyEachValue() {
		
		ArrayList<Integer> inputList = DataSet.generateArrayList(1000000);
		List<Integer> muliplyEachValue = 
				arrayListSpliteratorExample.muliplyEachValue(inputList, 2, false);
		assertEquals(1000000, muliplyEachValue.size());
	
	}
	
	@RepeatedTest(5)
	public void testMultiplyEachValue_parallel() {
		ArrayList<Integer> inputList = DataSet.generateArrayList(1000000);
		List<Integer> muliplyEachValue = 
				arrayListSpliteratorExample.muliplyEachValue(inputList, 2, true);
		assertEquals(1000000, muliplyEachValue.size());
	
	}
}
