package com.mulithreading.java.parallelstreams;

import static com.mulithreading.java.util.CommonUtil.delay;
import static com.mulithreading.java.util.CommonUtil.startTimer;
import static com.mulithreading.java.util.CommonUtil.timeTaken;
import static com.mulithreading.java.util.LoggerUtil.log;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.mulithreading.java.util.DataSet;

public class ParallelStreamExample {

    public static void main(String[] args) {

        List<String> names = DataSet.namesList();
        log("names : "+ names);
        
        ParallelStreamExample parallelStreamExample = new ParallelStreamExample();
        startTimer();
        List<String> resultList = parallelStreamExample.stringTransform(names);
        List<String> lowerCaseList = parallelStreamExample.stringToLoweCase(names, true);
        log("Lowercase result: "+lowerCaseList);

        log("Final Result : "+ resultList);
        timeTaken();
    }

    
    public List<String> stringTransform(List<String> namesList){
    	return namesList
    		.parallelStream()
    		.map(this::addNameLengthTransform)
    		.collect(Collectors.toList());
    	
    }
    
    public List<String> stringTransform_1(List<String> namesList, boolean isParallel){
    	Stream<String> namesStream = namesList.stream();
    	
    	if(isParallel) {
    		namesStream.parallel();
    	}
    	
    	return namesStream
    			.map(this::addNameLengthTransform)
    			.collect(Collectors.toList());
    	
    }
    
    public List<String> stringToLoweCase(List<String> namesList, boolean isParallel){
    	Stream<String> namesStream = namesList.stream();
    	
    	if(isParallel) {
    		namesStream.parallel();
    	}
    	
    	return namesStream
    			.map(String::toLowerCase)
    			.collect(Collectors.toList());
    	
    }

    private String addNameLengthTransform(String name) {
        delay(500);
        return name.length()+" - "+name ;
    }
}
