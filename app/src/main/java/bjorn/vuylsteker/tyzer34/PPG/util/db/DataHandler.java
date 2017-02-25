package bjorn.vuylsteker.tyzer34.PPG.util.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import bjorn.vuylsteker.tyzer34.PPG.R;

/**
 * Created by Tyzer34 on 24/05/2016.
 */
public class DataHandler {

    public static final String NAME = "name";
    public static final String FRANCHISE = "franchise";
    public static final String TYPE = "type";
    public static final String NUMBER = "number";
    public static final String BARCODE = "barcode";
    public static final String TABLE_NAME = "barcode_table";
    public static final String DB_NAME = "pop_db";
    public static final int DB_VERSION = 6;
    public static final String TABLE_CREATE = "create table " + TABLE_NAME + " (" + NAME + " text not null, " + FRANCHISE + " text not null, " + TYPE + " text not null, " + NUMBER + " text not null, " + BARCODE + " text not null);";

    DataBaseHelper dbHelper;
    Context ctx;
    SQLiteDatabase db;
    Boolean dbLoaded = Boolean.FALSE;

    public DataHandler(Context ctx) {
        this.ctx = ctx;
        this.dbHelper = new DataBaseHelper(ctx);
        open();
        try {
            loadData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        close();
    }

    public DataHandler open() {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public long insertData(String name, String franchise, String type, String number, String barcode) {
        ContentValues content = new ContentValues();
        content.put(NAME, name);
        content.put(FRANCHISE, franchise);
        content.put(TYPE, type);
        content.put(NUMBER, number);
        content.put(BARCODE, barcode);
        return db.insertOrThrow(TABLE_NAME, null, content);
    }

    public Cursor returnData() {
        return db.query(TABLE_NAME, new String[]{NAME, FRANCHISE, TYPE, NUMBER, BARCODE}, null, null, null, null, null);
    }

    public void loadData() throws IOException {
        if (dbLoaded == Boolean.FALSE) {
            final Resources resources = ctx.getResources();
            InputStream inputStream = resources.openRawResource(R.raw.barcodelist);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            db.beginTransaction();
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] strings = TextUtils.split(line, ";");
                    long id = insertData(strings[0].trim(), strings[1].trim(), strings[2].trim(), strings[3].trim(), strings[4].trim());
                    if (id < 0) {
                        Log.e("DataHandler", "unable to add word: " + strings[0].trim());
                    }
                }
            } finally {
                reader.close();
                db.setTransactionSuccessful();
                db.endTransaction();
                dbLoaded = Boolean.TRUE;
            }
        }
    }

    public Cursor searchDataByBarcode(String barcode) {
        String[] tableColumns =  new String[]{NAME, TYPE, NUMBER};
        String whereClause = BARCODE + " =?";
        String[] whereArgs = new String[] {barcode};
        String orderBy = NAME;
        return db.query(TABLE_NAME, tableColumns, whereClause, whereArgs, null, null, orderBy);
    }

    private static class DataBaseHelper extends SQLiteOpenHelper {

        public DataBaseHelper(Context ctx) {
            super(ctx, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try{
                db.execSQL(TABLE_CREATE);
            } catch (SQLException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

}
