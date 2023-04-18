package com.example.proiect;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class SelectedCoachForHavingATrainingSessionWith extends AppCompatActivity {

    //Trebuie sa luam mai intai datele din intent
    EditText coachNameEditText,currentTeamEditText,experienceEditText,trophiesWonEditText;
    TextView titleTextView;

    private DatePickerDialog datePickerDialog;

    private TimePickerDialog timePickerDialog;

    private Button dateButton, timeButton,backButton,confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_coach_for_having_atraining_session_with);
        
        titleTextView=findViewById(R.id.textViewActivityTrainingDrillsTitle);
        coachNameEditText=findViewById(R.id.editTextActivityTrainingDrillsConfirmFullName);
        currentTeamEditText=findViewById(R.id.editTextActivityTrainingDrillsConfirmAdress);
        experienceEditText=findViewById(R.id.editTextActivityTrainingDrillsConfirmPinCode);
        trophiesWonEditText=findViewById(R.id.editTextActivityTrainingDrillsConfirmContactNumber);
        dateButton=findViewById(R.id.buttonSelectedCoachForHavingATrainingSessionWithDate);
        timeButton=findViewById(R.id.buttonSelectedCoachForHavingATrainingSessionWithTime);
        confirmButton=findViewById(R.id.buttonConfirmTrainingDrills);
        backButton=findViewById(R.id.TrainingDrillsCartBack);
        //cele 4 EditText nu sunt editable, ele doar vor afisa detaliile de la previous activity (Select coach)
        coachNameEditText.setKeyListener(null);
        currentTeamEditText.setKeyListener(null);
        experienceEditText.setKeyListener(null);
        trophiesWonEditText.setKeyListener(null);

        //luam din activitatea precedenta cu ajutorul intentului caracteristicile antrenorului selectat de utilizator
        Intent intent=getIntent();
        String title=intent.getStringExtra("title");
        String coachName=intent.getStringExtra("coachName");
        String currentTeam=intent.getStringExtra("currentTeam");
        String experience=intent.getStringExtra("experience");
        String trophiesWon=intent.getStringExtra("trophiesWon");
        //setam continutul celor 4 EditText si a TextView-ului
        titleTextView.setText(title);
        coachNameEditText.setText(coachName);
        currentTeamEditText.setText(currentTeam);
        experienceEditText.setText(experience);
        trophiesWonEditText.setText(trophiesWon);

        InitializeDatePicker();
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show(); //afisam dialog boxul (casuta neagra)
            }
        });

        InitializeTimePicker();
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog.show(); //afisam dialog boxul (casuta neagra)
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectedCoachForHavingATrainingSessionWith.this,FindCoach.class));
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Database db=new Database(getApplicationContext(),"FootballAcademy",null,1);
                SharedPreferences sharedPreferences=getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username=sharedPreferences.getString("username","").toString();
                db.addOrder(username,title+"=>"+coachNameEditText.getText(),currentTeamEditText.getText().toString(),experienceEditText.getText().toString(), 0,0,"CoachingSession");
                Toast.makeText(getApplicationContext(),"Your coaching session was successfully reserved!",Toast.LENGTH_LONG).show();
                startActivity(new Intent(SelectedCoachForHavingATrainingSessionWith.this,HomeActivity.class));
            }
        });
    }

    private void InitializeDatePicker()
    {
        //cand apasam pe butonul de setare a datei, se va apela OnDateSetListener
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) { //i=year,i1=month,i2=dayOfMonth
                i1=i1+1;
                dateButton.setText(i2+"/"+i1+"/"+i); //cand ne mutam de la o optiune la alta
            }
        };
        Calendar calendar= Calendar.getInstance();
        int currentYear=calendar.get(Calendar.YEAR);
        int currentMonth=calendar.get(Calendar.MONTH);
        int currentDay=calendar.get(Calendar.DAY_OF_MONTH);
        int style= AlertDialog.THEME_HOLO_DARK;
        datePickerDialog=new DatePickerDialog(this,style,dateSetListener,currentYear,currentMonth,currentDay);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis()+86400000); //si cream DatePickerDialog
    }

    private void InitializeTimePicker()
    {
        //cand apasam pe butonul de setare a orei, se va apela OnTimeSetListener
        TimePickerDialog.OnTimeSetListener timeSetListener=new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) { //i=hourOfDay, i1=minute
                timeButton.setText(i+":"+i1); //cand ne mutam de la o optiune la alta
            }
        };
        Calendar calendar= Calendar.getInstance();
        int hours=calendar.get(Calendar.HOUR);
        int mins=calendar.get(Calendar.MINUTE);
        int style= AlertDialog.THEME_HOLO_DARK;
        timePickerDialog=new TimePickerDialog(this,style,timeSetListener,hours,mins,true); //si cream TimePickerDialog
    }
}