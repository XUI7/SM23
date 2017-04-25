package com.xui.sm23;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Flushable;
import java.io.IOException;

public class sm3 extends AppCompatActivity implements View.OnClickListener
{

    private  String xui = "threads";
    private EditText inputfromuser ;
    private String input;
    private File infile;
    private boolean oneortwo = false;


    //获取文件byte组
    public static byte[] file2bytes(File file) throws IOException {

        File f = file;
        if (!f.exists()) {
            throw new FileNotFoundException();
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(f));
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bos.close();
        }
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
            infile = new File(img_path);//锁定选中文件
            inputfromuser.setText("文件名为："+infile.getName());
            oneortwo = true;
//            Toast.makeText(sm3.this,infile.getName(),Toast.LENGTH_LONG).show();
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
                    byte[] in = null;
                    try {
                        in = file2bytes(infile);//获取文件byte[]
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.i(xui, "文件转换为byte[]："+ in.toString()+ "      "+in.length);
                    if (in.length >= 0x2000000000000000l) //最大长度限制
                    {
                        Toast.makeText(sm3.this, "文件过大无法处理", Toast.LENGTH_SHORT);
                    }
                    else
                    {
                        byte[] out = null;//定义输出byte[]
                        try
                        {
                            out = tohash.hash(in);
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                        sm23.javas.SM3.param pa = new sm23.javas.SM3.param();
                        String output = pa.byte2string(out);//输出结果转为String类型
                        Log.i("threads",output);
                        //输出值存储在根目录
                        try
                        {
                            ByteArrayInputStream inputStream = new ByteArrayInputStream(output.getBytes());
                            FileOutputStream outputStream = new FileOutputStream(Environment.getExternalStorageDirectory()+"/"+input.substring(3)+".txt");
                            int len = -1;
                            while ((len = inputStream.read())!=-1)
                            {
                                outputStream.write(len);
                            }
                            outputStream.close();
                            Toast.makeText(sm3.this,"散列值输出至根目录下，文件夹名称为"+input.substring(3)+".txt",Toast.LENGTH_LONG).show();
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            Toast.makeText(sm3.this, "有错误", Toast.LENGTH_LONG).show();
                        }

                    }

                }
                else
                {
                    input = inputfromuser.getText().toString();
                    Log.i("threads","获取到输入："+input+input.length());
                    inputfromuser.setText("");
                    //开始散列
                    sm23.javas.SM3.hash tohash = new sm23.javas.SM3.hash();
                    byte[] in = input.getBytes();
                    byte[] out = null;
                    try
                    {
                        out = tohash.hash(in);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    sm23.javas.SM3.param pa = new sm23.javas.SM3.param();
                    String output = pa.byte2string(out);//输出结果转为String类型
                    Log.i("threads",output);
                    //输出值存储在根目录
                    try
                    {
                        ByteArrayInputStream inputStream = new ByteArrayInputStream(output.getBytes());
                        FileOutputStream outputStream = new FileOutputStream(Environment.getExternalStorageDirectory()+"/"+input.substring(0,1)+".txt");
                        int len = -1;
                        while ((len = inputStream.read())!=-1)
                        {
                            outputStream.write(len);
                        }
                        outputStream.close();
                        Toast.makeText(sm3.this,"散列值输出至根目录下，文件夹名称为"+input.substring(0,1)+".txt",Toast.LENGTH_LONG).show();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        Toast.makeText(sm3.this, "有错误", Toast.LENGTH_LONG).show();
                    }
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
