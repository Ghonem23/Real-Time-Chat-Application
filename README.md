# 💬 Real-Time Chat Application

A **real-time full-stack chat system** built with **Spring Boot WebSocket** and **SockJS/STOMP** to enable instant, bi-directional communication between multiple users.  
This project demonstrates backend engineering, frontend integration, and live deployment — all running on **Replit (cloud-hosted)**.

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-orange?logo=java&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring%20Boot-3.5.x-brightgreen?logo=springboot&logoColor=white" />
  <img src="https://img.shields.io/badge/WebSocket-Realtime-blue?logo=websocket&logoColor=white" />
  <img src="https://img.shields.io/badge/Deployed_on-Replit-purple?logo=replit&logoColor=white" />
</p>

---

## 🚀 Live Demo
🌐 **[Try it Live](https://4e588ebe-dfa3-4bed-abd7-3874ca01817d-00-16emwnofwx20w.picard.replit.dev/)**  
💻 **[View Source on GitHub](https://github.com/Ghonem23/Real-Time-Chat-Application)**

Open the link in **two tabs**, enter different usernames (e.g., `Ghonem` and `Hussein`), and chat live in real time! ⚡

---

## 🧠 Features

✅ Real-time messaging powered by **WebSockets**  
✅ Multi-user chat (bi-directional communication)  
✅ Instant message broadcast using **STOMP protocol**  
✅ Simple, modern frontend built with **HTML, CSS, and JavaScript**  
✅ In-memory database for quick testing and persistence  
✅ Hosted online via **Replit** (secure HTTPS + WSS)  

---

## 🏗️ Tech Stack

| Layer | Technology |
|--------|-------------|
| **Backend** | Java 17, Spring Boot 3.5.x, WebSocket, STOMP, SockJS |
| **Frontend** | HTML5, CSS3, Vanilla JavaScript |
| **Database** | H2 (in-memory, auto-created at runtime) |
| **Build Tool** | Maven |
| **Server** | Embedded Tomcat (port 5000) |
| **Deployment** | Replit (Free Cloud Hosting) |

---

## ⚙️ How It Works

1. **Spring Boot WebSocket** endpoint handles real-time message exchange (`/ws`).
2. **SockJS + STOMP** clients manage the WebSocket connection from the browser.
3. **Messages** are sent to `/app/chat.sendMessage` and broadcast via `/topic/public`.
4. **Frontend (main.js)** connects automatically to the live backend URL and updates the DOM.

---

## 🧩 Project Structure

```
realtime-chat-backend/
│
├── src/
│ ├── main/
│ │ ├── java/com/chatapp/
│ │ │ ├── config/ # WebSocket configuration
│ │ │ ├── controller/ # Message controllers
│ │ │ ├── model/ # Chat message model
│ │ └── resources/
│ │ ├── static/ # Frontend (index.html, main.js)
│ │ └── application.properties
│
├── pom.xml # Maven dependencies
└── README.md # Project documentation
```

---

## 🧪 Local Setup (Optional)

If you’d like to run this app locally:

```bash
# Clone the repository  
...  
git clone https://github.com/Ghonem23/Real-Time-Chat-Application.git
cd Real-Time-Chat-Application

# Build and run using Maven
mvn clean spring-boot:run

Then visit:
👉 http://localhost:5000

🌍 Deployment Info

Hosted on Replit Cloud

Runs automatically on port 5000

Accessible via secure URL (HTTPS + WSS)

Frontend served directly by Spring Boot /static

🧑‍💻 Author

Ahmed Ghonem
🎓 Software Engineer

🔗 [LinkedIn Profile](https://www.linkedin.com/in/ahmed-ghonem-277468361/)

---

## ⭐ Show Your Support
If you like this project, please **⭐ star** the repository and connect with me on [LinkedIn](https://www.linkedin.com/in/ahmed-ghonem-277468361/) 💬  
Your support motivates me to build and share more open-source projects!
