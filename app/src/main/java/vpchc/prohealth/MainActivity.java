package vpchc.prohealth;

import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.content.Intent;
import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.drawable.ColorDrawable;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {


    Dialog callDialog;
    Dialog trackerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View callImage = findViewById(R.id.callButton);
        View providersImage = findViewById(R.id.providerButton);
        View locationsImage = findViewById(R.id.locationsButton);
        View formsImage = findViewById(R.id.formsButton);
        View portalImage = findViewById(R.id.portalButton);
        View programsImage = findViewById(R.id.programsButton);
        View trackerImage = findViewById(R.id.trackerButton);
        View jobsImage = findViewById(R.id.jobsButton);
        View facebookImage = findViewById(R.id.facebookButton);


        callImage.setOnClickListener(homeListener);
        providersImage.setOnClickListener(homeListener);
        locationsImage.setOnClickListener(homeListener);
        formsImage.setOnClickListener(homeListener);
        portalImage.setOnClickListener(homeListener);
        programsImage.setOnClickListener(homeListener);
        trackerImage.setOnClickListener(homeListener);
        jobsImage.setOnClickListener(homeListener);
        facebookImage.setOnClickListener(homeListener);
    }

    private View.OnClickListener homeListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.callButton:
                    ImageView homeButton = (ImageView) findViewById(R.id.callButton);
                    homeButton.setImageResource(R.drawable.call_on);
                    callPopup(0);
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
                    break;
                case R.id.programsButton:
                    ImageView programsButton = (ImageView) findViewById(R.id.programsButton);
                    programsButton.setImageResource(R.drawable.programs_on);
                    Intent openProgramsIntent = new Intent(MainActivity.this, ProgramsActivity.class);
                    startActivity(openProgramsIntent);
                    break;
                case R.id.trackerButton:
                    ImageView trackerButton = (ImageView) findViewById(R.id.trackerButton);
                    trackerButton.setImageResource(R.drawable.tracker_on);
                    busTrackerMain(1);
                    break;
                case R.id.jobsButton:
                    ImageView jobsButton = (ImageView) findViewById(R.id.jobsButton);
                    jobsButton.setImageResource(R.drawable.jobs_on);
                    Intent openJobsIntent = new Intent(MainActivity.this, JobsActivity.class);
                    startActivity(openJobsIntent);
                    break;
                case R.id.facebookButton:
                    ImageView facebookButton = (ImageView) findViewById(R.id.facebookButton);
                    facebookButton.setImageResource(R.drawable.facebook_on);
                    String facebookUrl = "https://www.facebook.com/VPCHC";
                    Intent facebookLink = new Intent(Intent.ACTION_VIEW);
                    facebookLink.setData(Uri.parse(facebookUrl));
                    startActivity(facebookLink);
                    break;
                default:
                    break;
            }
        }
    };

    private boolean callPopup(int choice){
        if(choice == 0) {
            callDialog = new Dialog(MainActivity.this);
            callDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            callDialog.setContentView(R.layout.call_dialog);
            callDialog.show();
        }else{
            callDialog.dismiss();
            ImageView homeButton = (ImageView) findViewById(R.id.callButton);
            homeButton.setImageResource(R.drawable.call_off);
            return true;
        }

        View buttonCallBloomImage = callDialog.findViewById(R.id.buttonCallBloom);
        View buttonCallCayImage = callDialog.findViewById(R.id.buttonCallCay);
        View buttonCallClintImage = callDialog.findViewById(R.id.buttonCallClint);
        View buttonCallCrawImage = callDialog.findViewById(R.id.buttonCallCraw);
        View buttonCallTerreImage = callDialog.findViewById(R.id.buttonCallTerre);
        View buttonCallCloseImage = callDialog.findViewById(R.id.buttonCallClose);

        buttonCallCloseImage.setOnClickListener(callListener);
        buttonCallBloomImage.setOnClickListener(callListener);
        buttonCallCayImage.setOnClickListener(callListener);
        buttonCallClintImage.setOnClickListener(callListener);
        buttonCallCrawImage.setOnClickListener(callListener);
        buttonCallTerreImage.setOnClickListener(callListener);



        return true;
    }

    private View.OnClickListener callListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.buttonCallBloom:
                    ImageView callBloomButton = (ImageView) callDialog.findViewById(R.id.buttonCallBloom);
                    callBloomButton.setImageResource(R.drawable.call_site_on);
                    Intent callBloomIntent = new Intent(Intent.ACTION_CALL);
                    callBloomIntent.setData(Uri.parse("tel:7654989000"));
                    startActivity(callBloomIntent);
                    Toast.makeText(getApplicationContext(),"Calling Bloomingdale Location",Toast.LENGTH_SHORT).show();
                    callPopup(1);
                    break;
                case R.id.buttonCallCay:
                    ImageView callCayButton = (ImageView) callDialog.findViewById(R.id.buttonCallCay);
                    callCayButton.setImageResource(R.drawable.call_site_on);
                    Intent callCayIntent = new Intent(Intent.ACTION_CALL);
                    callCayIntent.setData(Uri.parse("tel:7654929042"));
                    startActivity(callCayIntent);
                    Toast.makeText(getApplicationContext(),"Calling Cayuga Location",Toast.LENGTH_SHORT).show();
                    callPopup(1);
                    break;
                case R.id.buttonCallClint:
                    ImageView callClintButton = (ImageView) callDialog.findViewById(R.id.buttonCallClint);
                    callClintButton.setImageResource(R.drawable.call_site_on);
                    Intent callClintIntent = new Intent(Intent.ACTION_CALL);
                    callClintIntent.setData(Uri.parse("tel:7658281003"));
                    startActivity(callClintIntent);
                    Toast.makeText(getApplicationContext(),"Calling Clinton Location",Toast.LENGTH_SHORT).show();
                    callPopup(1);
                    break;
                case R.id.buttonCallCraw:
                    ImageView callCrawButton = (ImageView) callDialog.findViewById(R.id.buttonCallCraw);
                    callCrawButton.setImageResource(R.drawable.call_site_on);
                    Intent callCrawIntent = new Intent(Intent.ACTION_CALL);
                    callCrawIntent.setData(Uri.parse("tel:7653625100"));
                    startActivity(callCrawIntent);
                    Toast.makeText(getApplicationContext(),"Calling Crawfordsville Location",Toast.LENGTH_SHORT).show();
                    callPopup(1);
                    break;
                case R.id.buttonCallTerre:
                    ImageView callTerreButton = (ImageView) callDialog.findViewById(R.id.buttonCallTerre);
                    callTerreButton.setImageResource(R.drawable.call_site_on);
                    Intent callTerreIntent = new Intent(Intent.ACTION_CALL);
                    callTerreIntent.setData(Uri.parse("tel:8122387631"));
                    startActivity(callTerreIntent);
                    Toast.makeText(getApplicationContext(),"Calling Terre Haute Location",Toast.LENGTH_SHORT).show();
                    callPopup(1);
                    break;
                case R.id.buttonCallClose:
                    ImageView closeCallButton = (ImageView) callDialog.findViewById(R.id.buttonCallClose);
                    closeCallButton.setImageResource(R.drawable.dialog_close_on);
                    callPopup(1);
                    break;
                default:
                    break;
            }
        }
    };

    private int getCurrDay(){
/*
	Arguments:   None
	Description: Gets the current day of the year
	             (ex. Nov 11 is day 308 out of 365).
	Returns:     An index to the current day
*/

        Calendar ca1 = Calendar.getInstance();
        int dayOfYear=ca1.get(Calendar.DAY_OF_YEAR);
        return (dayOfYear - 1) * 5;
    }

    private int busTrackerTimeCheck(String[] busSchedule, int index, int twoLocationsFlag){
/*
	Arguments:   busSchedule (An array of either location 1 or 2)
	             index (index of current day)
			     twolocationsFlag (0 - one location; 1 - two locations)
	Description: Looks at the start and end times of the bus and compares to
	             the current time.
	Returns:     0 - Closed, 1 - Open, 2 - Opening Soon, 3 - En route,
	             4 - Closing Soon
*/

//Add two to index to get to the location to the start time
        int times = index + 2;
        double  busStartHour = 0, busStartMin = 0, busStartTime = 0,
                busEndHour = 0, busEndMin = 0, busEndTime = 0,
                currentYear = 0, currentMonth = 0, currentDay = 0,
                currentHour = 0, currentMin = 0, currentTime = 0, compareStart = 0,
                compareEnd = 0, currentMil = 0;
        String[] splitStartTime;
        String[] splitEndTime;
//Create a usable current time date method
        Calendar splitCurrentTime = Calendar.getInstance();
        currentHour = (splitCurrentTime.get(Calendar.HOUR))*3.6e6;
        currentMin = (splitCurrentTime.get(Calendar.MINUTE))*6e4;
        currentMil = splitCurrentTime.get(Calendar.MILLISECOND);
        currentTime =  currentHour + currentMin + currentMil;

//Split the start time and create a useble bus start time date method
        splitStartTime = busSchedule[times++].split(":");
        busStartHour = (Double.parseDouble(splitStartTime[0]))*3.6e6;
        busStartMin = (Double.parseDouble(splitStartTime[1]))*6e4;
        busStartTime = busStartHour + busStartMin;

//Split the end time and create a useable bus end time date method
        splitEndTime = busSchedule[times].split(":");
        busEndHour = (Double.parseDouble(splitStartTime[0]))*3.6e6;
        busEndMin = (Double.parseDouble(splitStartTime[1]))*6e4;
        busEndTime = busEndHour + busEndMin;

        compareStart = busStartTime - currentTime;
        compareEnd = busEndTime - currentTime;

//Where the comparing happens
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

    private void busTrackerMain(int choice){
/*
	Arguments: None
	Description: Main function for the bus tracker.
	Returns: Nothing
*/
        int today = 0;
        int ret = 0;
        int twoAreas = 0;
        String[] busSchedule;
        String[] busSubSchedule={};

        if(choice == 0) {
            trackerDialog.dismiss();
            ImageView trackerButton = (ImageView) findViewById(R.id.trackerButton);
            trackerButton.setImageResource(R.drawable.tracker_off);
            return;
        }else{
            trackerDialog = new Dialog(MainActivity.this);
            trackerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            trackerDialog.setContentView(R.layout.tracker_dialog);
            trackerDialog.show();
        }

// Check the times for current day location 1
        busSchedule = busTrackerSchedules(0);
        today = getCurrDay();
        ret = busTrackerTimeCheck(busSchedule, today , 0);

// If there are two locations and location 1 is closed,
// check times for location 2
        if(ret == 0 && busSchedule[today + 4] != "0"){
            busSubSchedule = busTrackerSchedules(1);
            ret = busTrackerTimeCheck(busSubSchedule, Integer.parseInt(busSchedule[today + 4]) , 1);
            twoAreas = 1;
        }

        TextView locationText = (TextView) trackerDialog.findViewById(R.id.trackerLocationText);
        TextView timesText = (TextView) trackerDialog.findViewById(R.id.trackerTimesText);
// Display the location and times
        if(twoAreas == 0){
            locationText.setText(busSchedule[today++]);
            timesText.setText(busSchedule[today]);
        }else{
            locationText.setText(busSubSchedule[Integer.parseInt(busSchedule[today + 4])]);
            timesText.setText(busSubSchedule[Integer.parseInt(busSchedule[today+1 + 4])]);
        }

        TextView statusText = (TextView) trackerDialog.findViewById(R.id.trackerStatusText);
// Display the status
        if(ret == 1){
            statusText.setText("Open");
        }else if(ret == 2){
            statusText.setText("Opening Soon");;
        }else if(ret == 3){
            statusText.setText("En Route");
        }else if(ret == 4){
            statusText.setText("Closing Soon");
        }else{
            statusText.setText("Closed");
        }
        View buttonTrackerClose = trackerDialog.findViewById(R.id.buttonTrackerCallClose);
        buttonTrackerClose.setOnClickListener(trackerListener);
    }

    private String[] busTrackerSchedules(int choice){
/*
	Arguments:   Choice (0 - Main Schedule, 1 - Sub Schedule)
	Description: This is just a way to get the long array out of the
				 main function to clean things up.
	Returns:     Main Schedule or Sub Schedule
*/

//subSchedule array is used for days where the bus visits two sites.
        String[] subSchedule = {
    /*01/12*/"SVMS/SVHS", "11:30a - 2:00p", "11:30", "14:00", "0",
	/*01/18*/"Van Duyn", "10:30a - 2:00p", "10:30", "14:00", "0",
    /*01/21*/"Rosedale Elem", "11:30a - 2:00p", "11:30", "14:00", "0",
    /*02/01*/"Van Duyn", "10:30a - 2:00p", "10:30", "14:00", "0",
    /*02/02*/"SVMS/SVHS", "10:30a - 2:45p", "10:30", "14:45", "0",
    /*02/04*/"Rosedale Elem", "11:30a - 2:00p", "11:30", "14:00", "0",
    /*02/18*/"Rosedale Elem", "11:30a - 2:00 p", "11:30", "14:00", "0",
    /*03/01*/"SVMS/SVHS", "10:30a - 2:45p", "10:30", "14:45", "0",
    /*03/03*/"Rosedale Elem", "11:30a - 2:00p", "11:30", "14:00", "0",
    /*03/14*/"Ernie Pyle", "11:30a - 2:00p", "11:30", "14:00", "0",
    /*03/17*/"Rosedale Elem", "11:30a - 2:00p", "11:30", "14:00", "0",
    /*04/05*/"SVMS/SVHS", "10:30a - 2:45p", "10:30", "14:45", "0",
    /*04/11*/"Van Duyn", "10:30a - 2:00p", "10:30", "14:00", "0",
    /*04/14*/"Rosedale Elem", "11:30a - 2:00p", "11:30", "14:00", "0",
    /*04/28*/"Rosedale Elem", "11:30a - 2:00p", "11:30", "14:00", "0",
    /*05/07*/"SVMS/SVHS", "10:30a - 2:45p", "10:30", "14:45", "0",
    /*05/09*/"Van Duyn", "10:30a - 2:00p", "10:30", "14:00", "0",
    /*05/12*/"Rosedale Elem", "11:30a - 2:00p", "11:30", "14:00", "0"
        };

//mainSchedule format: starting from 0, every 5th index is a new date.
//					1st index: location
//					2nd index: time in a easy to read format
//				    3rd index: bus start time
//					4th index: bus end time
//					5th index: two locations indicator/index
        String[] mainSchedule = {
      /*01/01*/"No Bus Today", "----", "0:0", "0:0", "0",
    /*01/02*/"Weekend", "----", "0:0", "0:0", "0",
    /*01/03*/"Weekend", "----", "0:0", "0:0", "0",
    /*01/04*/"No Bus Today", "----", "0:0", "0:0", "0",
    /*01/05*/"No Bus Today", "----", "0:0", "0:0", "0",
    /*01/06*/"No Bus Today", "----", "0:0", "0:0", "0",
    /*01/07*/"No Bus Today", "----", "0:0", "0:0", "0",
    /*01/08*/"NVMS/NVHS", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*01/09*/"Weekend", "----", "0:0", "0:0", "0",
    /*01/10*/"Weekend", "----", "0:0", "0:0", "0",
    /*01/11*/"Turkey Run Schools", "8:30a - 2:45a", "8:30", "14:45", "0",
    /*01/12*/"Central Elem", "8:30a - 10:00a", "8:30", "10:00", "0",
    /*01/13*/"Rockville Schools", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*01/14*/"Montezuma Elem", "8:30a - 2:00p", "8:30", "14:00", "0",
    /*01/15*/"NVMS/NVHS", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*01/16*/"Weekend", "----", "0:0", "0:0", "0",
    /*01/17*/"Weekend", "----", "0:0", "0:0", "0",
    /*01/18*/"Ernie Pyle", "8:30a - 10:00a", "8:30", "10:00", "5",
    /*01/19*/"SVMS/SVHS", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*01/20*/"Rockville Schools", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*01/21*/"Riverton Parke", "8:30a - 11:00a", "8:30", "11:00", "10",
    /*01/22*/"NVMS/NVHS", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*01/23*/"Weekend", "----", "0:0", "0:0", "0",
    /*01/24*/"Weekend", "----", "0:0", "0:0", "0",
    /*01/25*/"Turkey Run Schools", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*01/26*/"SVMS/SVHS", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*01/27*/"Rockville Schools", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*01/28*/"Montezuma Elem", "8:30a - 2:00p", "8:30", "14:00", "0",
    /*01/29*/"NVMS/NVHS", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*01/30*/"Weekend", "----", "0:0", "0:0", "0",
    /*01/31*/"Weekend", "----", "0:0", "0:0", "0",
 	  /*02/01*/"Ernie Pyle", "8:30a - 10:00a", "8:30", "10:00", "15",
    /*02/02*/"Central Elem", "8:30a - 10:00a", "8:30", "10:00", "20",
    /*02/03*/"Rockville Schools", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*02/04*/"Riverton Parke", "8:30a - 11:00a", "8:30", "11:00", "25",
    /*02/05*/"NVMS/NVHS", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*02/06*/"Weekend", "----", "0:0", "0:0", "0",
    /*02/07*/"Weekend", "----", "0:0", "0:0", "0",
    /*02/08*/"Turkey Run Schools", "8:30a - 2:45a", "8:30", "14:45", "0",
    /*02/09*/"SVMS/SVHS", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*02/10*/"Rockville Schools", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*02/11*/"Montezuma Elem", "8:30a - 2:00p", "8:30", "14:00", "0",
    /*02/12*/"NVMS/NVHS", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*02/13*/"Weekend", "----", "0:0", "0:0", "0",
    /*02/14*/"Weekend", "<3", "0:0", "0:0", "0",
    /*02/15*/"Presidents Day", "4:-)", "0:0", "0:0", "0",
    /*02/16*/"SVMS/SVHS", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*02/17*/"Rockville Schools", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*02/18*/"Riverton Parke", "8:30a - 11:00a", "8:30", "11:00", "30",
    /*02/19*/"NVMS/NVHS", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*02/20*/"Weekend", "----", "0:0", "0:0", "0",
    /*02/21*/"Weekend", "----", "0:0", "0:0", "0",
    /*02/22*/"Turkey Run Schools", "8:30a - 2:45a", "8:30", "14:45", "0",
    /*02/23*/"SVMS/SVHS", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*02/24*/"Rockville Schools", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*02/25*/"Montezuma Elem", "8:30a - 2:00p", "8:30", "14:00", "0",
    /*02/26*/"NVMS/NVHS", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*02/27*/"Weekend", "----", "0:0", "0:0", "0",
    /*02/28*/"Weekend", "----", "0:0", "0:0", "0",
    /*02/29*/"Riverton Parke", "----", "8:30", "14:00", "0",
      /*03/01*/"Central Elem", "8:30a - 10:00a", "8:30", "10:00", "35",
    /*03/02*/"Rockville Schools", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*03/03*/"Rosedale Elem", "10:30a - 2:00p", "10:30", "2:00", "0",
    /*03/04*/"NVMS/NVHS", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*03/05*/"Weekend", "----", "0:0", "0:0", "0",
    /*03/06*/"Weekend", "----", "0:0", "0:0", "0",
    /*03/07*/"Turkey Run Schools", "8:30a - 2:45a", "8:30", "14:45", "0",
    /*03/08*/"SVMS/SVHS", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*03/09*/"Rockville Schools", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*03/10*/"Montezuma Elem", "8:30a - 2:00p", "8:30", "14:00", "0",
    /*03/11*/"NVMS/NVHS", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*03/12*/"Weekend", "----", "0:0", "0:0", "0",
    /*03/13*/"Weekend", "----", "0:0", "0:0", "0",
    /*03/14*/"Van Duyn", "8:30a - 11:00a", "8:30", "11:00", "45",
    /*03/15*/"SVMS/SVHS", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*03/16*/"Rockville Schools", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*03/17*/"Riverton Parke", "8:30a - 11:00a", "8:30", "11:00", "50",
    /*03/18*/"NVMS/NVHS", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*03/19*/"Weekend", "----", "0:0", "0:0", "0",
    /*03/20*/"Weekend", "----", "0:0", "0:0", "0",
    /*03/21*/"Wal-Mart", "8:30a - 3:00p", "8:30", "15:00", "0",
    /*03/22*/"Wal-Mart", "8:30a - 3:00p", "8:30", "15:00", "0",
    /*03/23*/"Wal-Mart", "8:30a - 3:00p", "8:30", "15:00", "0",
    /*03/24*/"Wal-Mart", "8:30a - 3:00p", "8:30", "15:00", "0",
    /*03/25*/"No Bus Today", "✝", "0:0", "0:0", "0",
    /*03/26*/"Weekend", "----", "0:0", "0:0", "0",
    /*03/27*/"Weekend", ":D", "0:0", "0:0", "0",
    /*03/28*/"Spring Break", "B-)", "0:0", "0:0", "0",
    /*03/29*/"Spring Break", "B-)", "0:0", "0:0", "0",
    /*03/30*/"Spring Break", "B-)", "0:0", "0:0", "0",
    /*03/31*/"Spring Break", "B-)", "0:0", "0:0", "0",
      /*04/01*/"Spring Break", "B-)", "0:0", "0:0", "0",
    /*04/02*/"Weekend", "----", "0:0", "0:0", "0",
    /*04/03*/"Weekend", "----", "0:0", "0:0", "0",
    /*04/04*/"Turkey Run Schools", "8:30a - 2:45a", "8:30", "14:45", "0",
    /*04/05*/"Central Elem", "8:30a - 10:00a", "8:30", "10:00", "55",
    /*04/06*/"Rockville Schools", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*04/07*/"Montezuma Elem", "8:30a - 2:00p", "8:30", "14:00", "0",
    /*04/08*/"NVMS/NVHS", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*04/09*/"Weekend", "----", "0:0", "0:0", "0",
    /*04/10*/"Weekend", "----", "0:0", "0:0", "0",
    /*04/11*/"Ernie Pyle", "8:30a - 10:00a", "8:30", "10:00", "60",
    /*04/12*/"SVMS/SVHS", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*04/13*/"Rockville Schools", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*04/14*/"Riverton Parke", "8:30a - 11:00a", "8:30", "11:00", "65",
    /*04/15*/"NVMS/NVHS", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*04/16*/"Weekend", "----", "0:0", "0:0", "0",
    /*04/17*/"Weekend", "----", "0:0", "0:0", "0",
    /*04/18*/"NVMS/NVHS", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*04/19*/"SVMS/SVHS", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*04/20*/"Rockville Schools", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*04/21*/"Montezuma Elem", "8:30a - 2:00p", "8:30", "14:00", "0",
    /*04/22*/"NVMS/NVHS", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*04/23*/"Weekend", "----", "0:0", "0:0", "0",
    /*04/24*/"Weekend", "----", "0:0", "0:0", "0",
    /*04/25*/"NVMS/NVHS", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*04/26*/"SVMS/SVHS", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*04/27*/"Rockville Schools", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*04/28*/"Riverton Parke", "8:30a - 11:00a", "8:30", "11:00", "70",
    /*04/29*/"NVMS/NVHS", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*04/30*/"Weekend", "----", "0:0", "0:0", "0",
      /*05/01*/"Weekend", "----", "0:0", "0:0", "0",
    /*05/02*/"Turkey Run Schools", "8:30a - 2:45a", "8:30", "14:45", "0",
    /*05/03*/"Central Elem", "8:30a - 10:00a", "8:30", "10:00", "75",
    /*05/04*/"Rockville Schools", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*05/05*/"Montezuma Elem", "8:30a - 2:00p", "8:30", "14:00", "0",
    /*05/06*/"NVMS/NVHS", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*05/07*/"Weekend", "----", "0:0", "0:0", "0",
    /*05/08*/"Weekend", "----", "0:0", "0:0", "0",
    /*05/09*/"Ernie Pyle", "8:30a - 10:00a", "8:30", "10:00", "80",
    /*05/10*/"SVMS/SVHS", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*05/11*/"Rockville Schools", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*05/12*/"Riverton Parke", "8:30a - 11:00a", "8:30", "11:00", "85",
    /*05/13*/"NVMS/NVHS", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*05/14*/"Weekend", "----", "0:0", "0:0", "0",
    /*05/15*/"Weekend", "----", "0:0", "0:0", "0",
    /*05/16*/"NVMS/NVHS", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*05/17*/"SVMS/SVHS", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*05/18*/"Rockville Schools", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*05/19*/"Montezuma Elem", "8:30a - 2:00p", "8:30", "14:00", "0",
    /*05/20*/"NVMS/NVHS", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*05/21*/"Weekend", "----", "0:0", "0:0", "0",
    /*05/22*/"Weekend", "----", "0:0", "0:0", "0",
    /*05/23*/"NVMS/NVHS", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*05/24*/"NVMS/NVHS", "8:30a - 2:45p", "8:30", "14:45", "0",
    /*05/25*/"Summer Break", "☼", "0:0", "0:0", "0",
    /*05/26*/"Summer Break", "☼", "0:0", "0:0", "0",
    /*05/27*/"Summer Break", "☼", "0:0", "0:0", "0",
    /*05/28*/"Summer Break", "☼", "0:0", "0:0", "0",
    /*05/29*/"Summer Break", "☼", "0:0", "0:0", "0",
    /*05/30*/"Summer Break", "☼", "0:0", "0:0", "0",
    /*05/31*/"Summer Break", "☼", "0:0", "0:0", "0",
  	  /*06/01*/"Summer Break", "☼", "0:0", "0:0", "0",
    /*06/02*/"Summer Break", "☼", "0:0", "0:0", "0",
    /*06/03*/"Summer Break", "☼", "0:0", "0:0", "0",
    /*06/04*/"Summer Break", "☼", "0:0", "0:0", "0",
    /*06/05*/"Summer Break", "☼", "0:0", "0:0", "0",
    /*06/06*/"Summer Break", "☼", "0:0", "0:0", "0",
    /*06/07*/"Summer Break", "☼", "0:0", "0:0", "0",
    /*06/08*/"Summer Break", "☼", "0:0", "0:0", "0",
    /*06/09*/"Summer Break", "☼", "0:0", "0:0", "0",
    /*06/10*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*06/11*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*06/12*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*06/13*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*06/14*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*06/15*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*06/16*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*06/17*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*06/18*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*06/19*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*06/20*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*06/21*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*06/22*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*06/23*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*06/24*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*06/25*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*06/26*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*06/27*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*06/28*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*06/29*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*06/30*/"Summer Break", ":-D", "0:0", "0:0", "0",
	  /*07/01*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*07/02*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*07/03*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*07/04*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*07/05*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*07/06*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*07/07*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*07/08*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*07/09*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*07/10*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*07/11*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*07/12*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*07/13*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*07/14*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*07/15*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*07/16*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*07/17*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*07/18*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*07/19*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*07/20*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*07/21*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*07/22*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*07/23*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*07/24*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*07/25*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*07/26*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*07/27*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*07/28*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*07/29*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*07/30*/"Summer Break", ":-D", "0:0", "0:0", "0",
    /*07/31*/"Summer Break", ":-D", "0:0", "0:0", "0",
	  /*08/01*/" ", " ", " ", " ", " ",
    /*08/02*/" ", " ", " ", " ", " ",
    /*08/03*/" ", " ", " ", " ", " ",
    /*08/04*/" ", " ", " ", " ", " ",
    /*08/05*/" ", " ", " ", " ", " ",
    /*08/06*/" ", " ", " ", " ", " ",
    /*08/07*/" ", " ", " ", " ", " ",
    /*08/08*/" ", " ", " ", " ", " ",
    /*08/09*/" ", " ", " ", " ", " ",
    /*08/10*/" ", " ", " ", " ", " ",
    /*08/11*/" ", " ", " ", " ", " ",
    /*08/12*/" ", " ", " ", " ", " ",
    /*08/13*/" ", " ", " ", " ", " ",
    /*08/14*/" ", " ", " ", " ", " ",
    /*08/15*/" ", " ", " ", " ", " ",
    /*08/16*/" ", " ", " ", " ", " ",
    /*08/17*/" ", " ", " ", " ", " ",
    /*08/18*/" ", " ", " ", " ", " ",
    /*08/19*/" ", " ", " ", " ", " ",
    /*08/20*/" ", " ", " ", " ", " ",
    /*08/21*/" ", " ", " ", " ", " ",
    /*08/22*/" ", " ", " ", " ", " ",
    /*08/23*/" ", " ", " ", " ", " ",
    /*08/24*/" ", " ", " ", " ", " ",
    /*08/25*/" ", " ", " ", " ", " ",
    /*08/26*/" ", " ", " ", " ", " ",
    /*08/27*/" ", " ", " ", " ", " ",
    /*08/28*/" ", " ", " ", " ", " ",
    /*08/29*/" ", " ", " ", " ", " ",
    /*08/30*/" ", " ", " ", " ", " ",
    /*08/31*/" ", " ", " ", " ", " ",
	  /*09/01*/" ", " ", " ", " ", " ",
    /*09/02*/" ", " ", " ", " ", " ",
    /*09/03*/" ", " ", " ", " ", " ",
    /*09/04*/" ", " ", " ", " ", " ",
    /*09/05*/" ", " ", " ", " ", " ",
    /*09/06*/" ", " ", " ", " ", " ",
    /*09/07*/" ", " ", " ", " ", " ",
    /*09/08*/" ", " ", " ", " ", " ",
    /*09/09*/" ", " ", " ", " ", " ",
    /*09/10*/" ", " ", " ", " ", " ",
    /*09/11*/" ", " ", " ", " ", " ",
    /*09/12*/" ", " ", " ", " ", " ",
    /*09/13*/" ", " ", " ", " ", " ",
    /*09/14*/" ", " ", " ", " ", " ",
    /*09/15*/" ", " ", " ", " ", " ",
    /*09/16*/" ", " ", " ", " ", " ",
    /*09/17*/" ", " ", " ", " ", " ",
    /*09/18*/" ", " ", " ", " ", " ",
    /*09/19*/" ", " ", " ", " ", " ",
    /*09/20*/" ", " ", " ", " ", " ",
    /*09/21*/" ", " ", " ", " ", " ",
    /*09/22*/" ", " ", " ", " ", " ",
    /*09/23*/" ", " ", " ", " ", " ",
    /*09/24*/" ", " ", " ", " ", " ",
    /*09/25*/" ", " ", " ", " ", " ",
    /*09/26*/" ", " ", " ", " ", " ",
    /*09/27*/" ", " ", " ", " ", " ",
    /*09/28*/" ", " ", " ", " ", " ",
    /*09/29*/" ", " ", " ", " ", " ",
    /*09/30*/" ", " ", " ", " ", " ",
      /*10/01*/" ", " ", " ", " ", " ",
    /*10/02*/" ", " ", " ", " ", " ",
    /*10/03*/" ", " ", " ", " ", " ",
    /*10/04*/" ", " ", " ", " ", " ",
    /*10/05*/" ", " ", " ", " ", " ",
    /*10/06*/" ", " ", " ", " ", " ",
    /*10/07*/" ", " ", " ", " ", " ",
    /*10/08*/" ", " ", " ", " ", " ",
    /*10/09*/" ", " ", " ", " ", " ",
    /*10/10*/" ", " ", " ", " ", " ",
    /*10/11*/" ", " ", " ", " ", " ",
    /*10/12*/" ", " ", " ", " ", " ",
    /*10/13*/" ", " ", " ", " ", " ",
    /*10/14*/" ", " ", " ", " ", " ",
    /*10/15*/" ", " ", " ", " ", " ",
    /*10/16*/" ", " ", " ", " ", " ",
    /*10/17*/" ", " ", " ", " ", " ",
    /*10/18*/" ", " ", " ", " ", " ",
    /*10/19*/" ", " ", " ", " ", " ",
    /*10/20*/" ", " ", " ", " ", " ",
    /*10/21*/" ", " ", " ", " ", " ",
    /*10/22*/" ", " ", " ", " ", " ",
    /*10/23*/" ", " ", " ", " ", " ",
    /*10/24*/" ", " ", " ", " ", " ",
    /*10/25*/" ", " ", " ", " ", " ",
    /*10/26*/" ", " ", " ", " ", " ",
    /*10/27*/" ", " ", " ", " ", " ",
    /*10/28*/" ", " ", " ", " ", " ",
    /*10/29*/" ", " ", " ", " ", " ",
    /*10/30*/" ", " ", " ", " ", " ",
    /*10/31*/" ", " ", " ", " ", " ",
      /*11/01*/" ", " ", " ", " ", " ",
    /*11/02*/" ", " ", " ", " ", " ",
    /*11/03*/" ", " ", " ", " ", " ",
    /*11/04*/" ", " ", " ", " ", " ",
    /*11/05*/" ", " ", " ", " ", " ",
    /*11/06*/" ", " ", " ", " ", " ",
    /*11/07*/" ", " ", " ", " ", " ",
    /*11/08*/" ", " ", " ", " ", " ",
    /*11/09*/" ", " ", " ", " ", " ",
    /*11/10*/" ", " ", " ", " ", " ",
    /*11/11*/" ", " ", " ", " ", " ",
    /*11/12*/" ", " ", " ", " ", " ",
    /*11/13*/" ", " ", " ", " ", " ",
    /*11/14*/" ", " ", " ", " ", " ",
    /*11/15*/" ", " ", " ", " ", " ",
    /*11/16*/" ", " ", " ", " ", " ",
    /*11/17*/" ", " ", " ", " ", " ",
    /*11/18*/" ", " ", " ", " ", " ",
    /*11/19*/" ", " ", " ", " ", " ",
    /*11/20*/" ", " ", " ", " ", " ",
    /*11/21*/" ", " ", " ", " ", " ",
    /*11/22*/" ", " ", " ", " ", " ",
    /*11/23*/" ", " ", " ", " ", " ",
    /*11/24*/" ", "<:>==", " ", " ", " ",
    /*11/25*/" ", " ", " ", " ", " ",
    /*11/26*/" ", " ", " ", " ", " ",
    /*11/27*/" ", " ", " ", " ", " ",
    /*11/28*/" ", " ", " ", " ", " ",
    /*11/29*/" ", " ", " ", " ", " ",
    /*11/30*/" ", " ", " ", " ", " ",
      /*12/01*/" ", " ", " ", " ", " ",
    /*12/02*/" ", " ", " ", " ", " ",
    /*12/03*/" ", " ", " ", " ", " ",
    /*12/04*/" ", " ", " ", " ", " ",
    /*12/05*/" ", " ", " ", " ", " ",
    /*12/06*/" ", " ", " ", " ", " ",
    /*12/07*/" ", " ", " ", " ", " ",
    /*12/08*/" ", " ", " ", " ", " ",
    /*12/09*/" ", " ", " ", " ", " ",
    /*12/10*/" ", " ", " ", " ", " ",
    /*12/11*/" ", " ", " ", " ", " ",
    /*12/12*/" ", " ", " ", " ", " ",
    /*12/13*/" ", " ", " ", " ", " ",
    /*12/14*/" ", " ", " ", " ", " ",
    /*12/15*/" ", " ", " ", " ", " ",
    /*12/16*/" ", " ", " ", " ", " ",
    /*12/17*/" ", " ", " ", " ", " ",
    /*12/18*/" ", " ", " ", " ", " ",
    /*12/19*/" ", " ", " ", " ", " ",
    /*12/20*/" ", " ", " ", " ", " ",
    /*12/21*/" ", " ", " ", " ", " ",
    /*12/22*/" ", " ", " ", " ", " ",
    /*12/23*/" ", " ", " ", " ", " ",
    /*12/24*/" ", " ", " ", " ", " ",
    /*12/25*/" ", " ", " ", " ", " ",
    /*12/26*/" ", " ", " ", " ", " ",
    /*12/27*/" ", " ", " ", " ", " ",
    /*12/28*/" ", " ", " ", " ", " ",
    /*12/29*/" ", " ", " ", " ", " ",
    /*12/30*/" ", " ", " ", " ", " ",
    /*12/31*/" ", " ", " ", " ", " "
        };
        if(choice == 0){
            return mainSchedule;
        }else{
            return subSchedule;
        }
    }

    private View.OnClickListener trackerListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.buttonTrackerCallClose:
                   busTrackerMain(0);
                default:
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        ImageView callButton = (ImageView) findViewById(R.id.callButton);
        ImageView providerButton = (ImageView) findViewById(R.id.providerButton);
        ImageView locationsButton = (ImageView) findViewById(R.id.locationsButton);
        ImageView formsButton = (ImageView) findViewById(R.id.formsButton);
        ImageView portalButton = (ImageView) findViewById(R.id.portalButton);
        ImageView programsButton = (ImageView) findViewById(R.id.programsButton);
        ImageView trackerButton = (ImageView) findViewById(R.id.trackerButton);
        ImageView jobsButton = (ImageView) findViewById(R.id.jobsButton);
        ImageView facebookButton = (ImageView) findViewById(R.id.facebookButton);

        callButton.setImageResource(R.drawable.call_off);
        providerButton.setImageResource(R.drawable.providers_off);
        locationsButton.setImageResource(R.drawable.locations_off);
        formsButton.setImageResource(R.drawable.forms_off);
        portalButton.setImageResource(R.drawable.portal_off);
        programsButton.setImageResource(R.drawable.programs_off);
        trackerButton.setImageResource(R.drawable.tracker_off);
        jobsButton.setImageResource(R.drawable.jobs_off);
        facebookButton.setImageResource(R.drawable.facebook_off);
    }

}
