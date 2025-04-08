package lazarius.borg.zxspectrum.emulator;

/**
 * The ULA (Uncommitted Logic Array) class handles video output, memory contention,
 * and I/O port interactions for the ZX Spectrum emulator.
 */
public class ULA {

    private final Memory memory;
    private final VideoOutput videoOutput;
    private final CPU cpu;
    private final KeyboardInput keyboardInput;
    private final JoystickInput joystickInput;
    private final PrinterOutput printerOutput;
    private final Microdrive microdrive;
    private int borderColor;

    /**
     * Constructs a ULA instance with the specified components.
     *
     * @param memory the memory component
     * @param videoOutput the video output component
     * @param cpu the CPU component
     * @param keyboardInput the keyboard input component
     * @param joystickInput the joystick input component
     * @param printerOutput the printer output component
     * @param microdrive the microdrive component
     */
    public ULA(Memory memory, VideoOutput videoOutput, CPU cpu, KeyboardInput keyboardInput, JoystickInput joystickInput, PrinterOutput printerOutput, Microdrive microdrive) {
        this.memory = memory;
        this.videoOutput = videoOutput;
        this.cpu = cpu;
        this.keyboardInput = keyboardInput;
        this.joystickInput = joystickInput;
        this.printerOutput = printerOutput;
        this.microdrive = microdrive;
    }

    /**
     * Sets the border color of the video output.
     *
     * @param color the border color (0-7)
     */
    public void setBorderColor(int color) {
        this.borderColor = color & 0x07; // Border color is 3 bits (0-7)
        System.out.println("Border color set to: " + borderColor);
    }

    /**
     * Generates a video frame by reading video memory and applying attributes.
     */
    public void generateVideoFrame() {
        // Simulate reading video memory and generating a frame with color clash
        byte[] frameBuffer = new byte[256 * 192 / 8]; // Example size for monochrome display
        int videoMemoryStart = 0x4000;
        for (int i = 0; i < frameBuffer.length; i++) {
            frameBuffer[i] = memory.read(videoMemoryStart + i);
        }

        // Simulate color clash by applying attributes to 8x8 blocks
        byte[] attributeMemory = new byte[32 * 24]; // 32x24 attribute blocks
        int attributeMemoryStart = 0x5800;
        for (int i = 0; i < attributeMemory.length; i++) {
            attributeMemory[i] = memory.read(attributeMemoryStart + i);
        }

        videoOutput.renderFrameWithAttributes(frameBuffer, attributeMemory, borderColor);
    }

    /**
     * Handles memory contention by introducing delays for specific memory addresses.
     *
     * @param address the memory address being accessed
     */
    public void handleMemoryContention(int address) {
        // Simulate memory contention by introducing delays
        if (address >= 0x4000 && address < 0x8000) {
            try {
                Thread.sleep(1); // Example delay for contention
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Handles an I/O port interaction.
     *
     * @param port the I/O port number
     * @param value the value to write to the port
     */
    public void handleIOPort(int port, int value) {
        if ((port & 0x0001) == 0) {
            // Handle keyboard input
            int row = (port >> 8) & 0x07; // Extract row from port
            int[] keyboardState = keyboardInput.getKeyboardState()[row];
            int keyboardValue = 0xFF; // Default to all keys unpressed
            for (int col = 0; col < keyboardState.length; col++) {
                if (keyboardState[col] == 1) {
                    keyboardValue &= ~(1 << col); // Clear bit for pressed key
                }
            }
            System.out.println("Keyboard row " + row + " state: " + Integer.toBinaryString(keyboardValue));
        } else if ((port & 0x001F) == 0x001F) {
            // Handle Kempston joystick input
            int joystickState = joystickInput.getJoystickState();
            System.out.println("Joystick state: " + Integer.toBinaryString(joystickState));
        } else if ((port & 0x00FF) == 0x00FE) {
            // Handle ZX Printer output
            printerOutput.sendData((byte) value);
        } else if ((port & 0x00FF) == 0x00DF) {
            // Handle Microdrive input/output
            if ((port & 0x0100) == 0) {
                // Read operation
                int data = microdrive.readData(value);
                System.out.println("Microdrive read data: " + data);
            } else {
                // Write operation
                microdrive.writeData(value, (byte) (port & 0xFF));
                System.out.println("Microdrive wrote data: " + (port & 0xFF));
            }
        } else if ((port & 0x00FF) == 0x00FF) {
            // Handle additional peripheral I/O ports
            System.out.println("Accessed additional peripheral I/O port: " + port);
            // Add logic for other peripherals if needed
        } else {
            System.out.println("Unhandled I/O port: " + port + ", value: " + value);
        }
    }
}