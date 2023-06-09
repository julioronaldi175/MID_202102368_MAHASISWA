package com.example.mid_202102368_mahasiswa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "mid_202102368.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table biodata(nim TEXT primary key, nama TEXT, jk TEXT, alamat TEXT, email TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists biodata");
    }

    public Boolean insertData(String nim, String name, String jk, String alamat, String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("nim", nim);
        values.put("name", name);
        values.put("jk", jk);
        values.put("alamat", alamat);
        values.put("email", email);

        long result = db.insert("biodata", null, values);
        if(result == 1) return false;
        else return true;
    }

    public Boolean checkNIM(String nim){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from biodata where nim=?", new String[] {nim});

        if(cursor.getCount() > 0){
            return true;
        }else return false;
    }

    public Cursor tampilData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from biodata", null);
        return cursor;
    }
}
