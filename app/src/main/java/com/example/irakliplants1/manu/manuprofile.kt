package com.example.irakliplants1.manu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.irakliplants1.R
import com.example.irakliplants1.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class manuprofile : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("users")

        val currentUserId = auth.currentUser?.uid

        currentUserId?.let {
            database.child(it).child("fullName").get()
                .addOnSuccessListener { snapshot ->
                    val name = snapshot.value as? String ?: "მომხმარებელი"
                    binding.fullNameTextView.text = name
                }
                .addOnFailureListener {
                    binding.fullNameTextView.text = "შეცდომა მონაცემის მიღებისას"
                }
        }

        binding.changePasswordButton.setOnClickListener {
            safeNavigate(R.id.fragmentChangePassword)
        }

        binding.aboutAppButton.setOnClickListener {
            safeNavigate(R.id.fragmentAboutApp)
        }

        binding.signOutButton.setOnClickListener {
            auth.signOut()
            safeNavigate(R.id.fragmentSignIn)
        }
    }

    private fun safeNavigate(destinationId: Int) {
        val navController = findNavController()
        val navOptions = androidx.navigation.navOptions {
            popUpTo(R.id.manuprofile) {
                inclusive = false
            }
            launchSingleTop = true
        }
        if (navController.currentDestination?.id != destinationId) {
            navController.navigate(destinationId, null, navOptions)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
