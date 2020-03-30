package com.example.reminder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogReminder extends AppCompatDialogFragment {
    private EditText myText;
    private Button myButtonCreate;
    private Button myButtonCancel;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder bulider= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view =inflater.inflate(R.layout.dialog,null);
        bulider.setView(view).setTitle("Create Reminder");

        myText=view.findViewById(R.id.edittext_dialog);
        myButtonCancel=view.findViewById(R.id.cancel_dialog);
        myButtonCreate=view.findViewById(R.id.button_action_dialog);
        return bulider.create();

    }
}
