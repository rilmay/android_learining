package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.widget.TextView;

public class UiTesting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // создание TextView
        TextView textView = new TextView(this);
        // установка текста в TextView
        textView.setText("Hello Android From Java!");
        // установка высоты текста
        textView.setTextSize(22);

        ConstraintLayout constraintLayout = new ConstraintLayout(this);
        // устанавливаем параметры размеров и расположение элемента
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams
                (ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        // выравнивание по левому краю ConstraintLayout
        layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
        // выравнивание по верхней границе ConstraintLayout
        layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        // устанавливаем параметры для textView
        textView.setLayoutParams(layoutParams);
        // добавляем TextView в ConstraintLayout
        constraintLayout.addView(textView);
        // в качестве корневого
        setContentView(constraintLayout);
    }
}