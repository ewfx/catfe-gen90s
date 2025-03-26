import React, { useState } from "react";
import { Box, TextField, IconButton, Paper, Typography, Button, CircularProgress } from "@mui/material";
import ChatIcon from "@mui/icons-material/Chat";
import SendIcon from "@mui/icons-material/Send";
import CloseIcon from "@mui/icons-material/Close";
import axios from "axios";

const Chatbot = () => {
  const [open, setOpen] = useState(false);
  const [messages, setMessages] = useState([
    { text: "Hi! I am your AI assistant. Ask me about fraud detection, ethical hacking, compliance testing, or BDD test case generation.", sender: "bot" }
  ]);
  const [input, setInput] = useState("");
  const [loading, setLoading] = useState(false);

  const toggleChat = () => setOpen(!open);

  const handleSend = async () => {
    if (!input.trim()) return;

    const userMessage = { text: input, sender: "user" };
    setMessages((prev) => [...prev, userMessage]);
    setLoading(true);

    try {
      const response = await axios.post("http://localhost:8083/chat", { message: input });
      setMessages((prev) => [...prev, { text: response.data, sender: "bot" }]);
    } catch (error) {
      setMessages((prev) => [...prev, { text: "Error: Unable to reach the AI service.", sender: "bot" }]);
    } finally {
      setLoading(false);
    }

    setInput("");
  };

  const handleGenerateBDD = async () => {
    const context = prompt("Enter the application context for BDD generation:");
    if (!context) return;
    setLoading(true);

    try {
      const response = await axios.post("http://localhost:8083/chat/generate", { context });
      setMessages((prev) => [...prev, { text: response.data.message, sender: "bot" }]);
    } catch (error) {
      setMessages((prev) => [...prev, { text: "Error generating BDD test cases.", sender: "bot" }]);
    } finally {
      setLoading(false);
    }
  };

  const handleExecuteTests = async () => {
    setLoading(true);
    try {
      const response = await axios.post("http://localhost:8083/chat/execute");
      setMessages((prev) => [...prev, { text: response.data.message, sender: "bot" }]);
    } catch (error) {
      setMessages((prev) => [...prev, { text: "Error executing tests.", sender: "bot" }]);
    } finally {
      setLoading(false);
    }
  };

  const handleGenerateReport = async () => {
    setLoading(true);
   // try {
     // const response = await axios.post("http://localhost:8083/chat/report");
     // setMessages((prev) => [...prev, { text: response.data.message, sender: "bot" }]);
     // window.open(response.data.downloadLink, "_blank");
   // } catch (error) {
    //  setMessages((prev) => [...prev, { text: "Error generating the report.", sender: "bot" }]);
    //} finally {
    //  setLoading(false);
    //}
	 try {
      const reportPath = "/sample_report.html";
      setMessages((prev) => [...prev, { text: "Report generated. Click below to download.", sender: "bot" }]);
      setMessages((prev) => [...prev, { text: reportPath, sender: "link" }]);
    } catch (error) {
      setMessages((prev) => [...prev, { text: "Error generating the report.", sender: "bot" }]);
    } finally {
      setLoading(false);
    }
  };

  return (
    <>
      {!open && (
        <IconButton onClick={toggleChat} sx={{ position: "fixed", bottom: 20, right: 20, backgroundColor: "#1976d2", color: "white" }}>
          <ChatIcon />
        </IconButton>
      )}

      {open && (
        <Paper elevation={3} sx={{ position: "fixed", bottom: 20, right: 20, width: 350, maxHeight: 500, display: "flex", flexDirection: "column" }}>
          <Box sx={{ p: 2, backgroundColor: "#1976d2", color: "white", display: "flex", justifyContent: "space-between" }}>
            <Typography variant="h6">AI Chatbot</Typography>
            <IconButton onClick={toggleChat} sx={{ color: "white" }}>
              <CloseIcon />
            </IconButton>
          </Box>

          <Box sx={{ flex: 1, p: 2, overflowY: "auto" }}>
            {messages.map((msg, idx) => (
              <Box key={idx} sx={{ mb: 1 }}>
                {msg.sender === "link" ? (
                  <Button variant="contained" color="primary" href={msg.text} target="_blank">
                    Download Report
                  </Button>
                ) : (
                  <Typography sx={{ color: msg.sender === "user" ? "#007bff" : "#28a745" }}>
                    <strong>{msg.sender === "user" ? "You" : "Bot"}:</strong> {msg.text}
                  </Typography>
                )}
              </Box>
            ))}
            {loading && <CircularProgress sx={{ display: "block", margin: "auto" }} />}
          </Box>

          <Box sx={{ display: "flex", p: 1 }}>
            <TextField
              fullWidth
              variant="outlined"
              size="small"
              value={input}
              onChange={(e) => setInput(e.target.value)}
              onKeyDown={(e) => e.key === "Enter" && handleSend()}
              placeholder="Ask something..."
            />
            <IconButton onClick={handleSend} color="primary" disabled={loading}>
              <SendIcon />
            </IconButton>
          </Box>

          <Box sx={{ display: "flex", justifyContent: "space-around", p: 1 }}>
            <Button onClick={handleGenerateBDD} variant="contained" color="info" disabled={loading}>Generate BDD</Button>
            <Button onClick={handleExecuteTests} variant="contained" color="warning" disabled={loading}>Execute Tests</Button>
            <Button onClick={handleGenerateReport} variant="contained" color="success" disabled={loading}>Generate Report</Button>
          </Box>
        </Paper>
      )}
    </>
  );
};

export default Chatbot;