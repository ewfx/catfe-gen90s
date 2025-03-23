import React, { useState } from 'react';
import axios from 'axios';

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
    <div style={{ padding: '20px' }}>
      <h2>Read Any App</h2>
      <input 
        value={url} 
        onChange={(e) => setUrl(e.target.value)} 
        placeholder="Enter application URL..." 
        style={{ width: '100%', padding: '10px' }}
      />
      <button onClick={generateTestCases} style={{ marginTop: '10px' }}>Generate & Run Test Cases</button>
      <textarea readOnly value={testCases} style={{ width: '100%', height: '200px', marginTop: '10px' }} />
    </div>
  );
};

export default ReadAnyApp;
