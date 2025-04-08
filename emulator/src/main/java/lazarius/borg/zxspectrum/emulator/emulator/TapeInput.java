package lazarius.borg.zxspectrum.emulator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The TapeInput class simulates the ZX Spectrum's tape input system.
 * It supports loading and playing tape files to load programs into memory.
 */
public class TapeInput {

    private static final Logger logger = LoggerFactory.getLogger(TapeInput.class);

    private static final int LEADER_TONE_DURATION = 2000; // Example duration in milliseconds
    private static final int SYNC_PULSE_DURATION = 500; // Example duration in milliseconds
    private static final int BIT_DURATION = 100; // Example duration in milliseconds

    private byte[] tapeData;
    private Memory memory;
    private CPU cpu;
    private int tapePosition;

    public TapeInput(Memory memory, CPU cpu) {
        this.memory = memory;
        this.cpu = cpu;
        this.tapePosition = 0;
    }

    /**
     * Loads a tape file into memory.
     *
     * @param filePath the path to the tape file
     */
    public void loadTape(String filePath) {
        try {
            tapeData = Files.readAllBytes(Path.of(filePath));
            tapePosition = 0;
            logger.info("Tape loaded successfully from: {}", filePath);
        } catch (IOException e) {
            logger.error("Failed to load tape: {}", e.getMessage());
        }
    }

    /**
     * Loads the tape data into memory starting at the specified address.
     *
     * @param startAddress the starting memory address
     */
    public void loadTapeIntoMemory(int startAddress) {
        if (tapeData == null) {
            logger.error("No tape loaded. Please load a tape first.");
            return;
        }
        for (int i = 0; i < tapeData.length; i++) {
            memory.write(startAddress + i, tapeData[i]);
        }
        logger.info("Tape data loaded into memory starting at address: {}", startAddress);
    }

    /**
     * Starts playing the loaded tape.
     */
    public void playTape() {
        if (tapeData == null) {
            logger.error("No tape loaded. Please load a tape first.");
            return;
        }
        logger.info("Playing tape...");
        new Thread(this::processTape).start();
    }

    private void processTape() {
        try {
            // Simulate leader tone
            logger.info("Processing leader tone...");
            Thread.sleep(LEADER_TONE_DURATION);

            // Simulate sync pulse
            logger.info("Processing sync pulse...");
            Thread.sleep(SYNC_PULSE_DURATION);

            // Simulate data bits
            while (tapePosition < tapeData.length) {
                byte signal = tapeData[tapePosition++];
                processSignal(signal);
                Thread.sleep(BIT_DURATION); // Simulate timing for each bit
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Tape processing interrupted.");
        }
        logger.info("Tape playback finished.");
    }

    private void processSignal(byte signal) {
        // Simulate loading data into memory or interacting with the CPU
        int address = 0x4000 + (tapePosition % 0x4000); // Example address calculation
        memory.write(address, signal);
        logger.info("Loaded signal {} into memory at address {}", signal, Integer.toHexString(address));
    }

    /**
     * Stops the tape playback.
     */
    public void stopTape() {
        logger.info("Stopping tape...");
        // Logic to stop the tape playback
    }
}