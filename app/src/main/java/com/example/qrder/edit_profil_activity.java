package com.example.qrder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class edit_profil_activity extends AppCompatActivity {

    TextView userEmail;
    //fotoraf secmek icin alttaki 3 gerekli
    ImageView profil_photo;
    Uri imageDataUri;
    Bitmap selectedImage;

    public FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;

    DocumentReference docRef;


    private   FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    String userEmailString;
    String ppUrlstring;

    EditText userName;
    EditText userSurname;
    EditText userPhone;

    Button saveButton;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_edit_profil_activity);

        //user infoss

        userEmail=findViewById (R.id.kullanıcıEpostaText);

        userName =findViewById (R.id.kullanıcıAdıText);
        userSurname = findViewById (R.id.kullanıcıSoyadıText);
        userPhone = findViewById (R.id.kullanıcıTelefonText);

        saveButton=findViewById (R.id.userInfoSaveButton);

        firebaseAuth = FirebaseAuth.getInstance ();
         firebaseUser =firebaseAuth.getCurrentUser ();


        Intent ıntent=getIntent ();
        String temp =ıntent.getStringExtra ("mail");
        if (temp!=null) {
            userEmailString = ıntent.getStringExtra ("mail");

           // saveButton.setVisibility (View.INVISIBLE);
        }
        else
        userEmailString = firebaseUser.getEmail ();




        userEmail.setText (userEmailString);

        profil_photo=findViewById (R.id.kullanıcıPhotoImageView);


        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();





                    // profilFotoCekme ();



       String location = "Musteri/ProfilPhoto/"+userEmailString+"/Photo";
        if (location!=null) {

            StorageReference ppUriRef = FirebaseStorage.getInstance ().getReference (location);

            ppUriRef.getDownloadUrl ().addOnSuccessListener (new OnSuccessListener<Uri> ( ) {
                @Override
                public void onSuccess (Uri uri) {

                    ppUrlstring = uri.toString ();

                    Picasso.get().load(ppUrlstring).into(profil_photo);

                }
            });

        }



                   //////////////////////////////////////





        try {


           String temp2 =ıntent.getStringExtra ("mail");
            if (temp2!=null){
                userEmailString= ıntent.getStringExtra ("mail");
                saveButton.setVisibility (View.INVISIBLE);
            }
            else
                userEmailString = firebaseUser.getEmail ();



            DocumentReference docRef = firebaseFirestore.collection("Musteri")
                    .document(userEmailString);


                docRef.addSnapshotListener (new EventListener<DocumentSnapshot> ( ) {
                    @Override
                    public void onEvent (@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                        if (e!=null){

                            Toast.makeText (getApplicationContext (),e.getLocalizedMessage (),Toast.LENGTH_LONG).show ();

                        }


                        if (documentSnapshot!=null){

                            Map<String,Object> data =  documentSnapshot.getData ();

                            if (data!=null){
                                String isimFire = (String) data.get("İsim");
                                String soyisimFire = (String) data.get("Soyisim");
                                String telefonFire = (String) data.get("Telefon");



                                userName.setText (isimFire);
                                userSurname.setText (soyisimFire);
                                userPhone.setText (telefonFire);

                            }



                        }

                    }
                });



        }
        catch (Exception e) {


            Toast.makeText (getApplicationContext (),e.getLocalizedMessage (),Toast.LENGTH_LONG).show ();

        }






    }





    public void getFromFirebase () {
        Intent ıntent=getIntent ();
        String temp2 =ıntent.getStringExtra ("mail");
        if (temp2!=null){
            userEmailString= ıntent.getStringExtra ("mail");
            saveButton.setVisibility (View.INVISIBLE);
        }
        else
            userEmailString = firebaseUser.getEmail ();



         docRef = firebaseFirestore.collection("Musteri")
                .document(userEmailString);


        docRef.addSnapshotListener (new EventListener<DocumentSnapshot> ( ) {
            @Override
            public void onEvent (@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                if (e!=null){

                    Toast.makeText (getApplicationContext (),e.getLocalizedMessage (),Toast.LENGTH_LONG).show ();

                }


                if (documentSnapshot!=null){

                    Map<String,Object> data =  documentSnapshot.getData ();


                        String isimFire = (String) data.get("İsim");
                        String soyisimFire = (String) data.get("Soyisim");
                        String telefonFire = (String) data.get("Telefon");



                        userName.setText (isimFire);
                        userSurname.setText (soyisimFire);
                        userPhone.setText (telefonFire);





                }

            }
        });


    }


    public  void  userinfosave(View view){

        String isimString = userName.getText ().toString ();
        String soyisimString = userSurname.getText ().toString ();
        String telefonString = userPhone.getText ().toString ();


        HashMap<String,Object> userinfo = new HashMap<> ();

        userinfo.put ("İsim",isimString);
        userinfo.put ("Soyisim",soyisimString);
        userinfo.put ("Telefon",telefonString);




        userEmailString = firebaseUser.getEmail ();





        if(userinfo != null){



            firebaseFirestore.collection("Musteri").document (userEmailString).set (userinfo).addOnSuccessListener (new OnSuccessListener<Void> ( ) {
                @Override
                public void onSuccess (Void aVoid) {

                    Toast.makeText(getApplicationContext (),"Profil bilgileriniz Güncellendi.",Toast.LENGTH_LONG).show();


                    Intent ıntent = new Intent (getApplicationContext (),main_musteri_activity.class);
                    startActivity (ıntent);



                }
            }).addOnFailureListener (new OnFailureListener ( ) {
                @Override
                public void onFailure (@NonNull Exception e) {

                    Toast.makeText(getApplicationContext (),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();

                }
            });


        }

        getFromFirebase ();


        if (imageDataUri !=null){

            UUID uuıd = UUID.randomUUID ();

            userEmailString = firebaseUser.getEmail ();

            storageReference.child ("Musteri").child ("ProfilPhoto").child (userEmailString).child ("Photo").putFile (imageDataUri).addOnSuccessListener (new OnSuccessListener<UploadTask.TaskSnapshot> ( ) {
                @Override
                public void onSuccess (UploadTask.TaskSnapshot taskSnapshot) {

                    //Yüklenen resmin url desini Firebase den alma

                    String location = "Musteri/ProfilPhoto/"+userEmailString+"/Photo";

                     StorageReference ppUriRef = FirebaseStorage.getInstance ().getReference (location);

                    ppUriRef.getDownloadUrl ().addOnSuccessListener (new OnSuccessListener<Uri> ( ) {
                        @Override
                        public void onSuccess (Uri uri) {

                            ppUrlstring = uri.toString ();

                            Picasso.get().load(ppUrlstring).into(profil_photo);

                        }
                    });


                }
            }).addOnFailureListener (new OnFailureListener ( ) {
                @Override
                public void onFailure (@NonNull Exception e) {

                    Toast.makeText(edit_profil_activity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();

                }
            });


        }

    }

    public void UploadPhoto(View view){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},1);
        } else {
            Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intentToGallery,2);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentToGallery,2);
            }
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 2 && resultCode == RESULT_OK && data != null ) {

            imageDataUri = data.getData();

            try {

                if (Build.VERSION.SDK_INT >= 28) {
                    ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(),imageDataUri);
                    selectedImage = ImageDecoder.decodeBitmap(source);
                    profil_photo.setImageBitmap(selectedImage);
                } else {
                    selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageDataUri);
                    profil_photo.setImageBitmap(selectedImage);
                }


            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText (getApplicationContext (),"catch",Toast.LENGTH_LONG).show ();
            }


        }
        else {

            Toast.makeText (getApplicationContext (),"req2 not ok",Toast.LENGTH_LONG).show ();
        }



        super.onActivityResult(requestCode, resultCode, data);
    }





}
