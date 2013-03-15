package com.oulopo.android.debug;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
 
import org.acra.ACRA;
import org.acra.collector.CrashReportData;
import org.acra.ReportField;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
 
public class HockeySender implements ReportSender {
  private static final String BASE_URL = "https://rink.hockeyapp.net/api/2/apps/";
  private static final String CRASHES_PATH = "/crashes";
 
  @Override
  public void send(CrashReportData report) throws ReportSenderException {
    String log = createCrashLog(report);
    String url = BASE_URL + ACRA.getConfig().formKey() + CRASHES_PATH;
 
    try {
      DefaultHttpClient httpClient = new DefaultHttpClient(); 
      HttpPost httpPost = new HttpPost(url);
 
      List<NameValuePair> parameters = new ArrayList<NameValuePair>(); 
      parameters.add(new BasicNameValuePair("raw", log));
      parameters.add(new BasicNameValuePair("userID", report.get(ReportField.INSTALLATION_ID)));
      parameters.add(new BasicNameValuePair("contact", report.get(ReportField.USER_EMAIL)));
      parameters.add(new BasicNameValuePair("description", report.get(ReportField.USER_COMMENT)));
      
      httpPost.setEntity(new UrlEncodedFormEntity(parameters, HTTP.UTF_8));
 
      httpClient.execute(httpPost);   
    }
    catch (Exception e) {
      e.printStackTrace();
    } 
  }
 
  private String createCrashLog(CrashReportData report) {
    Date now = new Date();
    StringBuilder log = new StringBuilder();
    
    log.append("Package: ").append(report.get(ReportField.PACKAGE_NAME)).append("\n");
    log.append("Version: ").append(report.get(ReportField.APP_VERSION_CODE)).append("\n");
    log.append("Android: ").append(report.get(ReportField.ANDROID_VERSION)).append("\n");
    log.append("Manufacturer: ").append(android.os.Build.MANUFACTURER).append("\n");
    log.append("Model: ").append(report.get(ReportField.PHONE_MODEL)).append("\n");
    log.append("Date: ").append(now).append("\n");
    log.append("\n");
    log.append(report.get(ReportField.STACK_TRACE));
    log.append("\n");
    
    // Print one-liners first
    for( Map.Entry<ReportField, String> reportField : report.entrySet() )
    {
    	String value = reportField.getValue();
    	if( !value.endsWith( "\n" ) )
    	{
    		log.append( reportField.getKey().name() + ": " + value + "\n" );
    	}
    }
    
    // Then print big items
    for( Map.Entry<ReportField, String> reportField : report.entrySet() )
    {
    	String value = reportField.getValue();
    	if( value.endsWith( "\n" ) )
    	{
    		log.append( "-------------------------------------------------------------------------------------------\n" );
        	log.append( reportField.getKey().name() + ":\n" );
    		log.append( value );
    	}
    } 
    return log.toString();
  }
}