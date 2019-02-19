package com.kreativadezign.grocerylist.mygrocerylist.Activities;


import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.kreativadezign.grocerylist.mygrocerylist.Model.Grocery;
import com.kreativadezign.grocerylist.mygrocerylist.R;
import java.util.List;


public class DetailsActivity extends AppCompatActivity{


    protected int groceryId;


    private TextView ukupnaCena;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        ukupnaCena = findViewById(R.id.ukupnaCena);






        Bundle bundle = getIntent().getExtras();
        if(bundle != null){

            ukupnaCena.setText(String .valueOf(bundle.getInt("konacno")+ ",00"));
//            StringBuilder sb = new StringBuilder();
//            String nule = ",00";
//            sb.append(nule);



            groceryId = bundle.getInt("id");

        }


    }









}
