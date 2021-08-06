package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
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
import android.widget.FilterQueryProvider;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.VideoView;

import com.example.myapplication.entity.RecycledAdapter;
import com.example.myapplication.entity.State;
import com.example.myapplication.entity.StateAdapter;
import com.example.myapplication.entity.User;
import com.example.myapplication.entity.UserAge;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
        customDialogLayout(savedInstanceState);
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

    VideoView videoPlayer;

    protected void videoPlayerLayout(Bundle savedInstanceState) {
        setContentView(R.layout.videoplayer);

        videoPlayer =  (VideoView)findViewById(R.id.videoPlayer);
        if(false) {
            Uri myVideoUri= Uri.parse( "android.resource://" + getPackageName() + "/" + R.raw.xxxtentacion);
            videoPlayer.setVideoURI(myVideoUri);
        } else {
            videoPlayer.setVideoPath("https://r3---sn-4g5ednse.googlevideo.com/videoplayback?" +
                    "expire=1626804604&ei=HL32YMPqJYLiW766p_AJ&ip=95.104.111.204&id=o-ADk2BcA1w-" +
                    "u_ByGQxdOA5PnfasOsAR5fMvAemSdZ3Si-&itag=18&source=youtube&requiressl=yes&vpr" +
                    "v=1&mime=video%2Fmp4&ns=sFDpoYcB4a_Rk9b-6Cnsc0MG&gir=yes&clen=55046594&" +
                    "ratebypass=yes&dur=981.739&lmt=1626537260368221&fexp=24001373," +
                    "24007246&c=WEB&txp=5530434&n=E3HcKf0FA2_nyEV&sparams" +
                    "=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cvprv%2Cmime%2Cns%2Cgir%" +
                    "2Cclen%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRAIgIYy9d25GwfqSWwSpTjuYp6dtFa" +
                    "CiuqsdeqvwFBZfjW0CIH4FoKfS1a52Q2GS1-axTTFPyTmO_mUzY0cxAc4E-y01&rm=sn-h5bup" +
                    "jvh-c0ne7s,sn-nv4s67s&req_id=977ff412d184a3ee&redirect_counter=2&cms_redi" +
                    "rect=yes&ipbypass=yes&mh=b2&mip=46.53.251.230&mm=29&mn=sn-4g5ednse&ms=rdu" +
                    "&mt=1626782682&mv=m&mvi=3&pl=24&lsparams=ipbypass,mh,mip,mm,mn,ms,mv,mvi," +
                    "pl&lsig=AG3C_xAwRgIhAPmW28xh4ez5kfXAImY6ECdxtGN_qbFOcCoMCqKUoggFAiEAgJS3M" +
                    "Rdbyv-wtqqljfSRq65rFDUyJRmbPUtiiPW9h-Y%3D");
        }

        MediaController mediaController = new MediaController(this);
        videoPlayer.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoPlayer);
    }

    public void play(View view){
        videoPlayer.start();
    }
    public void pause(View view){
        videoPlayer.pause();
    }
    public void stop(View view){
        videoPlayer.stopPlayback();
        videoPlayer.resume();
    }

    MediaPlayer mPlayer;
    Button playButton, pauseButton, stopButton;
    protected void audioPlayerLayout(Bundle savedInstanceState) {
        setContentView(R.layout.audioplayer);

        mPlayer= MediaPlayer.create(this, R.raw.korzh);
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopPlay();
            }
        });
        playButton = (Button) findViewById(R.id.playButton);
        pauseButton = (Button) findViewById(R.id.pauseButton);
        stopButton = (Button) findViewById(R.id.stopButton);

        pauseButton.setEnabled(false);
        stopButton.setEnabled(false);



        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curValue = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        SeekBar volumeControl = (SeekBar) findViewById(R.id.volumeControl);
        volumeControl.setMax(maxVolume);
        volumeControl.setProgress(curValue);
        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    private void stopPlay(){
        mPlayer.stop();
        pauseButton.setEnabled(false);
        stopButton.setEnabled(false);
        try {
            mPlayer.prepare();
            mPlayer.seekTo(0);
            playButton.setEnabled(true);
        }
        catch (Throwable t) {
            Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void playAudio(View view){

        mPlayer.start();
        playButton.setEnabled(false);
        pauseButton.setEnabled(true);
        stopButton.setEnabled(true);
    }
    public void pauseAudio(View view){

        mPlayer.pause();
        playButton.setEnabled(true);
        pauseButton.setEnabled(false);
        stopButton.setEnabled(true);
    }
    public void stopAudio(View view){
        stopPlay();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer != null && mPlayer.isPlaying()) {
            stopPlay();
        }
    }

    String name ="undefined";


    final static String nameVariableKey = "NAME_VARIABLE";
    final static String textViewTexKey = "TEXTVIEW_TEXT";

    // сохранение состояния
    @Override
    protected void onSaveInstanceState(Bundle outState) {


        super.onSaveInstanceState(outState);
    }

    public void onSaveInstanceRotation(Bundle outState) {

        outState.putString(nameVariableKey, name);
        TextView nameView = (TextView) findViewById(R.id.nameView);
        outState.putString(textViewTexKey, nameView.getText().toString());
    }

    // получение ранее сохраненного состояния
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    protected void onRestoreInstanceStateRotation(Bundle savedInstanceState) {
        name = savedInstanceState.getString(nameVariableKey);
        String textViewText= savedInstanceState.getString(textViewTexKey);
        TextView nameView = (TextView) findViewById(R.id.nameView);
        nameView.setText(textViewText);
    }

    protected void saveTestingLayout(Bundle savedInstanceState) {
        setContentView(R.layout.save_state_testing);
        settings = getSharedPreferences(PREFS_FILE, MODE_PRIVATE);
    }

    public void saveName(View view) {
        saveNameSP(view);
    }

    public void saveNameRotation(View view) {
        // получаем введенное имя
        EditText nameBox = (EditText) findViewById(R.id.nameBox);
        name = nameBox.getText().toString();
    }

    public void getName(View view) {
        getNameSP(view);
    }

    public void getNameRotation(View view) {
        // получаем сохраненное имя
        TextView nameView = (TextView) findViewById(R.id.nameView);
        nameView.setText(name);
    }


    private static final String PREFS_FILE = "Account";
    private static final String PREF_NAME = "Name";
    SharedPreferences settings;


    public void saveNameSP(View view) {
        // получаем введенное имя
        EditText nameBox = (EditText) findViewById(R.id.nameBox);
        String name = nameBox.getText().toString();
        // сохраняем его в настройках
        SharedPreferences.Editor prefEditor = settings.edit();
        prefEditor.putString(PREF_NAME, name);
        prefEditor.apply();
    }

    public void getNameSP(View view) {
        // получаем сохраненное имя
        TextView nameView = (TextView) findViewById(R.id.nameView);
        String name = settings.getString(PREF_NAME,"не определено");
        nameView.setText(name);
    }


    SharedPreferences.Editor prefEditor;
    @Override
    protected void onPause(){
        super.onPause();
    }

    public void onPausePrefEditor(){
        EditText nameBox = (EditText) findViewById(R.id.nameBox);
        String name = nameBox.getText().toString();
        // сохраняем в настройках
        prefEditor = settings.edit();
        prefEditor.putString(PREF_NAME, name);
        prefEditor.apply();

    }

    public void settingsLayout(Bundle bundle) {
        setContentView(R.layout.button_to_settings);
        settingsText = (TextView) findViewById(R.id.settingsText);
    }

    TextView settingsText;
    boolean enabled;
    String login;

    @Override
    public void onResume() {
        super.onResume();
    }

    public void sharedPrefsResume() {

        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        enabled = prefs.getBoolean("enabled", false);
        login = prefs.getString("login", "не установлено");
        settingsText.setText(login);
        if(enabled)
            settingsText.setVisibility(View.VISIBLE);
        else
            settingsText.setVisibility(View.INVISIBLE);
    }

    public void setPrefs(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }


    private final static String FILE_NAME = "content.txt";

    protected void filesystemLayout(Bundle savedInstanceState) {
        setContentView(R.layout.filesystem_saving);
    }
    // сохранение файла
    public void saveText(View view){

        FileOutputStream fos = null;
        try {
            EditText textBox = (EditText) findViewById(R.id.editor);
            String text = textBox.getText().toString();

            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(text.getBytes());
            Toast.makeText(this, "Файл сохранен", Toast.LENGTH_SHORT).show();
        }
        catch(IOException ex) {

            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally{
            try{
                if(fos!=null)
                    fos.close();
            }
            catch(IOException ex){

                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    private File getExternalPath() {
        return new File(getExternalFilesDir(null), FILE_NAME);
    }

    public void saveTextExternal(View view){

        FileOutputStream fos = null;
        try {

            EditText textBox = (EditText) findViewById(R.id.editor);
            String text = textBox.getText().toString();

            fos = new FileOutputStream(getExternalPath());
            fos.write(text.getBytes());
            Toast.makeText(this, "Файл сохранен", Toast.LENGTH_SHORT).show();
        }
        catch(IOException ex) {

            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally{
            try{
                if(fos!=null)
                    fos.close();
            }
            catch(IOException ex){

                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    // открытие файла
    public void openText(View view){

        FileInputStream fin = null;
        TextView textView = (TextView) findViewById(R.id.text);
        try {
            fin = openFileInput(FILE_NAME);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String (bytes);
            textView.setText(text);
        }
        catch(IOException ex) {

            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally{

            try{
                if(fin!=null)
                    fin.close();
            }
            catch(IOException ex){

                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    // открытие файла
    public void openTextExternal(View view){

        FileInputStream fin = null;
        TextView textView = (TextView) findViewById(R.id.text);
        File file = getExternalPath();
        // если файл не существует, выход из метода
        if(!file.exists()) return;
        try {
            fin =  new FileInputStream(file);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String (bytes);
            textView.setText(text);
        }
        catch(IOException ex) {

            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally{

            try{
                if(fin!=null)
                    fin.close();
            }
            catch(IOException ex){

                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void sqlIntroductionLayout(Bundle bundle) {
        setContentView(R.layout.sql_introduction);
    }


    public void onClickSqlIntro(View view){
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS users (name TEXT, age INTEGER)");
        db.execSQL("INSERT INTO users VALUES ('Tom Smith', 23);");
        db.execSQL("INSERT INTO users VALUES ('John Dow', 31);");

        Cursor query = db.rawQuery("SELECT * FROM users;", null);
        TextView textView = (TextView) findViewById(R.id.textView);
        while(query.moveToNext()){
            String name = query.getString(0);
            int age = query.getInt(1);
            textView.append("Name: " + name + " Age: " + age + "\n");
        }
        query.close();
        db.close();
    }

    public void startDatabaseActivity(Bundle bundle) {
        Intent intent = new Intent(this, DatabaseActivity.class);
        startActivity(intent);
    }



    ListView userList;
    EditText userFilter;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;
    public void toUserActivityLayout(Bundle bundle) {
        setContentView(R.layout.to_user_activity);
        userList = (ListView) findViewById(R.id.list);
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        databaseHelper = new DatabaseHelper(getApplicationContext());
        // создаем базу данных
        databaseHelper.create_db();
    }

    public void userFinderLayout(Bundle bundle) {
        setContentView(R.layout.finder_layout);
        userList = (ListView)findViewById(R.id.userList);
        userFilter = (EditText)findViewById(R.id.userFilter);
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        databaseHelper = new DatabaseHelper(getApplicationContext());
        // создаем базу данных
        databaseHelper.create_db();
    }


    public void userFinderOnResume() {
        try {
            db = databaseHelper.open();
            userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE, null);
            String[] headers = new String[]{DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_YEAR};
            userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                    userCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);

            // если в текстовом поле есть текст, выполняем фильтрацию
            // данная проверка нужна при переходе от одной ориентации экрана к другой
            if(!userFilter.getText().toString().isEmpty())
                userAdapter.getFilter().filter(userFilter.getText().toString());

            // установка слушателя изменения текста
            userFilter.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) { }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                // при изменении текста выполняем фильтрацию
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    userAdapter.getFilter().filter(s.toString());
                }
            });

            // устанавливаем провайдер фильтрации
            userAdapter.setFilterQueryProvider(new FilterQueryProvider() {
                @Override
                public Cursor runQuery(CharSequence constraint) {

                    if (constraint == null || constraint.length() == 0) {

                        return db.rawQuery("select * from " + DatabaseHelper.TABLE, null);
                    }
                    else {
                        return db.rawQuery("select * from " + DatabaseHelper.TABLE + " where " +
                                DatabaseHelper.COLUMN_NAME + " like ?", new String[]{"%" + constraint.toString() + "%"});
                    }
                }
            });

            userList.setAdapter(userAdapter);
        }
        catch (SQLException ex){}
    }
    public void userFinderOnDestroy(){
        // Закрываем подключение и курсор
        db.close();
        userCursor.close();
    }

    private void userActivityOnResume() {
        // открываем подключение
        db = databaseHelper.open();
        //получаем данные из бд в виде курсора
        userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE, null);
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[]{DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_YEAR};
        // создаем адаптер, передаем в него курсор
        userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                userCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
        userList.setAdapter(userAdapter);
    }
    private void userActivityOnDestroy() {

        // Закрываем подключение и курсор
        db.close();
        userCursor.close();
    }

    // по нажатию на кнопку запускаем UserActivity для добавления данных
    public void add(View view) {
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }

    public void pageScrollingLayout(Bundle bundle) {
        setContentView(R.layout.pagescrolling_layout);

        ViewPager2 pager=(ViewPager2)findViewById(R.id.pager);
        FragmentStateAdapter pageAdapter = new PageScrollingAdapter(this);
        pager.setAdapter(pageAdapter);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        TabLayoutMediator tabLayoutMediator= new TabLayoutMediator(tabLayout, pager, new TabLayoutMediator.TabConfigurationStrategy(){

            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText("Страница " + (position + 1));
            }
        });
        tabLayoutMediator.attach();
    }

    public void mediaServiceLayout(Bundle bundle) {
        setContentView(R.layout.mediaservice_startstop);
    }

    public void mediaServiceClick(View view) {

        MediaPlayer ambientMediaPlayer=MediaPlayer.create(this, R.raw.korzh);
        ambientMediaPlayer.setLooping(true);
        ambientMediaPlayer.start();

        Intent i=new Intent(this, MediaService.class);
        if (view.getId()==R.id.start) {
            startService(i);
        }
        else {
            stopService(i);
        }
    }


    TextView currentDateTime;
    Calendar dateAndTime=Calendar.getInstance();

    public void datePickerDialogLayout(Bundle savedInstance) {
        setContentView(R.layout.datepicker_dialog);
        currentDateTime=(TextView)findViewById(R.id.currentDateTime);
        setInitialDateTime();
    }

    // отображаем диалоговое окно для выбора даты
    public void setDate(View v) {
        new DatePickerDialog(MainActivity.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // отображаем диалоговое окно для выбора времени
    public void setTime(View v) {
        new TimePickerDialog(MainActivity.this, t,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }
    // установка начальных даты и времени
    private void setInitialDateTime() {

        currentDateTime.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_SHOW_TIME));
    }

    // установка обработчика выбора времени
    TimePickerDialog.OnTimeSetListener t=new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            setInitialDateTime();
        }
    };

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };


    public void customDialogLayout(Bundle bundle) {
        setContentView(R.layout.show_custom_dialog);
    }


    public void showCustomDialog(View v) {
        CustomDialogFragment dialog = new CustomDialogFragment();
        dialog.show(getSupportFragmentManager(), "custom");
    }
}