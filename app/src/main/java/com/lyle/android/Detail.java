package com.lyle.android;

import android.database.DatabaseUtils;
import android.location.Address;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.wcdb.Cursor;
import com.tencent.wcdb.database.SQLiteDatabase;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static com.tencent.wcdb.database.SQLiteDatabase.openDatabase;

public class Detail extends AppCompatActivity {
    private SQLiteDatabase database = null;
    private TextView textView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        textView = (TextView)findViewById(R.id.textView2);
        database = openDatabase(MainActivity.db_path,null,SQLiteDatabase.OPEN_READWRITE);
        Date date = new Date(20170101);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        textView.setText(timestamp.toString());
        //textView.setText(timestamp.toString());
        //Long l = new Long(System.currentTimeMillis());
        //Toast.makeText(this,l.toString(),Toast.LENGTH_LONG).show();
        //textView.setText(l.toString());

        Address address = new Address(new Locale("Chinese","China","Beijing"));
        address.setCountryName("China");
        String s = address.getCountryName();
        textView.append(s);
        //database.execSQL("insert into user(id,name,birth) values(?,?,?)",new Object[]{"201530541495","zhanpeng",date});
        //database.execSQL("insert into user(id,name,birth,sex,address,photo) values(?,?,?,?,?,?)",new Object[]{"201530541494","zhanpeng",date,null,null,null});
        //database.execSQL("insert into content(userId,time,loc,desc)");
        //generateUser();
        Cursor cursor = database.rawQuery("select * from content",null);
        if(cursor.moveToFirst()){
            do{
                String id = cursor.getString(cursor.getColumnIndex("userId"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                String loc = cursor.getString(cursor.getColumnIndex("loc"));
                String desc = cursor.getString(cursor.getColumnIndex("desc"));
                //SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");

                StringBuilder sb = new StringBuilder(textView.getText()).append('\n')
                        .append(id).append("\n")
                        .append(time).append("\n")
                        .append(loc).append('\n')
                        .append(desc).append('\n');
                textView.setText(sb);
            }while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
    }

    private void generateUser(){
        if(!database.isOpen()) {
            Toast.makeText(this,"generate User failed",Toast.LENGTH_LONG);
            return;
        }

        String userId = "201530541494";
        Timestamp time;
        String loc = "SCUT";
        String desc = "我是测试我最大";
        String url;
        int n = 10;
        for(int i=0;i<n;i++){
            time = new Timestamp(System.currentTimeMillis());
            desc = desc+1;
            url = null;
            database.execSQL("insert into content(userId,time,loc,desc,url) values(?,?,?,?,?)",
                    new Object[]{userId,time,loc,desc,url});
        }
    }
}
