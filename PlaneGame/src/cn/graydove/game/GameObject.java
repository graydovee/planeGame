package cn.graydove.game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class GameObject {
	Image img;
	Image[] imgs;
	double x,y;
	int speed;
	int width,height;
	boolean rective;
	int count;
	Image[] boomImg;
	boolean hasBoom;
	boolean boom;
	int boomCount;
	int maxHp;
	int hp;
	
	public void boom(Graphics g) {
		if(hasBoom) {
			g.drawImage(boomImg[boomCount/3], (int)x, (int)y, null);
			++boomCount;
			if(boomCount>=(boomImg.length*3)) {
				this.rective = false;
				boomCount = 0;
			}
		}
		else {
			this.rective = false;
		}
	}
	
	public void drawSelf(Graphics g) {
		if(!boom) {
			g.drawImage(img, (int)x, (int)y, null);
		}
		else {
			boom(g);
		}
	}
	
	public void drawSelf(Graphics g, boolean mutiImg) {
		if(!boom) {
			count = (count + 1)%(imgs.length*5);
			g.drawImage(imgs[count/5], (int)x, (int)y, null);
		}
		else {
			boom(g);
		}
	}
	
	
	public GameObject(Image img, double x, double y, int speed, int width, int height) {
		super();
		this.img = img;
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.width = width;
		this.height = height;
	}
	
	public GameObject(Image img, double x, double y, int speed) {
		super();
		this.img = img;
		this.x = x;
		this.y = y;
		this.speed = speed;
	}
	
	public GameObject(Image img, double x, double y) {
		super();
		this.img = img;
		this.x = x;
		this.y = y;
		this.width = img.getWidth(null);
		this.height = img.getHeight(null);
	}
	
	public GameObject() {
		this.maxHp = 1;
	}
	
	public Rectangle getRect() {
		return new Rectangle((int)x, (int)y, width, height);
	}
	
	public void setBoom(Image boomImg[]) {
		this.boomImg = boomImg;
		this.hasBoom = true;
	}

	public void reset(double x, double y) {
		this.x = x;
		this.y = y;
		this.rective = true;
		this.boom = false;
		this.hp = this.maxHp;
	}
}
