package vpchc.valleyprohealth;

import org.vpchc.valleyprohealth.R;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class ServicesActivity extends AppCompatActivity {

    private boolean dentalCheck = false;
    private boolean medOnlyCheck = false;
    private int userSelectedServicesLocation;
    private Spinner spinnerServicesCategories;

    String availableServices[]={};
    String categories[];
    String locations[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Spinner spinnerServicesLocations;

        //Initial setup of activity
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

        //Setup arrays used as spinner items
        locations = getResources().getStringArray(R.array.vpchc_locations2);
        categories = getResources().getStringArray(R.array.services_categories);

        //Setup locations spinner
        spinnerServicesLocations = (Spinner)findViewById(R.id.spinnerServicesLocations);
        ArrayAdapter<String> adapterServicesLocations = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.fancy_spinner_item,locations);
        adapterServicesLocations.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
        spinnerServicesLocations.setAdapter(adapterServicesLocations);

        //Setup categories spinner
        spinnerServicesCategories= (Spinner)findViewById(R.id.spinnerServicesCategories);
        ArrayAdapter<String> adapterServicesCategories= new ArrayAdapter<String>(getApplicationContext(),
                R.layout.fancy_spinner_item,categories);
        adapterServicesCategories.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
        spinnerServicesCategories.setAdapter(adapterServicesCategories);

        //Sets the preferred location
        CommonFunc.sharedPrefSet(this, spinnerServicesLocations, spinnerServicesCategories, true);

        spinnerServicesLocations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position) {
                    case 0:
                        spinnerServicesCategories.setSelection(0);
                        spinnerServicesCategories.setVisibility(View.GONE);
                        break;
                    default:
                        userSelectedServicesLocation = position;
                        servicesCategoriesChange();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}

        });

        spinnerServicesCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position) {
                    case 0:
                        spinnerServicesCategories.setSelection(0);
                        break;
                    default:
                        servicesCategorySelected(position);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}

        });

    }

    private void servicesCategoriesChange(){
    /*
	    Arguments:   none
	    Description: Changes the services category listing to show or remove dental depending on
	                 the location.
	    Returns:     void
    */

        if(userSelectedServicesLocation == 5){
            categories  = getResources().getStringArray(R.array.services_categories3);
            dentalCheck = false;
            medOnlyCheck = true;
        }
        else if(userSelectedServicesLocation == 1 || userSelectedServicesLocation == 2){
            categories  = getResources().getStringArray(R.array.services_categories2);
            dentalCheck = true;
            medOnlyCheck = false;
        }else{
            categories  = getResources().getStringArray(R.array.services_categories);
            dentalCheck = false;
            medOnlyCheck = false;
        }

        spinnerServicesCategories = (Spinner) findViewById(R.id.spinnerServicesCategories);
        final ArrayAdapter<String> adapterServicesCategories = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.fancy_spinner_item, categories);
        adapterServicesCategories.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
        spinnerServicesCategories.setAdapter(adapterServicesCategories);
        spinnerServicesCategories.setVisibility(View.VISIBLE);
        spinnerServicesCategories.setSelection(0);
    }

    private void servicesCategorySelected(int userSelection){
    /*
        Arguments:   userSelection(category selected by the user)
	    Description: Resets the categories spinner, setup the services available based on
	                 user's selections, and brings up the dialog.
	    Returns:     void
    */
        //Reset categories spinner
        spinnerServicesCategories.setSelection(0);

        //Setup the services available based on user's selections
        availableServicesSetup(userSelection);

        //Opens services dialog
        servicesPopup(userSelection);
    }

    private void availableServicesSetup(int userSelection){
    /*
        Arguments:   userSelection(category selected by the user)
	    Description: Setup the services available based on user's selections.
	    Returns:     void
    */
        if(userSelection == 1){
            if(medOnlyCheck){
                availableServices = getResources().getStringArray(R.array.PrimaryCare1);
            }else{
                availableServices = getResources().getStringArray(R.array.BehavioralHealth);
            }
        }else if(userSelection == 2){
            if(dentalCheck){
                availableServices = getResources().getStringArray(R.array.Dental);
            }else{
                availableServices = getResources().getStringArray(R.array.PatientSupport);
            }
        }else if(userSelection == 3){
            if(dentalCheck){
                availableServices = getResources().getStringArray(R.array.PatientSupport);
            }else{
                if(userSelectedServicesLocation==6){
                    availableServices = getResources().getStringArray(R.array.PrimaryCare2);
                }else{
                    availableServices = getResources().getStringArray(R.array.PrimaryCare1);
                }
            }
        }else{
            if(userSelectedServicesLocation==6){
                availableServices = getResources().getStringArray(R.array.PrimaryCare2);
            }else{
                availableServices = getResources().getStringArray(R.array.PrimaryCare1);
            }
        }
    }

    private void servicesPopup(int userSelectedCategory){
       /*
	    Arguments:   userSelectedCategory(category selected by the user)
	    Description: Displays a dialog with the services listed from the chosen clinic.
	                 and service type.
	    Returns:     void
    */
        //Initialize the dialog
        int[] options = new int[] {3, 0};
        String[] titleText = new String[] {categories[userSelectedCategory], locations[userSelectedServicesLocation]};
        DialogSetup.Content.contentSetup(this, "services", options, titleText, availableServices);
    }

    private View.OnClickListener servicesListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.backButtonServices:
                    finish();
                    v.setSelected(true);
                    break;
                default:
                    break;
            }
        }
    };
}
