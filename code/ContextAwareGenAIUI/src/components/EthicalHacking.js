import React, { useState } from 'react';
import axios from 'axios';
import { Button, TextField, Typography, Paper, Box } from '@mui/material';

const EthicalHacking = () => {
  const [url, setUrl] = useState('');

  const handleHack = async () => {
    try {
      const response = await axios.post(
        `http://localhost:8083/api/ethical_hack?url=${url}`,
        { responseType: 'blob' }
      );
      
      const blob = new Blob([response.data], { type: 'text/html' });
      const link = document.createElement('a');
      link.href = window.URL.createObjectURL(blob);
      link.download = 'ethical_hack_report.html';
      link.click();
    } catch (error) {
      console.error('Error performing ethical hack:', error);
    }
  };

  return (
    <Box sx={{ display: 'flex', justifyContent: 'center', mt: 5 }}>
      <Paper elevation={3} sx={{ padding: 3, maxWidth: 500, textAlign: 'center' }}>
        <Typography variant="h5" gutterBottom>
          Ethical Hack Your App
        </Typography>
        <TextField
          fullWidth
          label="Enter App URL"
          variant="outlined"
          value={url}
          onChange={(e) => setUrl(e.target.value)}
          sx={{ mb: 2 }}
        />
        <Button
          variant="contained"
          color="primary"
          onClick={handleHack}
          disabled={!url}
        >
          Simulate Hack
        </Button>
      </Paper>
    </Box>
  );
};

export default EthicalHacking;