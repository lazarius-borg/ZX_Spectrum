package lazarius.borg.zxspectrum.emulator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The ZXEmulator class is the main entry point for the ZX Spectrum emulator.
 * It initializes and coordinates the various components of the emulator.
 */
public class ZXEmulator {

    private static final Logger logger = LoggerFactory.getLogger(ZXEmulator.class);

    private Memory memory;
    private CPU cpu;
    private KeyboardInput keyboardInput;
    private TapeInput tapeInput;
    private VideoOutput videoOutput;
    private AudioOutput audioOutput;
    private InterruptController interruptController;
    private ULA ula;
    private SoundChip soundChip;
    private boolean is48KModel;

    /**
     * Constructs a ZXEmulator instance.
     *
     * @param is48KModel true if the emulator should simulate the 48K model, false for the 128K model
     */
    public ZXEmulator(boolean is48KModel) {
        this.is48KModel = is48KModel;
        this.memory = new Memory(is48KModel);
        this.videoOutput = new VideoOutput();
        this.audioOutput = new AudioOutput();
        this.soundChip = new SoundChip(audioOutput);
        this.cpu = new CPU(memory, videoOutput, audioOutput);
        this.ula = new ULA(memory, videoOutput, cpu);
        this.interruptController = new InterruptController(cpu);
        this.keyboardInput = new KeyboardInput(cpu);
        this.tapeInput = new TapeInput(memory, cpu);
    }

    /**
     * Gets the sound chip instance.
     *
     * @return the sound chip
     */
    public SoundChip getSoundChip() {
        return soundChip;
    }

    /**
     * Loads the ROM file into memory.
     *
     * @param romPath the path to the ROM file
     */
    public void loadROM(String romPath) {
        try {
            byte[] romData = Files.readAllBytes(Path.of(romPath));
            for (int i = 0; i < romData.length; i++) {
                memory.write(i, romData[i]);
            }
            logger.info("ROM loaded successfully from: {}", romPath);
        } catch (IOException e) {
            logger.error("Failed to load ROM: {}", e.getMessage());
        }
    }

    /**
     * Boots the ZX Spectrum emulator by loading the ROM and starting the CPU.
     */
    public void boot() {
        logger.info("Booting ZX Spectrum Emulator...");
        if (is48KModel) {
            loadROM("src/main/resources/48.rom");
        } else {
            loadROM("src/main/resources/128.rom");
        }
        cpu.enableInterrupts();
        interruptController.startInterrupts();
        ula.generateVideoFrame(); // Generate the initial video frame
        cpu.run();
    }

    /**
     * Starts the emulator.
     */
    public void start() {
        logger.info("ZX Emulator started.");
        // Implementation for starting the emulator
    }

    /**
     * Stops the emulator.
     */
    public void stop() {
        logger.info("ZX Emulator stopped.");
        // Implementation for stopping the emulator
    }

    /**
     * Main method to start the emulator.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        ZXEmulator emulator = new ZXEmulator(false); // Change to true for 48K model
        emulator.boot();
    }
}