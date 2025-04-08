package lazarius.borg.zxspectrum.emulator;

import java.util.Arrays;

/**
 * The Memory class simulates the memory of the ZX Spectrum.
 * It supports both 48K and 128K models with bank switching for the latter.
 */
public class Memory {

    private static final int MEMORY_SIZE = 128 * 1024; // 128KB
    private static final int BANK_SIZE = 16 * 1024; // 16KB per bank
    private byte[] memory;
    private int currentBank; // Tracks the currently active bank
    private boolean is48KModel;

    /**
     * Constructs a Memory instance for the specified model.
     *
     * @param is48KModel true if the memory is for the 48K model, false for the 128K model
     */
    public Memory(boolean is48KModel) {
        this.is48KModel = is48KModel;
        memory = new byte[MEMORY_SIZE];
        currentBank = 0; // Default to the first bank
    }

    /**
     * Reads a byte from the specified memory address.
     *
     * @param address the memory address to read from
     * @return the byte value at the specified address
     */
    public byte read(int address) {
        if (is48KModel) {
            if (address < 0x4000) {
                // ROM area (first 16KB)
                return memory[address];
            } else {
                // RAM area (next 48KB)
                return memory[address];
            }
        } else {
            if (address < 0x4000) {
                // ROM area (first 16KB)
                return memory[address];
            } else if (address < 0x8000) {
                // Fixed RAM area (next 16KB)
                return memory[address];
            } else {
                // Banked RAM area (last 32KB)
                int bankedAddress = (currentBank * BANK_SIZE) + (address - 0x8000);
                return memory[bankedAddress];
            }
        }
    }

    /**
     * Writes a byte to the specified memory address.
     *
     * @param address the memory address to write to
     * @param value the byte value to write
     */
    public void write(int address, byte value) {
        if (is48KModel) {
            if (address < 0x4000) {
                // ROM area is read-only
                return;
            } else {
                // RAM area
                memory[address] = value;
            }
        } else {
            if (address < 0x4000) {
                // ROM area is read-only
                return;
            } else if (address < 0x8000) {
                // Fixed RAM area
                memory[address] = value;
            } else {
                // Banked RAM area
                int bankedAddress = (currentBank * BANK_SIZE) + (address - 0x8000);
                memory[bankedAddress] = value;
            }
        }
    }

    /**
     * Switches the active memory bank.
     *
     * @param bank the bank number to switch to
     */
    public void switchBank(int bank) {
        if (!is48KModel && bank >= 0 && bank < (MEMORY_SIZE / BANK_SIZE)) {
            currentBank = bank;
            System.out.println("Switched to bank: " + bank);
        } else if (is48KModel) {
            System.err.println("Bank switching is not supported in 48K model.");
        } else {
            System.err.println("Invalid bank: " + bank);
        }
    }

    /**
     * Clears the memory by setting all bytes to zero.
     */
    public void clear() {
        Arrays.fill(memory, (byte) 0);
    }
}