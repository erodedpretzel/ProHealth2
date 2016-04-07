package vpchc.prohealth;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.Window;
import android.widget.AdapterView.OnItemSelectedListener;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.view.View;
import android.widget.TextView;

public class ProvidersActivity extends AppCompatActivity {
    private Spinner spinnerProviderType;
    private Spinner spinnerProviderLocations;
    private boolean selectionProviderType;
    private int selectionProviderLocation;
    Dialog providersDialog;
    private static final String[]medicalproviders = {
                                              "Aziz Abed, MD","0","0","1","0","0","0",
                                              "Leslie Batty, FNP","0","0","0","0","0","1",
                                              "Gretchen Blevins, FNP","0","0","1","0","0","0",
                                              "James Buechler, MD","0","0","0","1","0","0",
                                              "Christi Busenbark, FNP","1","0","0","0","0","0",
                                              "Nicole Cook, FNP","0","0","1","0","1","0",
                                              "Beth Fields, FNP","0","0","0","0","0","1",
                                              "Bing Gale, MD","0","1","1","0","0","0",
                                              "Nicole Hall, FNP","0","0","1","0","0","0",
                                              "Do Hwang, MD","0","0","0","1","0","0",
                                              "Stephen Macke, MD","0","0","1","0","0","0",
                                              "Gwyn Morson, MD","0","0","0","1","0","0",
                                              "Tammi Mundy, FNP","1","0","1","0","0","0",
                                              "Renae Norman, FNP","0","1","0","0","1","0",
                                              "Louwanna Wallace, FNP","1","0","0","0","0","0"};
    private static final String[]behavioralproviders = {
                                              "Linda Lonneman, LCSW","0","0","1","0","0","0",
                                              "David McIntyre, LCAC","0","0","1","0","0","0",
                                              "Sara Ritter, LCSW","0","0","0","0","0","1",
                                              "Lacey Skwortz, LCSW","0","0","0","0","0","1",
                                              "Tasha Stevens, LCSW","1","1","1","0","0","0",
                                              "Paul Taraska, MD","1","1","1","1","1","1",
                                              "Dana Tinkle, LMHCA","0","0","0","1","0","0",
                                              "Julia Wernz, PHD","0","0","1","0","0","0",
                                              "Heather Woods, LMHCA","1","0","1","0","1","0"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_providers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String locations[]={};
        String providerTypes[]={};

        locations = getResources().getStringArray(R.array.vpchc_locations);
        providerTypes = getResources().getStringArray(R.array.provider_types);

        spinnerProviderLocations = (Spinner)findViewById(R.id.spinnerProviderLocations);
        ArrayAdapter<String>adapterProviderLocations = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.fancy_spinner_item,locations);
        adapterProviderLocations.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
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

        View buttonBack = findViewById(R.id.providerBackButton);
        buttonBack.setOnClickListener(providerListener);

        spinnerProviderLocations.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position) {
                    case 0:
                        spinnerProviderType.setSelection(0);
                        break;
                    case 1:
                        spinnerProviderType.setSelection(0);
                        spinnerProviderType.setVisibility(View.VISIBLE);
                        selectionProviderLocation = 1;
                        break;
                    case 2:
                        spinnerProviderType.setSelection(0);
                        spinnerProviderType.setVisibility(View.VISIBLE);
                        selectionProviderLocation = 2;
                        break;
                    case 3:
                        spinnerProviderType.setSelection(0);
                        spinnerProviderType.setVisibility(View.VISIBLE);
                        selectionProviderLocation = 3;
                        break;
                    case 4:
                        spinnerProviderType.setSelection(0);
                        spinnerProviderType.setVisibility(View.VISIBLE);
                        selectionProviderLocation = 4;
                        break;
                    case 5:
                        spinnerProviderType.setSelection(0);
                        spinnerProviderType.setVisibility(View.VISIBLE);
                        selectionProviderLocation = 5;
                        break;
                    case 6:
                        spinnerProviderType.setSelection(0);
                        spinnerProviderType.setVisibility(View.VISIBLE);
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
                        selectionProviderType = false;
                        providersPopup(1);
                        break;
                    case 2:
                        selectionProviderType = true;
                        providersPopup(1);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

    }

    private boolean providersPopup(int choice){
    /*
	    Arguments: choice(0 - dismiss dialog, 1 - create a dialog)
	    Description: Displays or dismisses a dialog with bh or medical providers listed.
	    Returns: True
    */
        int i;
        int count=0;
        int foundCount=1;
        int numberOfProviders;
        int locationIndex;
        int replaceTextId;
        String replaceTextString;
        String titleText;
        String temp[];

        if(choice == 0) {
            providersDialog.dismiss();
            spinnerProviderType.setSelection(0);
            return true;
        }else{
            //This cond. statement is to make the styling of the dialog look more modern on devices
            //that support it which are API's >= 14
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                providersDialog = new Dialog(ProvidersActivity.this);
            }else{
                providersDialog = new Dialog(ProvidersActivity.this, R.style.AppTheme_NoActionBar);
            }
            providersDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            providersDialog.setContentView(R.layout.providers_dialog);
            providersDialog.show();
            providersDialog.setCancelable(false);
            providersDialog.setCanceledOnTouchOutside(false);
        }

        //Sets title
        TextView typeText = (TextView) providersDialog.findViewById(R.id.providersTypeText);
        if(selectionProviderType == false){
            temp = behavioralproviders;
            titleText = getResources().getString(R.string.providers_bhprovider);
            typeText.setText(titleText);
        }else{
            temp = medicalproviders;
            titleText = getResources().getString(R.string.providers_mprovider);
            typeText.setText(titleText);
        }

        //Sets location displayed
        TextView locationsText = (TextView) providersDialog.findViewById(R.id.providersLocationsText);
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
        }else if(selectionProviderLocation == 6){
            locationsText.setText("MSBHC");
        }


        //Populate list of providers depending on provider type and location chosen
        locationIndex = selectionProviderLocation;
        numberOfProviders = temp.length/7;
        for(i=0;i<numberOfProviders;i++){
            if(temp[count+locationIndex]== "1"){
                replaceTextString = "providersText" + foundCount++;
                replaceTextId = getResources().getIdentifier(replaceTextString, "id", getPackageName());
                TextView tempText = (TextView) providersDialog.findViewById(replaceTextId);
                tempText.setText(temp[count]);
            }
            count+=7;
        }

        View buttonProvidersCloseImage = providersDialog.findViewById(R.id.buttonProvidersClose);
        buttonProvidersCloseImage.setOnClickListener(providerListener);
        return true;
    }

    private View.OnClickListener providerListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.providerBackButton:
                    finish();
                    ImageView backButton = (ImageView) findViewById(R.id.providerBackButton);
                    backButton.setImageResource(R.drawable.back_arrow_on);
                    break;
                case R.id.buttonProvidersClose:
                    ImageView closeButton = (ImageView) providersDialog.findViewById(R.id.buttonProvidersClose);
                    closeButton.setImageResource(R.drawable.dialog_close_on);
                    providersPopup(0);
                    break;
                default:
                    break;
            }
        }
    };

}
