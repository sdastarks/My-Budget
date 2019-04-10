package com.example.mybudget;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.dbtest.Models.WishList;

public class myDbHelper extends SQLiteOpenHelper {

    private static myDbHelper instance;
    public SQLiteDatabase db;

    public static final int VERSION = 1;
    private static final String DATABASE_NAME = "myDb.db";

    //WishList Table
    public static final String WISH_LIST = "WishList";
    public static final String WISHLISTID = "wishListId";
    public static final String TITLE = "title";
    public static final String COST = "cost";
    public static final String DATE = "date";

    //Entry Table
    public static final String ENTRY = "entry";
    public static final String ENTRYID = "entryId";
    public static final String ENTRY_DATE = "date";
    public static final String AMOUT = "amount";
    public static final String TYPEOFENTRY = "typeOfEntry";


        public void onConfigure(SQLiteDatabase db){
            super.onConfigure(db);
        }

    public myDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int Version){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL("CREATE TABLE "
                    + WISH_LIST + " ("
                    + WISHLISTID + " INTEGER PRIMARY KEY,"
                    + TITLE + " TEXT NOT NULL,"
                    + COST + " FLOAT);");

            db.execSQL("CREATE TABLE "
                    + ENTRY + " ("
                    + ENTRYID + " INTEGER PRIMARY KEY,"
                    + ENTRY_DATE + " DATE NOT NULL,"
                    + AMOUT + " FLOAT NOT NULL,"
                    + TYPEOFENTRY + " INTEGER);");

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WISH_LIST);
        this.onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
            this.onUpgrade(db, 1,1);
    }

    public String loadWishes(){
        String result = "";
        String query = "SELECT * FROM " + WISH_LIST + ";";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            float cost = cursor.getFloat(2);
            //short date = cursor.getShort(3);
            result += String.valueOf(id + " " + title + " " + cost);
            //System.getProperty("line.separator");
        }
        cursor.close();
        db.close();
        return result;
    }

    public void addWish(WishList wishList){
        ContentValues values = new ContentValues();
        values.put(WISHLISTID, wishList.getWishListId());
        values.put(TITLE, wishList.getTitle());
        values.put(COST, wishList.getCost());
        //values.put(DATE, "10-04-2019 16:55");

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(WISH_LIST, null, values);
        db.close();
    }

    public WishList findWishList(String title){
        String query = "Select * from " + WISH_LIST + "WHERE " + TITLE + " = " +
                "'" + title + "'";
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        WishList wishList = new WishList();

        if(cursor.moveToFirst()){
            cursor.moveToFirst();
            wishList.setWishListId(Integer.parseInt(cursor.getString(0)));
            wishList.setTitle(cursor.getString(1));
            wishList.setCost(Float.parseFloat(cursor.getString(2)));
        } else {
            wishList = null;
        }
        db.close();
        return wishList;
    }

    public boolean deleteWish(int Id){
        boolean result = false;
        String query = "Select * from " + WISH_LIST + "WHERE " + WISHLISTID + " = '" +
                String.valueOf(WISHLISTID) + "'";
        db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        WishList wishList = new WishList();
        if(cursor.moveToFirst()){
            wishList.setWishListId(Integer.parseInt(cursor.getString(0)));
            db.delete(WISH_LIST, WISHLISTID + "=?",
                    new String[] {
                            String.valueOf(wishList.getWishListId())
            });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public boolean updateWish(int Id, String title, Float cost){
        ContentValues args = new ContentValues();
        db = this.getWritableDatabase();
        //args.put(WISHLISTID, Id);
        args.put(TITLE, title);
        args.put(COST, cost);
        return db.update(WISH_LIST, args, WISHLISTID + "=" + Id, null) > 0;
    }
}
