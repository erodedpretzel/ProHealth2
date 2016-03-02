package vpchc.prohealth;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class FormsActivity extends AppCompatActivity {

    private Spinner spinnerFormsLocations;
    private Spinner spinnerFormsCategories;
    private Spinner spinnerFormsSelection;
    private static final String[]locations = {"Select a location", "Bloomingdale", "Cayuga",
            "Clinton", "Crawfordsville", "Terre Haute"};
    private static final String[]categories = {"Select a category","Consent","New Patient","Notice","Student Forms"};
    private String[]finalForms={"Select a form"};
    private static final String[]forms1 = {"Select a form","Behavioral Health Release", "Release of Information","Telemedicine"};
    private static final String[]forms2 = {"Select a form","Adult","Child", "Sliding Fee Scale"};
    private static final String[]forms3 = {"Select a form","Acknowledgement of Bill of Rights", "Bill of Rights","Privacy Practice"};
    private static final String[]forms4 = {"Select a form","ISHAA Physical","MSBHC Enrollment"};

    int selectionLocation=0;
    int selectionCategory=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forms);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View buttonBack = findViewById(R.id.formsBackButton);
        buttonBack.setOnClickListener(formsListener);

        spinnerFormsLocations = (Spinner)findViewById(R.id.spinnerFormsLocations);
        ArrayAdapter<String> adapterFormsLocations = new ArrayAdapter<String>(FormsActivity.this,
                R.layout.fancy_spinner_item,locations);
        adapterFormsLocations.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
        spinnerFormsLocations.setAdapter(adapterFormsLocations);

        spinnerFormsCategories = (Spinner)findViewById(R.id.spinnerFormsCategories);
        final ArrayAdapter<String>adapterFormsCategories = new ArrayAdapter<String>(FormsActivity.this,
                R.layout.fancy_spinner_item,categories);
        adapterFormsCategories.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
        spinnerFormsCategories.setAdapter(adapterFormsCategories);
        spinnerFormsCategories.setVisibility(View.GONE);

        spinnerFormsSelection = (Spinner)findViewById(R.id.spinnerFormsSelection);
        final ArrayAdapter<String>adapterFormsSelection = new ArrayAdapter<String>(FormsActivity.this,
                R.layout.fancy_spinner_item,finalForms);
        adapterFormsSelection.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
        spinnerFormsSelection.setAdapter(adapterFormsSelection);
        spinnerFormsSelection.setVisibility(View.GONE);

        spinnerFormsLocations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position) {
                    case 0:
                        spinnerFormsCategories.setSelection(0);
                        spinnerFormsSelection.setSelection(0);
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
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        spinnerFormsSelection = (Spinner)findViewById(R.id.spinnerFormsSelection);
                        final ArrayAdapter<String>adapterFormsSelection = new ArrayAdapter<String>(FormsActivity.this,
                                R.layout.fancy_spinner_item,forms1);
                        adapterFormsSelection.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
                        spinnerFormsSelection.setAdapter(adapterFormsSelection);
                        spinnerFormsSelection.setVisibility(View.VISIBLE);
                        spinnerFormsSelection.setSelection(0);
                        selectionCategory = 1;
                        break;
                    case 2:
                        spinnerFormsSelection = (Spinner)findViewById(R.id.spinnerFormsSelection);
                        final ArrayAdapter<String>adapterFormsSelection2 = new ArrayAdapter<String>(FormsActivity.this,
                                R.layout.fancy_spinner_item,forms2);
                        adapterFormsSelection2.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
                        spinnerFormsSelection.setAdapter(adapterFormsSelection2);
                        spinnerFormsSelection.setVisibility(View.VISIBLE);
                        spinnerFormsSelection.setSelection(0);
                        selectionCategory = 2;
                        finalForms = forms2;
                        break;
                    case 3:
                        spinnerFormsSelection = (Spinner)findViewById(R.id.spinnerFormsSelection);
                        final ArrayAdapter<String>adapterFormsSelection3 = new ArrayAdapter<String>(FormsActivity.this,
                                R.layout.fancy_spinner_item,forms3);
                        adapterFormsSelection3.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
                        spinnerFormsSelection.setAdapter(adapterFormsSelection3);
                        spinnerFormsSelection.setVisibility(View.VISIBLE);
                        spinnerFormsSelection.setSelection(0);
                        selectionCategory = 3;
                        finalForms = forms3;
                        break;
                    case 4:
                        spinnerFormsSelection = (Spinner)findViewById(R.id.spinnerFormsSelection);
                        final ArrayAdapter<String>adapterFormsSelection4 = new ArrayAdapter<String>(FormsActivity.this,
                                R.layout.fancy_spinner_item,forms4);
                        adapterFormsSelection4.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
                        spinnerFormsSelection.setAdapter(adapterFormsSelection4);
                        spinnerFormsSelection.setVisibility(View.VISIBLE);
                        spinnerFormsSelection.setSelection(0);
                        selectionCategory = 4;

                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

        spinnerFormsSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(selectionCategory==1){
                    switch (position) {
                        case 0:
                            break;
                        case 1:
                            Toast.makeText(getApplicationContext(), "Downloading Form...", Toast.LENGTH_SHORT).show();
                            Uri uri1 = Uri.parse("http://vpchc.org/files/form_behavioral_health_release.pdf");
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri1);
                            startActivity(intent);
                            break;
                        case 2:
                            Toast.makeText(getApplicationContext(), "Downloading Form...", Toast.LENGTH_SHORT).show();
                            Uri uri2 = Uri.parse("http://vpchc.org/files/form_release_of_information.pdf");
                            Intent intent2 = new Intent(Intent.ACTION_VIEW, uri2);
                            startActivity(intent2);
                            break;
                        case 3:
                            Toast.makeText(getApplicationContext(), "Downloading Form...", Toast.LENGTH_SHORT).show();
                            Uri uri3 = Uri.parse("http://vpchc.org/files/forms_telemedicine_consent.pdf");
                            Intent intent3 = new Intent(Intent.ACTION_VIEW, uri3);
                            startActivity(intent3);
                            break;
                    }
                }else if(selectionCategory==2){
                    if(selectionLocation>=1 && selectionLocation<=3){
                        switch (position) {
                            case 0:
                                break;
                            case 1:
                                Toast.makeText(getApplicationContext(), "Downloading Form...", Toast.LENGTH_SHORT).show();
                                Uri uri4 = Uri.parse("http://vpchc.org/files/form_new_patient_packet_adult_bloomcayclint.pdf");
                                Intent intent4 = new Intent(Intent.ACTION_VIEW, uri4);
                                startActivity(intent4);
                                break;
                            case 2:
                                Toast.makeText(getApplicationContext(), "Downloading Form...", Toast.LENGTH_SHORT).show();
                                Uri uri5 = Uri.parse("http://vpchc.org/files/form_new_patient_packet_child_bloomcayclint.pdf");
                                Intent intent5 = new Intent(Intent.ACTION_VIEW, uri5);
                                startActivity(intent5);
                                break;
                            case 3:
                                Toast.makeText(getApplicationContext(), "Downloading Form...", Toast.LENGTH_SHORT).show();
                                Uri uri6 = Uri.parse("http://vpchc.org/files/form_sliding_fee_scale_reqs.pdf");
                                Intent intent6 = new Intent(Intent.ACTION_VIEW, uri6);
                                startActivity(intent6);
                                break;
                        }
                    }else if(selectionLocation==4){
                        switch (position) {
                            case 0:
                                break;
                            case 1:
                                Toast.makeText(getApplicationContext(), "Downloading Form...", Toast.LENGTH_SHORT).show();
                                Uri uri7 = Uri.parse("http://vpchc.org/files/form_new_patient_packet_adult_crawfordsville.pdf");
                                Intent intent7 = new Intent(Intent.ACTION_VIEW, uri7);
                                startActivity(intent7);
                                break;
                            case 2:
                                Toast.makeText(getApplicationContext(), "Downloading Form...", Toast.LENGTH_SHORT).show();
                                Uri uri8 = Uri.parse("http://vpchc.org/files/form_new_patient_packet_child_crawfordsville.pdf");
                                Intent intent8 = new Intent(Intent.ACTION_VIEW, uri8);
                                startActivity(intent8);
                                break;
                            case 3:
                                Toast.makeText(getApplicationContext(), "Downloading Form...", Toast.LENGTH_SHORT).show();
                                Uri uri9 = Uri.parse("http://vpchc.org/files/form_sliding_fee_scale_reqs.pdf");
                                Intent intent9 = new Intent(Intent.ACTION_VIEW, uri9);
                                startActivity(intent9);
                                break;
                        }
                    }else if(selectionLocation==5){
                        switch (position) {
                            case 0:
                                break;
                            case 1:
                                Toast.makeText(getApplicationContext(), "Downloading Form...", Toast.LENGTH_SHORT).show();
                                Uri uri10 = Uri.parse("http://vpchc.org/files/form_new_patient_packet_adult_terrehaute.pdf");
                                Intent intent10 = new Intent(Intent.ACTION_VIEW, uri10);
                                startActivity(intent10);
                                break;
                            case 2:
                                Toast.makeText(getApplicationContext(), "Downloading Form...", Toast.LENGTH_SHORT).show();
                                Uri uri11 = Uri.parse("http://vpchc.org/files/form_new_patient_packet_child_terrehaute.pdf");
                                Intent intent11 = new Intent(Intent.ACTION_VIEW, uri11);
                                startActivity(intent11);
                                break;
                            case 3:
                                Toast.makeText(getApplicationContext(), "Downloading Form...", Toast.LENGTH_SHORT).show();
                                Uri uri12 = Uri.parse("http://vpchc.org/files/form_sliding_fee_scale_reqs.pdf");
                                Intent intent12 = new Intent(Intent.ACTION_VIEW, uri12);
                                startActivity(intent12);
                                break;
                        }
                    }

                }else if(selectionCategory==3){
                    switch (position) {
                        case 0:
                            break;
                        case 1:
                            Toast.makeText(getApplicationContext(), "Downloading Form...", Toast.LENGTH_SHORT).show();
                            Uri uri13 = Uri.parse("http://vpchc.org/files/form_acknowledgement_receipt.pdf");
                            Intent intent13 = new Intent(Intent.ACTION_VIEW, uri13);
                            startActivity(intent13);
                            break;
                        case 2:
                            Toast.makeText(getApplicationContext(), "Downloading Form...", Toast.LENGTH_SHORT).show();
                            Uri uri14 = Uri.parse("http://vpchc.org/files/form_patient_bill_of_rights.pdf");
                            Intent intent14 = new Intent(Intent.ACTION_VIEW, uri14);
                            startActivity(intent14);
                            break;
                        case 3:
                            Toast.makeText(getApplicationContext(), "Downloading Form...", Toast.LENGTH_SHORT).show();
                            Uri uri15 = Uri.parse("http://vpchc.org/files/form_notice_privacy_practices.pdf");
                            Intent intent15 = new Intent(Intent.ACTION_VIEW, uri15);
                            startActivity(intent15);
                            break;
                    }
                }else if(selectionCategory==4){
                    switch (position) {
                        case 0:
                            break;
                        case 1:
                            Toast.makeText(getApplicationContext(), "Downloading Form...", Toast.LENGTH_SHORT).show();
                            Uri uri16 = Uri.parse("http://vpchc.org/files/form_ihsaa_physical.pdf");
                            Intent intent16 = new Intent(Intent.ACTION_VIEW, uri16);
                            startActivity(intent16);
                            break;
                        case 2:
                            Toast.makeText(getApplicationContext(), "Downloading Form...", Toast.LENGTH_SHORT).show();
                            Uri uri17 = Uri.parse("http://vpchc.org/files/form_msbhc_student_enrollment.pdf");
                            Intent intent17 = new Intent(Intent.ACTION_VIEW, uri17);
                            startActivity(intent17);
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
    }


    private View.OnClickListener formsListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.formsBackButton:
                    finish();
                    ImageView backButton = (ImageView) findViewById(R.id.formsBackButton);
                    backButton.setImageResource(R.drawable.back_arrow_on);
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
