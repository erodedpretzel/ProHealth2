package vpchc.valleyprohealth;

import org.vpchc.valleyprohealth.R;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class FormsActivity extends AppCompatActivity {

    private Spinner spinnerFormsCategories;
    private Spinner spinnerFormsSelection;
    private String[]finalForms = {"Select a form"};
    int selectionCategory = 0;
    int selectionLocation = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Spinner spinnerFormsLocations;

        //Initial setup of activity
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

        //Setup arrays used as spinner items
        String locations[] = getResources().getStringArray(R.array.vpchc_locations2);
        String categories[] = getResources().getStringArray(R.array.forms_categories);

        //Setup locations spinner
        spinnerFormsLocations = (Spinner) findViewById(R.id.spinnerFormsLocations);
        ArrayAdapter<String> adapterFormsLocations = new ArrayAdapter<>(getApplicationContext(),
                R.layout.fancy_spinner_item, locations);
        adapterFormsLocations.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
        spinnerFormsLocations.setAdapter(adapterFormsLocations);

        //Setup categories spinner
        spinnerFormsCategories = (Spinner) findViewById(R.id.spinnerFormsCategories);
        final ArrayAdapter<String> adapterFormsCategories = new ArrayAdapter<>(getApplicationContext(),
                R.layout.fancy_spinner_item, categories);
        adapterFormsCategories.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
        spinnerFormsCategories.setAdapter(adapterFormsCategories);

        //Setup selection spinner
        spinnerFormsSelection = (Spinner) findViewById(R.id.spinnerFormsSelection);
        final ArrayAdapter<String> adapterFormsSelection = new ArrayAdapter<>(getApplicationContext(),
                R.layout.fancy_spinner_item, finalForms);
        adapterFormsSelection.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
        spinnerFormsSelection.setAdapter(adapterFormsSelection);
        spinnerFormsSelection.setVisibility(View.GONE);

        //Sets the preferred location
        CommonFunc.sharedPrefSet(this, spinnerFormsLocations, spinnerFormsCategories, false);

        spinnerFormsLocations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position) {
                    case 0:
                        break;
                    default:
                        spinnerFormsLocationSelectionSetup(position);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}

        });

        spinnerFormsCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position) {
                    case 0:
                        break;
                    default:
                        formsSetup(position);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });

        spinnerFormsSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position) {
                    case 0:
                        break;
                    default:
                        formsDownload(position - 1);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });
    }

    private void spinnerFormsLocationSelectionSetup(int selection){
    /*
        Arguments:   selection(location selected by user,
                      0 - Directions chosen, 1 - location chosen)
	    Description: Populates the forms selection based on the category chosen
	    Returns:     void
    */
        if(selection == 0){
            spinnerFormsCategories.setVisibility(View.GONE);
        }else{
            spinnerFormsCategories.setVisibility(View.VISIBLE);
        }
        spinnerFormsSelection.setVisibility(View.GONE);
        spinnerFormsCategories.setSelection(0);
        spinnerFormsSelection.setSelection(0);
        selectionLocation = selection;
    }

    private void formsSetup(int formChoice){
    /*
	    Arguments:   formChoice(1 - forms1, 2 - form2, 3 - forms3, 4 - forms4)
	                 all arrays found in strings.xml
	    Description: Populates the forms selection based on the category chosen
	    Returns:     void
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

    private void formsDownload(int formSelection){
    /*
	    Arguments:   formSelection(index of array of URLs)
	    Description: Gets URL from an array based on user's selection
	                 and opens that URL to download the form
	    Returns:     void
    */
        String formURL;

        //Lets the user know that they are downloading the form.
        String toastText = getResources().getString(R.string.toast_forms_download);
        Toast.makeText(getApplicationContext(), toastText ,Toast.LENGTH_SHORT).show();

        //Selects appropriate URL based on user's selection
        formURL = formsSelectURL(formSelection);

        //Opens the URL
        formOpenURL(formURL);
    }

    private String formsSelectURL(int formSelection){
    /*
	    Arguments:   formSelection(index of array of URLs)
	    Description: Selects the appropriate URL based on user's selection
	    Returns:     URL string of form to download
    */
        String[] urlArray = {};

        if(selectionCategory == 1){//Consent
            if(formSelection == 3){
                urlArray = getResources().getStringArray(R.array.forms1_release_url);
                formSelection = selectionLocation - 1;
            }else if(formSelection == 4){
                formSelection = 3;
            }else{
                urlArray = getResources().getStringArray(R.array.forms1_url);
            }
        }else if(selectionCategory == 2) {//New Patient
            if ((selectionLocation == 1)) {
                urlArray = getResources().getStringArray(R.array.forms2_bloom_url);
            }else if (selectionLocation == 2) {
                urlArray = getResources().getStringArray(R.array.forms2_cay_url);
            }else if (selectionLocation == 3){
                urlArray = getResources().getStringArray(R.array.forms2_clint_url);
            }else if (selectionLocation == 4 ) {
                urlArray = getResources().getStringArray(R.array.forms2_craw_url);
            } else if (selectionLocation == 5) {
                urlArray = getResources().getStringArray(R.array.forms2_terre_url);
            }
        }else if(selectionCategory == 3){//Notice
            urlArray = getResources().getStringArray(R.array.forms3_url);
        }else if(selectionCategory == 4){//Sliding Fee Scale
            urlArray = getResources().getStringArray(R.array.forms4_url);
        }else{//Student
            urlArray = getResources().getStringArray(R.array.forms5_url);
        }

        return urlArray[formSelection];
    }

    private void formOpenURL(String formURL){
    /*
        Arguments:   formURL(URL string of form to download)
        Description: Opens URL of form which, depending on browser, will download or open form
        Returns:     void
    */
        Intent downloadFormIntent;
        Uri formUri = Uri.EMPTY;

        formUri = Uri.parse(formURL);
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
