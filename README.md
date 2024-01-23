# Course Reviews

## Overview
Course Reviews is a Java-based application designed to manage and display reviews for various courses. It allows users to create accounts, submit reviews for courses, and view reviews submitted by others. This project is part of an academic exercise and showcases database management and user interface design in Java.

## Features
- **User Account Management**: Users can create accounts and log in to the system.
- **Review Submission**: Allows users to submit reviews for specific courses, including a rating and text feedback.
- **Review Browsing**: Users can browse and read reviews submitted for different courses.

## Project Structure
- **Main Application**: The entry point of the application, handling initial setup and user interface display.
- **Review Class**: Represents a review, including attributes like text and rating.
- **Course Class**: Represents a course with attributes like department and catalog number.
- **Student Class**: Represents a user or student with attributes like username and password.
- **UIHandler**: Manages the user interface and user interactions.
- **ReviewsDatabaseManager**: Handles database operations related to courses, students, and reviews.

## How to Run
- Ensure you have Java and Gradle installed.
- Clone the repository and navigate to the project directory.
- Use Gradle to build and run the application:
gradle run
