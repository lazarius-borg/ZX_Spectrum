package lazarius.borg.emulator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MicrodriveTest {

    @Test
    public void testLoadAndReadCartridge() {
        // Arrange
        Microdrive microdrive = new Microdrive();
        microdrive.formatCartridge(1024); // Format a cartridge with 1KB size

        // Act
        microdrive.writeData(0, (byte) 0x42); // Write data to the cartridge
        byte data = microdrive.readData(0); // Read data back

        // Assert
        assertEquals(0x42, data, "Data read from the cartridge should match the written data.");
    }

    @Test
    public void testSaveAndLoadCartridge() {
        // Arrange
        Microdrive microdrive = new Microdrive();
        microdrive.formatCartridge(1024); // Format a cartridge with 1KB size
        microdrive.writeData(0, (byte) 0x42); // Write data to the cartridge
        String filePath = "test_cartridge.mdr";

        // Act
        microdrive.saveCartridge(filePath); // Save the cartridge to a file
        microdrive.loadCartridge(filePath); // Load the cartridge back
        byte data = microdrive.readData(0); // Read data back

        // Assert
        assertEquals(0x42, data, "Data read from the reloaded cartridge should match the original data.");
    }
}