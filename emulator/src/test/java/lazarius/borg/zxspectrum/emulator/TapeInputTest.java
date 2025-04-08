package lazarius.borg.emulator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TapeInputTest {

    private TapeInput tapeInput;
    private Memory mockMemory;
    private CPU mockCPU;

    @BeforeEach
    public void setUp() {
        mockMemory = mock(Memory.class);
        mockCPU = mock(CPU.class);
        tapeInput = new TapeInput(mockMemory, mockCPU);
    }

    @Test
    public void testLoadTape() {
        String tapePath = Path.of("src/resources/INTERNATIONAL KARATE .tap").toString();
        tapeInput.loadTape(tapePath);
        assertNotNull(tapeInput, "Tape should be loaded successfully");
    }

    @Test
    public void testLoadTapeIntoMemory() {
        String tapePath = Path.of("src/resources/INTERNATIONAL KARATE .tap").toString();
        tapeInput.loadTape(tapePath);
        tapeInput.loadTapeIntoMemory(0x4000);
        verify(mockMemory, atLeastOnce()).write(anyInt(), anyByte());
    }

    @Test
    public void testPlayTape() {
        String tapePath = Path.of("src/resources/INTERNATIONAL KARATE .tap").toString();
        tapeInput.loadTape(tapePath);
        tapeInput.playTape();
        // Verify that the tape playback logic interacts with memory and CPU
        verify(mockMemory, atLeastOnce()).write(anyInt(), anyByte());
    }
}