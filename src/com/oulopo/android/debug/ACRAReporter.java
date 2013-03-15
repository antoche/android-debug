package com.oulopo.android.debug;

import org.acra.ACRA;
import org.acra.ACRAConfiguration;
import org.slf4j.Logger;

import android.app.Application;
import android.content.Context;

/**
 * 
 * Base class for reporters using ACRA. Takes care of all the ACRA stuff.
 * All the subclass have to do is override \c finishConfig to set up 
 * their specific configuration (e.g., set their sender or formUri).
 * 
 * Notes:
 *  - Your application class requires the @ReportsCrashes annotation
 *  in order for ACRA to initialize properly. 
 *
 */
public abstract class ACRAReporter extends Reporter {
	static final Logger LOG = Logging.getLogger(ACRAReporter.class);

	public void init(Application app) {
    	try
		{
			LOG.info( "Initializing ACRA reporter" );
			ACRA.init( app );
			ACRAConfiguration config = ACRA.getConfig();
			config.setApplicationLogFile( Logging.getLogFilePath() );
			config.setApplicationLogFileLines( 300 );
			finishConfig( app, config );
		}
		catch(IllegalStateException e)
		{
			// log acra error
			LOG.error( "ACRA error: " + e.toString() + e.getStackTrace().toString() );
		}
	}
	
	protected abstract void finishConfig( Application app, ACRAConfiguration config );

	@Override
	public void handleException(Throwable e) {
		ACRA.getErrorReporter().handleException(e);
	}

	@Override
	public void handleSilentException(Throwable e) {
		ACRA.getErrorReporter().handleSilentException(e);
	}

	@Override
	public void putCustomData(String key, String value) {
		ACRA.getErrorReporter().putCustomData(key, value);
	}

	@Override
	public void toggleReports(Context context, boolean state)
	{
		// Nothing do do, implemented implicitly by acra.enable preference. 
	}
}
