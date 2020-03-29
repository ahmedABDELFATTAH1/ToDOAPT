package com.example.reminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class RemindersActivity extends AppCompatActivity {

    private RemindersDbAdapter              dbAdapter;
    private ListView                        remindersList;
    private RemindersSimpleCursorAdapter    listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbAdapter = new RemindersDbAdapter(this);
        Cursor cursor = dbAdapter.fetchAllReminders();
        //data base columns names
        String[] from = {RemindersDbAdapter.COL_ID,
                         RemindersDbAdapter.COL_CONTENT ,
                         RemindersDbAdapter.COL_IMPORTANT};
        //reminder view items ids
        int[] to = { R.id.reminder_item_id,
                     R.id.reminder_item_text,
                     R.id.reminder_item_isImportant};

        listAdapter = new RemindersSimpleCursorAdapter(this,R.layout.reminder_item,cursor,from,to,0);
        //if it didnt work use this
        //listAdapter = new RemindersSimpleCursorAdapter(this,R.layout.reminder_item,null,null,null,0);

        remindersList = findViewById(R.id.Reminders_List);
        remindersList.setAdapter(listAdapter);

        

        registerForContextMenu(remindersList);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.reminder_dialog_box,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reminder_dialog_box_menu_new_reminder:
                //new reminder
                Toast.makeText(this, "implement new reminder", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.reminder_click_options_menu_exit:
                this.finish();
                System.exit(0);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.reminder_click_options,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info  = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Reminder reminder = (Reminder)listAdapter.getItem(info.position);
        switch(item.getItemId()) {
            case R.id.reminder_click_options_menu_edit_reminder:
                Toast.makeText(this, "implement editt reminder", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.reminder_click_options_menu_delete_reminder:
                Toast.makeText(this, "implement delete reminder", Toast.LENGTH_SHORT).show();
                return true;
        }

        return super.onContextItemSelected(item);
    }
}
