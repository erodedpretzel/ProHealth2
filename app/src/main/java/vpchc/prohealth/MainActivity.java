package vpchc.prohealth;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.content.Intent;
import android.app.Dialog;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
    private SharedPreferences pref;
    private SharedPreferences currDay;
    private SharedPreferences twolocflag;
    private SharedPreferences mainloc;
    private SharedPreferences mainhours;
    private SharedPreferences mainendt;
    private SharedPreferences mainstartt;
    private SharedPreferences subloc;
    private SharedPreferences subhours;
    private SharedPreferences subendt;
    private SharedPreferences substartt;
    private SharedPreferences.Editor editor;
    //Used to easily check network status
    int networkCheck = 1;

    Dialog callDialog;
    Dialog prefDialog;
    Dialog trackerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Checks if preference exists and if not, run the location preferences dialog
        pref = getSharedPreferences("prefLocation", MODE_PRIVATE);
        if(!pref.contains("prefLocation")) {
            locationPreferenceSet(1);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Sets company logo text to custom font due to it being unavailable natively
        String fontPath = "fonts/franklinGothicHeavyRegular.ttf";
        TextView mainTitleText = (TextView) findViewById(R.id.mainTitleText);
        Typeface customTitleText = Typeface.createFromAsset(getAssets(), fontPath);
        mainTitleText.setTypeface(customTitleText);

        //Checks the text file at valleyprohealth.org/info/ for bus schedule updates
        updateBusTracker();

        //Checks the handle #ValleyProHealth for latest tweet and setups the feed at the bottom
        ImageView twitterBird =(ImageView)findViewById(R.id.twitterBird);
        twitterBird.setOnClickListener(homeListener);
        twitterFeedSetup();

        //Buttons on MainActivity to other activities
        View callImage = findViewById(R.id.callButton);
        View providersImage = findViewById(R.id.providerButton);
        View locationsImage = findViewById(R.id.locationsButton);
        View formsImage = findViewById(R.id.formsButton);
        View portalImage = findViewById(R.id.portalButton);
        View servicesImage = findViewById(R.id.servicesButton);
        View trackerImage = findViewById(R.id.trackerButton);
        View websiteImage = findViewById(R.id.websiteButton);
        View facebookImage = findViewById(R.id.facebookButton);

        callImage.setOnClickListener(homeListener);
        providersImage.setOnClickListener(homeListener);
        locationsImage.setOnClickListener(homeListener);
        formsImage.setOnClickListener(homeListener);
        portalImage.setOnClickListener(homeListener);
        servicesImage.setOnClickListener(homeListener);
        trackerImage.setOnClickListener(homeListener);
        websiteImage.setOnClickListener(homeListener);
        facebookImage.setOnClickListener(homeListener);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuPref:
                //This will reset the preferred location and open the locationpreference activity to choose a new one.
                pref = getSharedPreferences("prefLocation", MODE_PRIVATE);
                editor = pref.edit();
                editor.clear();
                editor.commit();
                locationPreferenceSet(1);
                return true;
            case R.id.menuFeedback:
                Intent openFeedbackIntent = new Intent(MainActivity.this, FeedbackActivity.class);
                startActivity(openFeedbackIntent);
                return true;
            default:
                return true;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    private boolean locationPreferenceSet(int choice){
        if(choice == 0) {
            prefDialog.dismiss();
            return true;
        }else{
            prefDialog = new Dialog(MainActivity.this, R.style.AppTheme_NoActionBar);
            prefDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            prefDialog.setContentView(R.layout.locations_preference_dialog);
            prefDialog.show();
            prefDialog.setCancelable(false);
            prefDialog.setCanceledOnTouchOutside(false);
        }

        Spinner spinnerPrefSelection;
        String locations [] = {};
        locations = getResources().getStringArray(R.array.preferred_locations);

        spinnerPrefSelection = (Spinner)prefDialog.findViewById(R.id.spinnerPrefSelection);
        final ArrayAdapter<String> adapterPrefSelection = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.fancy_spinner_item,locations);
        adapterPrefSelection.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
        spinnerPrefSelection.setAdapter(adapterPrefSelection);

        spinnerPrefSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        savingLocationPreference(0);
                        break;
                    case 2:
                        savingLocationPreference(1);
                        break;
                    case 3:
                        savingLocationPreference(2);
                        break;
                    case 4:
                        savingLocationPreference(3);
                        break;
                    case 5:
                        savingLocationPreference(4);
                        break;
                    case 6:
                        savingLocationPreference(5);
                        break;
                    case 7:
                        savingLocationPreference(6);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });
        return true;
    }

    private void savingLocationPreference(int preferenceChoice){
    /*
	    Arguments: preferenceChoice(0 - No preference, 1 - Bloomingdale, 2 - Cayuga, 3 - Clinton,
	               4 - Crawfordsville, 5 - Terre Haute, 6 - MSBHC)
	    Description: Stores the preferred location
	    Returns: Nothing
    */
        String toastText = getResources().getString(R.string.toast_location_preference_save);
        Toast.makeText(getApplicationContext(),toastText, Toast.LENGTH_SHORT).show();
        editor = pref.edit();
        editor.putInt("prefLocation", preferenceChoice);
        editor.apply();
        locationPreferenceSet(0);
    }

    private void updateBusTracker() {
    /*
	    Arguments:   None
	    Description: Updates the current bus location from the web and stores the values
	    Returns:     Null
    */

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                BufferedReader in = null;
                try {
                    //Check to see if phone has internet connection
                    if (!isConnected()) {
                        networkCheck = 0;
                        return null;
                    }

                    //Reads the locations from an html file into a buffer
                    URL busScheduleUrl = new URL("http://www.valleyprohealth.org/info/bus_schedule.html");
                    in = new BufferedReader(new InputStreamReader(busScheduleUrl.openStream()));
                    String str;
                    String fileContents[] = {"value","value"};
                    int i = 0;
                    while ((str = in.readLine()) != null) {
                        fileContents[i++] = str;
                    }

                    //Stores day when update is ran to compare against current date for outdated
                    //schedule
                    getCurrDay(1);

                    //Stores locations from buffer into corresponding shared preference to be accessed
                    //when opening the tracker dialog
                    int count = 0;
                    for (int j = 1; j < fileContents.length; j++) {
                        String[] parseText = fileContents[count].toString().split(",");
                        String locationText = parseText[0];
                        String hoursText = parseText[1];
                        String startTimeText = parseText[2];
                        String endTimeText = parseText[3];
                        if (count == 0) {
                            twolocflag = getSharedPreferences("twolocflag", MODE_PRIVATE);
                            editor = twolocflag.edit();
                            editor.clear();
                            editor.apply();
                            mainloc = getSharedPreferences("mainloc", MODE_PRIVATE);
                            editor = mainloc.edit();
                            editor.putString("mainloc", locationText);
                            editor.apply();

                            mainhours = getSharedPreferences("mainhours", MODE_PRIVATE);
                            editor = mainhours.edit();
                            editor.putString("mainhours", hoursText);
                            editor.apply();

                            mainstartt = getSharedPreferences("mainstartt", MODE_PRIVATE);
                            editor = mainstartt.edit();
                            editor.putString("mainstartt", startTimeText);
                            editor.apply();

                            mainendt = getSharedPreferences("mainendt", MODE_PRIVATE);
                            editor = mainendt.edit();
                            editor.putString("mainendt", endTimeText);
                            editor.apply();
                            count++;
                        } else {
                            twolocflag = getSharedPreferences("twolocflag", MODE_PRIVATE);
                            editor = twolocflag.edit();
                            editor.putInt("twolocflag", 1);
                            editor.apply();
                            subloc = getSharedPreferences("subloc", MODE_PRIVATE);
                            editor = subloc.edit();
                            editor.putString("subloc", locationText);
                            editor.apply();

                            subhours = getSharedPreferences("subhours", MODE_PRIVATE);
                            editor = subhours.edit();
                            editor.putString("subhours", hoursText);
                            editor.apply();

                            substartt = getSharedPreferences("substartt", MODE_PRIVATE);
                            editor = substartt.edit();
                            editor.putString("substartt", startTimeText);
                            editor.apply();

                            subendt = getSharedPreferences("subendt", MODE_PRIVATE);
                            editor = subendt.edit();
                            editor.putString("subendt", endTimeText);
                            editor.apply();
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    networkCheck = 0;
                } catch (IOException e) {
                    e.printStackTrace();
                    networkCheck = 0;
                } finally {
                    if(in != null){
                        try{
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return null;
            }
        }.execute();
    }

    public boolean isConnected(){
    /*
	    Arguments:   None
	    Description: Gets the current day of the year and depending on the choice, may set
	                 the currDay shared preference.
	                 (ex. Nov 11 is day 308 out of 365).
	    Returns:     The current day of the year
    */

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    private int getCurrDay(int choice){
    /*
	    Arguments:   Choice(0 - ignore setting preference, 1 - set preference)
	    Description: Gets the current day of the year and depending on the choice, may set
	                 the currDay shared preference.
	                 (ex. Nov 11 is day 308 out of 365).
	    Returns:     The current day of the year
    */

        Calendar ca1 = Calendar.getInstance();
        int dayOfYear = ca1.get(Calendar.DAY_OF_YEAR);
        if(choice == 1) {
            currDay = getSharedPreferences("currDay", MODE_PRIVATE);
            editor = currDay.edit();
            editor.putInt("currDay", dayOfYear);
            editor.apply();
        }
        return dayOfYear;
    }


    private void twitterFeedSetup() {
    /*
	    Arguments: None
	    Description: Finds the latest tweet for #ValleyProHealth and sets the feed up.
	    Returns: Nothing
    */
        //This is needed in order for the feed to scroll.
        final TextView twitterFeed =(TextView)findViewById(R.id.twitterFeed);
        twitterFeed.setSelected(true);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                ConfigurationBuilder cb = new ConfigurationBuilder();
                cb.setDebugEnabled(true)
                        .setOAuthConsumerKey("2LnIpLnTSpdTuML3cUfoMYvyX")
                        .setOAuthConsumerSecret(
                                "52xraSEDlBYe0SrmA4NQU0mmbX9G2NMqSfN7LdZYnscHKpZ5Df")
                        .setOAuthAccessToken("706934622001606656-7WcIMBWTBdJ8S5ROfvk45OtcE2bKTln")
                        .setOAuthAccessTokenSecret("kVdlX11CLuRTn5oQlyGcRN3bTcuiPpvKvP1xi2NRUk7zB");

                TwitterFactory tf = new TwitterFactory(cb.build());
                Twitter twitter = tf.getInstance();
                try {
                    final List<twitter4j.Status> status;
                    String user;
                    user = "@ValleyProHealth";
                    status = twitter.getUserTimeline(user);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //If it is able to connect to the handle, it displays the latest tweet along with some tips/info
                            String tipsArray[]={};
                            tipsArray = getResources().getStringArray(R.array.twitter_feed_tips);
                            String initialTweet = getResources().getString(R.string.main_twitter_feed_initial_tweet);
                            final TextView twitterFeed =(TextView)findViewById(R.id.twitterFeed);
                            String[] parseText1 = status.toString().split("text=");
                            String[] parseText2 = parseText1[1].split("'");
                            String[] parseTextFinal = parseText2[1].split("'");
                            twitterFeed.setText(initialTweet +"          " + parseTextFinal[0] + "          " + tipsArray[twitterFeedTips()]);
                        }
                    });

                } catch (TwitterException te) {
                    te.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //If it is unable to connect to the handle, it displays only some tips/info
                            String tipsArray[]={};
                            String initialTweet = getResources().getString(R.string.main_twitter_feed_initial_tweet);
                            tipsArray = getResources().getStringArray(R.array.twitter_feed_tips);
                            twitterFeed.setText(initialTweet + "          " + tipsArray[twitterFeedTips()]);
                        }
                    });
                }
                return null;
            }
        }.execute();
    }

    private int twitterFeedTips(){
    /*
	    Arguments: None
	    Description: Gets a random tip from the tipsArray in the string.xml file
	    Returns: A random tip
    */
        Random r = new Random();
        String tipsArray[]={};
        tipsArray = getResources().getStringArray(R.array.twitter_feed_tips);
        int randomTip = (r.nextInt(tipsArray.length));
        return randomTip;
    }

    private void busTrackerMain(int choice){
    /*
	    Arguments: Choice (0 - dismiss dialog, 1 - create dialog)
	    Description: Main function for the bus tracker.
	    Returns: Nothing
    */
        int r = 0;
        int twoAreas = 0;
        int twolocations = 0;
        String busStatus;
        String location;
        String hours;

        if(choice == 0) {
            trackerDialog.dismiss();
            ImageView trackerButton = (ImageView) findViewById(R.id.trackerButton);
            trackerButton.setImageResource(R.drawable.tracker_off);
            return;
        }else{
            //Below if statement will only run if there is no network & there is no stored shared
            //bus preferences. Mainloc was chosen arbitrarily.
            mainloc = getSharedPreferences("mainloc", MODE_PRIVATE);
            if(networkCheck == 0 & !(mainloc.contains("mainloc"))){
                String toastNetworkText = getResources().getString(R.string.toast_network_error);
                Toast.makeText(getApplicationContext(), toastNetworkText ,Toast.LENGTH_LONG).show();
                ImageView trackerButton = (ImageView) findViewById(R.id.trackerButton);
                trackerButton.setImageResource(R.drawable.tracker_off);
                return;
            }
            //This cond. statement is to make the styling of the dialog look more modern on devices
            //that support it which are API's >= 14
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                trackerDialog = new Dialog(MainActivity.this);
            }else{
                trackerDialog = new Dialog(MainActivity.this, R.style.AppTheme_NoActionBar);
            }
            trackerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            trackerDialog.setContentView(R.layout.tracker_dialog);
            trackerDialog.show();
            trackerDialog.setCancelable(false);
            trackerDialog.setCanceledOnTouchOutside(false);
        }

        //Gets current date and displays it in xx/xx/20xx form
        Calendar currDate = Calendar.getInstance();
        String todaysYear = Integer.toString(currDate.get(Calendar.YEAR));
        String todaysMonth = Integer.toString(currDate.get(Calendar.MONTH) + 1);
        String todaysDay = Integer.toString(currDate.get(Calendar.DATE));
        String todaysDate = todaysMonth + "/" + todaysDay + "/" + todaysYear;
        TextView todaysDateText = (TextView) trackerDialog.findViewById(R.id.trackerDateText);
        todaysDateText.setText(todaysDate);

        //Look up the twolocations flag and sets a variable for easy use
        twolocflag = getSharedPreferences("twolocflag", MODE_PRIVATE);
        if(twolocflag.contains("twolocflag")){
            twolocations = 1;
        }else{
            twolocations = 0;
        }

        // Check the times for current day location 1
        r = busTrackerTimeCheck(0, twolocations);

        // If there are two locations and location 1 is closed, check times for location 2
        if(r == 0 && twolocations == 1){
            r = busTrackerTimeCheck(1, twolocations);
            twoAreas = 1;
        }

        TextView locationText = (TextView) trackerDialog.findViewById(R.id.trackerLocationText);
        TextView timesText = (TextView) trackerDialog.findViewById(R.id.trackerTimesText);
        TextView statusText = (TextView) trackerDialog.findViewById(R.id.trackerStatusText);

        //Setup the locations, hours, & status to be displayed
        currDay = getSharedPreferences("currDay", MODE_PRIVATE);
        int busDay = currDay.getInt("currDay", 0);
        if(busDay < getCurrDay(0)){
            String toastOutdatedText = getResources().getString(R.string.toast_schedule_outdated);
            Toast.makeText(getApplicationContext(), toastOutdatedText ,Toast.LENGTH_LONG).show();
            String outdatedText1 = getResources().getString(R.string.outdated_firstline);
            String outdatedText2 = getResources().getString(R.string.outdated_secondline);
            String outdatedText3 = getResources().getString(R.string.outdated_thirdline);
            location = outdatedText1;
            hours = outdatedText2;
            busStatus = outdatedText3;
        }else{
            if(twoAreas == 0){
                mainloc = getSharedPreferences("mainloc", MODE_PRIVATE);
                mainhours = getSharedPreferences("mainhours", MODE_PRIVATE);
                location = mainloc.getString("mainloc", "default");
                hours = mainhours.getString("mainhours", "default");
            }else{
                subloc = getSharedPreferences("subloc", MODE_PRIVATE);
                subhours = getSharedPreferences("subhours", MODE_PRIVATE);
                location = subloc.getString("subloc", "default");
                hours = subhours.getString("subhours", "default");
            }
            if(r == 1){
                busStatus = getResources().getString(R.string.tracker_status_open);
            }else if(r == 2){
                busStatus = getResources().getString(R.string.tracker_status_open_soon);
            }else if(r == 3){
                busStatus = getResources().getString(R.string.tracker_status_enroute);
            }else if(r == 4){
                busStatus = getResources().getString(R.string.tracker_status_close_soon);
            }else{
                busStatus = getResources().getString(R.string.tracker_status_closed);
            }
        }
        locationText.setText(location);
        timesText.setText(hours);
        statusText.setText(busStatus);

        //Listeners for Close, Download and Refresh buttons
        View buttonTrackerClose = trackerDialog.findViewById(R.id.buttonTrackerCallClose);
        buttonTrackerClose.setOnClickListener(trackerListener);
        View buttonTrackerScheduleDownload = trackerDialog.findViewById(R.id.buttonTrackerScheduleDownload);
        buttonTrackerScheduleDownload.setOnClickListener(trackerListener);
        View buttonTrackerRefresh = trackerDialog.findViewById(R.id.buttonTrackerRefresh);
        buttonTrackerRefresh.setOnClickListener(trackerListener);
    }

    private int busTrackerTimeCheck(int choice, int twoLocationsFlag){
    /*
	    Arguments:   choice (0 - main schedule, 1 - sub schedule)
			         twolocationsFlag (0 - one location; 1 - two locations)
	    Description: Looks at the start and end times of the bus and compares to
	                 the current time.
	    Returns:     0 - Closed, 1 - Open, 2 - Opening Soon, 3 - En route,
	                 4 - Closing Soon
    */
        String busSchedule [] ={"value","value"};
        double  busStartHour = 0, busStartMin = 0, busStartTime = 0,
                busEndHour = 0 , busEndMin = 0, busEndTime = 0,
                currentHour = 0, currentMin = 0, currentTime = 0, compareStart = 0,
                compareEnd = 0, currentMil = 0;
        String[] splitStartTime;
        String[] splitEndTime;

        //Gets current time in milliseconds
        Calendar splitCurrentTime = Calendar.getInstance();
        currentHour = (splitCurrentTime.get(Calendar.HOUR_OF_DAY))*3.6e6;
        currentMin = (splitCurrentTime.get(Calendar.MINUTE))*6e4;
        currentMil = splitCurrentTime.get(Calendar.MILLISECOND);
        currentTime =  currentHour + currentMin + currentMil;

        //Bus start time in milliseconds
        if(choice == 0) {
            SharedPreferences mainStartt= getSharedPreferences("mainstartt", MODE_PRIVATE);
            busSchedule[0] = mainStartt.getString("mainstartt", "default");
        }else{
            SharedPreferences subStartt= getSharedPreferences("substartt", MODE_PRIVATE);
            busSchedule[0] = subStartt.getString("substartt", "default");
        }
        splitStartTime = busSchedule[0].split(":");
        busStartHour = (Double.parseDouble(splitStartTime[0]))*3.6e6;
        busStartMin = (Double.parseDouble(splitStartTime[1]))*6e4;
        busStartTime = busStartHour + busStartMin;

        //Bus end time in milliseconds
        if(choice == 0) {
            SharedPreferences mainEndt= getSharedPreferences("mainendt", MODE_PRIVATE);
            busSchedule[1] = mainEndt.getString("mainendt", "default");
        }else{
            SharedPreferences subEndt= getSharedPreferences("subendt", MODE_PRIVATE);
            busSchedule[1] = subEndt.getString("subendt", "default");
        }
        splitEndTime = busSchedule[1].split(":");
        busEndHour = (Double.parseDouble(splitEndTime[0]))*3.6e6;
        busEndMin = (Double.parseDouble(splitEndTime[1]))*6e4;
        busEndTime = busEndHour + busEndMin;

        //Where the comparing happens
        compareStart = busStartTime - currentTime;
        compareEnd = busEndTime - currentTime;
        if(compareEnd <= 1.8e6 && compareEnd > 0){
            return 4;
        }else if(compareStart <= 1.8e6 && compareStart > 0){
            if(twoLocationsFlag == 1){
                return 3;
            }else{
                return 2;
            }
        }else if(compareStart <= 0 && compareEnd > 0){
            return 1;
        }else{
            return 0;
        }
    }

    private boolean callPopup(int choice){
    /*
	    Arguments: Choice to dismiss or make a new dialog
	    Description: Either creates a callDialog or dismisses it
	    Returns: true
    */
        if(choice == 0) {
            callDialog.dismiss();
            ImageView homeButton = (ImageView) findViewById(R.id.callButton);
            homeButton.setImageResource(R.drawable.call_off);
            return true;
        }else{
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                callDialog = new Dialog(MainActivity.this);
            }else{
                callDialog = new Dialog(MainActivity.this, R.style.AppTheme_NoActionBar);
            }
            callDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            callDialog.setContentView(R.layout.call_dialog);
            callDialog.show();
            callDialog.setCancelable(false);
            callDialog.setCanceledOnTouchOutside(false);
        }

        //Buttons for calling each location
        View buttonCallCloseImage = callDialog.findViewById(R.id.buttonCallClose);
        View buttonCallBloomImage = callDialog.findViewById(R.id.buttonCallBloom);
        View buttonCallCayImage = callDialog.findViewById(R.id.buttonCallCay);
        View buttonCallClintImage = callDialog.findViewById(R.id.buttonCallClint);
        View buttonCallCrawImage = callDialog.findViewById(R.id.buttonCallCraw);
        View buttonCallTerreImage = callDialog.findViewById(R.id.buttonCallTerre);
        View buttonCallMSBHCImage = callDialog.findViewById(R.id.buttonCallMSBHC);

        buttonCallCloseImage.setOnClickListener(callListener);
        buttonCallBloomImage.setOnClickListener(callListener);
        buttonCallCayImage.setOnClickListener(callListener);
        buttonCallClintImage.setOnClickListener(callListener);
        buttonCallCrawImage.setOnClickListener(callListener);
        buttonCallTerreImage.setOnClickListener(callListener);
        buttonCallMSBHCImage.setOnClickListener(callListener);


        return true;
    }

    private View.OnClickListener homeListener = new View.OnClickListener() {
        String toastText;
        public void onClick(View v) {
            //Each button, except twitter/call, will open either a dialog,activity, or page and change its drawable to 'light up'
            switch (v.getId()) {
                case R.id.callButton:
                    ImageView homeButton = (ImageView) findViewById(R.id.callButton);
                    homeButton.setImageResource(R.drawable.call_on);
                    callPopup(1);
                    break;
                case R.id.providerButton:
                    ImageView providerButton = (ImageView) findViewById(R.id.providerButton);
                    providerButton.setImageResource(R.drawable.providers_on);
                    Intent openProvidersIntent = new Intent(MainActivity.this, ProvidersActivity.class);
                    startActivity(openProvidersIntent);
                    break;
                case R.id.locationsButton:
                    ImageView locationsButton = (ImageView) findViewById(R.id.locationsButton);
                    locationsButton.setImageResource(R.drawable.locations_on);
                    Intent openLocationsIntent = new Intent(MainActivity.this, LocationsActivity.class);
                    startActivity(openLocationsIntent);
                    break;
                case R.id.formsButton:
                    ImageView formsButton = (ImageView) findViewById(R.id.formsButton);
                    formsButton.setImageResource(R.drawable.forms_on);
                    Intent openFormsIntent = new Intent(MainActivity.this, FormsActivity.class);
                    startActivity(openFormsIntent);
                    break;
                case R.id.portalButton:
                    ImageView portalButton = (ImageView) findViewById(R.id.portalButton);
                    portalButton.setImageResource(R.drawable.portal_on);
                    String portalUrl = "https://secure2.myunionportal.org/vpchc/default.aspx";
                    Intent portalLink = new Intent(Intent.ACTION_VIEW);
                    portalLink.setData(Uri.parse(portalUrl));
                    startActivity(portalLink);
                    toastText = getResources().getString(R.string.toast_portal_open);
                    Toast.makeText(getApplicationContext(), toastText ,Toast.LENGTH_SHORT).show();
                    break;
                case R.id.servicesButton:
                    ImageView servicesButton = (ImageView) findViewById(R.id.servicesButton);
                    servicesButton.setImageResource(R.drawable.services_on);
                    Intent openservicesIntent = new Intent(MainActivity.this, ServicesActivity.class);
                    startActivity(openservicesIntent);
                    break;
                case R.id.trackerButton:
                    ImageView trackerButton = (ImageView) findViewById(R.id.trackerButton);
                    trackerButton.setImageResource(R.drawable.tracker_on);
                    busTrackerMain(1);
                    break;
                case R.id.websiteButton:
                    ImageView websiteButton = (ImageView) findViewById(R.id.websiteButton);
                    websiteButton.setImageResource(R.drawable.website_on);
                    String websiteUrl = "http://www.vpchc.org";
                    Intent websiteLink = new Intent(Intent.ACTION_VIEW);
                    websiteLink.setData(Uri.parse(websiteUrl));
                    startActivity(websiteLink);
                    toastText = getResources().getString(R.string.toast_website_open);
                    Toast.makeText(getApplicationContext(), toastText ,Toast.LENGTH_SHORT).show();
                    break;
                case R.id.facebookButton:
                    ImageView facebookButton = (ImageView) findViewById(R.id.facebookButton);
                    facebookButton.setImageResource(R.drawable.facebook_on);
                    //Goes to the vpchc page in the facebook app or in the browser if the app isn't present.
                    try {
                        Intent facebookAppIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/132585333458352"));
                        startActivity(facebookAppIntent);
                    } catch (ActivityNotFoundException e) {
                        Intent facebookAppIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://facebook.com/VPCHC"));
                        startActivity(facebookAppIntent);
                    }
                    toastText = getResources().getString(R.string.toast_facebook_open);
                    Toast.makeText(getApplicationContext(), toastText ,Toast.LENGTH_SHORT).show();
                    break;
                case R.id.twitterBird:
                    //Goes to the handle #ValleyProHealth in the twitter app or in the browser if the app isn't present.
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + "ValleyProHealth")));
                    }catch (Exception e) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + "ValleyProHealth")));
                    }
                    toastText = getResources().getString(R.string.toast_twitter_open);
                    Toast.makeText(getApplicationContext(), toastText ,Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    private View.OnClickListener callListener = new View.OnClickListener() {
        public void onClick(View v) {
            String toastText;
            //All but the last will call the location and dismiss the dialog.
            switch (v.getId()) {
                case R.id.buttonCallBloom:
                    Intent callBloomIntent = new Intent(Intent.ACTION_CALL);
                    callBloomIntent.setData(Uri.parse("tel:7654989000"));
                    startActivity(callBloomIntent);
                    toastText = getResources().getString(R.string.toast_call_bloom);
                    Toast.makeText(getApplicationContext(), toastText ,Toast.LENGTH_SHORT).show();
                    callPopup(0);
                    break;
                case R.id.buttonCallCay:
                    Intent callCayIntent = new Intent(Intent.ACTION_CALL);
                    callCayIntent.setData(Uri.parse("tel:7654929042"));
                    startActivity(callCayIntent);
                    toastText = getResources().getString(R.string.toast_call_cay);
                    Toast.makeText(getApplicationContext(),toastText,Toast.LENGTH_SHORT).show();
                    callPopup(0);
                    break;
                case R.id.buttonCallClint:
                    Intent callClintIntent = new Intent(Intent.ACTION_CALL);
                    callClintIntent.setData(Uri.parse("tel:7658281003"));
                    startActivity(callClintIntent);
                    toastText = getResources().getString(R.string.toast_call_clint);
                    Toast.makeText(getApplicationContext(),toastText,Toast.LENGTH_SHORT).show();
                    callPopup(0);
                    break;
                case R.id.buttonCallCraw:
                    Intent callCrawIntent = new Intent(Intent.ACTION_CALL);
                    callCrawIntent.setData(Uri.parse("tel:7653625100"));
                    startActivity(callCrawIntent);
                    toastText = getResources().getString(R.string.toast_call_craw);
                    Toast.makeText(getApplicationContext(),toastText,Toast.LENGTH_SHORT).show();
                    callPopup(0);
                    break;
                case R.id.buttonCallTerre:
                    Intent callTerreIntent = new Intent(Intent.ACTION_CALL);
                    callTerreIntent.setData(Uri.parse("tel:8122387631"));
                    startActivity(callTerreIntent);
                    toastText = getResources().getString(R.string.toast_call_terre);
                    Toast.makeText(getApplicationContext(),toastText,Toast.LENGTH_SHORT).show();
                    callPopup(0);
                    break;
                case R.id.buttonCallMSBHC:
                    Intent callMSBHCIntent = new Intent(Intent.ACTION_CALL);
                    callMSBHCIntent.setData(Uri.parse("tel:7655926164"));
                    startActivity(callMSBHCIntent);
                    toastText = getResources().getString(R.string.toast_call_MSBHC);
                    Toast.makeText(getApplicationContext(),toastText,Toast.LENGTH_SHORT).show();
                    callPopup(0);
                    break;
                case R.id.buttonCallClose:
                    //Dismisses the dialog when the 'x' is hit
                    callPopup(0);
                    break;
                default:
                    break;
            }
        }
    };

    private View.OnClickListener trackerListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                //
                case R.id.buttonTrackerScheduleDownload:
                    String toastDownloadText = getResources().getString(R.string.toast_tracker_download_schedule);
                    Toast.makeText(getApplicationContext(),toastDownloadText,Toast.LENGTH_SHORT).show();
                    Uri scheduleUri = Uri.parse("http://vpchc.org/files/MSBHC_2016_Jan_May.pdf?");
                    Intent scheduleIntent = new Intent(Intent.ACTION_VIEW, scheduleUri);
                    startActivity(scheduleIntent);
                    break;
                case R.id.buttonTrackerRefresh:
                    String toastRefreshText = getResources().getString(R.string.toast_tracker_refresh);
                    Toast.makeText(getApplicationContext(),toastRefreshText,Toast.LENGTH_LONG).show();
                    updateBusTracker();
                    busTrackerMain(0);
                    break;
                case R.id.buttonTrackerCallClose:
                    //Dismisses the dialog when the 'x' is hit
                    busTrackerMain(0);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        updateBusTracker();
        ImageView callButton = (ImageView) findViewById(R.id.callButton);
        ImageView providerButton = (ImageView) findViewById(R.id.providerButton);
        ImageView locationsButton = (ImageView) findViewById(R.id.locationsButton);
        ImageView formsButton = (ImageView) findViewById(R.id.formsButton);
        ImageView portalButton = (ImageView) findViewById(R.id.portalButton);
        ImageView servicesButton = (ImageView) findViewById(R.id.servicesButton);
        ImageView trackerButton = (ImageView) findViewById(R.id.trackerButton);
        ImageView websiteButton = (ImageView) findViewById(R.id.websiteButton);
        ImageView facebookButton = (ImageView) findViewById(R.id.facebookButton);

        
        callButton.setImageResource(R.drawable.call_off);
        providerButton.setImageResource(R.drawable.providers_off);
        locationsButton.setImageResource(R.drawable.locations_off);
        formsButton.setImageResource(R.drawable.forms_off);
        portalButton.setImageResource(R.drawable.portal_off);
        servicesButton.setImageResource(R.drawable.services_off);
        trackerButton.setImageResource(R.drawable.tracker_off);
        websiteButton.setImageResource(R.drawable.website_off);
        facebookButton.setImageResource(R.drawable.facebook_off);
    }

}
