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
The Context-Aware Testing System is designed to ensure robust and intelligent application testing in financial ecosystems. Built using Java Spring Boot microservices and a dynamic ReactJS front-end, the system leverages advanced AI capabilities through Agentic AI integrated with Together AI using models like mistralai/Mixtral-8x22B-Instruct-v0.1 and meta-llama/Llama-3.3-70B-Instruct-Turbo. For Behavior-Driven Development (BDD) test generation, it utilizes Google API's Gemini-2.0-Flash. Additionally, real-time ethical hacking tests are performed using ZAP OWASP to detect vulnerabilities. The system offers a range of menus and scenarios, including Generic Context-Aware Testing, Payments BDD Testing, Context-Aware Testing by URL, Real-time Ethical Hacking Tests, Data-Driven Fraud Detection, and KYC Verification using Agentic AI. For live financial insights, it integrates with Alpha Vantage for real-time stock information. This innovative testing system empowers organizations to ensure compliance, detect fraud, and enhance application security, while significantly reducing testing effort and time to market.

## 🎥 Demo
Please refer artifacts/demo folder 

## 💡 Inspiration
Generate context aware test cases for financial transactions, customer interactions, fraud detection, regulatory compliance and risk assessment 
AI agent which can update test cases, based on system changes, reducing maintenance efforts 
GenAI driven test scenario synthesis to simulate real world banking activities such as KYC validation, loan approvals, real-time fraud detection and compliance monitoring and one of the key
motivation to build a Context-Aware Testing (CAT) system in financial ecosystems stems from the growing complexity, regulatory pressure, and evolving user expectations in the financial sector. 
Traditional testing approaches often fall short in simulating real-world financial scenarios, leading to undetected issues in production. Context-aware testing provides a more dynamic and intelligent solution. Here are the key inspirations driving its creation:

- Increasing Complexity of Financial Applications
- Data-Driven Decision Making
- Enhanced User Experience (UX)
- Regulatory Compliance and Risk Management
- Ethical Hack Integration

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
