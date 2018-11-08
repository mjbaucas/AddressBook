package com.mjbaucas.addressbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
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

    public List<Contact> getAllCOntacts(){
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

        return contacts;
    }
}
