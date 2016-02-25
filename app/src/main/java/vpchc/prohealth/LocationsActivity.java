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
            "Clinton", "Crawfordsville", "Terre Haute"};
    private static final String[]options = {"Please select an option", "Clinic Hours", "Contact Info",
            "Get Directions"};
    private static final String[]contactinfo = {"201 W. Academy St.","Bloomingdale, IN 47832",
            "(765) 498-9000","(765) 798-9004","114 N Division St.","Cayuga, IN 47928",
            "(765) 492-9042","(765) 492-9048","777 S. Main Street, Suite 100","Clinton, IN 47842",
            "(765) 828-1003","(765) 828-1030","1810 Layfayette Ave","Crawfordsville, IN 47933",
            "(765) 362-5100","(765) 362-5171","1530 North 7th Site, Suite 201",
            "Terre Haute, IN 47807","(812) 238-7631","(812) 238-7003"};
    private int selectionLocation;;
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
                R.layout.fancy_spinner_item,options);
        adapterLocationsOptions.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
        spinnerLocationsOptions.setAdapter(adapterLocationsOptions);
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
                        locationsClinicHoursPopup(1);
                    case 2:
                        locationsContactInfoPopup(1);
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
        int contactInfoIndex;
        int replaceTextId;
        String replaceTextString;
        String temp[];
        if(choice == 0) {
            locationsClinicHoursDialog.dismiss();
            spinnerLocationsOptions.setSelection(0);
            return true;
        }else{
            locationsClinicHoursDialog = new Dialog(LocationsActivity.this);
            locationsClinicHoursDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            locationsClinicHoursDialog.setContentView(R.layout.locations_clinichours_dialog);
            locationsClinicHoursDialog.show();
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
        buttonLocationsClinicHoursCloseImage.setOnClickListener(locationsCloseDialogListener);

        return true;
    }

    private boolean locationsContactInfoPopup(int choice) {
        int i;
        int contactInfoIndex;
        int count=0;
        int replaceTextId;
        String replaceTextString;
        String temp[];
        if(choice == 0){
            locationsContactInfoDialog.dismiss();
            spinnerLocationsOptions.setSelection(0);
            return true;
        }else{
            locationsContactInfoDialog = new Dialog(LocationsActivity.this);
            locationsContactInfoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            locationsContactInfoDialog.setContentView(R.layout.locations_contactinfo_dialog);
            locationsContactInfoDialog.show();
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

        contactInfoIndex = (selectionLocation - 1) * 4;
        for(i=2;i<=7;i++){
            replaceTextString = "locationsContactInfoText" + i;
            Log.w("myApp", replaceTextString);
            replaceTextId = getResources().getIdentifier(replaceTextString, "id", getPackageName());
            TextView tempText = (TextView) locationsContactInfoDialog.findViewById(replaceTextId);
            tempText.setText(contactinfo[contactInfoIndex + count++]);
            if(i>2){
                i++;
            }
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
                    locationsClinicHoursPopup(0);
                    break;
                case R.id.buttonLocationsContactInfoClose:
                    locationsContactInfoPopup(0);
                    break;
                default:
                    break;
            }
        }
    };

}
