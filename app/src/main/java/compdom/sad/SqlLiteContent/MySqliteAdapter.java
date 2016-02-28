package compdom.sad.SqlLiteContent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.nio.Buffer;
import java.util.ArrayList;

import compdom.sad.Toastnotify;

/**
 * Created by adity on 23/01/2016.
 */
public class MySqliteAdapter
{
    Mysqlitehelper mysqlitehelper;

    public MySqliteAdapter(Context context)
    {
        mysqlitehelper=new Mysqlitehelper(context);
    }

    public long insertData(int ColumnID)
    {
        SQLiteDatabase db= mysqlitehelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(Mysqlitehelper.ColumnMainDBid,ColumnID);
       // contentValues.put(Mysqlitehelper.ColumnImdbID,ImdbID);
        long verify_insert= db.insert(Mysqlitehelper.tablename,null,contentValues);
        return verify_insert;
    }

    public ArrayList selectData()
    {
        SQLiteDatabase db=mysqlitehelper.getReadableDatabase();
        Cursor cursor=db.query(Mysqlitehelper.tablename, null, null, null, null, null, null);
        //StringBuffer buffer=new StringBuffer();
      // int[] intarr=new int[cursor.getCount()];
        ArrayList<Integer> intarr=new ArrayList<Integer>();
        while(cursor.moveToNext())
        {
            int index1=cursor.getColumnIndex(Mysqlitehelper.ColumnMainDBid);
            int databaseID=cursor.getInt(index1);
           // int index2=cursor.getColumnIndex(Mysqlitehelper.ColumnImdbID);
           // String ImdbID=cursor.getString(index2);
            intarr.add(databaseID);

        }
        return  intarr;
    }

    public int deleteData(int id)
    {
        SQLiteDatabase db=mysqlitehelper.getWritableDatabase();
        String[] whereArgs={Integer.toString(id)};
        int count=db.delete(Mysqlitehelper.tablename,Mysqlitehelper.ColumnMainDBid+ "=?", whereArgs);
        return count;
    }

    public boolean checkdata(int value)
    {
        SQLiteDatabase db=mysqlitehelper.getReadableDatabase();
        Boolean br=false;
        Cursor cursor=db.query(Mysqlitehelper.tablename, null, null, null, null, null, null);
        while(cursor.moveToNext())
        {
            int index1=cursor.getColumnIndex(Mysqlitehelper.ColumnMainDBid);
            int databaseID=cursor.getInt(index1);
            if(databaseID==value)
            {
                br=true;
            }
        }
        return br;
    }

    class Mysqlitehelper extends SQLiteOpenHelper
    {
        private static final String databasename = "LocalAndroid.db";
        private static final String tablename = "Favourites";
        private static final int version = 13;
        private static final String ColumnImdbID = "ImdbId";
        private static final String ColumnMainDBid = "id";
       /* private static final String tablecreate = "CREATE TABLE " + tablename + " (" + ColumnMainDBid + " INTEGER,"
                + ColumnImdbID + " VARCHAR(20)" +
                ");";*/

        private static final String tablecreate = "CREATE TABLE " + tablename + " (" + ColumnMainDBid + " INTEGER NOT NULL UNIQUE);";
        private static final String droptable = "DROP TABLE " + tablename;

        Context context;

        public Mysqlitehelper(Context context) //initial default constructo:public MySqliteAdapter(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
        {
            super(context, databasename, null, version);
            this.context = context;

        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try
            {
                db.execSQL(tablecreate);
                new Toastnotify(context, "OnCreate was successful");
            } catch (SQLException e)
            {
                new Toastnotify(context, "OnCreate was successful");
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            try
            {
                db.execSQL(droptable);
                new Toastnotify(context, "OnUpgrade was successful");
            } catch (SQLException e)
            {
                new Toastnotify(context, "OnUpgrade Failed");
            }
            onCreate(db);
        }
    }
}
