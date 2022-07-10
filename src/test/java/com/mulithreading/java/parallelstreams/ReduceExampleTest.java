package com.mulithreading.java.parallelstreams;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ReduceExampleTest {
	
	private ReduceExample reduceExample = new ReduceExample();
	
	List<Integer> inputList = List.of(1,2,3,4,5,6,7,8);
	
	
	@Test
	void test_reduce_sum_parallelStream() {
		int result = reduceExample.reduce_sum_parallelStream(inputList);
		assertEquals(36, result);
	}
	
	@Test
	void test_reduce_sum_parallelStream_emptyList() {
		int result = reduceExample.reduce_sum_parallelStream(new ArrayList<Integer>());
		assertEquals(0, result);
	}
	
	@Test
	void test_reduce_multiply_parallelStream_emptyList() {
		int result = reduceExample.reduce_mulitply_parallelStream(new ArrayList<Integer>());
		assertEquals(1, result);
	}
	
	@Test
	void test_reduce_multiply_parallelStream() {
		int result = reduceExample.reduce_mulitply_parallelStream(inputList);
		assertEquals(40320, result);
	}
}
