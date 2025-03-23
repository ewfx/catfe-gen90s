import React, { useState } from 'react';
import axios from 'axios';

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
    <div style={{ padding: '20px' }}>
      <h2>Context-Aware Testing</h2>
      <textarea 
        value={appDescription} 
        onChange={(e) => setAppDescription(e.target.value)} 
        placeholder="Describe your application..." 
        style={{ width: '100%', height: '100px' }}
      />
      <button onClick={generateTestCases} style={{ marginTop: '10px' }}>Generate Test Cases</button>
      <textarea readOnly value={testCases} style={{ width: '100%', height: '200px', marginTop: '10px' }} />
    </div>
  );
};

export default ContextAware;
