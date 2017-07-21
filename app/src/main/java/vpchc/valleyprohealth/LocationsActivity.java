package vpchc.valleyprohealth;

import org.vpchc.valleyprohealth.R;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class LocationsActivity extends AppCompatActivity {

    private int selectionLocation;
    private Spinner spinnerLocationsOptions;
    String locations[] = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Initial setup of activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarLocations);
        setSupportActionBar(toolbar);

        //Sets company logo text to custom font due to it being unavailable natively
        String fontPath = "fonts/franklinGothicMedium.ttf";
        TextView titleText = (TextView) findViewById(R.id.title_locations);
        Typeface customTitleText = Typeface.createFromAsset(getAssets(), fontPath);
        titleText.setTypeface(customTitleText);

        //Back button listener
        View buttonBack = findViewById(R.id.backButtonLocations);
        buttonBack.setOnClickListener(locationsListener);

        //Setup arrays used as spinner items
        locations                 = getResources().getStringArray(R.array.vpchc_locations2);
        String locationsOptions[] = getResources().getStringArray(R.array.locations_options);

        //Setup locations spinner
        Spinner spinnerLocations = (Spinner)findViewById(R.id.spinnerLocations);
        ArrayAdapter<String> adapterLocations = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.fancy_spinner_item,locations);
        adapterLocations.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
        spinnerLocations.setAdapter(adapterLocations);

        //Setup location options spinner
        spinnerLocationsOptions = (Spinner)findViewById(R.id.spinnerLocationsOptions);
        ArrayAdapter<String>adapterLocationsOptions = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.fancy_spinner_item,locationsOptions);
        adapterLocationsOptions.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
        spinnerLocationsOptions.setAdapter(adapterLocationsOptions);

        //Sets the preferred location
        CommonFunc.sharedPrefSet(this, spinnerLocations, spinnerLocationsOptions, true);

        spinnerLocations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position) {
                    case 0:
                        spinnerLocationsOptions.setSelection(0);
                        spinnerLocationsOptions.setVisibility(View.GONE);
                        break;
                    default:
                        spinnerLocationsOptions.setSelection(0);
                        spinnerLocationsOptions.setVisibility(View.VISIBLE);
                        selectionLocation = position;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}

        });

        spinnerLocationsOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        locationsPopup();
                        //Reset the location options spinner
                        spinnerLocationsOptions.setSelection(0);
                        break;
                    case 2:
                        String locationCoordinates;
                        //Opens the Google Maps app with the locations address already entered in
                        if (selectionLocation == 1) {
                            locationCoordinates = "201 W. Academy St., Bloomingdale,IN 47832";
                        } else if (selectionLocation == 2) {
                            locationCoordinates = "114 N. Division St., Cayuga, IN 47928";
                        } else if (selectionLocation == 3) {
                            locationCoordinates = "777 S. Main Street, Suite 100 Clinton, IN 47842";
                        } else if (selectionLocation == 4) {
                            locationCoordinates = "1810 Lafayette Ave, Crawfordsville, IN 47933";
                        } else if (selectionLocation == 5) {
                            locationCoordinates = "727 N Lincoln Rd, Rockville, IN 47872";
                        }else{
                            locationCoordinates = "1530 North 7th Street, Suite 201, Terre Haute, IN 47807";
                        }
                        Uri gmmIntentUri = Uri.parse("geo:0,0?q="+locationCoordinates);
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                        spinnerLocationsOptions.setSelection(0);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });
    }

    private void locationsPopup(){
    /*
	    Arguments:   none
	    Description: Displays a dialog with the chosen clinics picture, contact info, and hours listed.
	    Returns:     void
    */
        Dialog locationsDialog;
        String dialogName;

        //Initialize the dialog
        if(selectionLocation == 3){//Clinton Location
            dialogName = "locations_clinicinfo2";
            locationsDialog = DialogSetup.Base.Create(this, dialogName);
        }else{
            dialogName = "locations_clinicinfo1";
            String[] titleText = new String[] {locations[selectionLocation]};
            locationsDialog = DialogSetup.Title.titleSetup(this, dialogName, 2, titleText);

            //Sets address, phone and fax
            locationContactInfoSet(locationsDialog);

            //Populate list of clinic hours depending on location chosen
            locationHoursSet(locationsDialog);
        }
        //Sets location picture displayed
        locationPicSet(locationsDialog);
    }

    private void locationPicSet(Dialog locationsDialog){
    /*
	    Arguments:   none
	    Description: Sets location picture displayed.
	    Returns:     void
    */
        ImageView locationsPic = (ImageView) locationsDialog.findViewById(R.id.locationsPic);
        if(selectionLocation == 1){
            locationsPic.setImageResource(R.drawable.bloomingdale_location);
        }else if(selectionLocation == 2){
            locationsPic.setImageResource(R.drawable.cayuga_location);
        }else if(selectionLocation == 3){
            locationsPic.setImageResource(R.drawable.clinton_location);
        }else if(selectionLocation == 4){
            locationsPic.setImageResource(R.drawable.crawfordsville_location);
        }else if(selectionLocation == 5){
            locationsPic.setImageResource(R.drawable.rockville_location);
        }else{
            locationsPic.setImageResource(R.drawable.terrehaute_location);
        }
    }

    private void locationContactInfoSet(Dialog locationsDialog){
    /*
	    Arguments:   none
	    Description: Sets the contact info of the location selected.
	    Returns:     void
    */
        TextView addressText1 = (TextView) locationsDialog.findViewById(R.id.locationsClinicInfoAddress1);
        TextView addressText2 = (TextView) locationsDialog.findViewById(R.id.locationsClinicInfoAddress2);
        TextView phoneText    = (TextView) locationsDialog.findViewById(R.id.locationsClinicInfoPhone);
        TextView faxText      = (TextView) locationsDialog.findViewById(R.id.locationsClinicInfoFax);
        String contactInfo[]  = getResources().getStringArray(R.array.locations_contact_info);

        int contactInfoIndex = (selectionLocation - 1) * 4;
        addressText1.setText(contactInfo[contactInfoIndex++]);
        addressText2.setText(contactInfo[contactInfoIndex++]);
        phoneText.setText(contactInfo[contactInfoIndex++]);
        faxText.setText(contactInfo[contactInfoIndex]);
    }

    private void locationHoursSet(Dialog locationsDialog){
    /*
	    Arguments:   none
	    Description: Set the hours of the location selected.
	    Returns:     void
    */
        int replaceTextId;
        String replaceTextString;

        //Set hours for each weekday for location chosen by user
        for (int i = 0; i < 5; i++) {
            replaceTextString = "locationsClinicInfoHours" + i;
            replaceTextId = getResources().getIdentifier(replaceTextString, "id", getPackageName());
            TextView hoursText = (TextView) locationsDialog.findViewById(replaceTextId);
            if (selectionLocation == 1){//Bloomingdale
                if (i == 0){
                    hoursText.setText("8:00 a.m. - 8:00 p.m.");
                }else{
                    hoursText.setText("8:00 a.m. - 5:00 p.m.");
                }
            } else if (selectionLocation == 2){//Cayuga
                if (i == 2){
                    hoursText.setText("8:00 a.m. - 8:00 p.m.");
                }else{
                    hoursText.setText("8:00 a.m. - 5:00 p.m.");
                }
            } else if (selectionLocation == 5){//Rockville
                if (i == 0 || i == 2 || i ==4 ){//Mon,Wed,Fri
                    hoursText.setText("7:30 a.m. - 5:00 p.m.");
                }else{//Tues,Thu
                    hoursText.setText("8:00 a.m. - 5:00 p.m.");
                }
            }else if (selectionLocation == 6) {//Terre Haute
                if (i == 3) {//Thu
                    hoursText.setText("8:30 a.m. - 5:00 p.m.");
                }else if (i == 4){//Fri
                    hoursText.setText("8:00 a.m. - 4:30 p.m.");
                }else{//Mon,Tue,Wed
                    hoursText.setText("8:00 a.m. - 5:00 p.m.");
                }
            }else {//All other hours
                hoursText.setText("8:00 a.m. - 5:00 p.m.");
            }
        }
    }

    private View.OnClickListener locationsListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.backButtonLocations:
                    finish();
                    v.setSelected(true);
                    break;
                default:
                    break;
            }
        }
    };

}