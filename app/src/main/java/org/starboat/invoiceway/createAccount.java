package org.starboat.invoiceway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

public class createAccount extends AppCompatActivity {

    private EditText pin, iboNum, falName, email;
    private SharedPreferences savedValues;
    private FirebaseAuth mAuth;


    public void createAccount(View view) {
        if (pin.getText().toString().isEmpty()){
    makeToast("Password is empty.");
        } else if (iboNum.getText().toString().isEmpty()){
            makeToast("IBO Number is empty.");

        } else if (falName.getText().toString().isEmpty()){
            makeToast("Name is empty.");

        } else if (email.getText().toString().isEmpty()){
            makeToast("Email is empty.");

        } else {
            mAuth.createUserWithEmailAndPassword(email.getText().toString(), pin.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                System.out.println("Created user sucesfully");
                                FirebaseUser user = mAuth.getCurrentUser();
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("users/"+iboNum.getText().toString());
                                myRef.child("IBONum").setValue(iboNum.getText().toString());
                                myRef.child("fullName").setValue(falName.getText().toString());
                                myRef.child("email").setValue(email.getText().toString());

                                makeToast("Welcome to InvoiceWay, " + falName.getText().toString() + "!");

                                SharedPreferences.Editor ed = savedValues.edit();
                                ed.putString("IBONum", iboNum.getText().toString());
                                ed.commit();

                                Intent intent = new Intent(createAccount.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.
                                makeToast("Unsucesfull. Please try again");
                            }

                            // ...
                        }
                    });


        }


    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        pin = findViewById(R.id.pin);
        iboNum = findViewById(R.id.iboNum);
        falName = findViewById(R.id.name);
        email = findViewById(R.id.email);
        savedValues = getSharedPreferences("savedValues", MODE_PRIVATE);
        mAuth = FirebaseAuth.getInstance();

    }
    public void makeToast(String statement) {
        Toast.makeText(this, statement, Toast.LENGTH_SHORT).show();
    }
}