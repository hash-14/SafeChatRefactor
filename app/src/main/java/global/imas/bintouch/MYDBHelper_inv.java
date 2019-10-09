package global.imas.bintouch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by bachirhabib on 7/6/2017.
 */


public class MYDBHelper_inv extends SQLiteOpenHelper {



    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "bintouchinv.db";

    public static final String TABLE_BOOKS = "books";
    public static final String COLUMN_SCORE = "score";


    // Constructor
    public MYDBHelper_inv(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }
    // onCreate
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String query = "CREATE TABLE " + TABLE_BOOKS + "(" + COLUMN_SCORE  + " TEXT "  +");";
        db.execSQL(query);
    }
    // onUpgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_BOOKS);
        onCreate(db);
    }
    // addBook
    public void addBook(Books book)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_SCORE, book.get_score());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_BOOKS, null, values);
        db.close();
    }
    // deleteBook
    public void deleteBook(String bookName)
    {

    }
    // databaseToString
    public String MAX_SCORE(String a)
    {
        String dbString = "false";
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_BOOKS + " WHERE 1";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        while(!c.isAfterLast())
        {
            if(c.getString(c.getColumnIndex("score")) != null)
            {
                if(c.getString(c.getColumnIndex("score")).equals(a))
                dbString = "true";

                c.moveToNext();
            }
        }
        db.close();
        return dbString;
    }


}

