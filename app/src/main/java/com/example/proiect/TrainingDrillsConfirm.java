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

public class TrainingDrillsConfirm extends AppCompatActivity {

    EditText fullnameEditText, addressEditText,contactNumberEditText,pincodeEditText;

    Button confirmationButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_drills_confirm);
        fullnameEditText=findViewById(R.id.editTextActivityTrainingDrillsConfirmFullName);
        addressEditText=findViewById(R.id.editTextActivityTrainingDrillsConfirmAdress);
        contactNumberEditText=findViewById(R.id.editTextActivityTrainingDrillsConfirmContactNumber);
        pincodeEditText=findViewById(R.id.editTextActivityTrainingDrillsConfirmPinCode);
        confirmationButton=findViewById(R.id.buttonConfirmTrainingDrills);

        //luam datele de pe activitatea de TrainingDrillsCart
        Intent intent=getIntent();
        String[] prices=intent.getStringExtra("price").toString().split(java.util.regex.Pattern.quote(":")); //facem split dupa : ca sa luam doar valoarea efectiva

        //adaugam comanda in baza de date:
        confirmationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences=getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username=sharedPreferences.getString("username","").toString();
                Database db=new Database(getApplicationContext(),"FootballAcademy",null,1);
                db.removeCart(username,"TrainingDrill");
                db.addOrder(username,fullnameEditText.getText().toString(),
                        addressEditText.getText().toString(),
                        contactNumberEditText.getText().toString(),
                        Integer.parseInt(pincodeEditText.getText().toString()),
                        Float.parseFloat(prices[1].toString()),"TrainingDrill");
                db.removeCart(username,"TrainingDrill");
                Toast.makeText(getApplicationContext(),"Transaction confirmed!",Toast.LENGTH_LONG).show();
                startActivity(new Intent(TrainingDrillsConfirm.this,HomeActivity.class));
            }
        });
    }
}