package cn.graydove.game;

import java.awt.Graphics;
import java.awt.Image;

public class Shell extends GameObject{
	@Override
	public void drawSelf(Graphics g) {
		this.y-=speed;
		if(this.y < 25)
			this.rective = false;
		super.drawSelf(g);
	}
	

	public Shell(Image img, int speed, double x, double y) {
		super();
		this.img = img;
		this.speed = speed;
		this.x = x;
		this.y = y;
	}
	public Shell(Image img, int speed) {
		super();
		this.img = img;
		this.speed = speed;
		this.width = img.getWidth(null);
		this.height = img.getHeight(null);
	}
	
	public void setXY(double x, double y) {
		this.x=x;
		this.y=y;
	}


	@Override
	public void reset(double x, double y) {
		super.reset(x, y);
	}
	
	
}
