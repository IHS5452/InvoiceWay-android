package org.starboat.invoiceway;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class collectInfo extends AppCompatActivity {
    private SharedPreferences savedValues;
    private String custName = "";
    private EditText add1, add2, city, state, zip, card, cvv, exp;

    public void onClick_complete(View view) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users/"+savedValues.getString("IBONum", "")+"/customers/"+custName);
        myRef.child("address").setValue(add1.getText().toString() + "," + add2.getText().toString() + "," + city.getText().toString() + "," +
                state.getText().toString() + ", " + zip.getText().toString());
//        myRef.child("cardNum").setValue(card.getText().toString());
//        myRef.child("CVV").setValue(cvv.getText().toString());
//        myRef.child("EXP").setValue(exp.getText().toString());

makeToast("Sucess! Info saved to pull up later!");
        Intent intent = new Intent(collectInfo.this, MainActivity.class);
        startActivity(intent);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_info);
        savedValues = getSharedPreferences("savedValues", MODE_PRIVATE);
        custName = getIntent().getStringExtra("custName");
        add1 = (EditText) findViewById(R.id.address1);
        add2= (EditText) findViewById(R.id.address2);
        city = (EditText) findViewById(R.id.city);
        state= (EditText) findViewById(R.id.stateTV);
        zip=  (EditText) findViewById(R.id.zipTV);
//        card= findViewById(R.id.card);
//        cvv= findViewById(R.id.cvv);
//        exp= findViewById(R.id.exp);


    }

    public void makeToast(String statement) {
        Toast.makeText(this, statement, Toast.LENGTH_SHORT).show();
    }
}