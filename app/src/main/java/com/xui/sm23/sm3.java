package com.xui.sm23;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class sm3 extends AppCompatActivity implements View.OnClickListener
{

    private  String xui = "threads";
    private EditText inputfromuser ;
    private String input;
    private File infile;
    private boolean oneortwo = false;



    private static byte[] file2byte(String filePath)//获取文件2进制
    {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sm3_layout);
        inputfromuser =  (EditText) findViewById(R.id.sm3_edittext);
        Button button_queding = (Button) findViewById(R.id.sm3_button_queding);
        button_queding.setOnClickListener(this);
        Button button_xuanzewenjian = (Button) findViewById(R.id.sm3_button_xuanzewenjian);
        button_xuanzewenjian.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == Activity.RESULT_OK)
        {//是否选择，没选择就不会继续
            Uri uri = data.getData();
            String[] proj = { MediaStore.Images.Media.DATA };
            Cursor actualimagecursor = managedQuery(uri,proj,null,null,null);
            int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualimagecursor.moveToFirst();
            String img_path = actualimagecursor.getString(actual_image_column_index);
            infile = new File(img_path);
            Toast.makeText(sm3.this,infile.getName(),Toast.LENGTH_LONG).show();
            inputfromuser.setText("文件名为："+infile.getName());
            oneortwo = true;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.sm3_button_queding:
                if (oneortwo)
                {
                    sm23.javas.SM3.hash tohash = new sm23.javas.SM3.hash();
                    byte[] in = file2byte(infile.getPath());
                    Log.i(xui,in.toString());
//                    byte[] out = null;
//                    try
//                    {
//                        out = tohash.hash(in);
//                    }
//                    catch (IOException e)
//                    {
//                        e.printStackTrace();
//                    }
//                    sm23.javas.SM3.param pa = new sm23.javas.SM3.param();
//                    String output = pa.byte2string(out);//输出结果转为String类型
////                Toast.makeText(sm3.this,output,Toast.LENGTH_LONG).show();
//                    Log.i("threads",output);
//                    //输出值存储在根目录
//                    try
//                    {
//                        ByteArrayInputStream inputStream = new ByteArrayInputStream(output.getBytes());
//                        FileOutputStream outputStream = new FileOutputStream(Environment.getExternalStorageDirectory()+"/"+input.substring(3)+".txt");
//                        int len = -1;
//                        while ((len = inputStream.read())!=-1)
//                        {
//                            outputStream.write(len);
//                        }
//                        outputStream.close();
//                        Toast.makeText(sm3.this,"散列值输出至根目录下，文件夹名称为"+input.substring(3)+".txt",Toast.LENGTH_LONG).show();
//                    }
//                    catch (Exception e)
//                    {
//                        e.printStackTrace();
//                        Toast.makeText(sm3.this,"有错误" ,Toast.LENGTH_LONG).show();
//
//                    }
//                }
//                input = inputfromuser.getText().toString();
//                Log.i("threads","获取到输入："+input);
//                inputfromuser.setText("");
//                //开始散列
//                sm23.javas.SM3.hash tohash = new sm23.javas.SM3.hash();
//                byte[] in = input.getBytes();
//                byte[] out = null;
//                try
//                {
//                    out = tohash.hash(in);
//                }
//                catch (IOException e)
//                {
//                    e.printStackTrace();
//                }
//                sm23.javas.SM3.param pa = new sm23.javas.SM3.param();
//                String output = pa.byte2string(out);//输出结果转为String类型
////                Toast.makeText(sm3.this,output,Toast.LENGTH_LONG).show();
//                Log.i("threads",output);
//                //输出值存储在根目录
//                try
//                {
//                    ByteArrayInputStream inputStream = new ByteArrayInputStream(output.getBytes());
//                    FileOutputStream outputStream = new FileOutputStream(Environment.getExternalStorageDirectory()+"/"+input.substring(3)+".txt");
//                    int len = -1;
//                    while ((len = inputStream.read())!=-1)
//                    {
//                        outputStream.write(len);
//                    }
//                    outputStream.close();
//                    Toast.makeText(sm3.this,"散列值输出至根目录下，文件夹名称为"+input.substring(3)+".txt",Toast.LENGTH_LONG).show();
//                }
//                catch (Exception e)
//                {
//                    e.printStackTrace();
//                    Toast.makeText(sm3.this,"有错误" ,Toast.LENGTH_LONG).show();
//
                }
                break;

            case R.id.sm3_button_xuanzewenjian:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent,1);
            default:
                break;
        }
    }
}
