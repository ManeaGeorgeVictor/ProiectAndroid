package com.example.proiect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BuyEquipmentDetailsActivity extends AppCompatActivity {

    TextView equipmentNameTextView, totalCostTextView;
    EditText equipmentDetailsEditText;

    Button backButton, addToCartButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_equipment_details);

        equipmentNameTextView=findViewById(R.id.textViewBuyEquipmentDetailsApplicationTitle);
        equipmentDetailsEditText=findViewById(R.id.editTextBuyEquipmentDetails);
        equipmentDetailsEditText.setKeyListener(null);
        totalCostTextView=findViewById(R.id.textViewBuyEquipmentDetailsTotalCost);
        backButton=findViewById(R.id.BuyEquipmentDetailsBackButton);
        addToCartButton=findViewById(R.id.BuyEquipmentDetailsAddToCartButton);

        Intent intent=getIntent();
        equipmentNameTextView.setText(intent.getStringExtra("text1"));
        equipmentDetailsEditText.setText(intent.getStringExtra("text2"));
        totalCostTextView.setText("Cost:"+ intent.getStringExtra("text3"));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BuyEquipmentDetailsActivity.this,BuyEquipmentActivity.class));
            }
        });

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences=getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username=sharedPreferences.getString("username","").toString();
                String equipment=equipmentNameTextView.getText().toString();
                float price=Float.parseFloat(intent.getStringExtra("text3").toString()); //text3=pret
                Database db=new Database(getApplicationContext(),"FootballAcademy",null,1);
                if(db.checkIfItemIsAlreadyInCart(username,equipment)==1)
                {
                    Toast.makeText(getApplicationContext(),"Equipment was already added!",Toast.LENGTH_SHORT).show();
                }
                else{
                    db.addToCart(username,equipment,price,"Equipment");
                    Toast.makeText(getApplicationContext(),"Succesfully added equipment to cart!",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(BuyEquipmentDetailsActivity.this,BuyEquipmentActivity.class));
                }
            }
        });
    }
}