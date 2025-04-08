package lazarius.borg.zxspectrum.emulator;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The InterruptController class manages interrupts for the ZX Spectrum emulator.
 * It triggers interrupts at regular intervals to simulate hardware behavior.
 */
public class InterruptController {

    private final CPU cpu;
    private final ScheduledExecutorService scheduler;
    private int clockCycles;
    private static final int INTERRUPT_INTERVAL_MS = 20; // 50 Hz interrupts

    public InterruptController(CPU cpu) {
        this.cpu = cpu;
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    /**
     * Starts the interrupt generation process.
     */
    public void startInterrupts() {
        scheduler.scheduleAtFixedRate(() -> {
            cpu.triggerInterrupt();
        }, 0, INTERRUPT_INTERVAL_MS, TimeUnit.MILLISECONDS); // Trigger interrupts every 20 ms
    }

    /**
     * Stops the interrupt generation process.
     */
    public void stopInterrupts() {
        scheduler.shutdownNow();
    }
}