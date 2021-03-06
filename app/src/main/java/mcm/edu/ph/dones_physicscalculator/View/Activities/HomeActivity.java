package mcm.edu.ph.dones_physicscalculator.View.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import mcm.edu.ph.dones_physicscalculator.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); //hide the action bar
        setContentView(R.layout.activity_home);
    }

    //public void goToAlgebra(View v){
        //Intent i = new Intent(HomeActivity.this, MainActivity.class);
        //i.putExtra("formulas", "algebra");
        //finish();
        //startActivity(i);
        //overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    //}

    public void goToGeometry(View v){
        Intent i = new Intent(HomeActivity.this, MainActivity.class);
        i.putExtra("formulas", "geometry");
        finish();
        startActivity(i);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void goToPhysics(View v){
        Intent i = new Intent(HomeActivity.this, MainActivity.class);
        i.putExtra("formulas", "physics");
        finish();
        startActivity(i);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


}