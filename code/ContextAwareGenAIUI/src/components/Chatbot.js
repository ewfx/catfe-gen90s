import React, { useState } from "react";
import { Box, TextField, IconButton, Paper, Typography, Button } from "@mui/material";
import ChatIcon from "@mui/icons-material/Chat";
import SendIcon from "@mui/icons-material/Send";
import CloseIcon from "@mui/icons-material/Close";

const Chatbot = () => {
  const [open, setOpen] = useState(false);
  const [messages, setMessages] = useState([
    { text: "Hi! I am your AI assistant. Ask me about fraud detection, ethical hacking, or compliance testing.", sender: "bot" }
  ]);
  const [input, setInput] = useState("");

  const toggleChat = () => setOpen(!open);

  const handleSend = () => {
    if (!input.trim()) return;

    const userMessage = { text: input, sender: "user" };
    setMessages((prev) => [...prev, userMessage]);

    let botResponse = "I'm not sure. Can you clarify?";

    if (input.toLowerCase().includes("fraud detection")) {
      botResponse = "Fraud detection uses AI to analyze transactions and identify suspicious patterns.";
    } else if (input.toLowerCase().includes("ethical hacking")) {
      botResponse = "Ethical hacking tests vulnerabilities in your application using AI-driven attack simulations.";
    } else if (input.toLowerCase().includes("compliance")) {
      botResponse = "Compliance testing ensures your application follows KYC, AML, and SOX regulations.";
    }

    setTimeout(() => {
      setMessages((prev) => [...prev, { text: botResponse, sender: "bot" }]);
    }, 1000);

    setInput("");
  };

  return (
    <>
      {/* Floating Chat Icon */}
      {!open && (
        <IconButton onClick={toggleChat} sx={{ position: "fixed", bottom: 20, right: 20, backgroundColor: "#1976d2", color: "white" }}>
          <ChatIcon />
        </IconButton>
      )}

      {/* Chat Window */}
      {open && (
        <Paper elevation={3} sx={{ position: "fixed", bottom: 20, right: 20, width: 300, maxHeight: 400, display: "flex", flexDirection: "column" }}>
          <Box sx={{ p: 2, backgroundColor: "#1976d2", color: "white", display: "flex", justifyContent: "space-between" }}>
            <Typography variant="h6">AI Chatbot</Typography>
            <IconButton onClick={toggleChat} sx={{ color: "white" }}>
              <CloseIcon />
            </IconButton>
          </Box>
          <Box sx={{ flex: 1, p: 2, overflowY: "auto" }}>
            {messages.map((msg, index) => (
              <Typography key={index} sx={{ textAlign: msg.sender === "user" ? "right" : "left", color: msg.sender === "user" ? "#0d47a1" : "#000" }}>
                <strong>{msg.sender === "user" ? "You" : "Bot"}:</strong> {msg.text}
              </Typography>
            ))}
          </Box>
          <Box sx={{ display: "flex", p: 1 }}>
            <TextField
              fullWidth
              variant="outlined"
              size="small"
              value={input}
              onChange={(e) => setInput(e.target.value)}
              placeholder="Ask something..."
            />
            <IconButton onClick={handleSend} color="primary">
              <SendIcon />
            </IconButton>
          </Box>
        </Paper>
      )}
    </>
  );
};

export default Chatbot;
