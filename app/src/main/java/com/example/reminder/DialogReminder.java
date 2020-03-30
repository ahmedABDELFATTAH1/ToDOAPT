package com.example.reminder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogReminder extends AppCompatDialogFragment {
    private EditText myText;
    private Button myButtonCreate;
    private Button myButtonCancel;
    private Switch simpleSwitch;
    private Boolean switchState;
    private RemindersDbAdapter reminderAdapter;
    private String typeOperation;
    private int reminderId;
    public DialogReminder(String type,Integer id)
    {
        typeOperation=type;
        reminderId=id;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder bulider= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view =inflater.inflate(R.layout.dialog,null);

        bulider.setView(view);
        bulider.setTitle(typeOperation);


        simpleSwitch = (Switch) view.findViewById(R.id.switch_dialog);

        myText=view.findViewById(R.id.edittext_dialog);
        myButtonCancel=view.findViewById(R.id.cancel_dialog);
        myButtonCreate=view.findViewById(R.id.button_action_dialog);
        reminderAdapter=new RemindersDbAdapter(getContext());
        myButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Toast.makeText(getContext(), "NOOOOOOOO", Toast.LENGTH_SHORT).show();
                getDialog().cancel();
            }
        });

        myButtonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                switchState = simpleSwitch.isChecked();
                String text=myText.getText().toString();
                reminderAdapter.createReminder(text,switchState);
                Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
                getDialog().cancel();
            }
        });
        return bulider.create();

    }
}
