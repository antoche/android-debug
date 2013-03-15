package com.oulopo.android.debug;

import org.slf4j.Logger;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.bugsnag.android.ActivityStack;
import com.bugsnag.android.Bugsnag;

/**
 * 
 * Crash reporter reporting to Bugsnag.
 * See https://bugsnag.com/
 * 
 * Note: uncommenting the addLogToBugsnag() calls seems to make the Bugsnag code run out of memory.
 */
public class BugsnagReporter extends Reporter {

	static final Logger LOG = Logging.getLogger(BugsnagReporter.class);
	String mKey = "";

	public BugsnagReporter( String key )
	{
		mKey = key;
	}
	
	@Override
	public void init(Application app) {
		LOG.info( "Initializing Bugsnag reporter" );
		Bugsnag.register( app, mKey );
	}

	private void addLogToBugsnag() 
	{
		String log = Logging.getLogContent();
		Bugsnag.addToTab( "User", "log", log );
	}

	public void handleException(Throwable e) 
	{
//		addLogToBugsnag();
		Bugsnag.notify( e );
	}
	
	public void handleSilentException(Throwable e)
	{
//		addLogToBugsnag();
		Bugsnag.notify( e );
	}
	
	public void putCustomData(String key, String value) 
	{ 
		Bugsnag.addToTab( "User", key, value );
	}
	
	public void onCreateActivity(Activity a)
	{
	    ActivityStack.add( a );
	    ActivityStack.setTopActivity( a );
	}

	public void onResumeActivity(Activity a) 
	{
    	ActivityStack.setTopActivity( a );
	}

	public void onPauseActivity(Activity a) 
	{
    	ActivityStack.clearTopActivity();
	}
		
	public void toggleReports(Context context, boolean state)
	{
		Bugsnag.setAutoNotify(state);
	}
}
