package com.lyle.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.lyle.android.db.MyDBHelper;
import com.tencent.wcdb.database.SQLiteDatabase;

public class MainActivity extends AppCompatActivity {
    public static final String DB_NAME = "People.db";
    private TextView textView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.textView);

        MyDBHelper myDBHelper = new MyDBHelper(this,DB_NAME,null,1);
        SQLiteDatabase database = myDBHelper.getReadableDatabase();

        if(database.isOpen()){
            String path = database.getPath();
            textView.setText(path);
        }else {
            textView.setText("open failed");
        }

    }
}
