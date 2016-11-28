package vpchc.valleyprohealth;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;


import org.vpchc.valleyprohealth.R;

/**
 * Created by Brice Webster on 11/21/2016.
 */

public class DialogSetup {

    public static Dialog closeDialog;

    public static Dialog dialogCreate(Dialog dialog, Context context, int[] IDs, String[] titleText, int dialogType) {
        //This cond. statement is to make the styling of the dialog look more modern on devices
        //that support it which are API's >= 14
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            dialog = new Dialog(context);
        } else {
            dialog = new Dialog(context, R.style.AppTheme_NoActionBar);
        }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(IDs[0]);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);

        //Setup close button
        closeDialog = dialog;
        View buttonDialogClose = dialog.findViewById(IDs[1]);
        Log.w("dialogClose", "close: " + IDs[1]);
        buttonDialogClose.setOnClickListener(new closeButton());

        //0 -none, 1-one title, 2-two
        //Set dialog title
        if(dialogType == 1){
            dialogTitleSet(dialog, IDs[2], titleText[0]);
        }else if(dialogType == 2){
            dialogTitleSet(dialog, IDs[2], titleText[0]);
            dialogTitleSet(dialog, IDs[3], titleText[1]);
        }
        return dialog;
    }

    public static class closeButton implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                default:
                    closeDialog.dismiss();
                    break;
            }
        }
    }

    public static void dialogTitleSet(Dialog dialog, int titleID, String text){
        TextView textDialogTitle = (TextView) dialog.findViewById(titleID);
        textDialogTitle.setText(text);
    }

}
