import React, { useState } from 'react';
import axios from 'axios';
import { Button, TextField, Typography, Paper, Box, CircularProgress } from '@mui/material';

const ReadAnyApp = () => {
  const [url, setUrl] = useState('');
  const [testCases, setTestCases] = useState('');
  const [appDescription, setAppDescription] = useState('');
  const [loading, setLoading] = useState(false);

  const generateTestCases = async () => {
    setLoading(true);
    try {
      const response = await axios.post('http://localhost:8083/api/generate-bdd-test-cases-by-url', {
        url: url,
        description: appDescription
      });
      setTestCases(response.data.gen_tests_by_url);
    } catch (error) {
      setTestCases('Error generating test cases');
    } finally {
      setLoading(false);
    }
  };

  return (
    <Box sx={{ display: 'flex', justifyContent: 'center', mt: 5 }}>
      <Paper elevation={3} sx={{ padding: 3, maxWidth: 800, textAlign: 'center' }}>
        <Typography variant="h5" gutterBottom>
          Read Any App
        </Typography>
        <TextField
          fullWidth
          label="Enter Application URL"
          variant="outlined"
          value={url}
          onChange={(e) => setUrl(e.target.value)}
          sx={{ mb: 2 }}
        />
        <TextField
          fullWidth
          multiline
          rows={4}
          label="Describe your application"
          variant="outlined"
          value={appDescription}
          onChange={(e) => setAppDescription(e.target.value)}
          sx={{ mb: 2 }}
        />
        <Button
          variant="contained"
          color="primary"
          onClick={generateTestCases}
          disabled={!url || loading}
        >
          {loading ? <CircularProgress size={24} /> : 'Generate Test Cases'}
        </Button>
        <TextField
          fullWidth
          multiline
          rows={12}
          variant="outlined"
          value={testCases}
          sx={{ mt: 2, whiteSpace: 'pre-wrap', fontFamily: 'monospace' }}
          InputProps={{ readOnly: true }}
        />
      </Paper>
    </Box>
  );
};

export default ReadAnyApp;