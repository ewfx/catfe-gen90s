import React, { useState } from 'react';
import axios from 'axios';
import { Button, TextField, Typography, Paper, CircularProgress, Box } from '@mui/material';

const FraudDetection = () => {
  const [file, setFile] = useState(null);
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleFileChange = (event) => {
    setFile(event.target.files[0]);
    setResult(null);
    setError(null);
  };

  const handleUpload = async () => {
    if (!file) {
      setError('Please select a CSV file to upload.');
      return;
    }

    setLoading(true);
    setError(null);

    const formData = new FormData();
    formData.append('file', file);

    try {
      const response = await axios.post('http://localhost:8083/api/analyze_payment_fraud_data', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });

      setResult(response.data);
	  console.log("data --- > "+response.data);
    } catch (err) {
      setError('Error uploading file: ' + (err.response?.data?.error || err.message));
    } finally {
      setLoading(false);
    }
  };

  return (
    <Box sx={{ padding: 3 }}>
      <Paper elevation={3} sx={{ padding: 3 }}>
        <Typography variant="h5" gutterBottom>
          Upload Transaction Data
        </Typography>
        <input
          type="file"
          accept=".csv"
          onChange={handleFileChange}
          style={{ marginBottom: '20px' }}
        />
        <Button
          variant="contained"
          color="primary"
          onClick={handleUpload}
          disabled={loading || !file}
        >
          {loading ? <CircularProgress size={24} /> : 'Analyze Fraud'}
        </Button>

        {error && (
          <Typography color="error" sx={{ marginTop: 2 }}>
            {error}
          </Typography>
        )}

        {result && (
          <Box sx={{ marginTop: 3 }}>
            <Typography variant="h6">Fraud Analysis and Test Cases</Typography>
            <pre style={{ whiteSpace: 'pre-wrap', backgroundColor: '#f5f5f5', padding: '10px', borderRadius: '4px' }}>
              {result.fraud_analysis_and_tests}
            </pre>

            <Typography variant="h6" sx={{ marginTop: 2 }}>Test Execution Summary</Typography>
            <pre style={{ whiteSpace: 'pre-wrap', backgroundColor: '#f5f5f5', padding: '10px', borderRadius: '4px' }}>
              {result.execution_summary}
            </pre>
          </Box>
        )}
      </Paper>
    </Box>
  );
};

export default FraudDetection;