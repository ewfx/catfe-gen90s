package com.wf.contextaware.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Service to execute and manage BDD test cases generated from fraud analysis.
 */
@Service
public class TestExecutorService {

    private static final Logger logger = LoggerFactory.getLogger(TestExecutorService.class);

    /**
     * Executes the BDD test cases provided in the input string.
     * For simulation, it logs the test execution results.
     * In a real implementation, this could integrate with Cucumber or another BDD framework.
     *
     * @param fraudAnalysisAndTests The string containing fraud analysis and BDD test cases.
     * @return A summary of test execution results.
     */
    public String executeBddTests(String fraudAnalysisAndTests) {
        if (fraudAnalysisAndTests == null || fraudAnalysisAndTests.trim().isEmpty()) {
            return "No test cases provided for execution.";
        }

        List<String> testScenarios = extractScenarios(fraudAnalysisAndTests);
        StringBuilder executionSummary = new StringBuilder("Test Execution Summary:\n");

        int passed = 0;
        int failed = 0;

        for (String scenario : testScenarios) {
            String scenarioName = extractScenarioName(scenario);
            boolean testPassed = simulateTestExecution(scenario);

            if (testPassed) {
                passed++;
                logger.info("Test Passed: {}", scenarioName);
                executionSummary.append(String.format("PASSED: %s\n", scenarioName));
            } else {
                failed++;
                logger.error("Test Failed: {}", scenarioName);
                executionSummary.append(String.format("FAILED: %s\n", scenarioName));
            }
        }

        executionSummary.append(String.format("\nTotal Tests: %d, Passed: %d, Failed: %d", 
            passed + failed, passed, failed));
        return executionSummary.toString();
    }

    /**
     * Extracts individual BDD scenarios from the combined fraud analysis and test string.
     *
     * @param fraudAnalysisAndTests The full output from GenAIService.
     * @return List of scenario strings.
     */
    private List<String> extractScenarios(String fraudAnalysisAndTests) {
        List<String> scenarios = new ArrayList<>();
        String[] parts = fraudAnalysisAndTests.split("(?=Scenario:)");
        
        for (String part : parts) {
            if (part.trim().startsWith("Scenario:")) {
                scenarios.add(part.trim());
            }
        }
        return scenarios;
    }

    /**
     * Extracts the scenario name from a BDD scenario block.
     *
     * @param scenario The scenario text.
     * @return The scenario name (e.g., "Detect Fraudulent Transaction - ID TX000075").
     */
    private String extractScenarioName(String scenario) {
        Pattern pattern = Pattern.compile("Scenario: (.+)");
        Matcher matcher = pattern.matcher(scenario);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return "Unnamed Scenario";
    }

    /**
     * Simulates test execution by checking if the scenario's conditions are met.
     * In a real system, this would call a BDD framework (e.g., Cucumber) to run the test.
     *
     * @param scenario The BDD scenario text.
     * @return True if the test "passes," false otherwise (simulated logic).
     */
    private boolean simulateTestExecution(String scenario) {
        // Simulated logic: "Pass" if the scenario contains expected keywords
        // In reality, this would parse Given/When/Then and validate against actual system behavior
        boolean hasGiven = scenario.contains("Given");
        boolean hasWhen = scenario.contains("When");
        boolean hasThen = scenario.contains("Then");
        
        // Simulate a passing test if all BDD steps are present
        boolean testPassed = hasGiven && hasWhen && hasThen;
        
        if (!testPassed) {
            logger.warn("Simulation failed for scenario due to missing steps: {}", scenario);
        }
        return testPassed;
    }
}
