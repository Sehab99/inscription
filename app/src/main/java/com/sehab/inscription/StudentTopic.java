package com.sehab.inscription;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class StudentTopic extends AppCompatActivity {
    private Button back,present,attend_code;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText otp_enter;

    int pos, tc=90, pc=70;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_topic);
        attend_code=(Button) findViewById(R.id.otp_button);
        attend_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp_pop();
            }
        });
    }
    public void otp_pop()
    {
        dialogBuilder = new AlertDialog.Builder(this);
        final View popup_view= getLayoutInflater().inflate(R.layout.popup,null);
        otp_enter=(EditText) popup_view.findViewById(R.id.otp_num);
        back=(Button) popup_view.findViewById(R.id.pop_back);
        present=(Button) popup_view.findViewById(R.id.pop_present);
        dialogBuilder.setView(popup_view);
        dialog=dialogBuilder.create();
        dialog.show();
        present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}