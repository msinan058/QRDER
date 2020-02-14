package com.example.qrder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Menu_Yukle_activity extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;

    String userEmailString;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    EditText yemekAdi;
    EditText yemekAciklama;
    EditText yemekFiyat;



    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_menu_yukleactivity);

        yemekAdi =findViewById (R.id.yemekİsmiEditText);
        yemekAciklama = findViewById (R.id.yemekAciklamasiEditText);
        yemekFiyat = findViewById (R.id.yemekFiyatEditText);



        ///for user info get firebase
         firebaseFirestore = FirebaseFirestore.getInstance ( );
         firebaseAuth = FirebaseAuth.getInstance ( );
         firebaseUser = firebaseAuth.getCurrentUser ( );
        userEmailString = firebaseUser.getEmail ();







    }

    public void menuyukle(View view){


        String yemekAdiString = yemekAdi.getText ().toString ();
        String yemekAciklamaString = yemekAciklama.getText ().toString ();
        String yemekFiyatString = yemekFiyat.getText ().toString ();


        HashMap<String,Object> menuinfo = new HashMap<> ();

        menuinfo.put ("YemekAdi",yemekAdiString);
        menuinfo.put ("YemekAciklama",yemekAciklamaString);
        menuinfo.put ("YemekFiyat",yemekFiyatString);


        userEmailString = firebaseUser.getEmail ();



        firebaseFirestore.collection ("Restoran").document ("Yemek Listesi").collection (userEmailString).add (menuinfo).addOnSuccessListener (new OnSuccessListener<DocumentReference> ( ) {
            @Override
            public void onSuccess (DocumentReference documentReference) {
                Toast.makeText(getApplicationContext (),"Yemek Listenize Eklendi.",Toast.LENGTH_LONG).show();

            }
        }).addOnFailureListener (new OnFailureListener ( ) {
            @Override
            public void onFailure (@NonNull Exception e) {

                Toast.makeText(getApplicationContext (),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });



    }



    }




