package com.mulithreading.java.parallelstreams;

import com.mulithreading.java.util.CommonUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LinkedListSpliteratorExample {

public List<Integer> muliplyEachValue(LinkedList<Integer> inputList, int mutiple, boolean isParallel) {
		
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
