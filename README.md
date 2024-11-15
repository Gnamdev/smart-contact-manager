# **Smart Contact Manager (SCM)**

The **Smart Contact Manager** is a secure web application designed to help users store, manage, and organize their contacts efficiently. This app enables users to add, update, delete, and view contacts, making contact management easy and accessible in one place.

---

## **Table of Contents**
- [Introduction](#introduction)
- [Core Technologies](#core-technologies)
- [Main Features](#main-features)
- [Challenges and Future Improvements](#challenges-and-future-improvements)
- [What I Learned](#what-i-learned)
- [Closing](#closing)

---

## **Introduction**
The Smart Contact Manager is a personal project that enables users to securely store and manage their contacts in a single, organized location. The platform supports essential features like adding, updating, deleting, and viewing contacts, ensuring that user data is both accessible and manageable.

---

## **Core Technologies**
This project was built using:
- **Backend**: Spring Boot for handling business logic and user interactions.
- **Database**: MySQL to securely store contact information.
- **Frontend**: HTML and CSS to create a simple, user-friendly interface.
- **Security**: Spring Security and OAuth2 for user authentication and authorization, ensuring only authorized users can access and manage their contacts.

---

## **Main Features**

### **User Authentication and Authorization**
- **Secure Login and Signup**: Users can register and log in securely using Spring Security.
- **OAuth2 Support**: Users can log in with external accounts, such as Google, for an additional level of convenience.

### **Contact Management (CRUD Operations)**
- **Add, Edit, Delete, and View Contacts**: Users can perform full CRUD operations on their contacts.
- **Form Validation**: Validation is implemented to ensure all required information is provided correctly before saving, ensuring data integrity.

### **Error Handling and Validation**
- **Form Validation**: Prevents incomplete submissions by showing error messages if fields are missing or incorrect.
- **Error Handling**: Provides users with clear feedback on any issues that may arise, enhancing user experience and data consistency.

---

## **Challenges and Future Improvements**

### **Data Security and Authorization**
Ensuring data security was a priority. Spring Security was implemented to manage user roles and permissions, ensuring that only authorized users can access or edit their data. 
- **Future Improvements**: Plan to implement multi-factor authentication to further strengthen data security.

### **Improving Form Validation**
The current validation handles basic cases effectively, but improvements are planned to make it more robust.
- **Future Improvements**: Introduce custom validations to catch common errors and further enhance the user experience.

---

## **What I Learned**
Working on this project provided valuable insights into:
- **Spring Boot and MySQL Integration**: For building secure and efficient web applications.
- **Spring Security**: For managing user authentication and authorization, ensuring user data protection.
- **User Data Management**: Building a reliable system that maintains data consistency and security.

---

## **Closing**
The **Smart Contact Manager** is a reliable and secure application that provides users with a convenient way to store and organize their contacts. Building this project was an enriching experience, especially in the areas of security and data management, which are crucial for any web application development.

---

