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

public class MainActivity extends AppCompatActivity {

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
                    callPopup();
                    break;
                case R.id.providerButton:
                    ImageView providerButton = (ImageView) findViewById(R.id.providerButton);
                    providerButton.setImageResource(R.drawable.providers_on);
                    break;
                case R.id.locationsButton:
                    ImageView locationsButton = (ImageView) findViewById(R.id.locationsButton);
                    locationsButton.setImageResource(R.drawable.locations_on);
                    break;
                case R.id.formsButton:
                    ImageView formsButton = (ImageView) findViewById(R.id.formsButton);
                    formsButton.setImageResource(R.drawable.forms_on);
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
                    break;
                case R.id.trackerButton:
                    ImageView trackerButton = (ImageView) findViewById(R.id.trackerButton);
                    trackerButton.setImageResource(R.drawable.tracker_on);
                    break;
                case R.id.jobsButton:
                    ImageView jobsButton = (ImageView) findViewById(R.id.jobsButton);
                    jobsButton.setImageResource(R.drawable.jobs_on);
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

    public boolean callPopup(){
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.call_dialog);
        dialog.show();

        View buttonCallBloomImage = findViewById(R.id.buttonCallBloom);
        View buttonCallCayImage = findViewById(R.id.buttonCallCay);
        View buttonCallClintImage = findViewById(R.id.buttonCallClint);
        View buttonCallCrawImage = findViewById(R.id.buttonCallCraw);
        View buttonCallTerreImage = findViewById(R.id.buttonCallTerre);
        View buttonCallCloseImage = findViewById(R.id.buttonCallClose);

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
                    Intent inBloom=new Intent(Intent.ACTION_CALL,Uri.parse("7654989000"));
                    try{
                        startActivity(inBloom);
                    }
                    catch (android.content.ActivityNotFoundException ex){
                        Toast.makeText(getApplicationContext(),"Could not make the call",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.buttonCallCay:
                    Intent inClay=new Intent(Intent.ACTION_CALL,Uri.parse("7654929042"));
                    try{
                        startActivity(inClay);
                    }
                    catch (android.content.ActivityNotFoundException ex){
                        Toast.makeText(getApplicationContext(),"Could not make the call",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.buttonCallClint:
                    Intent inClint=new Intent(Intent.ACTION_CALL,Uri.parse("7658281003"));
                    try{
                        startActivity(inClint);
                    }
                    catch (android.content.ActivityNotFoundException ex){
                        Toast.makeText(getApplicationContext(),"Could not make the call",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.buttonCallCraw:
                    Intent inCraw=new Intent(Intent.ACTION_CALL,Uri.parse("7653625100"));
                    try{
                        startActivity(inCraw);
                    }
                    catch (android.content.ActivityNotFoundException ex){
                        Toast.makeText(getApplicationContext(),"Could not make the call",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.buttonCallTerre:
                    Intent inTerre=new Intent(Intent.ACTION_CALL,Uri.parse("7652387631"));
                    try{
                        startActivity(inTerre);
                    }
                    catch (android.content.ActivityNotFoundException ex){
                        Toast.makeText(getApplicationContext(),"Could not make the call",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.buttonCallClose:

                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
