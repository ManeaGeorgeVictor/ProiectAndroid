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

public class CartBuyEquipmentActivity extends AppCompatActivity {

    HashMap<String,String> item;
    ArrayList arrayList;
    SimpleAdapter simpleAdapter;
    TextView totalCostTextView;
    Button confirmButton,backButton;

    ListView listView;
    String [][] userChosenEquipments={};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_buy_equipment);

        confirmButton=findViewById(R.id.buttonConfirmBuyEquipment);
        backButton=findViewById(R.id.BuyEquipmentCartBack);
        totalCostTextView=findViewById(R.id.textViewBuyEquipmentCartTotalCost);
        listView=findViewById(R.id.listViewBuyEquipmentCart);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartBuyEquipmentActivity.this,BuyEquipmentActivity.class));
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CartBuyEquipmentActivity.this,BuyEquipmentConfirmActivity.class);
                intent.putExtra("price",totalCostTextView.getText());
                startActivity(intent);
            }
        });
        SharedPreferences sharedPreferences=getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username=sharedPreferences.getString("username","").toString();
        Database db=new Database(getApplicationContext(),"FootballAcademy",null,1);
        float totalAmount=0;
        ArrayList equipments=db.getCartData(username,"Equipment");

        userChosenEquipments=new String[equipments.size()][];
        for(int i=0;i<userChosenEquipments.length;i++){
            userChosenEquipments[i]=new String[4];
        }

        for(int i=0;i<equipments.size();i++){
            String stringData=equipments.get(i).toString();
            String[] stringDataArray=stringData.split(java.util.regex.Pattern.quote("$"));
            userChosenEquipments[i][0]=stringDataArray[0];
            userChosenEquipments[i][3]="Cost:"+stringDataArray[1]+"$";
            totalAmount=totalAmount+Float.parseFloat(stringDataArray[1]);
        }

        totalCostTextView.setText("Total Cost:"+totalAmount);

        arrayList=new ArrayList();
        for(int i=0;i< userChosenEquipments.length;i++){
            item=new HashMap<String,String>();
            item.put("line1",userChosenEquipments[i][0]);
            item.put("line2",userChosenEquipments[i][1]);
            item.put("line3",userChosenEquipments[i][2]);
            item.put("line4",userChosenEquipments[i][3]);
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