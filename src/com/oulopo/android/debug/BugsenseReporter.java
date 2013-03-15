package com.oulopo.android.debug;

import org.acra.ACRA;
import org.acra.ACRAConfiguration;
import org.slf4j.Logger;

import android.app.Application;

/**
 * 
 * Crash reporter using ACRA with the Bugsense backend.
 * See https://www.bugsense.com/ 
 *
 */
public class BugsenseReporter extends ACRAReporter {

	static final Logger LOG = Logging.getLogger(BugsenseReporter.class);
	private String mKey = "";

	public BugsenseReporter( String key )
	{
		mKey = key;
	}
	
	@Override
	protected void finishConfig( Application app, ACRAConfiguration config ) 
	{
		LOG.info( "Using bugsense backend" );
		config.setFormUri( "http://www.bugsense.com/api/acra?api_key=" + mKey  );
		ACRA.getErrorReporter().setDefaultReportSenders();
	}
}
