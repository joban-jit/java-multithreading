package com.mulithreading.java.parallelstreams;

import static com.mulithreading.java.util.CommonUtil.startTimer;
import static com.mulithreading.java.util.CommonUtil.stopWatchReset;
import static com.mulithreading.java.util.CommonUtil.timeTaken;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.mulithreading.java.util.DataSet;

public class ParallelStreamExampleTest {

	ParallelStreamExample parallelSteamExample = new ParallelStreamExample();
	
	@Test
	void stringTransform() {
		List<String> inputList = DataSet.namesList();
		
		startTimer();
		List<String> resultList = parallelSteamExample.stringTransform(inputList);
		timeTaken();
		stopWatchReset();
		assertEquals(4, resultList.size());
		resultList.forEach(name->{
			assertTrue(name.contains("-"));
		});
	}
	
	@ParameterizedTest
	@ValueSource(booleans = {false, true})
	void stringTransform_1(boolean isParallel) {
		List<String> inputList = DataSet.namesList();
		
		startTimer();
		List<String> resultList = parallelSteamExample.stringTransform_1(inputList, isParallel);
		timeTaken();
		stopWatchReset();
		assertEquals(4, resultList.size());
		resultList.forEach(name->{
			assertTrue(name.contains("-"));
		});
	}
}
