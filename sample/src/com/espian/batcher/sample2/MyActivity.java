package com.espian.batcher.sample2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import com.espian.batcher.CacheCountCondition;

public class MyActivity extends Activity implements View.OnClickListener {

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
		batcher.attachCondition(new CacheCountCondition(5), true);

		findViewById(R.id.button).setOnClickListener(this);
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

	@Override
	public void onClick(View view) {
		batcher.addInputs("Hi");
	}

	@Override
	public Dialog onCreateDialog(int id) {
		AlertDialog.Builder build = new AlertDialog.Builder(this);
		build.setMessage("Batcher has been triggered!").setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				dialogInterface.dismiss();
			}
		});
		return build.create();
	}

}
