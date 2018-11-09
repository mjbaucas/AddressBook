package com.mjbaucas.addressbook;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

    @Override
    public void onResume() {
        super.onResume();
        contactLister.removeAllViews();
        populateContactList();
    }

    public void populateContactList(){
        final List<Contact> contactsList = db.getAllCOntacts();
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

            final AlertDialog.Builder deleteDialog = new AlertDialog.Builder(this)
                    .setTitle("Delete Contact")
                    .setMessage("Are you sure you want to delete " + firstName + " from your contacts?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (db.deleteContact(ShowActivity.this, contact)){
                                Toast.makeText(ShowActivity.this, "Contact successfully deleted", Toast.LENGTH_SHORT);
                                contactLister.removeAllViews();
                                populateContactList();
                            }
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            tempView = new TextView(this);
            tempLayout = new LinearLayout(this);
            tempLayout.setLayoutParams(layoutParams);

            tempView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            tempView.setTextColor(getResources().getColor(R.color.white));
            String textValue = "Name: " + firstName + " " + lastName + "\nEmail: " + email + "\nPhone: " + phoneNumber;
            tempView.setText(textValue);
            tempView.setLayoutParams(textParams);
            tempView.setPadding(20,15,20,15);
            tempView.setClickable(true);
            tempView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteDialog.show();
                }
            });

            tempLayout.addView(tempView);
            contactLister.addView(tempLayout);
        }
    }

}
