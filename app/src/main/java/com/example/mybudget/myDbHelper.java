package com.example.mybudget;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mybudget.Models.Entry;
import com.example.mybudget.Models.WishList;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Class to handle database connections and update
 * @author Dawnie Safar
 */
public class myDbHelper extends SQLiteOpenHelper {

    public SQLiteDatabase db;

    public static final int VERSION = 1;
    private static final String DATABASE_NAME = "myDb.db";

    //WishList Table
    public static final String WISH_LIST = "WishList";
    public static final String WISHLISTID = "wishListId";
    public static final String TITLE = "title";
    public static final String COST = "cost";

    //Entry Table
    public static final String ENTRY = "entry";
    public static final String ENTRYID = "entryId";
    public static final String ENTRY_DATE = "date";
    public static final String AMOUNT = "amount";
    public static final String TYPEOFENTRY = "typeOfEntry";
    public static final String DESC = "description";


  /*  public void onConfigure(SQLiteDatabase db){
            super.onConfigure(db);
        }*/

    public myDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int Version){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS "
                    + WISH_LIST + " ("
                    + WISHLISTID + " INTEGER PRIMARY KEY NOT NULL,"
                    + TITLE + " TEXT NOT NULL,"
                    + COST + " FLOAT);");

            db.execSQL("CREATE TABLE IF NOT EXISTS "
                    + ENTRY + " ("
                    + ENTRYID + " INTEGER PRIMARY KEY NOT NULL,"
                    + ENTRY_DATE + " TEXT NOT NULL,"
                    + AMOUNT + " FLOAT NOT NULL,"
                    + TYPEOFENTRY + " INTEGER,"
                    + DESC + " TEXT NOT NULL);");
            this.db=db;
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WISH_LIST);
        db.execSQL("DROP TABLE IF EXISTS " + ENTRY);
        this.onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
            this.onUpgrade(db, 1,1);
    }

    public String loadWishes(){
        String result = "";
        String query = "SELECT * FROM " + WISH_LIST + ";";
        open_db();

        Cursor cursor = db.rawQuery(query, null);

        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            float cost = cursor.getFloat(2);
            result += String.valueOf(id + " " + title + " " + cost);
            //System.getProperty("line.separator");
        }
        cursor.close();
        close_db();
        return result;
    }

    /**
     * method to add wishes to database
     * @param wishList
     */
    public void addWish(WishList wishList){

       open_db();
        ContentValues values = new ContentValues();
        values.put(WISHLISTID, wishList.getWishListId());
        values.put(TITLE, wishList.getTitle());
        values.put(COST, wishList.getCost());

        db.insert(WISH_LIST, null, values);
        close_db();
    }

    public WishList findWishList(String title){
        String query = "Select * from " + WISH_LIST + "WHERE " + TITLE + " = " +
                "'" + title + "'";
       open_db();
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
        close_db();
        return wishList;
    }

    public boolean deleteWish(int Id){
        boolean result = false;
        String query = "Select * from " + WISH_LIST + "WHERE " + WISHLISTID + " = '" +
                String.valueOf(WISHLISTID) + "'";
        open_db();

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
        close_db();
        return result;
    }

    public boolean updateWish(int Id, String title, Float cost){
        ContentValues args = new ContentValues();
        open_db();
        //args.put(WISHLISTID, Id);
        args.put(TITLE, title);
        args.put(COST, cost);
        return db.update(WISH_LIST, args, WISHLISTID + "=" + Id, null) > 0;
    }

    /**
     * this method to add enteries to database
     * @param entry
     */
    public void addEntry(Entry entry){
        open_db();
        ContentValues values = new ContentValues();
        values.put(ENTRYID, autoIdGenerator(entry));
        values.put(ENTRY_DATE, String.valueOf(entry.getDate()));
        values.put(AMOUNT, entry.getAmount());
        values.put(TYPEOFENTRY, entry.getTypeOfEntry());
        values.put(DESC, entry.getDesc());

        db.insert(ENTRY, null, values);
        close_db();
    }

    /**
     * @return  list of all entries
     */
    public ArrayList<Entry> allEntries() throws ParseException {
    open_db();
        ArrayList<Entry> allReconrds = new ArrayList<>();
        String query = "SELECT * FROM " + ENTRY + ";";

        Cursor cursor = db.rawQuery(query, null);
        Entry entry = new Entry();

        while(cursor.moveToNext()){

            entry.setEnteryId(cursor.getInt(0));
            entry.setAmount(cursor.getFloat(2));
            entry.setTypeOfEntry(cursor.getInt(3));
            String date1 = cursor.getString(1);
            DateTimeFormatter formate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate date = LocalDate.parse(date1, formate);

            entry.setDate(date);
            entry.setDesc(cursor.getString(4));
            allReconrds.add(entry);
        }
        return allReconrds;
    }

    public myDbHelper open_db(){
        db = this.getWritableDatabase();
        return this;
    }

    public void close_db(){
        db.close();
    }

    public int autoIdGenerator(Object o){
        String query = "";
         if (o.getClass() == Entry.class) {
             query = "SELECT " + ENTRYID + " FROM " + ENTRY;
         }
        else if(o.getClass() == WishList.class){
            query = "SELECT " + WISHLISTID + " FROM " + WISH_LIST;
         }
        Cursor cursor = db.rawQuery(query, null);
        return cursor.getCount() + 1;
    }
}
