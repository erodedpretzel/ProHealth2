package vpchc.prohealth;

import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.content.Intent;
import android.app.Dialog;
import android.content.Context;
import android.widget.Toast;
import android.graphics.drawable.ColorDrawable;

public class MainActivity extends AppCompatActivity {


    Dialog callDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View callImage = findViewById(R.id.callButton);
        View providersImage = findViewById(R.id.providerButton);
        View locationsImage = findViewById(R.id.locationsButton);
        View formsImage = findViewById(R.id.formsButton);
        View portalImage = findViewById(R.id.portalButton);
        View programsImage = findViewById(R.id.programsButton);
        View trackerImage = findViewById(R.id.trackerButton);
        View jobsImage = findViewById(R.id.jobsButton);
        View facebookImage = findViewById(R.id.facebookButton);


        callImage.setOnClickListener(homeListener);
        providersImage.setOnClickListener(homeListener);
        locationsImage.setOnClickListener(homeListener);
        formsImage.setOnClickListener(homeListener);
        portalImage.setOnClickListener(homeListener);
        programsImage.setOnClickListener(homeListener);
        trackerImage.setOnClickListener(homeListener);
        jobsImage.setOnClickListener(homeListener);
        facebookImage.setOnClickListener(homeListener);
    }

    private View.OnClickListener homeListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.callButton:
                    ImageView homeButton = (ImageView) findViewById(R.id.callButton);
                    homeButton.setImageResource(R.drawable.call_on);
                    callPopup(0);
                    break;
                case R.id.providerButton:
                    ImageView providerButton = (ImageView) findViewById(R.id.providerButton);
                    providerButton.setImageResource(R.drawable.providers_on);
                    Intent openProvidersIntent = new Intent(MainActivity.this, ProvidersActivity.class);
                    startActivity(openProvidersIntent);
                    break;
                case R.id.locationsButton:
                    ImageView locationsButton = (ImageView) findViewById(R.id.locationsButton);
                    locationsButton.setImageResource(R.drawable.locations_on);
                    Intent openLocationsIntent = new Intent(MainActivity.this, LocationsActivity.class);
                    startActivity(openLocationsIntent);
                    break;
                case R.id.formsButton:
                    ImageView formsButton = (ImageView) findViewById(R.id.formsButton);
                    formsButton.setImageResource(R.drawable.forms_on);
                    Intent openFormsIntent = new Intent(MainActivity.this, FormsActivity.class);
                    startActivity(openFormsIntent);
                    break;
                case R.id.portalButton:
                    ImageView portalButton = (ImageView) findViewById(R.id.portalButton);
                    portalButton.setImageResource(R.drawable.portal_on);
                    String portalUrl = "https://secure2.myunionportal.org/vpchc/default.aspx";
                    Intent portalLink = new Intent(Intent.ACTION_VIEW);
                    portalLink.setData(Uri.parse(portalUrl));
                    startActivity(portalLink);
                    break;
                case R.id.programsButton:
                    ImageView programsButton = (ImageView) findViewById(R.id.programsButton);
                    programsButton.setImageResource(R.drawable.programs_on);
                    Intent openProgramsIntent = new Intent(MainActivity.this, ProgramsActivity.class);
                    startActivity(openProgramsIntent);
                    break;
                case R.id.trackerButton:
                    ImageView trackerButton = (ImageView) findViewById(R.id.trackerButton);
                    trackerButton.setImageResource(R.drawable.tracker_on);
                    break;
                case R.id.jobsButton:
                    ImageView jobsButton = (ImageView) findViewById(R.id.jobsButton);
                    jobsButton.setImageResource(R.drawable.jobs_on);
                    Intent openJobsIntent = new Intent(MainActivity.this, JobsActivity.class);
                    startActivity(openJobsIntent);
                    break;
                case R.id.facebookButton:
                    ImageView facebookButton = (ImageView) findViewById(R.id.facebookButton);
                    facebookButton.setImageResource(R.drawable.facebook_on);
                    String facebookUrl = "https://www.facebook.com/VPCHC";
                    Intent facebookLink = new Intent(Intent.ACTION_VIEW);
                    facebookLink.setData(Uri.parse(facebookUrl));
                    startActivity(facebookLink);
                    break;
                default:
                    break;
            }
        }
    };

    private boolean callPopup(int choice){
        if(choice == 0) {
            callDialog = new Dialog(MainActivity.this);
            callDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            callDialog.setContentView(R.layout.call_dialog);
            callDialog.show();
        }else{
            callDialog.dismiss();
            ImageView homeButton = (ImageView) findViewById(R.id.callButton);
            homeButton.setImageResource(R.drawable.call_off);
            return true;
        }

        View buttonCallBloomImage = callDialog.findViewById(R.id.buttonCallBloom);
        View buttonCallCayImage = callDialog.findViewById(R.id.buttonCallCay);
        View buttonCallClintImage = callDialog.findViewById(R.id.buttonCallClint);
        View buttonCallCrawImage = callDialog.findViewById(R.id.buttonCallCraw);
        View buttonCallTerreImage = callDialog.findViewById(R.id.buttonCallTerre);
        View buttonCallCloseImage = callDialog.findViewById(R.id.buttonCallClose);

        buttonCallCloseImage.setOnClickListener(callListener);
        buttonCallBloomImage.setOnClickListener(callListener);
        buttonCallCayImage.setOnClickListener(callListener);
        buttonCallClintImage.setOnClickListener(callListener);
        buttonCallCrawImage.setOnClickListener(callListener);
        buttonCallTerreImage.setOnClickListener(callListener);



        return true;
    }

    private View.OnClickListener callListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.buttonCallBloom:
                    Intent callBloomIntent = new Intent(Intent.ACTION_CALL);
                    callBloomIntent.setData(Uri.parse("tel:7654989000"));
                    startActivity(callBloomIntent);
                    Toast.makeText(getApplicationContext(),"Calling Bloomingdale Location",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.buttonCallCay:
                    Intent callCayIntent = new Intent(Intent.ACTION_CALL);
                    callCayIntent.setData(Uri.parse("tel:7654929042"));
                    startActivity(callCayIntent);
                    Toast.makeText(getApplicationContext(),"Calling Cayuga Location",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.buttonCallClint:
                    Intent callClintIntent = new Intent(Intent.ACTION_CALL);
                    callClintIntent.setData(Uri.parse("tel:7658281003"));
                    startActivity(callClintIntent);
                    Toast.makeText(getApplicationContext(),"Calling Clinton Location",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.buttonCallCraw:
                    Intent callCrawIntent = new Intent(Intent.ACTION_CALL);
                    callCrawIntent.setData(Uri.parse("tel:7653625100"));
                    startActivity(callCrawIntent);
                    Toast.makeText(getApplicationContext(),"Calling Crawfordsville Location",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.buttonCallTerre:
                    Intent callTerreIntent = new Intent(Intent.ACTION_CALL);
                    callTerreIntent.setData(Uri.parse("tel:8122387631"));
                    startActivity(callTerreIntent);
                    Toast.makeText(getApplicationContext(),"Calling Terre Haute Location",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.buttonCallClose:
                    callPopup(1);
                    break;
                default:
                    break;
            }
        }
    };

}
