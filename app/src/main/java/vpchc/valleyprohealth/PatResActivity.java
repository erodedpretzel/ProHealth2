package vpchc.valleyprohealth;

import org.vpchc.valleyprohealth.R;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class PatResActivity extends AppCompatActivity {
    String categories[]={};
    String categoryItems[]={};
    private Spinner spinnerCategories;
    Dialog patresDialog;
    int selectionCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        categories = getResources().getStringArray(R.array.patres_categories);

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
                    case 1:
                        selectionCategory = 0;
                        categoryItems = getResources().getStringArray(R.array.patres_categories_diabetes);
                        patresPopup();
                        spinnerCategories.setSelection(0);
                        break;
                    case 2:
                        selectionCategory = 1;
                        categoryItems = getResources().getStringArray(R.array.patres_categories_prescription);
                        patresPopup();
                        spinnerCategories.setSelection(0);
                        break;
                    case 3:
                        selectionCategory = 2;
                        categoryItems = getResources().getStringArray(R.array.patres_categories_scale);
                        patresPopup();
                        spinnerCategories.setSelection(0);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });
    }

    private boolean patresPopup(){
    /*
	    Arguments:   choice(0 - dismiss dialog, 1 - create a dialog)
	    Description: Displays or dismisses a dialog with bh or medical providers listed.
	    Returns:     true
    */
        //Initialize the dialog
        int layoutID = getResources().getIdentifier("dialog_patres", "layout", this.getPackageName());
        int closeID = getResources().getIdentifier("buttonDialogClosePatRes", "id", this.getPackageName());
        int titleID = getResources().getIdentifier("patresSubTitle", "id", this.getPackageName());
        int[] IDs = new int[] {layoutID,closeID,titleID};
        String[] titleText = new String[] {categories[selectionCategory + 1]};
        patresDialog = DialogSetup.dialogCreate(patresDialog, this, IDs, titleText, 1);

        //Populates the Questions/Answers from the selected category
        LinearLayout patresContent = (LinearLayout) patresDialog.findViewById(R.id.patresContent);
        for(int i = 0;i < (categoryItems.length);i++){
            TextView itemToAdd = new TextView(this);
            itemToAdd.setText(categoryItems[i]);
            itemToAdd.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.dialog_content));
            itemToAdd.setTextColor(Color.parseColor("#000000"));
            patresContent.addView(itemToAdd);
            if(i != 0){
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)itemToAdd.getLayoutParams();
                params.setMargins(0, 30, 0, 0);
                itemToAdd.setLayoutParams(params);
            }
        }
        return true;
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
