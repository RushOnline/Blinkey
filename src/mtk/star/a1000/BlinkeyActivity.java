package mtk.star.a1000;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
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
        toggleBox.setOnCheckedChangeListener(this);
    }

	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    	File brigtness = new File("/sys/class/leds/button-backlight/brightness");
    	try {
    		FileOutputStream out = new FileOutputStream(brigtness);
			try {
				out.write(isChecked? '1' : '0');
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}