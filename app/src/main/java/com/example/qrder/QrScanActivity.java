package com.example.qrder;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QrScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{


    String ekstra;
    ZXingScannerView scannerView;
    Button barkodButton;
    EditText barkodText;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_qr_scan);


        scannerView =  findViewById (R.id.zxscan);

        barkodText = findViewById (R.id.barkodEditText);
        barkodButton = findViewById (R.id.barkodButton);

        barkodButton.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick (View v) {

               ekstra = barkodText.getText ().toString ();

                Intent ıntent = new Intent (getApplicationContext (),main_musteri_activity.class);
                ıntent.putExtra ("mail",ekstra);
                startActivity (ıntent);
                finish();
            }
        });

        Dexter.withActivity (this)
                .withPermission (Manifest.permission.CAMERA)
                .withListener (new PermissionListener ( ) {
                    @Override
                    public void onPermissionGranted (PermissionGrantedResponse response) {

                        scannerView.setResultHandler (QrScanActivity.this);
                        scannerView.setAutoFocus (true);
                        scannerView.startCamera ();

                    }

                    @Override
                    public void onPermissionDenied (PermissionDeniedResponse response) {
                        Toast.makeText (getApplicationContext (),"You must accepte permission",Toast.LENGTH_LONG).show ();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown (PermissionRequest permission, PermissionToken token) {

                    }
                })
                .check ();

    }

    /////

    @Override
    protected void onStop () {

        scannerView.stopCamera ();




        super.onStop ( );
    }



    @Override
    public void handleResult (Result rawResult) {


        ekstra = rawResult.getText ();

        //Intent ıntent = new Intent (getApplicationContext (),main_musteri_activity.class);
        Intent ıntent = new Intent (getApplicationContext (),main_musteri_activity.class);
        ıntent.putExtra ("mail",ekstra);
        startActivity (ıntent);
        finish();


    }
}
