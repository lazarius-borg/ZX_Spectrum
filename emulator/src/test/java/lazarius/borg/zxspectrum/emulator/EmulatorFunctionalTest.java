package lazarius.borg.emulator;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class EmulatorFunctionalTest {

    @Test
    public void testLoadAndRunInternationalKarate() throws Exception {
        // Arrange
        Path tapePath = Path.of("src/test/resources/INTERNATIONAL KARATE .tap");
        assertTrue(Files.exists(tapePath), "Tape file should exist: " + tapePath);

        Memory memory = new Memory();
        VideoOutput videoOutput = mock(VideoOutput.class); // Mock VideoOutput
        CPU cpu = new CPU(memory, videoOutput);
        TapeInput tapeInput = new TapeInput(memory, cpu);

        // Act
        tapeInput.loadTape(tapePath.toString());
        tapeInput.loadTapeIntoMemory(0x4000); // Load into video memory region
        tapeInput.playTape();

        // Assert
        cpu.generateFrame(); // Simulate generating video output
        verify(videoOutput, atLeastOnce()).renderFrame(any(byte[].class)); // Verify rendering occurred
    }

    @Test
    public void testLoadAndRunSpekman() throws Exception {
        // Arrange
        Path tapePath = Path.of("src/test/resources/spekman.tap");
        assertTrue(Files.exists(tapePath), "Tape file should exist: " + tapePath);

        Memory memory = new Memory();
        VideoOutput videoOutput = mock(VideoOutput.class); // Mock VideoOutput
        CPU cpu = new CPU(memory, videoOutput);
        TapeInput tapeInput = new TapeInput(memory, cpu);

        // Act
        tapeInput.loadTape(tapePath.toString());
        tapeInput.loadTapeIntoMemory(0x4000); // Load into video memory region
        tapeInput.playTape();

        // Assert
        cpu.generateFrame(); // Simulate generating video output
        verify(videoOutput, atLeastOnce()).renderFrame(any(byte[].class)); // Verify rendering occurred
    }
}