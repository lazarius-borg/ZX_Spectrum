package lazarius.borg.emulator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class AudioOutputTest {

    private AudioOutput audioOutput;

    @BeforeEach
    public void setUp() {
        audioOutput = new AudioOutput();
    }

    @Test
    public void testPlaySound() {
        byte[] mockSoundData = new byte[]{0x00, 0x01, 0x02}; // Mock sound data

        // Mock the AudioClip class to avoid actual sound playback
        AudioClip mockAudioClip = mock(AudioClip.class);
        Mockito.doNothing().when(mockAudioClip).play();

        // Simulate playing sound
        audioOutput.playSound(mockSoundData);

        // Verify that the play method was called
        verify(mockAudioClip, times(1)).play();
    }
}