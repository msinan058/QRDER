package com.example.qrder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class singIn_singUp extends AppCompatActivity {

    TextView sifremiUnuttumText;
    EditText  emailText;
    EditText passwordText;
    Button  singInButton;
    Button  singUpButton;

     FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_sing_in_sing_up);

        sifremiUnuttumText=findViewById (R.id.sisu_SifremiUnuttumText);
        emailText=findViewById (R.id.sisu_EmailText);
        passwordText=findViewById (R.id.sisu_PasswordText);
        singInButton = findViewById (R.id.sisu_singInButton);
        singUpButton = findViewById (R.id.sisu_singUpButton);

        firebaseAuth = FirebaseAuth.getInstance ();
        FirebaseUser firebaseUser =firebaseAuth.getCurrentUser ();


        if (firebaseUser!=null){
            Intent ıntent = new Intent (getApplicationContext (),main_musteri_activity.class);
            startActivity (ıntent);
            finish ();


        }

          Intent ıntent = getIntent ();

         final String tur = ıntent.getStringExtra ("Tür");

        singInButton.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick (View v) {

                String eMail = emailText.getText ().toString ();
                String password = passwordText.getText ().toString ();

                if (tur.matches ("restorant")){

                    firebaseAuth.signInWithEmailAndPassword (eMail,password).addOnSuccessListener (new OnSuccessListener<AuthResult> ( ) {
                        @Override
                        public void onSuccess (AuthResult authResult) {


                            Intent ıntent1 = new Intent (getApplicationContext (),main_restorant_activity.class);
                            startActivity (ıntent1);

                        }
                    }).addOnFailureListener (new OnFailureListener ( ) {
                        @Override
                        public void onFailure (@NonNull Exception e) {

                            Toast.makeText (getApplicationContext (),e.getLocalizedMessage (),Toast.LENGTH_SHORT).show ();
                        }
                    });


                }
                if (tur.matches ("musteri")){


                    firebaseAuth.signInWithEmailAndPassword (eMail,password).addOnSuccessListener (new OnSuccessListener<AuthResult> ( ) {
                        @Override
                        public void onSuccess (AuthResult authResult) {

                            Intent ıntent1 = new Intent (getApplicationContext (),main_musteri_activity.class);
                            startActivity (ıntent1);


                        }
                    }).addOnFailureListener (new OnFailureListener ( ) {
                        @Override
                        public void onFailure (@NonNull Exception e) {


                            Toast.makeText (getApplicationContext (),e.getLocalizedMessage (),Toast.LENGTH_SHORT).show ();
                        }
                    });


                }
            }
        });

        singUpButton.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick (View v) {

                if (tur.matches ("musteri")){

                    String eMail = emailText.getText ().toString ();
                    String password = passwordText.getText ().toString ();

                    firebaseAuth.createUserWithEmailAndPassword (eMail,password).addOnSuccessListener (new OnSuccessListener<AuthResult> ( ) {
                        @Override
                        public void onSuccess (AuthResult authResult) {

                            Toast.makeText (getApplicationContext (),"Customer User Created",Toast.LENGTH_LONG).show ();

                        }
                    }).addOnFailureListener (new OnFailureListener ( ) {
                        @Override
                        public void onFailure (@NonNull Exception e) {

                            Toast.makeText (getApplicationContext (),e.getLocalizedMessage (),Toast.LENGTH_LONG).show ();

                        }
                    });


                }

                if (tur.matches ("restorant")){


                    String eMail = emailText.getText ().toString ();
                    String password = passwordText.getText ().toString ();

                    firebaseAuth.createUserWithEmailAndPassword (eMail,password).addOnSuccessListener (new OnSuccessListener<AuthResult> ( ) {
                        @Override
                        public void onSuccess (AuthResult authResult) {

                            Toast.makeText (getApplicationContext (),"Restorant User Created",Toast.LENGTH_LONG).show ();

                        }
                    }).addOnFailureListener (new OnFailureListener ( ) {
                        @Override
                        public void onFailure (@NonNull Exception e) {

                            Toast.makeText (getApplicationContext (),e.getLocalizedMessage (),Toast.LENGTH_LONG).show ();

                        }
                    });


                }

            }
        });


    }


}
