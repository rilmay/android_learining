package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ContentViewActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_READ_CONTACTS=1;
    private static boolean READ_CONTACTS_GRANTED =false;
    ArrayList<String> contacts = new ArrayList<String>();
    Button addBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_view);

        addBtn = (Button) findViewById(R.id.addBtn);
        // получаем разрешения
        int hasReadContactPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        // если устройство до API 23, устанавливаем разрешение
        if(hasReadContactPermission == PackageManager.PERMISSION_GRANTED){
            READ_CONTACTS_GRANTED = true;
        }
        else{
            // вызываем диалоговое окно для установки разрешений
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS}, REQUEST_CODE_READ_CONTACTS);
        }
        // если разрешение установлено, загружаем контакты
        if (READ_CONTACTS_GRANTED){
            loadContacts();
        }

        addBtn.setEnabled(READ_CONTACTS_GRANTED);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){

        if (requestCode == REQUEST_CODE_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                READ_CONTACTS_GRANTED = true;
            }
            addBtn.setEnabled(READ_CONTACTS_GRANTED);
        }
        if(READ_CONTACTS_GRANTED){
            loadContacts();
        }
        else{
            Toast.makeText(this, "Требуется установить разрешения", Toast.LENGTH_LONG).show();
        }
    }
    public void onAddContact(View v) {
        ContentValues contactValues = new ContentValues();
        EditText contactText = (EditText) findViewById(R.id.newContact);
        String newContact = contactText.getText().toString();
        contactText.setText("");
        contactValues.put(ContactsContract.RawContacts.ACCOUNT_NAME, newContact);
        contactValues.put(ContactsContract.RawContacts.ACCOUNT_TYPE, newContact);
        Uri newUri = getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, contactValues);
        long rawContactsId = ContentUris.parseId(newUri);
        contactValues.clear();
        contactValues.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactsId);
        contactValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        contactValues.put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, newContact);
        getContentResolver().insert(ContactsContract.Data.CONTENT_URI, contactValues);
        Toast.makeText(getApplicationContext(), newContact + " добавлен в список контактов", Toast.LENGTH_LONG).show();
        loadContacts();
    }
    private void loadContacts(){
        contacts.clear();
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if(cursor!=null){
            while (cursor.moveToNext()) {

                // получаем каждый контакт
                String contact = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
                // добавляем контакт в список
                contacts.add(contact);
            }
            cursor.close();
        }
        // создаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, contacts);
        // устанавливаем для списка адаптер
        ListView contactList = (ListView) findViewById(R.id.contactList);
        contactList.setAdapter(adapter);
    }
}