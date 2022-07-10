package com.mulithreading.java.apiclient;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.mulithreading.java.domain.checkout.GitHubPosition;
import com.mulithreading.java.util.CommonUtil;
import com.mulithreading.java.util.LoggerUtil;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class GitHubJobsClient {

	private WebClient webClient;

	public GitHubJobsClient(WebClient webClient) {
		super();
		this.webClient = webClient;
	}
	
	public List<GitHubPosition> invokeGitHubJobsAPI_withPageNumber(int pageNum, String description){
		//building 
//		https://jobs.github.com/positions.json?description=ruby&page=1
		
		String uri = UriComponentsBuilder.fromUriString("/positions.json")
							.queryParam("description", description)
							.queryParam("page", pageNum)
							.buildAndExpand()
							.toUriString();
		LoggerUtil.log("URI: "+uri);
		List<GitHubPosition> gitHubPositions = webClient.get()
			.uri(uri)
			.retrieve()
			.bodyToFlux(GitHubPosition.class)
			.collect(Collectors.toList())
			.block();
			
		
		return gitHubPositions;
		
	}
	
	public List<GitHubPosition> invokeGitHubJobsUsingMuliplePageNumbers(List<Integer> pageNumbers, String description){
		
		CommonUtil.startTimer();
		List<GitHubPosition> gitHubPositions = pageNumbers
		.stream()
//		.parallelStream()
		.map(pageNumber-> invokeGitHubJobsAPI_withPageNumber(pageNumber, description))
		.flatMap(Collection::stream)
//		.flatMap(gitHubPosition-> gitHubPosition.stream())
		.collect(Collectors.toList());
		
		CommonUtil.timeTaken();
		return gitHubPositions;
		
	}
	
	
	public List<GitHubPosition> invokeGitHubJobsUsingMuliplePageNumbers_cf(List<Integer> pageNumbers, String description){
		
		CommonUtil.startTimer();
		List<CompletableFuture<List<GitHubPosition>>> gitHubPositionsCfList = pageNumbers
				.stream()
				.map(pageNumber-> CompletableFuture.supplyAsync(()->invokeGitHubJobsAPI_withPageNumber(pageNumber, description)))
				.collect(Collectors.toList());
		List<GitHubPosition> gitHubPositions = gitHubPositionsCfList.stream()
				.map(CompletableFuture::join)
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
		
		CommonUtil.timeTaken();
		return gitHubPositions;
		
	}
	
	public List<GitHubPosition> invokeGitHubJobsUsingMuliplePageNumbers_cf_approach2(List<Integer> pageNumbers, String description){
		
		CommonUtil.startTimer();
		List<CompletableFuture<List<GitHubPosition>>> gitHubPositionsCfList = pageNumbers
				.stream()
				.map(pageNumber-> CompletableFuture.supplyAsync(()->invokeGitHubJobsAPI_withPageNumber(pageNumber, description)))
				.collect(Collectors.toList());
		
		CompletableFuture<Void> cfAllOf = CompletableFuture.allOf(gitHubPositionsCfList.toArray(new CompletableFuture[gitHubPositionsCfList.size()]));
		
		List<GitHubPosition> gitHubPositions =	cfAllOf.thenApply(v-> gitHubPositionsCfList.stream()
				.map(CompletableFuture::join)
				.flatMap(Collection::stream)
				.collect(Collectors.toList()))
				.join();
		
		CommonUtil.timeTaken();
		return gitHubPositions;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
