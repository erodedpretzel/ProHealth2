package vpchc.valleyprohealth;

import org.vpchc.valleyprohealth.R;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.Window;
import android.widget.AdapterView.OnItemSelectedListener;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.view.View;
import android.widget.TextView;

public class ProvidersActivity extends AppCompatActivity {
    String providerTypes[]={};
    String locations[]={};
    String easy_locations[]={};
    String providersList[]={};
    String arraySearchString;

    private int selectionProviderType;
    private int selectionProviderLocation;
    int arraySearchID;
    private boolean dentalCheck = false;

    private Spinner spinnerProviderType;
    private Spinner spinnerProviderLocations;

    Dialog providersDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        SharedPreferences pref = getSharedPreferences("prefLocation", MODE_PRIVATE);
        int locationPref = pref.getInt("prefLocation", 0);
        spinnerProviderLocations.setSelection(locationPref);
        if(locationPref == 0){
            spinnerProviderType.setVisibility(View.GONE);
        }

        spinnerProviderLocations.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position) {
                    case 0:
                        spinnerProviderType.setSelection(0);
                        spinnerProviderType.setVisibility(View.GONE);
                        break;
                    case 1:
                        providerTypeChange(1);
                        selectionProviderLocation = 1;
                        break;
                    case 2:
                        providerTypeChange(1);
                        selectionProviderLocation = 2;
                        break;
                    case 3:
                        providerTypeChange(0);
                        selectionProviderLocation = 3;
                        break;
                    case 4:
                        providerTypeChange(0);
                        selectionProviderLocation = 4;
                        break;
                    case 5:
                        providerTypeChange(0);
                        selectionProviderLocation = 5;
                        break;
                    case 6:
                        providerTypeChange(0);
                        selectionProviderLocation = 6;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        spinnerProviderType.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        selectionProviderType = 0;
                        arraySearchString = "providers_bh_" + easy_locations[selectionProviderLocation - 1];
                        arraySearchID = getResources().getIdentifier(arraySearchString, "array", getPackageName());
                        providersList = getResources().getStringArray(arraySearchID);
                        providersPopup();
                        spinnerProviderType.setSelection(0);
                        break;
                    case 2:
                        selectionProviderType = 1;
                        if(dentalCheck){
                            arraySearchString = "providers_dental_" + easy_locations[selectionProviderLocation - 1];
                        }else{
                            arraySearchString = "providers_medical_" + easy_locations[selectionProviderLocation - 1];
                        }
                        arraySearchID = getResources().getIdentifier(arraySearchString, "array", getPackageName());
                        providersList = getResources().getStringArray(arraySearchID);
                        providersPopup();
                        spinnerProviderType.setSelection(0);
                        break;
                    case 3:
                        selectionProviderType = 2;
                        arraySearchString = "providers_medical_" + easy_locations[selectionProviderLocation - 1];
                        arraySearchID = getResources().getIdentifier(arraySearchString, "array", getPackageName());
                        providersList = getResources().getStringArray(arraySearchID);
                        providersPopup();
                        spinnerProviderType.setSelection(0);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

    }

    private void providerTypeChange(int choice){
    /*
	    Arguments:   None
	    Description: Changes the provider type listing if a site with dental is chosen.
	    Returns:     Nothing
    */
        if(choice == 0){
            providerTypes = getResources().getStringArray(R.array.provider_types);
            dentalCheck = false;
        }else{
            providerTypes = getResources().getStringArray(R.array.provider_types2);
            dentalCheck = true;
        }

        spinnerProviderType = (Spinner) findViewById(R.id.spinnerProviderType);
        final ArrayAdapter<String> adapterProviderType = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.fancy_spinner_item, providerTypes);
        adapterProviderType.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
        spinnerProviderType.setAdapter(adapterProviderType);
        spinnerProviderType.setVisibility(View.VISIBLE);
        spinnerProviderType.setSelection(0);
    }

    private boolean providersPopup(){
    /*
	    Arguments:   choice(0 - dismiss dialog, 1 - create a dialog)
	    Description: Displays or dismisses a dialog with bh or medical providers listed.
	    Returns:     true
    */
        //Initialize the dialog
        int layoutID = getResources().getIdentifier("dialog_providers", "layout", this.getPackageName());
        int closeID = getResources().getIdentifier("buttonDialogCloseProviders", "id", this.getPackageName());
        int titleID = getResources().getIdentifier("providersTypeText", "id", this.getPackageName());
        int subTitleID = getResources().getIdentifier("providersLocationsText", "id", this.getPackageName());
        int[] IDs = new int[] {layoutID,closeID,titleID,subTitleID};
        String[] titleText = new String[] {providerTypes[selectionProviderType + 1], locations[selectionProviderLocation]};
        providersDialog = DialogSetup.dialogCreate(providersDialog, this, IDs, titleText, 2);

        //Populate list of providers depending on provider type and location chosen
        LinearLayout providersContent = (LinearLayout) providersDialog.findViewById(R.id.providersContent);
        for(int i = 0;i < providersList.length ;i++){
            TextView providerToAdd = new TextView(this);
            providerToAdd.setText(providersList[i]);
            providerToAdd.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.dialog_content));
            providerToAdd.setTextColor(Color.parseColor("#000000"));
            providerToAdd.setGravity(Gravity.CENTER);
            providersContent.addView(providerToAdd);
            if(i != 0){
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)providerToAdd.getLayoutParams();
                params.setMargins(0, 50, 0, 0);
                providerToAdd.setLayoutParams(params);
            }
        }
        return true;
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
