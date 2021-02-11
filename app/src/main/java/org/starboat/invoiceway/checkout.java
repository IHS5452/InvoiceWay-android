package org.starboat.invoiceway;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class checkout extends AppCompatActivity {
    private TextView custName, cartSV, TVdue;
    private EditText email, cashGiven;
    String cart = "";
    double Totaldue = 0.00;
    double change = 0.00;
    private SharedPreferences savedValues;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    public void onClick_Pay_with_cash(View view) {
        if (cashGiven.getText().equals("")) {
            makeToast("Enter the cash you were given");

        } else{

            try {
                change = Totaldue - Double.parseDouble(cashGiven.getText().toString());
                if (change > 0) {
                    cashGiven.setText("");
                    Totaldue = change;
                    makeToast("Money is still due. Please collect the remaning balance.");
                    TVdue.setText("Due: $" + Totaldue);

                } else {

                    savedValues = getSharedPreferences("savedValues", MODE_PRIVATE);

                    DatabaseReference myRef = database.getReference("users/"+savedValues.getString("IBONum", "")+"/customers/"+getIntent().getStringExtra("custName"));

                    myRef.child("email").setValue(email.getText().toString());
                    makeToast("Their change is $" + change);
                    Intent intent = new Intent(checkout.this, MainActivity.class);
                    startActivity(intent);
                }

            } catch (Exception e) {
                makeToast("Error: " + e);
            }
    }
    }

    public void onClick_ship_to_cust(View view) {
        savedValues = getSharedPreferences("savedValues", MODE_PRIVATE);

        DatabaseReference myRef = database.getReference("users/"+savedValues.getString("IBONum", "")+"/customers/"+getIntent().getStringExtra("custName"));

        myRef.child("email").setValue(email.getText().toString());
        Intent intent = new Intent(checkout.this, collectInfo.class);
        intent.putExtra("custName", custName.getText().toString());
        startActivity(intent);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        custName = findViewById(R.id.custName);
        TVdue = findViewById(R.id.due);
        custName.setText(getIntent().getStringExtra("custName"));
        cart = getIntent().getStringExtra("cart");
        cartSV = findViewById(R.id.cartSV);
        cartSV.setText(cart);
        Totaldue = getIntent().getDoubleExtra("due", 0);
        TVdue.setText("Due: $" + Totaldue);
        cashGiven = findViewById(R.id.cashGiven);
        email = (EditText) findViewById(R.id.emailTF);

    }
    public void makeToast(String statement) {
        Toast.makeText(this, statement, Toast.LENGTH_SHORT).show();
    }

}