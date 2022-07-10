package com.mulithreading.java.apiclient;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import com.mulithreading.java.domain.checkout.GitHubPosition;
import com.mulithreading.java.util.LoggerUtil;

import java.util.List;

public class GitHubJobsClientTest {
	
	private WebClient webClient  = WebClient.create("https://jobs.github.com/");
	private GitHubJobsClient gitHubJobsClient = new GitHubJobsClient(webClient);

//	@Test
	void test_invokeGitHubJobsAPI_withPageNumber() {
		
		int pageNum = 1;
		String description = "JavaScript";
		List<GitHubPosition> gitHubPositions = gitHubJobsClient.invokeGitHubJobsAPI_withPageNumber(pageNum, description);
		LoggerUtil.log("GitHubPositions : "+gitHubPositions);
		assertTrue(gitHubPositions.size()>0);
		gitHubPositions.stream().forEach(Assertions::assertNotNull);
			
	}
	
	
//	@Test
	void test_invokeGitHubJobsUsingMuliplePageNumbers() {
		
		List<Integer> pageNumList = List.of(1,2,3);
		String description = "JavaScript";
		List<GitHubPosition> gitHubPositions = gitHubJobsClient.invokeGitHubJobsUsingMuliplePageNumbers(pageNumList, description);
//		LoggerUtil.log("GitHubPositions : "+gitHubPositions);
		assertTrue(gitHubPositions.size()>0);
		gitHubPositions.stream().forEach(Assertions::assertNotNull);
		
	}
//	@Test
	void test_invokeGitHubJobsUsingMuliplePageNumbers_cf() {
		
		List<Integer> pageNumList = List.of(1,2,3);
		String description = "JavaScript";
		List<GitHubPosition> gitHubPositions = gitHubJobsClient.invokeGitHubJobsUsingMuliplePageNumbers_cf(pageNumList, description);
//		LoggerUtil.log("GitHubPositions : "+gitHubPositions);
		assertTrue(gitHubPositions.size()>0);
		gitHubPositions.stream().forEach(Assertions::assertNotNull);
		
	}
	@Test
	void test_invokeGitHubJobsUsingMuliplePageNumbers_cf_approach2() {
		
		List<Integer> pageNumList = List.of(1,2,3);
		String description = "ruby";
		List<GitHubPosition> gitHubPositions = gitHubJobsClient.invokeGitHubJobsUsingMuliplePageNumbers_cf_approach2(pageNumList, description);
//		LoggerUtil.log("GitHubPositions : "+gitHubPositions);
		assertTrue(gitHubPositions.size()>0);
		gitHubPositions.stream().forEach(Assertions::assertNotNull);
		
	}
}
