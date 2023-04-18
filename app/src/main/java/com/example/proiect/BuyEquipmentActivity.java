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

public class BuyEquipmentActivity extends AppCompatActivity {

    private String[][] equipments={
            {"Barcelona Equipment","","","","150"}, //cele 3 pozitii din mijloc sunt libere pentru a refolosi multi_lines.xml
            {"Napoli Equipment","","","","125"},
            {"Real Madrid Equipment","","","","175"},
            {"FC Bayern Equipment","","","","150"},
            {"Manchester United Equipment","","","","130"},
            {"Atletico Madrid Equipment","","","","150"},
            {"AC Milan Equipment","","","","150"},
            {"Borussia Dortmund Equipment","","","","150"},
            {"Ajax Equipment","","","","150"},
    };

    private String[] equipmentsDetails={
       "Barcelona full 2016-2017 home equipment",
       "Napoli full 2022-2023 away equipment",
       "Real Madrid 2014-2015 full home equipment",
       "FC Bayern 2015-2016 full home equipment",
       "Manchester United 2019-2020 alternate equipment",
       "Atletico Madrid 2017-2018 away equipment" ,
       "AC Milan 2018-2019 home equipment",
       "Borussia Dortmund 2020-2021 home equipment",
       "Ajax 2021-2022 home equipment" ,
    };

    HashMap<String,String> item;
    ArrayList arrayList;
    SimpleAdapter simpleAdapter;
    ListView listView;
    Button backButton,goToCartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_equipment);
        listView=findViewById(R.id.listViewEquipments);
        backButton=findViewById(R.id.BuyEquipmentBackButton);
        goToCartButton=findViewById(R.id.BuyEquipmentGoToCartButton);

        goToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(BuyEquipmentActivity.this,CartBuyEquipmentActivity.class));
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BuyEquipmentActivity.this,HomeActivity.class));
            }
        });

        arrayList=new ArrayList(); //alocam lista
        for(int i=0;i<equipments.length;i++)
        {
            item=new HashMap<String,String>();
            item.put("line1",equipments[i][0]);
            item.put("line2",equipments[i][1]);
            item.put("line3",equipments[i][2]);
            item.put("line4",equipments[i][3]);
            arrayList.add(item);//adaugam item-ul in lista.
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
                Intent intent=new Intent(BuyEquipmentActivity.this,BuyEquipmentDetailsActivity.class);
                intent.putExtra("text1",equipments[i][0]);//numele
                intent.putExtra("text2",equipmentsDetails[i]);//details
                intent.putExtra("text3",equipments[i][4]);//total cost
                startActivity(intent);
            }
        });
    }
}