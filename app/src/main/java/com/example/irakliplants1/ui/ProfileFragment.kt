package com.example.irakliplants1.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.irakliplants1.R
import com.google.firebase.auth.FirebaseAuth



class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var auth: FirebaseAuth
    private lateinit var logoutButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        logoutButton = view.findViewById(R.id.signOutButton)

        logoutButton.setOnClickListener {
            auth.signOut()
            findNavController().navigate(R.id.fragmentSignIn)
        }
    }
}
