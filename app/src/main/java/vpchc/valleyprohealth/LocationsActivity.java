package vpchc.valleyprohealth;

import org.vpchc.valleyprohealth.R;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class LocationsActivity extends AppCompatActivity {

    String locations[] = {};
    private int selectionLocation;

    private Spinner spinnerLocations;
    private Spinner spinnerLocationsOptions;

    Dialog locationsDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String[] locationCoordinates = new String[1];
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

        String locationsOptions[]={};

        locations = getResources().getStringArray(R.array.vpchc_locations2);
        locationsOptions = getResources().getStringArray(R.array.locations_options);

        spinnerLocations = (Spinner)findViewById(R.id.spinnerLocations);
        ArrayAdapter<String> adapterLocations = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.fancy_spinner_item,locations);
        adapterLocations.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
        spinnerLocations.setAdapter(adapterLocations);

        spinnerLocationsOptions = (Spinner)findViewById(R.id.spinnerLocationsOptions);
        ArrayAdapter<String>adapterLocationsOptions = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.fancy_spinner_item,locationsOptions);
        adapterLocationsOptions.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
        spinnerLocationsOptions.setAdapter(adapterLocationsOptions);

        //Sets the preferred location
        SharedPreferences pref = getSharedPreferences("prefLocation", MODE_PRIVATE);
        int locationPref = pref.getInt("prefLocation", 0);
        if(locationPref == 6){
            locationPref = 0;//This sets MSBHC to no preference do to no location section for it
        }
        spinnerLocations.setSelection(locationPref);
        if(locationPref == 0){
            spinnerLocationsOptions.setVisibility(View.GONE);
        }

        spinnerLocations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position) {
                    case 0:
                        spinnerLocationsOptions.setSelection(0);
                        spinnerLocationsOptions.setVisibility(View.GONE);
                        break;
                    case 1:
                        spinnerLocationsOptions.setVisibility(View.VISIBLE);
                        spinnerLocationsOptions.setSelection(0);
                        selectionLocation = 1;
                        break;
                    case 2:
                        spinnerLocationsOptions.setVisibility(View.VISIBLE);
                        spinnerLocationsOptions.setSelection(0);
                        selectionLocation = 2;
                        break;
                    case 3:
                        spinnerLocationsOptions.setVisibility(View.VISIBLE);
                        spinnerLocationsOptions.setSelection(0);
                        selectionLocation = 3;
                        break;
                    case 4:
                        spinnerLocationsOptions.setVisibility(View.VISIBLE);
                        spinnerLocationsOptions.setSelection(0);
                        selectionLocation = 4;
                        break;
                    case 5:
                        spinnerLocationsOptions.setVisibility(View.VISIBLE);
                        spinnerLocationsOptions.setSelection(0);
                        selectionLocation = 5;
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        spinnerLocationsOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        locationsPopup();
                        spinnerLocationsOptions.setSelection(0);
                        break;
                    case 2:
                        //Opens the Google Maps app with the locations address already entered in
                        if(selectionLocation == 1){
                            locationCoordinates[0] = "201 W. Academy St., Bloomingdale,IN 47832";
                        }else if(selectionLocation == 2){
                            locationCoordinates[0] = "114 N. Division St., Cayuga, IN 47928";
                        }else if(selectionLocation == 3){
                            locationCoordinates[0] = "777 S. Main Street, Suite 100 Clinton, IN 47842";
                        }else if(selectionLocation == 4){
                            locationCoordinates[0] = "1810 Lafayette Ave, Crawfordsville, IN 47933";
                        }else if(selectionLocation == 5){
                            locationCoordinates[0] = "1530 North 7th Street, Suite 201, Terre Haute, IN 47807";
                        }
                        Uri gmmIntentUri = Uri.parse("geo:0,0?q="+locationCoordinates[0]);
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                        spinnerLocationsOptions.setSelection(0);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
    }

    private boolean locationsPopup(){
    /*
	    Arguments:   choice(0 - dismiss dialog, 1 - create a dialog)
	    Description: Displays or dismisses a dialog with the chosen location's clinic hours listed.
	    Returns:     true
    */
        int i;
        int replaceTextId;
        int layoutID;
        String replaceTextString;

        //Initialize the dialog
        if(selectionLocation == 3){//Clinton Location
            layoutID = getResources().getIdentifier("dialog_locations_clinicinfo2", "layout", this.getPackageName());
        }else{//All other locations
            layoutID = getResources().getIdentifier("dialog_locations_clinicinfo1", "layout", this.getPackageName());
        }
        int closeID = getResources().getIdentifier("buttonDialogCloseLocations", "id", this.getPackageName());
        int titleID = getResources().getIdentifier("locationsClinicInfoLocationText", "id", this.getPackageName());
        int[] IDs = new int[] {layoutID,closeID,titleID};
        String[] titleText = new String[] {locations[selectionLocation]};
        locationsDialog = DialogSetup.dialogCreate(locationsDialog, this, IDs, titleText, 1);

        //Sets location picture displayed
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
            locationsPic.setImageResource(R.drawable.terrehaute_location);
        }

        //Don't run this for clinton location which already has hours populated
        if(selectionLocation != 3) {
            //Sets address, phone and fax
            TextView addressText1 = (TextView) locationsDialog.findViewById(R.id.locationsClinicInfoAddress1);
            TextView addressText2 = (TextView) locationsDialog.findViewById(R.id.locationsClinicInfoAddress2);
            TextView phoneText = (TextView) locationsDialog.findViewById(R.id.locationsClinicInfoPhone);
            TextView faxText = (TextView) locationsDialog.findViewById(R.id.locationsClinicInfoFax);
            String contactInfo[]  = getResources().getStringArray(R.array.locations_contact_info);
            int contactInfoIndex = (selectionLocation - 1) * 4;
            addressText1.setText(contactInfo[contactInfoIndex++]);
            addressText2.setText(contactInfo[contactInfoIndex++]);
            phoneText.setText(contactInfo[contactInfoIndex++]);
            faxText.setText(contactInfo[contactInfoIndex]);

            //Populate list of clinic hours depending on location chosen
            for (i = 2; i <= 10; i += 2) {
                replaceTextString = "locationsClinicInfoText" + i;
                replaceTextId = getResources().getIdentifier(replaceTextString, "id", getPackageName());
                TextView tempText = (TextView) locationsDialog.findViewById(replaceTextId);
                if (selectionLocation != 3 & selectionLocation != 5) {
                    tempText.setText("8:00 a.m. - 5:00 p.m.");
                }else {
                    if (i == 8) {
                        tempText.setText("8:30 a.m. - 5:00 p.m.");
                    } else if (i == 10) {
                        tempText.setText("8:00 a.m. - 4:30 p.m.");
                    } else {
                        tempText.setText("8:00 a.m. - 5:00 p.m.");
                    }
                }
            }
        }
        return true;
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
