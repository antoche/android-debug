package com.oulopo.android.debug;

import org.slf4j.Logger;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.GoogleAnalytics;

/**
 * 
 * Wrapper utility around logging, analytics and crash reporting facilities.
 * 
 * Create yourself a static instance of this class in your project and call
 * {@link #onCreateApp(Application)}) from your Application's {@code onCreate()} method.
 * 
 * For example:
 * 
 * <code><pre>
 * @ReportsCrashes( 
 * 	formKey = "",
 * 	resToastText = R.string.crash_toast_text,
 * 	mode = ReportingInteractionMode.TOAST,
 * )
 * public class MyApp extends Application {
 * 	static public Debug debug = new Debug( 
 * 		true,
 * 		new AcralyzerReporter( "https://my.server/", "user", "password" )
 * 	);
 * 
 * 	@Override
 * 	public void onCreate() {
 * 		super.onCreate();
 * 		debug.onCreateApp(this);
 * 	}
 * }
 * </pre></code>
 *
 */
public class Debug {
	static final Logger LOG = Logging.getLogger(Debug.class);
	static boolean mUseAnalytics = true;
	static Reporter mReporter; 
		
	public Debug( boolean useAnalytics, Reporter reporter ) {
		mUseAnalytics = useAnalytics;
		mReporter = reporter;
	}
	
	public void onCreateApp(Application app ) {
		Logging.initLogger( app );
		mReporter.init( app );
	}
	
	public void handleException(Throwable e) 
	{
		mReporter.handleException(e);
	}

	public void handleSilentException(Throwable e) 
	{
		mReporter.handleSilentException(e);
	}

	public void putCustomData(String key, String value) 
	{
		mReporter.putCustomData(key, value);
	} 

	public void onCreateActivity(Activity a) 
	{
		mReporter.onCreateActivity(a);
	}

	public void onStartActivity(Activity a)
	{
		if( mUseAnalytics )
		{
			EasyTracker.getInstance().activityStart( a );
		}
		mReporter.onStartActivity(a);
	}

	public void onStopActivity(Activity a)
	{
		if( mUseAnalytics )
		{
			EasyTracker.getInstance().activityStart( a );
		}
		mReporter.onStopActivity(a);
	}

	public void onResumeActivity(Activity a) 
	{
		mReporter.onResumeActivity(a);
	}

	public void onPauseActivity(Activity a) 
	{
		mReporter.onPauseActivity(a);
	} 

	public void toggleReports(Context context, boolean state)
	{
		if( mUseAnalytics ) {
			GoogleAnalytics.getInstance( context ).setAppOptOut( !state );
		}
		mReporter.toggleReports(context, state);
	}


}
