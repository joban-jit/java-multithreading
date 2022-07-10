package com.mulithreading.java.parallelstreams;

import com.mulithreading.java.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArrayListSpliteratorExample {

	public List<Integer> muliplyEachValue(ArrayList<Integer> inputList, int mutiple, boolean isParallel) {
		
		CommonUtil.startTimer();
		Stream<Integer> integerStream = inputList.stream();
		if(isParallel) {
			integerStream.parallel();
		}
		List<Integer> collect = integerStream.map(i->i*mutiple)
		.collect(Collectors.toList());
		CommonUtil.timeTaken();
		return collect;
	}
}
