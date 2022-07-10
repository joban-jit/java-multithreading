package com.mulithreading.java.parallelstreams;

import java.util.List;

public class ReduceExample {

	
	public int reduce_sum_parallelStream(List<Integer> inputList) {
		return inputList.parallelStream()
						.reduce(0, Integer::sum);
	}
	public int reduce_mulitply_parallelStream(List<Integer> inputList) {
		return inputList.parallelStream()
						.reduce(1,(x,y)->x*y );
	}

}
