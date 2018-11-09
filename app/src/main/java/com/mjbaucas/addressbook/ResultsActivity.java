package com.mjbaucas.addressbook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class ResultsActivity extends AppCompatActivity {
    LinearLayout resultList;
    SQLiteHelper db;

    Button backBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultList = findViewById(R.id.result_list);

        db = new SQLiteHelper(this);
        searchContactList();

        backBtn = findViewById(R.id.back_result_button);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void searchContactList(){
        Bundle searchExtras = getIntent().getExtras();
        String firstNameInt = searchExtras.getString("firstName");
        String lastNameInt = searchExtras.getString("lastName");
        String phoneNumberInt = searchExtras.getString("phoneNumber");

        final List<Contact> contactsList = db.searchContacts(firstNameInt, lastNameInt, phoneNumberInt);
        TextView tempView;
        LinearLayout tempLayout;

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(15, 15,15,15);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        for (int i = 0; i < contactsList.size(); i++){
            final Contact contact = contactsList.get(i);
            String firstName = contact.getFirstName();
            String lastName = contact.getLastName();
            String email = contact.getEmail();
            String phoneNumber = contact.getPhoneNumber();

            tempView = new TextView(this);
            tempLayout = new LinearLayout(this);
            tempLayout.setLayoutParams(layoutParams);

            tempView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            tempView.setTextColor(getResources().getColor(R.color.white));
            String textValue = "Name: " + firstName + " " + lastName + "\nEmail: " + email + "\nPhone: " + phoneNumber;
            tempView.setText(textValue);
            tempView.setLayoutParams(textParams);
            tempView.setPadding(20,15,20,15);

            tempLayout.addView(tempView);
            resultList.addView(tempLayout);
        }

        if (contactsList.size() == 0) {
            tempView = new TextView(this);
            tempLayout = new LinearLayout(this);
            tempLayout.setLayoutParams(layoutParams);

            tempView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            tempView.setTextColor(getResources().getColor(R.color.white));
            String textValue = "There are no results to show";
            tempView.setText(textValue);
            tempView.setLayoutParams(textParams);
            tempView.setPadding(20,15,20,15);

            tempLayout.addView(tempView);
            resultList.addView(tempLayout);
        }
    }
}
