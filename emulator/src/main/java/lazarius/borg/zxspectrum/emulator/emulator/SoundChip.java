package lazarius.borg.zxspectrum.emulator;

/**
 * The SoundChip class simulates the AY-3-8912 sound chip used in the ZX Spectrum 128K.
 * It handles sound generation and register manipulation.
 */
public class SoundChip {

    private static final int NUM_CHANNELS = 3;
    private final byte[] registers;
    private final int[] toneRegisters;
    private final int[] noiseRegisters;
    private final int[] envelopeRegisters;
    private final AudioOutput audioOutput;

    public SoundChip(AudioOutput audioOutput) {
        this.registers = new byte[16]; // AY-3-8912 has 16 registers
        this.audioOutput = audioOutput;
        this.toneRegisters = new int[NUM_CHANNELS];
        this.noiseRegisters = new int[NUM_CHANNELS];
        this.envelopeRegisters = new int[NUM_CHANNELS];
    }

    /**
     * Writes a value to a sound chip register.
     *
     * @param register the register to write to
     * @param value the value to write
     */
    public void writeRegister(int register, byte value) {
        if (register >= 0 && register < registers.length) {
            registers[register] = value;
            System.out.println("SoundChip: Register " + register + " set to " + value);
            generateSound();
        } else {
            System.err.println("Invalid register: " + register);
        }
    }

    /**
     * Reads a value from a sound chip register.
     *
     * @param register the register to read from
     * @return the value of the register
     */
    public byte readRegister(int register) {
        if (register >= 0 && register < registers.length) {
            return registers[register];
        } else {
            System.err.println("Invalid register: " + register);
            return 0;
        }
    }

    private void generateSound() {
        // Simulate sound generation based on register values
        byte[] soundData = new byte[256]; // Example sound data
        for (int i = 0; i < soundData.length; i++) {
            double tone = 0;
            for (int channel = 0; channel < NUM_CHANNELS; channel++) {
                tone += Math.sin(i / (10.0 + toneRegisters[channel])); // Example tone generation
            }
            soundData[i] = (byte) (tone / NUM_CHANNELS * 127); // Normalize and scale
        }
        audioOutput.playSound(soundData);
    }
}