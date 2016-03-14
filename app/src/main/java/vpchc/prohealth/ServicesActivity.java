package vpchc.prohealth;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class ServicesActivity extends AppCompatActivity {

    private Spinner spinnerServicesLocations;
    private Spinner spinnerServicesCategories;
    private int selectionProviderLocation;
    private int selectionProviderCategory;

    Dialog servicesDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View buttonBack = findViewById(R.id.servicesBackButton);
        buttonBack.setOnClickListener(servicesListener);

        String locations[];
        String categories[];
        locations = getResources().getStringArray(R.array.vpchc_locations);
        categories = getResources().getStringArray(R.array.services_categories);

        spinnerServicesLocations = (Spinner)findViewById(R.id.spinnerServicesLocations);
        ArrayAdapter<String> adapterServicesLocations = new ArrayAdapter<String>(ServicesActivity.this,
                R.layout.fancy_spinner_item,locations);
        adapterServicesLocations.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
        spinnerServicesLocations.setAdapter(adapterServicesLocations);

        spinnerServicesCategories= (Spinner)findViewById(R.id.spinnerServicesCategories);
        ArrayAdapter<String> adapterServicesCategories= new ArrayAdapter<String>(ServicesActivity.this,
                R.layout.fancy_spinner_item,categories);
        adapterServicesCategories.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
        spinnerServicesCategories.setAdapter(adapterServicesCategories);

        SharedPreferences pref = getSharedPreferences("prefLocation", MODE_PRIVATE);
        int locationPref = pref.getInt("prefLocation", 0);
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
                        break;
                    case 1:
                        spinnerServicesCategories.setSelection(0);
                        spinnerServicesCategories.setVisibility(View.VISIBLE);
                        selectionProviderLocation = 1;
                        break;
                    case 2:
                        spinnerServicesCategories.setSelection(0);
                        spinnerServicesCategories.setVisibility(View.VISIBLE);
                        selectionProviderLocation = 2;
                        break;
                    case 3:
                        spinnerServicesCategories.setSelection(0);
                        spinnerServicesCategories.setVisibility(View.VISIBLE);
                        selectionProviderLocation = 3;
                        break;
                    case 4:
                        spinnerServicesCategories.setSelection(0);
                        spinnerServicesCategories.setVisibility(View.VISIBLE);
                        selectionProviderLocation = 4;
                        break;
                    case 5:
                        spinnerServicesCategories.setSelection(0);
                        spinnerServicesCategories.setVisibility(View.VISIBLE);
                        selectionProviderLocation = 5;
                        break;
                    case 6:
                        spinnerServicesCategories.setSelection(0);
                        spinnerServicesCategories.setVisibility(View.VISIBLE);
                        selectionProviderLocation = 6;
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
                        selectionProviderCategory = 1;
                        servicesPopup(1);
                        break;
                    case 2:
                        selectionProviderCategory = 2;
                        servicesPopup(1);
                        break;
                    case 3:
                        selectionProviderCategory = 3;
                        servicesPopup(1);
                        break;
                    case 4:
                        selectionProviderCategory = 4;
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
        int i;
        int replaceTextId;
        String categories[]={};
        String replaceTextString;
        String availableServices[]={};

        if(choice == 0) {
            servicesDialog.dismiss();
            spinnerServicesCategories.setSelection(0);
            return true;
        }else{
            servicesDialog = new Dialog(ServicesActivity.this);
            servicesDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            servicesDialog.setContentView(R.layout.services_dialog);
            servicesDialog.show();
            servicesDialog.setCancelable(false);
            servicesDialog.setCanceledOnTouchOutside(false);
        }

        categories = getResources().getStringArray(R.array.services_categories);
        TextView titleText = (TextView) servicesDialog.findViewById(R.id.servicesTitleText);
        if(selectionProviderCategory == 1){
            titleText.setText(categories[1]);
            availableServices = getResources().getStringArray(R.array.BehavioralHealth);
        }else if(selectionProviderCategory == 2){
            availableServices = getResources().getStringArray(R.array.Dental);
            titleText.setText(categories[2]);
        }else if(selectionProviderCategory == 3){
            availableServices = getResources().getStringArray(R.array.PatientSupport);
            titleText.setText(categories[3]);
        }else if(selectionProviderCategory == 4){
            if(selectionProviderLocation==5) {
                availableServices = getResources().getStringArray(R.array.PrimaryCare2);
            }else{
                availableServices = getResources().getStringArray(R.array.PrimaryCare1);
            }
            titleText.setText(categories[4]);
        }

        TextView locationsText = (TextView) servicesDialog.findViewById(R.id.servicesLocationsText);
        if(selectionProviderLocation == 1){
            locationsText.setText("Bloomingdale");
        }else if(selectionProviderLocation == 2){
            locationsText.setText("Cayuga");
        }else if(selectionProviderLocation == 3){
            locationsText.setText("Clinton");
        }else if(selectionProviderLocation == 4){
            locationsText.setText("Crawfordsville");
        }else if(selectionProviderLocation == 5){
            locationsText.setText("Terre Haute");
        }


        for(i=1;i<=availableServices.length;i++){
            replaceTextString = "servicesText" + i;
            replaceTextId = getResources().getIdentifier(replaceTextString, "id", getPackageName());
            TextView tempText = (TextView) servicesDialog.findViewById(replaceTextId);
            tempText.setText(availableServices[i-1]);
        }


        View buttonServicesDialogClose = servicesDialog.findViewById(R.id.buttonServicesDialogClose);
        buttonServicesDialogClose.setOnClickListener(servicesListener);


        return true;
    }

    private View.OnClickListener servicesListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.servicesBackButton:
                    finish();
                    ImageView backButton = (ImageView) findViewById(R.id.servicesBackButton);
                    backButton.setImageResource(R.drawable.back_arrow_on);
                    break;
                case R.id.buttonServicesDialogClose:
                    ImageView closeButton = (ImageView) servicesDialog.findViewById(R.id.buttonServicesDialogClose);
                    closeButton.setImageResource(R.drawable.dialog_close_on);
                    servicesPopup(0);
                    break;
                default:
                    break;
            }
        }
    };
}
