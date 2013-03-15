package com.oulopo.android.debug;

import org.acra.ACRA;
import org.acra.ACRAConfiguration;
import org.acra.sender.HttpSender.Method;
import org.acra.sender.HttpSender.Type;
import org.slf4j.Logger;

import android.app.Application;

/**
 * 
 * Crash reporter using ACRA with the Acralyzer backend.
 * See https://github.com/ACRA/acralyzer
 * 
 * Note: this reporter requires ACRA v 4.5.
 *
 */
public class AcralyzerReporter extends ACRAReporter {

	static final Logger LOG = Logging.getLogger(AcralyzerReporter.class);
	private String mURL = "";
	private String mLogin = "";
	private String mPassword = "";
	
	public AcralyzerReporter( String url, String login, String password )
	{
		mURL = url;
		mLogin = login;
		mPassword = password;
	}
	
	@Override
	protected void finishConfig( Application app, ACRAConfiguration config )
	{
		LOG.info( "Using acralyzer backend" );
		config.setFormUri( mURL );
		config.setHttpMethod( Method.PUT );
		config.setReportType( Type.JSON );
		config.setFormUriBasicAuthLogin( mLogin );
		config.setFormUriBasicAuthPassword( mPassword );
		ACRA.getErrorReporter().setDefaultReportSenders();
	}

}
