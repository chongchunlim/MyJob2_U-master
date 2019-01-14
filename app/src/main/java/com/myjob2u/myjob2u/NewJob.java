package com.myjob2u.myjob2u;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewJob extends AppCompatActivity {

    DatabaseReference databaseUser;
    boolean valid = false;
    EditText title,salary,desc;
    Spinner spinnerQ;
    Button btnPost;

    public NewJob()
    {

    }


    public static boolean isEmptyString(String text) {
        return (text == null || text.trim().equals("null") || text.trim()
                .length() <= 0);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_newjob);

        databaseUser = FirebaseDatabase.getInstance().getReference("jobdetails");


        title = findViewById(R.id.editTextTitle);
        salary = findViewById(R.id.editTextSalary);
        desc = findViewById(R.id.editTextDescription);
        spinnerQ = findViewById(R.id.spinnerQP);

        btnPost = findViewById(R.id.btnPostjob);

        title = findViewById(R.id.editTextTitle);
        (findViewById(R.id.editTextTitle)).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (isEmptyString(title.getText().toString())) {
                        title.setError("Please fill in the blank.");
                        valid = false;
                    } else {
                        valid = true;
                    }
                }
            }
        });

        salary = findViewById(R.id.editTextSalary);
        (findViewById(R.id.editTextSalary)).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (isEmptyString(salary.getText().toString())) {
                        salary.setError("Please fill in the blank.");
                        valid = false;
                    } else {
                        valid = true;
                    }
                }
            }
        });

        desc = findViewById(R.id.editTextDescription);
        (findViewById(R.id.editTextDescription)).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (isEmptyString(desc.getText().toString())) {
                        desc.setError("Please fill in the blank.");
                        valid = false;
                    } else {
                        valid = true;
                    }
                }
            }
        });


        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postNewJob();

                finish();
            }
        });


    }

    private boolean isEmptyText(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    public void postNewJob()
    {
        if(isEmptyText(title)||isEmptyText(salary)||isEmptyText(desc))
        {
            Toast.makeText(this,"please enter everything",Toast.LENGTH_LONG).show();
            return;
        }

        SharedPreferences loginSession = getApplicationContext().getSharedPreferences("loginSession", MODE_PRIVATE);


        String username = loginSession.getString("username","Anonymous");

        String id = databaseUser.push().getKey();

        PostNewJob newJob = new PostNewJob(title.getText().toString(),desc.getText().toString(),salary.getText().toString(),spinnerQ.getSelectedItem().toString(),username);

        databaseUser.child(id).setValue(newJob);

        Toast.makeText(this,"job successfully posted",Toast.LENGTH_LONG).show();
    }



}
