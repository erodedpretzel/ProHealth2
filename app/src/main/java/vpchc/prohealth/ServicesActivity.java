package vpchc.prohealth;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class ServicesActivity extends AppCompatActivity {

    String locations[];
    String categories[];
    String availableServices[]={};

    private int selectionServicesLocation;
    private int selectionServicesCategory;

    private Spinner spinnerServicesLocations;
    private Spinner spinnerServicesCategories;

    Dialog servicesDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarServices);
        setSupportActionBar(toolbar);

        //Sets company logo text to custom font due to it being unavailable natively
        String fontPath = "fonts/franklinGothicMedium.ttf";
        TextView titleText = (TextView) findViewById(R.id.title_services);
        Typeface customTitleText = Typeface.createFromAsset(getAssets(), fontPath);
        titleText.setTypeface(customTitleText);

        //Back button listener
        View buttonBack = findViewById(R.id.backButtonServices);
        buttonBack.setOnClickListener(servicesListener);

        locations = getResources().getStringArray(R.array.vpchc_locations2);
        categories = getResources().getStringArray(R.array.services_categories);

        spinnerServicesLocations = (Spinner)findViewById(R.id.spinnerServicesLocations);
        ArrayAdapter<String> adapterServicesLocations = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.fancy_spinner_item,locations);
        adapterServicesLocations.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
        spinnerServicesLocations.setAdapter(adapterServicesLocations);

        spinnerServicesCategories= (Spinner)findViewById(R.id.spinnerServicesCategories);
        ArrayAdapter<String> adapterServicesCategories= new ArrayAdapter<String>(getApplicationContext(),
                R.layout.fancy_spinner_item,categories);
        adapterServicesCategories.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
        spinnerServicesCategories.setAdapter(adapterServicesCategories);

        //Sets the preferred location
        SharedPreferences pref = getSharedPreferences("prefLocation", MODE_PRIVATE);
        int locationPref = pref.getInt("prefLocation", 0);
        if (locationPref == 6) {
            locationPref = 0;//This sets MSBHC to no preference due to no location section for it
        }
        spinnerServicesLocations.setSelection(locationPref);
        if(locationPref == 0){
            spinnerServicesCategories.setVisibility(View.GONE);
        }

        spinnerServicesLocations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position) {
                    case 0:
                        spinnerServicesCategories.setSelection(0);
                        spinnerServicesCategories.setVisibility(View.GONE);
                        break;
                    case 1:
                        spinnerServicesCategories.setSelection(0);
                        spinnerServicesCategories.setVisibility(View.VISIBLE);
                        selectionServicesLocation = 1;
                        break;
                    case 2:
                        spinnerServicesCategories.setSelection(0);
                        spinnerServicesCategories.setVisibility(View.VISIBLE);
                        selectionServicesLocation = 2;
                        break;
                    case 3:
                        spinnerServicesCategories.setSelection(0);
                        spinnerServicesCategories.setVisibility(View.VISIBLE);
                        selectionServicesLocation = 3;
                        break;
                    case 4:
                        spinnerServicesCategories.setSelection(0);
                        spinnerServicesCategories.setVisibility(View.VISIBLE);
                        selectionServicesLocation = 4;
                        break;
                    case 5:
                        spinnerServicesCategories.setSelection(0);
                        spinnerServicesCategories.setVisibility(View.VISIBLE);
                        selectionServicesLocation = 5;
                        break;
                    case 6:
                        spinnerServicesCategories.setSelection(0);
                        spinnerServicesCategories.setVisibility(View.VISIBLE);
                        selectionServicesLocation = 6;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        spinnerServicesCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position) {
                    case 0:
                        spinnerServicesCategories.setSelection(0);
                        break;
                    case 1:
                        selectionServicesCategory = 1;
                        availableServices = getResources().getStringArray(R.array.BehavioralHealth);
                        servicesPopup(1);
                        break;
                    case 2:
                        selectionServicesCategory = 2;
                        availableServices = getResources().getStringArray(R.array.Dental);
                        servicesPopup(1);
                        break;
                    case 3:
                        selectionServicesCategory = 3;
                        availableServices = getResources().getStringArray(R.array.PatientSupport);
                        servicesPopup(1);
                        break;
                    case 4:
                        selectionServicesCategory = 4;
                        if(selectionServicesLocation==5) {
                            availableServices = getResources().getStringArray(R.array.PrimaryCare2);
                        }else{
                            availableServices = getResources().getStringArray(R.array.PrimaryCare1);
                        }
                        servicesPopup(1);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

    }

    private boolean servicesPopup(int choice){
    /*
	    Arguments:   choice(0 - dismiss dialog, 1 - create a dialog)
	    Description: Displays or dismisses a dialog with selected type of services listed
	    Returns:     true
    */
        if(choice == 0) {
            servicesDialog.dismiss();
            spinnerServicesCategories.setSelection(0);
            return true;
        }else{
            //This cond. statement is to make the styling of the dialog look more modern on devices
            //that support it which are API's >= 14
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                servicesDialog = new Dialog(ServicesActivity.this);
            }else{
                servicesDialog = new Dialog(ServicesActivity.this, R.style.AppTheme_NoActionBar);
            }
            servicesDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            servicesDialog.setContentView(R.layout.dialog_services);
            servicesDialog.show();
            servicesDialog.setCancelable(false);
            servicesDialog.setCanceledOnTouchOutside(false);
        }

        //Back button listener
        View buttonServicesDialogClose = servicesDialog.findViewById(R.id.buttonServicesDialogClose);
        buttonServicesDialogClose.setOnClickListener(servicesListener);

        //Sets the preferred location
        TextView titleText = (TextView) servicesDialog.findViewById(R.id.servicesTitleText);
        titleText.setText(categories[selectionServicesCategory]);

        //Sets location title
        TextView locationsTitle = (TextView) servicesDialog.findViewById(R.id.servicesLocationsText);
        locationsTitle.setText(locations[selectionServicesLocation]);

        //Populate list of services based on type of service chosen
        LinearLayout servicesContent = (LinearLayout) servicesDialog.findViewById(R.id.servicesContent);
        for(int i = 0;i < availableServices.length;i++){
            TextView serviceToAdd = new TextView(this);
            serviceToAdd.setText(availableServices[i]);
            serviceToAdd.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.dialog_content));
            serviceToAdd.setTextColor(Color.parseColor("#000000"));
            serviceToAdd.setGravity(Gravity.CENTER);
            servicesContent.addView(serviceToAdd);
            if(i != 0){
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)serviceToAdd.getLayoutParams();
                params.setMargins(0, 50, 0, 0);
                serviceToAdd.setLayoutParams(params);
            }
        }
        return true;
    }

    private View.OnClickListener servicesListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.backButtonServices:
                    finish();
                    v.setSelected(true);
                    break;
                case R.id.buttonServicesDialogClose:
                    servicesPopup(0);
                    break;
                default:
                    break;
            }
        }
    };
}
