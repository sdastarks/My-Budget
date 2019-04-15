package com.example.mybudget;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mybudget.Models.Entry;
import com.example.mybudget.Models.WishList;

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
    public static final String SAVED = "saved";
    public static final String IMAGE = "image";

    //Entry Table
    public static final String ENTRY = "entry";
    public static final String ENTRYID = "entryId";
    public static final String ENTRY_DATE = "date";
    public static final String AMOUNT = "amount";
    public static final String TYPEOFENTRY = "typeOfEntry";
    public static final String DESC = "description";


    public myDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int Version) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS "
                    + WISH_LIST + " ("
                    + WISHLISTID + " INTEGER PRIMARY KEY NOT NULL,"
                    + TITLE + " TEXT NOT NULL,"
                    + COST + " INTEGER, "
                    + SAVED + " INTEGER, "
                    + IMAGE + " TEXT);");

            db.execSQL("CREATE TABLE IF NOT EXISTS "
                    + ENTRY + " ("
                    + ENTRYID + " INTEGER PRIMARY KEY NOT NULL,"
                    + ENTRY_DATE + " TEXT NOT NULL,"
                    + AMOUNT + " FLOAT NOT NULL,"
                    + TYPEOFENTRY + " INTEGER,"
                    + DESC + " TEXT NOT NULL);");
            this.db = db;
        } catch (SQLException e) {
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
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        this.onUpgrade(db, 1, 1);
    }

    public ArrayList<WishList> loadWishes() {
        open_db();
        ArrayList<WishList> result = new ArrayList<>();
        WishList wish = new WishList();

        Cursor cursor = db.rawQuery("select * from " + WISH_LIST, null);

        while (cursor.moveToNext()) {
            wish.setWishListId(cursor.getInt(0));
            wish.setTitle(cursor.getString(1));
            wish.setCost(cursor.getInt(2));
            wish.setSaved(cursor.getInt(3));
            wish.setImage(cursor.getString(4));
            result.add(wish);
        }
        cursor.close();
        close_db();
        return result;
    }

    /**
     * method to add wishes to database
     *
     * @param wish
     */
    public void addWish(WishList wish) {
        open_db();
        ContentValues values = new ContentValues();
        values.put(WISHLISTID, autoIdGenerator(wish));
        values.put(TITLE, wish.getTitle());
        values.put(COST, wish.getCost());
        values.put(SAVED, wish.getSaved());
        values.put(IMAGE, wish.getImage());

        db.insert(WISH_LIST, null, values);
        close_db();
    }

    public WishList findWishList(String title) {
        String query = "Select * from " + WISH_LIST + "WHERE " + TITLE + " = " +
                "'" + title + "'";
        open_db();
        Cursor cursor = db.rawQuery(query, null);

        WishList wishList = new WishList();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            wishList.setWishListId(Integer.parseInt(cursor.getString(0)));
            wishList.setTitle(cursor.getString(1));
            wishList.setCost(Integer.parseInt(cursor.getString(2)));
            wishList.setSaved(Integer.parseInt(cursor.getString(3)));
            wishList.setImage(cursor.getColumnName(4));
        } else {
            wishList = null;
        }
        close_db();
        return wishList;
    }

    /**
     * deleting a wish from wishlist
     *
     * @param wishId
     * @return
     */
    public boolean deleteWish(int wishId) {
        open_db();
        Cursor cursor = db.rawQuery("SELECT " + WISHLISTID + " FROM " + WISH_LIST, null);
        if (cursor.getCount() > 0) {
            String id = Integer.toString(wishId);
            db.delete(WISH_LIST, WISHLISTID + "=" + id, null);
            cursor.close();
            close_db();
            return true;
        } else return false;
    }

    public boolean updateWish(int Id, String title, int cost, int saved, String image) {
        ContentValues args = new ContentValues();
        open_db();
        //args.put(WISHLISTID, Id);
        args.put(TITLE, title);
        args.put(COST, cost);
        args.put(SAVED, saved);
        args.put(IMAGE, image);
        return db.update(WISH_LIST, args, WISHLISTID + "=" + Id, null) > 0;
    }

    /**
     * this method to add enteries to database
     *
     * @param entry
     */
    public void addEntry(Entry entry) {
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

    public boolean deleteEntry(int entryId) {
        open_db();
        Cursor cursor = db.rawQuery("SELECT " + ENTRYID + " FROM " + ENTRY, null);
        if (cursor.getCount() > 0) {
            String id = Integer.toString(entryId);
            db.delete(ENTRY, ENTRYID + "=" + id, null);
            cursor.close();
            close_db();
            return true;
        } else return false;
    }

    /**
     * @return list of all entries
     */
    public ArrayList<Entry> allEntries() {
        open_db();
        ArrayList<Entry> allReconrds = new ArrayList<>();
        String query = "SELECT * FROM " + ENTRY + ";";

        Cursor cursor = db.rawQuery(query, null);
        Entry entry = new Entry();

        while (cursor.moveToNext()) {

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

    public myDbHelper open_db() {
        db = this.getWritableDatabase();
        return this;
    }

    public void close_db() {
        db.close();
    }

    /**
     * method genertates an auto id for tables according to its type of object
     *
     * @param o
     * @return
     */
    public int autoIdGenerator(Object o) {
        String query = "";
        if (o.getClass() == Entry.class) {
            query = "SELECT " + ENTRYID + " FROM " + ENTRY;
        } else if (o.getClass() == WishList.class) {
            query = "SELECT " + WISHLISTID + " FROM " + WISH_LIST;
        }
        Cursor cursor = db.rawQuery(query, null);
        return cursor.getCount() + 1;
    }

    /**
     * @return total amount of expneses
     */
    public float calculateExpenses() {
        ArrayList<Entry> allEntries = allEntries();
        float total = 0;
        for (Entry e : allEntries) {
            if (e.getTypeOfEntry() == 0)
                total += e.getAmount();
        }
        return total;
    }

    /**
     * @return total amount of income
     */
        public float calcIncome(){
            ArrayList<Entry> allEntries = allEntries();
            float total = 0;
            for(Entry e : allEntries){
                if(e.getTypeOfEntry() == 1)
                    total += e.getAmount();
            }
            return total;
        }

    /**
     * @return total amount spend on wishes
     */
        public float calcWish(){
            ArrayList<Entry> allEntries = allEntries();
            float total = 0;
            for(Entry e : allEntries){
                if(e.getTypeOfEntry() == 2)
                    total += e.getAmount();
            }
            return total;
        }

    /**
     * @return total amount of earnings
     */
        public float calcEarning(){
        ArrayList<Entry> allEntries = allEntries();
        float total = 0;
        for(Entry e : allEntries){
            if(e.getTypeOfEntry() == 3)
                total += e.getAmount();
        }
        return total;
        }

    /**
     * @return all expenses entries
     */
    public ArrayList<Entry> expensesEntries(){
        ArrayList<Entry> allEntries = allEntries();
        ArrayList<Entry> allExpenses = new ArrayList<>();

        for (Entry e : allEntries) {
            if(e.getTypeOfEntry() == 0) {
                allExpenses.add(e);
            }
        }
        return allExpenses;
    }

    /**
     * @return all income entries
     */
    public ArrayList<Entry> incomeEntries(){
        ArrayList<Entry> allEntries = allEntries();
        ArrayList<Entry> allIncome = new ArrayList<>();

        for (Entry e : allEntries) {
            if(e.getTypeOfEntry() == 1) {
                allIncome.add(e);
            }
        }
        return allIncome;
    }

    /**
     * @return all wishes alocated entries
     */
    public ArrayList<Entry> wishEntries(){
        ArrayList<Entry> allEntries = allEntries();
        ArrayList<Entry> allWishes = new ArrayList<>();

        for (Entry e : allEntries) {
            if(e.getTypeOfEntry() == 2) {
                allWishes.add(e);
            }
        }
        return allWishes;
    }

    /**
     * @return all earnings entries
     */
    public ArrayList<Entry> earningsEntries(){
        ArrayList<Entry> allEntries = allEntries();
        ArrayList<Entry> allEarnings = new ArrayList<>();

        for (Entry e : allEntries) {
            if(e.getTypeOfEntry() == 3) {
                allEarnings.add(e);
            }
        }
        return allEarnings;
    }
}
