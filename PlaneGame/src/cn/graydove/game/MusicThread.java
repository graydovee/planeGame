package cn.graydove.game;

public class MusicThread implements Runnable {

	private Music music;
	boolean isLoop;
	int repeat;
	
	@Override
	public void run() {
		if(music != null) {
			if(isLoop) {
				music.loop();
			}
			else if(repeat == 0){
				music.play();
			}
			else {
				for(int i = 0; i<repeat; ++i) {
					music.play();
				}
			}
		}
	}
	
	public MusicThread(Music music, boolean isLoop){
		this.music = music;
		this.isLoop = isLoop;
	}
	
	public MusicThread(Music music, int repeat){
		this.music = music;
		this.repeat = repeat;
	}
	
	public MusicThread(){
		this.music = null;
	}
	
}
