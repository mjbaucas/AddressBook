package com.mjbaucas.addressbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SearchActivity extends AppCompatActivity {
    EditText firstNameFld;
    EditText lastNameFld;
    EditText phoneNumberFld;

    Button backBtn;
    Button searchBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        firstNameFld = findViewById(R.id.search_first_name_fld);
        lastNameFld = findViewById(R.id.search_last_name_fld);
        phoneNumberFld = findViewById(R.id.search_phone_number_fld);

        searchBtn = findViewById(R.id.search_contact_btn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameFld.getText().toString();
                String lastName = lastNameFld.getText().toString();
                String phoneNumber = phoneNumberFld.getText().toString();

                Intent resultIntent = new Intent(SearchActivity.this, ResultsActivity.class);
                resultIntent.putExtra("firstName", firstName);
                resultIntent.putExtra("lastName", lastName);
                resultIntent.putExtra("phoneNumber", phoneNumber);

                startActivity(resultIntent);
            }
        });

        backBtn = findViewById(R.id.back_search_contact_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
