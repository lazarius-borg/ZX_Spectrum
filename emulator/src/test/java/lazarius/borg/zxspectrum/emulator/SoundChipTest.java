package lazarius.borg.emulator;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class SoundChipTest {

    @Test
    public void testToneGeneration() {
        // Arrange
        AudioOutput audioOutput = mock(AudioOutput.class);
        SoundChip soundChip = new SoundChip(audioOutput);

        // Act
        soundChip.writeRegister(0, (byte) 0x10); // Set tone for channel 0
        soundChip.writeRegister(1, (byte) 0x20); // Set tone for channel 1
        soundChip.writeRegister(2, (byte) 0x30); // Set tone for channel 2

        // Assert
        verify(audioOutput, atLeastOnce()).playSound(any(byte[].class));
    }
}