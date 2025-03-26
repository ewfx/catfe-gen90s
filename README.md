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
The Context-Aware Testing System is a comprehensive testing platform designed to enhance the quality, security, and compliance of financial applications by leveraging AI-powered intelligence and real-time analysis. It provides end-to-end testing capabilities with a focus on simulating real-world financial scenarios. Here's what it does:

Context-Aware Test Generation and Execution:

Dynamically generates and executes behavior-driven test cases (BDD) using contextual data such as application state, user behavior, and transaction patterns.

Supports automated BDD test case generation through Google API's Gemini-2.0-Flash for accurate and efficient scenario coverage.

## Payments BDD Testing:

Performs specialized testing for payment gateways, validating payment flows, transaction reliability, and regulatory compliance.

Simulates real-time financial scenarios like failed transactions, fraud detection, and cross-border payments.

## Context-Aware Testing by URL:

Allows users to input application URLs for targeted test generation and execution.

Provides AI-generated BDD scenarios based on the URL’s context using Together AI models.

## Real-Time Ethical Hacking Tests:

Conducts automated security assessments using ZAP OWASP to detect vulnerabilities such as SQL injection, cross-site scripting (XSS), and API misconfigurations.

Generates detailed reports on identified vulnerabilities, categorized by severity.

## Data-Driven Fraud Detection:

Leverages AI models to detect and flag suspicious transactions in real-time using predictive fraud analytics.

Analyzes large datasets to identify anomalies and generate actionable insights for fraud prevention.

## KYC Verification Using Agentic AI:

Automates the KYC (Know Your Customer) process by verifying customer information using Agentic AI.

Conducts advanced document and identity verification to ensure compliance with regulatory standards.

## Real-Time Stock Information:

Integrates with Alpha Vantage to fetch live stock market data and analyze market trends.

Provides real-time financial insights, assisting in scenario-based testing of trading and investment platforms.

## Chatbot with Agentic AI for Real-Time Stock Integration Testing:

Features an AI-powered chatbot that uses Agentic AI to facilitate real-time interaction for stock market-related queries.

The chatbot provides instant access to live stock data using Alpha Vantage and assists in generating and executing context-aware test cases for financial applications.

Enables financial analysts, developers, and testers to simulate stock market scenarios, predict system behavior, and identify potential issues.

## Intelligent Reporting and Analysis:

Generates comprehensive reports with AI-powered insights, including test results, vulnerability analysis, and fraud detection summaries.

Visualizes data using interactive dashboards for easy decision-making by stakeholders.

In essence, the Context-Aware Testing System ensures that financial applications are reliable, secure, and compliant by proactively identifying potential issues before they impact users. It significantly reduces the manual effort involved in test creation and accelerates the time-to-market for financial products.

## 🛠️ How We Built It
The Context-Aware Testing System was developed using a modern and scalable technology stack, incorporating AI-powered intelligence, microservices architecture, and real-time analysis. Each component was carefully designed to ensure seamless integration, high performance, and scalability. Here’s how we built it:

## Backend Development Using Java Spring Boot Microservices
- The backend is built using Java Spring Boot, following a microservices architecture to ensure modularity and flexibility.

- Each microservice is dedicated to specific functionalities, such as test case generation, execution, fraud detection, ethical hacking tests, and reporting.

- The microservices communicate using REST APIs and gRPC for efficient data exchange.

- Spring Security ensures secure authentication and authorization for all users.

## Frontend with ReactJS
- The user interface is developed using ReactJS to provide an interactive and responsive user experience.

- Users can access various menus for different testing scenarios, input application URLs, generate BDD test cases, and view reports through a clean and intuitive UI.

- State management using Redux ensures efficient data handling and UI updates.

## AI-Powered Context-Aware Capabilities Using Agentic AI and Together AI
- The system uses Agentic AI through Together AI to interpret the application context, predict potential risks, and recommend relevant test cases.

- For natural language understanding and BDD generation, we integrated AI models such as:

      mistralai/Mixtral-8x22B-Instruct-v0.1 for intelligent scenario generation.

      meta-llama/Llama-3.3-70B-Instruct-Turbo for context-aware decision-making.

- The AI chatbot also utilizes Together AI to provide real-time insights and stock data interactions.
## BDD Test Case Generation Using Google API's Gemini-2.0-Flash
- For Behavior-Driven Development (BDD) test generation, we use Gemini-2.0-Flash from Google APIs.

- Users provide contextual inputs such as application URLs or financial scenarios, and the system generates precise BDD test cases using AI-driven predictions.

## Real-Time Ethical Hacking with ZAP OWASP
- ZAP OWASP (Zed Attack Proxy) is integrated to perform real-time security assessments.

- It simulates cyberattacks to identify vulnerabilities like SQL injections, cross-site scripting (XSS), and API weaknesses.

- Reports are generated with categorized risk levels for further analysis and mitigation.

## Fraud Detection Using AI Models
- The system uses Agentic AI to analyze transactional data in real-time and detect fraudulent activities using advanced anomaly detection algorithms.

- Suspicious transactions are flagged for further investigation, reducing financial losses and enhancing security.
## KYC Verification Using Agentic AI
- The KYC (Know Your Customer) module uses AI for document verification, identity validation, and compliance checks.

- It leverages machine learning to detect anomalies and ensure financial institutions meet regulatory requirements.

## Real-Time Stock Integration Using Alpha Vantage  
- For live stock data, we integrated Alpha Vantage APIs.

- Users can access real-time stock information to perform trading scenario testing or simulate stock-related application behavior.

- The chatbot uses this data to provide financial insights and assist in testing stock-related use cases.

## Reporting and Visualization
- Comprehensive reports are generated with AI-powered insights and presented through interactive dashboards using ReactJS.

- Reports include test results, vulnerability analysis, fraud detection insights, and stock data analytics.

- Reports can also be emailed directly to stakeholders for faster decision-making.

## Jira Ready Integration
- This app is also having a plug play capability to integrate with other tools such as Jira for realtime ticket creation integration.
  
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
