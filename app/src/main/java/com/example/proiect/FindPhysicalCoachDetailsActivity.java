package com.example.proiect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FindPhysicalCoachDetailsActivity extends AppCompatActivity {

    TextView textView;

    ImageView imageView;

    Button backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_physical_coach_details);

        backButton=findViewById(R.id.FindPhysicalCoachDetailsBackButton);
        textView=findViewById(R.id.FindPhysicalCoachDetailsNameTextView);
        imageView=findViewById(R.id.FindPhysicalCoachDetailsImageView);

        Intent intent=getIntent();
        textView.setText(intent.getStringExtra("text1"));

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            int idd=bundle.getInt("text2");
            imageView.setImageResource(idd);
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FindPhysicalCoachDetailsActivity.this,HomeActivity.class));
            }
        });
    }
}