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
import Chatbot from './components/Chatbot'; // Import chatbot

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
        <ListItemText primary="Context-Aware Testing" />
      </ListItem>
      <ListItem button component={Link} to="/ethical-hacking">
        <ListItemText primary="Ethical Hacking Test" />
      </ListItem>
      <ListItem button component={Link} to="/fraud-detection">
        <ListItemText primary="Fraud Detection Dashboard" />
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
            <Route path="/ethical-hacking" element={<EthicalHacking />} />
            <Route path="/fraud-detection" element={<FraudDetection />} />
          </Routes>
        </Box>
      </Box>
      <Chatbot /> {/* Chatbot added here */}
    </Router>
  );
};

export default App;
