package mcm.edu.ph.dones_physicscalculator.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.MenuCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import mcm.edu.ph.dones_physicscalculator.R;
import mcm.edu.ph.dones_physicscalculator.View.Fragments.GasFragment;
import mcm.edu.ph.dones_physicscalculator.View.Fragments.KEFragment;
import mcm.edu.ph.dones_physicscalculator.View.Fragments.PEFragment;
import mcm.edu.ph.dones_physicscalculator.View.Fragments.SDTFragment;
import mcm.edu.ph.dones_physicscalculator.View.Fragments.VolumeFragment;
import mcm.edu.ph.dones_physicscalculator.databinding.ActivityMainBinding;
import mcm.edu.ph.dones_physicscalculator.View.Fragments.PerimeterFragment;
import mcm.edu.ph.dones_physicscalculator.View.Fragments.AreaFragment;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(findViewById(R.id.toolbar));

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_perimeter, R.id.nav_area,
                R.id.nav_sdt, R.id.nav_gas, R.id.nav_ke, R.id.nav_pe)
                .setOpenableLayout(drawer)
                .build();

        NavHostFragment navHost = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
        NavController navController = navHost.getNavController();

        NavInflater navInflater = navController.getNavInflater();
        NavGraph graph = navInflater.inflate(R.navigation.mobile_navigation);

        String formulas = getIntent().getExtras().getString("formulas");
        switch (formulas){
            case ("algebra"):
                graph.setStartDestination(R.id.nav_area);
                break;
            case ("geometry"):
                graph.setStartDestination(R.id.nav_perimeter);
                break;
            case ("physics"):
                graph.setStartDestination(R.id.nav_sdt);
                break;
        }

        navController.setGraph(graph);

        //NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
        //NavController navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                androidx.fragment.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

                switch (menuItem.getItemId()) {
                    case (R.id.nav_home):
                        Intent newIntent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(newIntent);
                        break;

                    case (R.id.nav_perimeter): fragment = new PerimeterFragment(); break;
                    case (R.id.nav_area): fragment = new AreaFragment(); break;
                    case (R.id.nav_volume): fragment = new VolumeFragment(); break;
                    case (R.id.nav_sdt): fragment = new SDTFragment(); break;
                    case (R.id.nav_gas): fragment = new GasFragment(); break;
                    case (R.id.nav_ke): fragment = new KEFragment(); break;
                    case (R.id.nav_pe): fragment = new PEFragment(); break;

                    default:
                        Toast.makeText(MainActivity.this, "Item can't be opened",
                                Toast.LENGTH_LONG).show();
                        break;
                }
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.addToBackStack(null);
                if (fragment != null) {
                    fragmentTransaction.replace(R.id.framelayout, fragment, null);
                }
                fragmentTransaction.commit();
                drawer.closeDrawers();
                return true;
            }
        });
    }

    //@Override
    //public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
    //    getMenuInflater().inflate(R.menu.main, menu);
    //    MenuCompat.setGroupDividerEnabled(menu, true);
    //    return true;
    //}

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        MenuCompat.setGroupDividerEnabled(menu, true);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
        NavController navController = navHostFragment.getNavController();
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}