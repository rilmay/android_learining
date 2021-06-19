package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.entity.User;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        secondActivityWithResultLayout(savedInstanceState);
    }

    public void sendingUserLayout(Bundle bundle) {
        //setContentView(R.layout.activity_second);

        TextView textView = new TextView(this);
        textView.setTextSize(26);
        textView.setPadding(16, 16, 16, 16);

        Bundle arguments = getIntent().getExtras();

        User user;
        if(arguments!=null){
            user = arguments.getParcelable(User.class.getSimpleName());

            textView.setText("Name: " + user.getName() + "\nCompany: " + user.getCompany() +
                    "\nAge: " + String.valueOf(user.getAge()));
        }
        setContentView(textView);
    }

    public void onButton1ClickResult(View view) {
        sendMessage("Доступ разрешен");
    }

    public void onButton2ClickResult(View view) {
        sendMessage("Доступ запрещен");
    }

    public void onButton3ClickResult(View view) {
        sendMessage("Недопустимый возраст");
    }

    public void onCancelClickResult(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    private void sendMessage(String message){
        Intent data = new Intent();
        data.putExtra(MainActivity.ACCESS_MESSAGE, message);
        setResult(RESULT_OK, data);
        finish();
    }

    public void secondActivityWithResultLayout(Bundle bundle) {
        setContentView(R.layout.second_activity_with_result);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            TextView ageView = (TextView) findViewById(R.id.ageView23);
            Log.d(LoggedActivity.TAG, String.valueOf(extras));
            Log.d(LoggedActivity.TAG, extras.getString(MainActivity.AGE_KEY));
            Log.d(LoggedActivity.TAG, extras.describeContents() + "");
            String age = extras.getString(MainActivity.AGE_KEY);
            String message = "Age " + age;
            ageView.setText(message);
        }
    }
}