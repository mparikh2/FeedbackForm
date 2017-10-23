package com.example.milind.feedback;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String Email = "milu2409@yahoo.com";
    private Spinner mSubjectSpinner;
    private Spinner mChannelSpinner;
    private TextView mChannelSpinnerText;
    private TextView mSubjectSpinnerText;
    private EditText mMessageEditText;
    private EditText mEmailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Fills the subject spinner with the unit options
        addItemsToSubjectSpinner();

        // Add listener to the subject Spinner
        addListenerToSubjectSpinner();

        // Fills the channel spinner with the unit options
        addItemsToChannelSpinner();

        // Add listener to the channel Spinner
        addListenerToChannelSpinner();

        // Get a reference to the edit text view to retrieve the amount of the unit type
        mMessageEditText = (EditText) findViewById(R.id.messageEditText);
        mEmailEditText = (EditText) findViewById(R.id.emailEditText);
        Button mSendButton = (Button) findViewById(R.id.sendbutton);

        initializeTextViews();

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailEditText.getText().toString();
                String subject = (String)(mSubjectSpinner.getSelectedItem());
                String channel = (String)(mChannelSpinner.getSelectedItem());
                String message = mMessageEditText.getText().toString();
                Intent mailIntent = new Intent(Intent.ACTION_SEND);
                //mailIntent.setData(Uri.parse("email:" + Email));
                mailIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{Email});
                mailIntent.putExtra(Intent.EXTRA_CC, new String[]{email});
                mailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                mailIntent.putExtra(Intent.EXTRA_TEXT, channel);
                mailIntent.putExtra(Intent.EXTRA_TEXT, message);
                mailIntent.setType("message/rfc822");
                if(mailIntent.resolveActivity(getPackageManager()) != null){
                    startActivity(Intent.createChooser(mailIntent, "Send email via:"));
                    resetForm();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"There is no app that support this action",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

        // Reset the feedback forms.
    private void resetForm() {
        if (mSubjectSpinner != null) mSubjectSpinner.setSelection(0);
        if (mChannelSpinner != null) mChannelSpinner.setSelection(0);
        if (mEmailEditText != null) mEmailEditText.setText("");
        if (mMessageEditText != null) mMessageEditText.setText("");
    }

    public void initializeTextViews(){
        mChannelSpinnerText = (TextView) findViewById(R.id.channel_spinner_text);
        mSubjectSpinnerText = (TextView) findViewById(R.id.subject_spinner_text);
    }

    public void addItemsToSubjectSpinner(){
        // Get a reference to the spinner
        mSubjectSpinner = (Spinner) findViewById(R.id.subjectSpinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> unitTypeSpinnerAdapter = ArrayAdapter.createFromResource(this,

                R.array.feedback_subjects, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        unitTypeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        mSubjectSpinner.setAdapter(unitTypeSpinnerAdapter);
    }

    public void addListenerToSubjectSpinner() {
        mSubjectSpinner = (Spinner) findViewById(R.id.subjectSpinner);
        mSubjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the item selected in the Spinner
                //String itemSelectedInSpinner = parent.getItemAtPosition(position).toString();
                if (parent.getId() == R.id.subjectSpinner) {
                    String selection = (String) parent.getItemAtPosition(position);

                    // Channel feedback allows user to select a specific channel
                    if (selection.equals(getString(R.string.feedback_channel_feedback))) {
                        if (mChannelSpinner != null) mChannelSpinner.setVisibility(View.VISIBLE);
                        if (mChannelSpinnerText != null)
                            mChannelSpinnerText.setVisibility(View.VISIBLE);
                    } else {
                        if (mChannelSpinner != null) mChannelSpinner.setVisibility(View.GONE);
                        if (mChannelSpinnerText != null)
                            mChannelSpinnerText.setVisibility(View.GONE);
                    }
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO maybe add something here later
            }
        });
    }

    public void addItemsToChannelSpinner(){
        // Get a reference to the spinner
        mChannelSpinner = (Spinner) findViewById(R.id.channelSpinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> unitTypeSpinnerAdapter = ArrayAdapter.createFromResource(this,

                R.array.feedback_channels, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        unitTypeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        mChannelSpinner.setAdapter(unitTypeSpinnerAdapter);
    }

    public void addListenerToChannelSpinner() {
        mChannelSpinner = (Spinner) findViewById(R.id.channelSpinner);
        mChannelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the item selected in the Spinner
                String itemSelectedInSpinner = parent.getItemAtPosition(position).toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO maybe add something here later
            }
        });
    }
}
