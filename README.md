# Wordle Game Copy

## Overview
Wordle Game Copy is a Java-based recreation of the popular Wordle game. This project replicates the classic word puzzle, allowing players to guess a five-letter word within a limited number of attempts. It provides both a graphical user interface (GUI) and a command-line interface for gameplay.

## Features
- **Graphical User Interface:** Developed with JavaFX for a user-friendly experience.
- **Command-Line Interface:** Supports text-based gameplay for simplicity.
- **Dynamic Word Selection:** Random word generation ensures varied gameplay sessions.
- **Game Feedback:** Provides visual and textual feedback on guesses.

## Project Structure
### Core Components
- **`WordleApplication`:** Entry point for the JavaFX GUI application.
- **`WordleController`:** Manages GUI interactions and updates.
- **`Wordle`:** Interface defining core game logic and mechanics.
- **`CommandLineWordle`:** Enables gameplay through the command line.
- **`WordleDictionary`:** Handles the word list and dictionary operations.

### Test Suite
- Comprehensive unit tests included to validate core game functionalities using JUnit.

## Technologies Used
- **Java:** Core language for logic and mechanics.
- **JavaFX:** Used for the graphical interface.
- **JUnit:** For testing game logic and ensuring stability.

## How to Run
### Prerequisites
- Java Development Kit (JDK) and JavaFX installed.

### Steps
1. **Clone the repository:**
   ```bash
   git clone https://github.com/AkanMNdem/Wordle-Game-Copy.git
   cd Wordle-Game-Copy
   ```

2. **Compile the project:**
   Use your favorite Java IDE or a command-line build tool.

3. **Run the game:**
   - **GUI Mode:**
     ```bash
     java -jar WordleGame.jar
     ```
   - **Command-Line Mode:**
     ```bash
     java edu.virginia.cs.wordle.CommandLineWordle
     ```

## How to Play

1. **GUI Mode:**
   - Start the application and interact via the provided graphical interface.

2. **Command-Line Mode:**
   - Guess a five-letter word based on the feedback provided after each attempt.
   - Continue guessing until the word is revealed or attempts are exhausted.

## Note
This project is for educational purposes only and showcases skills in Java development, GUI design, and algorithm implementation.

## License
This project does not include an open-source license and is intended for personal and educational use only.
