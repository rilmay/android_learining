package com.example.myapplication;

import android.widget.ArrayAdapter;

public interface Removable {
    void remove(String value);


    class removableImpl implements Removable{
        private ArrayAdapter<String> adapter;

        public removableImpl(ArrayAdapter<String> adapter) {
            this.adapter = adapter;
        }

        @Override
        public void remove(String value) {
            adapter.remove(value);
        }
    }
}
