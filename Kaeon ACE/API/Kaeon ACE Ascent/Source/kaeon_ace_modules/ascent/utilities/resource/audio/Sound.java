package kaeon_ace_modules.ascent.utilities.resource.audio;

import kaeon_ace_modules.ascent.processes.scene.utilities.Entity;
import kaeon_ace_modules.ascent.processes.scene.utilities.script.Script;

public class Sound extends Entity {
	
	private Audio audio;
	private boolean isPlaying;
	
	public static int LOOP = 0;
	
	public Sound() {
		
		audio = new Audio();
		
		script();
	}
	
	public Sound(String fileName) {
		
		audio = new Audio(fileName);
		
		script();
	}
	
	public Sound(String fileName, int loops) {
		
		audio = new Audio(fileName);
		
		if(loops > 0)
			play(loops);
		
		else
			play();
		
		script();
	}
	
	public void play() {
		
		if(!audio.isPlaying())
			audio.play();
		
		isPlaying = true;
	}
	
	public void play(int loops) {
		
		if(!audio.isPlaying())
			audio.play(loops);
		
		isPlaying = true;
	}
	
	public void stop() {
		
		audio.stop();
		
		isPlaying = false;
	}
	
	public boolean isPlaying() {
		return isPlaying;
	}
	
	public void setAudio(String fileName) {
		
		if(audio.getFileName().equals(fileName))
			return;
		
		if(isPlaying()) {
			audio.stop();
			audio.setAudio(fileName);
			audio.play();
		}
		
		else
			audio.setAudio(fileName);
	}
	
	public Audio getAudio() {
		return audio;
	}
	
	public String getFileName() {
		return audio.getFileName();
	}
	
	public void script() {
		
		getRunTime().addScript(
				
			new Script() {
				
				public void onActivate() {
					
					if(isPlaying())
						audio.play();
				}
				
				public void onDeactivate() {
					audio.stop();
				}
				
				public void onParentActivate() {
					
					if(isPlaying())
						audio.play();
				}
				
				public void onParentDeactivate() {
					audio.stop();
				}
				
				public void onDestroy() {
					audio.stop();
				}
				
				public void onClose() {
					audio.stop();
				}
			}
		);
	}
}