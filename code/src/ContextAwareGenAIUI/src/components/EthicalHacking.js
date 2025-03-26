import React, { useState } from 'react';
import axios from 'axios';
import { Button, TextField, Typography, Paper, Box, CircularProgress, Alert, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from '@mui/material';

const EthicalHacking = () => {
  const [url, setUrl] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [reportContent, setReportContent] = useState(null);
  const [downloadLink, setDownloadLink] = useState(null);
  const [rawHtml, setRawHtml] = useState(null);

  const handleHack = async () => {
    setLoading(true);
    setError(null);
    setReportContent(null);
    setDownloadLink(null);
    setRawHtml(null);

    try {
      const response = await axios.post('http://localhost:8083/api/ethical_hack?url='+url, {
      responseType: 'blob', // Important for file download
    });

      const blob = new Blob([response.data], { type: 'text/html' });
      const fileURL = window.URL.createObjectURL(blob);
      setDownloadLink(fileURL);

      // Read the HTML content and display it
      const reader = new FileReader();
      reader.onload = (e) => {
        const htmlContent = e.target.result;
        setRawHtml(htmlContent); // Store the raw HTML
        const parser = new DOMParser();
        const doc = parser.parseFromString(htmlContent, 'text/html');
        const extractedData = extractTableData(doc);
        setReportContent(extractedData);
      };
      reader.readAsText(blob);
      
    } catch (error) {
      setError('Failed to perform ethical hack. Please try again.');
      console.error('Error performing ethical hack:', error);
    } finally {
      setLoading(false);
    }
  };

  // Function to extract table data from the parsed HTML document
  const extractTableData = (doc) => {
    const table = doc.querySelector('table');
    if (!table) return null;

    const headers = Array.from(table.querySelectorAll('th')).map(th => th.innerText);
    const rows = Array.from(table.querySelectorAll('tr'))
      .slice(1) // Skip the header row
      .map(tr => Array.from(tr.querySelectorAll('td')).map(td => td.innerText));

    return { headers, rows };
  };

  return (
    <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center', mt: 5 }}>
      <Paper elevation={3} sx={{ padding: 3, minWidth: 600, maxWidth: 1000, textAlign: 'center' }}>
        <Typography variant="h5" gutterBottom>
          Ethical Hack Your App
        </Typography>
        {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}
        <TextField
          fullWidth
          label="Enter App URL"
          variant="outlined"
          value={url}
          onChange={(e) => setUrl(e.target.value)}
          sx={{ mb: 2 }}
          aria-label="Enter app URL to test security"
        />
        <Button
          variant="contained"
          color="primary"
          onClick={handleHack}
          disabled={!url || loading}
          sx={{ mb: 2 }}
        >
          {loading ? <CircularProgress size={24} /> : 'Ethical Hack'}
        </Button>

        {downloadLink && (
          <Box sx={{ mt: 2 }}>
            <Typography variant="body1">
              Download Report: <a href={downloadLink} download="ethical_hack_report.html">Click here</a>
            </Typography>
          </Box>
        )}
      </Paper>

      {reportContent && (
        <Paper elevation={3} sx={{ padding: 3, mt: 3, maxWidth: 800 }}>
          <Typography variant="h7" gutterBottom>
            Ethical Hack Report
          </Typography>
          <TableContainer>
            <Table>
              <TableHead>
                <TableRow>
                  {reportContent.headers.map((header, index) => (
                    <TableCell key={index} sx={{ fontWeight: 'bold' }}>{header}</TableCell>
                  ))}
                </TableRow>
              </TableHead>
              <TableBody>
                {reportContent.rows.map((row, rowIndex) => (
                  <TableRow key={rowIndex}>
                    {row.map((cell, cellIndex) => (
                      <TableCell key={cellIndex}>{cell}</TableCell>
                    ))}
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        </Paper>
      )}

      {rawHtml && (
        <Paper elevation={3} sx={{ padding: 3, mt: 3, maxWidth: 800, overflow: 'auto' }}>

          <Box 
            sx={{ border: '1px solid #ddd', padding: 2, borderRadius: 2, backgroundColor: '#f9f9f9' }}
            dangerouslySetInnerHTML={{ __html: rawHtml }} 
          />
        </Paper>
      )}
    </Box>
  );
};

export default EthicalHacking;
