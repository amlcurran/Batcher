package com.espian.batcher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

/**
 * Created with IntelliJ IDEA.
 * User: alexcurran
 * Date: 18/11/2012
 * Time: 13:56
 * To change this template use File | Settings | File Templates.
 */
public class HighBatteryCondition extends SwitchCondition {

	float cutOff = 0.2f; // Default cut-off is 15% remaining, when the battery goes "red".
	BroadcastReceiver rec;
	boolean mIsActive = false;

   	public HighBatteryCondition(float cutOffFraction) {
		super();
		if (cutOffFraction <= 1 || cutOffFraction > 0)
			cutOff = cutOffFraction;
		else throw new IllegalArgumentException("Incorrect battery cutoff supplied - this must be a decimal between 0 and 1.");
	}

	@Override
	public boolean isActive() {
		return mIsActive;
	}

	@Override
	public void onPause() {
		mHost.getContext().unregisterReceiver(rec);
	}

	@Override
	public void onResume() {
		IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		if (rec == null)
			rec = new InternalBatteryReceiver();
		mHost.getContext().registerReceiver(rec, filter);
	}

	public class InternalBatteryReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
			int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
			float result = ((Number) level).floatValue() / ((Number) scale).floatValue();
			mIsActive = result >= cutOff;
		}
	}
}
