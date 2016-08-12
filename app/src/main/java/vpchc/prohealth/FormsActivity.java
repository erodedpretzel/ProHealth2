package vpchc.prohealth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class FormsActivity extends AppCompatActivity {

    private String[]finalForms={"Select a form"};

    int selectionLocation=0;
    int selectionCategory=0;

    private Spinner spinnerFormsLocations;
    private Spinner spinnerFormsCategories;
    private Spinner spinnerFormsSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forms);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarForms);
        setSupportActionBar(toolbar);

        //Sets company logo text to custom font due to it being unavailable natively
        String fontPath = "fonts/franklinGothicMedium.ttf";
        TextView titleText = (TextView) findViewById(R.id.title_forms);
        Typeface customTitleText = Typeface.createFromAsset(getAssets(), fontPath);
        titleText.setTypeface(customTitleText);

        //Back button listener
        View buttonBack = findViewById(R.id.backButtonForms);
        buttonBack.setOnClickListener(formsListener);

        String locations[]  = {};
        String categories[] = {};

        locations = getResources().getStringArray(R.array.vpchc_locations2);
        categories = getResources().getStringArray(R.array.forms_categories);

        spinnerFormsLocations = (Spinner) findViewById(R.id.spinnerFormsLocations);
        ArrayAdapter<String> adapterFormsLocations = new ArrayAdapter<>(getApplicationContext(),
                R.layout.fancy_spinner_item, locations);
        adapterFormsLocations.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
        spinnerFormsLocations.setAdapter(adapterFormsLocations);

        spinnerFormsCategories = (Spinner) findViewById(R.id.spinnerFormsCategories);
        final ArrayAdapter<String> adapterFormsCategories = new ArrayAdapter<>(getApplicationContext(),
                R.layout.fancy_spinner_item, categories);
        adapterFormsCategories.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
        spinnerFormsCategories.setAdapter(adapterFormsCategories);

        spinnerFormsSelection = (Spinner) findViewById(R.id.spinnerFormsSelection);
        final ArrayAdapter<String> adapterFormsSelection = new ArrayAdapter<>(getApplicationContext(),
                R.layout.fancy_spinner_item, finalForms);
        adapterFormsSelection.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
        spinnerFormsSelection.setAdapter(adapterFormsSelection);
        spinnerFormsSelection.setVisibility(View.GONE);

        //Sets the preferred location
        SharedPreferences pref = getSharedPreferences("prefLocation", MODE_PRIVATE);
        int locationPref = pref.getInt("prefLocation", 0);
        if (locationPref == 6) {
            locationPref = 0;//This sets MSBHC to no preference due to no specific options for it
        }
        spinnerFormsLocations.setSelection(locationPref);
        if (locationPref == 0) {
            spinnerFormsCategories.setVisibility(View.GONE);
        }

        spinnerFormsLocations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position) {
                    case 0:
                        spinnerFormsCategories.setSelection(0);
                        spinnerFormsSelection.setSelection(0);
                        spinnerFormsCategories.setVisibility(View.GONE);
                        spinnerFormsSelection.setVisibility(View.GONE);
                        break;
                    case 1:
                        spinnerFormsCategories.setVisibility(View.VISIBLE);
                        spinnerFormsCategories.setSelection(0);
                        spinnerFormsSelection.setSelection(0);
                        selectionLocation = 1;
                        break;
                    case 2:
                        spinnerFormsCategories.setVisibility(View.VISIBLE);
                        spinnerFormsCategories.setSelection(0);
                        spinnerFormsSelection.setSelection(0);
                        selectionLocation = 2;
                        break;
                    case 3:
                        spinnerFormsCategories.setVisibility(View.VISIBLE);
                        spinnerFormsCategories.setSelection(0);
                        spinnerFormsSelection.setSelection(0);
                        selectionLocation = 3;
                        break;
                    case 4:
                        spinnerFormsCategories.setVisibility(View.VISIBLE);
                        spinnerFormsCategories.setSelection(0);
                        spinnerFormsSelection.setSelection(0);
                        selectionLocation = 4;
                        break;
                    case 5:
                        spinnerFormsCategories.setVisibility(View.VISIBLE);
                        spinnerFormsCategories.setSelection(0);
                        spinnerFormsSelection.setSelection(0);
                        selectionLocation = 5;
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        spinnerFormsCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (Locale.getDefault().getLanguage() == "es") {
                    switch (position) {
                        case 0:
                            break;
                        case 1:
                            formsSetup(1);
                            break;
                        case 2:
                            formsSetup(2);
                            break;
                        case 3:
                            formsSetup(3);
                            break;
                    }
                } else {
                    switch (position) {
                        case 0:
                            break;
                        case 1:
                            formsSetup(1);
                            break;
                        case 2:
                            formsSetup(2);
                            break;
                        case 3:
                            formsSetup(3);
                            break;
                        case 4:
                            formsSetup(4);
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

        spinnerFormsSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (selectionCategory == 1) {
                    if(Locale.getDefault().getLanguage() == "es"){
                        switch (position) {
                            case 0:
                                break;
                            case 1:
                                formsDownload(0);
                                break;
                        }
                    }else {
                        switch (position) {
                            case 0:
                                break;
                            case 1:
                                formsDownload(0);
                                break;
                            case 2:
                                formsDownload(1);
                                break;
                            case 3:
                                formsDownload(2);
                                break;
                            case 4:
                                formsDownload(3);
                                break;
                            case 5:
                                formsDownload(4);
                                break;
                        }
                    }
                } else if (selectionCategory == 2) {
                    switch (position) {
                        case 0:
                            break;
                        case 1:
                            formsDownload(0);
                            break;
                        case 2:
                            formsDownload(1);
                            break;
                        case 3:
                            formsDownload(2);
                            break;
                        }
                } else if (selectionCategory == 3) {
                    switch (position) {
                        case 0:
                            break;
                        case 1:
                            formsDownload(0);
                            break;
                        case 2:
                            formsDownload(1);
                            break;
                        case 3:
                            formsDownload(2);
                            break;
                    }
                } else if (selectionCategory == 4 & Locale.getDefault().getLanguage() != "es" ) {
                    switch (position) {
                        case 0:
                            break;
                        case 1:
                            formsDownload(0);
                            break;
                        case 2:
                            formsDownload(1);;
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
    }

    private void formsSetup(int formChoice){
    /*
	    Arguments:   formChoice(1 - forms1, 2 - form2, 3 - forms3, 4 - forms4) all arrays found in strings.xml
	    Description: Populates the forms selection based on the category chosen
	    Returns:     Nothing
    */
        //Gets correct forms array
        String replaceTextString = "forms" + formChoice;
        int replaceTextId = getResources().getIdentifier(replaceTextString, "array", getPackageName());
        finalForms = getResources().getStringArray(replaceTextId);

        //Recreates adapter to change the available forms from the form category chosen.
        //TODO: There should be a cleaner way to update the spinner without recreating it.
        selectionCategory = formChoice;
        spinnerFormsSelection = (Spinner) findViewById(R.id.spinnerFormsSelection);
        final ArrayAdapter<String> adapterFormsSelection = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.fancy_spinner_item, finalForms);
        adapterFormsSelection.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
        spinnerFormsSelection.setAdapter(adapterFormsSelection);
        spinnerFormsSelection.setVisibility(View.VISIBLE);
        spinnerFormsSelection.setSelection(0);
    }

    private void formsDownload(int selectForm){
    /*
	    Arguments:   selectForm(index of array of URLs)
	    Description: Gets URL from an array based on selection and opens that URL to download form
	    Returns:     Nothing
    */
        Intent downloadFormIntent;
        Uri formUri = Uri.EMPTY;
        String urlArray[]={};

        //Lets the user know that they are downloading the form.
        String toastText = getResources().getString(R.string.toast_forms_download);
        Toast.makeText(getApplicationContext(), toastText ,Toast.LENGTH_SHORT).show();

        //Selects appropriate URL based on selection
        if(selectionCategory == 1){
            if(selectForm == 3){
                urlArray = getResources().getStringArray(R.array.forms1_release_url);
                selectForm = selectionLocation - 1;
            }else if(selectForm == 4){
                selectForm = 3;
            }else{
                urlArray = getResources().getStringArray(R.array.forms1_url);
            }
        }else if(selectionCategory == 2){
            if((selectionLocation >= 1 && selectionLocation <= 3)) {
                urlArray = getResources().getStringArray(R.array.forms2_bloomcayclint_url);
            }else if (selectionLocation == 4 ) {
                urlArray = getResources().getStringArray(R.array.forms2_craw_url);
            } else if (selectionLocation == 5) {
                urlArray = getResources().getStringArray(R.array.forms2_terre_url);
            }
        }else if(selectionCategory == 3){
            urlArray = getResources().getStringArray(R.array.forms3_url);
        }else if(selectionCategory == 4){
            urlArray = getResources().getStringArray(R.array.forms4_url);
        }

        //Opens the URL
        formUri = Uri.parse(urlArray[selectForm]);
        downloadFormIntent = new Intent(Intent.ACTION_VIEW, formUri);
        startActivity(downloadFormIntent);
    }


    private View.OnClickListener formsListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.backButtonForms:
                    finish();
                    v.setSelected(true);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        spinnerFormsSelection.setSelection(0);
    }


}
