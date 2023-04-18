package com.example.proiect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.proiect.databinding.ActivityHomeBinding;
import com.example.proiect.databinding.ActivityMainBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {

    GoogleSignInOptions googleSignInOptions;

    GoogleSignInClient googleSignInClient;
    Button buttonGoogleLogout;
   BottomNavigationView bottomNavigationView;

   HomeFragment homeFragment;
   ProfileFragment profileFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //BOTTOM NAVIGATION:
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        homeFragment=new HomeFragment();
        profileFragment=new ProfileFragment(); //profile fragment va fi fragment-ul in care vom afisa cei mai valorosi jucatori ai momentului
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {
                switch(item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(HomeActivity.this,HomeActivity.class));
                        return true;
                    case R.id.profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,profileFragment).commit();
                        return true;
                }
                return false;
            }
        });

        //GOOGLE LOGIN:
        buttonGoogleLogout=findViewById(R.id.buttonGoogleLogout);
        googleSignInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient= GoogleSignIn.getClient(this,googleSignInOptions);

        buttonGoogleLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        finish();
                        startActivity(new Intent(HomeActivity.this,LoginActivity.class));
                    }
                });
            }
        });

        //DUPA CE UTILIZATORUL S-A LOGAT CU SUCCES, AFISAM UN MESAJ DE BUN VENIIT
        SharedPreferences sharedPreferences=getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);//ASA LUAM EFECTIV REFERINTA CATRE FILE-UL CARE ARE VALORILE PREFERINTELOR
        //SHAREDPREFERENCES EDITOR E FOLOSIT CA SA SALVEZI DATA:
        String username=sharedPreferences.getString("username","").toString();
        Toast.makeText(getApplicationContext(),"Welcome "+username+"!",Toast.LENGTH_SHORT).show();

        //IMPLEMENTAM FUNCTIONALITATEA CARDVIEW-ULUI DE EXIT
        CardView logoutView = findViewById(R.id.LogoutCard);
        logoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor=sharedPreferences.edit();
                SharedPreferences sharedPreferences=getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username=sharedPreferences.getString("username","").toString();
                Database db=new Database(getApplicationContext(),"FootballAcademy",null,1);
                db.removeCart(username,"TrainingDrill");
                editor.clear();//STERGEM TOATE PERECHILE CHEIE VALOARE DIN EL CA NUMAI AVEM DE CE SA TINEM MINTE CEVA IN MEMORIA LOCALA ODATA CE DAM LOGOUT
                editor.apply();//CA UN COMMIT
                startActivity(new Intent(HomeActivity.this,LoginActivity.class));//NE MUTAM LA LOGIN ACTIVITY
            }
        });

        //NE MUTAM PE ACTIVITATEA DE FindCoach:
        CardView findCoach = findViewById(R.id.FindTrainerCard);
        findCoach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,FindCoach.class));//NE MUTAM LA FIND COACH
            }
        });

        CardView findPhysicalCoach = findViewById(R.id.FindPhysicalTrainerCard);
        findPhysicalCoach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,FindPhysicalCoachActivity.class));//NE MUTAM LA FIND PHYSICAL COACH
            }
        });

        CardView trainingDrills=findViewById(R.id.TrainingDrillsCard);
        trainingDrills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,TrainingDrillsActivity.class));//NE MUTAM LA TrainingDrills
            }
        });

        CardView buyEquipment=findViewById(R.id.BuyEquipmentCard);
        buyEquipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,BuyEquipmentActivity.class));
            }
        });
    }

}