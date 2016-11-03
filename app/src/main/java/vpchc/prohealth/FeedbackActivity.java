package vpchc.prohealth;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

        //Sets company logo text to custom font due to it being unavailable natively
        String fontPath = "fonts/franklinGothicMedium.ttf";
        TextView titleText = (TextView) findViewById(R.id.title_feedback);
        Typeface customTitleText = Typeface.createFromAsset(getAssets(), fontPath);
        titleText.setTypeface(customTitleText);

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
                    //This will create an email intent and attempt to open it in an email client if available.
                    String email[] = {"androidfeedback@vpchc.org"};
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setData(Uri.parse("mail to: androidfeedback@vpchc.org"));
                    emailIntent.setType("text/plain");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, email);
                    try {
                        String toastMessage = getResources().getString(R.string.toast_feedback_send_message);
                        Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
                    } catch (android.content.ActivityNotFoundException ex) {
                        String toastNoClients = getResources().getString(R.string.toast_feedback_no_clients);
                        Toast.makeText(getApplicationContext(), toastNoClients, Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
        }
    };


}
