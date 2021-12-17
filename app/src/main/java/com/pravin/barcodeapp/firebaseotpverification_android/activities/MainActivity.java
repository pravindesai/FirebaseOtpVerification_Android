package com.pravin.barcodeapp.firebaseotpverification_android.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
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
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    FragmentContainerView fragmentContainerView;
    BottomNavigationView bottom_navigation;
    ImageView navigationdrawericon,walletIcon;

    NavigationView nav_view;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        initUI();

//        setSupportActionBar(toolbar);
//        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open,R.string.close);
//        drawerLayout.addDrawerListener(actionBarDrawerToggle);
//        actionBarDrawerToggle.syncState();


        navigationdrawericon.setOnClickListener(v -> {
            drawerLayout.openDrawer(Gravity.LEFT);
        });

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getTitle().toString()){
                    case "Logout":
                            if (firebaseUser!=null){
                                FirebaseAuth.getInstance().signOut();
                                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(loginIntent);
                                loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                                loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                loginIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                finishAffinity();
                            }
                        break;
                }

                return true;
            }
        });

        walletIcon.setOnClickListener(v -> {
            Toast.makeText(this, "Wallet icon", Toast.LENGTH_SHORT).show();
        });

        bottom_navigation.setOnItemReselectedListener(item -> {
        });
        bottom_navigation.setOnItemSelectedListener(item -> {
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
        });
    }

    private void initUI() {
        toolbar = findViewById(R.id.toolbar);
        navigationdrawericon = findViewById(R.id.navigationdrawericon);
        walletIcon = findViewById(R.id.walletIcon);
        fragmentContainerView = findViewById(R.id.fragmentContainerView);
        bottom_navigation = findViewById(R.id.bottom_navigation);

        nav_view = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawerLayout);
    }


}