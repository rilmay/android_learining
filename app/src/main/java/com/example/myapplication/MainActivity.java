package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.icu.util.BuddhistCalendar;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.myapplication.entity.RecycledAdapter;
import com.example.myapplication.entity.State;
import com.example.myapplication.entity.StateAdapter;
import com.example.myapplication.entity.User;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    List<String> countries = new ArrayList<>(Arrays.asList("Бразилия","Аргентина","Колумбия","Чили","Уругвай"));

    int clicks = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        httpTestingLayout(savedInstanceState);
    }

    public void menuTitleLayout(Bundle bundle) {
        setContentView(R.layout.menu_title_layout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        menu.add("еще один");
        menu.add(1        // Группа
                ,1        // id
                ,0        //порядок
                ,"Создать");  // заголовок
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        TextView headerView = (TextView) findViewById(R.id.selectedMenuItem);
        switch(id){
            case R.id.action_settings :
                headerView.setText("Настройки");
                return true;
            case R.id.open_settings:
                headerView.setText("Открыть");
                return true;
            case R.id.save_settings:
                headerView.setText("Сохранить");
                return true;
            default:
                headerView.setText(item.getTitle());
                return true;
        }
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

    public void buttonToLoggedActivity(Bundle bundle){
        setContentView(R.layout.button_to_acitivty_cycle);
    }

    public void sendToLoggedActivity(View view) {
        Intent intent = new Intent(this, LoggedActivity.class);
        startActivity(intent);
    }

    public void onRadioButtonClicked(View view) {
        // если переключатель отмечен
        boolean checked = ((RadioButton) view).isChecked();
        TextView selection = (TextView) findViewById(R.id.selection);
        // Получаем нажатый переключатель
        switch(view.getId()) {
            case R.id.java:
                if (checked){
                    selection.setText("Выбрана Java");
                }
                break;
            case R.id.kotlin:
                if (checked){
                    selection.setText("Выбран Kotlin");
                }
                break;
        }
    }

    ArrayList<State> states = new ArrayList();
    ListView countriesList;
    protected void adapterWithImagesLayout(Bundle savedInstanceState) {
        setContentView(R.layout.countries_list_with_images);
        // начальная инициализация списка
        setInitialData();
        // получаем элемент ListView
        countriesList = (ListView) findViewById(R.id.countriesList);
        // создаем адаптер
        StateAdapter stateAdapter = new StateAdapter(this, R.layout.adapter_list_item, states);
        // устанавливаем адаптер
        countriesList.setAdapter(stateAdapter);
        // слушатель выбора в списке
        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                // получаем выбранный пункт
                State selectedState = (State)parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), "Был выбран пункт " + selectedState.getName(),
                        Toast.LENGTH_SHORT).show();
            }
        };
        countriesList.setOnItemClickListener(itemListener);
    }
    private void setInitialData(){

        states.add(new State ("Бразилия", "Бразилиа", R.drawable.ic_launcher_foreground,0));
        states.add(new State ("Аргентина", "Буэнос-Айрес", R.drawable.ic_launcher_foreground,0));
        states.add(new State ("Колумбия", "Богота", R.drawable.ic_launcher_foreground,0));
        states.add(new State ("Уругвай", "Монтевидео", R.drawable.ic_launcher_foreground,0));
        states.add(new State ("Чили", "Сантьяго", R.drawable.ic_launcher_foreground,0));
    }

    public void arrayAdapterLayout(Bundle bundle) {
        setContentView(R.layout.array_adapter_testing);
        // получаем элемент TextView
        TextView selection = (TextView) findViewById(R.id.selection);

        // получаем элемент ListView
        ListView countriesList = (ListView) findViewById(R.id.countriesList);

        // получаем ресурс
        String[] countries = getResources().getStringArray(R.array.countries);

        // создаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, countries);

        // устанавливаем для списка адаптер
        countriesList.setAdapter(adapter);

        countriesList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                SparseBooleanArray selected=countriesList.getCheckedItemPositions();

                String selectedItems="";
                for(int i=0;i < countries.length;i++)
                {
                    if(selected.get(i))
                        selectedItems+=countries[i]+",";
                }
                // установка текста элемента TextView
                selection.setText("Выбрано: " + selectedItems);
            }
        });
    }

    ArrayList<String> usersAdapterEditing = new ArrayList<String>();
    ArrayList<String> selectedUsersAdapterEditing = new ArrayList<String>();
    ArrayAdapter<String> adapterAdapterEditing;
    ListView usersListAdapterEditing;

    public void arrayAdapterEditing(Bundle bundle) {
        setContentView(R.layout.adapter_list_editing);

        // добавляем начальные элементы
        Collections.addAll(usersAdapterEditing, "Tom", "Bob", "Sam", "Alice");
        // получаем элемент ListView
        usersListAdapterEditing = (ListView) findViewById(R.id.usersList);
        // создаем адаптер
        adapterAdapterEditing =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, usersAdapterEditing);
        // устанавливаем для списка адаптер
        usersListAdapterEditing.setAdapter(adapterAdapterEditing);

        // обработка установки и снятия отметки в списке
        usersListAdapterEditing.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                // получаем нажатый элемент
                String user = adapterAdapterEditing.getItem(position);
                if(usersListAdapterEditing.isItemChecked(position))
                    selectedUsersAdapterEditing.add(user);
                else
                    selectedUsersAdapterEditing.remove(user);
            }
        });
    }


    public void addAdapter(View view){

        EditText userName = (EditText) findViewById(R.id.userName);
        String user = userName.getText().toString();
        if(!user.isEmpty()){
            adapterAdapterEditing.add(user);
            userName.setText("");
            adapterAdapterEditing.notifyDataSetChanged();
        }
    }
    public void removeAdapter(View view){
        // получаем и удаляем выделенные элементы
        for(int i=0; i< selectedUsersAdapterEditing.size();i++){
            adapterAdapterEditing.remove(selectedUsersAdapterEditing.get(i));
        }
        // снимаем все ранее установленные отметки
        usersListAdapterEditing.clearChoices();
        // очищаем массив выбраных объектов
        selectedUsersAdapterEditing.clear();

        adapterAdapterEditing.notifyDataSetChanged();
    }


    public void imageLayout(Bundle bundle) {
        ConstraintLayout constraintLayout = new ConstraintLayout(this);
        ImageView imageView = new ImageView(this);
        Resources res = getResources();
        Drawable drawable = ResourcesCompat.getDrawable(res, R.drawable.rel, null);
        // применяем ресурс
        imageView.setImageDrawable(drawable);

        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams
                (ConstraintLayout.LayoutParams.WRAP_CONTENT , ConstraintLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        imageView.setLayoutParams(layoutParams);
        constraintLayout.addView(imageView);

        setContentView(constraintLayout);
    }

    public void imageLayoutFromAssets(Bundle bundle) {

        setContentView(R.layout.pictures_testing);

        ImageView imageView = (ImageView) findViewById(R.id.image) ;
        String filename = "rel.jpg";
        InputStream inputStream = null;
        try{
            inputStream = getApplicationContext().getAssets().open(filename);
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            imageView.setImageDrawable(drawable);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                if(inputStream!=null)
                    inputStream.close();
            }
            catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }

    public void dimensionsProgrammaticalLayout(Bundle bundle) {
        Resources resources = getResources();
        float textSize = resources.getDimension(R.dimen.text_size);
        int hMargin = (int)resources.getDimension(R.dimen.horizontal_margin);
        int vMargin = (int)resources.getDimension(R.dimen.vertical_margin);

        ConstraintLayout constraintLayout = new ConstraintLayout(this);

        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams
                (ConstraintLayout.LayoutParams.WRAP_CONTENT , ConstraintLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;

        TextView textView = new TextView(this);
        textView.setText("Hello Android");
        textView.setBackgroundColor(0XFFEAEAEA);
        // устанавливаем размер шрифт по ресурсу
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        // устанавливаем отступы пв соответствии с ресурсами
        layoutParams.setMargins(hMargin, vMargin, hMargin, vMargin);

        textView.setLayoutParams(layoutParams);
        constraintLayout.addView(textView);

        setContentView(constraintLayout);
    }

    public void colorLayout(Bundle bundle) {
        Resources resources = getResources();
        int textColor = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            textColor = resources.getColor(R.color.textViewFontColor,  null);
        }
        int backgroundColor = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            backgroundColor = resources.getColor(R.color.textViewBackColor,  null);
        }

        ConstraintLayout constraintLayout = new ConstraintLayout(this);

        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams
                (ConstraintLayout.LayoutParams.WRAP_CONTENT , ConstraintLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;

        TextView textView = new TextView(this);
        textView.setText("Hello Android");
        textView.setTextSize(32);

        // используем ресурсы color
        textView.setTextColor(textColor);
        textView.setBackgroundColor(backgroundColor);

        textView.setLayoutParams(layoutParams);
        constraintLayout.addView(textView);

        setContentView(constraintLayout);
    }

    public void resourceLayout(Bundle bundle) {
        setContentView(R.layout.resource_testing);
        TextView textView = (TextView) findViewById(R.id.insert_here);


        String userName = "Евгений";
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        String text = getString(R.string.welcome_message, userName, hour, minute);

        Resources res = getResources();
        String[] languages = res.getStringArray(R.array.languages);
        String allLangs = "";
        for (String lang: languages) {
            allLangs += lang + " ";
        }

        textView.setText(text + allLangs);
        textView.setTextSize(28);

    }

    public void radioButtonLayout(Bundle bundle) {
        setContentView(R.layout.radiobutton_testing);
    }

    public void datePickerLayout(Bundle bundle) {
        setContentView(R.layout.datepicker_testing);
        TextView dateTextView = (TextView)findViewById(R.id.dateTextView);
        DatePicker datePicker = (DatePicker)this.findViewById(R.id.datePicker);

        // Месяц начиная с нуля. Для отображения добавляем 1.
        datePicker.init(2020, 02, 01, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                // Отсчет месяцев начинается с нуля. Для отображения добавляем 1.
                dateTextView.setText("Дата: " + view.getDayOfMonth() + "/" +
                        (view.getMonth() + 1) + "/" + view.getYear());

                // альтернативная запись
                // dateTextView.setText("Дата: " + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
            }
        });
    }

    public void timePickerLayout(Bundle bundle) {
        setContentView(R.layout.timepicker_testing);
        TextView timeTextView = (TextView)findViewById(R.id.timeTextView);
        TimePicker timePicker = (TimePicker)this.findViewById(R.id.timePicker);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

                timeTextView.setText("Время: " + hourOfDay + ":" + minute);
                // или так
                // timeTextView.setText("Время: " + view.getHour() + ":" + view.getMinute());
            }
        });
    }

    public void seekBarLayout(Bundle bundle) {
        setContentView(R.layout.seekbar_testing);
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        TextView textView = (TextView) findViewById(R.id.seekBarValue);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                textView.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void onToggleClicked(View view) {

        // включена ли кнопка
        boolean on = ((ToggleButton) view).isChecked();
        if (on) {
            // действия если включена
            Toast.makeText(this, "Свет включен", Toast.LENGTH_LONG).show();
        } else {
            // действия, если выключена
            Toast.makeText(this, "Свет выключен!", Toast.LENGTH_LONG).show();
        }
    }

    public void toggleLayout(Bundle bundle) {
        setContentView(R.layout.toggle_testing);
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

    public void buttonToCalculatorLayout(Bundle savedInstanceState) {
        setContentView(R.layout.button_to_calculator);
    }

    public void sendToCalculatorLayout(View view) {
        Intent intent = new Intent(this, CalculatorActivity.class);
        startActivity(intent);
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

    public void toastLayout(Bundle savedInstanceState) {
        setContentView(R.layout.toast_and_snackbar_testing);
    }

    public void showToastMessage(View view) {

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout,
                (ViewGroup) findViewById(R.id.toast_layout));

        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText("Hello Android!");

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public void showSnackbar(View view) {
        Snackbar snackbar = Snackbar.make(view, "Hello Android", Snackbar.LENGTH_LONG);
        snackbar.setTextColor(0XFF81C784);
        snackbar.setBackgroundTint(0XFF555555);
        snackbar.setActionTextColor(0XFF0277BD);

        snackbar.setAction("Next...", new View.OnClickListener (){
            @Override
            public void onClick(View v) {
                showToastMessage(v);
            }
        });
        snackbar.show();
    }

    public void checkBoxLayout(Bundle bundle) {
        setContentView(R.layout.checkbox_testing);
    }

    public void onCheckboxClicked(View view) {
        // Получаем флажок
        CheckBox checkBox = (CheckBox) view;
        TextView selection = (TextView) findViewById(R.id.selection);
        // Получаем, отмечен ли данный флажок
        if(checkBox.isChecked()) {
            selection.setText("Включено");
            checkBox.setText("Выключить");
        }
        else {
            selection.setText("Выключено");
            checkBox.setText("Включить");
        }
    }

    public void buttonToSecondActivityLayout(Bundle bundle){
        setContentView(R.layout.button_to_second_activity);
    }

    public void sendToSecondActivity(View view) {
        EditText nameText = findViewById(R.id.name);
        EditText companyText = findViewById(R.id.company);
        EditText ageText = findViewById(R.id.age);

        String name = nameText.getText().toString();
        String company = companyText.getText().toString();
        int age = Integer.parseInt(ageText.getText().toString());

        User user = new User(name, company, age);

        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra(User.class.getSimpleName(), user);
        startActivity(intent);
    }

    static final String AGE_KEY = "AGE";
    static final String ACCESS_MESSAGE="ACCESS_MESSAGE";
    private static  final int REQUEST_ACCESS_TYPE=1;

    public void mainActivityWithResult(Bundle bundle) {
        setContentView(R.layout.main_acitivity_with_result);
    }

    public void onClickWithResult(View view) {
        // получаем введенный возраст
        EditText ageBox = (EditText) findViewById(R.id.age);
        String age = ageBox.getText().toString();

        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra(AGE_KEY, age);
        startActivityForResult(intent, REQUEST_ACCESS_TYPE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        TextView textView = (TextView) findViewById(R.id.textView);
        if(requestCode==REQUEST_ACCESS_TYPE){
            if(resultCode==RESULT_OK){
                String accessMessage = data.getStringExtra(ACCESS_MESSAGE);
                textView.setText(accessMessage);
            }
            else{
                textView.setText("Ошибка доступа");
            }
        }
        else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void toListActivityViewLayout(Bundle bundle) {
        setContentView(R.layout.to_list_view_activity);
    }

    public void toListViewActivity(View view) {
        Intent intent = new Intent(this, ListViewActivity.class);
        startActivity(intent);
    }

    public void spinnerLayout(Bundle bundle) {
        setContentView(R.layout.spinner_layout);
        TextView selection = (TextView) findViewById(R.id.selection);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countries);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinner.setAdapter(adapter);
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Получаем выбранный объект
                String item = (String)parent.getItemAtPosition(position);
                selection.setText(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);
    }

    String[] cities = new String[]{"New Yorke", "Moscow", "Warsaw"};

    public void autocompleteLayout(Bundle bundle) {
        setContentView(R.layout.autocoplete_testing);
        // Получаем ссылку на элемент AutoCompleteTextView в разметке
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autocomplete);
        // Создаем адаптер для автозаполнения элемента AutoCompleteTextView
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, cities);
        autoCompleteTextView.setAdapter(adapter);

        MultiAutoCompleteTextView autoCompleteMulti = (MultiAutoCompleteTextView) findViewById(R.id.autocomplete_multi);
        // Создаем адаптер для автозаполнения элемента AutoCompleteTextView
        autoCompleteMulti.setAdapter(adapter);
        // установка запятой в качестве разделителя
        autoCompleteMulti.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }

    public void gridLayout(Bundle bundle) {
        setContentView(R.layout.gridview_testing);

        // получаем элемент GridView
        GridView countriesList = (GridView) findViewById(R.id.gridview);
        // создаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries);
        countriesList.setAdapter(adapter);

        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"Вы выбрали "
                                + parent.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT).show();
            }
        };
        countriesList.setOnItemClickListener(itemListener);
    }



    public void recycledAdapterLayout(Bundle bundle) {
        setContentView(R.layout.recyclerview_adapter);
        // начальная инициализация списка
        setInitialData();
        TextView selectedState = (TextView) findViewById(R.id.selected_state);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        RecycledAdapter.OnStateClickListener listener = new RecycledAdapter.OnStateClickListener(){
            @Override
            public void onStateClick(State state, int position) {
                selectedState.setText(state.getCapital());
            }
        };
        // создаем адаптер
        RecycledAdapter adapter = new RecycledAdapter(this, states, listener);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);
    }

    public void fragmentContainerLayout(Bundle bundle) {
        setContentView(R.layout.fragment_container);
    }

    public void fragmentContainerProgrammableLayout(Bundle bundle) {
        setContentView(R.layout.fragment_container_programmable);
        if (bundle == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_view, ContentFragment.class, null)
                    .commit();
        }
    }

    public void toFragmentActivity(View view) {
        Intent intent = new Intent(this, FragmentActivity.class);
        startActivity(intent);
    }

    public void buttonToFragmentLayout(Bundle bundle) {
        setContentView(R.layout.button_to_fragment_activity);
    }

    public void WebViewLayout(Bundle bundle) {
        setContentView(R.layout.web_view_layout);

        WebView browser=(WebView)findViewById(R.id.webBrowser);
        browser.loadUrl("https://metanit.com");
    }

    public void programmableWebView(Bundle bundle) {
        WebView browser = new WebView(this);
        setContentView(browser);
        browser.loadData("<html><body><h2>Hello, Android!</h2></body></html>", "text/html", "UTF-8");
    }

    public void httpTestingLayout(Bundle bundle) {
        setContentView(R.layout.http_testing);

        TextView contentView = (TextView) findViewById(R.id.content);
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        Button btnFetch = (Button)findViewById(R.id.downloadBtn);
        btnFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentView.setText("Загрузка...");
                new Thread(new Runnable() {
                    public void run() {
                        try{
                            String content = getContent("https://stackoverflow.com/");
                            webView.post(new Runnable() {
                                public void run() {
                                    webView.loadDataWithBaseURL("https://stackoverflow.com/",content, "text/html", "UTF-8", "https://stackoverflow.com/");
                                    Toast.makeText(getApplicationContext(), "Данные загружены", Toast.LENGTH_SHORT).show();
                                }
                            });
                            contentView.post(new Runnable() {
                                public void run() {
                                    contentView.setText(content);
                                }
                            });
                        }
                        catch (IOException ex){
                            contentView.post(new Runnable() {
                                public void run() {
                                    contentView.setText("Ошибка: " + ex.getMessage());
                                    Toast.makeText(getApplicationContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
            }
        });
    }
    private String getContent(String path) throws IOException {
        BufferedReader reader=null;
        InputStream stream = null;
        HttpsURLConnection connection = null;
        try {
            URL url=new URL(path);
            connection =(HttpsURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);
            connection.connect();
            stream = connection.getInputStream();
            reader= new BufferedReader(new InputStreamReader(stream));
            StringBuilder buf=new StringBuilder();
            String line;
            while ((line=reader.readLine()) != null) {
                buf.append(line).append("\n");
            }
            return(buf.toString());
        }
        finally {
            if (reader != null) {
                reader.close();
            }
            if (stream != null) {
                stream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}