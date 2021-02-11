package org.starboat.invoiceway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Search extends AppCompatActivity implements SearchRecyclerViewAdapter.onRowListener{
    private RecyclerView RV;
    private RecyclerView.Adapter RVA;
    private RecyclerView.LayoutManager RVLM;
    ArrayList<row> peopleCart = new ArrayList<>();
    private SharedPreferences savedValues;
    private EditText name;


    public void onClick_Search(View view) {
        addToCart();

    }

    public void addToCart() {
        peopleCart.removeAll(peopleCart);
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = database.child("users/"+savedValues.getString("IBONum", "") + "/customers/"+ name.getText().toString()+"/orders");
// Read from the database
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    try {





                        String value = postSnapshot.getKey();
                        peopleCart.add(new row(value));
                        System.out.println(peopleCart);

                        RVA.notifyItemInserted(0);
                        RVA.notifyDataSetChanged();
                    }catch (NullPointerException e) {
                        makeToast("Error: Item not found");
                    } catch (Exception e) {
                        makeToast("Error: " + e);
                    }

                }
                makeToast("Search complete. Found " + peopleCart.size() + " transactions.");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                makeToast("Failed to read value: "  + error.toException());

            }



        });



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        buildRV();

        savedValues = getSharedPreferences("savedValues", MODE_PRIVATE);
        name = (EditText) findViewById(R.id.custName);



    }

    private void buildRV() {
        RV = findViewById(R.id.searchRV);
        RV.setHasFixedSize(true);
        RVLM = new LinearLayoutManager(this);
        RVA = new SearchRecyclerViewAdapter(peopleCart, this);
        RV.setLayoutManager(RVLM);
        RV.setAdapter(RVA);


    }

    public void makeToast(String statement) {
        Toast.makeText(this, statement, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onRowClicked(int position) {
        Intent intent = new Intent(Search.this, custInfo.class);
        row itemOnList = peopleCart.get(position);

        intent.putExtra("searchCust", name.getText().toString());
        intent.putExtra("rowContent", itemOnList.getmText());

        startActivity(intent);

     }
}







