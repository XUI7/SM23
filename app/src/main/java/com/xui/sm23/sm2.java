package com.xui.sm23;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECFieldElement;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.spec.ECPoint;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECKeyGenerationParameters;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECFieldElement;
import org.bouncycastle.math.ec.ECFieldElement.Fp;

public class sm2 extends AppCompatActivity implements View.OnClickListener
{
    private String xui = "threads";
    private TextView textview;
    private EditText ID,M,pa_x,pa_y,da,r,s;
    private Button sign,check;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sm2_layout);

        textview = (TextView) findViewById(R.id.sm2_textView);
        ID = (EditText) findViewById(R.id.sm2_editText_IDA);
        M = (EditText) findViewById(R.id.sm2_editText_M);
        pa_x = (EditText) findViewById(R.id.sm2_editText_gongyue_x);
        pa_y = (EditText) findViewById(R.id.sm2_editText_gongyue_y);
        da = (EditText) findViewById(R.id.sm2_editText_siyue);
        r = (EditText) findViewById(R.id.sm2_editText_R);
        s = (EditText) findViewById(R.id.sm2_editText_S);

        sign = (Button) findViewById(R.id.sm2_button_qianming);
        check = (Button) findViewById(R.id.sm2_button_yanqian);
        sign.setOnClickListener(this);
        check.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.sm2_button_qianming: {
                Log.i(xui, "开始进行签名");
                //各位朋友！签名开始啦！
                byte[] uid = ID.getText().toString().getBytes();
                byte[] m = M.getText().toString().getBytes();
                String das = da.getText().toString();
                BigInteger dab = new BigInteger(das, 16);
                byte[] da = dab.toByteArray();
                //初始化签名函数
                sm23.javas.SM2.sign sig = new sm23.javas.SM2.sign();
                BigInteger[] result = null;
                try
                {
                    result = sig.ToSign(uid, da, m);//签名
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                r.setText(result[1].toString(16));
                s.setText(result[2].toString(16));
                //输出值存储在根目录
                //将所有需要输出的数据整合
                String output = "r:";
                output = output + r.getText().toString();
                output = output + "        ";
                output = output + "s:";
                output = output + s.getText().toString();
                try
                {
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(output.getBytes());
                    FileOutputStream outputStream = new FileOutputStream(Environment.getExternalStorageDirectory()+"/签名文件_"+M.getText().toString().substring(0,1)+".txt");
                    int len = -1;
                    while ((len = inputStream.read())!=-1)
                    {
                        outputStream.write(len);
                    }
                    outputStream.close();
                    Toast.makeText(sm2.this,"散列值输出至根目录下，文件夹名称为:签名文件_"+M.getText().toString().substring(0,1)+".txt",Toast.LENGTH_LONG).show();
    }
    catch (Exception e)
    {
        e.printStackTrace();
        Toast.makeText(sm2.this, "有错误", Toast.LENGTH_LONG).show();
    }
    break;
}
case R.id.sm2_button_yanqian:
        {
        //验签
            byte[] IDA = ID.getText().toString().getBytes();
            //确定公钥点PA
            sm23.javas.SM2.param pa = new sm23.javas.SM2.param();
            org.bouncycastle.math.ec.ECPoint PA;//定义PA需要,pa_x,pa_y,ecc_curve
            BigInteger pax = new BigInteger(pa_x.getText().toString(),16);
            BigInteger pay = new BigInteger(pa_y.getText().toString(),16);
            ECFieldElement x,y;
            x = new ECFieldElement.Fp(pa.sm2_p,pax);
            y = new ECFieldElement.Fp(pa.sm2_p,pay);
            PA = new ECPoint.Fp(pa.ecc_curve,x,y);//再说……

        break;
        }
default:
        break;
        }
    }
}
