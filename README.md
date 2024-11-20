# Course Reviews Application

## Overview

The Course Reviews Application is a Java-based program designed to facilitate course review submissions and retrievals for academic institutions. It allows students to create accounts, log in, submit reviews for courses, and view aggregated reviews.

## Features

- **User Account Management**: Create and log into accounts.
- **Course Reviews**: Submit textual reviews and numerical ratings for specific courses.
- **Review Retrieval**: View all reviews for a course along with its average rating.
- **Database Integration**: Stores data securely using SQLite.

## Project Structure

- **Main**: Entry point for the application.
- **UIHandler**: Manages user interactions and flows (e.g., login, review submissions, viewing reviews).
- **Student**: Represents user accounts.
- **Course**: Defines course details (e.g., department and catalog number).
- **Review**: Stores review text and ratings.
- **ReviewsDatabaseManager**: Handles database connections, queries, and table management.

### Database Tables

1. **Students**:
   - ID (Primary Key)
   - Username
   - Password
2. **Courses**:
   - ID (Primary Key)
   - Department
   - Catalog Number
3. **Reviews**:
   - ID (Primary Key)
   - StudentID (Foreign Key)
   - CourseID (Foreign Key)
   - Review (Text)
   - Rating (Integer)

## Technologies Used

- **Java**: Core programming language.
- **SQLite**: Lightweight database for persistent data storage.

## How to Run

### Prerequisites

Ensure that Java Development Kit (JDK) and SQLite are installed on your system.

### Steps

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/AkanMNdem/course-reviews.git
   cd course-reviews/hw7-coursereview-xaa8tq-faf4xj-rqd3jkx
   ```
2.	Compile and Run the Project:
   ```bash
   javac -d . src/main/java/edu/virginia/cs/hw7/*.java
   java edu.virginia.cs.hw7.Main
   ```

## How to Use

	1.	Creating an Account:
	•	Follow the prompts to create a new account by providing a username and password.
	2.	Logging In:
	•	Use your credentials to log into the application.
	3.	Submitting a Review:
	•	Enter the course details (e.g., “BIOL 2200”).
	•	Provide a textual review and rate the course on a scale of 1-5.
	4.	Viewing Reviews:
	•	Search for a course by its department and catalog number.
	•	View reviews and the course’s average rating.

## Notes

This project demonstrates skills in Java development, SQLite integration, and user interface design. The application currently runs in a terminal environment for simplicity.

## License

This project is for educational purposes and does not include an open-source license.
