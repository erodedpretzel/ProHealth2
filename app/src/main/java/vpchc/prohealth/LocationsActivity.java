package vpchc.prohealth;

import android.app.Dialog;
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
import android.widget.Spinner;
import android.widget.TextView;

public class LocationsActivity extends AppCompatActivity {

    private Spinner spinnerLocations;
    private Spinner spinnerLocationsOptions;
    private static final String[]locations = {"Please select a location", "Bloomingdale", "Cayuga",
            "Clinton", "Crawfordsville", "MSBHC", "Terre Haute"};
    private static final String[]options = {"Please select an option", "Clinic Hours", "Contact Info",
            "Get Directions"};
    private int selectionLocation;
    private static final String[]terrehauteclinichours = {"8:00 a.m. - 5:00 p.m.",
            "8:00 a.m. - 5:00 p.m.","8:00 a.m. - 5:00 p.m.","8:30 a.m. - 5:00 p.m.",
            "8:00 a.m. - 4:30 p.m."};
    Dialog locationsClinicHoursDialog;
    Dialog locationsContactInfoDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spinnerLocations = (Spinner)findViewById(R.id.spinnerLocations);
        ArrayAdapter<String> adapterLocations = new ArrayAdapter<String>(LocationsActivity.this,
                R.layout.fancy_spinner_item,locations);
        adapterLocations.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
        spinnerLocations.setAdapter(adapterLocations);

        spinnerLocationsOptions = (Spinner)findViewById(R.id.spinnerLocationsOptions);
        ArrayAdapter<String>adapterLocationsOptions = new ArrayAdapter<String>(LocationsActivity.this,
                R.layout.fancy_spinner_item,locations);
        adapterLocationsOptions.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
        spinnerLocationsOptions.setAdapter(adapterProviderLocations);
        spinnerLocationsOptions.setVisibility(View.GONE);

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
                        locationsClinicHoursPopup(0);
                    case 2:
                        locationsContactInfoPopup(0);
                    case 3:
                        //open map here
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });


    }

    private boolean locationsClinicHoursPopup(int choice){
        int replaceText;
        int i;
        int j=0;
        int count=0;
        int foundCount=1;
        int numberOfProviders;
        int locationIndex;
        int replaceTextId;
        String replaceTextString;
        String temp[];
        if(choice == 0) {
            locationsClinicHoursDialog.dismiss();
            spinnerLocationsOptions.setSelection(0);
            return true;
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
            if(selectionLocation!=3 | selectionLocation!=5){
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
        buttonLocationsClinicHoursCloseImage.setOnClickListener(locationsCloseDialogListener);

        return true;
    }

    private boolean locationsContactInfoPopup(int choice) {
        if (choice == 0){
            locationsContactInfoDialog.dismiss();
            spinnerLocationsOptions.setSelection(0);
            return true;
        }

        TextView locationsText = (TextView) locationsContactInfoDialog.findViewById(R.id.locationsClinicHoursLocationText);
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

        View buttonLocationsContactInfoCloseImage = locationsContactInfoDialog.findViewById(R.id.buttonLocationsContactInfoClose);
        buttonLocationsContactInfoCloseImage.setOnClickListener(locationsCloseDialogListener);
        return true;
    }

    private View.OnClickListener locationsListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.locationsBackButton:
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    private View.OnClickListener locationsCloseDialogListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.buttonLocationsClinicHoursClose:
                    locationsPopup(0);
                    break;
                case R.id.buttonLocationsContactInfoClose:
                    locationsPopup(1);
                    break;
                default:
                    break;
            }
        }
    };

    }

}
