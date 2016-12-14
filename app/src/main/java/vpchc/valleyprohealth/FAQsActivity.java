package vpchc.valleyprohealth;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.vpchc.valleyprohealth.R;

public class FAQsActivity extends AppCompatActivity {
    String categories[]={};
    String faqCategory[]={};

    private Spinner spinnerCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Initial setup of activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarFAQs);
        setSupportActionBar(toolbar);

        //Sets company logo text to custom font due to it being unavailable natively
        String fontPath = "fonts/franklinGothicMedium.ttf";
        TextView titleText = (TextView) findViewById(R.id.title_faqs);
        Typeface customTitleText = Typeface.createFromAsset(getAssets(), fontPath);
        titleText.setTypeface(customTitleText);

        //Back button listener
        View buttonBack = findViewById(R.id.faqsBackButton);
        buttonBack.setOnClickListener(faqsListener);

        //Setup arrays used as spinner items
        categories = getResources().getStringArray(R.array.faqs_categories);

        //Setup categories spinner
        spinnerCategories = (Spinner)findViewById(R.id.spinnerFAQsCategories);
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
                        faqCategorySelected(position - 1);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}

        });

    }

    private void faqCategorySelected(int userSelection){
    /*
        Arguments:   userSelection(category selected by the user)
	    Description: Resets the categories spinner, setup the faqs category based on
	                 user's selections, and brings up the dialog.
	    Returns:     void
    */
        //Resets the categories spinner
        spinnerCategories.setSelection(0);

        //Setup the faqs category based on user's selections
        selectedCategorySetup(userSelection);

        //Opens faqs dialog
        faqsPopup(userSelection);
    }

    private void selectedCategorySetup(int userSelection){
    /*
        Arguments:   userSelectedCategory(category selected by the user)
	    Description: Setup the faqs category based on user's selection.
	    Returns:     void
    */
        if(userSelection == 0){
            faqCategory = getResources().getStringArray(R.array.faqs_bill);
        }else if(userSelection == 1){
            faqCategory = getResources().getStringArray(R.array.faqs_misc);
        }else if(userSelection == 2){
            faqCategory = getResources().getStringArray(R.array.faqs_newpat);
        }else{
            faqCategory = getResources().getStringArray(R.array.faqs_services);
        }
    }

    private void faqsPopup(int userSelectedCategory){
    /*
	    Arguments:   userSelectedCategory(category selected by the user)
	    Description: Displays a dialog with the faqs listed from the chosen category.
	    Returns:     void
    */
        //Initialize the dialog
        int[] options = new int[] {1, 1};
        String[] titleText = new String[] {categories[userSelectedCategory + 1]};
        DialogSetup.Content.contentSetup(this, "faqs", options, titleText, faqCategory);
    }

    private View.OnClickListener faqsListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.faqsBackButton:
                    v.setSelected(true);
                    finish();
                    break;
                default:
                    break;
            }
        }
    };
    }
