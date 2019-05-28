package cn.graydove.game;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class Music {
	
	private String new_path;
	private URL new_url;
	private AudioInputStream ais;
	private Clip new_audio;
	
	public Music(){
		
	}
	
	public Music(String urlBase){
		this.new_path = "file:/"+System.getProperty("user.dir")+"/src/"+urlBase;
			try {
				this.new_url = new URL(new_path);
				this.ais = AudioSystem.getAudioInputStream(new_url);
				new_audio = AudioSystem.getClip();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}
	}
	
	public void play() {
		if(new_url != null) {
			try {
				new_audio.open(ais);
				new_audio.start();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
			}
		}
	}
	
	public void loop() {
		if(new_url != null) {
			try {
				new_audio.open(ais);
				new_audio.loop(Clip.LOOP_CONTINUOUSLY);
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
			}
		}
	}

	public void stop() {
		if(new_url != null) {
			new_audio.stop();
		}
	}
}


/*import java.applet.Applet;
import java.applet.AudioClip;
import java.net.MalformedURLException;
import java.net.URL;

public class Music {
	public URL url;
	boolean running;
	AudioClip audio;
	
	public Music(){
		
	}
	
	public Music(String urlBase){
		try {
			String root = System.getProperty("user.dir");
			String baseCode = root+"/src/"+urlBase;
//			System.out.println(urlBase);
			this.url=new URL("file:/"+baseCode);
		}catch(MalformedURLException e) {
			e.printStackTrace();
		}
		audio = Applet.newAudioClip(url);
	}
	
	public void play() {
		if(url != null) {
//			System.out.println(url);
			audio.play();
		}
	}
	
	public void loop() {
		if(url != null) {
		audio.loop();
		}
	}

	public void stop() {
		if(url != null) {
			audio.stop();
		}
	}
}*/
