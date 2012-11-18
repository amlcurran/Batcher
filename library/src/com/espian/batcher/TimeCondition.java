package com.espian.batcher;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * Created with IntelliJ IDEA.
 * User: alexcurran
 * Date: 15/11/2012
 * Time: 00:55
 * To change this template use File | Settings | File Templates.
 */
public class TimeCondition extends ShotCondition {

	private int mPeriod = 10000;

	public TimeCondition(int period) {
		mPeriod = period;
	}

	@Override
	public void setUp(Batcher host) {
		super.setUp(host);

		AlarmManager am = (AlarmManager) host.getContext().getSystemService(Context.ALARM_SERVICE);
		am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, 0, mPeriod, PendingIntent.getBroadcast(host.getContext(), 0, new Intent(ACTION_BATCHER_TRIGGER),
				PendingIntent.FLAG_CANCEL_CURRENT));
	}

	@Override
	public void onPause() {
		AlarmManager am = (AlarmManager) mHost.getContext().getSystemService(Context.ALARM_SERVICE);
		am.cancel(PendingIntent.getBroadcast(mHost.getContext(), 0, new Intent(ACTION_BATCHER_TRIGGER),
				PendingIntent.FLAG_CANCEL_CURRENT));
	}

	@Override
	public void onResume() {
		AlarmManager am = (AlarmManager) mHost.getContext().getSystemService(Context.ALARM_SERVICE);
		am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, 0, mPeriod, PendingIntent.getBroadcast(mHost.getContext(), 0, new Intent(ACTION_BATCHER_TRIGGER),
				PendingIntent.FLAG_CANCEL_CURRENT));
	}

	@Override
    protected void shootBroadcast() {
        // Intent is shot with AlarmManager, so this isn't required.
    }

}
