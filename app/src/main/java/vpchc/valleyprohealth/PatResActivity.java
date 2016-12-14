package vpchc.valleyprohealth;

import org.vpchc.valleyprohealth.R;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class PatResActivity extends AppCompatActivity {
    String categories[]={};
    String categoryItems[]={};
    private Spinner spinnerCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Initial setup of activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patres);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarPatRes);
        setSupportActionBar(toolbar);

        //Sets company logo text to custom font due to it being unavailable natively
        String fontPath = "fonts/franklinGothicMedium.ttf";
        TextView titleText = (TextView) findViewById(R.id.title_patres);
        Typeface customTitleText = Typeface.createFromAsset(getAssets(), fontPath);
        titleText.setTypeface(customTitleText);

        //Back button listener
        View buttonBack = findViewById(R.id.backButtonPatRes);
        buttonBack.setOnClickListener(patresListener);

        //Setup arrays used as spinner items
        categories = getResources().getStringArray(R.array.patres_categories);

        //Setup categories spinner
        spinnerCategories = (Spinner)findViewById(R.id.spinnerPatResCategories);
        ArrayAdapter<String> adapterCategories = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.fancy_spinner_item, categories);
        adapterCategories.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
        spinnerCategories.setAdapter(adapterCategories);

        spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position) {
                    case 0:
                        break;
                    default:
                        patresCategorySelected(position);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}

        });
    }

    private void patresCategorySelected(int userSelection){
    /*
        Arguments:   userSelection(category selected by the user)
	    Description: Resets the categories spinner, setup the patient resources category based on
	                 user's selections, and brings up the dialog.
	    Returns:     void
    */
        //Resets the categories spinner
        spinnerCategories.setSelection(0);

        //Setup the patient resources category based on user's selections
        selectedCategorySetup(userSelection);

        //Opens patient resources dialog
        patresPopup(userSelection);
    }

    private void selectedCategorySetup(int userSelection){
    /*
        Arguments:   userSelection(category selected by the user)
	    Description: Setup the patient resources category based on user's selections.
	    Returns:     void
    */
        if(userSelection == 1){
            categoryItems = getResources().getStringArray(R.array.patres_categories_diabetes);
        }else if(userSelection == 2){
            categoryItems = getResources().getStringArray(R.array.patres_categories_prescription);
        }else{
            categoryItems = getResources().getStringArray(R.array.patres_categories_scale);
        }
    }

    private void patresPopup(int userSelectedCategory){
    /*
	    Arguments:   userSelectedCategory(category selected by the user)
	    Description: Displays a dialog with the faqs listed from the chosen category.
	    Returns:     void
    */
        //Initialize the dialog
        int[] options      = new int[] {2, 0};
        String[] titleText = new String[] {categories[userSelectedCategory]};
        DialogSetup.Content.contentSetup(this, "patres", options, titleText, categoryItems);
    }

    private View.OnClickListener patresListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.backButtonPatRes:
                    finish();
                    v.setSelected(true);
                    break;
                default:
                    break;
            }
        }
    };
}
