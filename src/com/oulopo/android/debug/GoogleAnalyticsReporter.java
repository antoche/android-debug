package com.oulopo.android.debug;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.GoogleAnalytics;

/**
 * 
 * Google Analytics reporter.
 * See https://developers.google.com/analytics/devguides/collection/android/v2/
 * 
 * Notes: 
 *  - This is not a real crash reporter. It merely takes care of reporting
 *  handled exceptions (and crashes if you configured GA to do so) to Google 
 *  Analytics along with the other reporters, as well as calling 
 *  the EasyTracker.activityXXX() methods and initializing the EasyTracker's
 *  first context. The rest of the actual analytics code is up to you.
 *  - Don't forget to create your analytics.xml file as described in the dev guide.
 */
public class GoogleAnalyticsReporter extends Reporter {

	@Override
	public void init(Application app) {
		EasyTracker.getInstance().setContext(app);
	}

	@Override
	public void handleException(Throwable e) {
		EasyTracker.getTracker().sendException( e.toString(), e, false );
	}

	@Override
	public void handleSilentException(Throwable e) {
		EasyTracker.getTracker().sendException( e.toString(), e, false );
	}

	@Override
	public void putCustomData(String key, String value) {
		// Custome data is ignored
	}

	@Override
	public void toggleReports(Context context, boolean state) {
		GoogleAnalytics.getInstance( context ).setAppOptOut( !state );
	}
	
	@Override
	public void onStartActivity(Activity a) {
		EasyTracker.getInstance().activityStart( a );
	}

	@Override
	public void onStopActivity(Activity a)	{
		EasyTracker.getInstance().activityStart( a );
	}

}
