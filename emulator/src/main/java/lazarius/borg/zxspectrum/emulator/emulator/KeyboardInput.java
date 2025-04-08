package lazarius.borg.zxspectrum.emulator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The KeyboardInput class simulates the ZX Spectrum keyboard.
 * It maps key presses to the keyboard matrix and updates the CPU state.
 */
public class KeyboardInput {

    private static final Logger logger = LoggerFactory.getLogger(KeyboardInput.class);
    private static final int ROWS = 5;
    private static final int COLUMNS = 8;
    private final int[][] keyboardMatrix;
    private CPU cpu;

    public KeyboardInput(CPU cpu) {
        this.cpu = cpu;
        this.keyboardMatrix = new int[ROWS][COLUMNS];
    }

    /**
     * Processes a key press event.
     *
     * @param keyCode the key code of the pressed key
     */
    public void processKeyPress(int keyCode) {
        logger.info("Processing key press: {}", keyCode);
        int[] keyPosition = mapKeyCodeToMatrix(keyCode);
        if (keyPosition != null) {
            int row = keyPosition[0];
            int col = keyPosition[1];
            keyboardMatrix[row][col] = 1; // Mark the key as pressed
            cpu.updateKeyboardState(row, col, true);
            logger.info("Key pressed: {} (Row: {}, Col: {})", keyCode, row, col);
        } else {
            logger.warn("Unknown key pressed: {}", keyCode);
        }
    }

    /**
     * Processes a key release event.
     *
     * @param keyCode the key code of the released key
     */
    public void processKeyRelease(int keyCode) {
        logger.info("Processing key release: {}", keyCode);
        int[] keyPosition = mapKeyCodeToMatrix(keyCode);
        if (keyPosition != null) {
            int row = keyPosition[0];
            int col = keyPosition[1];
            keyboardMatrix[row][col] = 0; // Mark the key as released
            cpu.updateKeyboardState(row, col, false);
            logger.info("Key released: {} (Row: {}, Col: {})", keyCode, row, col);
        } else {
            logger.warn("Unknown key released: {}", keyCode);
        }
    }

    /**
     * Retrieves the current state of the keyboard matrix.
     *
     * @return a 2D array representing the keyboard matrix state
     */
    public int[][] getKeyboardState() {
        return keyboardMatrix;
    }

    private int[] mapKeyCodeToMatrix(int keyCode) {
        // Map host key codes to ZX Spectrum keyboard matrix positions
        switch (keyCode) {
            case 49: return new int[]{0, 0}; // '1'
            case 50: return new int[]{0, 1}; // '2'
            case 51: return new int[]{0, 2}; // '3'
            case 52: return new int[]{0, 3}; // '4'
            case 53: return new int[]{0, 4}; // '5'
            case 54: return new int[]{0, 5}; // '6'
            case 55: return new int[]{0, 6}; // '7'
            case 56: return new int[]{0, 7}; // '8'
            case 57: return new int[]{1, 0}; // '9'
            case 48: return new int[]{1, 1}; // '0'
            // Add mappings for other keys here
            default: return null; // Unknown key
        }
    }
}