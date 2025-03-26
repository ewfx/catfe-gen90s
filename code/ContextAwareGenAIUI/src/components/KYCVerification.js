import React, { useState } from 'react';
import axios from 'axios';
import { Box, TextField, Button, Typography } from '@mui/material';
import ReactMarkdown from 'react-markdown';

const KYCVerification = () => {
  const [requestId, setRequestId] = useState('REQ001'); // Initial ID
  const [customerName, setCustomerName] = useState('');
  const [idNumber, setIdNumber] = useState('');
  const [income, setIncome] = useState('');
  const [creditScore, setCreditScore] = useState('');
  const [defaults, setDefaults] = useState('');
  const [loanHistory, setLoanHistory] = useState('');
  const [creditScoreError, setCreditScoreError] = useState('');
  const [analysisDetails, setAnalysisDetails] = useState('');

  const handleSubmit = async () => {
    if (creditScoreError) {
      alert('Please fix errors before submitting.');
      return;
    }

    const requestData = {
      requestId,
      customerName,
      idNumber,
      uploadedImagePath: '/uploads/ids/sample_id.jpg', // Placeholder
      financialData: JSON.stringify({ income, creditScore, defaults, loanHistory })
    };

    try {
      const res = await axios.post('http://localhost:8083/api/kyc/verify', requestData);
      setAnalysisDetails(res.data.analysisDetails || 'No analysis details available');
    } catch (error) {
      setAnalysisDetails(`Error: ${error.message}`);
    }
  };

  return (
    <Box sx={{ maxWidth: 500, margin: 'auto', mt: 5, p: 3, border: '1px solid #ccc', borderRadius: 2 }}>
      <Typography variant="h6" gutterBottom>KYC Verification</Typography>
      <TextField label="Request ID" value={requestId} fullWidth margin="normal" InputProps={{ readOnly: true }} />
      <TextField label="Customer Name" value={customerName} onChange={(e) => setCustomerName(e.target.value)} fullWidth margin="normal" />
      <TextField label="ID Number" value={idNumber} onChange={(e) => setIdNumber(e.target.value)} fullWidth margin="normal" />
      
      <Typography variant="h6" gutterBottom>Financial Data</Typography>
      <TextField label="Income" type="number" value={income} onChange={(e) => setIncome(e.target.value)} fullWidth margin="normal" />
      <TextField label="Credit Score" type="number" value={creditScore} onChange={(e) => setCreditScore(e.target.value)} fullWidth margin="normal" />
      <TextField label="Defaults" type="number" value={defaults} onChange={(e) => setDefaults(e.target.value)} fullWidth margin="normal" />
      <TextField label="Loan History" value={loanHistory} onChange={(e) => setLoanHistory(e.target.value)} fullWidth margin="normal" multiline rows={3} />
      
      <Button variant="contained" color="primary" onClick={handleSubmit} sx={{ mt: 2 }}>Submit</Button>
      
      <Typography variant="h6" gutterBottom sx={{ mt: 2 }}>Analysis Details</Typography>
      <Box sx={{ p: 2, border: '1px solid #ccc', borderRadius: 2, backgroundColor: '#f9f9f9' }}>
        <ReactMarkdown>{analysisDetails}</ReactMarkdown>
      </Box>
    </Box>
  );
};

export default KYCVerification;
