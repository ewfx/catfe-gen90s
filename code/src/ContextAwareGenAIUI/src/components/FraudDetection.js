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
    } catch (err) {
      setError('Error uploading file: ' + (err.response?.data?.error || err.message));
    } finally {
      setLoading(false);
    }
  };

  return (
    <Box sx={{ display: 'flex', justifyContent: 'center', mt: 5 }}>
      <Paper elevation={3} sx={{ padding: 3, maxWidth: 500, textAlign: 'center' }}>
        <Typography variant="h5" gutterBottom>
          Upload Transaction Data
        </Typography>
        <TextField
          type="file"
          fullWidth
          variant="outlined"
          inputProps={{ accept: '.csv' }}
          onChange={handleFileChange}
          sx={{ mb: 2 }}
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
          <Box sx={{ marginTop: 3, textAlign: 'left' }}>
            <Typography variant="h6">Fraud Analysis and Test Cases</Typography>
            <Paper sx={{ padding: 2, backgroundColor: '#f5f5f5', borderRadius: '4px' }}>
              <Typography variant="body2">{result.fraud_analysis_and_tests}</Typography>
            </Paper>

            <Typography variant="h6" sx={{ marginTop: 2 }}>Test Execution Summary</Typography>
            <Paper sx={{ padding: 2, backgroundColor: '#f5f5f5', borderRadius: '4px' }}>
              <Typography variant="body2">{result.execution_summary}</Typography>
            </Paper>
          </Box>
        )}
      </Paper>
    </Box>
  );
};

export default FraudDetection;
