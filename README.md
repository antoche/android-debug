android-debug
=============

Crash reporting, logging and analytics utilities for android

This package makes it (hopefully) a little easier to try out the various
crash report services available out there.

## Step-by-step guide


* Import the code into your project
* Add lockback-android to your project: http://tony19.github.com/logback-android/
* Setup your project for Google Analytics (or disable the analytics code)
* Have your `Application` follow this model (where `MyReporter` is one of the available reporters in the package):

        @ReportsCrashes( 
           formKey = "",
           resToastText = R.string.crash_toast_text,
           mode = ReportingInteractionMode.TOAST,
        )
        public class MyApp extends Application {
            static public Debug debug = new Debug( 
                true,
                new MyReporter( "https://my.server/", "user", "password" )
            );
            @Override
            public void onCreate() {
                super.onCreate();
                debug.onCreateApp(this);
            }
        }

* Each reporter requires you to create an account to the corresponding service (or host it) and may require your to download the vendor's SDK as well. See "Reporters requirements" below.
* Test the reporting by throwing an exception from somewhere in your code:

        throw new RuntimeException( "Hey, is this working?" );

  or try sending a handled exception to the reporter:

        // ...
        catch( Exception e )
        {
            MyApp.debug.handleException( e );
        }
* If all goes according to plan, the service associated with the reporter should have received the crash report.
* If something doesn't seem to work, check logcat.
* Don't forget to read the comments and the vendor's docs.

## Reporters requirements

* `AcralyzerReporter`
     * Requires hosting (e.g., IrishCouch or Cloudant)
     * Requires ACRA-4.5: https://oss.sonatype.org/content/repositories/snapshots/ch/acra/acra/4.5.0-SNAPSHOT/
     * See http://github.com/ACRA/acralyzer
* `ACRAReporterReporter`
     * Requires hosting on Google App Engine
     * Requires ACRA: http://acra.ch/
     * See http://code.google.com/p/acra-reporter/
* `BugsenseReporter`
     * Requires an account on Bugsense
     * Requires ACRA: http://acra.ch/
     * See https://www.bugsense.com/
* `BugsnagReporter`
     * Requires an account on Bugsnag
     * Requires the Bugsnag SDK 
     * See https://bugsnag.com/
* `CrittercismReporter`:
     * Requires an account on Crittercism
     * Requires the Crittercism SDK 
     * See https://www.crittercism.com/
* `HockeyappReporter`:
     * Requires an account on HockeyApp
     * Requires ACRA: http://acra.ch/
     * See http://hockeyapp.net/
* `LogentriesReporter`:
     * Requires an account on Logentries
     * Requires ACRA: http://acra.ch/
     * See https://logentries.com/ and http://www.vaudaux-ruth.com/android-log-collecting-with-acra-and-logentries
* `SentryReporter`:
     * Requires an account on Sentry
     * Requires ACRA: http://acra.ch/
     * See https://www.getsentry.com/
 
