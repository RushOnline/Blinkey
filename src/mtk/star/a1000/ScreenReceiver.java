package mtk.star.a1000;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ScreenReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent i = new Intent(context, BlinkeyService.class);
		Log.v(this.getClass().getName(), "received intent action: " + intent.getAction());
		
		if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
			i.setAction(BlinkeyService.ACTION_SCREEN_ON);
		} else
		if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
			i.setAction(BlinkeyService.ACTION_SCREEN_OFF);
		} else
		if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {
			i.setAction(BlinkeyService.ACTION_USER_PRESENT);
		}

		if (i.getAction() != null) context.startService(i);

	}

}
