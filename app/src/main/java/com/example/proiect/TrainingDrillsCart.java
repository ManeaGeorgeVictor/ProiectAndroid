package com.example.proiect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class TrainingDrillsCart extends AppCompatActivity {

    HashMap<String,String>item;
    ArrayList arrayList;
    SimpleAdapter simpleAdapter;
    TextView totalCostTextView;
    Button confirmButton,backButton;

    ListView listView;
    String [][] userChosenPackages={};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_drills_cart);
        confirmButton=findViewById(R.id.buttonConfirmTrainingDrills);
        backButton=findViewById(R.id.TrainingDrillsCartBack);
        totalCostTextView=findViewById(R.id.textViewTrainingDrillsCartTotalCost);
        listView=findViewById(R.id.listViewTrainingDrillsCart);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TrainingDrillsCart.this,TrainingDrillsActivity.class));
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TrainingDrillsCart.this,TrainingDrillsConfirm.class);
                intent.putExtra("price",totalCostTextView.getText());
                startActivity(intent);
            }
        });
        SharedPreferences sharedPreferences=getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username=sharedPreferences.getString("username","").toString();
        Database db=new Database(getApplicationContext(),"FootballAcademy",null,1);
        float totalAmount=0;
        ArrayList trainingDrillsNamesAndCosts=db.getCartData(username,"TrainingDrill");
        //Toast.makeText(getApplicationContext(),""+trainingDrillsNamesAndCosts,Toast.LENGTH_LONG).show();

        //userChosenPackages va avea trainingDrillsNamesAndCosts.size() linii si fiecare linie va fi formata din 4 coloane
        userChosenPackages=new String[trainingDrillsNamesAndCosts.size()][];
        for(int i=0;i<userChosenPackages.length;i++){
            userChosenPackages[i]=new String[4]; //4 e numarul total de TrainingDrillsPackages; 4 adica 4 coloane
        }

        //CALCULAM SUMA TUTUROR TRAINING PACKAGES-URILOR ALESE DE UTILIZATOR:
        for(int i=0;i<trainingDrillsNamesAndCosts.size();i++){
            String stringData=trainingDrillsNamesAndCosts.get(i).toString();//luam item-ul curent din trainingDrillsNamesAndCosts, care va avea un nume si un pret
            String[] stringDataArray=stringData.split(java.util.regex.Pattern.quote("$"));//astfel, in stringDataArray[0] va fi numele TrainingDrill-ului, iar in stringDataArray[1] va fi pretul
            userChosenPackages[i][0]=stringDataArray[0];
            userChosenPackages[i][3]="Cost:"+stringDataArray[1]+"$";
            totalAmount=totalAmount+Float.parseFloat(stringDataArray[1]);
        }

        totalCostTextView.setText("Total Cost:"+totalAmount);

        //AFISAM TRAINING PACKAGES-URILE ALESE DE UTILIZATOR:
        arrayList=new ArrayList();
        for(int i=0;i< userChosenPackages.length;i++){
            item=new HashMap<String,String>();
            item.put("line1",userChosenPackages[i][0]);
            item.put("line2",userChosenPackages[i][1]);
            item.put("line3",userChosenPackages[i][2]);
            item.put("line4",userChosenPackages[i][3]);
            arrayList.add(item);
        }
        String[] fromArray=new String[]{"line1","line2","line3","line4"};
        int[] toArray=new int[]{R.id.line_1,R.id.line_2,R.id.line_3,R.id.line_4};
        simpleAdapter=new SimpleAdapter(this,
                arrayList,
                R.layout.multi_lines,
                fromArray,
                toArray);
        listView.setAdapter(simpleAdapter);
    }
}