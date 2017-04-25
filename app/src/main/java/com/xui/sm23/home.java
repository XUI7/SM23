package com.xui.sm23;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class home extends AppCompatActivity implements View.OnClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        Button bu_sm3,bu_sm2;
        bu_sm3 = (Button) findViewById(R.id.home_button_sm3);
        bu_sm2 = (Button) findViewById(R.id.home_button_sm2);
        bu_sm3.setOnClickListener(this);
        bu_sm2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.home_button_sm3:
                Intent intent = new Intent(home.this,sm3.class);
                startActivity(intent);
                break;
            case R.id.home_button_sm2:
                Intent intent1 = new Intent(home.this,sm2.class);
                startActivity(intent1   );
                break;
            default:
                Toast.makeText(home.this," 该操作为指定命令",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
