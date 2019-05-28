package cn.graydove.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class GameUtil {
	private GameUtil() {
		
	}
	
	public static Image getImage(String path) {
		BufferedImage bi = null;
		try {
			URL u = GameUtil.class.getClassLoader().getResource(path);
			bi = ImageIO.read(u);
		}catch (IOException e) {
			e.printStackTrace();
		}
		return bi;
	}

	public static void drawString(Graphics g, String str, int x, int y, int size) {
		Font f = g.getFont();
		Color c = g.getColor();
		g.setColor(Color.white);
		g.setFont(Loadfont.Font(size));
		g.drawString(str, x, y);
		g.setColor(c);
		g.setFont(f);
	}
	
	public static boolean inBorder(GameObject img, int X, int Y) {
		return (X > img.x && X < img.x + img.width && Y >img.y && Y < img.y + img.height);
	}
}
