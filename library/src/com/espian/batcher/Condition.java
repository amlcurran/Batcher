package com.espian.batcher;

/**
 * Created with IntelliJ IDEA.
 * User: alexcurran
 * Date: 15/11/2012
 * Time: 00:34
 * To change this template use File | Settings | File Templates.
 */
public abstract class Condition {

	public final static String ACTION_BATCHER_TRIGGER = "com.espian.batcher.TRIGGER";

	Batcher mHost;
	boolean isResumed;

	/**
	 * Called to tidy up any resources before the Condition is removed from the Batcher.
	 * Deprecated from release - use {@link #onPause()} instead.
	 */
	@Deprecated
	public void cleanUp() {
		pause();
		mHost = null;
	}

	/**
	 * Called to tidy up any resources before the Condition is removed from the Batcher.
	 * Deprecated from release - use {@link #onPause()} instead.
	 */
	protected void setUp(Batcher host) {
		mHost = host;
		resume();
	}

	protected void pause() {
		onPause();
		isResumed = false;
	}

	protected void resume() {
		onResume();
		isResumed = true;
	}

	public abstract void onPause();

	public abstract void onResume();

}
