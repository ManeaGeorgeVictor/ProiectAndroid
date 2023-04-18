package com.example.proiect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class TrainingDrillsActivity extends AppCompatActivity {

    private String[][] trainingDrillsPackages=
            {
                    {"Training drill 1:Dribbling Drills","","","","900"},
                    {"Training drill 2:Shooting Drills","","","","800"},
                    {"Training drill 3:Passing Drills","","","","700"},
                    {"Training drill 4:Tackling Drills","","","","600"}
            };

    private String[] trainingDrillsDetails={
            "1. STRAIGHT CONE DRIBBLE DRILL\n"+
            "2. FORTH & BACK CONE DRIBBLE DRILL\n"+
            "3. ONE ON ONE CONTROLLED DRIBBLE DRILL\n"+
            "4. CIRCULAR CONE DRIBBLE DRILL\n"+
            "5. RANDOM DRIBBLE DRILL\n"+
            "6. ONE AGAINST TWO DRIBBLE DRILL\n"+
            "7. ROUND THE CYCLIC CONE DRIBBLE DRILL\n"+
            "8. ROUND THE SQUARE DRIBBLE DRILL\n"+
            "9. MUSICAL DRIBBLE DRILL\n"+
            "10. BULLDOG ATTACK DRIBBLE DRILL",

            "1. 1, 2 SHOOT\n"+
            "2. 1-ON-1 SHOOTOUT\n"+
            "3. CROSS AND FINISH COMPETITION\n"+
            "4. CROSS CONTROL\n"+
            "5. DIAGONAL SHOOTING\n"+
            "6. FINAL PASS AND FINISH\n"+
            "7. FIRST TO FINISH\n"+
            "8. GATE SHOOTING\n"+
            "9. MOVING GOAL\n"+
            "10. POWER AND FINESSE",

            "1. 1-IN 1-OUT\n"+
            "2. 4-ON-3 ATTACK\n"+
            "3. GRID PASSING\n"+
            "4. GUARD THE CASTLE\n"+
            "5. MEET THE BALL\n"+
            "6. NUMBERS PASSING\n"+
            "7. ONE TOUCH PAIRS PASSING\n"+
            "8. PASS & OVERLAP\n"+
            "9. PASS THROUGH TRAFIC\n"+
            "10.  SPACE WARS",

            "1. 1v1\n"+
            "2. 2v2\n"+
            "3. 3v3\n"+
            "4. PIGGY IN THE MIDDLE\n"+
            "5. RONDO\n"+
            "6. PROTECT THE BALL\n"+
            "7. GATE TACKLING\n"+
            "8. TIMED TACKLING\n"+
            "9. RACE TACKLING\n"+
            "10. 2v1"

    };

    //acceasi logica ca la CoachDetails:
    HashMap<String,String>item;
    ArrayList arrayList;
    SimpleAdapter simpleAdapter;
    Button goToCartButton,backButton;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_drills);
        goToCartButton=findViewById(R.id.BuyEquipmentGoToCartButton);
        backButton=findViewById(R.id.BuyEquipmentBackButton);
        listView=findViewById(R.id.listViewTrainingDrills);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TrainingDrillsActivity.this,HomeActivity.class));
            }
        });

        arrayList=new ArrayList();
        for(int i=0;i<trainingDrillsPackages.length;i++)
        {
            item=new HashMap<String,String>();
            item.put("line1",trainingDrillsPackages[i][0]);
            item.put("line2",trainingDrillsPackages[i][1]);
            item.put("line3",trainingDrillsPackages[i][2]);
            item.put("line4",trainingDrillsPackages[i][3]);
            arrayList.add(item);
        }

        String[] fromArray=new String[]{"line1","line2","line3","line4"};
        int[] toArray=new int[]{R.id.line_1,R.id.line_2,R.id.line_3,R.id.line_4};
        simpleAdapter=new SimpleAdapter(this,
                arrayList,
                R.layout.multi_lines,
                fromArray,
                toArray);

        listView.setAdapter(simpleAdapter); //setam SimpleAdapterul

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(TrainingDrillsActivity.this,TrainingDrillsDetailsActivity.class);
                intent.putExtra("packageName",trainingDrillsPackages[i][0]);//numele
                intent.putExtra("packageDetails",trainingDrillsDetails[i]);//detaliile despre fiecare pachet
                intent.putExtra("cost",trainingDrillsPackages[i][4]);
                startActivity(intent);
            }
        });

        goToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TrainingDrillsActivity.this,TrainingDrillsCart.class));
            }
        });
    }
}