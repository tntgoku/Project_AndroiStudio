package com.example.onlineshopp;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.onlineshopp.FragmentLayout.Fragment_Home;
import com.example.onlineshopp.FragmentLayout.Fragment_me;
import com.example.onlineshopp.FragmentLayout.Fragment_search;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements InterFace {
    private  String TAG= MainActivity.class.getName();
    BottomNavigationView bottomNavigationView;
    FrameLayout mView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        setMapping();


        //Khi Chay app  FrameLayout se load Fragment_Home dau` tien
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_view,new Fragment_Home()).commit();
        }


        eVentCompoment();
    }


    @Override
    public void setMapping() {
        //Ánh xạ tới  BottomNavigationView
        bottomNavigationView=findViewById(R.id.btngv);

        //Ánh xạ tới FrameLayout
        mView=findViewById(R.id.framelayout_view);
    }

    @Override
    public void eVentCompoment() {
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                            //Set idItem in Menu
                        int idItem = menuItem.getItemId();
                        if (idItem == R.id.navigation_home) {
//                            Load Fragment_Home
                            loadFragment(new Fragment_Home());
                        } else if (idItem == R.id.navigation_cart) {
//                            Load Fragment_Search
                            loadFragment(new Fragment_search());
                        } else if (idItem == R.id.navigation_notifications) {
//                            Load Fragment_Notification
                            loadFragment(new Fragment_me());
                        } else if (idItem == R.id.navigation_profile) {
//                            Load Fragment_me
                            loadFragment(new Fragment_me());
                        }
                        return true;
                    }
        });
    }


//    Load FragmentLayout
    private void loadFragment(Fragment fragment){

        //Changed FrameLayout if Click  ItemMenu  on BottomNavigationView
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_view,fragment).commit();
    }

}