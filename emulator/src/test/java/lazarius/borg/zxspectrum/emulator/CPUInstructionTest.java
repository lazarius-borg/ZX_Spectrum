package lazarius.borg.zxspectrum.emulator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CPUInstructionTest {

    private CPU cpu;
    private Memory memory;

    @BeforeEach
    public void setUp() {
        memory = new Memory();
        cpu = new CPU(memory);
    }

    @Test
    public void testNOP() {
        memory.write(0x0000, (byte) 0x00); // NOP opcode
        cpu.run();
        assertEquals(0x0001, cpu.getProgramCounter(), "Program counter should increment by 1 after NOP");
    }

    @Test
    public void testLD_A_n() {
        memory.write(0x0000, (byte) 0x3E); // LD A, n opcode
        memory.write(0x0001, (byte) 0x42); // Immediate value 0x42
        cpu.run();
        assertEquals(0x0002, cpu.getProgramCounter(), "Program counter should increment by 2 after LD A, n");
        // Add assertion for A register when implemented
    }

    @Test
    public void testHALT() {
        memory.write(0x0000, (byte) 0x76); // HALT opcode
        cpu.run();
        assertFalse(cpu.isRunning(), "CPU should stop running after HALT");
    }

    @Test
    public void testJP_nn() {
        memory.write(0x0000, (byte) 0xC3); // JP nn opcode
        memory.write(0x0001, (byte) 0x34); // Low byte of address
        memory.write(0x0002, (byte) 0x12); // High byte of address
        cpu.run();
        assertEquals(0x1234, cpu.getProgramCounter(), "Program counter should jump to address 0x1234 after JP nn");
    }

    @Test
    public void testUndocumentedInstructions() {
        memory.write(0x0000, (byte) 0xED71); // OUT (C), 0 opcode
        cpu.run();
        assertEquals(0x0001, cpu.getProgramCounter(), "Program counter should increment by 1 after OUT (C), 0");

        memory.write(0x0000, (byte) 0xED70); // IN (C), 0 opcode
        cpu.run();
        assertEquals(0x0001, cpu.getProgramCounter(), "Program counter should increment by 1 after IN (C), 0");

        memory.write(0x0000, (byte) 0xED6C); // LD (nn), SP opcode
        cpu.run();
        assertEquals(0x0001, cpu.getProgramCounter(), "Program counter should increment by 1 after LD (nn), SP");

        memory.write(0x0000, (byte) 0xED6D); // LD SP, (nn) opcode
        cpu.run();
        assertEquals(0x0001, cpu.getProgramCounter(), "Program counter should increment by 1 after LD SP, (nn)");
    }

    // Add more tests for other instructions
}