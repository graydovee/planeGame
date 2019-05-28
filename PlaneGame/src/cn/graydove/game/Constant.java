package cn.graydove.game;

import java.awt.Image;

public final class Constant {
	
	private Constant() {
		
	}
	
	public static final int GAME_HEIGHT = 700;
	public static final int GAME_WIDTH = 480;
	public static boolean bg_play;
	
	
	public static double getRandX(Image img) {
		return Math.random()*(GAME_WIDTH - img.getWidth(null) - 20) + 10;
	}
	
	public static double getRanY(int a, int b) {
		return (-a-(Math.random()*5))*GAME_HEIGHT;
	}
	
	public static void collision(GameObject element, GameObject[] array, Thread thread) {
		 for(int i=0;i<array.length;++i) {
			 if(array[i] != null &&array[i].rective&&!array[i].boom) {
				 if(element.getRect().intersects(array[i].getRect())) {
					 --array[i].hp;
					 if(array[i].hp<=0) {
						 array[i].boom = true;
						 if(thread!=null) {
							 if(!thread.isAlive()) {
								 thread.start();
							 }
						 }
					 }
					 --element.hp;
					 if(element.hp <= 0) {
						 element.boom = true;
					 }
				 }
			 }
		 }
	}
	
}

