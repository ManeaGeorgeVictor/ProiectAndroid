package com.example.proiect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText edUsername, edEmail, edPassword, edConfirmationPassword;
    Button registerButton;
    TextView alreadyHaveAnAccountTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //INITIALIZAM VARIABILELE CU findViewById:
        edUsername = findViewById(R.id.editTextActivityTrainingDrillsConfirmFullName);
        edPassword = findViewById(R.id.editTextActivityTrainingDrillsConfirmPinCode);
        edEmail = findViewById(R.id.editTextActivityTrainingDrillsConfirmAdress);
        edConfirmationPassword = findViewById(R.id.editTextActivityTrainingDrillsConfirmContactNumber);
        registerButton = findViewById(R.id.buttonConfirmTrainingDrills);
        alreadyHaveAnAccountTextView = findViewById(R.id.textViewAlreadyHaveAnAccount);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel=new NotificationChannel("notification","notification",NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }

        alreadyHaveAnAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Vrem ca la apasarea butonului de register, sa schimbam activitatea, ajungand astfel la RegisterActivity.Procedam astfel:
                //folosim un intent explicit, deoarece este vorba de o activitate user-defined
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class)); //ne mutam de pe LoginActivity pe RegisterActivity
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameText = edUsername.getText().toString();
                String emailText = edEmail.getText().toString();
                String passwordText = edPassword.getText().toString();
                String confirmationPasswordText = edConfirmationPassword.getText().toString();
                Database db=new Database(getApplicationContext(),"FootballAcademy",null,1);//APELAM CONSTRUCTORUL CLASEI DATABASE PE CARE AM CREAT-O
                if (usernameText.length() == 0 || passwordText.length() == 0 || emailText.length() == 0 || confirmationPasswordText.length() == 0)//daca nu a completat vreunul dintre cele 2 edit texturi:
                {
                    Toast.makeText(getApplicationContext(), "Please complete all the required fields", Toast.LENGTH_SHORT).show();
                } else {
                    //VERIFICAM DACA PASSWORD SI CONFIRM PASSWORD SUNT IDENTICE
                    if (passwordText.compareTo(confirmationPasswordText) == 0) {
                        //VERIFICAM DACA PAROLA E VALIDA, IE:SA AIBE MACAR O LITERA,O CIFRA,UN CARACTER SPECIAL SI SA FIE DE LUNGIME >=8
                        if(isPasswordValid(passwordText))
                        {
                            db.registerNewUser(usernameText,emailText,passwordText);
                            //Toast.makeText(getApplicationContext(), "Registration succesfull!", Toast.LENGTH_SHORT).show();

                            //FACEM O NOTIFICARE LA INSERAREA UNUI NOU USER CU SUCCES:
                            String message="New user successfully inserted!";
                            NotificationCompat.Builder builder=new NotificationCompat.Builder(RegisterActivity.this,"notification"
                                    ).setSmallIcon(R.drawable.baseline_account_circle_24)
                                    .setContentTitle("User inserted in database!")
                                    .setContentText(message)
                                    .setAutoCancel(true);
                            Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                            NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(RegisterActivity.this);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            PendingIntent pendingIntent=PendingIntent.getActivity(RegisterActivity.this,
                                    0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                            builder.setContentIntent(pendingIntent);
                            NotificationManager notificationManager=(NotificationManager)getSystemService(
                                    Context.NOTIFICATION_SERVICE
                            );
                            notificationManager.notify(1,builder.build());
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Invalid password:Password must contain at least 1 letter, digit and special character and be of minimum length 8", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Password and confirm password doesn't match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    public static boolean isPasswordValid(String password)
    {
        int hasOneLetter=0,hasOneDigit=0,hasOneSpecialCharacter=0;
      if(password.length()<8)
      {
          return false;
      }
      else{
          //VERIFICAM DACA ARE MACAR O LITERA:
          for(int i=0;i<password.length();i++)
          {
              if(Character.isLetter(password.charAt(i)))
              {
                  hasOneLetter=1;
              }
          }

          //VERIFICAM DACA ARE MACAR O CIFRA:
          for(int i=0;i<password.length();i++)
          {
              if(Character.isDigit(password.charAt(i)))
              {
                  hasOneDigit=1;
              }
          }

          //VERIFICAM DACA ARE MACAR UN CARACTER SPECIAL:
          for(int i=0;i<password.length();i++)
          {
              char potentialSpecialCharacter=password.charAt(i);
              if((potentialSpecialCharacter>=33&&potentialSpecialCharacter<=46)||potentialSpecialCharacter==64)
              {
                  hasOneSpecialCharacter=1;
              }
          }

          if(hasOneLetter==1&&hasOneDigit==1&&hasOneSpecialCharacter==1)
          {
              return true;
          }
          return false;
      }
    }

    }
