import React, { useState } from 'react';
import axios from 'axios';
import { Button, TextField, Typography, Paper, Box, CircularProgress } from '@mui/material';

const ContextAware = () => {
  const [appDescription, setAppDescription] = useState('');
  const [message, setMessage] = useState('');
  const [loading, setLoading] = useState(false);

  const generateTestCases = async () => {
    setLoading(true);
    try {
      const response = await axios.post('http://localhost:8083/api/generate-test-cases', { description: appDescription });
      setMessage(response.data.testCases);
    } catch (error) {
      setMessage('Error generating test cases');
    } finally {
      setLoading(false);
    }
  };

  return (
    <Box sx={{ display: 'flex', justifyContent: 'center', mt: 5 }}>
      <Paper elevation={3} sx={{ padding: 3, maxWidth: 800, textAlign: 'center' }}>
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
          disabled={!appDescription || loading}
        >
          {loading ? <CircularProgress size={24} /> : 'Generate Test Cases'}
        </Button>
        <TextField
          fullWidth
          multiline
          rows={12}
          variant="outlined"
          value={message}
          sx={{ mt: 2, whiteSpace: 'pre-wrap', fontFamily: 'monospace' }}
          InputProps={{ readOnly: true }}
        />
      </Paper>
    </Box>
  );
};

export default ContextAware;