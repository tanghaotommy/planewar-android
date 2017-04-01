package com.example.game;

import java.util.Vector;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View; 

public class MainActivity extends Activity {
	private int id=0;
	private float mX=0,mY=0;
	Boat MyBoat;
	Bitmap BoatImg;
	Bitmap Enemy1Img;
	Bitmap Enemy2Img;
	Bitmap Enemy3Img;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id=1;
        setContentView(R.layout.activity_main);
    }
    
    	
    class MyView extends SurfaceView implements SurfaceHolder.Callback{
    	int screenWidth,screenHeight;
    	int Enemy1Width,Enemy1Height;
    	int Enemy2Width,Enemy2Height;
    	int Enemy3Width,Enemy3Height;
    	int BoatWidth,BoatHeight;
    	float xRatio,yRatio;
    	int bulletappearrate=5;
    	int enemy1appearrate=15;
    	int enemy2appearrate=40;
    	int enemy3appearrate=100;
    	int enemy1speed=5;
    	int enemy2speed=3;
    	int enemy3speed=2;
    	Vector <Bullet> bullet = new Vector<Bullet>();  
    	
    	
    	boolean IsRunning=false;
    	private SurfaceHolder holder;
    	private Paint paint;


    	public MyView(Context context){
    		super(context);
    		holder = this.getHolder();
    		holder.addCallback(this);
    		paint = new Paint();
    	}


		@Override
		public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void surfaceCreated(SurfaceHolder arg0) {
			// TODO Auto-generated method stub	
			//设定参数
			screenWidth=this.getWidth();
			screenHeight=this.getHeight();
			xRatio=(float) (screenWidth/540.0);
			yRatio=(float) (screenHeight/788.0);
			Bitmap _BoatImg=BitmapFactory.decodeResource(getResources(),R.drawable.boat);
			Bitmap _Enemy1Img=BitmapFactory.decodeResource(getResources(),R.drawable.enemy1);
			Bitmap _Enemy2Img=BitmapFactory.decodeResource(getResources(),R.drawable.enemy2);
			Bitmap _Enemy3Img=BitmapFactory.decodeResource(getResources(),R.drawable.enemy3);
			Matrix matrix = new Matrix();
			matrix.postScale(xRatio, yRatio);// 缩放原图
			Enemy1Width=(int) (_Enemy1Img.getWidth()*xRatio);Enemy1Height=(int) (_Enemy1Img.getHeight()*yRatio);
			Enemy2Width=(int) (_Enemy2Img.getWidth()*xRatio);Enemy2Height=(int) (_Enemy2Img.getHeight()*yRatio);
			Enemy3Width=(int) (_Enemy3Img.getWidth()*xRatio);Enemy3Height=(int) (_Enemy3Img.getHeight()*yRatio);
     		BoatWidth=(int) (_BoatImg.getWidth()*xRatio);BoatHeight=(int) (_BoatImg.getHeight()*yRatio);
			Enemy1Img=Bitmap.createBitmap(_Enemy1Img, 0, 0, _Enemy1Img.getWidth(), _Enemy1Img.getHeight(),matrix, true);
			Enemy2Img=Bitmap.createBitmap(_Enemy2Img, 0, 0, _Enemy2Img.getWidth(), _Enemy2Img.getHeight(),matrix, true);
			Enemy3Img=Bitmap.createBitmap(_Enemy3Img, 0, 0, _Enemy3Img.getWidth(), _Enemy3Img.getHeight(),matrix, true);
			BoatImg=Bitmap.createBitmap(_BoatImg, 0, 0, _BoatImg.getWidth(),_BoatImg.getHeight(),matrix,true);
			//BoatWidth=40;BoatHeight=50;
			//BoatImg=BitmapFactory.decodeResource(getResources(),R.drawable.boat);
			MyBoat = new Boat(250,600,BoatImg,BoatWidth,BoatHeight);
			IsRunning=true;
	        new Thread(new MyThread()).start();
		}
		
		@Override
		public void surfaceDestroyed(SurfaceHolder arg0) {
			IsRunning=false;
			// TODO Auto-generated method stub
			
		}
		
		public boolean onTouchEvent(MotionEvent event) { 
	        mX = event.getX(); // 获得坐标
	        mY = event.getY(); 
	        MyBoat.setx(mX);
	        MyBoat.sety(mY);
	        return true; 
	    }
		
		public void drawbackground(Canvas canvas){
			paint.setColor(Color.BLACK);
			canvas.drawRect(0, 0, 540*xRatio, 900*yRatio, paint);
		}
		
		public void drawMyBoat(Canvas canvas){
			MyBoat.draw(canvas);
			
		}
		
		public void drawbullet(Canvas canvas){
			paint.setColor(Color.WHITE);
			for(int i=0;i<bullet.size();i++){
				canvas.drawCircle(bullet.get(i).getx(), bullet.get(i).gety(), 5, paint);
				bullet.get(i).move();
			}
		}
		
		class MyThread implements Runnable{
			@Override
			public void run(){
				int countbullet=bulletappearrate;
				id=2;
				while(IsRunning){
					Canvas canvas=holder.lockCanvas();
		            drawbackground(canvas);
					paint.setColor(Color.WHITE);
					canvas.drawRect(50, 50, 100, 100, paint);
					if(countbullet==bulletappearrate){
						bullet.add(new Bullet(mX,mY-MyBoat.getheight()/2));
					}
					drawMyBoat(canvas);
					drawbullet(canvas);
					if(mX>50 && mX<100 && mY>50 && mY<100){
						runOnUiThread(new Runnable()    
					    {    
							public void run()    
					        {
					            id=1;
					            setContentView(R.layout.activity_main);//返回开始界面
					        }
					     });
				     }
					holder.unlockCanvasAndPost(canvas);
					try {
						Thread.sleep(20);
						countbullet++;
						if(countbullet>bulletappearrate){
							countbullet=0;
						}
					} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
					  }
				}
			}
		}
    }
    
    public boolean onKeyDown(int keyCode, KeyEvent event) {         

    	if(keyCode == KeyEvent.KEYCODE_BACK && id==2){
    		id=1;
         	setContentView(R.layout.activity_main);
         	return true;
    	 }
    	if(keyCode == KeyEvent.KEYCODE_BACK && id==1){
    		java.lang.System.exit(0);
         	return true;
    	 }
    	return super.onKeyDown(keyCode, event);
     }

    
    public void Click(View v){
    	setContentView(new MyView(this));
    }
    
}
