package com.lyle.android.db;

import android.content.Context;
import android.widget.Toast;

import com.lyle.android.MainActivity;
import com.tencent.wcdb.database.SQLiteDatabase;
import com.tencent.wcdb.database.SQLiteOpenHelper;

/**
 * Created by ZHANPENG on 2018/3/20.
 */

public class MyDBHelper extends SQLiteOpenHelper{
    /*id
    用户表：
    用户id是定长的数字串，所以用text
    photo是用户头像存放的位置
     */
    private static final String CREATE_USER = "create table user(" +
            "id text primary key, " +
            "name text not null, " +
            "sex integer, " +
            "birth text, " +
            "address text, " +
            "photo text," +
            "token text)";
    /*
    动态内容表：
    desc是动态中的文字描述，可以为空
    url是图片或者视频的地址
     */
    private static final String CREATE_CONTENT = "create table content(" +
            "id integer primary key autoincrement," +
            "userId text not null," +
            "time text not null, " +
            "loc text not null," +
            "desc text," +
            "url text)";
    /*
    留言表：
    发送者和接收者不能为空
     */
    private static final String CREATE_MESSAGE = "create table message(" +
            "id integer primary key autoincrement," +
            "senderId text not null," +
            "recverId text not null," +
            "time text not null," +
            "words text not null)";

    private Context context;

    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_CONTENT);
        db.execSQL(CREATE_MESSAGE);
        Toast.makeText(context,"db was created",Toast.LENGTH_LONG).show();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //context.deleteDatabase("People.db");
        db.execSQL("drop table if exists user");
        db.execSQL("drop table if exists content");
        db.execSQL("drop table if exists massage");

        onCreate(db);
    }
}
