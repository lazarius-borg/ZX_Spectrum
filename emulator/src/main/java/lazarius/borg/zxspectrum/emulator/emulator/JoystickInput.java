package lazarius.borg.zxspectrum.emulator;

/**
 * The JoystickInput class simulates joystick input for the ZX Spectrum emulator.
 * It maps joystick movements to emulator input states.
 */
public class JoystickInput {

    private int joystickState;

    /**
     * Constructs a JoystickInput instance with the default state.
     */
    public JoystickInput() {
        this.joystickState = 0x00; // Default state: no buttons pressed
    }

    /**
     * Simulates pressing a joystick button.
     *
     * @param button the button to press
     */
    public void pressButton(int button) {
        joystickState |= (1 << button); // Set the bit for the pressed button
        System.out.println("Joystick button pressed: " + button);
    }

    /**
     * Simulates releasing a joystick button.
     *
     * @param button the button to release
     */
    public void releaseButton(int button) {
        joystickState &= ~(1 << button); // Clear the bit for the released button
        System.out.println("Joystick button released: " + button);
    }

    /**
     * Retrieves the current state of the joystick.
     *
     * @return an integer representing the joystick state
     */
    public int getJoystickState() {
        return joystickState;
    }
}