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
import com.google.firebase.database.*;

public class login extends AppCompatActivity {

private EditText iboNum, pin, email;
private String fullName = "";

    private SharedPreferences savedValues;
    private FirebaseAuth mAuth;

//
//    public void onLogin(View view) {
//        // Write a message to the database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference iboRef = database.getReference("users/"+iboNum.getText().toString());
//
//        iboRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                iboClass ibo = dataSnapshot.getValue(iboClass.class);
//
//                System.out.println("IBONum_Input: " + ibo.IBONum);
//                System.out.println("PIN_Input: " + ibo.pin);
//                fullName = ibo.fullName;
////                checkIBONum(ibo.IBONum);
////                checkPass(ibo.pin);
//                System.out.println("CheckIBO: " + CheckIBO + " CheckPIN: " + CheckPIN);
//
//
//                if (ibo.IBONum.equals(iboNum.getText().toString())) {
//                    System.out.println("Correct IBONum");
//                    if (ibo.pin.equals(pin.getText().toString())) {
//                        System.out.println("Correct PIN");
//                        startNewActivity();
//
//                    } else {
//                        makeToast("Wrong PIN Number. Try again");
//                    }
//                    } else{
//                        makeToast("Wrong IBO Number. Try again");
//
//                    }
//
//
//
////                if ((CheckPIN = true) && (CheckIBO = true)) {
////                   startNewActivity();
////                } else if ((CheckPIN = false) && (CheckIBO = true)) {
////                    makeToast("Your PIN is incorect. Please try again.");
////                }else if ((CheckPIN = true) && (CheckIBO = false)) {
////                    makeToast("Your IBO Number is incorect. Please try again.");
////                } else {
////                    makeToast("Both your PIN and IBO Number are incorect. Please try again.");
////                }
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Toast.makeText(login.this, "Error: " + error, Toast.LENGTH_SHORT).show();            }
//        });
//
//
//
//
//
//
//    }


    public void onLogin(View view) {
        mAuth.signInWithEmailAndPassword(email.getText().toString(), pin.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

sIBON();
                            makeToast("Sign in sucesfull");
                        } else {
                            // If sign in fails, display a message to the user.
                           makeToast("Sign in unsucesfull. Please check your IBO Number, Email, or password again.");
                        }

                        // ...
                    }
                });
    }



    private void startNewActivity() {

        Intent intent = new Intent(login.this, MainActivity.class);
        startActivity(intent);
    }


    public void makeToast(String statement) {
        Toast.makeText(this, statement, Toast.LENGTH_SHORT).show();
    }

//public void checkIBONum(String iboNumber) {
//    if (iboNumber == iboNum.getText().toString()) {
//        System.out.println("Correct IBONum");
//         CheckIBO = true;
//    } else {
//        makeToast("Wrong IBO Number. Try again");
//         CheckIBO = false;
//    }
//}

//    public void checkPass(String pinNum) {
//        if (pinNum == pin.getText().toString()) {
//            System.out.println("Correct PIN");
//            CheckPIN = true;
//        } else {
//            makeToast("Wrong PIN Number. Try again");
//            CheckPIN = false;
//        }
//    }


    /*




 */





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        iboNum = (EditText) findViewById(R.id.ibo);
        pin = (EditText) findViewById(R.id.pin);
        email = (EditText) findViewById(R.id.email);
        savedValues = getSharedPreferences("savedValues",MODE_PRIVATE);


        mAuth = FirebaseAuth.getInstance();


    }

public void sIBON() {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference iboRef = database.getReference("users/" + iboNum.getText().toString());

    iboRef.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            // This method is called once with the initial value and again
            // whenever data at this location is updated.
            iboClass ibo = dataSnapshot.getValue(iboClass.class);

            System.out.println("IBONum_Input: " + ibo.IBONum);
            fullName = ibo.fullName;

            SharedPreferences.Editor ed = savedValues.edit();
            makeToast("Welcome back, " + fullName);
            ed.putString("IBONum", ibo.IBONum);
            ed.commit();
    startNewActivity();



        }

        @Override
        public void onCancelled(DatabaseError error) {
            // Failed to read value
            Toast.makeText(login.this, "Error: " + error, Toast.LENGTH_SHORT).show();
        }
    });
}

}

