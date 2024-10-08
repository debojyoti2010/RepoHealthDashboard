package org.capitalone.dashboard.model;


public class SonarMetrics {

    private int majorViolations;
    private int criticalViolations;
    private double codeCoverage;
    private double testCoverage;

    // Constructors
    public SonarMetrics() {
    }

    public SonarMetrics(int majorViolations, int criticalViolations, double codeCoverage, double testCoverage) {
        this.majorViolations = majorViolations;
        this.criticalViolations = criticalViolations;
        this.codeCoverage = codeCoverage;
        this.testCoverage = testCoverage;
    }

    // Getters and Setters
    public int getMajorViolations() {
        return majorViolations;
    }

    public void setMajorViolations(int majorViolations) {
        this.majorViolations = majorViolations;
    }

    public int getCriticalViolations() {
        return criticalViolations;
    }

    public void setCriticalViolations(int criticalViolations) {
        this.criticalViolations = criticalViolations;
    }

    public double getCodeCoverage() {
        return codeCoverage;
    }

    public void setCodeCoverage(double codeCoverage) {
        this.codeCoverage = codeCoverage;
    }

    public double getTestCoverage() {
        return testCoverage;
    }

    public void setTestCoverage(double testCoverage) {
        this.testCoverage = testCoverage;
    }
}