package cn.graydove.game;

import java.awt.Graphics;
import java.awt.Image;

public class Support extends GameObject{

	@Override
	public void drawSelf(Graphics g) {
		g.drawImage(img, (int)x, (int)y, null);
		this.y += speed;
	}
	
	
	
	public void reset() {
		this.x = Constant.getRandX(img);
		this.y = -10;
		this.rective = true;
	}



	Support(Image img){
		this.img = img;
		this.width = img.getWidth(null);
		this.height = img.getHeight(null);
		this.rective = false;
		this.speed = 12;
	}
	
	
}
