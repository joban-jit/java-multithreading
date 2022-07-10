package com.mulithreading.java.parallelstreams;

import com.mulithreading.java.util.LoggerUtil;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ParallelStreamResultOrder {
	
	public static void main(String[] args) {
		List<Integer> inputList = List.of(1,2,3,4,5,6,7,8,9,10);
		LoggerUtil.log("inputlist: "+inputList);
		List<Integer> outputList = listOrder(inputList);
		LoggerUtil.log("result: "+outputList);
		
		Set<Integer> inputSet = Set.of(1,2,3,4,5,6,7,8,9,10);
		LoggerUtil.log("inputSet: "+inputSet);
		Set<Integer> outputSet = setOrder(inputSet);
		LoggerUtil.log("result: "+outputSet);
	}
	
	public static List<Integer> listOrder(List<Integer> inputList){
		return inputList.parallelStream()
				.map(i->i*2)
				.collect(Collectors.toList());
	}
	
	public static Set<Integer> setOrder(Set<Integer> inputSet){
		return inputSet.parallelStream()
				.map(i->i*2)
				.collect(Collectors.toSet());
	}
}
