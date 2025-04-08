package lazarius.borg.zxspectrum.emulator;

/**
 * The PrinterOutput class simulates the ZX Spectrum's printer output.
 * It handles sending data to a virtual printer device.
 */
public class PrinterOutput {

    private StringBuilder printBuffer;

    /**
     * Constructs a new PrinterOutput instance.
     */
    public PrinterOutput() {
        this.printBuffer = new StringBuilder();
    }

    /**
     * Sends data to the printer.
     *
     * @param data the data to send to the printer
     */
    public void sendData(byte data) {
        // Simulate printing by appending data to the buffer
        printBuffer.append((char) data);
        System.out.println("Printer received data: " + (char) data);
    }

    /**
     * Clears the printer buffer.
     */
    public void flush() {
        // Simulate printing the buffer content
        System.out.println("Printing: " + printBuffer.toString());
        printBuffer.setLength(0); // Clear the buffer
    }
}