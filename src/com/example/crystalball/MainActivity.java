package com.example.crystalball;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.crystalball.ShakeDetector.OnShakeListener;

public class MainActivity extends Activity {
	
	public static final String TAG = MainActivity.class.getSimpleName();

	private CrystalBall mCrystalBall = new CrystalBall();
	private TextView mAnswerLabel;
	private ImageView mCrystalBallImage;
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private ShakeDetector mShakeDetector;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mAnswerLabel = (TextView) findViewById(R.id.textView1);
		mCrystalBallImage = (ImageView) findViewById(R.id.imageView1);
		
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mShakeDetector = new ShakeDetector(new OnShakeListener() {
			
			@Override
			public void onShake() {
				handleNewAnswer();
			}
		});
		
//		Toast.makeText(this, "YAY our activity was created!", Toast.LENGTH_LONG).show();
//		Toast welcomeToast = Toast.makeText(this, "Look at me up here", Toast.LENGTH_LONG);
//		welcomeToast.setGravity(Gravity.TOP, 0, 0);
//		welcomeToast.show();
		
		Log.d(TAG, "Logging from the onCreate method");
	}
	
	@Override
	public void onResume(){
		super.onResume();
		mSensorManager.registerListener(mShakeDetector, mAccelerometer, 
				SensorManager.SENSOR_DELAY_UI);
	}
	
	@Override
	public void onPause(){
		super.onPause();
		mSensorManager.unregisterListener(mShakeDetector);
	}
	
	// animate image
	private void animateCrystalBall(){
		mCrystalBallImage.setImageResource(R.drawable.ball_animation);
		AnimationDrawable ballAnimation = (AnimationDrawable) mCrystalBallImage.getDrawable();
		
		if (ballAnimation.isRunning()){
			ballAnimation.stop();
		}
		ballAnimation.start();
	}
	
	// fade-in text
	private void animateAnswer(){
		AlphaAnimation fadeInAnimation = new AlphaAnimation(0, 1);
		fadeInAnimation.setDuration(1500);
		fadeInAnimation.setFillAfter(true);
		
		mAnswerLabel.setAnimation(fadeInAnimation);
	}
	
	// play sound
	private void playSound(){
		MediaPlayer player = MediaPlayer.create(this, R.raw.crystal_ball);
		player.start();
		player.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.release();
			}
		}); 
	}

	private void handleNewAnswer() {
		String answer = mCrystalBall.getAnswer();
		
		// Update label
		mAnswerLabel.setText(answer);
		
		animateCrystalBall();
		animateAnswer();
		playSound();
	}
}
