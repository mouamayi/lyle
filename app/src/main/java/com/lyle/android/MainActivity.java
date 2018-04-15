package com.lyle.android;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lyle.android.db.MyDBHelper;
import com.tencent.wcdb.database.SQLiteDatabase;

import java.io.File;
import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {
    public static final String DB_NAME = "lyle.db";
    public static String db_path = null;
    private TextView textView = null;
    private ImageView imageView = null;
    private Button button = null;
    private SQLiteDatabase database = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.textView);

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.INTERNET},1);
        }

        MyDBHelper myDBHelper = new MyDBHelper(this,DB_NAME,null,1);
        if(myDBHelper!=null) {
            database = myDBHelper.getReadableDatabase();
        }
        else{
            Toast.makeText(this,"db create failed",Toast.LENGTH_LONG);
            finish();
        }

        if(database.isOpen()){
            db_path = database.getPath();
            textView.setText(db_path);
        }else {
            textView.setText("open failed");
            finish();
        }
        database.close();
        myDBHelper.close();

        Intent intent = new Intent(MainActivity.this,Detail.class);
        //startActivity(intent);

        imageView = (ImageView)findViewById(R.id.imageView2);
        //imageView.setImageURI(Uri.fromFile(new File("/sdcard/PianoDream/screenshot.png")));
        Glide.with(this)
                .load("http://img.my.csdn.net/uploads/201211/18/1353170697_1414.png")
                .into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
                intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
                intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */

                startActivityForResult(intent, 1);
            }
        });

        button = (Button)findViewById(R.id.btn1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://google.com"));
                startActivity(intent);
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    String img_url = uri.getPath();//这是本机的图片路径
                    ContentResolver cr = this.getContentResolver();
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                        ImageView imageView = (ImageView) findViewById(R.id.imageView2);
                    /* 将Bitmap设定到ImageView */
                        imageView.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.e("Exception", e.getMessage(), e);
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
