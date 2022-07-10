package com.mulithreading.java.parallelstreams;

import com.mulithreading.java.util.DataSet;
import com.mulithreading.java.util.LoggerUtil;

import java.util.List;
import java.util.stream.Collectors;

public class CollectVsReduce {

	public static void main(String[] args) {
		LoggerUtil.log("collect: "+collect());
		LoggerUtil.log("reduce: "+reduce());

	}
	
	public static String collect() {
		List<String> list = DataSet.namesList();
		String result = list.parallelStream()
							.collect(Collectors.joining());// use String builder , So it is better
		return result;
	}
	
	public static String reduce() {
		List<String> list = DataSet.namesList();
		
		String result = list.parallelStream()
						.reduce("", (s1,s2)-> s1+s2);
		
		return result;
	}

}
