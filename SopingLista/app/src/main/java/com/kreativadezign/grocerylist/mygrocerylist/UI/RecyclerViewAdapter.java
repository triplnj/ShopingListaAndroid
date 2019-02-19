package com.kreativadezign.grocerylist.mygrocerylist.UI;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kreativadezign.grocerylist.mygrocerylist.Activities.DetailsActivity;
import com.kreativadezign.grocerylist.mygrocerylist.Data.DatabaseHandler;
import com.kreativadezign.grocerylist.mygrocerylist.Model.Grocery;
import com.kreativadezign.grocerylist.mygrocerylist.R;


import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by cyberdog on 4/15/2018.
 */

public class RecyclerViewAdapter  extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    public Context context;
    public List<Grocery> groceryItems;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;
    private LayoutInflater inflater;


    public RecyclerViewAdapter(Context context, List<Grocery> groceryItems) {
        this.context = context;
        this.groceryItems = groceryItems;
    }


    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new ViewHolder(view, context);

}


    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        Grocery grocery = groceryItems.get(position);

        holder.groceryItemName.setText(grocery.getName());
        holder.quantity.setText(grocery.getQuantity());
        holder.dateAdded.setText(grocery.getDateItemAdded());

        holder.unesenaCena.setText(String.valueOf(grocery.getCena()));



    }

    @Override
    public int getItemCount() {
        return groceryItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView groceryItemName;
        public TextView quantity;
        public TextView dateAdded;
        public Button editButton;
        public Button deleteButton;
        public TextView unesenaCena;
        public int id;




        public ViewHolder(View view, Context ctx) {
            super(view);
            context = ctx;


            groceryItemName = view.findViewById(R.id.name);
            quantity = view.findViewById(R.id.quantity);
            dateAdded = view.findViewById(R.id.dateAdded);
            unesenaCena = view.findViewById(R.id.cenaNamirnice);

            editButton = view.findViewById(R.id.editButton);
            deleteButton = view.findViewById(R.id.deleteButton);
            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);

//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //idi na sledeci ekran DetailsActivity
//                    int position = getAdapterPosition();
//                    Grocery grocery = groceryItems.get(position);
//                    Intent intent = new Intent(context, DetailsActivity.class);
//                    intent.putExtra("name", grocery.getName());
//                    intent.putExtra("quantity", grocery.getQuantity());
//                    intent.putExtra("id", grocery.getId());
//                    intent.putExtra("date", grocery.getDateItemAdded());
//                    intent.putExtra("cena", grocery.getCena());
//                    int zbir = (grocery.getCena());
//
//                    intent.putExtra("ukupnost", zbir);
//                    context.startActivity(intent);
//
////
//               }
//           });
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.editButton:
                    int position = getAdapterPosition();
                    Grocery grocery = groceryItems.get(position);
                    editItem(grocery);


                    break;
                case R.id.deleteButton:
                    position = getAdapterPosition();
                    grocery = groceryItems.get(position);
                    deleteItem(grocery.getId());

                    break;

            }

        }



        private void deleteItem (final int id){
            //kreiranje AlertDialoga
            alertDialogBuilder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.confirmation_dialog, null);

            Button noButton = view.findViewById(R.id.noButton);
            Button yesButton = view.findViewById(R.id.yesButton);

            alertDialogBuilder.setView(view);
            dialog = alertDialogBuilder.create();
            dialog.show();

            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //delete ajtem
                    DatabaseHandler db = new DatabaseHandler(context);
                    //brisanje
                    db.deleteGrocery(id);
                    groceryItems.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());

                    dialog.dismiss();

                }
            });

        }



        private void editItem(final Grocery grocery) {
            alertDialogBuilder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            final View view = inflater.inflate(R.layout.popup, null);

            final EditText groceryItem = view.findViewById(R.id.groceryItem);
            final EditText quantity = view.findViewById(R.id.groceryQty);
            final EditText unosCene = view.findViewById(R.id.cenaNamir);
            final TextView naslov = view.findViewById(R.id.naslov);


            naslov.setText("Uredi listu");
            Button saveButton = view.findViewById(R.id.saveButton);




            alertDialogBuilder.setView(view);
            dialog = alertDialogBuilder.create();
            dialog.show();

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHandler db = new DatabaseHandler(context);

                    //Update ajtem
                    grocery.setName(groceryItem.getText().toString());
                    grocery.setQuantity(quantity.getText().toString());
                    if(!unosCene.getText().toString().equals("")&& unosCene !=null) {
                        grocery.setCena(Integer.parseInt(unosCene.getText().toString()));
                    }





                    if((!groceryItem.getText().toString().isEmpty() && !quantity.getText().toString().
                            isEmpty()) && (!unosCene.getText().toString().equals("") && unosCene != null))  {
                        db.updateGrocery(grocery);
                        Snackbar.make(view, "Stavka snimljena!", Snackbar.LENGTH_LONG).show();


                        grocery.setQuantity("Količina: " + grocery.getQuantity());
                        grocery.setCena(grocery.getCena());

                        notifyItemChanged(getAdapterPosition(), grocery);
                        dialog.dismiss();

                    }else{

                        Snackbar.make(view, "Dodaj namirnicu, količinu i cenu!", Snackbar.LENGTH_LONG).show();
                    }


                }
            });

        }




    }



}
