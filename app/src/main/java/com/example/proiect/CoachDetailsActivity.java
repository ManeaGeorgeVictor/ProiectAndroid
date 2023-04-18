package com.example.proiect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class CoachDetailsActivity extends AppCompatActivity {

    private String[][] coachDetailsPsychologists=
            {
                    {"Coach name: Carlo Ancelotii", "Current team: Real Madrid", "Experience: 25 years", "Trophies won:20"},
                    {"Coach name: Jurgen Klopp", "Current team: Liverpool", "Experience: 15 years", "Trophies won:15"},
                    {"Coach name: Diego Simeone", "Current team: Atletico Madrid", "Experience: 12 years", "Trophies won:10"},
                    {"Coach name: Maurizio Sarri", "Current team: Lazio", "Experience: 22 years", "Trophies won:5"},
                    {"Coach name: Luciano Spalletti", "Current team: SSC Napoli", "Experience: 17 years", "Trophies won:8"}

            };

    private String[][] coachDetailsTacticians=
            {
                    {"Coach name: Jose Mourinho", "Current team: AS Roma", "Experience: 25 years", "Trophies won:30"},
                    {"Coach name: Claudio Ranieri", "Current team: Watford FC", "Experience: 20 years", "Trophies won:10"},
                    {"Coach name: Rafael Benitez", "Current team: Everton FC", "Experience: 18 years", "Trophies won:6"},
                    {"Coach name: Antonio Conte", "Current team: Tottenham Hotspur FC", "Experience: 13 years", "Trophies won:9"},
                    {"Coach name: Mikel Arteta", "Current team: Arsenal", "Experience: 3 years", "Trophies won:2"}

            };

    private String[][] coachDetailsGeniuses=
            {
                    {"Coach name: Pep Guardiola", "Current team: Manchester City", "Experience: 17 years", "Trophies won:20"},
                    {"Coach name: Tomas Tuchel", "Current team: Borussia Dortmund", "Experience: 10 years", "Trophies won:15"},
                    {"Coach name: Unai Emery", "Current team: Aston Villa FC", "Experience: 5 years", "Trophies won:2"},
                    {"Coach name: Erik Ten Hag", "Current team: Manchester United", "Experience: 9 years", "Trophies won:5"},
                    {"Coach name: Lionel Scaloni", "Current team: Argentina", "Experience: 25 years", "Trophies won:2"}

            };

    private String[][] coachDetailsHardworkers=
            {
                    {"Coach name: Hansi Flick", "Current team: FC Bayern Munchen", "Experience: 15 years", "Trophies won:10"},
                    {"Coach name: Gica Hagi", "Current team: FC Viitorul", "Experience: 14 years", "Trophies won:6"},
                    {"Coach name: Massimiliano Allegri", "Current team: Juventus Torino", "Experience: 18 years", "Trophies won:13"},
                    {"Coach name: Tite", "Current team: Brazil", "Experience: 20 years", "Trophies won:5"},
                    {"Coach name: Fernando Santos", "Current team: Portugal", "Experience: 15 years", "Trophies won:3"}

            };

    private String[][] coachDetailsExperienced=
            {
                    {"Coach name: Mircea Lucescu", "Current team: FC Dinamo Kiev", "Experience: 25 years", "Trophies won:12"},
                    {"Coach name: Didier Deschamps", "Current team: Franta", "Experience: 18 years", "Trophies won:8"},
                    {"Coach name: Christophe Galtier", "Current team: Paris Saint-Germain", "Experience: 22 years", "Trophies won:6"},
                    {"Coach name: Jorge Sampaoli", "Current team: FC Sevilla", "Experience: 20 years", "Trophies won:13"},
                    {"Coach name: Stefano Pioli", "Current team: AC Milan", "Experience: 20 years", "Trophies won:12"}

            };
    TextView coachDetailsTextView;

    Button coachDetailsBackButton;

    String[][] chosenCoachDetails={};

    ArrayList list;

    SimpleAdapter simpleAdapter; //vom folosi un SimpleAdapter pentru a mapui data din lista list cu listView ul din fisierul xml activity_coach_details(pentru a popula efectiv listView-ul din fisierul xml activity_coach_details)

    HashMap<String,String> item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_details);
        coachDetailsTextView=findViewById(R.id.textViewCoachDetailsApplicationTitle);
        coachDetailsBackButton=findViewById(R.id.coachDetailsBackButton);
        Intent it=getIntent(); //cream un obiect intent pentru a putea afisa pe activitatea de CoachDetails tipul antrenorului ales
        String title=it.getStringExtra("type");
        coachDetailsTextView.setText(title);

        //in functie de tipul antrenorului ales, populam coachDetails corespunzator (ie cu antrenori fie psychologists, fie tacticians, etc)
        if(title!=null) {
            if (title.compareTo("PSYCHOLOGISTS") == 0) //ie daca se matchuiesc perfect stringurile
            {
                chosenCoachDetails = coachDetailsPsychologists;
            } else if (title.compareTo("TACTICIANS") == 0) {
                chosenCoachDetails = coachDetailsTacticians;
            } else if (title.compareTo("GENIUSES") == 0) {
                chosenCoachDetails = coachDetailsGeniuses;
            } else if (title.compareTo("HARDWORKERS") == 0) {
                chosenCoachDetails = coachDetailsHardworkers;
            } else {
                chosenCoachDetails = coachDetailsExperienced;
            }
        }
        //Setam ca la click pe buton sa ne dea inapoi la activitatea de FindCoach
        coachDetailsBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CoachDetailsActivity.this,FindCoach.class));
            }
        });

        list=new ArrayList(); //alocam lista
        for(int i=0;i<chosenCoachDetails.length;i++)
        {
            item=new HashMap<String,String>();//trebuie sa trimitem datele in SimpleAdapter sub forma unui ArrayList de mapuri.
            //pentru fiecare element din chosenCoachDetails:
            item.put("line1",chosenCoachDetails[i][0]); //punem pe linia 1 numele antrenorului
            item.put("line2",chosenCoachDetails[i][1]);// punem pe linia a 2 a echipa antrenorului
            item.put("line3",chosenCoachDetails[i][2]);//punem pe linia a 3 a anii experienta
            item.put("line4",chosenCoachDetails[i][3]);//punem pe linia a 4 a trofeele castigate
            list.add(item);//adaugam item-ul in lista.Fiecare item din lista list va fi o linie din listView-ul listViewCoachDetails
        }
        //alocam adapter-ul:prima data ii dam contextul(this)
        //apoi indicam fisierul costum de layout(multi_lines),
        //apoi lista de hashmapuri
        //apoi se face maparea efectiva dintre array-ul de string-uri si array-l de int-uri
        String[] fromArray=new String[]{"line1","line2","line3","line4"};
        int[] toArray=new int[]{R.id.line_1,R.id.line_2,R.id.line_3,R.id.line_4};
        simpleAdapter=new SimpleAdapter(this,
                list,
                R.layout.multi_lines,
                fromArray,
                toArray);

        ListView listViewCoachDetails=findViewById(R.id.listViewCoachDetails);
        listViewCoachDetails.setAdapter(simpleAdapter); //setam SimpleAdapterul

        //setam comportamentul de click pe un element din listView:
        listViewCoachDetails.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(CoachDetailsActivity.this,SelectedCoachForHavingATrainingSessionWith.class);
                //cand ne mutam de la activitatea aceasta la SelectedCoachForHavingATrainingSessionWith, trebuie sa pasam niste date:
                intent.putExtra("title",title);
                intent.putExtra("coachName",chosenCoachDetails[i][0]);
                intent.putExtra("currentTeam",chosenCoachDetails[i][1]);
                intent.putExtra("experience",chosenCoachDetails[i][2]);
                intent.putExtra("trophiesWon",chosenCoachDetails[i][3]);
                startActivity(intent);
            }
        });
    }
}