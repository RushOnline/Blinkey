package mtk.star.a1000;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

public class BlinkeyService extends Service {

	public static final int INTERVAL = 10000; // 10 sec
	public static final int FIRST_RUN = 5000; // 5 seconds
	
	public static final String ACTION_BOOT_COMPLETE = "mtk.star.a1000.blinkey.boot_complete";
	public static final String ACTION_WAKEUP_EVENT = "mtk.star.a1000.blinkey.wakeup_event";
	public static final String ACTION_SCREEN_ON = "mtk.star.a1000.blinkey.screen_on";
	public static final String ACTION_SCREEN_OFF = "mtk.star.a1000.blinkey.screen_off";
	public static final String ACTION_USER_PRESENT = "mtk.star.a1000.blinkey.user_present";
	public static final String ACTION_START = "mtk.star.a1000.blinkey.start";
	public static final String ACTION_STOP = "mtk.star.a1000.blinkey.stop";
	
	int REQUEST_CODE = 11223344;
	
	boolean isUserPresent = false;

	// We need alarm message for background monitoring missed calls and messages
	AlarmManager alarmManager;
	ScreenReceiver screenReceiver;

	@Override
	public void onCreate() {
		super.onCreate();
		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		filter.addAction(Intent.ACTION_USER_PRESENT);

		alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

		screenReceiver = new ScreenReceiver();
		registerReceiver(screenReceiver, filter);
		
		Toast.makeText(this, "Blinkey Service Started.", Toast.LENGTH_LONG).show();
		// scheduleWakeup();
	};

	@Override
	public void onDestroy() {
		cancelWakeup();
		unregisterReceiver(screenReceiver);

	    Toast.makeText(this, "Service Stopped!", Toast.LENGTH_LONG).show();
	    Log.v(this.getClass().getName(), "Service onDestroy(). Stop AlarmManager at " + new java.sql.Timestamp(System.currentTimeMillis()).toString());
		super.onDestroy();
	};

	protected void scheduleWakeup() {
		if (alarmManager != null) {
			Intent intent = new Intent(this, RepeatingAlarmService.class);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(this, REQUEST_CODE, intent, 0);
			
			alarmManager.setRepeating(
			        AlarmManager.ELAPSED_REALTIME_WAKEUP,
			        SystemClock.elapsedRealtime() + FIRST_RUN,
			        INTERVAL,
			        pendingIntent);
		    Log.v(this.getClass().getName(), "AlarmManger started at " + new java.sql.Timestamp(System.currentTimeMillis()).toString());
		}
	}

	protected void cancelWakeup() {
		Intent intent = new Intent(this, RepeatingAlarmService.class);
		alarmManager.cancel(PendingIntent.getBroadcast(this, REQUEST_CODE, intent, 0));
	    Log.v(this.getClass().getName(), "AlarmManger stopped at " + new java.sql.Timestamp(System.currentTimeMillis()).toString());
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent.getAction() == null) {
			Log.v(this.getClass().getName(), "received null intent, ignoring");
		} else
		if (intent.getAction().equals(ACTION_BOOT_COMPLETE)) {
			// TODO: init service
			Log.v(this.getClass().getName(), "initializing service after phone boot complete");
		} else
		if (intent.getAction().equals(ACTION_WAKEUP_EVENT)) {
			// TODO: ask phone for missed calls and messages
			Log.v(this.getClass().getName(), "received wakeup event");
			setBacklight(true);
		} else
		if (intent.getAction().equals(ACTION_SCREEN_ON)) {
			// TODO: attention: on incoming call screen will be on and be off after 
			Log.v(this.getClass().getName(), "screen is on");
			scheduleWakeup();
		} else
		if (intent.getAction().equals(ACTION_SCREEN_OFF)) {
			// TODO: start missed calls and messages monitoring
			Log.v(this.getClass().getName(), "screen is off");
			setBacklight(false);
			cancelWakeup();
		} else
		if (intent.getAction().equals(ACTION_USER_PRESENT)) {
			// TODO: start user present monitoring
			Log.v(this.getClass().getName(), "user present");
			setBacklight(true);
		} else {
			Log.v(this.getClass().getName(), "received unknown intent: " + intent.getAction());
		}
			
		return Service.START_NOT_STICKY;
	}

	static public void setBacklight(boolean on) {
		Log.v("setBacklight", on ? "ON" : "OFF");
    	File brigtness = new File("/sys/class/leds/button-backlight/brightness");
    	try {
    		FileOutputStream out = new FileOutputStream(brigtness);
			try {
				out.write(on? '1' : '0');
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		};
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
