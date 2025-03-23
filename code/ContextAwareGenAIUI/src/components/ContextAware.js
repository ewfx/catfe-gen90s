import React, { useState } from 'react';
import axios from 'axios';
import { Button, TextField, Typography, Paper, Box } from '@mui/material';

const ContextAware = () => {
  const [appDescription, setAppDescription] = useState('');
  const [testCases, setTestCases] = useState('');

  const generateTestCases = async () => {
    try {
      const response = await axios.post('http://localhost:8083/api/generate-test-cases', { description: appDescription });
      setTestCases(response.data.testCases);
    } catch (error) {
      alert('Error generating test cases');
    }
  };

  return (
    <Box sx={{ display: 'flex', justifyContent: 'center', mt: 5 }}>
      <Paper elevation={3} sx={{ padding: 3, maxWidth: 500, textAlign: 'center' }}>
        <Typography variant="h5" gutterBottom>
          Context-Aware Testing
        </Typography>
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
          disabled={!appDescription}
        >
          Generate Test Cases
        </Button>
        <TextField
          fullWidth
          multiline
          rows={6}
          variant="outlined"
          value={testCases}
          sx={{ mt: 2 }}
          InputProps={{ readOnly: true }}
        />
      </Paper>
    </Box>
  );
};

export default ContextAware;