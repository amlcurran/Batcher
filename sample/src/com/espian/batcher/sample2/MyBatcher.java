package com.espian.batcher.sample2;

import android.content.Context;
import android.widget.Toast;
import com.espian.batcher.Batcher;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alexcurran
 * Date: 15/11/2012
 * Time: 01:07
 * To change this template use File | Settings | File Templates.
 */
public class MyBatcher extends Batcher<String> {

	public MyBatcher(Context c) {
		super(c);
	}

	@Override
	public void performOperation(List<String> inputs) {
		Toast.makeText(getContext(), "WORKED!", Toast.LENGTH_SHORT).show();
	}
}
