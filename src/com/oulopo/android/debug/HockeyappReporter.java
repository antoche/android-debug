package com.oulopo.android.debug;

import org.acra.ACRA;
import org.acra.ACRAConfiguration;
import org.slf4j.Logger;

import android.app.Application;

/**
 * 
 * Crash reporter using ACRA with the HockeyApp backend.
 * See http://hockeyapp.net/
 *
 */
public class HockeyappReporter extends ACRAReporter {

	static final Logger LOG = Logging.getLogger(HockeyappReporter.class);
	private String mKey = "";

	public HockeyappReporter( String key )
	{
		mKey = key;
	}
	
	@Override
	protected void finishConfig( Application app, ACRAConfiguration config ) 
	{
		LOG.info( "Using hockeyapp backend" );
		config.setFormKey( mKey );
		ACRA.getErrorReporter().setReportSender( new HockeySender() );
	}

}
