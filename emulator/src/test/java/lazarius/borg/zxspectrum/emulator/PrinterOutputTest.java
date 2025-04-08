package lazarius.borg.emulator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrinterOutputTest {

    @Test
    public void testPrinterOutput() {
        // Arrange
        PrinterOutput printerOutput = new PrinterOutput();

        // Act
        printerOutput.sendData((byte) 'H');
        printerOutput.sendData((byte) 'i');
        printerOutput.flush();

        // Assert
        // Verify that the output matches the expected result (printed to console in this case)
        // This test assumes manual verification of the console output
    }
}