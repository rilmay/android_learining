package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class CustomDialogPhones extends DialogFragment {

    private Removable removable;

    public CustomDialogPhones(Removable removable) {
        this.removable = removable;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String phone = getArguments().getString("phone");
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        return builder
                .setTitle("Диалоговое окно")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Вы хотите удалить " + phone + "?")
                .setPositiveButton("OK",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removable.remove(phone);
                    }
                })
                .setNegativeButton("Отмена", null)
                .create();
    }
}