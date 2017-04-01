package com.example.game;

public class Enemy {
	public	Enemy()
		{
			y=0;
			x=0;
			judge=true;
			width=40;
			length=50;
			num=5;
		}
	public void move()
		{
		}
	public	void stay()
		{
		}
	public	void explode()
		{
		}
	public	void setspeed(int i)
		{
			speed=i;
		}
	public void setjudge(boolean i)
		{
			judge=i;
		}
	public	boolean getjudge()
		{
			return judge;
		}
	public   int getx()
		{
			return x;
		}
	public int gety()
		{
			return y;
		}
	public	int getwidth()
		{
			return width;
		}
	public	int getlength()
		{
			return length;
		}
	public	int getnum()
		{
			return num;
		}
	public	void setnum()
		{
			num=num-1;
		}
	
		boolean judge;
		int y,x;
		int speed;
		int width,length;
		int num;
}
