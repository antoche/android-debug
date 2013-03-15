package com.oulopo.android.debug;

import org.acra.ACRA;
import org.acra.ACRAConfiguration;
import org.slf4j.Logger;

import android.app.Application;

/**
 * 
 * Crash reporter using ACRA the acra-reporter backend.
 * See http://code.google.com/p/acra-reporter/
 *
 */
public class ACRAReporterReporter extends ACRAReporter {
	
	static final Logger LOG = Logging.getLogger(ACRAReporterReporter.class);
	private String mURL = "";
	private String mLogin = "";
	private String mPassword = "";
	
	public ACRAReporterReporter( boolean useAnalytics, String url, String login, String password )
	{
		mURL = url;
		mLogin = login;
		mPassword = password;
	}
	
	@Override
	protected void finishConfig( Application app, ACRAConfiguration config )
	{
		LOG.info( "Using acra-reporter backend" );
		config.setFormUri( mURL );
		config.setFormUriBasicAuthLogin( mLogin );
		config.setFormUriBasicAuthPassword( mPassword );
		ACRA.getErrorReporter().setDefaultReportSenders();
	}
}
