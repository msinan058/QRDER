package com.example.qrder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;



public class main_musteri_activity extends AppCompatActivity {




    FloatingActionButton floatingActionButton;

    ConstraintLayout constraintLayout;


    TextView uyariText;
    String getTagYemekAdi;
    String getTagYemekFiyat;

    String getTagToplam;

    ArrayList<String> sepetliste;


    private FirebaseAuth firebaseAuth;
    String mailAdresi;
    private FirebaseFirestore firebaseFirestore;

    ArrayList<String> yemekAdiFromFb;
    ArrayList<String> yemekAciklamaFromFb;
    ArrayList<String> yemekFiyatFromFb;
    adapter_main_musteri adapter_main_musteri;









    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main_musteri_activity);



       constraintLayout = findViewById (R.id.consLayout_mm);







       floatingActionButton = findViewById (R.id.fabMenu);


       floatingActionButton.setOnClickListener (new View.OnClickListener ( ) {
           @Override
           public void onClick (View v) {

               constraintLayout.removeView (uyariText);

               startActivity (new Intent (getApplicationContext (),QrScanActivity.class));

           }
       });

        uyariText= findViewById (R.id.UyariTv);

        firebaseAuth=FirebaseAuth.getInstance ();
        firebaseFirestore = FirebaseFirestore.getInstance();

        yemekAciklamaFromFb = new ArrayList<>();
        yemekAdiFromFb = new ArrayList<> ();
        yemekFiyatFromFb = new ArrayList<> ();

        sepetliste = new ArrayList<> ();


        Intent ıntent=getIntent ();
        mailAdresi= ıntent.getStringExtra ("mail");

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager (this));

        adapter_main_musteri = new adapter_main_musteri (yemekAdiFromFb,yemekAciklamaFromFb,yemekFiyatFromFb);

       recyclerView.setAdapter (adapter_main_musteri);

        getDataFromFirestore ();



        //constraintLayout.setBackground (Color.parseColor ());


    }



    public void getDataFromFirestore() {

        if (mailAdresi!=null) {

            constraintLayout.removeView (uyariText);



            CollectionReference collectionReference = firebaseFirestore.collection ("Restoran").document ("Yemek Listesi").collection (mailAdresi);

            if (collectionReference != null) {

                collectionReference.addSnapshotListener (new EventListener<QuerySnapshot> ( ) {
                    @Override
                    public void onEvent (@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                        if (e != null) {
                            Toast.makeText (main_musteri_activity.this, e.getLocalizedMessage ( ), Toast.LENGTH_LONG).show ( );
                        }

                        if (queryDocumentSnapshots != null) {

                            for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments ( )) {

                                Map<String, Object> data = snapshot.getData ( );

                                if (data != null) {
                                    //Casting
                                    String yemekAdiString = (String) data.get ("YemekAdi");
                                    String yemekAciklamaString = (String) data.get ("YemekAciklama");
                                    String yemekFiyatString = (String) data.get ("YemekFiyat");

                                     yemekAdiFromFb.add ( yemekAdiString);
                                    yemekAciklamaFromFb.add (yemekAciklamaString);
                                   yemekFiyatFromFb.add (yemekFiyatString);

                                    System.out.println ("yemek liste" + yemekAdiString);

                                     adapter_main_musteri.notifyDataSetChanged ( );
                                }

                            }


                        }

                    }
                });


            }
        }

    }



    ////////////////////menü ayarları////////////
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {

        MenuInflater menuInflater = getMenuInflater ();
        menuInflater.inflate (R.menu.musteri_menu,menu);

        return super.onCreateOptionsMenu (menu);
    }


    @Override
    public boolean onOptionsItemSelected (@NonNull MenuItem item) {

        if (item.getItemId ()==R.id.edit_profil){
            Intent ıntent = new Intent (getApplicationContext (),edit_profil_activity.class);
            startActivity (ıntent);

        }

        if (item.getItemId ()==R.id.cıkıs_yap){

            firebaseAuth.signOut ();
            Intent ıntent = new Intent (getApplicationContext (),MainActivity.class);
            startActivity (ıntent);
            finish ();

        }

        if (item.getItemId ()==R.id.barkodtarat){


            constraintLayout.removeView (uyariText);




            startActivity (new Intent (getApplicationContext (),QrScanActivity.class));






        }

        if (item.getItemId ()==R.id.Sepetim){

            if (sepetliste.isEmpty ())
            Toast.makeText (getApplicationContext (),"sepet bos",Toast.LENGTH_SHORT).show ();
                else {
                startActivity (new Intent (getApplicationContext ( ), Sepetim.class));

            }

        }




        return super.onOptionsItemSelected (item);
    }




    public void tagdeneme (View view) {

        getTagYemekAdi = (String) view.getTag ();



            if (sepetliste.contains (getTagYemekAdi)){
                    Toast.makeText (getApplicationContext (),"Sepetten Çıkarıldı",Toast.LENGTH_SHORT).show ();
                    sepetliste.remove (getTagYemekAdi);
                view.setBackgroundColor (Color.parseColor ("#039BE5"));
            }
            else {
                sepetliste.add (getTagYemekAdi);
                Toast.makeText (getApplicationContext (),"Sepete eklendi",Toast.LENGTH_SHORT).show ();
                view.setBackgroundColor (Color.parseColor ("#D1FF9800"));
            }









    }

    public void SepetUlustur (View view) {

        if (sepetliste.isEmpty ()){


            Toast.makeText (getApplicationContext (),"sepet bos",Toast.LENGTH_SHORT).show ();
          }
        else{

            Intent ıntent =new Intent (getApplicationContext (),Sepetim.class);
            ıntent.putStringArrayListExtra ("Sepet",sepetliste);
            startActivity (ıntent);


        }




    }






    @Override
    protected void onDestroy () {

      //  sepetliste.removeAll (sepetliste);

        super.onDestroy ( );
    }


}
