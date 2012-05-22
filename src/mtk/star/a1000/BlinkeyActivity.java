package mtk.star.a1000;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class BlinkeyActivity extends Activity implements OnCheckedChangeListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final CheckBox toggleBox = (CheckBox) findViewById(R.id.toggleBacklight);
        final CheckBox toggleSvc = (CheckBox) findViewById(R.id.toggleServiceRun);
        toggleBox.setOnCheckedChangeListener(this);
        toggleSvc.setOnCheckedChangeListener(this);
    }

    /*
    @Override
    public void onBackPressed() {
		Log.v(this.getClass().getName(), "back pressed");
    	this.finish();
    	super.onBackPressed();
    }
    */
    
	public void onCheckedChanged(CompoundButton src, boolean isChecked) {
		switch (src.getId()) {
		case R.id.toggleBacklight:
			Log.v(this.getClass().getName(), "toggling backlight");
			BlinkeyService.setBacklight(isChecked);
			break;
		case R.id.toggleServiceRun:
			Intent i = new Intent(this, BlinkeyService.class);
			i.setAction(isChecked? BlinkeyService.ACTION_START : BlinkeyService.ACTION_STOP);
			if (isChecked) {
				Log.v(this.getClass().getName(), "start service");
				startService(i);
			} else {
				Log.v(this.getClass().getName(), "stop service");
				stopService(i);
			}
			break;
		}
	}
}