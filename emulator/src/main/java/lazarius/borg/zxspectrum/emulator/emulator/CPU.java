package lazarius.borg.zxspectrum.emulator;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * The CPU class simulates the Z80 CPU used in the ZX Spectrum.
 * It handles instruction execution and state management.
 */
public class CPU {

    private Memory memory;
    private VideoOutput videoOutput;
    private boolean running;
    private int programCounter;
    private Map<Integer, Instruction> instructionSet;
    private boolean interruptEnabled;
    private ULA ula;
    private int clockCycles;
    private static final int CLOCK_RATE_HZ = 3500000; // ZX Spectrum clock rate (3.5 MHz)
    private long lastCycleTime;

    public CPU(Memory memory, VideoOutput videoOutput, AudioOutput audioOutput, ULA ula) {
        this.memory = memory;
        this.videoOutput = videoOutput;
        this.ula = ula;
        this.programCounter = 0x0000; // Start at address 0x0000
        this.instructionSet = new HashMap<>();
        initializeInstructionSet();
    }

    public int getClockCycles() {
        return clockCycles;
    }

    private void initializeInstructionSet() {
        initializeArithmeticInstructions();
        initializeControlFlowInstructions();
        initializeMemoryInstructions();
        initializeBitManipulationInstructions();
        initializeUndocumentedInstructions();
    }

    private void initializeArithmeticInstructions() {
        instructionSet.put(0xC6, (cpu) -> {
            int value = Byte.toUnsignedInt(cpu.memory.read(cpu.programCounter));
            cpu.programCounter = (cpu.programCounter + 1) & 0xFFFF;
            System.out.println("ADD A, " + value);
            // Add logic to update A register and flags
        });
        // Add other arithmetic instructions here
    }

    private void initializeControlFlowInstructions() {
        instructionSet.put(0xC3, (cpu) -> {
            int low = Byte.toUnsignedInt(cpu.memory.read(cpu.programCounter));
            int high = Byte.toUnsignedInt(cpu.memory.read((cpu.programCounter + 1) & 0xFFFF));
            cpu.programCounter = (high << 8) | low;
            System.out.println("JP " + Integer.toHexString(cpu.programCounter));
        });
        // Add other control flow instructions here
    }

    private void initializeMemoryInstructions() {
        instructionSet.put(0x32, (cpu) -> {
            int low = Byte.toUnsignedInt(cpu.memory.read(cpu.programCounter));
            int high = Byte.toUnsignedInt(cpu.memory.read((cpu.programCounter + 1) & 0xFFFF));
            int address = (high << 8) | low;
            cpu.programCounter = (cpu.programCounter + 2) & 0xFFFF;
            System.out.println("LD (" + Integer.toHexString(address) + "), A");
            // Add logic to store A at address
        });
        // Add other memory instructions here
    }

    private void initializeBitManipulationInstructions() {
        instructionSet.put(0xCB46, (cpu) -> { System.out.println("BIT 0, (HL)"); }); // BIT 0, (HL)
        // Add other bit manipulation instructions here
    }

    private void initializeUndocumentedInstructions() {
        instructionSet.put(0xED71, (cpu) -> { System.out.println("OUT (C), 0"); }); // OUT (C), 0
        instructionSet.put(0xED70, (cpu) -> { System.out.println("IN (C), 0"); }); // IN (C), 0
        instructionSet.put(0xED6C, (cpu) -> { System.out.println("LD (nn), SP"); }); // LD (nn), SP
        instructionSet.put(0xED6D, (cpu) -> { System.out.println("LD SP, (nn)"); }); // LD SP, (nn)
        instructionSet.put(0xED7C, (cpu) -> { System.out.println("LD (nn), IX"); }); // LD (nn), IX
        instructionSet.put(0xED7D, (cpu) -> { System.out.println("LD IX, (nn)"); }); // LD IX, (nn)
        instructionSet.put(0xED74, (cpu) -> { System.out.println("LD (nn), IY"); }); // LD (nn), IY
        instructionSet.put(0xED75, (cpu) -> { System.out.println("LD IY, (nn)"); }); // LD IY, (nn)
    }

    public void run() {
        running = true;
        System.out.println("CPU is running...");
        lastCycleTime = System.nanoTime();
        while (running) {
            long currentTime = System.nanoTime();
            long elapsedTime = currentTime - lastCycleTime;
            long cyclesToExecute = (elapsedTime * CLOCK_RATE_HZ) / 1_000_000_000L;

            while (cyclesToExecute > 0 && running) {
                int opcode = Byte.toUnsignedInt(memory.read(programCounter));
                programCounter = (programCounter + 1) & 0xFFFF; // Increment PC and wrap around
                executeInstruction(opcode);
                cyclesToExecute--;
            }

            lastCycleTime = currentTime;
        }
    }

    /**
     * Executes a single instruction.
     */
    private void executeInstruction(int opcode) {
        Instruction instruction = instructionSet.get(opcode);
        if (instruction != null) {
            instruction.execute(this);
            clockCycles += getInstructionCycles(opcode); // Add the cycles for this instruction
        } else {
            System.out.println("Unknown opcode: " + Integer.toHexString(opcode));
            running = false;
        }
    }

    private int getInstructionCycles(int opcode) {
        // Return the number of clock cycles for the given opcode
        // This is a simplified example; a full implementation would include all opcodes
        switch (opcode) {
            case 0x00: // NOP
                return 4;
            case 0xC3: // JP nn
                return 10;
            case 0x76: // HALT
                return 4;
            default:
                return 4; // Default cycle count for unimplemented instructions
        }
    }

    /**
     * Resets the CPU to its initial state.
     */
    public void reset() {
        System.out.println("CPU is resetting...");
        running = false;
        programCounter = 0x0000;
    }

    public void updateKeyboardState(int row, int col, boolean pressed) {
        // Handle the keyboard state update
        System.out.println("Keyboard state updated: Row " + row + ", Col " + col + ", Pressed: " + pressed);
        // Add logic to process the keyboard input as needed
    }

    public void generateFrame() {
        // Simulate generating a framebuffer (e.g., 256x192 pixels for ZX Spectrum)
        byte[] frameBuffer = new byte[256 * 192 / 8]; // Example size for monochrome display

        // Assume video memory starts at address 0x4000 (typical for ZX Spectrum)
        int videoMemoryStart = 0x4000;
        int videoMemoryEnd = videoMemoryStart + frameBuffer.length;

        for (int i = 0; i < frameBuffer.length; i++) {
            frameBuffer[i] = memory.read(videoMemoryStart + i);
        }

        videoOutput.renderFrame(frameBuffer);
    }

    /**
     * Enables interrupts for the CPU.
     */
    public void enableInterrupts() {
        interruptEnabled = true;
    }

    /**
     * Disables interrupts for the CPU.
     */
    public void disableInterrupts() {
        interruptEnabled = false;
    }

    public void triggerInterrupt() {
        if (interruptEnabled) {
            System.out.println("Interrupt triggered");
            // Push the current program counter onto the stack
            int sp = getStackPointer();
            memory.write((sp - 1) & 0xFFFF, (byte) ((programCounter >> 8) & 0xFF)); // High byte
            memory.write((sp - 2) & 0xFFFF, (byte) (programCounter & 0xFF)); // Low byte
            setStackPointer((sp - 2) & 0xFFFF);

            // Set the program counter to the interrupt vector
            programCounter = 0x0038;
        }
    }

    public void handleIO(int port, int value) {
        ula.handleIOPort(port, value);
    }

    @FunctionalInterface
    private interface Instruction {
        void execute(CPU cpu);
    }
}