package org.capitalone.dashboard.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class GithubService {

    private final RestTemplate restTemplate;

    @Value("${github.token}")
    private String githubToken;

    public GithubService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<PullRequest> getOldPullRequests(String owner, String repo) {
        String url = String.format("https://api.github.com/repos/%s/%s/pulls?state=open", owner, repo);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + githubToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<PullRequest[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, PullRequest[].class);
            return Arrays.asList(response.getBody());
        } catch (HttpClientErrorException.NotFound e) {
            System.out.println("Repository not found: " + e.getMessage());
            return Collections.emptyList(); // Gracefully return an empty list
        } catch (HttpClientErrorException e) {
            System.out.println("Error fetching PRs: " + e.getMessage());
            throw e; // Rethrow or handle based on your requirements
        }
    }
}