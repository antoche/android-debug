package com.oulopo.android.debug;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Application;
import android.content.Context;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.android.LogcatAppender;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.util.StatusPrinter;


/**
 * 
 * Logging utilities wrapping logback.
 * See http://tony19.github.com/logback-android/
 *
 */
public class Logging {
	
	static final Logger LOG = getLogger(Logging.class);
	static protected String mLogFilePath = "";

	/**
	 * 
	 * Initialize logback with two appenders: one to a file (named after your Application class
	 * and placed under the app's cache directory), and one to logcat.
	 * 
	 */
	static void initLogger( Application app ) 
	{
		File dir = app.getCacheDir();
		mLogFilePath = dir.getAbsolutePath() + File.separator + app.getClass().getName() + ".log";
		// Initialize logback
		LoggerContext lc = (LoggerContext)LoggerFactory.getILoggerFactory();
        lc.reset();
        
        PatternLayoutEncoder fileEncoder = new PatternLayoutEncoder();
        fileEncoder.setContext(lc);
        fileEncoder.setPattern( "%date/%.-1level [%thread] %logger{10} %msg%n" );
        fileEncoder.start();
        
        FileAppender<ILoggingEvent> fa = new FileAppender<ILoggingEvent>();
        fa.setContext( lc );
        fa.setEncoder( fileEncoder );
        fa.setFile( mLogFilePath );
        fa.start();
        
        PatternLayoutEncoder logcatEncoder = new PatternLayoutEncoder();
        logcatEncoder.setContext(lc);
        logcatEncoder.setPattern( "[%thread] %msg%n" );
        logcatEncoder.start();
        
        LogcatAppender la = new LogcatAppender();
        la.setContext(lc);
        la.setEncoder( logcatEncoder );
        la.start();
        
        ch.qos.logback.classic.Logger rootLogger = lc.getLogger( Logger.ROOT_LOGGER_NAME );
        rootLogger.addAppender( fa );
        rootLogger.addAppender( la );
        
        StatusPrinter.printInCaseOfErrorsOrWarnings(lc);
	}
	
	public static String getLogFilePath()
	{
		return mLogFilePath;
	}

	public static void deleteLog(Context context) {
		try
		{
			context.deleteFile( mLogFilePath );
		}
		catch( RuntimeException e )
		{
			LOG.warn( "Could not delete log file " + mLogFilePath );
		}
	}
	
	public static Logger getLogger(Class clazz) {
		return LoggerFactory.getLogger( clazz );
	}
	
	public static String getLogContent() {
		String log = "";
		try{
			FileInputStream stream = new FileInputStream(new File(mLogFilePath));
			try {
			    FileChannel fc = stream.getChannel();
			    MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
			    /* Instead of using default, pass in a decoder. */
			    log = Charset.defaultCharset().decode(bb).toString();
			  }
			  finally {
			    stream.close();
			  }
		}
		catch( FileNotFoundException ex )
		{
			LOG.error( ex.toString() );
		}
		catch( IOException ex )
		{
			LOG.error( ex.toString() );
		}
		return log;
	}

}
