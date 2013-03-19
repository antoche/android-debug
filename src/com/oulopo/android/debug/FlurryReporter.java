package com.oulopo.android.debug;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.flurry.android.FlurryAgent;

/**
 * 
 * Flurry Analytics reporter.
 * See http://www.flurry.com/
 * 
 * Notes: 
 *  - This is not a real crash reporter. It merely takes advantage of the Reporter interface
 *  to call FlurryAgent.onStartSession() and FlurryAgent.onEndSession(). There is no exception handler,
 *  no handled exception, and you can't even turn it off. 
 */
public class FlurryReporter extends Reporter {

	private String mKey = "";
	
	public FlurryReporter( String key ) {
		mKey = key;
	}
	
	@Override
	public void init(Application app) {
	}

	@Override
	public void handleException(Throwable e) {
	}

	@Override
	public void handleSilentException(Throwable e) {
	}

	@Override
	public void putCustomData(String key, String value) {
	}

	@Override
	public void toggleReports(Context context, boolean state) {
	}
	
	@Override
	public void onStartActivity(Activity a) {
		FlurryAgent.onStartSession( a, mKey );
	}

	@Override
	public void onStopActivity(Activity a)	{
		FlurryAgent.onEndSession( a );
	}
	
}
