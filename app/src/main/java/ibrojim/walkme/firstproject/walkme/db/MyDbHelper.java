package ibrojim.walkme.firstproject.walkme.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ibrojim.walkme.firstproject.walkme.db.TagDbSchema.TagTable;
import ibrojim.walkme.firstproject.walkme.db.UserDbSchema.UserTable;

public class MyDbHelper extends SQLiteOpenHelper {


    private static final int VERSION=1;
    private static final String DATABASE_NAME="walkMe.db";


    public MyDbHelper( Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table "+ UserTable.NAME+"("+ UserTable.Field.ID+" integer primary key,"
        +UserTable.Field.NAME+" text,"+UserTable.Field.SECOND_NAME+" text,"+UserTable.Field.AGE+" integer,"
        +UserTable.Field.MY_SELF+" text,"+UserTable.Field.RATING+" integer"+")");

        db.execSQL("create table "+TagTable.NAME+"("+UserTable.Field.ID+" ineger primary key,"
        +TagTable.Field.TAG+" text"+")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
