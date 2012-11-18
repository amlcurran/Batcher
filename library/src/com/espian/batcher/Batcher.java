package com.espian.batcher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alex Curran
 * Date: 15/11/2012
 * Time: 00:06
 * To change this template use File | Settings | File Templates.
 */
public abstract class Batcher<Inputs> {

	private Context mContext;
	protected ArrayList<Inputs> mInputList = new ArrayList<Inputs>();
	private ShotCondition mShootah;
	private SwitchCondition mSwitchah;
	private InternalBroadcastRec mRec;

	private static IntentFilter sFilter;

	static {
		sFilter = new IntentFilter();
		sFilter.addAction(Condition.ACTION_BATCHER_TRIGGER);
	}

	public Batcher(Context c) {
		mContext = c;
		mRec = new InternalBroadcastRec();
	}

	public void attachCondition(ShotCondition condition, boolean forceTrigger) {

		if (condition == null)
			throw new NullPointerException("An attached Condition cannot be null. " +
					"Use detachShotCondition() to remove a Condition");

		detachShotCondition();
		mShootah = condition;
		mShootah.setUp(this);
		mContext.registerReceiver(mRec, sFilter);
		if (forceTrigger)
			mShootah.forceTrigger();

	}

	/**
	 * Attach a {@link SwitchCondition} and a {@link ShotCondition} to the Batcher. Use this when you want
	 * to limit batching to certain requirements (e.g. to upload data every 30 minutes, but only when on
	 * Wi-fi signal.
	 * @param shotCondition the ShotCondition to attach. Any previous ShotCondition will be removed.
	 * @param switchCondition the SwitchCondition to attach. Any previous SwitchCondition will be removed.
	 * @param forceTrigger force a triggering of the ShotCondition, although this will only cause a batch if the
	 *                     SwitchCondition is satisfied.
	 */
	public void attachConditions(ShotCondition shotCondition, SwitchCondition switchCondition, boolean forceTrigger) {
		detachShotCondition();
		detachSwitchCondition();
		mShootah = shotCondition;
		mShootah.setUp(this);
		mSwitchah = switchCondition;
		mSwitchah.setUp(this);
		mContext.registerReceiver(mRec, sFilter);
		if (forceTrigger)
			mShootah.forceTrigger();
	}

	/* This is currently unimplemented as having a Batcher with only a SwitchCondition is
	 ambiguous. */
//	/**
//	 * Set the Condition of this batcher. If there was a previous Condition, {@link Condition#cleanUp()}
//	 * is called before it is replaced.
//	 * @param condition the Condition to replace any current one
//	 */
//	public void attachSwitchCondition(SwitchCondition condition) {
//
//		if (condition == null)
//			throw new NullPointerException("An attached Condition cannot be null. " +
//					"Use detachSwitchCondition() to remove a Condition");
//
//		detachSwitchCondition();
//		mSwitchah = condition;
//		mSwitchah.setUp(this);
//		mContext.registerReceiver(mRec, sFilter);
//	}

	/**
	 * Detach the {@link SwitchCondition} currently associated with this batcher. {@link Condition#cleanUp()} will
	 * be called before it is detached.
	 */
	public void detachSwitchCondition() {
		if (mSwitchah != null) {
			mSwitchah.cleanUp();
			if (mShootah == null)
				mContext.unregisterReceiver(mRec);
			mSwitchah = null;
		}
	}

	/**
	 * Detach the {@link ShotCondition} currently associated with this batcher. {@link Condition#cleanUp()} will
	 * be called before it is detached.
	 */
	public void detachShotCondition() {
		if (mShootah != null) {
			mShootah.cleanUp();
			if (mSwitchah == null)
				mContext.unregisterReceiver(mRec);
			mShootah = null;
		}
	}

	public Context getContext() {
		return mContext;
	}

	/**
	 * Add inputs into the batcher. These will be consumed by {@link #performOperation(java.util.List)} on the next
	 * batch trigger.
	 * @param inputs the inputs to add
	 */
	public void addInputs(Inputs... inputs) {
		Collections.addAll(mInputList, inputs);
	}

	public void pause() {
		if (mShootah != null)
			mShootah.pause();
		if (mSwitchah != null)
			mSwitchah.pause();
	}

	public void resume() {
		if (mShootah != null)
			mShootah.resume();
		if (mSwitchah != null)
			mSwitchah.resume();
	}

	/**
	 * Called when a batch operation is triggered. Here, you should access and consume the inputs and perform the
	 * operations you wish to perform on them. Bear in mind that this runs on the UI thread, so network or long
	 * operations should use {@link Thread Threads} or {@link android.os.AsyncTask AsyncTasks}.
	 */
	public abstract void performOperation(List<Inputs> inputs);



	private class InternalBroadcastRec extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (mSwitchah != null) {
				if (mSwitchah.isActive())
					performOperation(mInputList);
			} else {
				performOperation(mInputList);
			}
		}
	}

}
