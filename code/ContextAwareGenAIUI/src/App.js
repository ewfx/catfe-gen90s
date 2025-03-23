import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import ContextAware from './components/ContextAware';
import ReadAnyApp from './components/ReadAnyApp';
import EthicalHacking from './components/EthicalHacking';
import FraudDetection from './components/FraudDetection';
const Home = () => (
  <div style={{ padding: '20px' }}>
    <h1>Context-Aware Testing System</h1>
    <div style={{ display: 'flex', gap: '20px', marginTop: '20px' }}>
      <Link to="/context-aware"><button>Context-Aware Testing</button></Link>
      <Link to="/ethical-hacking"><button>Ethical Hacking Test</button></Link>
	  <Link to="/fraud-detection"><button>Payments Fraud Detection Test Dashboard</button></Link>
    </div>
  </div>
);

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/context-aware" element={<ContextAware />} />
        <Route path="/ethical-hacking" element={<EthicalHacking />} />
		 <Route path="/fraud-detection" element={<FraudDetection />} />
      </Routes>
    </Router>
  );
};

export default App;
