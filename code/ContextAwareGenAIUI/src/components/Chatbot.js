import React, { useState } from "react";
import { Box, TextField, IconButton, Paper, Typography, Button } from "@mui/material";
import ChatIcon from "@mui/icons-material/Chat";
import SendIcon from "@mui/icons-material/Send";
import CloseIcon from "@mui/icons-material/Close";
import axios from "axios";
const Chatbot = () => {
  const [open, setOpen] = useState(false);
  const [messages, setMessages] = useState([
    { text: "Hi! I am your AI assistant. Ask me about fraud detection, ethical hacking, or compliance testing.", sender: "bot" }
  ]);
  const [input, setInput] = useState("");

  const toggleChat = () => setOpen(!open);

  const handleSend = async () => {
    if (!input.trim()) return;

    const userMessage = { text: input, sender: "user" };
	
	const response = await axios.post("http://localhost:8083/chat", { message: input });
    setMessages([...messages, { user: input, bot: response.data }]);   

    

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
            {messages.map((msg, idx) => (
          <div key={idx}>
            <strong>You:</strong> {msg.user} <br />
            <strong>Bot:</strong> {msg.bot}
          </div>
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
