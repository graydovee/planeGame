package cn.graydove.game;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;



public class MyGameFrame extends Frame {

	private static final long serialVersionUID = 8034354118553024187L;
	private boolean restart = true;
	private boolean pause = false;
	private boolean pausePressed = false;
	private boolean running = true;
	
	private Image offScreenImage = null;
	int shellNum = 4;
	int shellIndex = 0;
	int enemySmallNum = 15;
	int enemyMiddleNum = 5;
	int enemyBigNum = 2;
	int bombNum = 3;
	boolean shell_up;
	
	int count = 1;
	int counttime = 100;
	
	static int score;
	
	
	Image bg = GameUtil.getImage("image/background.png");
	Image[] planes = {GameUtil.getImage("image/me1.png"), GameUtil.getImage("image/me2.png")};
	Image bullet1 = GameUtil.getImage("image/bullet1.png");
	Image bullet2 = GameUtil.getImage("image/bullet2.png");
	Image enemy1 = GameUtil.getImage("image/enemy1.png");
	Image enemy2 = GameUtil.getImage("image/enemy2.png");
	Image[] enemy3 = {GameUtil.getImage("image/enemy3_n1.png"), GameUtil.getImage("image/enemy3_n2.png")};
	
	Image[] boom1 = {GameUtil.getImage("image/enemy1_down1.png"),
			GameUtil.getImage("image/enemy1_down2.png"),
			GameUtil.getImage("image/enemy1_down3.png"),
			GameUtil.getImage("image/enemy1_down4.png")};
	
	Image[] boom2 = {GameUtil.getImage("image/enemy2_down1.png"),
			GameUtil.getImage("image/enemy2_down2.png"),
			GameUtil.getImage("image/enemy2_down3.png"),
			GameUtil.getImage("image/enemy2_down4.png")};
	
	Image[] boom3 = {GameUtil.getImage("image/enemy3_down1.png"),
			GameUtil.getImage("image/enemy3_down2.png"),
			GameUtil.getImage("image/enemy3_down3.png"),
			GameUtil.getImage("image/enemy3_down4.png"),
			GameUtil.getImage("image/enemy3_down5.png"),
			GameUtil.getImage("image/enemy3_down6.png")};
	
	Image[] boomMe = {
			GameUtil.getImage("image/me_destroy_1.png"),
			GameUtil.getImage("image/me_destroy_2.png"),
			GameUtil.getImage("image/me_destroy_3.png"),
			GameUtil.getImage("image/me_destroy_4.png")
	};
	
	Image life = GameUtil.getImage("image/life.png");
	
	Image gameOver = GameUtil.getImage("image/gameover.png");
	Image again = GameUtil.getImage("image/again.png");
	Image pause_nor = GameUtil.getImage("image/pause_nor.png");
	Image pause_pressed = GameUtil.getImage("image/pause_pressed.png");
	Image resume_nor  = GameUtil.getImage("image/resume_nor.png");
	Image resume_pressed = GameUtil.getImage("image/resume_pressed.png");
	Image bomb = GameUtil.getImage("image/bomb.png");
	Image suppprt_shell_img = GameUtil.getImage("image/bullet_supply.png");
	Image support_bomb_img = GameUtil.getImage("image/bomb_supply.png");
	
	GameObject GameOver = new GameObject(gameOver, (Constant.GAME_WIDTH - gameOver.getWidth(null))/2, Constant.GAME_HEIGHT/3 + 175);
	GameObject Again = new GameObject(again, (Constant.GAME_WIDTH - gameOver.getWidth(null))/2, Constant.GAME_HEIGHT/3 + 75);
	GameObject PauseN = new GameObject(pause_nor, Constant.GAME_WIDTH - pause_nor.getWidth(null) - 20, 50);
	GameObject PauseY = new GameObject(pause_pressed, Constant.GAME_WIDTH - pause_pressed.getWidth(null) - 20, 50);
	GameObject ResumeN = new GameObject(resume_nor, Constant.GAME_WIDTH - resume_nor.getWidth(null) - 20, 50);
	GameObject ResumeY = new GameObject(resume_pressed, Constant.GAME_WIDTH - resume_pressed.getWidth(null) - 20, 50);
	
	
	Plane plane = new Plane(planes,(bg.getWidth(null)-planes[0].getWidth(null))/2,bg.getHeight(null)-planes[0].getHeight(null)-100,5);
	Shell[] shell = new Shell[shellNum];
	Shell[] shellUp = new Shell[shellNum*2];
	Enemy[] enemySmall = new Enemy[enemySmallNum]; 
	Enemy[] enemyMiddle = new Enemy[enemyMiddleNum];
	Enemy[] enemyBig = new Enemy[enemyBigNum];
	
