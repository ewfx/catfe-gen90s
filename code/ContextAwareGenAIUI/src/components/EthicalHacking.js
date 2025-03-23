import React, { useState } from 'react';
import axios from 'axios';

const EthicalHacking = () => {
  const [url, setUrl] = useState('');

  const handleHack = async () => {
	  console.log("url "+url);
    const response = await axios.post('http://localhost:8083/api/ethical_hack?url='+url, {
      responseType: 'blob', // Important for file download
    });

    // Create a blob URL and trigger download
    const blob = new Blob([response.data], { type: 'text/html' });
    const link = document.createElement('a');
    link.href = window.URL.createObjectURL(blob);
    link.download = 'ethical_hack_report.html';
    link.click();
  };

  return (
    <div className="menu">
      <h2>Ethical Hack Your App</h2>
      <input value={url} onChange={(e) => setUrl(e.target.value)} placeholder="Enter app URL" />
      <button onClick={handleHack}>Simulate Hack</button>
    </div>
  );
};

export default EthicalHacking;
