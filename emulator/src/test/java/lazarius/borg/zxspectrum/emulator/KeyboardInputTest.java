package lazarius.borg.emulator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class KeyboardInputTest {

    private KeyboardInput keyboardInput;
    private CPU mockCPU;

    @BeforeEach
    public void setUp() {
        mockCPU = mock(CPU.class);
        keyboardInput = new KeyboardInput(mockCPU);
    }

    @Test
    public void testProcessKeyPress() {
        keyboardInput.processKeyPress(49); // Key '1'
        int[][] state = keyboardInput.getKeyboardState();
        assertEquals(1, state[0][0], "Key '1' should be marked as pressed in the keyboard matrix");
        verify(mockCPU).updateKeyboardState(0, 0, true);
    }

    @Test
    public void testProcessKeyRelease() {
        keyboardInput.processKeyPress(49); // Key '1'
        keyboardInput.processKeyRelease(49); // Release key '1'
        int[][] state = keyboardInput.getKeyboardState();
        assertEquals(0, state[0][0], "Key '1' should be marked as released in the keyboard matrix");
        verify(mockCPU).updateKeyboardState(0, 0, false);
    }

    @Test
    public void testUnknownKeyPress() {
        keyboardInput.processKeyPress(999); // Unknown key
        int[][] state = keyboardInput.getKeyboardState();
        for (int[] row : state) {
            for (int col : row) {
                assertEquals(0, col, "Unknown key should not affect the keyboard matrix");
            }
        }
        verify(mockCPU, never()).updateKeyboardState(anyInt(), anyInt(), anyBoolean());
    }
}