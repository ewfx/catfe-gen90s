# 🚀 Context-Aware Generative AI-driven Test Automation Platform

## 📌 Table of Contents
- [Introduction](#introduction)
- [Demo](#demo)
- [Inspiration](#inspiration)
- [What It Does](#what-it-does)
- [How We Built It](#how-we-built-it)
- [Challenges We Faced](#challenges-we-faced)
- [How to Run](#how-to-run)
- [Tech Stack](#tech-stack)
- [Team](#team)

---

## 🎯 Introduction
This project is a Context-Aware Generative AI-driven Test Automation Platform built using Spring Boot and deployed in a Kubernetes environment. It integrates OpenAI/LLM services, ethical hacking simulations, and fraud detection mechanisms to automate test case generation, execution, and security validation. The application provides RESTful APIs for interacting with AI-driven test automation and security assessments. 

## 🎥 Demo
Please refer artifacts/demo folder 

## 💡 Inspiration
Generate context aware test cases for financial transactions, customer interactions, fraud detection, regulatory compliance and risk assessment 
AI agent which can update test cases, based on system changes, reducing maintenance efforts 
AI driven test scenario synthesis to simulate real world banking activities such as KYC validation, loan approvals, real-time fraud detection and compliance monitoring. 

## ⚙️ What It Does
This application supports below testing scenarios: 

Generic context  

Ethical hacking 

Fraud Detection 

Loan KYC 

Financial Stock 

Chat bot based on Agentic AI 

BDD testing 

## 🛠️ How We Built It
Frontend - React/Vue/Angular
Processing Layer - Spring Boot Microservices
LLM Models used - OpenAI/LLM APIs,Alpha Vantage API, OWASP ZAP, TogetherAI API, meta LLama, Gemini Flash
Runs on Kubernetes using ConfigMaps. 
Uses Maven + Cucumber for test execution. 
## 🚧 Challenges We Faced
1. API Key/Token limits, only free LLM models could be used.

## 🏃 How to Run
1. Clone the repository  
   ```sh
   git@github.com:ewfx/catfe-gen90s.git
   ```
2. Install dependencies  for react application under code -> src -> ContextAwareGenAIUI
   ```sh
   npm install  # or pip install -r requirements.txt (for Python)
   ```
3. Run the project  
   ```sh
   npm start  # or python app.py
4. Build Backend Application  
   ```mvnw
   clean install
   ```  ```
5. Run Backend Application  , go to target directory
   ```java -jar 
   spring-boot-configmaps-demo-0.0.1-SNAPSHOT.jar
   ```  ```
once started , you should the application home page like below

![image](https://github.com/user-attachments/assets/2b9a0152-356d-451e-a0ba-469442ccbe72)

## 🏗️ Tech Stack
- 🔹 Frontend: React 
- 🔹 Backend: Spring boot Microservice and Cucumber
- 🔹 Database: MongoDB
- 🔹 Other:
     •	Chatbot with Agentic AI From TogetherAI -- mistralai/Mixtral-8x22B-Instruct-v0.1
     •	meta-llama/Llama-3.3-70B-Instruct-Turbo
     •	BDD Generation -> Google API's - gemini-2.0-flash
     •	Ethical Hack -> ZAP OWASP
     •	Alpha vantage – Interface for Stock API’s information
     •	Spring Boot Microservices for middleware 
     •	ReactJs for UI


## 👥 Team
- **Prakash Bhat** - [GitHub](#) | [LinkedIn](#)
- **Prasanth Raghav Kuppa** - [GitHub](#) | [LinkedIn](#)
- **Umamaheshwara Reddy G** - [GitHub](#) | [LinkedIn](#)
- **Anna Koshy** - [GitHub](#) | [LinkedIn](#)
- **Mohammed Siraj** - [GitHub](#) | [LinkedIn](#)
