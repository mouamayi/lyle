package com.lyle.android.db;

import android.content.Context;
import android.widget.Toast;

import com.tencent.wcdb.database.SQLiteDatabase;
import com.tencent.wcdb.database.SQLiteOpenHelper;

/**
 * Created by ZHANPENG on 2018/3/20.
 */

public class MyDBHelper extends SQLiteOpenHelper{
    private static final String CREATE_PEOPLE = "create table people(" +
            "id integer primary key autoincrement," +
            "name text)";
    private Context context;

    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PEOPLE);
        Toast.makeText(context,"db was created",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
