package org.capitalone.dashboard.controller;

import org.capitalone.dashboard.model.SonarMetrics;
import org.capitalone.dashboard.service.AWSPipelineService;
import org.capitalone.dashboard.service.GithubService;
import org.capitalone.dashboard.service.PullRequest;
import org.capitalone.dashboard.service.SonarService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import software.amazon.awssdk.services.codepipeline.model.PipelineExecutionSummary;

import java.util.List;

@Controller
public class DashboardController {

    private final GithubService githubService;
    private final SonarService sonarService;
    private final AWSPipelineService awsPipelineService;

    public DashboardController(GithubService githubService, SonarService sonarService, AWSPipelineService awsPipelineService) {
        this.githubService = githubService;
        this.sonarService = sonarService;
        this.awsPipelineService = awsPipelineService;
    }

    @GetMapping("/dashboard")
    public String getDashboard(@RequestParam("repoName") String repoName, Model model) {
        // Fetch Data from Services
        List<PullRequest> oldPRs = githubService.getOldPullRequests(repoName);
        SonarMetrics sonarMetrics = sonarService.getSonarMetrics(repoName);
        List<PipelineExecutionSummary> builds = awsPipelineService.getLastTenBuilds("YourPipelineName");

        // Add to Model
        model.addAttribute("repoName", repoName);
        model.addAttribute("pullRequests", oldPRs);
        model.addAttribute("sonarMetrics", sonarMetrics);
        model.addAttribute("builds", builds);
        // Add merged branches logic similarly
        return "dashboard";
    }
}