package vpchc.valleyprohealth;

import org.vpchc.valleyprohealth.R;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ProvidersActivity extends AppCompatActivity {
    String locations[]={};
    String easy_locations[]={};
    String providerTypes[]={};
    String providersList[]={};
    String providerTypeSelected = "";

    private int selectionProviderLocation;
    private boolean dentalCheck = false;
    private boolean medOnlyCheck = false;

    private Spinner spinnerProviderType;
    private Spinner spinnerProvidersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final Spinner spinnerProviderLocations;

        //Initial setup of activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_providers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarProviders);
        setSupportActionBar(toolbar);

        //Sets company logo text to custom font due to it being unavailable natively
        String fontPath = "fonts/franklinGothicMedium.ttf";
        TextView titleText = (TextView) findViewById(R.id.title_providers);
        Typeface customTitleText = Typeface.createFromAsset(getAssets(), fontPath);
        titleText.setTypeface(customTitleText);

        //Back button listener
        View buttonBack = findViewById(R.id.backButtonProviders);
        buttonBack.setOnClickListener(providerListener);

        //Setup arrays used as spinner items
        locations = getResources().getStringArray(R.array.vpchc_locations);
        providerTypes = getResources().getStringArray(R.array.provider_types);
        easy_locations = getResources().getStringArray(R.array.easy_locations);

        spinnerProviderLocations = (Spinner)findViewById(R.id.spinnerProviderLocations);
        ArrayAdapter<String>adapterProviderLocations = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.fancy_spinner_item,locations);
        adapterProviderLocations.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
        spinnerProviderLocations.setAdapter(adapterProviderLocations);

        spinnerProviderType = (Spinner)findViewById(R.id.spinnerProviderType);
        ArrayAdapter<String>adapterProviderType = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.fancy_spinner_item,providerTypes);
        adapterProviderType.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
        spinnerProviderType.setAdapter(adapterProviderType);

        spinnerProvidersList= (Spinner)findViewById(R.id.spinnerProvidersList);
        ArrayAdapter<String>adapterProvidersList = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.fancy_spinner_item,providersList);
        adapterProvidersList.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
        spinnerProvidersList.setAdapter(adapterProvidersList);

        //Sets the preferred location
        CommonFunc.sharedPrefSet(this, spinnerProviderLocations, spinnerProviderType, false);

        spinnerProviderLocations.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position) {
                    case 0:
                        spinnerProviderType.setSelection(0);
                        spinnerProviderType.setVisibility(View.GONE);
                        spinnerProvidersList.setVisibility(View.GONE);
                        break;
                    default:
                        selectionProviderLocation = position;
                        providerTypeChange();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}

        });

        spinnerProviderType.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position) {
                    case 0:
                        spinnerProvidersList.setSelection(0);
                        spinnerProvidersList.setVisibility(View.GONE);
                        break;
                    default:
                        providersListChange(position);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}

        });

        spinnerProvidersList.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position) {
                    case 0:
                        break;
                    case 3:
                        //Opens residency bios webpage
                        if(selectionProviderLocation == 6 && providerTypeSelected.equals("medical")){
                            String toastText = getResources().getString(R.string.toast_residencybio_open);
                            Toast.makeText(getApplicationContext(), toastText ,Toast.LENGTH_SHORT).show();
                            String websiteUrl  = "http://uhfmr.org/index.php/about-us/45";
                            Intent websiteLink = new Intent(Intent.ACTION_VIEW);
                            websiteLink.setData(Uri.parse(websiteUrl));
                            startActivity(websiteLink);
                        }else{
                            providersListSelection(position);
                        }
                        break;
                    default:
                        providersListSelection(position);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}

        });

    }

    private void providerTypeChange(){
    /*
	    Arguments:   none
	    Description: Changes the provider types listed in the ProviderType Spinner based on location
	                 chosen by the user.
	    Returns:     void
    */
        if(selectionProviderLocation == 5) {//Rockville
            providerTypes = getResources().getStringArray(R.array.provider_types3);
            dentalCheck = false;
            medOnlyCheck = true;
        }else if(selectionProviderLocation == 2){//Cayuga
            providerTypes = getResources().getStringArray(R.array.provider_types2);
            dentalCheck = true;
            medOnlyCheck = false;
        }else{
            providerTypes = getResources().getStringArray(R.array.provider_types);
            dentalCheck = false;
            medOnlyCheck = false;
        }

        //Setup ProviderType spinner with new provider type list
        spinnerProviderType = (Spinner) findViewById(R.id.spinnerProviderType);
        final ArrayAdapter<String> adapterProviderType = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.fancy_spinner_item, providerTypes);
        adapterProviderType.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
        spinnerProviderType.setAdapter(adapterProviderType);
        spinnerProviderType.setVisibility(View.VISIBLE);
        spinnerProviderType.setSelection(0);
    }

    private void providersListChange(int userSelection){
    /*
	    Arguments:   userSelection(provider type selected by the user)
	    Description: Sets the list of providers for the ProviderList Spinner.
	    Returns:     void
    */
        String arraySearchString;

        //Select string based on user selection
        if(userSelection == 1){
            if(medOnlyCheck){
                arraySearchString = "providers_medical_" + easy_locations[selectionProviderLocation - 1];
                providerTypeSelected = "medical";
            }else{
                arraySearchString = "providers_bh_" + easy_locations[selectionProviderLocation - 1];
                providerTypeSelected = "bh";
            }
        }else if(userSelection == 2){
            if(dentalCheck){
                arraySearchString = "providers_dental_" + easy_locations[selectionProviderLocation - 1];
                providerTypeSelected = "dental";
            }else{
                arraySearchString = "providers_medical_" + easy_locations[selectionProviderLocation - 1];
                providerTypeSelected = "medical";
            }
        }else{
            arraySearchString = "providers_medical_" + easy_locations[selectionProviderLocation - 1];
            providerTypeSelected = "medical";
        }

        //Retrieve array using userSelection string and assign to providersList
        int arraySearchID = getResources().getIdentifier(arraySearchString, "array", getPackageName());
        providersList = getResources().getStringArray(arraySearchID);

        //Setup ProviderList spinner with the provider list
        spinnerProvidersList= (Spinner)findViewById(R.id.spinnerProvidersList);
        ArrayAdapter<String>adapterProvidersList = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.fancy_spinner_item,providersList);
        adapterProvidersList.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
        spinnerProvidersList.setAdapter(adapterProvidersList);
        spinnerProvidersList.setVisibility(View.VISIBLE);
        spinnerProvidersList.setSelection(0);
    }

    private void providersListSelection(int userSelection){
    /*
	    Arguments:   userSelection(provider selected by the user)
	    Description: Prepares the provider name and title to be displayed, resets the ProviderList
	                 spinner, and then opens the Provider Dialog.
	    Returns:     An array with provider name and title separated.
    */

        //Prepares the provider name and title to be displayed
        String providerInfo[] = providerPreparePopup(userSelection);

        //Resets the ProviderList spinner
        spinnerProvidersList.setSelection(0);

        //Opens the Provider Dialog
        providerPopup(providerInfo);
    }

    private String[] providerPreparePopup(int userSelection){
    /*
	    Arguments:   userSelection(provider selected by the user)
	    Description: Splits the provider name and title and stores them in an array.
	    Returns:     An array with provider name and title separated.
    */

        String name;
        String title;
        String splitProviderInfo[];
        String newProviderInfo[] = {"", ""};

        //Split the provider string
        splitProviderInfo = providersList[userSelection].split(",");
        name = splitProviderInfo[0];
        title = splitProviderInfo[1].replaceAll("\\s+","");

        //Expands the title of the provider
        if(title.equals("M.D.")){
            if(providerTypeSelected.equals("bh")){
                title = "Psychiatrist";
            }else{
                title = "Physician";
            }
        }else if(title.equals("PhD")){//Only Dr. Wernz but will need to change when other PhDs are hired
            title = "Director of Behavioral Health";
        }else if(title.equals("FNP-C")){
            title = "Certified Family Nurse Practitioner";
        }else if(title.equals("MS")){
            title = "Mental Health Counselor";
        }else if(title.equals("LCSW")){
            title = "Licensed Clinical Social Worker";
        }else if(title.equals("LCAC")){
            title = "Licensed Clinical Addictions Coordinator";
        }else if(title.equals("LMHCA")){
            title = "Licensed Mental Health Coordinator Associates";
        }else if(title.equals("PMHNP")){
            title = "Psychiatric-Mental Health Nurse Practitioner";
        }else if(title.equals("LDH")){
            title = "Licensed Dental Hygienist";
        }else{
            title = "Dentist";
        }

        //Stores the name and expanded title in the array to be returned
        newProviderInfo[0] = name;
        newProviderInfo[1] = title;
        return newProviderInfo;
    }

    private void providerPopup(String[] providerInfo){
    /*
	    Arguments:   providerInfo(Array of provider info based on user selection.
	                 0 - Provider Name, 1 - Provider Title)
	    Description: Displays a dialog with the provider listed chosen by the user.
	    Returns:     void
    */
        Dialog providerDialog;

        //Initialize the dialog
        String[] titleText = new String[] {providerInfo[0], providerInfo[1]};
        providerDialog = DialogSetup.Content.titleSetup(this, "providers", 3, titleText);

        //Convert providerName to easily search for picture and bio
        String searchableProviderName = providerPrepareSearchableName(providerInfo[0]);

        //Set Provider Picture
        providerPictureSet(providerDialog, searchableProviderName);

        //Set Provider Bio
        providerBioSet(providerDialog, searchableProviderName);
    }

    private String providerPrepareSearchableName(String providerName){
    /*
	    Arguments:   providerName(name of the provider)
	    Description: Convert providerName string to easily search for picture and bio.
	    Returns:     A searchable provider string.
    */
        String searchableProviderName = "";
        String splitProviderName[] = providerName.split(" ");
        for(int i = 0; i < splitProviderName.length; i++){
            if(i == splitProviderName.length - 1){
                searchableProviderName = searchableProviderName + splitProviderName[i].toLowerCase().replace(".", "");
            }else {
                searchableProviderName = searchableProviderName + splitProviderName[i].toLowerCase().replace(".", "") + "_";
            }
        }
        return searchableProviderName;
    }

    private void providerPictureSet(Dialog providerDialog, String searchableProviderName){
    /*
	    Arguments:   providerDialog(Dialog which the picture will be set),
	                 searchableProviderName(provider name which is setup to easily search for provider pic)
	    Description: Set the provider picture.
	    Returns:     void
    */
        int providerPicID = getResources().getIdentifier(searchableProviderName, "drawable", getPackageName());
        ImageView providerPic = (ImageView) providerDialog.findViewById(R.id.providersDialogProviderPicture);
        providerPic.setImageResource(providerPicID);
    }

    private void providerBioSet(Dialog providerDialog, String searchableProviderName){
    /*
	    Arguments:   providerDialog(Dialog which the bio will be set),
	                 searchableProviderName(provider name which is setup to easily search for provider bio)
	    Description: Set the provider bio.
	    Returns:     void
    */
        int providerBioEducationString = getResources().getIdentifier("provider_bio_education_" + searchableProviderName, "string", getPackageName());
        int providerBioPersonalString = getResources().getIdentifier("provider_bio_personal_" + searchableProviderName, "string", getPackageName());
        TextView providerBioEducationText = (TextView) providerDialog.findViewById(R.id.providerDialogProviderEducationContent);
        TextView providerBioPersonalText = (TextView) providerDialog.findViewById(R.id.providerDialogProviderPersonalContent);
        providerBioEducationText.setText(providerBioEducationString);
        providerBioPersonalText.setText(providerBioPersonalString);
    }

    private View.OnClickListener providerListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.backButtonProviders:
                    finish();
                    v.setSelected(true);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}