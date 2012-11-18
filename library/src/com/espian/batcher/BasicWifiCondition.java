package com.espian.batcher;

import android.content.Context;
import android.net.wifi.WifiManager;

/**
 * Created with IntelliJ IDEA.
 * User: alexcurran
 * Date: 18/11/2012
 * Time: 13:39
 * To change this template use File | Settings | File Templates.
 */
public class BasicWifiCondition extends SwitchCondition {

	private WifiManager mManager;

	@Override
	public boolean isActive() {
		return mManager.isWifiEnabled(); // Poor implementation - instead check for connection state
	}

	@Override
	public void onPause() {
		mManager = null;
	}

	@Override
	public void onResume() {
		mManager = (WifiManager) mHost.getContext().getSystemService(Context.WIFI_SERVICE);
	}
}
