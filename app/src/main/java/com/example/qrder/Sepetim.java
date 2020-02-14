package com.example.qrder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class Sepetim extends AppCompatActivity {

    ArrayList<String> gelenSepet;
    ListView listView;
   // String[] gelensepet;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_sepetim);

        gelenSepet = new ArrayList<> ();



        gelenSepet = getIntent ().getExtras ().getStringArrayList ("Sepet");



        listView=findViewById (R.id.listView);
        ArrayAdapter arrayAdapter = new ArrayAdapter (getApplicationContext (),android.R.layout.simple_list_item_1,gelenSepet);

        listView.setAdapter (arrayAdapter);



       // System.out.println ("sepet eleman" +gelensepet[0]+ "  ikici " + gelensepet[1]  );



    }
}
