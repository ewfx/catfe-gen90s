import React from "react";
import { Container, Typography, Paper, Box, List, ListItem, ListItemIcon, ListItemText, Button } from "@mui/material";
import CheckCircleIcon from "@mui/icons-material/CheckCircle";
import LayersIcon from "@mui/icons-material/Layers";
import SecurityIcon from "@mui/icons-material/Security";
import VerifiedUserIcon from "@mui/icons-material/VerifiedUser";
import AssessmentIcon from "@mui/icons-material/Assessment";
import AutoAwesomeIcon from "@mui/icons-material/AutoAwesome";

const Home = () => {
  return (
    <Container maxWidth="lg" sx={{ mt: 4 }}>
      <Paper elevation={3} sx={{ p: 4, textAlign: "center", backgroundColor: "#f5f5f5", borderRadius: 2 }}>
        <Typography variant="h5" gutterBottom sx={{ fontWeight: "bold", color: "#1976d2" }}>
          Context-Aware Testing System
        </Typography>
        <Typography variant="h7" sx={{ mb: 3, color: "#555" }}>
          AI-driven test automation for financial transactions, fraud detection, compliance, and risk assessments.
        </Typography>

        {/* Key Features Section */}
        <Box sx={{ textAlign: "left", mt: 3 }}>
          <Typography variant="h5" gutterBottom sx={{ fontWeight: "bold", color: "#333" }}>
            Key Features:
          </Typography>
          <List>
            <ListItem>
              <ListItemIcon>
                <AutoAwesomeIcon sx={{ color: "#ff9800" }} />
              </ListItemIcon>
              <ListItemText primary="Generates dynamic AI-powered test cases for banking and finance." />
            </ListItem>
            <ListItem>
              <ListItemIcon>
                <SecurityIcon sx={{ color: "#f44336" }} />
              </ListItemIcon>
              <ListItemText primary="Simulates real-world banking activities such as KYC, fraud detection, and risk assessment." />
            </ListItem>
            <ListItem>
              <ListItemIcon>
                <VerifiedUserIcon sx={{ color: "#4caf50" }} />
              </ListItemIcon>
              <ListItemText primary="Ensures compliance with financial regulations (KYC, AML, SOX)." />
            </ListItem>
            <ListItem>
              <ListItemIcon>
                <LayersIcon sx={{ color: "#3f51b5" }} />
              </ListItemIcon>
              <ListItemText primary="Supports OpenAI, Hugging Face, LangChain, GPT-J, and LLaMA." />
            </ListItem>
            <Box sx={{ pl: 4, mt: 1 }}>
              <Typography variant="h7" sx={{ fontWeight: "bold", color: "#333" }}>We have used the below models for our implementation:</Typography>
              <List>
			  
                <ListItem>
				<ListItemIcon>
                <VerifiedUserIcon sx={{ color: "#4caf50" }} />
              </ListItemIcon>
                  <ListItemText primary="Agentic AI -> Together AI - mistralai/Mixtral-8x22B-Instruct-v0.1" />
                </ListItem>
				
                <ListItem>
				<ListItemIcon>
                <VerifiedUserIcon sx={{ color: "#4caf50" }} />
              </ListItemIcon>
                  <ListItemText primary="OpenAI -> Together AI - meta-llama/Llama-3.3-70B-Instruct-Turbo" />
                </ListItem>
				
                <ListItem>
				<ListItemIcon>
                <VerifiedUserIcon sx={{ color: "#4caf50" }} />
              </ListItemIcon>
                  <ListItemText primary="BDD Generation -> Google API's - gemini-2.0-flash" />
                </ListItem>
				
                <ListItem>
				<ListItemIcon>
                <VerifiedUserIcon sx={{ color: "#4caf50" }} />
              </ListItemIcon>
                  <ListItemText primary="Ethical Hack -> ZAP OWASP" />
                </ListItem>
				
                <ListItem>
				<ListItemIcon>
                <VerifiedUserIcon sx={{ color: "#4caf50" }} />
              </ListItemIcon>
                  <ListItemText primary="Realstock Info -> Alpha vantage" />
                </ListItem>
              </List>
            </Box>
          </List>
        </Box>

        {/* Test Cases Section */}
        <Box sx={{ textAlign: "left", mt: 4 }}>
          <Typography variant="h5" gutterBottom sx={{ fontWeight: "bold", color: "#333" }}>
            AI-Driven Test Scenarios:
          </Typography>
          <List>
            <ListItem>
              <ListItemIcon>
                <AssessmentIcon sx={{ color: "#ff5722" }} />
              </ListItemIcon>
              <ListItemText primary="Payment Servicing: Test regulatory changes like FED, CHIPS, and Swift ISO." />
            </ListItem>
            <ListItem>
              <ListItemIcon>
                <SecurityIcon sx={{ color: "#9c27b0" }} />
              </ListItemIcon>
              <ListItemText primary="Fraud Detection: Generate test cases mimicking evolving fraud patterns." />
            </ListItem>
            <ListItem>
              <ListItemIcon>
                <VerifiedUserIcon sx={{ color: "#009688" }} />
              </ListItemIcon>
              <ListItemText primary="Credit Risk Assessments: AI-driven customer creditworthiness simulations." />
            </ListItem>
            <ListItem>
              <ListItemIcon>
                <CheckCircleIcon sx={{ color: "#ffeb3b" }} />
              </ListItemIcon>
              <ListItemText primary="Chatbot Testing: Evaluate banking chatbots' responses to queries." />
            </ListItem>
          </List>
        </Box>

        {/* Call to Action Button */}
        <Box sx={{ mt: 5 }}>
          <Button variant="contained" color="primary" size="large" href="/context-aware">
            Explore AI Testing
          </Button>
        </Box>
      </Paper>
    </Container>
  );
};

export default Home;