# 🏦 Full-Stack Cloud Bank Management System

A production-ready, secure, and robust web application simulating modern banking operations. Built natively using the **Java EE** ecosystem following the **MVC (Model-View-Controller) Architecture**, this application has been migrated from a local `localhost` development environment to a fully live cloud-hosted pipeline.

🚀 **[Live Demo Link](https://bank-management-system1-3bmb.onrender.com)** 🖥️ *Note: For the optimized visual layout, please view this application on a desktop browser or switch to "Desktop Site" mode on your mobile browser.*

---

## 🛠️ Tech Stack & Architecture

* **Backend Engine:** Java Servlets, Core Java (OOPs, Exceptions, Collections)
* **Design Pattern:** MVC (Model-View-Controller) Architecture
* **Presentation Layer:** JSP (JavaServer Pages), JSTL (JSP Standard Tag Library), HTML5, CSS3
* **Data Layer:** MySQL, JDBC (Java Database Connectivity)
* **Build & Dependancy Management:** Apache Maven
* **Cloud Infrastructure & DevOps:** Render (Web Service Hosting), Clever Cloud (Cloud Managed MySQL)

---

## 📈 System Architecture & Data Flow

The application strictly separates concerns across decoupled layers to maximize maintainability and scalability:

1. **View Layer (JSP & JSTL):** Handles the presentation. JSTL tags are leveraged extensively to eliminate raw Java scriptlets, ensuring clean, maintainable UI scripts.
2. **Controller Layer (Java Servlets):** Acts as the centralized traffic director. Intercepts incoming HTTP requests, processes business logic, and manages session state.
3. **Model Layer (JDBC/MySQL):** Handles persistent application data. Connects to a cloud-hosted relational database via secure, pooling JDBC drivers.

 [ Client Browser ] <───HTTP Requests/Responses───> [ Java Servlets (Controller) ]
│
(Processes Logic)
│
▼
[ Client UI (JSP / JSTL) ] <────Binds Data──── [ MySQL Database (Model Layer) ]

---

## ✨ Core Features

* **Secure User Lifecycle Onboarding:** Secure registration and login workflows backed by HTTP session validations to prevent unauthorized page access.
* **Dynamic Account Creation:** Authenticated users can instantly generate a unique checking/savings account linked directly to their profile.
* **Core Banking Transactions:** Real-time processing for dynamic balance updates including **Deposits**, **Withdrawals**, and **Account-to-Account Balance Transfers**.
* **Comprehensive Transaction History:** Dynamic, tabular audit trail rendering past transaction types, target accounts, amounts, and dates using JSTL loops.
* **Defensive Error Handling:** Dedicated routing configurations for transaction failures, missing routes (404s), and input validations.

---

## ⚙️ DevOps & Cloud Migration Journey

Transitioning this project from a local machine to a live web environment involved resolving key infrastructure challenges:

* **Continuous Deployment (CD) Pipeline:** Integrated GitHub webhooks with Render to automate background builds via Maven upon every code push.
* **Environment Configuration Isolation:** Secured sensitive production credentials (database URLs, passwords) completely outside the codebase using system environment variables (`System.getenv()`).
* **Cross-Cloud Networking:** Migrated local MySQL instances onto a remote cloud database infrastructure, configuring robust connection strings to handle secure cross-network database requests.

---

## 🚀 How to Run and Test the Demo

To test the live banking system smoothly, follow this onboarding order:

1. Navigate to the **Live Demo Link**.
2. Click **Register** on the login screen to set up a brand-new user profile.
3. Log into your dashboard and click **Create Account** to initialize your dynamic banking profile.
4. Perform your first transaction (e.g., make a deposit or simulate an internal fund transfer).
5. Existing Bank Account Numbers: 10000100, 10000101
---

## 📦 Local Setup Instructions

To run this project locally on your machine using an IDE (like NetBeans or Eclipse):

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/Abhimanyu124-p/Bank_Management_System1.git](https://github.com/Abhimanyu124-p/Bank_Management_System1.git)
Configure Database:
Import the project SQL schema into your local MySQL instance.

Set Environment Variables:
Set up your local system variables for DB_URL, DB_USER, and DB_PASSWORD.

Build and Run:
Clean and build the project via Maven, and deploy it onto a local application server (like GlassFish or Tomcat).

👤 Author
Abhimanyu - B.Tech Student & Software Developer

Let's connect on LinkedIn!  (www.linkedin.com/in/abhimanyukumarsingh07051)
