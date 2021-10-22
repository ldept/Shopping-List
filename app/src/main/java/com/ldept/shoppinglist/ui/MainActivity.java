package com.ldept.shoppinglist.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.tabs.TabLayout;
import com.ldept.shoppinglist.R;
import com.ldept.shoppinglist.databinding.ActivityMainBinding;
import com.ldept.shoppinglist.ui.archived_shopping_lists.ArchivedShoppingListsFragmentDirections;
import com.ldept.shoppinglist.ui.shopping_lists.ShoppingListsFragmentDirections;

public class MainActivity extends AppCompatActivity {


    private NavController navController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

        navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController);
        getSupportActionBar().setElevation(0F);

        TabLayout tabLayout = binding.tabLayout;
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0)
                    navController.navigate(
                            ArchivedShoppingListsFragmentDirections
                                    .actionShoppingListsArchivedFragmentToShoppingListsFragment()
                    );
                else
                    navController.navigate(
                            ShoppingListsFragmentDirections
                                    .actionShoppingListsFragmentToShoppingListsArchivedFragment()
                    );
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }
}
