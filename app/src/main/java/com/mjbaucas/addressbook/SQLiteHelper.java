package com.mjbaucas.addressbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME= "ContactDB";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACT_TABLE = "CREATE TABLE contacts (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "firstName TEXT, " +
                "lastName TEXT, " +
                "email TEXT," +
                "phoneNumber TEXT UNIQUE)";
        db.execSQL(CREATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS contacts");
        this.onCreate(db);
    }

    public boolean addContact(Context context, Contact contact){
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put("firstName", contact.getFirstName());
        values.put("lastName", contact.getLastName());
        values.put("email", contact.getEmail());
        values.put("phoneNumber", contact.getPhoneNumber());

        try {
            db.insertOrThrow("contacts", null, values);
            return true;
        } catch (SQLiteConstraintException e) {
            Toast.makeText(context, "Phone number needs to be unique", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public boolean deleteContact(Context context, Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("contacts", "phoneNumber=?", new String[]{contact.getPhoneNumber()});
        return true;
    }

    public List<Contact> getAllContacts(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM contacts";
        Cursor cursor = db.rawQuery(query, null);

        List<Contact> contacts = new ArrayList<>();
        Contact contact;
        if (cursor.moveToFirst()){
            do {
                contact = new Contact();
                contact.setFirstName(cursor.getString(1));
                contact.setLastName(cursor.getString(2));
                contact.setEmail(cursor.getString(3));
                contact.setPhoneNumber(cursor.getString(4));

                contacts.add(contact);
            } while(cursor.moveToNext());
        }

        Collections.sort(contacts, new Comparator<Contact>() {
            @Override
            public int compare(Contact o1, Contact o2) {
                return o1.getFirstName().compareToIgnoreCase(o2.getFirstName());
            }
        });

        return contacts;
    }

    public List<Contact> searchContacts(String firstName, String lastName, String phoneNumber){
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = "";
        List<String> fields = new ArrayList<>();
        int set = 0;
        if (!firstName.isEmpty()){
            selection = selection + "firstName = ?";
            fields.add(firstName);
            set = 1;
        }

        if (!lastName.isEmpty()){
            if (set == 1){ selection = selection + " AND "; }
            selection = selection + "lastName = ?";
            fields.add(lastName);
            set = 1;
        }

        if (!phoneNumber.isEmpty()){
            if (set == 1){ selection = selection + " AND "; }
            fields.add(phoneNumber);
            selection = selection + "phoneNumber = ?";
        }

        String[] fieldArr = fields.toArray(new String[0]);
        Cursor cursor = db.query(
                "contacts",
                new String[]{"id", "firstName", "lastName", "email", "phoneNumber"},
                selection,
                fieldArr,
                null, null, null, null
                );

        List<Contact> contacts = new ArrayList<>();
        Contact contact;
        if (cursor.moveToFirst()){
            do {
                contact = new Contact();
                contact.setFirstName(cursor.getString(1));
                contact.setLastName(cursor.getString(2));
                contact.setEmail(cursor.getString(3));
                contact.setPhoneNumber(cursor.getString(4));

                contacts.add(contact);
            } while(cursor.moveToNext());
        }

        Collections.sort(contacts, new Comparator<Contact>() {
            @Override
            public int compare(Contact o1, Contact o2) {
                return o1.getFirstName().compareToIgnoreCase(o2.getFirstName());
            }
        });

        return contacts;
    }
}
