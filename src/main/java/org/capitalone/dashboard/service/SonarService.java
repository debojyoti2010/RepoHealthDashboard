package org.capitalone.dashboard.service;

import org.capitalone.dashboard.model.SonarMetrics;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class SonarService {

    private final RestTemplate restTemplate;
    private final String sonarApiUrl = "https://sonarqube-url/api";
    private final String sonarToken = "YOUR_SONAR_TOKEN"; // Store securely

    public SonarService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public SonarMetrics getSonarMetrics(String projectKey) {
        String url = String.format("%s/measures/component?component=%s&metricKeys=critical_violations,major_violations,coverage,tests", sonarApiUrl, projectKey);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + sonarToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<Map<String, Object>>() {});

        // Extract and map data from response
        Map<String, Object> measures = (Map<String, Object>) response.getBody().get("component");

        int majorViolations = getMeasureValue(measures, "major_violations");
        int criticalViolations = getMeasureValue(measures, "critical_violations");
        double codeCoverage = getMeasureValue(measures, "coverage");
        double testCoverage = getMeasureValue(measures, "tests");

        return new SonarMetrics(majorViolations, criticalViolations, codeCoverage, testCoverage);
    }

    private int getMeasureValue(Map<String, Object> measures, String key) {
        return measures.get(key) != null ? Integer.parseInt(measures.get(key).toString()) : 0;
    }
}