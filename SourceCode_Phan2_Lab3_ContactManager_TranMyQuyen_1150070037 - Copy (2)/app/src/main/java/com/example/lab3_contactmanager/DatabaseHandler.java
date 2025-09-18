package com.example.lab3_contactmanager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
public class DatabaseHandler  extends SQLiteOpenHelper{
    // Phiên bản của CSDL
    private static final int DATABASE_VERSION = 1;
    // Tên của CSDL
    private static final String DATABASE_NAME = "contactsManager";
    // Tên bảng
    private static final String TABLE_CONTACTS = "contacts";
    // Tên các cột trong bảng
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Phương thức này được gọi khi CSDL được tạo lần đầu tiên
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Câu lệnh SQL để tạo bảng contacts
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PH_NO + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Phương thức này được gọi khi nâng cấp phiên bản CSDL
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa bảng cũ nếu nó đã tồn tại
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        // Tạo lại bảng
        onCreate(db);
    }

    /**
     * Các hàm CRUD (Create, Read, Update, Delete)
     */

    // Thêm một contact mới
    void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName()); // Tên contact
        values.put(KEY_PH_NO, contact.getPhoneNumber()); // Số điện thoại

        // Chèn một dòng mới vào bảng
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Đóng kết nối CSDL
    }

    // Lấy tất cả các contact
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<>();
        // Câu lệnh truy vấn
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Lặp qua tất cả các dòng và thêm vào danh sách
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setID(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)));
                contact.setName(cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME)));
                contact.setPhoneNumber(cursor.getString(cursor.getColumnIndexOrThrow(KEY_PH_NO)));
                // Thêm contact vào danh sách
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return contactList;
    }

    // Xóa một contact
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[]{String.valueOf(contact.getID())});
        db.close();
    }

    // Xóa tất cả contact (hàm này tự thêm để tiện cho việc test)
    public void deleteAllContacts() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, null, null);
        db.close();
    }
}
