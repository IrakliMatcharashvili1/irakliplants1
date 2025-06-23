package com.example.irakliplants1

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.irakliplants1.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    fun setSelectedBottomNavItem(itemId: Int) {
        binding.bottomNavView.selectedItemId = itemId
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavView.setOnItemSelectedListener { menuItem ->
            val destination = when (menuItem.itemId) {
                R.id.plantListFragment -> R.id.plantListFragment
                R.id.addPlantFragment -> R.id.addPlantFragment
                R.id.manuprofile -> R.id.manuprofile
                else -> null
            }

            destination?.let {
                if (navController.currentDestination?.id != it) {
                    navController.popBackStack(navController.graph.startDestinationId, false)
                    navController.navigate(it)
                }
                true
            } ?: false
        }

        if (auth.currentUser == null) {
            navController.navigate(R.id.fragmentSignIn)
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.fragmentSignIn,
                R.id.fragmentSignUp,
                R.id.fragmentForgetPassword -> binding.bottomNavView.visibility = View.GONE
                else -> binding.bottomNavView.visibility = View.VISIBLE
            }
        }

        if (auth.currentUser != null) {
            navController.navigate(R.id.plantListFragment)
        }
    }
}
