package com.avaboy.btb.crudsqlite.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iengpho on 8/28/18.
 */

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String KEY_ID = "id";
    private static final String DATABASE_NAME = "contactInfo";
    private static final String TABLE_CONTACT = "contact";

    private static final String KEY_FIRST_NAME = "first_name";
    private static final String KEY_LAST_NAME = "last_name";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_EMAIL = "email";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACT + "(" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_FIRST_NAME + " TEXT, " +
                "" + KEY_LAST_NAME + " TEXT, " + KEY_GENDER + " TEXT, " + KEY_PHONE + " TEXT, " + KEY_EMAIL + " TEXT)";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT);
        onCreate(db);
    }

    public boolean addContact(ContactModel contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FIRST_NAME, contact.getFirstName());
        values.put(KEY_LAST_NAME, contact.getLastName());
        values.put(KEY_GENDER, contact.getGender());
        values.put(KEY_PHONE, contact.getPhone());
        values.put(KEY_EMAIL, contact.getEmail());
        try {
            db.insert(TABLE_CONTACT, null, values);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public ContactModel getContactById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContactModel contact = new ContactModel();
        Cursor cursor = db.query(TABLE_CONTACT, new String[]{KEY_ID, KEY_FIRST_NAME, KEY_LAST_NAME, KEY_GENDER, KEY_PHONE, KEY_EMAIL}, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            contact = new ContactModel(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getColumnName(4), cursor.getColumnName(5));
        }
        db.close();
        if (cursor != null)
            cursor.close();
        return contact;
    }

    public List<ContactModel> getAllContacts() {
        List<ContactModel> contactList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CONTACT;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                ContactModel contact = new ContactModel();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setFirstName(cursor.getString(1));
                contact.setLastName(cursor.getString(2));
                contact.setGender(cursor.getString(3));
                contact.setPhone(cursor.getString(4));
                contact.setEmail(cursor.getString(5));

                contactList.add(contact);

            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return contactList;
    }

    public int getContactCount() {
        String countQuery = "SELECT * FROM " + TABLE_CONTACT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        db.close();
        return cursor.getCount();
    }

    public int updateContact(ContactModel contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FIRST_NAME, contact.getFirstName());
        values.put(KEY_LAST_NAME, contact.getLastName());
        values.put(KEY_GENDER, contact.getGender());
        values.put(KEY_PHONE, contact.getPhone());
        values.put(KEY_EMAIL, contact.getEmail());
        return db.update(TABLE_CONTACT, values, KEY_ID + "= ?", new String[]{String.valueOf(contact.getId())});
    }

    public boolean deleteContact(ContactModel contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACT, KEY_ID + " = ?", new String[]{String.valueOf(contact.getId())});
        db.close();
        return true;
    }

}
