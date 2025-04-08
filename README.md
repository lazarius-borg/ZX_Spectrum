# ZX Spectrum Emulator

This ZX Spectrum Emulator was completely developed by vibe coding with the help of GitHub Copilot. It is a Java-based emulator that replicates the functionality of the classic ZX Spectrum computer, including its video output, keyboard input, tape loading, and more.

## Features
- **Video Output**: Simulates the ZX Spectrum's video display.
- **Keyboard Input**: Allows users to interact with the emulator using a virtual keyboard.
- **Tape Input**: Load and play `.tap` files to simulate loading games and programs.
- **Sound Output**: Generates sound using a simulated AY-3-8912 sound chip.
- **Peripheral Support**: Includes support for joystick input, printer output, and microdrive storage.

## Prerequisites
- **Java Development Kit (JDK)**: Ensure you have JDK 17 or later installed.
- **Gradle**: The project uses Gradle for build automation.

## How to Run the Emulator

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd ZX_Spectrum
   ```

2. Build the project using Gradle:
   ```bash
   ./gradlew build
   ```

3. Run the application:
   ```bash
   ./gradlew :app:run
   ```

   This will launch the emulator's graphical user interface (GUI).

## Using the Emulator

### Video Output
The emulator's video output is displayed in the center of the application window. It simulates the ZX Spectrum's screen.

### Keyboard Input
A virtual keyboard is provided at the bottom of the application window. Click on the buttons to simulate key presses.

### Tape Recorder Controls
On the right side of the application window, you will find buttons to:
- **Load Tape**: Open a `.tap` file to load a game or program.
- **Play Tape**: Start playing the loaded tape.
- **Stop Tape**: Stop the tape playback.

### Additional Features
- **Sound**: The emulator generates sound output for games and programs.
- **Joystick Input**: Simulate joystick input (not yet exposed in the GUI).
- **Printer Output**: Simulate printing (not yet exposed in the GUI).

## Project Structure
- `app/`: Contains the main application code and GUI implementation.
- `emulator/`: Contains the core emulator logic, including CPU, memory, and peripherals.
- `build-logic/`: Gradle build logic and conventions.
- `gradle/`: Gradle wrapper and dependency management.

## Contributing
Contributions are welcome! Feel free to open issues or submit pull requests.

## License
This project is licensed under the MIT License. See the `LICENSE` file for details.