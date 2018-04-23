package com.example.architpanwar.lockup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class passwordrecoverset extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    Spinner questionsSpinner;
    EditText answer;
    Button confirmButton;
    int questionNumber = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwordrecoverset);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        confirmButton = (Button) findViewById(R.id.submitres);
        questionsSpinner = (Spinner) findViewById(R.id.questionsSpinner);
        answer = (EditText) findViewById(R.id.answer);


        sharedPreferences = getSharedPreferences(Constants.MyPREFERENCES, MODE_PRIVATE);
        editor = sharedPreferences.edit();



        List<String> list = new ArrayList<>();
        list.add("Select your security question?");
        list.add("What is your pet name?");
        list.add("Who is your favorite teacher?");
        list.add("Who is your favorite actor?");
        list.add("Who is your favorite actress?");
        list.add("Who is your favorite cricketer?");
        list.add("Who is your favorite footballer?");

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        questionsSpinner.setAdapter(stringArrayAdapter);


        questionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                questionNumber = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionNumber != 0 && !answer.getText().toString().isEmpty()) {
                    editor.putBoolean(Constants.IS_PASSWORD_SET, true);
                    editor.commit();
                    editor.putString(Constants.ANSWER, answer.getText().toString());
                    editor.commit();
                    editor.putInt(Constants.QUESTION_NUMBER, questionNumber);
                    editor.commit();

                    Intent i = new Intent(passwordrecoverset.this, MainActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Please select a question and write an answer", Toast.LENGTH_SHORT).show();
                }

            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
