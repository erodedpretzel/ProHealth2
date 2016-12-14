package vpchc.valleyprohealth;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.vpchc.valleyprohealth.R;

/*
    Created by Brice Webster on 11/21/2016.
*/

public class DialogSetup {

    public static class Base {
        //Simple Dialog with a close button.

        public static Dialog closeDialog;

        public static Dialog Create(Context context, String dialogName) {
        /*
            Arguments:   context(context of the activity),
            dialogName(name of dialog that is used to find resource ids)
            Description: Creates a basic dialog with a close button and returns it.
            Returns:     The created dialog.
        */

            Dialog dialog;
            int dialogID = context.getResources().getIdentifier("dialog_" + dialogName, "layout", context.getPackageName());
            //This cond. statement is to make the styling of the dialog look more modern on devices
            //that support it which are API's >= 14
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                dialog = new Dialog(context);
            } else {
                dialog = new Dialog(context, R.style.AppTheme_NoActionBar);
            }
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(dialogID);
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);

            //Setup close button
            closeDialog = dialog;
            int closeButtonID = context.getResources().getIdentifier(dialogName + "DialogClose", "id", context.getPackageName());
            View buttonDialogClose = dialog.findViewById(closeButtonID);
            buttonDialogClose.setOnClickListener(new closeButton());

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
    }

    public static class Title extends Base {
        //Base dialog with changeable titles.
        public static Dialog titleSetup(Context context, String dialogName, int dialogTitlesOption, String[] titleText){
        /*
            Arguments:   context(context of the activity),
                         dialogName(name of dialog that is used to find resource ids),
                         dialogTitlesOption(option of which titles need set)
                         titleText(array of titles)
            Description: Creates a base dialog with titles and returns it.
            Returns:     The created dialog.
        */
            //Create dialog base.
             Dialog dialog = Create(context, dialogName);

            //Set dialog titles.
            if (dialogTitlesOption == 1){//Only a title
                int titleID = context.getResources().getIdentifier(dialogName + "DialogTitle", "id", context.getPackageName());
                titleSet(dialog, titleID, titleText[0]);
            }else if(dialogTitlesOption == 2){//Only a subtitle
                int subTitleID = context.getResources().getIdentifier(dialogName + "DialogSubTitle", "id", context.getPackageName());
                titleSet(dialog, subTitleID, titleText[0]);
            }else{//Both title and subtitle
                int titleID = context.getResources().getIdentifier(dialogName + "DialogTitle", "id", context.getPackageName());
                int subTitleID = context.getResources().getIdentifier(dialogName + "DialogSubTitle", "id", context.getPackageName());
                titleSet(dialog, titleID, titleText[0]);
                titleSet(dialog, subTitleID, titleText[1]);
            }

            return dialog;
        }

        public static void titleSet(Dialog dialog, int titleID, String text){
        /*
            Arguments:   dialog(the dialog to add a title to),
                         titleID(ID of the textview to add the title text to),
                         text(title text that is added)
            Description: Sets the title text.
            Returns:     void
        */
            TextView textDialogTitle = (TextView) dialog.findViewById(titleID);
            textDialogTitle.setText(text);
        }

    }

    public static class Content extends Title {
        //Base dialog with changeable titles and content.
        public static void contentSetup(Context context, String dialogName, int[] options, String[] titleText, String[] content){
        /*
            Arguments:   context(context of the activity),
                         dialogName(name of dialog that is used to find resource ids)
                         options(options for the content such as how many titles there are,
                         content size, and whether to use bold or not)
                         titleText(array of titles), content(array of content for the dialog)
            Description: Creates a base dialog with titles & content.
            Returns:     void
        */
            //Create dialog base and set dialog titles.
            Dialog dialog = titleSetup(context, dialogName, options[0], titleText);

            //Set dialog content.
            int dialogContentID = context.getResources().getIdentifier(dialogName + "Content", "id", context.getPackageName());
            int contentSize = (int) (context.getResources().getDimension(R.dimen.dialog_content) / context.getResources().getDisplayMetrics().density);
            LinearLayout dialogContent = (LinearLayout) dialog.findViewById(dialogContentID);
            for(int i = 0;i < (content.length);i++){
                TextView itemToAdd = new TextView(context);
                itemToAdd.setText(content[i]);
                itemToAdd.setTextSize(contentSize);
                if((i % 2 == 0) && options[1] == 1){//If Even row & dialog bold content option chosen.
                    itemToAdd.setTypeface(itemToAdd.getTypeface(), Typeface.BOLD);
                }
                itemToAdd.setTextColor(Color.parseColor("#000000"));
                dialogContent.addView(itemToAdd);
                if(i != 0){
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)itemToAdd.getLayoutParams();
                    params.setMargins(0, 30, 0, 0);
                    itemToAdd.setLayoutParams(params);
                }
            }
        }
    }

}
