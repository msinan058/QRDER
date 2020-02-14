package com.example.qrder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button müsteriGirisi;
    Button restorantGirisi;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        müsteriGirisi=findViewById (R.id.mainButtonMusteriGirisi);
        restorantGirisi=findViewById (R.id.mainButtonRestorantGirisi);



        müsteriGirisi.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick (View v) {
                Intent ıntent = new Intent (getApplicationContext (),singIn_singUp.class);
                ıntent.putExtra ("Tür","musteri");
                startActivity (ıntent);
            }
        });

        restorantGirisi.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick (View v) {
                Intent ıntent = new Intent (getApplicationContext (),singIn_singUp.class);
                ıntent.putExtra ("Tür","restorant");
                startActivity (ıntent);

            }
        });

    }
}
