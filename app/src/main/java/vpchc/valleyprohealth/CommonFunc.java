package vpchc.valleyprohealth;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Spinner;

import static android.content.Context.MODE_PRIVATE;

/*
   Created by Brice Webster on 11/30/2016.
*/

public class CommonFunc {
    public static void sharedPrefSet(Context context, Spinner locations, Spinner options, boolean noBusCheck){
    /*
	    Arguments:   context(context of activity), locations(spinner of locations),
	                 options(second spinner below locations),
	                 noBusCheck(Boolean to check if there are options for the bus with this activity)
	    Description: Get location preference and sets location spinner accordingly.
	    Returns:     void
    */
        SharedPreferences pref = context.getSharedPreferences("prefLocation", MODE_PRIVATE);
        int locationPref = pref.getInt("prefLocation", 0);

        if(locationPref == 0 || (locationPref == 6 && noBusCheck)){
            locations.setSelection(0);
            options.setVisibility(View.GONE);
        }else{
            locations.setSelection(locationPref);
        }
    }
}
