package com.pravin.barcodeapp.firebaseotpverification_android.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pravin.barcodeapp.firebaseotpverification_android.R;
import com.pravin.barcodeapp.firebaseotpverification_android.fragments.basketFragment;
import com.pravin.barcodeapp.firebaseotpverification_android.fragments.billsFragment;
import com.pravin.barcodeapp.firebaseotpverification_android.fragments.chatFragment;
import com.pravin.barcodeapp.firebaseotpverification_android.fragments.homefragment;
import com.pravin.barcodeapp.firebaseotpverification_android.fragments.subscriptionFragment;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    String TAG = "**MainActivity";
    Toolbar toolbar;
    FragmentContainerView fragmentContainerView;
    BottomNavigationView bottom_navigation;
    ImageView navigationdrawericon,walletIcon;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Objects.requireNonNull(getSupportActionBar()).hide();


        toolbar = findViewById(R.id.toolbar);
        navigationdrawericon = findViewById(R.id.navigationdrawericon);
        walletIcon = findViewById(R.id.walletIcon);

        fragmentContainerView = findViewById(R.id.fragmentContainerView);
        bottom_navigation = findViewById(R.id.bottom_navigation);

        navigationdrawericon.setOnClickListener(v -> {
            Toast.makeText(this, "Navigation Drawer", Toast.LENGTH_SHORT).show();
        });
        walletIcon.setOnClickListener(v -> {
            Toast.makeText(this, "Wallet icon", Toast.LENGTH_SHORT).show();
        });

        bottom_navigation.setOnItemReselectedListener(item -> {

        });

        bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                switch (item.getTitle().toString()){
                        case "Home":
                            ft.replace(R.id.fragmentContainerView, new homefragment(), "NewFragmentTag");
                        break;
                        case "Subscription":
                            ft.replace(R.id.fragmentContainerView, new subscriptionFragment(), "NewFragmentTag");
                            break;
                        case "Bill":
                            ft.replace(R.id.fragmentContainerView, new billsFragment(), "NewFragmentTag");
                            break;
                        case "Basket":
                            ft.replace(R.id.fragmentContainerView, new basketFragment(), "NewFragmentTag");
                            break;
                        case "Chat":
                            ft.replace(R.id.fragmentContainerView, new chatFragment(), "NewFragmentTag");
                            break;

                }
                ft.commit();
                return true;
            }
        });
    }


}