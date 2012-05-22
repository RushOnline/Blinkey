package mtk.star.a1000;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class OnBootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent i = new Intent(context, BlinkeyService.class);
		Log.v(this.getClass().getName(), "received intent action: " + intent.getAction());

		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
			i.setAction(BlinkeyService.ACTION_BOOT_COMPLETE);
		}

		if (i.getAction() != null) context.startService(i);
	}
}
