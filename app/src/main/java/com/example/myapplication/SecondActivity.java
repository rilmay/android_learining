package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.entity.User;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_second);

        TextView textView = new TextView(this);
        textView.setTextSize(26);
        textView.setPadding(16, 16, 16, 16);

        Bundle arguments = getIntent().getExtras();

        User user;
        if(arguments!=null){
            user = (User) arguments.getSerializable(User.class.getSimpleName());

            textView.setText("Name: " + user.getName() + "\nCompany: " + user.getCompany() +
                    "\nAge: " + String.valueOf(user.getAge()));
        }
        setContentView(textView);
    }
}