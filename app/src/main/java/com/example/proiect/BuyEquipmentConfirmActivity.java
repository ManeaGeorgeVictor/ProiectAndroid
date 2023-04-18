package com.example.proiect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BuyEquipmentConfirmActivity extends AppCompatActivity {

    EditText fullnameEditText, addressEditText,contactNumberEditText,pincodeEditText;

    Button confirmationButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_equipment_confirm);

        fullnameEditText=findViewById(R.id.editTextActivityBuyEquipmentConfirmFullName);
        addressEditText=findViewById(R.id.editTextActivityBuyEquipmentConfirmAdress);
        contactNumberEditText=findViewById(R.id.editTextActivityBuyEquipmentConfirmContactNumber);
        pincodeEditText=findViewById(R.id.editTextActivityBuyEquipmentConfirmPinCode);
        confirmationButton=findViewById(R.id.buttonConfirmBuyEquipment);

        Intent intent=getIntent();
        String[] prices=intent.getStringExtra("price").toString().split(java.util.regex.Pattern.quote(":")); //facem split dupa : ca sa luam doar valoarea efectiva

        //adaugam comanda in baza de date:
        confirmationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences=getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username=sharedPreferences.getString("username","").toString();
                Database db=new Database(getApplicationContext(),"FootballAcademy",null,1);
                db.removeCart(username,"Equipment");
                db.addOrder(username,fullnameEditText.getText().toString(),
                        addressEditText.getText().toString(),
                        contactNumberEditText.getText().toString(),
                        Integer.parseInt(pincodeEditText.getText().toString()),
                        Float.parseFloat(prices[1].toString()),"Equipment");
                db.removeCart(username,"Equipment");
                Toast.makeText(getApplicationContext(),"Transaction confirmed!",Toast.LENGTH_LONG).show();
                startActivity(new Intent(BuyEquipmentConfirmActivity.this,HomeActivity.class));
            }
        });
    }

}