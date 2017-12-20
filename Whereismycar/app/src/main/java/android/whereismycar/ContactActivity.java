package android.whereismycar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ContactActivity extends AppCompatActivity {
    private TextView to;
    private TextView subject;
    private TextView body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        to = (TextView)findViewById(R.id.toTextContact);
        subject = (TextView)findViewById(R.id.subjectTextContact);
        body = (TextView)findViewById(R.id.bodyTextContact);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ContactActivity.this);
        String email = preferences.getString("email","");
        if(!email.equals("")){
            to.setText(email);
        }
    }

    public void handleSendEmail(View view) {
        String to = this.to.getText().toString();
        if(TextUtils.isEmpty(to)){
            Toast.makeText(this, "To address is empty", Toast.LENGTH_SHORT).show();
        }else{
            String subject = this.subject.getText().toString();
            if(TextUtils.isEmpty(subject)){
                Toast.makeText(this, "Subject is empty", Toast.LENGTH_SHORT).show();
            }else{
                String body = this.body.getText().toString();
                if(TextUtils.isEmpty(body)){
                    Toast.makeText(this, "Body is empty", Toast.LENGTH_SHORT).show();
                }else{
                    String uriText = "mailto:" + Uri.encode(to)
                            + "?body=" + Uri.encode(body)
                            + "&subject=" + Uri.encode(subject);

                    Intent sendIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse(uriText));
                    sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    startActivity(sendIntent);
                    finish();
                }
            }
        }
    }
}
