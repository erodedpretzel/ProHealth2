package vpchc.valleyprohealth;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.content.Intent;
import android.app.Dialog;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import org.vpchc.valleyprohealth.R;

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
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences pref;
    private SharedPreferences currLocale;
    private SharedPreferences currDay;
    private SharedPreferences locations;
    private SharedPreferences.Editor editor;

    private static final int NUM_PAGES = 2;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    Dialog callDialog;
    Dialog prefDialog;
    Dialog trackerDialog;
    Dialog optionsDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //Changes the menu icon
        Drawable menuIcon = ContextCompat.getDrawable(getApplicationContext(),R.drawable.menu_state_list_drawable);
        toolbar.setOverflowIcon(menuIcon);


        //Checks if preference exists and if not, run the location preferences dialog
        pref = getSharedPreferences("prefLocation", MODE_PRIVATE);
        if(!pref.contains("prefLocation")) {
            locationPreferenceSet(1);
        }

        //Sets up the language for the app depending on the pref choosen if there is one
        currLocale = getSharedPreferences("currLocale", MODE_PRIVATE);
        String currentLocale = currLocale.getString("currLocale", "en");
        if(!currLocale.contains("currLocale") &
                !currentLocale.equals(Locale.getDefault().getLanguage()) &
                (Locale.getDefault().getLanguage().equals("en") || Locale.getDefault().getLanguage().equals("es"))){
            editor = currLocale.edit();
            editor.putString("currLocale", Locale.getDefault().getLanguage());
            editor.apply();
        }
        changeLang(currentLocale);

        //Sets company logo text to custom font due to it being unavailable natively
        String fontPath = "fonts/franklinGothicHeavyRegular.ttf";
        TextView mainTitleText = (TextView) findViewById(R.id.mainTitleText);
        Typeface customTitleText = Typeface.createFromAsset(getAssets(), fontPath);
        mainTitleText.setTypeface(customTitleText);


        //Checks the text file at valleyprohealth.org/info/ for bus schedule updates
        if(isConnected()){
            updateBusTracker();
        }else{
            Log.d("isConnected", "Bus Tracker no connection");
        }

        //Checks the handle #ValleyProHealth for latest tweet and setups the feed at the bottom
        twitterFeedSetup();

        //Sets up the viewpager used to scroll through the pages on the main screen
        mPager = (ViewPager) findViewById(R.id.mainPager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        final ViewPager.OnPageChangeListener onPageChangeListener;

        mPager.addOnPageChangeListener(onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //Buttons on MainActivity to other activities
                View callImage      = findViewById(R.id.callButton);
                View providersImage = findViewById(R.id.providerButton);
                View locationsImage = findViewById(R.id.locationsButton);
                View formsImage     = findViewById(R.id.formsButton);
                View portalImage    = findViewById(R.id.portalButton);
                View servicesImage  = findViewById(R.id.servicesButton);
                View trackerImage   = findViewById(R.id.trackerButton);
                View websiteImage   = findViewById(R.id.websiteButton);
                View facebookImage  = findViewById(R.id.facebookButton);
                View faqImage       = findViewById(R.id.faqsButton);
                View patresImage    = findViewById(R.id.patresButton);
                View twitterImage   = findViewById(R.id.twitterButton);
                View memoryImage    = findViewById(R.id.memoryButton);
                View residencyImage = findViewById(R.id.residencyButton);

                callImage.setOnClickListener(homeListener);
                providersImage.setOnClickListener(homeListener);
                locationsImage.setOnClickListener(homeListener);
                formsImage.setOnClickListener(homeListener);
                portalImage.setOnClickListener(homeListener);
                servicesImage.setOnClickListener(homeListener);
                trackerImage.setOnClickListener(homeListener);
                websiteImage.setOnClickListener(homeListener);
                facebookImage.setOnClickListener(homeListener);
                faqImage.setOnClickListener(homeListener);
                patresImage.setOnClickListener(homeListener);
                twitterImage.setOnClickListener(homeListener);
                memoryImage.setOnClickListener(homeListener);
                residencyImage.setOnClickListener(homeListener);

                //Viewpager indicator
                RadioGroup rGroup = (RadioGroup)findViewById(R.id.pagerIndicator);
                ((RadioButton)rGroup.getChildAt(position)).setChecked(true);

                //Used to change the indicator when the page changes as well as when the user
                //taps the radio buttons.
                rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId)
                    {
                        switch(checkedId)
                        {
                            case R.id.homeRadioButton:
                                int pos = mPager.getCurrentItem();
                                if (pos == 1) {
                                    mPager.setCurrentItem(0, true);
                                }
                                break;
                            case R.id.homeRadioButton2:
                                int pos2 = mPager.getCurrentItem();
                                if (pos2 == 0) {
                                    mPager.setCurrentItem(1, true);
                                }
                                break;
                        }
                    }
                });
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //This section is used to initiate the onPageSelected listener after the mpager has
        //completely loaded. If this wasn't here, the first page wouldn't initiate the
        //onPageSelected section until the user scrolled to the second page and then came back.
        //Essentially the user couldn't be able to click most of the buttons without it initially.
        mPager.post(new Runnable(){
            @Override
            public void run() {
               onPageChangeListener.onPageSelected(mPager.getCurrentItem());
            }
        });
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }
        //Creates a new fragment when the user scrolls on the main page.
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new ScreenSlidePageFragmentOne();
            } else {
                return new ScreenSlidePageFragmentTwo();
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    //region Bus Tracker
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
                    //Reads the locations from an html file into a buffer and then stores them in a
                    //locations preference.
                    URL busScheduleUrl = new URL("https://www.valleyprohealth.org/info/bus_app_schedule.html");
                    in = new BufferedReader(new InputStreamReader(busScheduleUrl.openStream()));
                    String str;
                    int i = 0;
                    while ((str = in.readLine()) != null) {
                        locations = getSharedPreferences("locations_" + i, MODE_PRIVATE);
                        editor = locations.edit();
                        editor.putString("locations_" + i++, str);
                        editor.apply();
                    }

                    //Stores day when update is ran to compare against current date for outdated
                    //schedule and stores date to be displayed so that the user can understand the outdated
                    //schedule better.
                    getCurrDay(1);
                    getCurrDate(1);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
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

    private void busTrackerMain(){
    /*
	    Arguments:   choice (0 - dismiss dialog, 1 - create dialog)
	    Description: Main function for the bus tracker.
	    Returns:     Nothing
    */
        int busCheckStatus = 0;
        //0 - Location, 1 - Hours, 2 - Start Time, 3 - End Time, 4 - Flag
        String[] locationsArray = new String[5];

        //Below if statement there is no stored bus preferences.
        locations = getSharedPreferences("locations_0", MODE_PRIVATE);
        if(!(locations.contains("locations_0"))){
            String toastNetworkText = getResources().getString(R.string.toast_network_error);
            Toast.makeText(getApplicationContext(), toastNetworkText ,Toast.LENGTH_LONG).show();
            return;
        }

        String todaysDate = getCurrDate(0);

        //Initialize the dialog
        int layoutID = getResources().getIdentifier("dialog_tracker", "layout", this.getPackageName());
        int closeID = getResources().getIdentifier("buttonDialogCloseTracker", "id", this.getPackageName());
        int titleID = getResources().getIdentifier("trackerDateText", "id", this.getPackageName());
        int[] IDs = new int[] {layoutID,closeID,titleID};
        String[] titleText = new String[] {todaysDate};
        trackerDialog = DialogSetup.dialogCreate(trackerDialog, this, IDs, titleText, 1);

        //Listener for Download button
        View buttonTrackerScheduleDownload = trackerDialog.findViewById(R.id.buttonTrackerScheduleDownload);
        buttonTrackerScheduleDownload.setOnClickListener(trackerListener);

        //Checks the times of each location until it finds one that is open, opening soon,
        //closing soon or en route or there are no other locations left.
        busCheckStatus = busLocationCheck(locationsArray);

        //Sets the bus information displayed on the screen.
        busInfoDisplay(busCheckStatus, locationsArray);
    }

    private int busLocationCheck(String[] locationsArray){
    /*
	    Arguments:   locationsArray - Bus information
	    Description: Checks each location of the bus unless a condition is met.
	    Returns:     r - status of the bus
    */
        int i = 0;
        int r;
        String[] parseText;
        while(true){
            locations = getSharedPreferences("locations_" + i, MODE_PRIVATE);
            String loc = locations.getString("locations_" + i,"DEFAULT");
            parseText = loc.split(",");
            locationsArray[0] = (parseText[0]);
            locationsArray[1] = (parseText[1]);
            locationsArray[2] = (parseText[2]);
            locationsArray[3] = (parseText[3]);
            locationsArray[4] = (parseText[4]);
            r = busTimeCheck(i,locationsArray);
            if (r != 0 || locationsArray[4].equals("0")){
                break;
            }
            i++;
        }
        return r;
    }


    private int busTimeCheck(Integer count, String[] locationsArray){
    /*
	    Arguments:   Array with locations info
	    Description: Looks at the start and end times of the bus location and compares to
	                 the current time.
	    Returns:     0 - Closed, 1 - Open, 2 - Opening Soon, 3 - En route,
	                 4 - Closing Soon
    */
        String[] splitStartTime;
        String[] splitEndTime;
        double  busStartHour, busStartMin, busStartTime,
                busEndHour, busEndMin, busEndTime,
                currentHour, currentMin, currentTime, compareStart,
                compareEnd, currentMil;

        //Gets current time in milliseconds
        Calendar splitCurrentTime = Calendar.getInstance();
        currentHour = (splitCurrentTime.get(Calendar.HOUR_OF_DAY))*3.6e6;
        currentMin  = (splitCurrentTime.get(Calendar.MINUTE))*6e4;
        currentMil  = splitCurrentTime.get(Calendar.MILLISECOND);
        currentTime =  currentHour + currentMin + currentMil;

        //Bus start time in milliseconds
        splitStartTime = locationsArray[2].split(":");
        busStartHour   = (Double.parseDouble(splitStartTime[0]))*3.6e6;
        busStartMin    = (Double.parseDouble(splitStartTime[1]))*6e4;
        busStartTime   = busStartHour + busStartMin;

        //Bus end time in milliseconds
        splitEndTime = locationsArray[3].split(":");
        busEndHour   = (Double.parseDouble(splitEndTime[0]))*3.6e6;
        busEndMin    = (Double.parseDouble(splitEndTime[1]))*6e4;
        busEndTime   = busEndHour + busEndMin;

        //Where the comparing happens
        compareStart = busStartTime - currentTime;
        compareEnd   = busEndTime - currentTime;
        if(compareEnd <= 1.8e6 && compareEnd > 0){
            return 4;
        }else if(count > 0 && compareStart > 1.8e6) {
            return 3;
        }else if(compareStart < 1.8e6 && compareStart > 0){
            return 2;
        }else if(compareStart <= 0 && compareEnd > 0){
            return 1;
        }else{
            return 0;
        }
    }

    private int busInfoDisplay(Integer status, String[] locationsArray){
    /*
	    Arguments:   Status - Status of the bus, locationsArray - Bus information
	    Description: Displays the bus information on the screen.
	    Returns:     True
    */
        String busStatus;
        String location;
        String hours;
        //Setup the locations, hours, & status to be displayed
        currDay = getSharedPreferences("currDay", MODE_PRIVATE);
        int busDay = currDay.getInt("currDay", -1);
        if(busDay < getCurrDay(0)){
            //This condition is a check if the current schedule the user has is outdated.
            //If so it lets the user know.
            String toastOutdatedText = getResources().getString(R.string.toast_schedule_outdated);
            Toast.makeText(getApplicationContext(), toastOutdatedText ,Toast.LENGTH_LONG).show();
            String outdatedText1 = getResources().getString(R.string.outdated_firstline);
            String outdatedText2 = getResources().getString(R.string.outdated_secondline);
            String outdatedText3 = getResources().getString(R.string.outdated_thirdline);
            location  = outdatedText1;
            hours     = outdatedText2;
            busStatus = outdatedText3;
        }else{
            location  = locationsArray[0];
            hours     = locationsArray[1];
            if(status == 1){
                busStatus = getResources().getString(R.string.tracker_status_open);
            }else if(status == 2){
                busStatus = getResources().getString(R.string.tracker_status_open_soon);
            }else if(status == 3){
                busStatus = getResources().getString(R.string.tracker_status_enroute);
            }else if(status == 4){
                busStatus = getResources().getString(R.string.tracker_status_close_soon);
            }else{
                busStatus = getResources().getString(R.string.tracker_status_closed);
            }
        }

        //Gets the text areas where the bus information will be displayed
        TextView locationText = (TextView) trackerDialog.findViewById(R.id.trackerLocationText);
        TextView timesText    = (TextView) trackerDialog.findViewById(R.id.trackerTimesText);
        TextView statusText   = (TextView) trackerDialog.findViewById(R.id.trackerStatusText);


        //Displays the current bus information on the screen
        locationText.setText(location);
        timesText.setText(hours);
        statusText.setText(busStatus);

        return 1;
    }

    private View.OnClickListener trackerListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.buttonTrackerScheduleDownload:
                    String toastDownloadText = getResources().getString(R.string.toast_tracker_download_schedule);
                    Toast.makeText(getApplicationContext(),toastDownloadText,Toast.LENGTH_SHORT).show();
                    Uri scheduleUri = Uri.parse("http://vpchc.org/files/MSBHC_2016_Jan_May.pdf?");
                    Intent scheduleIntent = new Intent(Intent.ACTION_VIEW, scheduleUri);
                    startActivity(scheduleIntent);
                    break;
                default:
                    break;
            }
        }
    };
    //endregion

    //region Call
    private boolean callPopup(){
    /*
	    Arguments:   Choice( 0 - dismiss dialog, 1 - make a new dialog)
	    Description: Either creates a callDialog or dismisses it
	    Returns:     true
    */
        //Initialize the dialog
        int layoutID = getResources().getIdentifier("dialog_call", "layout", this.getPackageName());
        int closeID = getResources().getIdentifier("buttonDialogCloseCall", "id", this.getPackageName());
        int[] IDs = new int[] {layoutID, closeID};
        String[] titleText = new String[] {};
        callDialog = DialogSetup.dialogCreate(callDialog, this, IDs, titleText, 0);

        // Check call permissions and ask user to grant them
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    0);
        }

        //Listeners for the call dialog buttons
        View buttonCallBloomImage = callDialog.findViewById(R.id.buttonCallBloom);
        View buttonCallCayImage   = callDialog.findViewById(R.id.buttonCallCay);
        View buttonCallClintImage = callDialog.findViewById(R.id.buttonCallClint);
        View buttonCallCrawImage  = callDialog.findViewById(R.id.buttonCallCraw);
        View buttonCallTerreImage = callDialog.findViewById(R.id.buttonCallTerre);
        View buttonCallMSBHCImage = callDialog.findViewById(R.id.buttonCallMSBHC);
        buttonCallBloomImage.setOnClickListener(callListener);
        buttonCallCayImage.setOnClickListener(callListener);
        buttonCallClintImage.setOnClickListener(callListener);
        buttonCallCrawImage.setOnClickListener(callListener);
        buttonCallTerreImage.setOnClickListener(callListener);
        buttonCallMSBHCImage.setOnClickListener(callListener);

        return true;
    }

    private View.OnClickListener callListener = new View.OnClickListener() {
        public void onClick(View v) {
            String toastText;
            //All but the last will call the location and dismiss the dialog.
            switch (v.getId()) {
                case R.id.buttonCallBloom:
                    Intent callBloomIntent = new Intent(Intent.ACTION_CALL);
                    try {
                        callBloomIntent.setData(Uri.parse("tel:7654989000"));
                        startActivity(callBloomIntent);
                        toastText = getResources().getString(R.string.toast_call_bloom);
                        Toast.makeText(getApplicationContext(), toastText ,Toast.LENGTH_SHORT).show();
                    }catch(SecurityException ex) {
                        toastText = getResources().getString(R.string.toast_call_perm);
                        Toast.makeText(getApplicationContext(),toastText,Toast.LENGTH_SHORT).show();
                    }
                    callDialog.dismiss();
                    break;
                case R.id.buttonCallCay:
                    Intent callCayIntent = new Intent(Intent.ACTION_CALL);
                    try{
                        callCayIntent.setData(Uri.parse("tel:7654929042"));
                        startActivity(callCayIntent);
                        toastText = getResources().getString(R.string.toast_call_cay);
                        Toast.makeText(getApplicationContext(),toastText,Toast.LENGTH_SHORT).show();
                    }catch(SecurityException ex) {
                        toastText = getResources().getString(R.string.toast_call_perm);
                        Toast.makeText(getApplicationContext(),toastText,Toast.LENGTH_SHORT).show();
                    }
                    callDialog.dismiss();
                    break;
                case R.id.buttonCallClint:
                    Intent callClintIntent = new Intent(Intent.ACTION_CALL);
                    try {
                        callClintIntent.setData(Uri.parse("tel:7658281003"));
                        startActivity(callClintIntent);
                        toastText = getResources().getString(R.string.toast_call_clint);
                        Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT).show();
                    }catch(SecurityException ex) {
                        toastText = getResources().getString(R.string.toast_call_perm);
                        Toast.makeText(getApplicationContext(),toastText,Toast.LENGTH_SHORT).show();
                    }
                    callDialog.dismiss();
                    break;
                case R.id.buttonCallCraw:
                    Intent callCrawIntent = new Intent(Intent.ACTION_CALL);
                    try{
                        callCrawIntent.setData(Uri.parse("tel:7653625100"));
                        startActivity(callCrawIntent);
                        toastText = getResources().getString(R.string.toast_call_craw);
                        Toast.makeText(getApplicationContext(),toastText,Toast.LENGTH_SHORT).show();
                    }catch(SecurityException ex){
                        toastText = getResources().getString(R.string.toast_call_perm);
                        Toast.makeText(getApplicationContext(),toastText,Toast.LENGTH_SHORT).show();
                    }
                    callDialog.dismiss();
                    break;
                case R.id.buttonCallTerre:
                    Intent callTerreIntent = new Intent(Intent.ACTION_CALL);
                    try{
                        callTerreIntent.setData(Uri.parse("tel:8122387631"));
                        startActivity(callTerreIntent);
                        toastText = getResources().getString(R.string.toast_call_terre);
                        Toast.makeText(getApplicationContext(),toastText,Toast.LENGTH_SHORT).show();
                    }catch(SecurityException ex){
                        toastText = getResources().getString(R.string.toast_call_perm);
                        Toast.makeText(getApplicationContext(),toastText,Toast.LENGTH_SHORT).show();
                    }
                    callDialog.dismiss();
                    break;
                case R.id.buttonCallMSBHC:
                    Intent callMSBHCIntent = new Intent(Intent.ACTION_CALL);
                    try {
                        callMSBHCIntent.setData(Uri.parse("tel:7655926164"));
                        startActivity(callMSBHCIntent);
                        toastText = getResources().getString(R.string.toast_call_MSBHC);
                        Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT).show();
                    }catch(SecurityException ex){
                        toastText = getResources().getString(R.string.toast_call_perm);
                        Toast.makeText(getApplicationContext(),toastText,Toast.LENGTH_SHORT).show();
                    }
                    callDialog.dismiss();
                    break;
                default:
                    break;
            }
        }
    };
    //endregion

    //region Current Date
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

    private String getCurrDate(int choice){
    /*
	    Arguments:   Choice(0 - load preference, 1 - set preference)
	    Description: Gets  current date formats it in xx/xx/20xx
	    Returns:     todaysDate - The current date
    */
        String todaysDate = "";
        if(choice == 1){
            Calendar currDate       = Calendar.getInstance();
            String todaysYear       = Integer.toString(currDate.get(Calendar.YEAR));
            String todaysMonth      = Integer.toString(currDate.get(Calendar.MONTH) + 1);
            String todaysDay        = Integer.toString(currDate.get(Calendar.DATE));
            todaysDate       = todaysMonth + "/" + todaysDay + "/" + todaysYear;
            currDay = getSharedPreferences("currDate", MODE_PRIVATE);
            editor = currDay.edit();
            editor.putString("currDate", todaysDate);
            editor.apply();
        }else{
            SharedPreferences dateOfDay = getSharedPreferences("currDate", MODE_PRIVATE);
            todaysDate = dateOfDay.getString("currDate", " ");
        }
        return todaysDate;
    }
    //endregion

    //region Location Preference
    private boolean locationPreferenceSet(int choice){
        //This is the initial locations preference
        // dialog that appears when a user first loads the app.
        if(choice == 0) {
            prefDialog.dismiss();
            return true;
        }else{
            prefDialog = new Dialog(MainActivity.this, R.style.AppTheme_NoActionBar);
            prefDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            prefDialog.setContentView(R.layout.dialog_first_launch_location_preference);
            prefDialog.show();
            prefDialog.setCancelable(false);
            prefDialog.setCanceledOnTouchOutside(false);
        }

        Spinner spinnerPrefSelection;
        String locations [];
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
                        savingLocationPreference(4);;
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
	    Arguments:   preferenceChoice(0 - No preference, 1 - Bloomingdale, 2 - Cayuga, 3 - Clinton,
	                 4 - Crawfordsville, 5 - Terre Haute, 6 - MSBHC)
	    Description: Stores the preferred location
	    Returns:     Nothing
    */
        String toastText = getResources().getString(R.string.toast_location_preference_save);
        Toast.makeText(getApplicationContext(),toastText, Toast.LENGTH_SHORT).show();
        editor = pref.edit();
        editor.putInt("prefLocation", preferenceChoice);
        editor.apply();
        locationPreferenceSet(0);
    }

    private void changeLang(String langChoice){
    /*
        Arguments:   langChoice(Either en for english or es for spanish)
	    Description: Changes the language to either English or Spanish depending on the argument.
	    Returns:     Nothing
    */
        Locale myLocale = new Locale(langChoice);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }
    //endregion

    //region Options
    private boolean optionsSet(){

        int layoutID = getResources().getIdentifier("dialog_options", "layout", this.getPackageName());
        int closeID = getResources().getIdentifier("buttonDialogCloseOptions", "id", this.getPackageName());
        int[] IDs = new int[] {layoutID, closeID};
        String[] titleText = new String[] {};
        optionsDialog = DialogSetup.dialogCreate(optionsDialog, this, IDs, titleText, 0);

        //Listeners for save button
        View buttonSaveImage = optionsDialog.findViewById(R.id.buttonOptionsSave);
        buttonSaveImage.setOnClickListener(optionsListener);

        RadioButton radioButtonLocPref;
        RadioButton radioButtonLang;

        //Presets the location preference radio button to current location preference
        pref = getSharedPreferences("prefLocation", MODE_PRIVATE);
        int locationPref = pref.getInt("prefLocation", 0);
        if(locationPref == 0){
            radioButtonLocPref = (RadioButton)optionsDialog.findViewById(R.id.optionsRadioButtonNoPref);
        }else if(locationPref == 1){
            radioButtonLocPref = (RadioButton)optionsDialog.findViewById(R.id.optionsRadioButtonBloom);
        }else if(locationPref == 2){
            radioButtonLocPref = (RadioButton)optionsDialog.findViewById(R.id.optionsRadioButtonCay);
        }else if(locationPref == 3){
            radioButtonLocPref = (RadioButton)optionsDialog.findViewById(R.id.optionsRadioButtonClint);
        }else if (locationPref == 4){
            radioButtonLocPref = (RadioButton)optionsDialog.findViewById(R.id.optionsRadioButtonCraw);
        }else if(locationPref == 5){
            radioButtonLocPref = (RadioButton)optionsDialog.findViewById(R.id.optionsRadioButtonTerre);
        }else{
            radioButtonLocPref = (RadioButton)optionsDialog.findViewById(R.id.optionsRadioButtonMSBHC);
        }
        radioButtonLocPref.setChecked(true);

        //Presets the language radio button to spanish if set or english by default
        currLocale = getSharedPreferences("currLocale", MODE_PRIVATE);
        String currentLocale = currLocale.getString("currLocale", "en");
        if(currentLocale.equals("es")){
            radioButtonLang = (RadioButton)optionsDialog.findViewById(R.id.optionsRadioButtonLangEs);
        }else{
            radioButtonLang = (RadioButton)optionsDialog.findViewById(R.id.optionsRadioButtonLangEn);
        }
        radioButtonLang.setChecked(true);

        return true;
    }

    private void optionsSave() {
    /*
	    Arguments:   None
	    Description: Performs actions, depending on what the user chooses, when they tap
	                 the save button in the options dialog
	    Returns:     Nothing
    */

        int selectionLocPref;
        int selectionLang;
        int currLocaleToInt;
        String selectionLangToString;

        //Get the index of the selected radio button from the location pref section
        RadioGroup radioGroupLocPref = (RadioGroup)optionsDialog.findViewById(R.id.optionsRadioGroupLocPref);
        int selectedLocPrefId = radioGroupLocPref.getCheckedRadioButtonId();
        RadioButton radioButtonLocPref = (RadioButton)optionsDialog.findViewById(selectedLocPrefId);
        selectionLocPref = radioGroupLocPref.indexOfChild(radioButtonLocPref);

        //Get the index of the selected radio button from the language section
        RadioGroup radioGroupLang = (RadioGroup)optionsDialog.findViewById(R.id.optionsRadioGroupLang);
        int selectedLangId = radioGroupLang.getCheckedRadioButtonId();
        RadioButton radioButtonLang = (RadioButton)optionsDialog.findViewById(selectedLangId);
        selectionLang = radioGroupLang.indexOfChild(radioButtonLang);

        //Gets the current locale
        currLocale = getSharedPreferences("currLocale", MODE_PRIVATE);
        String currentLocale = currLocale.getString("currLocale", "en");

        //Converts the current locale to any easy to use number
        if(currentLocale.equals("en")){
            currLocaleToInt = 0;
        }else{
            currLocaleToInt = 1;
        }

        //Converts the selected language to an easy to use string
        if(selectionLang == 0){
            selectionLangToString = "en";
        }else{
            selectionLangToString = "es";
        }

        //Gets the current location preference
        SharedPreferences pref = getSharedPreferences("prefLocation", MODE_PRIVATE);
        int locationPref = pref.getInt("prefLocation", -1);

        if(selectionLocPref == locationPref && selectionLang == currLocaleToInt){
            //This condition is when there have been no changes to either the location preference
            //or the selected language
            String toastNoChange = getResources().getString(R.string.toast_options_no_change);
            Toast.makeText(getApplicationContext(),toastNoChange,Toast.LENGTH_SHORT).show();
        }else if(selectionLocPref != locationPref && selectionLang == currLocaleToInt){
            //This condition is when there has been a change to the location preference but not
            //the selected language
            String toastLocPrefChange = getResources().getString(R.string.toast_options_loc_pref_change);
            Toast.makeText(getApplicationContext(),toastLocPrefChange,Toast.LENGTH_SHORT).show();
            editor = pref.edit();
            editor.putInt("prefLocation", selectionLocPref);
            editor.apply();
            optionsDialog.dismiss();
        }else if(selectionLocPref == locationPref && selectionLang != currLocaleToInt){
            //This condition is when there has been a change to the selected language but not
            //the location preference
            String toastLangChange = getResources().getString(R.string.toast_options_lang_change);
            Toast.makeText(getApplicationContext(),toastLangChange,Toast.LENGTH_SHORT).show();
            editor = currLocale.edit();
            editor.putString("currLocale", selectionLangToString);
            editor.apply();
            optionsDialog.dismiss();
            changeLang(selectionLangToString);
            finish();
            startActivity(getIntent());
        }else{
            //This condition is when there has been a change to both the location preference and
            //the selected language
            String toastBothChange = getResources().getString(R.string.toast_options_both_change);
            Toast.makeText(getApplicationContext(),toastBothChange,Toast.LENGTH_SHORT).show();
            editor = pref.edit();
            editor.putInt("prefLocation", selectionLocPref);
            editor.apply();
            editor = currLocale.edit();
            editor.putString("currLocale", selectionLangToString);
            editor.apply();
            optionsDialog.dismiss();
            changeLang(selectionLangToString);
            finish();
            startActivity(getIntent());
        }
    }

    private View.OnClickListener optionsListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.buttonOptionsSave:
                    optionsSave();
                    break;
                default:
                    break;
            }
        }
    };
    //endregion

    //region Menu
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuFeedback:
                Intent openFeedbackIntent = new Intent(MainActivity.this, FeedbackActivity.class);
                startActivity(openFeedbackIntent);
                return true;
            case R.id.menuOptions:
                optionsSet();
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
    //endregion

    //region Twitter
    private void twitterFeedSetup() {
    /*
	    Arguments:   None
	    Description: Finds the latest tweet for #ValleyProHealth and sets the feed up.
	    Returns:     Nothing
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
                    if(!isConnected()){
                        return null;
                    }
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
                            String[] parseText1 = status.toString().split("text='");
                            String[] parseTextFinal = parseText1[1].split("',");
                            twitterFeed.setText("          " + parseTextFinal[0] +  "          " + tipsArray[twitterFeedTips()] +  "          " + initialTweet);
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
                            twitterFeed.setText("          " + initialTweet + "          " + tipsArray[twitterFeedTips()]);
                        }
                    });
                }
                return null;
            }
        }.execute();
    }

    private int twitterFeedTips(){
    /*
	    Arguments:   None
	    Description: Gets a random tip from the tipsArray in the string.xml file
	    Returns:     A random tip
    */
        Random r = new Random();
        String tipsArray[];
        tipsArray = getResources().getStringArray(R.array.twitter_feed_tips);
        return (r.nextInt(tipsArray.length));
    }
    //endregion

    public boolean isConnected(){
    /*
	    Arguments:   None
	    Description: Checks if there is a wifi or mobile connection.
	    Returns:     True - There is a connection, False - These is not a connection
    */

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connMgr.getActiveNetworkInfo();

        return  (activeNetwork != null && activeNetwork.isConnected());
    }

    private View.OnClickListener homeListener = new View.OnClickListener() {
        String toastText;
        public void onClick(View v) {
            //Each button, except twitter/call, will open either a dialog, activity, or webpage
            switch (v.getId()) {
                case R.id.callButton:
                    callPopup();
                    break;
                case R.id.providerButton:
                    Intent openProvidersIntent = new Intent(MainActivity.this, ProvidersActivity.class);
                    startActivity(openProvidersIntent);
                    break;
                case R.id.locationsButton:
                    Intent openLocationsIntent = new Intent(MainActivity.this, LocationsActivity.class);
                    startActivity(openLocationsIntent);
                    break;
                case R.id.formsButton:
                    v.setSelected(true);
                    Intent openFormsIntent = new Intent(MainActivity.this, FormsActivity.class);
                    startActivity(openFormsIntent);
                    break;
                case R.id.portalButton:
                    toastText = getResources().getString(R.string.toast_portal_open);
                    Toast.makeText(getApplicationContext(), toastText ,Toast.LENGTH_SHORT).show();
                    String portalUrl  = "https://secure2.myunionportal.org/vpchc/default.aspx";
                    Intent portalLink = new Intent(Intent.ACTION_VIEW);
                    portalLink.setData(Uri.parse(portalUrl));
                    startActivity(portalLink);
                    break;
                case R.id.servicesButton:
                    Intent openServicesIntent = new Intent(MainActivity.this, ServicesActivity.class);
                    startActivity(openServicesIntent);
                    break;
                case R.id.trackerButton:
                    busTrackerMain();
                    break;
                case R.id.websiteButton:
                    toastText = getResources().getString(R.string.toast_website_open);
                    Toast.makeText(getApplicationContext(), toastText ,Toast.LENGTH_SHORT).show();
                    String websiteUrl  = "https://www.valleyprohealth.org";
                    Intent websiteLink = new Intent(Intent.ACTION_VIEW);
                    websiteLink.setData(Uri.parse(websiteUrl));
                    startActivity(websiteLink);
                    break;
                case R.id.facebookButton:
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
                case R.id.faqsButton:
                    Intent openFAQsIntent = new Intent(MainActivity.this, FAQsActivity.class);
                    startActivity(openFAQsIntent);
                    break;
                case R.id.patresButton:
                    Intent openPatResIntent = new Intent(MainActivity.this, PatResActivity.class);
                    startActivity(openPatResIntent);
                    break;
                case R.id.twitterButton:
                    //Goes to the handle #ValleyProHealth in the twitter app or in the browser if the app isn't present.
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + "ValleyProHealth")));
                    }catch (Exception e) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + "ValleyProHealth")));
                    }
                    toastText = getResources().getString(R.string.toast_twitter_open);
                    Toast.makeText(getApplicationContext(), toastText ,Toast.LENGTH_SHORT).show();
                    break;
                case R.id.memoryButton:
                    toastText = getResources().getString(R.string.toast_memwebsite_open);
                    Toast.makeText(getApplicationContext(), toastText ,Toast.LENGTH_SHORT).show();
                    String websiteMemUrl  = "http://www.memoryandagingcenter.org";
                    Intent websiteMemLink = new Intent(Intent.ACTION_VIEW);
                    websiteMemLink.setData(Uri.parse(websiteMemUrl));
                    startActivity(websiteMemLink);
                    break;
                case R.id.residencyButton:
                    toastText = getResources().getString(R.string.toast_reswebsite_open);
                    Toast.makeText(getApplicationContext(), toastText ,Toast.LENGTH_SHORT).show();
                    String websiteResUrl  = "http://www.uhfmr.org";
                    Intent websiteResLink = new Intent(Intent.ACTION_VIEW);
                    websiteResLink.setData(Uri.parse(websiteResUrl));
                    startActivity(websiteResLink);
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
    }

}
