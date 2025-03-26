package com.redhat.developers.service;
import org.springframework.stereotype.Service;
import org.zaproxy.clientapi.core.Alert;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;

import com.redhat.developers.model.Vulnerability;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EthicalHackService {

    private static final String ZAP_HOST = "localhost";
    private static final int ZAP_PORT = 8080; // Default ZAP proxy port
    private static final String ZAP_API_KEY = "t0lgpn0i6pe24auqo24s4fkenj"; // Replace with your ZAP API key

    public String simulateEthicalHack(String url) {
        ClientApi zapClient = new ClientApi(ZAP_HOST, ZAP_PORT, ZAP_API_KEY);
        List<Vulnerability> vulnerabilities = new ArrayList<>();

        try {
            // Spider the target URL
            zapClient.spider.scan(url, null, null, null, null);
            Thread.sleep(5000); // Wait for spidering (adjust as needed)

            // Perform active scan
            zapClient.ascan.scan(url, "True", "False", null, null, null);
            Thread.sleep(10000); // Wait for active scan (adjust as needed)

            // Retrieve alerts
            List<Alert> alerts = zapClient.getAlerts(url, 0, Integer.MAX_VALUE);
            for (Alert alert : alerts) {
                vulnerabilities.add(new Vulnerability(
                    alert.getName(),
                    alert.getRisk().name(),
                    alert.getDescription() + " (URL: " +url + ")"
                ));
            }
        } catch (ClientApiException | InterruptedException e) {
            e.printStackTrace();
            vulnerabilities.add(new Vulnerability("Error", "High", "Failed to scan: " + e.getMessage()));
        }

        // Generate HTML report
        StringBuilder report = new StringBuilder("<html><body><h1>Ethical Hack Report</h1><ul>");
        for (Vulnerability vuln : vulnerabilities) {
            report.append(String.format("<li>%s - %s: %s</li>", vuln.getType(), vuln.getSeverity(), vuln.getDetails()));
        }
        report.append("</ul></body></html>");

        try {
            File reportFile = new File("src/main/resources/static/reports/hack_report.html");
            reportFile.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(reportFile);
            writer.write(report.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "/reports/hack_report.html";
    }
    
    public String generateEthicalHackReport(String url) {
        ClientApi zapClient = new ClientApi(ZAP_HOST, ZAP_PORT, ZAP_API_KEY);
        List<Vulnerability> vulnerabilities = new ArrayList<>();

        try {
            zapClient.spider.scan(url, null, null, null, null);
            Thread.sleep(5000); // Wait for spidering
            zapClient.ascan.scan(url, "True", "False", null, null, null);
            Thread.sleep(10000); // Wait for active scan

            List<Alert> alerts = zapClient.getAlerts(url, 0, Integer.MAX_VALUE);
            for (Alert alert : alerts) {
                vulnerabilities.add(new Vulnerability(
                    alert.getName(),
                    alert.getRisk().name(),
                    alert.getDescription() + " (URL: " + url + ")"
                ));
            }
        } catch (ClientApiException | InterruptedException e) {
            e.printStackTrace();
            vulnerabilities.add(new Vulnerability("Error", "High", "Failed to scan: " + e.getMessage()));
        }

        // Generate HTML report content
        StringBuilder report = new StringBuilder("<html><body><h1>Ethical Hack Report</h1><ul>");
        for (Vulnerability vuln : vulnerabilities) {
            report.append(String.format("<li>%s - %s: %s</li>", vuln.getType(), vuln.getSeverity(), vuln.getDetails()));
        }
        report.append("</ul></body></html>");

        return report.toString();
    }
}
