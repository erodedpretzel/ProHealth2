package vpchc.prohealth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

public class ServicesActivity extends AppCompatActivity {

    private static final String[]locations = {"Select a location", "Bloomingdale", "Cayuga",
            "Clinton", "Crawfordsville", "Terre Haute"};

    private Spinner spinnerServicesLocations;
    private Spinner spinnerServicesCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View buttonBack = findViewById(R.id.servicesBackButton);
        buttonBack.setOnClickListener(servicesListener);

//        spinnerServicesLocations = (Spinner)findViewById(R.id.spinnerServicesLocations);
//        ArrayAdapter<String> adapterFormsLocations = new ArrayAdapter<String>(FormsActivity.this,
//                R.layout.fancy_spinner_item,locations);
//        adapterFormsLocations.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
//        spinnerFormsLocations.setAdapter(adapterFormsLocations);
//
//        spinnerFormsLocations = (Spinner)findViewById(R.id.spinnerFormsLocations);
//        ArrayAdapter<String> adapterFormsLocations = new ArrayAdapter<String>(FormsActivity.this,
//                R.layout.fancy_spinner_item,locations);
//        adapterFormsLocations.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
//        spinnerFormsLocations.setAdapter(adapterFormsLocations);
    }

    private View.OnClickListener servicesListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.servicesBackButton:
                    finish();
                    ImageView backButton = (ImageView) findViewById(R.id.servicesBackButton);
                    backButton.setImageResource(R.drawable.back_arrow_on);
                    break;
                default:
                    break;
            }
        }
    };
}
