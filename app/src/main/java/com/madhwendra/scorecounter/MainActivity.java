package com.madhwendra.scorecounter;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    int teamAScore = 0;
    int teamBScore = 0;
    TextView teamBValue;
    TextView teamAValue;
    private DatabaseReference databasereference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        teamBValue = findViewById(R.id.teamScoreB);
        teamAValue = findViewById(R.id.teamScoreA);
        databasereference = FirebaseDatabase.getInstance().getReference();


    }

    private void checkA() {
        String tvValue = teamAValue.getText().toString();

        if (!tvValue.equals("")) {
            int num1 = Integer.parseInt(tvValue);
            if (num1 >= 100) {
                showDialogA();
            }
        }

    }

    private void showDialogA() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setMessage("Team A Won this Match");
        builder.setTitle("Winner");
        builder.setCancelable(false);

        builder.setPositiveButton("Ok", (dialog, which) -> uploadData());

        AlertDialog dialog = builder.create();
        dialog.show();
        ;
    }

    public void TwentypointteamA(View view) {
        teamAScore = teamAScore + 20;
        display(teamAScore);
        checkA();
    }

    private void display(int teamAScore) {
        teamAValue.setText("" + teamAScore);
    }

    public void thirtypointteamA(View view) {
        teamAScore = teamAScore + 30;
        display(teamAScore);
        checkA();
    }

    public void fiftypointteamA(View view) {
        teamAScore = teamAScore + 50;
        display(teamAScore);
        checkA();
    }

    public void TwentypointteamB(View view) {
        teamBScore = teamBScore + 20;
        displayB(teamBScore);
        checkB();
    }

    private void checkB() {
        String tvValue = teamBValue.getText().toString();

        if (!tvValue.equals("")) {
            int num1 = Integer.parseInt(tvValue);
            if (num1 >= 100) {
                showDialogB();
            }
        }
    }

    private void showDialogB() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setMessage("Team B Won this Match");
        builder.setTitle("Winner");
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", (dialog, which) -> uploadData());

        AlertDialog dialog = builder.create();
        dialog.show();
        ;

    }

    private void uploadData() {
        String uniqueKey = databasereference.child("Score").push().getKey();
        String scoreA = teamAValue.getText().toString();
        String scoreB = teamBValue.getText().toString();

        HashMap data = new HashMap();
        data.put("Team A Score", scoreA);
        data.put("Team B Score", scoreB);


        databasereference.child("Score").child(uniqueKey).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                resetScore();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(MainActivity.this, "Failed to Upload data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void resetScore() {
        teamAScore = 0;
        teamBScore = 0;
        display(teamAScore);
        displayB(teamBScore);
    }

    private void displayB(int teamBScore) {
        teamBValue.setText("" + teamBScore);
    }

    public void thirtypointteamB(View view) {
        teamBScore = teamBScore + 30;
        displayB(teamBScore);
        checkB();
    }

    public void fiftypointteamB(View view) {
        teamBScore = teamBScore + 50;
        displayB(teamBScore);
        checkB();
    }

    public void reset(View view) {
        teamAScore = 0;
        teamBScore = 0;
        display(teamAScore);
        displayB(teamBScore);
    }
}