	Music bg_music = new Music("sound/game_music.wav");
	Thread BgMusicThread = new Thread(new MusicThread(bg_music, true));
	
	Support [] support = {new Support(suppprt_shell_img), new Support(support_bomb_img)};
	
	
	//双缓冲
	public void update(Graphics g) {
		if(offScreenImage == null)
			offScreenImage = this.createImage(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
		
		Graphics gOff = offScreenImage.getGraphics();
		paint(gOff);
		g.drawImage(offScreenImage, 0, 0, null);
	}
	
	//画图像
	@Override
	public void paint(Graphics g) {
		g.drawImage(bg, 0, 0, null);

		if(restart && !pause)//绘制游戏画面
		{
			if(count == 30) {
				support[(int)(Math.random()*2)].reset();
				count = 1;
			}
			
			/*碰撞检测*/
			Constant.collision(plane, enemyBig, new Thread(new MusicThread(new Music("sound/me_down.wav"), false)));
			Constant.collision(plane, enemyMiddle, new Thread(new MusicThread(new Music("sound/me_down.wav"), false)));
			Constant.collision(plane, enemySmall, new Thread(new MusicThread(new Music("sound/me_down.wav"), false)));
			
			for(int i =0;i<2;++i) {
				if(support[i].rective) {
					support[i].drawSelf(g);
				}
			}
			
			if(plane.getRect().intersects(support[0].getRect()) && support[0].rective) {
				shell_up = true;
				counttime = 0;
				support[0].rective = false;
				new Thread(new MusicThread(new Music("sound/supply.wav"), false)).start();
			}
			else if(plane.getRect().intersects(support[1].getRect()) && support[1].rective) {
				if(bombNum<3) {
					bombNum+=1;
				}
				new Thread(new MusicThread(new Music("sound/supply.wav"), false)).start();
				support[1].rective=false;
			}
			
			
			
			//自己飞机
			if(plane.rective)
				plane.drawSelf(g, true);
			else {
				plane.reset((bg.getWidth(null)-planes[0].getWidth(null))/2,bg.getHeight(null)-planes[0].getHeight(null)-100);
				shell_up = false;
			}
			
			
			
	
			if(plane.lives == 0)
				restart = false;
		
		
			//子弹
			if(counttime>17)
				shell_up = false;
			if(shell_up) {
				for(int i=0;i<shellUp.length;++i) {
					if(shellUp[i]!=null) {
						if(shellUp[i].rective) {
							Constant.collision(shellUp[i], enemySmall, new Thread(new MusicThread(new Music("sound/enemy1_down.wav"), false)));
							Constant.collision(shellUp[i], enemyMiddle, new Thread(new MusicThread(new Music("sound/enemy2_down.wav"), false)));
							Constant.collision(shellUp[i], enemyBig, new Thread(new MusicThread(new Music("sound/enemy3_down.wav"), false)));
						}
					}
				}
				for(int i=0;i<shellUp.length;++i) {
					if(shellUp[i]!=null) {
						if(shellUp[i].rective) {
							shellUp[i].drawSelf(g);
						}
					}
				}
			}else {
				for(int i=0;i<shell.length;++i) {
					if(shell[i]!=null) {
						if(shell[i].rective) {
							Constant.collision(shell[i], enemySmall, new Thread(new MusicThread(new Music("sound/enemy1_down.wav"), false)));
							Constant.collision(shell[i], enemyMiddle, new Thread(new MusicThread(new Music("sound/enemy2_down.wav"), false)));
							Constant.collision(shell[i], enemyBig, new Thread(new MusicThread(new Music("sound/enemy3_down.wav"), false)));
						}
					}
				}
				for(int i=0; i<shell.length;++i) {
					if(shell[i] != null) {
						if(shell[i].rective) {
							shell[i].drawSelf(g);
							
						}
					}
				}
			}
			
			//大飞机
			for(int i =0;i<enemyBig.length;++i) {
				if(enemyBig[i]!=null) {
					if(enemyBig[i].rective) {
						enemyBig[i].drawSelf(g,true);
					}
					else {
						enemyBig[i].reset(Constant.getRandX(enemy3[0]), Constant.getRanY(5, 15));
						score += 5000;
					}
				}
			}
			
			//中飞机
			for(int i=0;i<enemyMiddle.length;++i) {
				if(enemyMiddle[i]!=null) {
					if(enemyMiddle[i].rective) {
						enemyMiddle[i].drawSelf(g);
					}
					else {
						enemyMiddle[i].reset(Constant.getRandX(enemy1), Constant.getRanY(0, 10));
						score += 2000;
					}
				}
			}
			
			//小飞机
			for(int i=0;i<enemySmall.length;++i) {
				if(enemySmall[i] != null ) {
					if(enemySmall[i].rective) {
						enemySmall[i].drawSelf(g);
					}
					else {
						enemySmall[i].reset(Constant.getRandX(enemy1), Constant.getRanY(0, 5));
						score += 1000;
					}
				}
			}
			
			for(int i=0;i<plane.lives;++i) {
				g.drawImage(life, Constant.GAME_WIDTH - ((life.getWidth(null)+10)*(i+1)), Constant.GAME_HEIGHT - 90, null);
			}
			
			g.drawImage(bomb, 30, Constant.GAME_HEIGHT-bomb.getHeight(null)-35, null);
			GameUtil.drawString(g, "×"+bombNum, 40 +bomb.getWidth(null) , Constant.GAME_HEIGHT-45, 50);

			if(pausePressed) {
				PauseY.drawSelf(g);
			}
			else {
				PauseN.drawSelf(g);
			}
			
		}
		else if(!pause){   //绘制结束画面
			GameUtil.drawString(g, "Game over", Constant.GAME_WIDTH/2 - 120,  Constant.GAME_HEIGHT/3, 50);
			GameOver.drawSelf(g);
			Again.drawSelf(g);
			running = false;
		}
		else {
			if(pausePressed) {
				ResumeY.drawSelf(g);
			}
			else {
				ResumeN.drawSelf(g);
			}
		}
		GameUtil.drawString(g, "Score: "+score, 20, 70, 30);
			
	}
	
	//窗口加载
	public void lunchFrame() {
		this.setTitle("AircriftWar");
		this.setVisible(true);
		this.setSize(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
		this.setLocation(300, 300);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		
		plane.speed=10;
		plane.setBoom(boomMe);
		
		//子弹初始化
		for(int i = 0; i<shell.length; ++i) {
			shell[i] = new Shell(bullet1,20);
		}
		
		for(int i = 0; i<shellUp.length; ++i) {
			shellUp[i] = new Shell(bullet2,20);
		}
		
		//小飞机初始化
		for(int i=0;i<enemySmall.length;++i)
		{
			enemySmall[i] = new Enemy(enemy1, 3, 1);
			enemySmall[i].setBoom(boom1);
		}
		
		//中飞机初始化
		for(int i=0;i<enemyMiddle.length;++i)
		{
			enemyMiddle[i] = new Enemy(enemy2,2, 6);
			enemyMiddle[i].setBoom(boom2);
		}
		
		//大飞机初始化
		for(int i=0;i<enemyBig.length;++i)
		{
			enemyBig[i] = new Enemy(enemy3, 2, 15);
			enemyBig[i].setBoom(boom3);
		}
		
		enemyLoad();
		
		new PaintThread(this).start();
		new ShellThread().start();
		new TimeThread().start();
		//BgMusicThread.start();
		bg_music.loop();

		
		addKeyListener(new KeyMonitor());
		addMouseListener(new MouseMonitor());
	}
	
	//图像刷新线程
	class PaintThread extends Thread{
		MyGameFrame f;
		PaintThread(MyGameFrame f){
			this.f = f;
		}
		@Override
		public void run() {
			while(true) {
				repaint();
				PointerInfo pinfo = MouseInfo.getPointerInfo();
				Point p = pinfo.getLocation();
				double X = p.x-f.getX();
				double Y = p.y-f.getY();
				if(GameUtil.inBorder(PauseN, (int)X, (int)Y)) {
					pausePressed = true;
				}
				else {
					pausePressed = false;
				}
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	//子弹线程
	class ShellThread extends Thread{
		@Override
		
		public void run() {

			int cnt = 0;
			while(true) {
				cnt = (cnt+1)%2;
				if(cnt==0) {
				}
				if(shell_up) {
					shellUp[shellIndex].reset(plane.x+19, plane.y+plane.height/2-3);
					shellUp[shellIndex+shellNum].reset(plane.x+plane.width-25, plane.y+plane.height/2-8);
					shellIndex = (shellIndex + 1)%shellNum;
				}else {
					shell[shellIndex].reset(plane.x + plane.width/2, plane.y);
					shellIndex = (shellIndex + 1)%shellNum;
				}

				if(running && !pause) {
					new Thread(new MusicThread(new Music("sound/bullet.wav"), false)).start();
				}
				try {
					Thread.sleep(150);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	class TimeThread extends Thread{

		@Override
		public void run() {
			while(true) {
				++count; 
				if(counttime<18)
					++counttime;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	
	//键盘事件
	class KeyMonitor extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e) {
			plane.addDirection(e);
			if(e.getKeyCode()==KeyEvent.VK_SPACE) {
				if(bombNum>0) {
					new Thread(new MusicThread(new Music("sound/use_bomb.wav"), false)).start();
					--bombNum;
					for(int i =0;i<enemyBig.length;++i) {
						if(enemyBig[i].y>0) {
							enemyBig[i].boom = true;
						}
					}
					
					for(int i=0;i<enemyMiddle.length;++i) {
						if(enemyMiddle[i].y>0) {
							enemyMiddle[i].boom = true;
						}
					}
					
					for(int i=0;i<enemySmall.length;++i) {
						if(enemySmall[i].y>0) {
							enemySmall[i].boom = true;
						}
					}
				}
			}
		}


		@Override
		public void keyReleased(KeyEvent e) {
			plane.minusDirection(e);
		}
		
	}
	
	class MouseMonitor extends MouseAdapter{

		public void mouseReleased(MouseEvent e) {
			int X = e.getX();
			int Y = e.getY();
			if(!restart) {
				if(GameUtil.inBorder(Again, X, Y)) {

					new Thread(new MusicThread(new Music("sound/button.wav"), false)).start();
					enemyLoad();
					shell_up = false;
					plane.lives = 3;
					restart = true;
					count = 1;
					running = true;

				}

				if(GameUtil.inBorder(GameOver, X, Y)) {

					new Thread(new MusicThread(new Music("sound/button.wav"), false)).start();
					bg_music.stop();
					System.exit(0);
				}
				
			}
			
			if(GameUtil.inBorder(PauseN, X, Y)) {

				new Thread(new MusicThread(new Music("sound/button.wav"), false)).start();
				pause = !pause;
			}
			
		}

	}
	
	
	public static void main(String[] args) {
		MyGameFrame f = new MyGameFrame();
		f.lunchFrame();
	}
	
	private void enemyLoad() {

		for(int i =0;i<enemyBig.length;++i) {
			enemyBig[i].reset(Constant.getRandX(enemy3[0]), Constant.getRanY(5, 15));
		}
		
		for(int i=0;i<enemyMiddle.length;++i) {
			enemyMiddle[i].reset(Constant.getRandX(enemy1), Constant.getRanY(0, 10));
		}
		
		for(int i=0;i<enemySmall.length;++i) {
			enemySmall[i].reset(Constant.getRandX(enemy1), Constant.getRanY(0, 5));
		}
	}
	
	
}
