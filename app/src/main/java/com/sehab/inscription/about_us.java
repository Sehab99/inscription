package com.sehab.inscription;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
public class about_us extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        setupHyperlink();
    }

    private void setupHyperlink() {
        TextView linkTextView1 = findViewById(R.id.textView3);
        linkTextView1.setMovementMethod(LinkMovementMethod.getInstance());
        TextView linkTextView2 = findViewById(R.id.textView4);
        linkTextView2.setMovementMethod(LinkMovementMethod.getInstance());
        TextView linkTextView3 = findViewById(R.id.textView5);
        linkTextView3.setMovementMethod(LinkMovementMethod.getInstance());
        TextView linkTextView4 = findViewById(R.id.textView6);
        linkTextView4.setMovementMethod(LinkMovementMethod.getInstance());



    }
}