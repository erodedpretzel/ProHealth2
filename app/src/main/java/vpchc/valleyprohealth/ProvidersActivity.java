package vpchc.valleyprohealth;

import org.vpchc.valleyprohealth.R;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.view.View;
import android.widget.TextView;

public class ProvidersActivity extends AppCompatActivity {
    String providersList[]={};
    String providerTypes[]={};
    String locations[]={};
    String easy_locations[]={};

    private int selectionProviderLocation;
    private boolean dentalCheck = false;
    private boolean medOnlyCheck = false;

    private Spinner spinnerProviderType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Spinner spinnerProviderLocations;

        //Initial setup of activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_providers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarProviders);
        setSupportActionBar(toolbar);

        //Sets company logo text to custom font due to it being unavailable natively
        String fontPath = "fonts/franklinGothicMedium.ttf";
        TextView titleText = (TextView) findViewById(R.id.title_providers);
        Typeface customTitleText = Typeface.createFromAsset(getAssets(), fontPath);
        titleText.setTypeface(customTitleText);

        //Back button listener
        View buttonBack = findViewById(R.id.backButtonProviders);
        buttonBack.setOnClickListener(providerListener);

        //Setup arrays used as spinner items
        locations = getResources().getStringArray(R.array.vpchc_locations);
        providerTypes = getResources().getStringArray(R.array.provider_types);
        easy_locations = getResources().getStringArray(R.array.easy_locations);

        spinnerProviderLocations = (Spinner)findViewById(R.id.spinnerProviderLocations);
        ArrayAdapter<String>adapterProviderLocations = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.fancy_spinner_item,locations);
        adapterProviderLocations.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
        spinnerProviderLocations.setAdapter(adapterProviderLocations);

        spinnerProviderType = (Spinner)findViewById(R.id.spinnerProviderType);
        ArrayAdapter<String>adapterProviderType = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.fancy_spinner_item,providerTypes);
        adapterProviderType.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
        spinnerProviderType.setAdapter(adapterProviderType);

        //Sets the preferred location
        CommonFunc.sharedPrefSet(this, spinnerProviderLocations, spinnerProviderType, false);

        spinnerProviderLocations.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position) {
                    case 0:
                        spinnerProviderType.setSelection(0);
                        spinnerProviderType.setVisibility(View.GONE);
                        break;
                    default:
                        selectionProviderLocation = position;
                        providerTypeChange();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}

        });

        spinnerProviderType.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position) {
                    case 0:
                        break;
                    default:
                        providerTypeSelection(position);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}

        });

    }

    private void providerTypeChange(){
    /*
	    Arguments:   none
	    Description: Changes the provider type listing.
	    Returns:     void
    */
        if(selectionProviderLocation == 5) {//Rockville
            providerTypes = getResources().getStringArray(R.array.provider_types3);
            dentalCheck = false;
            medOnlyCheck = true;
        }else if(selectionProviderLocation == 2){//Cayuga
            providerTypes = getResources().getStringArray(R.array.provider_types2);
            dentalCheck = true;
            medOnlyCheck = false;
        }else{
            providerTypes = getResources().getStringArray(R.array.provider_types);
            dentalCheck = false;
            medOnlyCheck = false;
        }

        //Setup Provider Type spinner with new type list
        spinnerProviderType = (Spinner) findViewById(R.id.spinnerProviderType);
        final ArrayAdapter<String> adapterProviderType = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.fancy_spinner_item, providerTypes);
        adapterProviderType.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
        spinnerProviderType.setAdapter(adapterProviderType);
        spinnerProviderType.setVisibility(View.VISIBLE);
        spinnerProviderType.setSelection(0);
    }

    private void providerTypeSelection(int userSelection){
    /*
	    Arguments:   userSelection(provider type selected by the user)
	    Description: Sets the list of providers, resets the provider type picker,
	                 and opens the provider dialog.
	    Returns:     void
    */
        //Set the list of providers based on the users selection.
        providerListSet(userSelection);

        //Reset provider type spinner
        spinnerProviderType.setSelection(0);

        //Opens provider dialog
        providersPopup(userSelection);
    }

    private void providerListSet(int userSelection){
    /*
	    Arguments:   userSelection(provider type selected by the user)
	    Description: Sets the list of providers.
	    Returns:     void
    */
        String arraySearchString;

        //Select string based on user selection
        if(userSelection == 1){
            if(medOnlyCheck) {
                arraySearchString = "providers_medical_" + easy_locations[selectionProviderLocation - 1];
            }else{
                arraySearchString = "providers_bh_" + easy_locations[selectionProviderLocation - 1];
            }
        }else if(userSelection == 2){
            if(dentalCheck){
                arraySearchString = "providers_dental_" + easy_locations[selectionProviderLocation - 1];
            }else{
                arraySearchString = "providers_medical_" + easy_locations[selectionProviderLocation - 1];
            }
        }else{
            arraySearchString = "providers_medical_" + easy_locations[selectionProviderLocation - 1];
        }

        //Retrieve array using userSelection string and assign to providersList
        int arraySearchID = getResources().getIdentifier(arraySearchString, "array", getPackageName());
        providersList = getResources().getStringArray(arraySearchID);
    }

    private void providersPopup(int userSelectedProviderType){
    /*
	    Arguments:   userSelectedProviderType(provider type selected by the user)
	    Description: Displays a dialog with the providers listed from the chosen clinic
	                 and provider type.
	    Returns:     void
    */
        //Initialize the dialog
        int[] options = new int[] {3, 0};
        String[] titleText = new String[] {providerTypes[userSelectedProviderType], locations[selectionProviderLocation]};
        DialogSetup.Content.contentSetup(this, "providers", options, titleText, providersList);
    }

    private View.OnClickListener providerListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.backButtonProviders:
                    finish();
                    v.setSelected(true);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
