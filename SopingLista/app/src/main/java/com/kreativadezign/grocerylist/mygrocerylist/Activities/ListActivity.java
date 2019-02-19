package com.kreativadezign.grocerylist.mygrocerylist.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.kreativadezign.grocerylist.mygrocerylist.Data.DatabaseHandler;
import com.kreativadezign.grocerylist.mygrocerylist.Model.Grocery;
import com.kreativadezign.grocerylist.mygrocerylist.R;
import com.kreativadezign.grocerylist.mygrocerylist.UI.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Grocery> groceryList;
    private List<Grocery> listItems;
    private List<Grocery> zbirCena;
    private DatabaseHandler db;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText groceryItem;
    private EditText quantity;
    private EditText unosCene;
    private Button saveButton;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                createPopupDialog();

            }
        });
        db = new DatabaseHandler(this);
        recyclerView = findViewById(R.id.recyclerViewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        groceryList = new ArrayList<>();
        listItems = new ArrayList<>();

        //get item from db
        groceryList = db.getAllGroceries();

        for(Grocery c : groceryList){
            Grocery grocery = new Grocery();
            grocery.setName(c.getName());
            grocery.setQuantity("Količina: " + c.getQuantity());
            grocery.setId(c.getId());
            grocery.setDateItemAdded("Dodato: " + c.getDateItemAdded());

            grocery.setCena(c.getCena());


            listItems.add(grocery);
        }



        final FloatingActionButton suma = findViewById(R.id.suma);
        suma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ListActivity.this, DetailsActivity.class);
                db.getSum();
                intent.putExtra("konacno", db.getSum());
                ListActivity.this.startActivity(intent);

            }
        });




        recyclerViewAdapter = new RecyclerViewAdapter(this, listItems);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();

    }
    private void createPopupDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup, null);
        groceryItem = view.findViewById(R.id.groceryItem);
        quantity = view.findViewById(R.id.groceryQty);
        unosCene = view.findViewById(R.id.cenaNamir);


        saveButton = view.findViewById(R.id.saveButton);




        dialogBuilder.setView(view);
        dialog = dialogBuilder.create();
        dialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if((!groceryItem.getText().toString().isEmpty()) && (!quantity.getText().toString().isEmpty())
                        && !unosCene.getText().toString().isEmpty()){
                    saveGroceryToDB(v);
                }else{
                    Snackbar.make(v, "Dodaj namirnicu, količinu i cenu!", Snackbar.LENGTH_LONG).show();
                }

            }
        });

    }

    public void saveGroceryToDB(View v){
        db = new DatabaseHandler(this);
        Grocery grocery = new Grocery();
        String newGrocery = groceryItem.getText().toString();
        String newGroceryQuantity = quantity.getText().toString();
        String newCena = unosCene.getText().toString();


        grocery.setName(newGrocery);
        grocery.setQuantity(newGroceryQuantity);
        grocery.setCena(Integer.parseInt(newCena));



        if((groceryItem != null) && (quantity != null)&&(unosCene != null)){
            db.addGrocery(grocery);

            Snackbar.make(v, "Stavka snimljena!", Snackbar.LENGTH_LONG).show();
        }

        //Save to DB

      //  dialog.dismiss();

        //Log.d("Item Added Id:", String.valueOf(db.getGroceriesCount()));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                //start new activity
               startActivity(new Intent(ListActivity.this, ListActivity.class));


            }
        }, 1000);
    }







}
