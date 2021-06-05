package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int clicks = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buttonTestingLayout(savedInstanceState);
    }
    // Метод обработки нажатия на кнопку
    public void sendMessage(View view) {
        // действия, совершаемые после нажатия на кнопку
        // Создаем объект Intent для вызова новой Activity
        Intent intent = new Intent(this, MessageActivity.class);
        // Получаем текстовое поле в текущей Activity
        EditText editText = (EditText) findViewById(R.id.editText);
        // Получае текст данного текстового поля
        String message = editText.getText().toString();
        // Добавляем с помощью свойства putExtra объект - первый параметр - ключ,
        // второй параметр - значение этого объекта
        intent.putExtra("message", message);
        // запуск activity
        startActivity(intent);
    }

    public void sendMessageButtonTesting(View view){
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(message);
    }

    public void sendToLayoutFromJava(View view) {
        Intent intent = new Intent(this, UiTesting.class);
        startActivity(intent);
    }

    public void buttonTestingLayout(Bundle savedInstanceState) {
        setContentView(R.layout.buttom_testing);
    }

    public void textViewLayout(Bundle savedInstanceState) {
        setContentView(R.layout.edittext_testing);


        EditText editText = (EditText) findViewById(R.id.editText);

        editText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                TextView textView = (TextView) findViewById(R.id.textView);
                textView.setText(s);
            }
        });
    }

    public void programManagedLayout(Bundle savedInstanceState) {
        TextView textView = new TextView(this);
        textView.setText("Hello Android");
        textView.setTextSize(26);

        ConstraintLayout constraintLayout = new ConstraintLayout(this);
        // устанавливаем параметры размеров и расположение элемента
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams
                (ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        // эквивалент app:layout_constraintLeft_toLeftOf="parent"
        layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
        // эквивалент app:layout_constraintTop_toTopOf="parent"
        layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.setMargins(40,40,40,40);
        // устанавливаем параметры для textView
        textView.setLayoutParams(layoutParams);
        textView.setPadding(0,0,0,70);
        textView.setBackgroundColor(Color.BLUE);
        // добавляем TextView в ConstraintLayout
        constraintLayout.addView(textView);
        constraintLayout.setPadding(49,49,49,49);
        setContentView(constraintLayout);
    }

    public void oldCreate1(Bundle savedInstanceState) {

        setContentView(R.layout.outer_layout);

        View plusButtonView = findViewById(R.id.plus_button);
        View minusButtonView = findViewById(R.id.minus_button);
        TextView clicksText = findViewById(R.id.clicksText);

        Button plusButton = plusButtonView.findViewById(R.id.clickButton);
        Button minusButton = minusButtonView.findViewById(R.id.clickButton);

        plusButton.setText("+");
        minusButton.setText("-");

        plusButton.setOnClickListener(v -> {
            clicks++;
            clicksText.setText(clicks + " Clicks");
        });
        minusButton.setOnClickListener(v -> {
            clicks--;
            clicksText.setText(clicks + " Clicks");
        });
    }
}