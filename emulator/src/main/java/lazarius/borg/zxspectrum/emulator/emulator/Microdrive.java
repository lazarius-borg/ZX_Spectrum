package lazarius.borg.emulator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Microdrive class simulates the ZX Spectrum's microdrive storage system.
 * It handles reading and writing data to virtual microdrive cartridges.
 */
public class Microdrive {

    private static final Logger logger = LoggerFactory.getLogger(Microdrive.class);

    private byte[] cartridgeData;
    private boolean isCartridgeLoaded;

    /**
     * Constructs a new Microdrive instance.
     */
    public Microdrive() {
        this.cartridgeData = new byte[0];
        this.isCartridgeLoaded = false;
    }

    /**
     * Loads a cartridge from the specified file path.
     *
     * @param filePath the path to the cartridge file
     */
    public void loadCartridge(String filePath) {
        try {
            cartridgeData = Files.readAllBytes(Path.of(filePath));
            isCartridgeLoaded = true;
            logger.info("Cartridge loaded successfully from: {}", filePath);
        } catch (IOException e) {
            logger.error("Failed to load cartridge: {}", e.getMessage());
        }
    }

    /**
     * Saves the currently loaded cartridge to the specified file path.
     *
     * @param filePath the path to save the cartridge file
     */
    public void saveCartridge(String filePath) {
        if (!isCartridgeLoaded) {
            logger.warn("No cartridge loaded. Please load a cartridge first.");
            return;
        }
        try {
            Files.write(Path.of(filePath), cartridgeData);
            logger.info("Cartridge saved successfully to: {}", filePath);
        } catch (IOException e) {
            logger.error("Failed to save cartridge: {}", e.getMessage());
        }
    }

    /**
     * Reads data from the microdrive.
     *
     * @param address the address to read from
     * @return the data read from the microdrive
     */
    public byte readData(int address) {
        if (!isCartridgeLoaded || address < 0 || address >= cartridgeData.length) {
            logger.error("Invalid read operation.");
            return 0;
        }
        return cartridgeData[address];
    }

    /**
     * Writes data to the microdrive.
     *
     * @param address the address to write to
     * @param value the data to write
     */
    public void writeData(int address, byte value) {
        if (!isCartridgeLoaded || address < 0 || address >= cartridgeData.length) {
            logger.error("Invalid write operation.");
            return;
        }
        cartridgeData[address] = value;
    }

    /**
     * Formats the microdrive cartridge with the specified size.
     *
     * @param size the size of the cartridge in bytes
     */
    public void formatCartridge(int size) {
        cartridgeData = new byte[size];
        Arrays.fill(cartridgeData, (byte) 0xFF); // Fill with default value
        isCartridgeLoaded = true;
        logger.info("Cartridge formatted with size: {} bytes.", size);
    }
}