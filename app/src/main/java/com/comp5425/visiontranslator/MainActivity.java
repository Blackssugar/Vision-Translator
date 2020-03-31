package com.comp5425.visiontranslator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import comp5425.sydney.edu.au.visiontranslator.R;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    TranslationItemDB db;
    TranslationItemDao translationItemDao;
    private MainListAdapter listAdapter;
    private ArrayList<TranslationItem> translations;// = new ArrayList<TranslationItem>();
    FloatingActionButton fab;

    public final int CAMERA_REQUEST_CODE = 666;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        db = TranslationItemDB.getDatabase(getApplicationContext());
        translationItemDao = db.translationItemDao();
        readItemsFromDatabase();
        listView = (ListView) findViewById(R.id.word_list);
        listAdapter = new MainListAdapter(this,translations);
        listView.setAdapter(listAdapter);
        setupListViewListener();
        listView.addFooterView(new View(getApplicationContext()));
    }

    public void fabOnClick(View view){
        Intent intent = new Intent(MainActivity.this, ClassifierActivity.class);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==CAMERA_REQUEST_CODE) {
            readItemsFromDatabase();
            listAdapter = new MainListAdapter(getApplicationContext(),translations);
            listView.setAdapter(listAdapter);
            setupListViewListener();
        }
    }

    private void setupListViewListener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long rowId)
            {
                TranslationItem item = listAdapter.getItem(position);
                String display = item.getRecogntion() + "\n";
                String[] tokens = item.getTranslation().split(",");
                for(int i=0;i<tokens.length;i++){
                    display += "\n" + tokens[i];
                }
                //display += "\n" + String.join("\n",tokens);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Delete an item")
                        .setMessage("Do you want to delete this item?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                translations.remove(position); // Remove item from the ArrayList
                                listAdapter.notifyDataSetChanged(); // Notify listView adapter to update the list
                                saveItemsToDatabase();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User cancelled the dialog
                                // Nothing happens
                            }
                        });

                builder.create().show();
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TranslationItem item = listAdapter.getItem(position);
                String display = item.getRecogntion() + "\n";
                String[] tokens = item.getTranslation().split(",");
                for(int i=0;i<tokens.length;i++) {
                    display += "\n" + tokens[i];
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Detail")
                        .setMessage(display)
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User cancelled the dialog
                                // Nothing happens
                            }
                        });

                builder.create().show();
            }
        });
    }

    private void saveItemsToDatabase()
    {
        //Use asynchronous task to run query on the background to avoid locking UI
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                //delete all items and re-insert
                translationItemDao.deleteAll();
                for (TranslationItem todo : translations) {
                    Translation item = new Translation(todo.getTranslation(),todo.getRecogntion());
                    translationItemDao.insert(item);
                    Log.i("SQLite saved item", todo.getRecogntion());
                }
                return null;
            }
        }.execute();
    }

    private void readItemsFromDatabase()
    {
        //Use asynchronous task to run query on the background and wait for result
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    //read items from database
                    List<Translation> itemsFromDB = translationItemDao.listAll();
                    translations = new ArrayList<TranslationItem>();
                    if (itemsFromDB != null & itemsFromDB.size() > 0) {
                        for (Translation item : itemsFromDB) {
                            TranslationItem temp = new TranslationItem(item.getTranslationChinese(),item.getTranslationEnglish());
                            translations.add(temp);
                            Log.i("SQLite read item", "ID: " + item.getTranslationEnglish() + " Name: " + item.getTranslationChinese());
                        }
                    }
                    return null;
                }
            }.execute().get();
        }
        catch(Exception ex) {
            Log.e("readItemsFromDatabase", ex.getStackTrace().toString());
        }
    }
}
