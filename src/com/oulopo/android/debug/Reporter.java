package com.oulopo.android.debug;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

/**
 * 
 * Base crash reporter interface.  
 *
 * Notes:
 * - Not all reporters support the possibility to handle exceptions
 * either silently or not. ACRA does. Other reporters may implement
 * handleException and handleSilentException in exactly the same way.
 */
public abstract class Reporter {

	/** Mandatory methods */
	
	/** Initialise the reporter and install the exception handler */
	public abstract void init( Application app );

	/** Report a handled exception (same as ACRA) */
	public abstract void handleException(Throwable e);

	/** Silently report a handled exception (same as ACRA) */
	public abstract void handleSilentException(Throwable e);

	/** Insert/replace custom metadata that will be sent in the next report */
	public abstract void putCustomData(String key, String value); 

	/** Turn reporting on/off */ 
	public abstract void toggleReports(Context context, boolean state);
	
	/** Optional methods. 
	 * 
	 * Some reporters add extra information in the crash report (e.g.,
	 * activity stack) by having your apps call these methods. 
	 */

	public void onCreateActivity(Activity a) {}
	public void onStartActivity(Activity a) {}
	public void onStopActivity(Activity a) {}
	public void onResumeActivity(Activity a) {}
	public void onPauseActivity(Activity a) {} 
}