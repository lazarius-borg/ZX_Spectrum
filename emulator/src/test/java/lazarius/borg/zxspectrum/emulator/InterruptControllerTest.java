package lazarius.borg.emulator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Timer;
import java.util.TimerTask;

import static org.mockito.Mockito.*;

public class InterruptControllerTest {

    private InterruptController interruptController;
    private CPU mockCPU;

    @BeforeEach
    public void setUp() {
        mockCPU = mock(CPU.class);
        interruptController = new InterruptController(mockCPU);
    }

    @Test
    public void testStartInterrupts() throws InterruptedException {
        interruptController.startInterrupts();

        // Wait for a short period to allow interrupts to trigger
        Thread.sleep(50);

        // Verify that the CPU's triggerInterrupt method was called at least once
        verify(mockCPU, atLeastOnce()).triggerInterrupt();

        interruptController.stopInterrupts();
    }

    @Test
    public void testStopInterrupts() throws InterruptedException {
        interruptController.startInterrupts();
        Thread.sleep(50);
        interruptController.stopInterrupts();

        // Reset interactions and wait to ensure no further interrupts occur
        reset(mockCPU);
        Thread.sleep(50);

        // Verify that no further interrupts were triggered after stopping
        verify(mockCPU, never()).triggerInterrupt();
    }
}