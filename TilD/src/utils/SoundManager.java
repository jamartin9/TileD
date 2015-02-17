package utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC10;
import org.lwjgl.util.WaveData;

public class SoundManager {

	int source;
	IntBuffer buffer;
	int channels;

	public static int index;

	HashMap<String, Integer> soundMap = new HashMap<String, Integer>();

	private boolean soundEnabled;
	private boolean mute = false;

	private boolean disposed;

	public SoundManager(int channels) {

		this.channels = channels;

		try {

			AL.create();
			System.out.println("Default device: "
					+ ALC10.alcGetString(null,
							ALC10.ALC_DEFAULT_DEVICE_SPECIFIER));

			if (ALC10.alcIsExtensionPresent(null, "ALC_ENUMERATION_EXT")) {
				String[] devices = ALC10.alcGetString(null,
						ALC10.ALC_DEVICE_SPECIFIER).split("\0");
				System.out.println("Available devices: ");
				for (int i = 0; i < devices.length; i++) {
					System.out.println(i + ": " + devices[i]);
				}
			}

			soundEnabled = true;
		} catch (Exception e) {
			System.err
					.println("Unable to create OpenAL.\n"
							+ "Please make sure that OpenAL is available on this system.\n"
							+ "Exception: " + e);
			return;
		}

		// create several buffers and 1 source
		buffer = BufferUtils.createIntBuffer(channels);
		buffer.flip();
		initialize();
	}

	protected void pause(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException inte) {
		}
	}

	public void initialize() {

		int lastError;

		// al generate buffers and sources
		buffer.position(0).limit(channels);

		AL10.alGenBuffers(buffer);
		if ((lastError = AL10.alGetError()) != AL10.AL_NO_ERROR) {
			exit(lastError);
		}

		source = AL10.alGenSources();
		if ((lastError = AL10.alGetError()) != AL10.AL_NO_ERROR) {
			exit(lastError);
		}

	}

	public void addSound(String path, String name) {

		int lastError = 0;

		int handle = buffer.get(index);
		index = (index + 1) % channels;

		soundMap.put(name, handle);
		/*
		 * This method of loading didn't allow audio to be properly packed into
		 * a stand alone jar because of mark/reset not supported in a inputStream.
		 * I wrapped in a bufferedInputStream.
		 * I then wrapped the buffered input stream in a audioStream for the wave file created.
		 * I had to use: System.setProperty("org.lwjgl.util.Debug", "true");
		 *  Null pointer was being thrown instead of mark/reset when lwjgl debug was false
		 */
		//read audio data from whatever source (file/classloader/etc.)
		InputStream audioSrc = this.getClass().getClassLoader().getResourceAsStream(path);
		//add buffer for mark/reset support
		InputStream bufferedIn = new BufferedInputStream(audioSrc);
		AudioInputStream audioStream = null;
		try {
			audioStream = AudioSystem.getAudioInputStream(bufferedIn);
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}

		WaveData wavefile = WaveData.create(audioStream);

		// copy to buffers
		AL10.alBufferData(handle, wavefile.format, wavefile.data,
				wavefile.samplerate);

		// unload file again
		wavefile.dispose();

		if ((lastError = AL10.alGetError()) != AL10.AL_NO_ERROR) {
			exit(lastError);
		}

	}

	public void dispose() {
		int lastError = 0;
		if (soundEnabled) {
			// delete buffers and sources
			AL10.alDeleteSources(source);
			if ((lastError = AL10.alGetError()) != AL10.AL_NO_ERROR) {
				System.exit(lastError);
			}
			buffer.position(0).limit(channels);
			AL10.alDeleteBuffers(buffer);
			if ((lastError = AL10.alGetError()) != AL10.AL_NO_ERROR) {
				System.exit(lastError);
			}

		}
		if (AL.isCreated()) {
			AL.destroy();
		}

		disposed = true;
	}

	public boolean isDisposed() {
		return disposed;
	}

	protected void exit(int error) {

		System.out.println("OpenAL Error: " + AL10.alGetString(error));
		if (AL.isCreated()) {
			AL.destroy();
		}
		System.exit(-1);
	}

	public void playSound(String name) {

		int lastError = 0;
		int handle = soundMap.get(name);

		if (isPlayingSound(source))
			stopSound();
		// set up source input
		AL10.alSourcei(source, AL10.AL_BUFFER, handle);
		if ((lastError = AL10.alGetError()) != AL10.AL_NO_ERROR) {
			exit(lastError);
		}

		if (soundEnabled) {

			play();
		}
	}

	public void play() {
		int lastError = 0;
		AL10.alSourcePlay(source);
		if ((lastError = AL10.alGetError()) != AL10.AL_NO_ERROR) {
			exit(lastError);
		}
	}

	public synchronized void waitOnPlay() {
		while (isPlayingSound(source)) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public boolean isPlayingSound(int src) {
		return AL10.alGetSourcei(src, AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING;
	}

	public void enableLoop() {
		int lastError = 0;
		// lets loop the sound
		AL10.alSourcei(source, AL10.AL_LOOPING, AL10.AL_TRUE);
		if ((lastError = AL10.alGetError()) != AL10.AL_NO_ERROR) {
			exit(lastError);
		}
	}

	// stop source 0
	public void stopSound() {
		int lastError = 0;
		AL10.alSourceStop(source);
		if ((lastError = AL10.alGetError()) != AL10.AL_NO_ERROR) {
			exit(lastError);
		}
	}

	public boolean isMute() {
		return mute;
	}

	public void setVolume(int vol) {

		float volume = (float) vol;

		if (volume < 0.0f || volume > 100.0f)
			return;

		if (volume == 0)
			mute = true;
		else
			mute = false;

		AL10.alSourcef(source, AL10.AL_GAIN, volume / 100.0f);
	}

	public static void main(String[] args) {
		SoundManager sm = new SoundManager(2);
		sm.addSound("/audio/clunk.wav", "slide");
		sm.playSound("slide");
		// sm.stopSound();
		sm.waitOnPlay();
		sm.dispose();
		System.exit(0);
	}

}
