package vpchc.prohealth;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.Window;
import android.widget.AdapterView.OnItemSelectedListener;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ProvidersActivity extends AppCompatActivity {


    private Spinner spinnerProviderType;
    private Spinner spinnerProviderLocations;
    private boolean selectionProviderType;
    private int selectionProviderLocation;
    private static final String[]types = {"Select a type of Provider", "Behavioral Health",
            "Medical"};
    private static final String[]locations = {"Select a location", "Bloomingdale", "Cayuga",
            "Clinton", "Crawfordsville", "Terre Haute", "MSBHC"};
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
                                              "Gwen Morison, MD","0","0","0","1","0","0",
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


        spinnerProviderLocations = (Spinner)findViewById(R.id.spinnerProviderLocations);
        ArrayAdapter<String>adapterProviderLocations = new ArrayAdapter<String>(ProvidersActivity.this,
                R.layout.fancy_spinner_item,locations);
        adapterProviderLocations.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
        spinnerProviderLocations.setAdapter(adapterProviderLocations);

        spinnerProviderType = (Spinner)findViewById(R.id.spinnerProviderType);
        ArrayAdapter<String>adapterProviderType = new ArrayAdapter<String>(ProvidersActivity.this,
                R.layout.fancy_spinner_item,types);
        adapterProviderType.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
        spinnerProviderType.setAdapter(adapterProviderType);

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
                        providersPopup(0);
                        break;
                    case 2:
                        selectionProviderType = true;
                        providersPopup(0);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

    }

    private View.OnClickListener providerListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.providerBackButton:
                    finish();
                    ImageView backButton = (ImageView) findViewById(R.id.providerBackButton);
                    backButton.setImageResource(R.drawable.back_arrow_on);
                    break;
                default:
                    break;
            }
        }
    };


    private boolean providersPopup(int choice){
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
            providersDialog = new Dialog(ProvidersActivity.this);
            providersDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            providersDialog.setContentView(R.layout.providers_dialog);
            providersDialog.show();
        }else{
            providersDialog.dismiss();
            spinnerProviderType.setSelection(0);
            return true;
        }

        TextView typeText = (TextView) providersDialog.findViewById(R.id.providersTypeText);
        locationIndex = selectionProviderLocation;
        if(selectionProviderType == false){
            temp = behavioralproviders;
            typeText.setText("Behavioral Health Providers");
        }else{
            temp = medicalproviders;
            typeText.setText("Medical Providers");
        }

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



        View buttonProvidersCloseImage = providersDialog.findViewById(R.id.buttonProvidersClose);
        buttonProvidersCloseImage.setOnClickListener(callListener);


        return true;
    }

    private View.OnClickListener callListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.buttonProvidersClose:
                    ImageView closeButton = (ImageView) providersDialog.findViewById(R.id.buttonProvidersClose);
                    closeButton.setImageResource(R.drawable.dialog_close_on);
                    providersPopup(1);
                    break;
                default:
                    break;
            }
        }
    };

}
