package org.starboat.invoiceway;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class custInfo extends AppCompatActivity {
    private SharedPreferences savedValues;
    private TextView name, email, add, itemz;
    private String iboNum;
    private String rowText = "";
    private String cartText = "";
    private String[] itemArray;
    ArrayList<row> oldCart = new ArrayList<>();
    ArrayList<String> oldCartAsString = new ArrayList<>();

    private RecyclerView RV;
    private RecyclerView.Adapter RVA;
    private RecyclerView.LayoutManager RVLM;
    int i = 0;
private String value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_info);
        savedValues = getSharedPreferences("savedValues", MODE_PRIVATE);
    name = findViewById(R.id.name);
    email = findViewById(R.id.email);
    add = findViewById(R.id.shipadd);
     itemz = (TextView) findViewById(R.id.items);




    iboNum = savedValues.getString("IBONum", "");
    name.setText(getIntent().getStringExtra("searchCust"));
rowText = getIntent().getStringExtra("rowContent");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users/"+iboNum+"/customers/" + name.getText().toString().toUpperCase() + "/orders/" + rowText);
        DatabaseReference secRef = database.getReference("users/"+iboNum+"/customers/" + name.getText().toString().toUpperCase());

// Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    try {



                        for (int z = 0; z <= postSnapshot.getChildrenCount() - 1; z++) {

                            cartText += postSnapshot.getValue(String.class) + "\n";
                            z++;
                        }

                        itemz.setText(cartText);


                        RVA.notifyItemInserted(0);
                        RVA.notifyDataSetChanged();
                    }catch (NullPointerException e) {
                        makeToast("Error: " + e);
                    } catch (Exception e) {
                        makeToast("Error: " + e);
                    }

                }
//                for (String s : oldCartAsString)
//                {
//                    oldCart.add(new row(s));
//                    makeToast(s);
//                    i++;
//                }
//                RVA.notifyItemInserted(0);
//                RVA.notifyDataSetChanged();






            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value


            }
        });

        secRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
//                    try {
                            custInfoClass cis = dataSnapshot.getValue(custInfoClass.class);

                        email.setText(cis.email);
                        add.setText(cis.address);

//                    }catch (NullPointerException e) {
//                        makeToast("Error: " + e);
//                    } catch (Exception e) {
//                        makeToast("Error: " + e);
//                    }

//                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value


            }
        });




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    public void makeToast(String statement) {
        Toast.makeText(this, statement, Toast.LENGTH_SHORT).show();
    }

}