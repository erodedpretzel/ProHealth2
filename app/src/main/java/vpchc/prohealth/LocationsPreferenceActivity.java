package vpchc.prohealth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class LocationsPreferenceActivity extends AppCompatActivity {

    private Spinner spinnerPrefSelection;
    private static final String[]locations = {"Select a preferred location", "No preference","Bloomingdale", "Cayuga",
            "Clinton", "Crawfordsville", "Terre Haute", "MSBHC"};
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations_preference);

        pref = getSharedPreferences("prefLocation", MODE_PRIVATE);

        if(pref.contains("prefLocation")) {
            openMainActivity();
        }

        editor = pref.edit();

        spinnerPrefSelection = (Spinner)findViewById(R.id.spinnerPrefSelection);
        final ArrayAdapter<String> adapterPrefSelection = new ArrayAdapter<String>(LocationsPreferenceActivity.this,
                R.layout.fancy_spinner_item,locations);
        adapterPrefSelection.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
        spinnerPrefSelection.setAdapter(adapterPrefSelection);

        spinnerPrefSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        Toast.makeText(getApplicationContext(), "Saving...", Toast.LENGTH_SHORT).show();
                        editor.putInt("prefLocation", 0);
                        editor.apply();
                        openMainActivity();
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(), "Saving...", Toast.LENGTH_SHORT).show();
                        editor.putInt("prefLocation", 1);
                        editor.apply();
                        openMainActivity();
                        break;
                    case 3:
                        Toast.makeText(getApplicationContext(), "Saving...", Toast.LENGTH_SHORT).show();
                        editor.putInt("prefLocation", 2);
                        editor.apply();
                        openMainActivity();
                        break;
                    case 4:
                        Toast.makeText(getApplicationContext(), "Saving...", Toast.LENGTH_SHORT).show();
                        editor.putInt("prefLocation", 3);
                        editor.apply();
                        openMainActivity();
                        break;
                    case 5:
                        Toast.makeText(getApplicationContext(), "Saving...", Toast.LENGTH_SHORT).show();
                        editor.putInt("prefLocation", 4);
                        editor.apply();
                        openMainActivity();
                        break;
                    case 6:
                        Toast.makeText(getApplicationContext(), "Saving...", Toast.LENGTH_SHORT).show();
                        editor.putInt("prefLocation", 5);
                        editor.apply();
                        openMainActivity();
                        break;
                    case 7:
                        Toast.makeText(getApplicationContext(), "Saving...", Toast.LENGTH_SHORT).show();
                        editor.putInt("prefLocation", 6);
                        editor.apply();
                        openMainActivity();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });
    }

    private void openMainActivity() {
        Intent openMainIntent = new Intent(LocationsPreferenceActivity.this, MainActivity.class);
        openMainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(openMainIntent);
    }
}
