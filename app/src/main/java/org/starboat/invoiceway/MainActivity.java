package org.starboat.invoiceway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//Android UI
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.getbase.floatingactionbutton.*;

//database
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.Date;



public class MainActivity extends AppCompatActivity {

    private EditText name, SKU;
    private RadioButton he, be, pc, nu;
    public static TextView total;
    private RecyclerView cartTable;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference cartRef = database.getReference("users/");
    private SharedPreferences savedValues;
    private com.getbase.floatingactionbutton.FloatingActionButton vpo, cco, ca, li, lo;
    private FloatingActionsMenu fam;
    RecyclerView cartView;

    ArrayList<String> itemCartToString = new ArrayList<>();
    org.starboat.invoiceway.MyRecyclerViewAdapter adapter;
    String IBONUM = "";
    private String iboNumCheck = "";
    private String getSelected;
    private AdView mAdView;

    private RecyclerView RV;
    public static RecyclerView.Adapter RVA;
    private RecyclerView.LayoutManager RVLM;

    public void onClick_checkout(View view) {

    }


    public void onClick_addToCart(View view) {

        if (savedValues.getString("IBONum", "").isEmpty()) {
            makeToast("Please login first");
            Intent intent = new Intent(MainActivity.this, org.starboat.invoiceway.login.class);
            startActivity(intent);
        } else if (name.getText().equals(""))  {
            makeToast("Please enter your customer's name");
            name.requestFocus();
        } else {
            addToCart();

        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.topbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.checkout:
                checkout();
                break;
            case R.id.vpo_menu:
                gotoPastOrdersView();
                break;
            case R.id.cco_menu:
                clearOrder();
                break;
            case R.id.ca_menu:
                gotocreateAccountView();
                break;
            case R.id.li_menu:
                gotoLoginView();
                break;
            case R.id.lo_menu:
                logout();
                break;
            default:
                makeToast("Error.");
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        vpo = (FloatingActionButton) findViewById(R.id.viewPastOrders);
//        cco = (FloatingActionButton) findViewById(R.id.clearCurrentOrder);
//        ca = (FloatingActionButton) findViewById(R.id.CreateAccount);
//        li = (FloatingActionButton) findViewById(R.id.login);
//        lo = (FloatingActionButton) findViewById(R.id.logout);
//        fam = (FloatingActionsMenu) findViewById(R.id.fam);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        cartView = (RecyclerView) findViewById(R.id.due);
        name = (EditText) findViewById(R.id.custName);
        SKU = (EditText) findViewById(R.id.sku);
        savedValues = getSharedPreferences("savedValues", MODE_PRIVATE);
        total = (TextView) findViewById(R.id.total);



        //spinner.setOnItemSelectedListener(this);

        RV = findViewById(R.id.due);
        RV.setHasFixedSize(true);
        RVLM = new LinearLayoutManager(this);
        //RVA = new MyRecyclerViewAdapter(itemCart);
        RVA = new org.starboat.invoiceway.MyRecyclerViewAdapter(master.itemCart);
        RV.setLayoutManager(RVLM);
        RV.setAdapter(RVA);


    }

            public void makeToast(String statement) {
                Toast.makeText(this, statement, Toast.LENGTH_SHORT).show();
            }


            public void gotoPastOrdersView() {
                Intent intent = new Intent(MainActivity.this, Search.class);
                startActivity(intent);

            }
    public void gotocreateAccountView() {
        SharedPreferences.Editor ed = savedValues.edit();
        if (savedValues.getString("IBONum", "").isEmpty()) {
            Intent intent = new Intent(MainActivity.this, createAccount.class);
            startActivity(intent);
        } else {
            makeToast("You already have an account.");

        }
    }

    public void gotoLoginView() {
        //makeToast("Saved Value is " + savedValues.getString("IBONum", ""));
        SharedPreferences.Editor ed = savedValues.edit();
        if (savedValues.getString("IBONum", "").isEmpty()) {
            Intent intent = new Intent(MainActivity.this, login.class);
            startActivity(intent);
        } else {
            makeToast("You are already logged in.");

        }
    }

    public void logout() {
        SharedPreferences.Editor ed = savedValues.edit();
        ed.putString("IBONum", "");
        ed.commit();
        makeToast("Logout was sucesfull");
    }



    public void addToCart() {



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("amway_products/"+ SKU.getText().toString());
// Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                try {


                    itemClass item = dataSnapshot.getValue(itemClass.class);

                    master.itemCart.add(new row("$" + item.price + " - (" + item.itemName + ") - " + item.SKU));
                    master.priceCart.add(Double.parseDouble(item.price));
                    modifyCart.getSumOfArrayAndChangeTotal(master.priceCart, total);
                    System.out.println(master.priceCart);
                    itemCartToString.add("$" + item.price + " - (" + item.itemName + ") - " + item.SKU);
                    makeToast("Added " + item.itemName + " to the bill.");
                    RVA.notifyItemInserted(0);
                    RVA.notifyDataSetChanged();
                }catch (NullPointerException e) {
                    makeToast("Error: Item not found");
                } catch (Exception e) {
                    makeToast("Error: " + e);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
makeToast("Failed: " + error);
            }
        });



    }

    public void checkout() {
        DatabaseReference myRef = database.getReference("users/"+savedValues.getString("IBONum", "")+"/customers");

        Intent intent = new Intent(MainActivity.this, checkout.class);
        intent.putExtra("custName", name.getText().toString());
        String cartTogether = "";
        int i = 0;
        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());

        for (String s : itemCartToString)
        {
            cartTogether += i+1 + ") " + s + "\n";
            myRef.child(name.getText().toString()).child("orders").child("cart on " + currentDateTimeString+"/"+i).setValue(s);
            i++;
        }
        intent.putExtra("cart", cartTogether);
        intent.putExtra("due", master.totalBill);


        startActivity(intent);
    }


    public void clearOrder() {
        master.itemCart.removeAll(master.itemCart);
        master.priceCart.removeAll(master.priceCart);
        RVA.notifyDataSetChanged();
        SKU.setText("");
        name.setText("");
        total.setText("Total: $0.00");
        makeToast("Order Cleared.");

    }





}
