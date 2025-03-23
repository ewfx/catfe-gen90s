import React, { useState } from 'react';
import axios from 'axios';
import { Button, TextField, Typography, Paper, Box } from '@mui/material';

const ReadAnyApp = () => {
  const [url, setUrl] = useState('');
  const [testCases, setTestCases] = useState('');

  const generateTestCases = async () => {
    try {
      const response = await axios.post('http://localhost:5000/api/read-any-app', { url });
      setTestCases(response.data.testCases);
    } catch (error) {
      alert('Error generating test cases');
    }
  };

  return (
    <Box sx={{ display: 'flex', justifyContent: 'center', mt: 5 }}>
      <Paper elevation={3} sx={{ padding: 3, maxWidth: 500, textAlign: 'center' }}>
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
        <Button
          variant="contained"
          color="primary"
          onClick={generateTestCases}
          disabled={!url}
        >
          Generate & Run Test Cases
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

export default ReadAnyApp;
