package com.mjbaucas.addressbook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class ShowActivity extends AppCompatActivity {
    SQLiteHelper db;
    LinearLayout contactLister;
    Button backBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        db = new SQLiteHelper(this);

        contactLister = findViewById(R.id.contact_list);

        populateContactList();

        backBtn = findViewById(R.id.back_show_all_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public void populateContactList(){
        List<Contact> contactsList = db.getAllCOntacts();
        TextView tempView;
        LinearLayout tempLayout;

        for (int i = 0; i < contactsList.size(); i++){
            String firstName = contactsList.get(i).getFirstName();
            String lastName = contactsList.get(i).getLastName();
            String email = contactsList.get(i).getEmail();
            String phoneNumber = contactsList.get(i).getPhoneNumber();

            tempView = new TextView(this);
            tempLayout = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 10,10,10);
            tempLayout.setLayoutParams(params);

            tempView.setText("Name: " + firstName + " " + lastName + "\nEmail: " + email + "\nPhone: " + phoneNumber);
            tempLayout.addView(tempView);
            contactLister.addView(tempLayout);
        }
    }

}
