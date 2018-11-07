package com.mjbaucas.addressbook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    EditText firstNameFld;
    EditText lastNameFld;
    EditText emailFld;
    EditText phoneNumberFld;

    Button addBtn;
    Button cancelBtn;

    SQLiteHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        db = new SQLiteHelper(this);

        firstNameFld = findViewById(R.id.add_first_name_fld);
        lastNameFld = findViewById(R.id.add_last_name_fld);
        emailFld = findViewById(R.id.add_email_fld);
        phoneNumberFld = findViewById(R.id.add_phone_number_fld);

        addBtn = findViewById(R.id.add_contact_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContactEntry();
            }
        });

        cancelBtn = findViewById(R.id.cancel_add_contact_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void addContactEntry(){
        String firstName = firstNameFld.getText().toString();
        String lastName = lastNameFld.getText().toString();
        String email = emailFld.getText().toString();
        String phoneNumber = phoneNumberFld.getText().toString();

        boolean check = true;
        if (TextUtils.isEmpty(firstName)){
            Toast.makeText(AddActivity.this, "First name field is empty", Toast.LENGTH_SHORT).show();
            check = false;
        }
        if (TextUtils.isEmpty(lastName)){
            Toast.makeText(AddActivity.this, "Last name field is empty", Toast.LENGTH_SHORT).show();
            check = false;
        }
        if (TextUtils.isEmpty(email)){
            Toast.makeText(AddActivity.this, "Email field is empty", Toast.LENGTH_SHORT).show();
            check = false;
        }
        if (TextUtils.isEmpty(phoneNumber)){
            Toast.makeText(AddActivity.this, "Phone number field is empty", Toast.LENGTH_SHORT).show();
            check = false;
        }

        if (check){
            Contact newContact = new Contact(firstName, lastName, email, phoneNumber);
            db.addContact(newContact);
            Toast.makeText(this, "Successfully added contact", Toast.LENGTH_LONG).show();
            clearFields();
        }
    }

    public void clearFields(){
        firstNameFld.setText("");
        lastNameFld.setText("");
        emailFld.setText("");
        phoneNumberFld.setText("");
    }

}
