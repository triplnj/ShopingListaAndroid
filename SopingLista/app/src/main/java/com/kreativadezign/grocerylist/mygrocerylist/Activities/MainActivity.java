package com.kreativadezign.grocerylist.mygrocerylist.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kreativadezign.grocerylist.mygrocerylist.Data.DatabaseHandler;
import com.kreativadezign.grocerylist.mygrocerylist.Model.Grocery;
import com.kreativadezign.grocerylist.mygrocerylist.R;

public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText groceryItem;
    private EditText quantity;
    private Button saveButton;
    private EditText unoseCene;
    private DatabaseHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        db = new DatabaseHandler(this);
        byPassActitvity();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//               Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                createPopupDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void createPopupDialog(){

        dialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup,null);
        groceryItem = view.findViewById(R.id.groceryItem);
        quantity = view.findViewById(R.id.groceryQty);
        saveButton = view.findViewById(R.id.saveButton);
        unoseCene = view.findViewById(R.id.cenaNamir);


        dialogBuilder.setView(view);
        dialog = dialogBuilder.create();
        dialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if((!groceryItem.getText().toString().isEmpty()) && (!quantity.getText().toString().isEmpty())
                        && (!unoseCene.getText().toString().isEmpty())) {
                    saveGroceryToDB(v);
                }else{
                    Snackbar.make(v, "Dodaj namirnicu, količinu i cenu!", Snackbar.LENGTH_LONG).show();
                }

            }
        });

    }
    public void saveGroceryToDB(View v){

        Grocery grocery = new Grocery();
        String newGrocery = groceryItem.getText().toString();
        String newGroceryQuantity = quantity.getText().toString();
        String newCena = unoseCene.getText().toString();

        grocery.setName(newGrocery);
        grocery.setQuantity(newGroceryQuantity);
        grocery.setCena(Integer.parseInt(newCena));







        //Save to DB
        db.addGrocery(grocery);

       Snackbar.make(v, "Stavka sačuvana!", Snackbar.LENGTH_LONG).show();

        //Log.d("Item Added Id:", String.valueOf(db.getGroceriesCount()));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                //start new activity
                startActivity(new Intent(MainActivity.this, ListActivity.class));

            }
        }, 1000);
    }



    public void byPassActitvity(){
        //provera da li je db prazna; ako nije onda idemo na LIstActivity i prikazujemo dodate stavke

        if(db.getGroceriesCount()>0){
            startActivity(new Intent(MainActivity.this, ListActivity.class));
            finish();
        }
    }
}
