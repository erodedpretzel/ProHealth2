package vpchc.prohealth;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

    private Spinner spinnerLocations;
    private Spinner spinnerLocationsOptions;
    private int selectionLocation;;
    Dialog locationsClinicHoursDialog;
    Dialog locationsContactInfoDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String[] locationCoordinates = new String[1];
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String locations[]={};
        String locationsOptions[]={};

        locations = getResources().getStringArray(R.array.vpchc_locations);
        locationsOptions = getResources().getStringArray(R.array.locations_options);

        spinnerLocations = (Spinner)findViewById(R.id.spinnerLocations);
        ArrayAdapter<String> adapterLocations = new ArrayAdapter<String>(LocationsActivity.this,
                R.layout.fancy_spinner_item,locations);
        adapterLocations.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
        spinnerLocations.setAdapter(adapterLocations);

        spinnerLocationsOptions = (Spinner)findViewById(R.id.spinnerLocationsOptions);
        ArrayAdapter<String>adapterLocationsOptions = new ArrayAdapter<String>(LocationsActivity.this,
                R.layout.fancy_spinner_item,locationsOptions);
        adapterLocationsOptions.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
        spinnerLocationsOptions.setAdapter(adapterLocationsOptions);

        SharedPreferences pref = getSharedPreferences("prefLocation", MODE_PRIVATE);
        int locationPref = pref.getInt("prefLocation", 0);

        if(locationPref == 6){
            locationPref = 0;//This sets MSBHC to no preference do to no location section for it
        }

        spinnerLocations.setSelection(locationPref);

        if(locationPref == 0){
            spinnerLocationsOptions.setVisibility(View.GONE);
        }

        View buttonBack = findViewById(R.id.locationsBackButton);
        buttonBack.setOnClickListener(locationsListener);

        spinnerLocations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position) {
                    case 0:
                        spinnerLocationsOptions.setSelection(0);
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
                        locationsClinicHoursPopup(1);
                        break;
                    case 2:
                        locationsContactInfoPopup(1);
                        break;
                    case 3:
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
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
    }

    private boolean locationsClinicHoursPopup(int choice){
        int i;
        int replaceTextId;
        String replaceTextString;
        if(choice == 0) {
            locationsClinicHoursDialog.dismiss();
            spinnerLocationsOptions.setSelection(0);
            return true;
        }else{
            locationsClinicHoursDialog = new Dialog(LocationsActivity.this);
            locationsClinicHoursDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            locationsClinicHoursDialog.setContentView(R.layout.locations_clinichours_dialog);
            locationsClinicHoursDialog.show();
            locationsClinicHoursDialog.setCancelable(false);
            locationsClinicHoursDialog.setCanceledOnTouchOutside(false);
        }

        TextView locationsText = (TextView) locationsClinicHoursDialog.findViewById(R.id.locationsClinicHoursLocationText);
        if(selectionLocation == 1){
            locationsText.setText("Bloomingdale");
        }else if(selectionLocation == 2){
            locationsText.setText("Cayuga");
        }else if(selectionLocation == 3){
            locationsText.setText("Clinton");
        }else if(selectionLocation == 4){
            locationsText.setText("Crawfordsville");
        }else if(selectionLocation == 5){
            locationsText.setText("Terre Haute");
        }

        for(i=2;i<=10;i+=2){
            replaceTextString = "locationsClinicHoursText" + i;
            replaceTextId = getResources().getIdentifier(replaceTextString, "id", getPackageName());
            TextView tempText = (TextView) locationsClinicHoursDialog.findViewById(replaceTextId);
            if(selectionLocation!=3 & selectionLocation!=5){
                tempText.setText("8:00 a.m. - 5:00 p.m.");
            }else if(selectionLocation == 3) {
                if(i==10){
                    tempText.setText("8:00 a.m. - 5:00 p.m.");
                }else{
                    tempText.setText("8:00 a.m. - 8:00 p.m.");
                }
            }else if(selectionLocation == 5) {
                if (i == 8) {
                    tempText.setText("8:30 a.m. - 5:00 p.m.");
                } else if (i == 10) {
                    tempText.setText("8:00 a.m. - 4:30 p.m.");
                } else {
                    tempText.setText("8:00 a.m. - 5:00 p.m.");
                }
            }
        }

        View buttonLocationsClinicHoursCloseImage = locationsClinicHoursDialog.findViewById(R.id.buttonLocationsClinicHoursClose);
        buttonLocationsClinicHoursCloseImage.setOnClickListener(locationsListener);

        return true;
    }

    private boolean locationsContactInfoPopup(int choice) {
        int i;
        int contactInfoIndex;
        int count=0;
        int replaceTextId;
        String replaceTextString;
        String locationsContactInfo[]={};

        if(choice == 0){
            locationsContactInfoDialog.dismiss();
            spinnerLocationsOptions.setSelection(0);
            return true;
        }else{
            locationsContactInfoDialog = new Dialog(LocationsActivity.this);
            locationsContactInfoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            locationsContactInfoDialog.setContentView(R.layout.locations_contactinfo_dialog);
            locationsContactInfoDialog.show();
            locationsContactInfoDialog.setCancelable(false);
            locationsContactInfoDialog.setCanceledOnTouchOutside(false);
        }

        TextView locationsText = (TextView) locationsContactInfoDialog.findViewById(R.id.locationsContactInfoLocationText);
        if(selectionLocation == 1){
            locationsText.setText("Bloomingdale");
        }else if(selectionLocation == 2){
            locationsText.setText("Cayuga");
        }else if(selectionLocation == 3){
            locationsText.setText("Clinton");
        }else if(selectionLocation == 4){
            locationsText.setText("Crawfordsville");
        }else if(selectionLocation == 5){
            locationsText.setText("Terre Haute");
        }

        locationsContactInfo = getResources().getStringArray(R.array.locations_contact_info);
        contactInfoIndex = (selectionLocation - 1) * 4;
        for(i=2;i<=7;i++){
            replaceTextString = "locationsContactInfoText" + i;
            Log.w("myApp", replaceTextString);
            replaceTextId = getResources().getIdentifier(replaceTextString, "id", getPackageName());
            TextView tempText = (TextView) locationsContactInfoDialog.findViewById(replaceTextId);
            tempText.setText(locationsContactInfo[contactInfoIndex + count++]);
            if(i>2){
                i++;
            }
        }

        View buttonLocationsContactInfoCloseImage = locationsContactInfoDialog.findViewById(R.id.buttonLocationsContactInfoClose);
        buttonLocationsContactInfoCloseImage.setOnClickListener(locationsListener);
        return true;
    }

    private View.OnClickListener locationsListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.locationsBackButton:
                    finish();
                    ImageView backButton = (ImageView) findViewById(R.id.locationsBackButton);
                    backButton.setImageResource(R.drawable.back_arrow_on);
                    break;
                case R.id.buttonLocationsClinicHoursClose:
                    ImageView closeClinicHoursButton = (ImageView) locationsClinicHoursDialog.findViewById(R.id.buttonLocationsClinicHoursClose);
                    closeClinicHoursButton.setImageResource(R.drawable.dialog_close_on);
                    locationsClinicHoursPopup(0);
                    break;
                case R.id.buttonLocationsContactInfoClose:
                    ImageView closeContactInfoButton = (ImageView) locationsContactInfoDialog.findViewById(R.id.buttonLocationsContactInfoClose);
                    closeContactInfoButton.setImageResource(R.drawable.dialog_close_on);
                    locationsContactInfoPopup(0);
                    break;
                default:
                    break;
            }
        }
    };

}
