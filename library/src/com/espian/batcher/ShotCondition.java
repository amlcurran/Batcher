package com.espian.batcher;

import android.app.PendingIntent;
import android.content.Intent;

/**
 * Created with IntelliJ IDEA.
 * User: alexcurran
 * Date: 18/11/2012
 * Time: 13:13
 * To change this template use File | Settings | File Templates.
 */
public abstract class ShotCondition extends Condition {

	PendingIntent mShootIntent;

	@Override
	public void setUp(Batcher host) {
		super.setUp(host);
		mShootIntent = PendingIntent.getBroadcast(host.getContext(), 0, new Intent(ACTION_BATCHER_TRIGGER),
				PendingIntent.FLAG_CANCEL_CURRENT);
	}

	public PendingIntent getShotIntent() {
		return mShootIntent;
	}

	public void forceTrigger() {
		shootBroadcast();
	}

	protected abstract void shootBroadcast();

}
