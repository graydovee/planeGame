package cn.graydove.game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

public class Plane extends GameObject{
	
	boolean down,up,left,right;
	int lives;
	
	@Override
	public void drawSelf(Graphics g, boolean mutiImg) {
		
		if(left){
			if(x-speed>0)
				x-=speed;
		}
		if(right) {
			if(x+speed<Constant.GAME_WIDTH-this.width)
				x+=speed;
		}
		if(up) {
			if(y-speed>30)
				y-=speed;
		}
		if(down) {
			if(y+speed<Constant.GAME_HEIGHT-this.height-100)
				y+=speed;
		}
		super.drawSelf(g, mutiImg);
	}
	
	public Plane(Image[] imgs,double x,double y,int speed) {
		super();
		this.imgs=imgs;
		this.x=x;
		this.y=y;
		this.speed=speed;
		this.width=this.imgs[0].getWidth(null);
		this.height=this.imgs[0].getHeight(null);
		this.lives = -10086;
	}
	
	public void addDirection(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_A:
			left = true;
			break;
		case KeyEvent.VK_UP:
		case KeyEvent.VK_W:
			up = true;
			break;
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_D:
			right = true;
			break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_S:
			down = true;
			break;
		default:
			break;
		}
	}
	
	public void minusDirection(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_A:
			left = false;
			break;
		case KeyEvent.VK_UP:
		case KeyEvent.VK_W:
			up = false;
			break;
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_D:
			right = false;
			break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_S:
			down = false;
			break;
		default:
			break;
		}
	}

	@Override
	public void reset(double x, double y) {
		super.reset(x, y);
		if(this.lives == -10086)
			this.lives = 3;
		else
			--this.lives;
	}
	
	

}
