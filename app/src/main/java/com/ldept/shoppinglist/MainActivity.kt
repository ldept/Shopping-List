//package com.ldept.shoppinglist
//
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import androidx.navigation.NavController
//import androidx.navigation.fragment.NavHostFragment
//import androidx.navigation.fragment.findNavController
//import androidx.navigation.ui.setupActionBarWithNavController
//import com.google.android.material.tabs.TabLayout
//import com.ldept.shoppinglist.ui.archived_shopping_lists.ShoppingListsArchivedFragmentDirections
//import com.ldept.shoppinglist.ui.shopping_lists.ShoppingListsFragmentDirections
//
//
//class MainActivity : AppCompatActivity() {
//
//    private lateinit var navController: NavController
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        val navHostFragment: NavHostFragment =
//            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        navController = navHostFragment.findNavController()
//
//        setupActionBarWithNavController(navController)
//        supportActionBar?.elevation = 0F
//
//        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
//        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//            }
//
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                when (tab?.position) {
//                    0 -> navController.navigate(
//                        ShoppingListsArchivedFragmentDirections
//                            .actionShoppingListsArchivedFragmentToShoppingListsFragment()
//                    )
//                    1 -> navController.navigate(
//                        ShoppingListsFragmentDirections
//                            .actionShoppingListsFragmentToShoppingListsArchivedFragment()
//                    )
//                }
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//            }
//        })
//    }
//
//    override fun onSupportNavigateUp(): Boolean {
//        return navController.navigateUp()
//    }
//
//}