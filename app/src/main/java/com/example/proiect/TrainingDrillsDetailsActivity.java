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

public class TrainingDrillsDetailsActivity extends AppCompatActivity {

    TextView packageName, totalCost;

    EditText editTextDetails;

    Button backButton,addToCartButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_drills_details);

        packageName=findViewById(R.id.textViewTrainingDrillsApplicationTitle);
        totalCost=findViewById(R.id.textViewTrainingDrillsTotalCost);
        editTextDetails=findViewById(R.id.editTextTrainingDrillsDetails);
        addToCartButton=findViewById(R.id.TrainingDrillsDetailsAddToCartButton);
        backButton=findViewById(R.id.TrainingDrillsDetailsBackButton);

        editTextDetails.setKeyListener(null);// ca sa nu il putem modifica

        Intent intent=getIntent();
        packageName.setText(intent.getStringExtra("packageName"));
        editTextDetails.setText(intent.getStringExtra("packageDetails"));
        totalCost.setText("Cost:"+intent.getStringExtra("cost")+ "$");

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TrainingDrillsDetailsActivity.this,TrainingDrillsActivity.class));
            }
        });

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences=getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username=sharedPreferences.getString("username","").toString(); //luam username-ul din shared preferences
                String product=packageName.getText().toString(); //luam numele pachetului ales pentru a-l adauga in cart
                float price=Float.parseFloat(intent.getStringExtra("cost").toString()); //luam pretul pachetului ales pentru a-l adauga in cart

                Database db=new Database(getApplicationContext(),"FootballAcademy",null,1);
                if(db.checkIfItemIsAlreadyInCart(username,product)==1)
                {
                    Toast.makeText(getApplicationContext(),"Product was already added!",Toast.LENGTH_SHORT).show();
                }
                else{
                    db.addToCart(username,product,price,"TrainingDrill");
                    Toast.makeText(getApplicationContext(),"Succesfully added training drill to cart!",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(TrainingDrillsDetailsActivity.this,TrainingDrillsActivity.class));
                }
            }
        });
    }
}