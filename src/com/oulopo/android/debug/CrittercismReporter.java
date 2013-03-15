package com.oulopo.android.debug;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;

import android.app.Application;
import android.content.Context;

import com.crittercism.app.Crittercism;

/**
 * 
 * Crash reporter reporting to Crittercism.
 * See https://www.crittercism.com/
 *
 */
public class CrittercismReporter extends Reporter {

	static final Logger LOG = Logging.getLogger(CrittercismReporter.class);
	private JSONObject crittercismMetadata = new JSONObject();
	private String mKey = "";

	public CrittercismReporter( String key )
	{
		mKey = key;
	}

	@Override
	public void init(Application app) {
		LOG.info( "Initializing Crittercism reporter" );
		JSONObject crittercismConfig = new JSONObject();
		try {
			crittercismConfig.put("delaySendingAppLoad", true);
		    crittercismConfig.put( "shouldCollectLogcat", true ); // send logcat data for devices with API Level 16 and higher
		} catch (JSONException e ) {
			LOG.error( e.toString() );
		}

		Crittercism.init( app.getApplicationContext(), mKey, crittercismConfig );
		Crittercism.setMetadata( crittercismMetadata );
	}

	public void handleException(Throwable e)
	{
		Crittercism.logHandledException( e );
	}
	
	public void handleSilentException(Throwable e) 
	{
		Crittercism.logHandledException( e );
	}

	public void putCustomData(String key, String value) 
	{ 
		try{
			crittercismMetadata.put( key, value );
		} catch(JSONException e) {
			LOG.error( e.toString() );
		}
	}

	public void toggleReports(Context context, boolean state)
	{
		Crittercism.setOptOutStatus( !state );
	}
}
