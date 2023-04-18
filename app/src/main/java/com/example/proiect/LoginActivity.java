package com.example.proiect;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {

    //PENTRU GOOGLE AUTENTIFICATION:
    GoogleSignInOptions googleSignInOptions;

    GoogleSignInClient googleSignInClient;
    EditText edUsername, edPassword;
    Button loginBtn;
    ImageView googleBtn;
    TextView registerTextView;

    ImageView infoBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        googleSignInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient= GoogleSignIn.getClient(this,googleSignInOptions);
        GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null)
        {
            NavigateToSecondActivity();
        }

        //INITIALIZAM VARIABILELE CU findViewById:
        edUsername=findViewById(R.id.editTextLoginUsername);
        edPassword=findViewById(R.id.editTextLoginPassword);
        loginBtn=findViewById(R.id.buttonLogin);
        registerTextView=findViewById(R.id.textViewNewUser);
        googleBtn=findViewById(R.id.googleButton);
        infoBtn=findViewById(R.id.redirectToVideoButton);

        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,VideoPlaybackActivity.class));
            }
        });

        //II SPUNEM BUTONULUI DE LOGIN CE SA FACA LA CLICK:
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //LUAM string-ul de la input field-ul(edit text-ul) de username introdus de utilizator:
                String usernameText=edUsername.getText().toString();
                //LUAM string-ul de la input field-ul(edit text-ul) de password introdus de utilizator:
                String passwordText=edPassword.getText().toString();
                Database db=new Database(getApplicationContext(),"FootballAcademy",null,1);//APELAM CONSTRUCTORUL CLASEI DATABASE PE CARE AM CREAT-O
                if(usernameText.length()==0||passwordText.length()==0)//daca nu a completat vreunul dintre cele 2 edit texturi:
                {
                    Toast.makeText(getApplicationContext(),"Please complete all the required fields",Toast.LENGTH_SHORT).show();
                }
                else//daca le-a completat pe ambele:
                {
                    if(db.loginUser(usernameText,passwordText)==1) //DACA LOGAREA A REUSIT, IE LINIA EXISTA IN TABEL
                    {
                        //SHAREDPREFERENCES TE LASA SA SA SALVEZI SI IEI DATE SUB FORMA DE PERECHI CHEIE VALOARE:
                        Toast.makeText(getApplicationContext(),"Login succes!",Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPreferences=getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);//ASA LUAM EFECTIV REFERINTA CATRE FILE-UL CARE ARE VALORILE PREFERINTELOR
                        //SHAREDPREFERENCES EDITOR E FOLOSIT CA SA SALVEZI DATA:
                        SharedPreferences.Editor sharedPreferencesEditor=sharedPreferences.edit();
                        sharedPreferencesEditor.putString("username",usernameText);//AICI SALVAM EFECTIV
                        sharedPreferencesEditor.apply();//CA UN COMMIT
                        startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Invalid username or password!",Toast.LENGTH_SHORT).show();
                    }
                }
                //PENTRU DEBUGGING:primul parametru e contextul curent,apoi mesajul afisat,si apoi cat timp sa afiseze respectivul mesaj
                // Toast.makeText(getApplicationContext(),"login succes",Toast.LENGTH_SHORT).show();
            }

        });

        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //aici vom face login cu ajutorul Google-ului
                SignIn();
            }
        });

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Vrem ca la apasarea butonului de register, sa schimbam activitatea, ajungand astfel la RegisterActivity.Procedam astfel:
                //folosim un intent explicit, deoarece este vorba de o activitate user-defined
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class)); //ne mutam de pe LoginActivity pe RegisterActivity
            }
        });

    }

    void SignIn()
    {
        Intent signInIntent=googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000)
        {
            Task<GoogleSignInAccount>  task=GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                //blocul de try
                task.getResult(ApiException.class);
                NavigateToSecondActivity();
            }catch(ApiException e){
                //blocul de catch:logarea a esuat
                Toast.makeText(getApplicationContext(),"Something went wrong!",Toast.LENGTH_SHORT).show();
            }
        }
    }

    void NavigateToSecondActivity(){
        finish();
        Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
        startActivity(intent);
    }
}