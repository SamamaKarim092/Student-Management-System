# 🎓 Project: Student Management System

## 🔍 Overview
The **Student Management System** is a scalable, modular desktop application designed to efficiently manage students, courses, and grade records. Developed with a strong emphasis on clean architecture and maintainability, the system is built using **Java (Swing for GUI)** and integrates with **Microsoft SQL Server Management Studio (SSMS)** to ensure robust, secure, and centralized data management.

To ensure maintainability and software quality, multiple **Design Patterns** are applied for key components in the system.

## 🧠 Design Patterns Used

- **🔁 Singleton Pattern:** Manages a centralized, thread-safe database connection.
- **🏭 Factory Pattern:** Creates consistent and validated Student and Course objects.
- **👁️ Observer Pattern:** Enables real-time UI updates across panels when grades or selections change.
- **🧩 Mediator Pattern:** Coordinates communication between loosely coupled UI components (e.g., StudentPanel, CoursePanel).
- **🚫 Null Object Pattern:** Prevents null pointer exceptions by using default placeholder objects.
- **🌉 Bridge Pattern:** Decouples UI behavior from styling, supporting flexible runtime theming and modular UI development.

## ✨ Features

- **👨‍🎓 Student Registration**
  - Add, edit, and remove student records with validation.

- **📚 Course Management**
  - Manage courses with consistent object creation via the factory pattern.

- **📊 Grade Assignment**
  - Assign and update grades for students per course.

- **🔄 Real-Time UI Sync**
  - Seamless panel updates using the Observer pattern.

- **🧠 Smart Architecture**
  - Modular components for easier debugging, testing, and future scalability.

- **📡 Centralized SQL Server Integration**
  - Secure data operations with SSMS-backed persistence.

- **🧩 UI Flexibility**
  - Bridge Pattern supports theming and styling extensions.

## ⚙️ Technologies Used

- **Java (Swing)** – GUI development  
- **Microsoft SQL Server (SSMS)** – Backend database  
- **JDBC** – Java Database Connectivity  
- **Object-Oriented Programming (OOP)** – Modular design  
- **Software Design Patterns** – Clean, maintainable architecture  

---

## 📸 Interface Snapshot

![image](https://github.com/user-attachments/assets/3dd4cddb-8f7a-4f68-b3c8-946c599d773c)

---

## 🔗 Live Preview / Demo

This is a desktop application, so no web demo is available. However, the source code and instructions to run it locally are provided below.
