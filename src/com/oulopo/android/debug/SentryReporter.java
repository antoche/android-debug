package com.oulopo.android.debug;

import org.acra.ACRA;
import org.acra.ACRAConfiguration;
import org.slf4j.Logger;

import android.app.Application;

/**
 * 
 * Crash reporter using ACRA with the Sentry backend.
 * See https://www.getsentry.com/
 * 
 * Note: the SentrySender used in this package is not the exact
 * SentrySender implementation submitted, in order to be able to
 * use the unmodified ACRA library. See https://github.com/dz0ny/acra
 * for the original SentrySender.
 *
 */
public class SentryReporter extends ACRAReporter {

	static final Logger LOG = Logging.getLogger(SentryReporter.class);
	private String mURL = "";

	public SentryReporter( String url )
	{
		mURL = url;
	}
	@Override
	protected void finishConfig( Application app, ACRAConfiguration config ) 
	{
		LOG.info( "Using Sentry backend" );
		ACRA.getErrorReporter().setReportSender( new SentrySender( mURL ) );
	}

}
