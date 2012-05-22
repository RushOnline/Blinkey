package mtk.star.a1000;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


public class RepeatingAlarmService extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
	    Log.v(this.getClass().getName(), context.getClass().getName() + "Timed alarm onReceive() started at time: " + new java.sql.Timestamp(System.currentTimeMillis()).toString());
		Intent i = new Intent(context, BlinkeyService.class);
		i.setAction(BlinkeyService.ACTION_WAKEUP_EVENT);
		context.startService(i);
	}

}
