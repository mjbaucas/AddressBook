package com.mjbaucas.addressbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        String CREAATE_CONTACT_TABLE = "CREATE TABLE contacts (" +
                "firstName TEXT, " +
                "lastName TEXT, " +
                "email TEXT, " +
                "phoneNumber TEXT PRIMARY KEY)";
        db.execSQL(CREAATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS contacts");
        this.onCreate(db);
    }

    public void addContact(Contact contact){
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put("firstName", contact.getFirstName());
        values.put("lastName", contact.getLastName());
        values.put("email", contact.getEmail());
        values.put("phoneNumber", contact.getPhoneNumber());

        db.insert("contacts", null, values);
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
                contact.setFirstName(cursor.getString(0));
                contact.setLastName(cursor.getString(1));
                contact.setEmail(cursor.getString(2));
                contact.setPhoneNumber(cursor.getString(3));

                contacts.add(contact);
            } while(cursor.moveToNext());
        }

        return contacts;
    }
}
