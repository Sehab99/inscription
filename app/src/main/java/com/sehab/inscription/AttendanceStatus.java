package com.sehab.inscription;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;


public class AttendanceStatus extends AppCompatActivity {
    private Button back,present,attend_code;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private TextView tv1,tv2,tv3;


    String pc,ct,per;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
         DecimalFormat form = new DecimalFormat("0.###");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_status);
        pc = getIntent().getStringExtra("classes attended");
        ct= getIntent().getStringExtra("classes taken");
        tv1=findViewById(R.id.classtaken);
        tv1.setText(ct);
        tv2=findViewById(R.id.presentclass);
        tv2.setText(pc);
        double s1 = Double.parseDouble(pc);
        double s2 = Double.parseDouble(ct);
        double p=s1/s2*100;
        tv3=findViewById(R.id.percentage);
        //per = Double.toString(p);
        tv3.setText(form.format(p)+"%");
        //tv3.setText(String.valueOf(p));
        //tv3=setText(pc);

    }

}