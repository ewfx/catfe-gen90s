import React, { useState } from "react";
import { Box, Button, Typography, CircularProgress, TextField, Tooltip } from "@mui/material";
import axios from "axios";

const BDD = () => {
  const [loadingBDD, setLoadingBDD] = useState(false);
  const [loadingExecute, setLoadingExecute] = useState(false);
  const [loadingReport, setLoadingReport] = useState(false);
  const [context, setContext] = useState("");
  const [bddTestCases, setBddTestCases] = useState("");
  const [executionResult, setExecutionResult] = useState("");

  const formatBDDTestCases = (testCases) => {
    return testCases.split("Scenario:").map((scenario, index) =>
      scenario.trim() ? `\n• Scenario: ${scenario.trim()}` : ""
    ).join("\n");
  };

  const handleGenerateBDD = async () => {
    if (!context.trim()) {
      alert("Please enter the application context for BDD generation.");
      return;
    }
    setLoadingBDD(true);
    try {
      const response = await axios.post("http://localhost:8083/chat/generate", { context });
      setBddTestCases(formatBDDTestCases(response.data.message || "BDD test cases generated successfully."));
    } catch (error) {
      setBddTestCases("Error generating BDD test cases.");
    } finally {
      setLoadingBDD(false);
    }
  };

  const handleExecuteTests = async () => {
    setLoadingExecute(true);
    try {
      const response = await axios.post("http://localhost:8083/chat/execute");
      setExecutionResult(response.data.message || "Tests executed successfully.");
    } catch (error) {
      setExecutionResult("Error executing tests.");
    } finally {
      setLoadingExecute(false);
    }
  };

  const handleGenerateReport = async () => {
    setLoadingReport(true);
    try {
      window.open("/sample_report.html", "_blank");
    } catch (error) {
      alert("Error generating the report.");
    } finally {
      setLoadingReport(false);
    }
  };

  return (
    <Box sx={{ textAlign: 'center', mt: 4, display: 'flex', flexDirection: 'column', alignItems: 'center', width: '100%' }}>
      <Typography variant="h5" sx={{ fontWeight: 'bold', color: '#1976d2' }}>
        BDD Testing & Execution
      </Typography>
      <Box sx={{ display: 'flex', alignItems: 'center', justifyContent: 'center', width: '60%', mt: 2 }}>
        <Tooltip title="Enter the application context for BDD generation:">
          <TextField
            fullWidth
            multiline
            rows={2}
            placeholder="Enter application context..."
            variant="outlined"
            value={context}
            onChange={(e) => setContext(e.target.value)}
            sx={{ mr: 2 }}
          />
        </Tooltip>
        <Button
          variant="contained"
          color="info"
          onClick={handleGenerateBDD}
          disabled={loadingBDD}
          sx={{ whiteSpace: 'nowrap' }}
        >
          {loadingBDD ? <CircularProgress size={24} color="inherit" /> : "Generate BDD"}
        </Button>
      </Box>
      <Box sx={{ mt: 2, textAlign: 'left', p: 2, border: '1px solid #ddd', borderRadius: 2, width: '60%', minHeight: '80px', maxHeight: '150px', overflowY: 'auto', backgroundColor: '#f9f9f9' }}>
        <Typography variant="h7" sx={{ fontWeight: 'bold', color: '#333' }}>BDD Test Cases:</Typography>
        <Typography sx={{ whiteSpace: 'pre-wrap', color: '#555' }}>{bddTestCases}</Typography>
      </Box>
      <Box sx={{ display: 'flex', justifyContent: 'flex-end', width: '60%', mt: 3 }}>
        <Button
          variant="contained"
          color="warning"
          onClick={handleExecuteTests}
          sx={{ m: 1 }}
          disabled={loadingExecute}
        >
          {loadingExecute ? <CircularProgress size={24} color="inherit" /> : "Execute Tests"}
        </Button>
        
      </Box>
      <Box sx={{ mt: 2, textAlign: 'left', p: 2, border: '1px solid #ddd', borderRadius: 2, width: '60%', backgroundColor: '#f9f9f9' }}>
        <Typography variant="h7" sx={{ fontWeight: 'bold', color: '#333' }}>Execution Results: {executionResult}</Typography>
      </Box>
	   <Box sx={{ display: 'flex', justifyContent: 'flex-end', width: '60%', mt: 3 }}>
	  <Button
          variant="contained"
          color="success"
          onClick={handleGenerateReport}
          sx={{ m: 1 }}
          disabled={loadingReport}
        >
          {loadingReport ? <CircularProgress size={24} color="inherit" /> : "Generate Report"}
        </Button>
		</Box>
    </Box>
  );
};

export default BDD;