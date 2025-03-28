import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import {
  AppBar, Toolbar, Typography, Drawer, List, ListItem, ListItemText,
  IconButton, Box, CssBaseline
} from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import ContextAware from './components/ContextAware';
import EthicalHacking from './components/EthicalHacking';
import FraudDetection from './components/FraudDetection';
import Home from './components/Home';
import Chatbot from './components/Chatbot';
import ReadAnyApp from './components/ReadAnyApp';
import BDD from './components/BDD';
import KYCVerification from './components/KYCVerification'; // Import KYCVerification component

const drawerWidth = 240;

const Sidebar = ({ open, toggleDrawer }) => (
  <Drawer
    variant="permanent"
    sx={{
      width: open ? drawerWidth : 70,
      flexShrink: 0,
      '& .MuiDrawer-paper': {
        width: open ? drawerWidth : 70,
        transition: 'width 0.3s',
        overflowX: 'hidden',
      },
    }}
  >
    <List>
      <ListItem button onClick={toggleDrawer}>
        <IconButton>
          <MenuIcon />
        </IconButton>
      </ListItem>
      <ListItem button component={Link} to="/">
        <ListItemText primary="Home" />
      </ListItem>
      <ListItem button component={Link} to="/context-aware">
        <ListItemText primary="Generic Context-Aware Testing" />
      </ListItem>
	  <ListItem button component={Link} to="/bdd">
        <ListItemText primary="Payments BDD Testing" />
      </ListItem>
      <ListItem button component={Link} to="/context-by-url">
        <ListItemText primary="Context Aware By URL" />
      </ListItem>
      <ListItem button component={Link} to="/ethical-hacking">
        <ListItemText primary="Realtime Ethical Hacking Test" />
      </ListItem>
      <ListItem button component={Link} to="/fraud-detection">
        <ListItemText primary="Data Driven Fraud Detection" />
      </ListItem>
      
      <ListItem button component={Link} to="/kyc-verification">
        <ListItemText primary="KYC Verification- using Agentic AI" />
      </ListItem>
    </List>
  </Drawer>
);

const App = () => {
  const [sidebarOpen, setSidebarOpen] = useState(true);
  const toggleDrawer = () => setSidebarOpen(!sidebarOpen);

  return (
    <Router>
      <CssBaseline />
      <AppBar position="fixed" sx={{ zIndex: 1201 }}>
        <Toolbar>
          <Typography variant="h6" noWrap sx={{ flexGrow: 1 }}>
            Context-Aware Testing System
          </Typography>
        </Toolbar>
      </AppBar>
      <Box sx={{ display: 'flex', mt: 8 }}>
        <Sidebar open={sidebarOpen} toggleDrawer={toggleDrawer} />
        <Box component="main" sx={{ flexGrow: 1, p: 3 }}>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/context-aware" element={<ContextAware />} />
            <Route path="/context-by-url" element={<ReadAnyApp />} />
            <Route path="/ethical-hacking" element={<EthicalHacking />} />
            <Route path="/fraud-detection" element={<FraudDetection />} />
            <Route path="/bdd" element={<BDD />} />
            <Route path="/kyc-verification" element={<KYCVerification />} /> {/* New KYC route */}
          </Routes>
        </Box>
      </Box>
      <Chatbot />
    </Router>
  );
};

export default App;
