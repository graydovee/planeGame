package cn.graydove.game;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;

public class Loadfont
{
	public static Font loadFont(String fontFileName, float fontSize)  //��һ���������ⲿ���������ڶ����������С
	{
		try
		{
			File file = new File(fontFileName);
			FileInputStream aixing = new FileInputStream(file);
			Font dynamicFont = Font.createFont(Font.TRUETYPE_FONT, aixing);
			Font dynamicFontPt = dynamicFont.deriveFont(fontSize);
			aixing.close();
			return dynamicFontPt;
		}
		catch(Exception e)//�쳣����
		{
			e.printStackTrace();
			return new java.awt.Font("����", Font.PLAIN, 30);
		}
	}
	public static java.awt.Font Font(int size){
		String root=System.getProperty("user.dir");//��Ŀ��Ŀ¼·��
		Font font = Loadfont.loadFont(root+"/src/font/font.ttf", size);//����
		return font;//��������
	}
}