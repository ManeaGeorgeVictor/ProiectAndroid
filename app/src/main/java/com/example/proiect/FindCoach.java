package com.example.proiect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class FindCoach extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_coach);

        //IMPLEMENTAM FUNCTIONALITATEA CARDVIEW-ULUI DE EXIT
        CardView logoutView = findViewById(R.id.FindCoachBackCard);
        logoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FindCoach.this,HomeActivity.class));//NE MUTAM LA LOGIN ACTIVITY
            }
        });

        CardView ancelottiCardView=findViewById(R.id.CarloAncelottiCard);
        ancelottiCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FindCoach.this,CoachDetailsActivity.class);
                intent.putExtra("type","PSYCHOLOGISTS");
                startActivity(intent);
            }
        });

        CardView mourinhoCardView=findViewById(R.id.JoseMourinhoCard);
        mourinhoCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FindCoach.this,CoachDetailsActivity.class);
                intent.putExtra("type","TACTICIANS");
                startActivity(intent);
            }
        });

        CardView guardiolaCardView=findViewById(R.id.PepGuardiolaCard);
        guardiolaCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FindCoach.this,CoachDetailsActivity.class);
                intent.putExtra("type","GENIUSES");
                startActivity(intent);
            }
        });

        CardView flickCardView=findViewById(R.id.HansiFlickCard);
        flickCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FindCoach.this,CoachDetailsActivity.class);
                intent.putExtra("type","HARDWORKERS");
                startActivity(intent);
            }
        });

        CardView lucescuCardView=findViewById(R.id.MirceaLucescuCard);
        lucescuCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FindCoach.this,CoachDetailsActivity.class);
                intent.putExtra("type","EXPERIENCED");
                startActivity(intent);
            }
        });
    }
}