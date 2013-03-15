package com.oulopo.android.debug;

import org.acra.ACRA;
import org.acra.ACRAConfiguration;
import org.slf4j.Logger;

import android.app.Application;

/**
 * 
 * Crash reporter reporting to Logentries.
 * See https://logentries.com/ and http://www.vaudaux-ruth.com/android-log-collecting-with-acra-and-logentries
 *
 */
public class LogentriesReporter extends ACRAReporter {

	static final Logger LOG = Logging.getLogger(LogentriesReporter.class);
	private String mKey = "";

	public LogentriesReporter( String key )
	{
		mKey = key;
	}
	
	@Override
	protected void finishConfig( Application app, ACRAConfiguration config )
	{
		LOG.info( "Using logentries backend" );
		LogentriesSender sender = new LogentriesSender( app.getBaseContext(), mKey );
		ACRA.getErrorReporter().setReportSender(sender);
	}

}
