package com.espian.batcher.sample2;

import android.app.Activity;
import android.os.Bundle;
import com.espian.batcher.HighBatteryCondition;
import com.espian.batcher.TimeCondition;

public class MyActivity extends Activity {

	MyBatcher batcher;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//TODO turn batcher off when not in foreground
		batcher = new MyBatcher(this);
		batcher.attachConditions(new TimeCondition(5000), new HighBatteryCondition(0.3f), true);
	}

	@Override
	public void onPause() {
		super.onPause();
		batcher.pause();
	}

	@Override
	public void onResume() {
		super.onResume();
		batcher.resume();
	}

}
