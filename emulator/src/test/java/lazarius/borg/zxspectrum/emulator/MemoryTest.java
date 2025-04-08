package lazarius.borg.emulator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MemoryTest {

    private Memory memory48K;
    private Memory memory128K;

    @BeforeEach
    public void setUp() {
        memory48K = new Memory(true); // 48K model
        memory128K = new Memory(false); // 128K model
    }

    @Test
    public void testReadWrite48KModel() {
        memory48K.write(0x4000, (byte) 0x42);
        assertEquals(0x42, memory48K.read(0x4000), "Should read back the written value in 48K model");

        memory48K.write(0x2000, (byte) 0x24); // ROM area
        assertNotEquals(0x24, memory48K.read(0x2000), "ROM area should be read-only in 48K model");
    }

    @Test
    public void testReadWrite128KModel() {
        memory128K.write(0x4000, (byte) 0x42);
        assertEquals(0x42, memory128K.read(0x4000), "Should read back the written value in 128K model");

        memory128K.write(0x2000, (byte) 0x24); // ROM area
        assertNotEquals(0x24, memory128K.read(0x2000), "ROM area should be read-only in 128K model");
    }

    @Test
    public void testBankSwitching128KModel() {
        memory128K.switchBank(1);
        memory128K.write(0x8000, (byte) 0x55);
        memory128K.switchBank(2);
        assertNotEquals(0x55, memory128K.read(0x8000), "Bank switching should isolate memory banks");

        memory128K.switchBank(1);
        assertEquals(0x55, memory128K.read(0x8000), "Should read back the value after switching back to the original bank");
    }

    @Test
    public void testClearMemory() {
        memory128K.write(0x4000, (byte) 0x42);
        memory128K.clear();
        assertEquals(0x00, memory128K.read(0x4000), "Memory should be cleared to 0");
    }
}