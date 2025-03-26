import React, { useState } from 'react';
import axios from 'axios';
import { Box, TextField, Button, Typography, CircularProgress } from '@mui/material';
import ReactMarkdown from 'react-markdown';

const generateRequestId = () => {
  let lastId = localStorage.getItem('lastRequestId') || 0;
  lastId = parseInt(lastId, 10) + 1;
  localStorage.setItem('lastRequestId', lastId);
  return `REQ${String(lastId).padStart(3, '0')}`;
};

const KYCVerification = () => {
  const [requestId, setRequestId] = useState(generateRequestId);
  const [customerName, setCustomerName] = useState('');
  const [idNumber, setIdNumber] = useState('');
  const [income, setIncome] = useState('');
  const [creditScore, setCreditScore] = useState('');
  const [defaults, setDefaults] = useState('');
  const [loanHistory, setLoanHistory] = useState('');
  const [creditScoreError, setCreditScoreError] = useState('');
  const [analysisDetails, setAnalysisDetails] = useState('');
  const [riskScore, setRiskScore] = useState('');
  const [verificationStatus, setVerificationStatus] = useState('');
  const [loading, setLoading] = useState(false);
  const [responseReceived, setResponseReceived] = useState(false);

  const handleSubmit = async () => {
    if (creditScoreError) {
      alert('Please fix errors before submitting.');
      return;
    }

    setLoading(true);

    const requestData = {
      requestId,
      customerName,
      idNumber,
      uploadedImagePath: '/uploads/ids/sample_id.jpg', // Placeholder
      financialData: JSON.stringify({ income, creditScore, defaults, loanHistory })
    };

    try {
      const res = await axios.post('http://localhost:8083/api/kyc/verify', requestData);
      setRiskScore(res.data.riskScore || 'N/A');
      setVerificationStatus(res.data.verificationStatus || 'N/A');
      setAnalysisDetails(res.data.analysisDetails || 'No analysis details available');
      setResponseReceived(true);
    } catch (error) {
      setAnalysisDetails(`Error: ${error.message}`);
    } finally {
      setLoading(false);
    }
  };

  return (
    <Box sx={{ minWidth: 700, maxWidth: 900, margin: 'auto', mt: 5, p: 3, border: '1px solid #ccc', borderRadius: 2 }}>
      <Typography variant="h6" gutterBottom>KYC Verification</Typography>
      <TextField label="Request ID" value={requestId} fullWidth margin="normal" InputProps={{ readOnly: true }} />
      <TextField label="Customer Name" value={customerName} onChange={(e) => setCustomerName(e.target.value)} fullWidth margin="normal" />
      <TextField label="ID Number" value={idNumber} onChange={(e) => setIdNumber(e.target.value)} fullWidth margin="normal" />
      
      <Typography variant="h6" gutterBottom>Financial Data</Typography>
      <TextField label="Income" type="number" value={income} onChange={(e) => setIncome(e.target.value)} sx={{ maxWidth: 500 }} fullWidth margin="normal" />
      <TextField label="Credit Score" type="number" value={creditScore} onChange={(e) => setCreditScore(e.target.value)} sx={{ maxWidth: 500 }} fullWidth margin="normal" />
      <TextField label="Defaults" type="number" value={defaults} onChange={(e) => setDefaults(e.target.value)} sx={{ maxWidth: 500 }} fullWidth margin="normal" />
      <TextField label="Loan History" value={loanHistory} onChange={(e) => setLoanHistory(e.target.value)} sx={{ maxWidth: 600 }} fullWidth margin="normal" multiline rows={3} />
      
	  <Box>
      <Button variant="contained" color="primary" onClick={handleSubmit} sx={{ mt: 2 }} disabled={loading}>
        {loading ? <CircularProgress size={24} /> : 'Submit'}
      </Button>
       </Box> <Box sx={{ p: 2, height:2 }}></Box> 
       {responseReceived && (
        <>
          <Box sx={{ p: 2, border: '1px solid #ccc', borderRadius: 2, backgroundColor: '#f9f9f9', minWidth: 500, maxWidth: 900 }}>
            <Typography variant="h7" gutterBottom sx={{ mt: 2 }}>Verification Status: <b>{verificationStatus}</b></Typography>
          </Box>
          <Box sx={{ p: 2, border: '1px solid #ccc', borderRadius: 2, backgroundColor: '#f9f9f9', minWidth: 500, maxWidth: 900 }}>
            <Typography variant="h7" gutterBottom>Risk Score: <b>{riskScore}</b></Typography>
          </Box>
          <Typography variant="h6" gutterBottom>Analysis Details</Typography>
          <Box sx={{ p: 2, border: '1px solid #ccc', borderRadius: 2, backgroundColor: '#f9f9f9', minWidth: 700, maxWidth: 900 }}>
            <ReactMarkdown>{analysisDetails}</ReactMarkdown>
          </Box>
        </>
      )}
    </Box>
  );
};

export default KYCVerification;
