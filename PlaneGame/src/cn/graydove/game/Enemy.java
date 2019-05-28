package cn.graydove.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

public class Enemy extends GameObject{
	@Override
	public void drawSelf(Graphics g) {
		drawHpLine(g);
		this.y += this.speed;
		if(this.y > Constant.GAME_HEIGHT)
			this.rective = false;
		super.drawSelf(g);
	}
	
	public void drawSelf(Graphics g, boolean mutiImg) {
		drawHpLine(g);
		this.y += this.speed;
		if(this.y > Constant.GAME_HEIGHT)
			this.rective = false;
		if(mutiImg) {
			super.drawSelf(g, mutiImg);
		}
	}
	
	public void drawHpLine(Graphics g) {
		if(maxHp>2) {
			Graphics2D g2 = (Graphics2D)g;
			g2.setStroke(new BasicStroke(2.0f));
			double hpLenth = (double)hp/maxHp;
			if(hpLenth>0.34) {
				g2.setColor(Color.green);
			}
			else {
				g2.setColor(Color.red);
			}
			if(hpLenth>0) {
				g2.drawLine((int)x, (int)y, (int)(x + width*hpLenth), (int)y);
			}
		}
	}
	
	
	public Enemy(Image img, int speed, int maxHp) {
		this.maxHp = maxHp;
		this.img = img;
		this.speed = speed;
		this.rective = false;
		this.width = img.getWidth(null);
		this.height = img.getHeight(null);
	}
	
	public Enemy(Image imgs[], int speed, int maxHp) {
		this.maxHp = maxHp;
		this.imgs = imgs;
		this.speed = speed;
		this.rective = false;
		this.width = imgs[0].getWidth(null);
		this.height = imgs[0].getHeight(null);
	}
	
	
}
