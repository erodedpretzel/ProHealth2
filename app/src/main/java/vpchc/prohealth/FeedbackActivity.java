package vpchc.prohealth;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class FeedbackActivity extends AppCompatActivity {
    Dialog feedbackMessageDialog;
    EditText messageSubject, messageBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarFeedback);
        setSupportActionBar(toolbar);

        //Back button listener
        View buttonBack = findViewById(R.id.backButtonFeedback);
        buttonBack.setOnClickListener(feedbackListener);

        //Rate button listener
        View buttonRate = findViewById(R.id.feedbackRateButton);
        buttonRate.setOnClickListener(feedbackListener);

        //Message button listener
        View buttonMessage = findViewById(R.id.feedbackMessageButton);
        buttonMessage.setOnClickListener(feedbackListener);
    }

    private boolean feedbackMessagePopup(int choice){
    /*
	    Arguments:   choice(0 - dismiss dialog, 1 - create a dialog)
	    Description: Displays or dismisses a dialog for creating a feedback message
	    Returns:     true
    */
        if(choice == 0) {
            ImageView feedbackMessageButton = (ImageView) findViewById(R.id.feedbackMessageButton);
            feedbackMessageButton.setImageResource(R.drawable.message_off);
            feedbackMessageDialog.dismiss();
            return true;
        }else{
            //This cond. statement is to make the styling of the dialog look more modern on devices
            //that support it which are API's >= 14
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                feedbackMessageDialog = new Dialog(FeedbackActivity.this);
            }else{
                feedbackMessageDialog = new Dialog(FeedbackActivity.this, R.style.AppTheme_NoActionBar);
            }
            feedbackMessageDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            feedbackMessageDialog.setContentView(R.layout.dialog_feedback_message);
            feedbackMessageDialog.show();
            feedbackMessageDialog.setCancelable(false);
            feedbackMessageDialog.setCanceledOnTouchOutside(false);
        }

        //Dialog close listener
        View buttonDialogClose= feedbackMessageDialog.findViewById(R.id.buttonFeedbackDialogClose);
        buttonDialogClose.setOnClickListener(feedbackListener);

        //Send button listener
        View buttonFeedbackSendMessage= feedbackMessageDialog.findViewById(R.id.feedbackButtonSendMessage);
        buttonFeedbackSendMessage.setOnClickListener(feedbackListener);
        return true;
    }


    private View.OnClickListener feedbackListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.backButtonFeedback:
                    finish();
                    break;
                case R.id.feedbackRateButton:
                    String toastRate = getResources().getString(R.string.toast_feedback_rate);
                    Toast.makeText(getApplicationContext(), toastRate, Toast.LENGTH_SHORT).show();
                    String appstoreUrl = "https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName();
                    Intent appstoreUrlLink = new Intent(Intent.ACTION_VIEW);
                    appstoreUrlLink.setData(Uri.parse(appstoreUrl));
                    startActivity(appstoreUrlLink);
                    break;
                case R.id.feedbackMessageButton:
                    feedbackMessagePopup(1);
                    break;
                case R.id.feedbackButtonSendMessage:
                    String toastMessage = getResources().getString(R.string.toast_feedback_send_message);
                    Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
                    //This will create an email intent and attempt to open it in an email client if available.
                    messageSubject = (EditText) feedbackMessageDialog.findViewById(R.id.feedbackMessageSubjectLine);
                    messageBody = (EditText) feedbackMessageDialog.findViewById(R.id.feedbackMessageBodyLine);
                    String email[] = {"androidfeedback@vpchc.org"};
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setData(Uri.parse("mail to: androidfeedback@vpchc.org"));
                    emailIntent.setType("text/plain");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, email);
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, messageSubject.getText().toString());
                    emailIntent.putExtra(Intent.EXTRA_TEXT, messageBody.getText().toString());
                    try {
                        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        String toastNoClients = getResources().getString(R.string.toast_feedback_no_clients);
                        Toast.makeText(getApplicationContext(), toastNoClients, Toast.LENGTH_SHORT).show();
                    }
                    feedbackMessagePopup(0);
                    break;
                case R.id.buttonFeedbackDialogClose:
                    feedbackMessagePopup(0);
                    break;
                default:
                    break;
            }
        }
    };


}
