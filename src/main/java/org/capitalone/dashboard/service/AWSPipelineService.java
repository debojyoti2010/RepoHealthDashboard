package org.capitalone.dashboard.service;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.codepipeline.CodePipelineClient;
import software.amazon.awssdk.services.codepipeline.model.ListPipelineExecutionsRequest;
import software.amazon.awssdk.services.codepipeline.model.ListPipelineExecutionsResponse;
import software.amazon.awssdk.services.codepipeline.model.PipelineExecutionSummary;

import java.util.List;

@Service
public class AWSPipelineService {

    private final CodePipelineClient codePipelineClient;

    public AWSPipelineService() {
        // Initialize AWS CodePipeline client with region configuration
        this.codePipelineClient = CodePipelineClient.builder()
                .region(Region.US_EAST_1) // Set your region accordingly
                .build();
    }

    public List<PipelineExecutionSummary> getLastTenBuilds(String pipelineName) {
        ListPipelineExecutionsRequest request = ListPipelineExecutionsRequest.builder()
                .pipelineName(pipelineName)
                .maxResults(10)
                .build();

        ListPipelineExecutionsResponse response = codePipelineClient.listPipelineExecutions(request);

        return response.pipelineExecutionSummaries();
    }
}