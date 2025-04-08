package lazarius.borg.emulator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JoystickInputTest {

    private JoystickInput joystickInput;

    @BeforeEach
    public void setUp() {
        joystickInput = new JoystickInput();
    }

    @Test
    public void testPressButton() {
        joystickInput.pressButton(1);
        assertEquals(0b10, joystickInput.getJoystickState(), "Button 1 should be pressed");

        joystickInput.pressButton(3);
        assertEquals(0b1010, joystickInput.getJoystickState(), "Buttons 1 and 3 should be pressed");
    }

    @Test
    public void testReleaseButton() {
        joystickInput.pressButton(1);
        joystickInput.pressButton(3);
        joystickInput.releaseButton(1);
        assertEquals(0b1000, joystickInput.getJoystickState(), "Only button 3 should remain pressed");
    }

    @Test
    public void testGetJoystickState() {
        assertEquals(0x00, joystickInput.getJoystickState(), "Initial joystick state should be 0");

        joystickInput.pressButton(2);
        assertEquals(0b100, joystickInput.getJoystickState(), "Button 2 should be pressed");
    }
}